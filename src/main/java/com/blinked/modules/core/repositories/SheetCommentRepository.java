package com.blinked.modules.core.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.blinked.modules.core.model.entities.SheetComment;
import com.blinked.modules.core.model.projection.CommentChildrenCountProjection;
import com.blinked.modules.core.model.projection.CommentCountProjection;
import com.blinked.modules.core.repositories.base.BaseCommentRepository;

/**
 * Sheet comment repository.
 *
 * @author ssatwa
 * @date 2019-04-24
 */
public interface SheetCommentRepository extends BaseCommentRepository<SheetComment> {

	/**
	 * Count comments by sheet ids.
	 *
	 * @param sheetIds sheet id collection must not be null
	 * @return a list of CommentCountProjection
	 */
	@Query("select new com.blinked.modules.core.model.projection.CommentCountProjection(count(comment.id), comment.postId) "
			+ "from SheetComment comment " + "where comment.postId in ?1 group by comment.postId")
	@NonNull
	@Override
	List<CommentCountProjection> countByPostIds(@NonNull Collection<Integer> sheetIds);

	/**
	 * Finds direct children count by comment ids.
	 *
	 * @param commentIds comment ids must not be null.
	 * @return a list of CommentChildrenCountProjection
	 */
	@Query("select new com.blinked.modules.core.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) "
			+ "from SheetComment comment " + "where comment.parentId in ?1 " + "group by comment.parentId")
	@NonNull
	@Override
	List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> commentIds);
}
