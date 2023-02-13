package test.modules.core.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.blinked.modules.core.exceptions.BadRequestException;
import com.blinked.modules.core.exceptions.NotFoundException;
import com.blinked.modules.core.model.dto.post.BasePostMinimalDTO;
import com.blinked.modules.core.model.entities.Sheet;
import com.blinked.modules.core.model.entities.SheetComment;
import com.blinked.modules.core.model.enums.SheetPermalinkType;
import com.blinked.modules.core.model.vo.SheetCommentWithSheetVO;
import com.blinked.modules.core.repositories.SheetCommentRepository;
import com.blinked.modules.core.repositories.SheetRepository;
import com.blinked.modules.core.services.OptionService;
import com.blinked.modules.core.services.SheetCommentService;
import com.blinked.modules.core.services.UserService;
import com.blinked.modules.core.utils.ServiceUtils;

/**
 * Sheet comment service implementation.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
@Service
public class SheetCommentServiceImpl extends BaseCommentServiceImpl<SheetComment> implements SheetCommentService {

	private final SheetRepository sheetRepository;

	public SheetCommentServiceImpl(SheetCommentRepository sheetCommentRepository, OptionService optionService,
			UserService userService, ApplicationEventPublisher eventPublisher, SheetRepository sheetRepository) {
		super(sheetCommentRepository, optionService, userService, eventPublisher);
		this.sheetRepository = sheetRepository;
	}

	@Override
	public void validateTarget(Integer sheetId) {
		Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(
				() -> new NotFoundException("Cannot find the information of this page").setErrorData(sheetId));

		if (sheet.getDisallowComment()) {
			throw new BadRequestException("This page has been banned from comments").setErrorData(sheetId);
		}
	}

	@Override
	public SheetCommentWithSheetVO convertToWithSheetVo(SheetComment comment) {
		Assert.notNull(comment, "SheetComment must not be null");
		SheetCommentWithSheetVO sheetCommentWithSheetVO = new SheetCommentWithSheetVO().convertFrom(comment);

		BasePostMinimalDTO basePostMinimalDTO = new BasePostMinimalDTO()
				.convertFrom(sheetRepository.getOne(comment.getPostId()));

		sheetCommentWithSheetVO.setSheet(buildSheetFullPath(basePostMinimalDTO));
		return sheetCommentWithSheetVO;
	}

	@Override
	public List<SheetCommentWithSheetVO> convertToWithSheetVo(List<SheetComment> sheetComments) {
		if (CollectionUtils.isEmpty(sheetComments)) {
			return Collections.emptyList();
		}

		Set<Integer> sheetIds = ServiceUtils.fetchProperty(sheetComments, SheetComment::getPostId);

		Map<Integer, Sheet> sheetMap = ServiceUtils.convertToMap(sheetRepository.findAllById(sheetIds), Sheet::getId);

		return sheetComments.stream().filter(comment -> sheetMap.containsKey(comment.getPostId())).map(comment -> {
			SheetCommentWithSheetVO sheetCmtWithPostVO = new SheetCommentWithSheetVO().convertFrom(comment);

			BasePostMinimalDTO postMinimalDTO = new BasePostMinimalDTO().convertFrom(sheetMap.get(comment.getPostId()));

			sheetCmtWithPostVO.setSheet(buildSheetFullPath(postMinimalDTO));
			return sheetCmtWithPostVO;
		}).collect(Collectors.toList());
	}

	private BasePostMinimalDTO buildSheetFullPath(BasePostMinimalDTO basePostMinimalDTO) {
		StringBuilder fullPath = new StringBuilder();

		SheetPermalinkType permalinkType = optionService.getSheetPermalinkType();

		if (optionService.isEnabledAbsolutePath()) {
			fullPath.append(optionService.getBlogBaseUrl());
		}

		basePostMinimalDTO.setFullPath(fullPath.toString());
		return basePostMinimalDTO;
	}

	@Override
	public Page<SheetCommentWithSheetVO> convertToWithSheetVo(Page<SheetComment> sheetCommentPage) {
		Assert.notNull(sheetCommentPage, "Sheet comment page must not be null");

		return new PageImpl<>(convertToWithSheetVo(sheetCommentPage.getContent()), sheetCommentPage.getPageable(),
				sheetCommentPage.getTotalElements());

	}
}
