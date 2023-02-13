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
import com.blinked.modules.core.exceptions.ForbiddenException;
import com.blinked.modules.core.exceptions.NotFoundException;
import com.blinked.modules.core.model.dto.post.BasePostMinimalDTO;
import com.blinked.modules.core.model.entities.Post;
import com.blinked.modules.core.model.entities.PostComment;
import com.blinked.modules.core.model.enums.CommentViolationTypeEnum;
import com.blinked.modules.core.model.enums.PostPermalinkType;
import com.blinked.modules.core.model.properties.CommentProperties;
import com.blinked.modules.core.model.vo.PostCommentWithPostVO;
import com.blinked.modules.core.repositories.PostCommentRepository;
import com.blinked.modules.core.repositories.PostRepository;
import com.blinked.modules.core.services.CommentBlackListService;
import com.blinked.modules.core.services.OptionService;
import com.blinked.modules.core.services.PostCommentService;
import com.blinked.modules.core.services.UserService;
import com.blinked.modules.core.utils.ServiceUtils;
import com.blinked.modules.core.utils.ServletUtils;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * PostCommentService implementation class
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Slf4j
@Service
public class PostCommentServiceImpl extends BaseCommentServiceImpl<PostComment> implements PostCommentService {

	private final PostRepository postRepository;

	private final CommentBlackListService commentBlackListService;

	public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostRepository postRepository,
			UserService userService, OptionService optionService, CommentBlackListService commentBlackListService,
			ApplicationEventPublisher eventPublisher) {
		super(postCommentRepository, optionService, userService, eventPublisher);
		this.postRepository = postRepository;
		this.commentBlackListService = commentBlackListService;
	}

	@Override
	public Page<PostCommentWithPostVO> convertToWithPostVo(Page<PostComment> commentPage) {
		Assert.notNull(commentPage, "PostComment page must not be null");

		return new PageImpl<>(convertToWithPostVo(commentPage.getContent()), commentPage.getPageable(),
				commentPage.getTotalElements());

	}

	@Override
	public PostCommentWithPostVO convertToWithPostVo(PostComment comment) {
		Assert.notNull(comment, "PostComment must not be null");
		PostCommentWithPostVO postCommentWithPostVO = new PostCommentWithPostVO().convertFrom(comment);

		BasePostMinimalDTO basePostMinimalDTO = new BasePostMinimalDTO()
				.convertFrom(postRepository.getOne(comment.getPostId()));

		postCommentWithPostVO.setPost(buildPostFullPath(basePostMinimalDTO));
		return postCommentWithPostVO;
	}

	@Override
	public List<PostCommentWithPostVO> convertToWithPostVo(List<PostComment> postComments) {
		if (CollectionUtils.isEmpty(postComments)) {
			return Collections.emptyList();
		}

		// Fetch goods ids
		Set<Integer> postIds = ServiceUtils.fetchProperty(postComments, PostComment::getPostId);

		// Get all posts
		Map<Integer, Post> postMap = ServiceUtils.convertToMap(postRepository.findAllById(postIds), Post::getId);

		return postComments.stream().filter(comment -> postMap.containsKey(comment.getPostId())).map(comment -> {
			// Convert to vo
			PostCommentWithPostVO postCommentWithPostVO = new PostCommentWithPostVO().convertFrom(comment);

			BasePostMinimalDTO basePostMinimalDTO = new BasePostMinimalDTO()
					.convertFrom(postMap.get(comment.getPostId()));

			postCommentWithPostVO.setPost(buildPostFullPath(basePostMinimalDTO));

			return postCommentWithPostVO;
		}).collect(Collectors.toList());
	}

	private BasePostMinimalDTO buildPostFullPath(BasePostMinimalDTO post) {
		PostPermalinkType permalinkType = optionService.getPostPermalinkType();

		String pathSuffix = optionService.getPathSuffix();

		String archivesPrefix = optionService.getArchivesPrefix();

		int month = DateUtil.month(post.getCreateTime()) + 1;

		String monthString = month < 10 ? "0" + month : String.valueOf(month);

		int day = DateUtil.dayOfMonth(post.getCreateTime());

		String dayString = day < 10 ? "0" + day : String.valueOf(day);

		StringBuilder fullPath = new StringBuilder();

		if (optionService.isEnabledAbsolutePath()) {
			fullPath.append(optionService.getBlogBaseUrl());
		}

		post.setFullPath(fullPath.toString());

		return post;
	}

	@Override
	public void validateTarget(Integer postId) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new NotFoundException("Can't find information about this article").setErrorData(postId));

		if (post.getDisallowComment()) {
			throw new BadRequestException("Comments on this article have been banned").setErrorData(postId);
		}
	}

	@Override
	public void validateCommentBlackListStatus() {
		CommentViolationTypeEnum banStatus = commentBlackListService.commentsBanStatus(ServletUtils.getRequestIp());
		Integer banTime = optionService.getByPropertyOrDefault(CommentProperties.COMMENT_BAN_TIME, Integer.class, 10);
		if (banStatus == CommentViolationTypeEnum.FREQUENTLY) {
			throw new ForbiddenException(
					String.format("Your comments are too frequent, please try again in %s minutes。", banTime));
		}
	}

}
