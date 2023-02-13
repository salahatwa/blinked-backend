package com.blinked.modules.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blinked.modules.core.model.entities.Post;
import com.blinked.modules.core.model.enums.PostStatus;
import com.blinked.modules.core.repositories.base.BasePostRepository;

/**
 * Post repository.
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface PostRepository extends BasePostRepository<Post>, JpaSpecificationExecutor<Post> {

	/**
	 * Count all post visits.
	 *
	 * @return visits.
	 */
	@Override
	@Query("select sum(p.visits) from Post p")
	Long countVisit();

	/**
	 * Count all post likes.
	 *
	 * @return likes.
	 */
	@Override
	@Query("select sum(p.likes) from Post p")
	Long countLike();

	/**
	 * Find by post year and month and slug.
	 *
	 * @param year  post create year
	 * @param month post create month
	 * @param slug  post slug
	 * @return a optional of post
	 */
	@Query("select post from Post post where year(post.createTime) = :year and month(post.createTime) = :month and post.slug = :slug")
	Optional<Post> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("slug") String slug);

	/**
	 * Find by post year and slug.
	 *
	 * @param year post create year
	 * @param slug post slug
	 * @return a optional of post
	 */
	@Query("select post from Post post where year(post.createTime) = :year and post.slug = :slug")
	Optional<Post> findBy(@Param("year") Integer year, @Param("slug") String slug);

	/**
	 * Find by post year and month and slug and status.
	 *
	 * @param year   post create year
	 * @param month  post create month
	 * @param slug   post slug
	 * @param status post status
	 * @return a optional of post
	 */
	@Query("select post from Post post where year(post.createTime) = :year and month(post.createTime) = :month and post.slug = :slug and post.status = :status")
	Optional<Post> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("slug") String slug,
			@Param("status") PostStatus status);

	/**
	 * Find by post year and month and day and slug.
	 *
	 * @param year  post create year
	 * @param month post create month
	 * @param day   post create day
	 * @param slug  post slug
	 * @return a optional of post
	 */
	@Query("select post from Post post where year(post.createTime) = :year and month(post.createTime) = :month and day(post.createTime) = :day and post.slug = :slug")
	Optional<Post> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day,
			@Param("slug") String slug);

	/**
	 * Find by post year and month and day and slug and status.
	 *
	 * @param year   post create year
	 * @param month  post create month
	 * @param day    post create day
	 * @param slug   post slug
	 * @param status post status
	 * @return a optional of post
	 */
	@Query("select post from Post post where year(post.createTime) = :year and month(post.createTime) = :month and day(post.createTime) = :day and post.slug = :slug and post.status = :status")
	Optional<Post> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day,
			@Param("slug") String slug, @Param("status") PostStatus status);
}
