package com.blinked.modules.core.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.blinked.modules.core.model.entities.PostComment;
import com.blinked.modules.core.model.vo.PostCommentWithPostVO;
import com.blinked.modules.core.services.base.BaseCommentService;

/**
 * Post comment service interface.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-14
 */
public interface PostCommentService extends BaseCommentService<PostComment> {

	/**
	 * Converts to with post vo.
	 *
	 * @param commentPage comment page must not be null
	 * @return a page of comment with post vo
	 */
	@NonNull
	Page<PostCommentWithPostVO> convertToWithPostVo(@NonNull Page<PostComment> commentPage);

	/**
	 * Converts to with post vo
	 *
	 * @param comment comment
	 * @return a comment with post vo
	 */
	@NonNull
	PostCommentWithPostVO convertToWithPostVo(@NonNull PostComment comment);

	/**
	 * Converts to with post vo
	 *
	 * @param postComments comment list
	 * @return a list of comment with post vo
	 */
	@NonNull
	List<PostCommentWithPostVO> convertToWithPostVo(@Nullable List<PostComment> postComments);

	/**
	 * Validate CommentBlackList Status
	 */
	void validateCommentBlackListStatus();
}
