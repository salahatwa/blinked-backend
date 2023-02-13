package com.blinked.modules.core.repositories;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import com.blinked.modules.core.model.entities.PostComment;
import com.blinked.modules.core.model.projection.CommentChildrenCountProjection;
import com.blinked.modules.core.model.projection.CommentCountProjection;
import com.blinked.modules.core.repositories.base.BaseCommentRepository;

/**
 * PostComment repository.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-21
 */
public interface PostCommentRepository extends BaseCommentRepository<PostComment> {

	/**
	 * Count comments by post ids.
	 *
	 * @param postIds post id collection must not be null
	 * @return a list of CommentCountProjection
	 */
	@Query("select new com.blinked.modules.core.model.projection.CommentCountProjection(count(comment.id), comment.postId) "
			+ "from PostComment comment " + "where comment.postId in ?1 group by comment.postId")
	@NonNull
	@Override
	List<CommentCountProjection> countByPostIds(@NonNull Collection<Integer> postIds);

	/**
	 * Finds direct children count by comment ids.
	 *
	 * @param commentIds comment ids must not be null.
	 * @return a list of CommentChildrenCountProjection
	 */
	@Query("select new com.blinked.modules.core.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) "
			+ "from PostComment comment " + "where comment.parentId in ?1 " + "group by comment.parentId")
	@NonNull
	@Override
	List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Collection<Long> commentIds);

	/**
	 *
	 * @param ipAddress IP地址
	 * @param startTime 起始时间
	 * @param endTime   结束时间
	 * @return 评论次数
	 */
	@Query("SELECT COUNT(id) FROM PostComment WHERE ipAddress=?1 AND updateTime BETWEEN ?2 AND ?3 AND status <> 2")
	int countByIpAndTime(String ipAddress, Date startTime, Date endTime);
}
