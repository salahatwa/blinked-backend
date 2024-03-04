package com.blinked.services.impl;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.api.common.exception.NotFoundException;
import com.api.common.repo.AbstractCrudService;
import com.api.common.utils.ServiceUtils;
import com.api.common.utils.ServletUtils;
import com.blinked.apis.requests.BaseRateParam;
import com.blinked.apis.requests.RatePage;
import com.blinked.apis.requests.RateQuery;
import com.blinked.apis.responses.BaseRateVO;
import com.blinked.apis.responses.BaseRateWithParentVO;
import com.blinked.apis.responses.RateWithHasChildrenVO;
import com.blinked.entities.BaseRate;
import com.blinked.entities.dto.BaseRateDTO;
import com.blinked.entities.enums.RateStatus;
import com.blinked.entities.projection.RateChildrenCountProjection;
import com.blinked.entities.projection.RateCountProjection;
import com.blinked.repositories.BaseRateRepository;
import com.blinked.services.BaseRateService;
import com.blinked.services.UserService;

import cn.hutool.core.util.URLUtil;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * Base rate service implementation.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
@Slf4j
public abstract class BaseRateServiceImpl<RATE extends BaseRate> extends AbstractCrudService<RATE, Long>
		implements BaseRateService<RATE> {

	protected final UserService userService;
	private final BaseRateRepository<RATE> baseRateRepository;

	public BaseRateServiceImpl(BaseRateRepository<RATE> baseRateRepository, UserService userService) {
		super(baseRateRepository);
		this.baseRateRepository = baseRateRepository;
		this.userService = userService;
	}

	@Override
	public List<RATE> listBy(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");

		return baseRateRepository.findAllByProductId(productId);
	}

	@Override
	public Page<RATE> pageLatest(int top) {
		return pageLatest(top, null);
	}

	@Override
	public Page<RATE> pageLatest(int top, RateStatus status) {
		if (status == null) {
			return listAll(ServiceUtils.buildLatestPageable(top));
		}

		return baseRateRepository.findAllByStatus(status, ServiceUtils.buildLatestPageable(top));
	}

	@Override
	public Page<RATE> pageBy(RateStatus status, Pageable pageable) {

		Assert.notNull(status, "Rate status must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Find all
		return baseRateRepository.findAllByStatus(status, pageable);
	}

	@Override
	public Page<RATE> pageBy(RateQuery rateQuery, Pageable pageable) {
		Assert.notNull(pageable, "Page info must not be null");

		return baseRateRepository.findAll(buildSpecByQuery(rateQuery), pageable);
	}

	@Override
	public Page<BaseRateVO> pageVosAllBy(Integer productId, Pageable pageable) {
		Assert.notNull(productId, "Product id must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		log.debug("Getting rate tree view of post: [{}], page info: [{}]", productId, pageable);

		// List all the top rates (Caution: This list will be cleared)
		List<RATE> rates = baseRateRepository.findAllByProductId(productId);

		return pageVosBy(rates, pageable);
	}

	@Override
	public Page<BaseRateVO> pageVosBy(List<RATE> rates, Pageable pageable) {
		Assert.notNull(rates, "Rates must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		Comparator<BaseRateVO> rateComparator = buildRateComparator(
				pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createTime")));

		// Convert to vo
		List<BaseRateVO> topRates = convertToVo(rates, rateComparator);

		List<BaseRateVO> pageContent;

		// Calc the shear index
		int startIndex = pageable.getPageNumber() * pageable.getPageSize();
		if (startIndex >= topRates.size() || startIndex < 0) {
			pageContent = Collections.emptyList();
		} else {
			int endIndex = startIndex + pageable.getPageSize();
			if (endIndex > topRates.size()) {
				endIndex = topRates.size();
			}

			log.debug("Top rates size: [{}]", topRates.size());
			log.debug("Start index: [{}]", startIndex);
			log.debug("End index: [{}]", endIndex);

			pageContent = topRates.subList(startIndex, endIndex);
		}

		return new RatePage<>(pageContent, pageable, topRates.size(), rates.size());
	}

	@Override
	public Page<BaseRateVO> pageVosBy(Integer productId, Pageable pageable) {
		Assert.notNull(productId, "Product id must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		log.debug("Getting rate tree view of post: [{}], page info: [{}]", productId, pageable);

		// List all the top rates (Caution: This list will be cleared)
		List<RATE> rates = baseRateRepository.findAllByProductIdAndStatus(productId, RateStatus.PUBLISHED);

		return pageVosBy(rates, pageable);
	}

	@Override
	public Page<BaseRateWithParentVO> pageWithParentVoBy(Integer productId, Pageable pageable) {
		Assert.notNull(productId, "Product id must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		log.debug("Getting rate list view of post: [{}], page info: [{}]", productId, pageable);

		// List all the top rates (Caution: This list will be cleared)
		Page<RATE> ratePage = baseRateRepository.findAllByProductIdAndStatus(productId, RateStatus.PUBLISHED, pageable);

		// Get all rates
		List<RATE> rates = ratePage.getContent();

		// Get all rate parent ids
		Set<Long> parentIds = ServiceUtils.fetchProperty(rates, RATE::getParentId);

		// Get all parent rates
		List<RATE> parentRates = baseRateRepository.findAllByIdIn(parentIds, pageable.getSort());

		// Convert to rate map (Key: rate id, value: rate)
		Map<Long, RATE> parentRateMap = ServiceUtils.convertToMap(parentRates, RATE::getId);

		Map<Long, BaseRateWithParentVO> parentRateVoMap = new HashMap<>(parentRateMap.size());

		// Convert to rate page
		return ratePage.map(rate -> {
			// Convert to with parent vo
			BaseRateWithParentVO rateWithParentVO = new BaseRateWithParentVO().convertFrom(rate);

			// Get parent rate vo from cache
			BaseRateWithParentVO parentRateVo = parentRateVoMap.get(rate.getParentId());

			if (parentRateVo == null) {
				// Get parent rate
				RATE parentRate = parentRateMap.get(rate.getParentId());

				if (parentRate != null) {
					// Convert to parent rate vo
					parentRateVo = new BaseRateWithParentVO().convertFrom(parentRate);
					// Cache the parent rate vo
					parentRateVoMap.put(parentRate.getId(), parentRateVo);
				}
			}

			// Set parent
			rateWithParentVO.setParent(parentRateVo == null ? null : parentRateVo.clone());

			return rateWithParentVO;
		});
	}

	@Override
	public Map<Integer, Long> countByProductIds(Collection<Integer> productIds) {
		if (CollectionUtils.isEmpty(productIds)) {
			return Collections.emptyMap();
		}

		// Get all rate counts
		List<RateCountProjection> rateCountProjections = baseRateRepository.countByProductIds(productIds);

		return ServiceUtils.convertToMap(rateCountProjections, RateCountProjection::getProductId,
				RateCountProjection::getCount);
	}

	@Override
	public long countByProductId(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");
		return baseRateRepository.countByProductId(productId);
	}

	@Override
	public long countByStatus(RateStatus status) {
		return baseRateRepository.countByStatus(status);
	}

	@Override
	public RATE create(RATE rate) {
		Assert.notNull(rate, "Domain must not be null");

		// Check post id
		if (!ServiceUtils.isEmptyId(rate.getProductId())) {
			validateTarget(rate.getProductId());
		}

		// Check parent id
		if (!ServiceUtils.isEmptyId(rate.getParentId())) {
			try {
				mustExistById(rate.getParentId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Check user login status and set this field
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Set some default values
//		if (rate.getIpAddress() == null) {
//			rate.setIpAddress(ServletUtils.getRequestIp());
//		}
//
//		if (rate.getUserAgent() == null) {
//			rate.setUserAgent(ServletUtils.getHeaderIgnoreCase(HttpHeaders.USER_AGENT));
//		}

		if (rate.getGravatarMd5() == null) {
			rate.setGravatarMd5(DigestUtils.md5Hex(rate.getEmail()));
		}

		if (StringUtils.isNotEmpty(rate.getAuthorUrl())) {
			rate.setAuthorUrl(URLUtil.normalize(rate.getAuthorUrl()));
		}

		if (authentication != null) {
			// Rate of blogger
			rate.setIsAdmin(true);
			rate.setStatus(RateStatus.PUBLISHED);
		} else {
			// Rate of guest
			rate.setStatus(RateStatus.PUBLISHED);
		}

		// Create rate
		RATE createdRate = super.create(rate);

		return createdRate;
	}

	@Override
	public RATE createBy(BaseRateParam<RATE> rateParam) {
		Assert.notNull(rateParam, "Rate param must not be null");

		// Check user login status and set this field
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//		if (authentication != null) {
//			// Blogger rate
//			User user = authentication.getDetail().getUser();
//			rateParam.setAuthor(StringUtils.isBlank(user.getNickname()) ? user.getUsername() : user.getNickname());
//			rateParam.setEmail(user.getEmail());
//			rateParam.setAuthorUrl(optionService.getByPropertyOrDefault(BlogProperties.BLOG_URL, String.class, null));
//		}
//
//	
//
//		if (authentication == null) {
//			// Anonymous rate
//			// Check email
//			if (userService.getByEmail(rateParam.getEmail()).isPresent()) {
//				throw new BadRequestException(
//						"The blogger’s mailbox cannot be used. If you are a blogger, please log in to the management terminal to reply。");
//			}
//		}

		// Convert to rate
		return create(rateParam.convertTo());
	}

	@Override
	public RATE updateStatus(Long rateId, RateStatus status) {
		Assert.notNull(rateId, "Rate id must not be null");
		Assert.notNull(status, "Rate status must not be null");

		// Get rate by id
		RATE rate = getById(rateId);

		// Set rate status
		rate.setStatus(status);

		// Update rate
		return update(rate);
	}

	@Override
	public List<RATE> updateStatusByIds(List<Long> ids, RateStatus status) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return ids.stream().map(id -> {
			return updateStatus(id, status);
		}).collect(Collectors.toList());
	}

	@Override
	public List<RATE> removeByProductId(Integer productId) {
		Assert.notNull(productId, "Product id must not be null");
		return baseRateRepository.deleteByProductId(productId);
	}

	@Override
	public RATE removeById(Long id) {
		Assert.notNull(id, "Rate id must not be null");

		RATE rate = baseRateRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("No information found for this rate").setErrorData(id));

		List<RATE> children = listChildrenBy(rate.getProductId(), id, Sort.by(DESC, "createTime"));

		if (children.size() > 0) {
			children.forEach(child -> {
				super.removeById(child.getId());
			});
		}

		return super.removeById(id);
	}

	@Override
	public List<RATE> removeByIds(Collection<Long> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		return ids.stream().map(this::removeById).collect(Collectors.toList());
	}

	@Override
	public List<BaseRateDTO> convertTo(List<RATE> rates) {
		if (CollectionUtils.isEmpty(rates)) {
			return Collections.emptyList();
		}
		return rates.stream().map(this::convertTo).collect(Collectors.toList());
	}

	@Override
	public Page<BaseRateDTO> convertTo(Page<RATE> ratePage) {
		Assert.notNull(ratePage, "Rate page must not be null");

		return ratePage.map(this::convertTo);
	}

	@Override
	public BaseRateDTO convertTo(RATE rate) {
		Assert.notNull(rate, "Rate must not be null");

		return new BaseRateDTO().convertFrom(rate);
	}

	@NonNull
	protected Specification<RATE> buildSpecByQuery(@NonNull RateQuery rateQuery) {
		Assert.notNull(rateQuery, "Rate query must not be null");

		return (Specification<RATE>) (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new LinkedList<>();

			if (rateQuery.getStatus() != null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), rateQuery.getStatus()));
			}

			if (rateQuery.getKeyword() != null) {

				String likeCondition = String.format("%%%s%%", StringUtils.strip(rateQuery.getKeyword()));

				Predicate authorLike = criteriaBuilder.like(root.get("author"), likeCondition);
				Predicate contentLike = criteriaBuilder.like(root.get("content"), likeCondition);
				Predicate emailLike = criteriaBuilder.like(root.get("email"), likeCondition);

				predicates.add(criteriaBuilder.or(authorLike, contentLike, emailLike));
			}

			return query.where(predicates.toArray(new Predicate[0])).getRestriction();
		};
	}

	/**
	 * Builds a rate comparator.
	 *
	 * @param sort sort info
	 * @return rate comparator
	 */
	protected Comparator<BaseRateVO> buildRateComparator(Sort sort) {
		return (currentRate, toCompareRate) -> {
			Assert.notNull(currentRate, "Current rate must not be null");
			Assert.notNull(toCompareRate, "Rate to compare must not be null");

			// Get sort order
			Sort.Order order = sort.filter(anOrder -> "id".equals(anOrder.getProperty())).get().findFirst()
					.orElseGet(() -> Sort.Order.desc("id"));

			// Init sign
			int sign = order.getDirection().isAscending() ? 1 : -1;

			// Compare id property
			return sign * currentRate.getId().compareTo(toCompareRate.getId());
		};
	}

	@NonNull
	@Override
	public List<BaseRateVO> convertToVo(@Nullable List<RATE> rates, @Nullable Comparator<BaseRateVO> comparator) {
		if (CollectionUtils.isEmpty(rates)) {
			return Collections.emptyList();
		}

		// Init the top virtual rate
		BaseRateVO topVirtualRate = new BaseRateVO();
		topVirtualRate.setId(0L);
		topVirtualRate.setChildren(new LinkedList<>());

		// Concrete the rate tree
		concreteTree(topVirtualRate, new LinkedList<>(rates), comparator);

		return topVirtualRate.getChildren();
	}

	@Override
	public Page<RateWithHasChildrenVO> pageTopRatesBy(Integer targetId, RateStatus status, Pageable pageable) {
		Assert.notNull(targetId, "Target id must not be null");
		Assert.notNull(status, "Rate status must not be null");
		Assert.notNull(pageable, "Page info must not be null");

		// Get all rates
		Page<RATE> topRatePage = baseRateRepository.findAllByProductIdAndStatusAndParentId(targetId, status, 0L,
				pageable);

//		if (topRatePage.isEmpty()) {
//			// If the rates is empty
//			return ServiceUtils.buildEmptyPageImpl(topRatePage);
//		}

		// Get top rate ids
		Set<Long> topRateIds = ServiceUtils.fetchProperty(topRatePage.getContent(), BaseRate::getId);

		// Get direct children count
		List<RateChildrenCountProjection> directChildrenCount = baseRateRepository.findDirectChildrenCount(topRateIds);

		// Convert to rate - children count map
		Map<Long, Long> rateChildrenCountMap = ServiceUtils.convertToMap(directChildrenCount,
				RateChildrenCountProjection::getRateId, RateChildrenCountProjection::getDirectChildrenCount);

		// Convert to rate with has children vo
		return topRatePage.map(topRate -> {
			RateWithHasChildrenVO rate = new RateWithHasChildrenVO().convertFrom(topRate);
			rate.setHasChildren(rateChildrenCountMap.getOrDefault(topRate.getId(), 0L) > 0);
			return rate;
		});
	}

	@Override
	public List<RATE> listChildrenBy(Integer targetId, Long rateParentId, RateStatus status, Sort sort) {
		Assert.notNull(targetId, "Target id must not be null");
		Assert.notNull(rateParentId, "Rate parent id must not be null");
		Assert.notNull(sort, "Sort info must not be null");

		// Get rates recursively

		// Get direct children
		List<RATE> directChildren = baseRateRepository.findAllByProductIdAndStatusAndParentId(targetId, status,
				rateParentId);

		// Create result container
		Set<RATE> children = new HashSet<>();

		// Get children rates
		getChildrenRecursively(directChildren, status, children);

		// Sort children
		List<RATE> childrenList = new ArrayList<>(children);
		childrenList.sort(Comparator.comparing(BaseRate::getId));

		return childrenList;
	}

	@Override
	public List<RATE> listChildrenBy(Integer targetId, Long rateParentId, Sort sort) {
		Assert.notNull(targetId, "Target id must not be null");
		Assert.notNull(rateParentId, "Rate parent id must not be null");
		Assert.notNull(sort, "Sort info must not be null");

		// Get rates recursively

		// Get direct children
		List<RATE> directChildren = baseRateRepository.findAllByProductIdAndParentId(targetId, rateParentId);

		// Create result container
		Set<RATE> children = new HashSet<>();

		// Get children rates
		getChildrenRecursively(directChildren, children);

		// Sort children
		List<RATE> childrenList = new ArrayList<>(children);
		childrenList.sort(Comparator.comparing(BaseRate::getId));

		return childrenList;
	}

	@Override
	@Deprecated
	public <T extends BaseRateDTO> T filterIpAddress(@NonNull T rate) {
		Assert.notNull(rate, "Base rate dto must not be null");

		// Clear ip address
		rate.setIpAddress("");

		// Handle base rate vo
		if (rate instanceof BaseRateVO) {
			BaseRateVO baseRateVO = (BaseRateVO) rate;
			Queue<BaseRateVO> rateQueue = new LinkedList<>();
			rateQueue.offer(baseRateVO);
			while (!rateQueue.isEmpty()) {
				BaseRateVO current = rateQueue.poll();

				// Clear ip address
				current.setIpAddress("");

				if (!CollectionUtils.isEmpty(current.getChildren())) {
					// Add children
					rateQueue.addAll(current.getChildren());
				}
			}
		}

		return rate;
	}

	@Override
	@Deprecated
	public <T extends BaseRateDTO> List<T> filterIpAddress(List<T> rates) {
		if (CollectionUtils.isEmpty(rates)) {
			return Collections.emptyList();
		}

		rates.forEach(this::filterIpAddress);

		return rates;
	}

	@Override
	@Deprecated
	public <T extends BaseRateDTO> Page<T> filterIpAddress(Page<T> ratePage) {
		Assert.notNull(ratePage, "Rate page must not be null");
		ratePage.forEach(this::filterIpAddress);

		return ratePage;
	}

	@Override
	public List<BaseRateDTO> replaceUrl(String oldUrl, String newUrl) {
		List<RATE> rates = listAll();
		List<RATE> replaced = new ArrayList<>();
		rates.forEach(rate -> {
			if (StringUtils.isNotEmpty(rate.getAuthorUrl())) {
				rate.setAuthorUrl(rate.getAuthorUrl().replaceAll(oldUrl, newUrl));
			}
			replaced.add(rate);
		});
		List<RATE> updated = updateInBatch(replaced);
		return convertTo(updated);
	}

	/**
	 * Get children rates recursively.
	 *
	 * @param topRates top rate list
	 * @param status   rate status must not be null
	 * @param children children result must not be null
	 */
	private void getChildrenRecursively(@Nullable List<RATE> topRates, @NonNull RateStatus status,
			@NonNull Set<RATE> children) {
		Assert.notNull(status, "Rate status must not be null");
		Assert.notNull(children, "Children rate set must not be null");

		if (CollectionUtils.isEmpty(topRates)) {
			return;
		}

		// Convert rate id set
		Set<Long> rateIds = ServiceUtils.fetchProperty(topRates, RATE::getId);

		// Get direct children
		List<RATE> directChildren = baseRateRepository.findAllByStatusAndParentIdIn(status, rateIds);

		// Recursively invoke
		getChildrenRecursively(directChildren, status, children);

		// Add direct children to children result
		children.addAll(topRates);
	}

	/**
	 * Get children rates recursively.
	 *
	 * @param topRates top rate list
	 * @param children children result must not be null
	 */
	private void getChildrenRecursively(@Nullable List<RATE> topRates, @NonNull Set<RATE> children) {
		Assert.notNull(children, "Children rate set must not be null");

		if (CollectionUtils.isEmpty(topRates)) {
			return;
		}

		// Convert rate id set
		Set<Long> rateIds = ServiceUtils.fetchProperty(topRates, RATE::getId);

		// Get direct children
		List<RATE> directChildren = baseRateRepository.findAllByParentIdIn(rateIds);

		// Recursively invoke
		getChildrenRecursively(directChildren, children);

		// Add direct children to children result
		children.addAll(topRates);
	}

	/**
	 * Concretes rate tree.
	 *
	 * @param parentRate     parent rate vo must not be null
	 * @param rates          rate list must not null
	 * @param rateComparator rate vo comparator
	 */
	protected void concreteTree(@NonNull BaseRateVO parentRate, @Nullable Collection<RATE> rates,
			@Nullable Comparator<BaseRateVO> rateComparator) {
		Assert.notNull(parentRate, "Parent rate must not be null");

		if (CollectionUtils.isEmpty(rates)) {
			return;
		}

		// Get children
		List<RATE> children = rates.stream().filter(rate -> Objects.equals(parentRate.getId(), rate.getParentId()))
				.collect(Collectors.toList());

		// Add children
		children.forEach(rate -> {
			// Convert to rate vo
			BaseRateVO rateVO = new BaseRateVO().convertFrom(rate);

			if (parentRate.getChildren() == null) {
				parentRate.setChildren(new LinkedList<>());
			}

			parentRate.getChildren().add(rateVO);
		});

		// Remove children
		rates.removeAll(children);

		if (!CollectionUtils.isEmpty(parentRate.getChildren())) {
			// Recursively concrete the children
			parentRate.getChildren().forEach(childRate -> concreteTree(childRate, rates, rateComparator));
			// Sort the children
			if (rateComparator != null) {
				parentRate.getChildren().sort(rateComparator);
			}
		}
	}

}
