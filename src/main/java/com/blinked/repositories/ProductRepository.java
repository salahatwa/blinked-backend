package com.blinked.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blinked.entities.Product;
import com.blinked.entities.enums.ProductStatus;

/**
 *
 * @author ssatwa
 * @date 2019-03-19
 */
public interface ProductRepository extends BaseProductRepository<Product>, JpaSpecificationExecutor<Product> {

	/**
	 * Count all product visits.
	 *
	 * @return visits.
	 */
	@Override
	@Query("select sum(p.visits) from Product p")
	Long countVisit();

	/**
	 * Count all product likes.
	 *
	 * @return likes.
	 */
	@Override
	@Query("select sum(p.likes) from Product p")
	Long countLike();

	/**
	 * Find by product year and month and slug.
	 *
	 * @param year  product create year
	 * @param month product create month
	 * @param slug  product slug
	 * @return a optional of product
	 */
	@Query("select product from Product product where year(product.createTime) = :year and month(product.createTime) = :month and product.slug = :slug")
	Optional<Product> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("slug") String slug);

	/**
	 * Find by product year and slug.
	 *
	 * @param year product create year
	 * @param slug product slug
	 * @return a optional of product
	 */
	@Query("select product from Product product where year(product.createTime) = :year and product.slug = :slug")
	Optional<Product> findBy(@Param("year") Integer year, @Param("slug") String slug);

	/**
	 * Find by product year and month and slug and status.
	 *
	 * @param year   product create year
	 * @param month  product create month
	 * @param slug   product slug
	 * @param status product status
	 * @return a optional of product
	 */
	@Query("select product from Product product where year(product.createTime) = :year and month(product.createTime) = :month and product.slug = :slug and product.status = :status")
	Optional<Product> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("slug") String slug,
			@Param("status") ProductStatus status);

	/**
	 * Find by product year and month and day and slug.
	 *
	 * @param year  product create year
	 * @param month product create month
	 * @param day   product create day
	 * @param slug  product slug
	 * @return a optional of product
	 */
	@Query("select product from Product product where year(product.createTime) = :year and month(product.createTime) = :month and day(product.createTime) = :day and product.slug = :slug")
	Optional<Product> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day,
			@Param("slug") String slug);

	/**
	 * Find by product year and month and day and slug and status.
	 *
	 * @param year   product create year
	 * @param month  product create month
	 * @param day    product create day
	 * @param slug   product slug
	 * @param status product status
	 * @return a optional of product
	 */
	@Query("select product from Product product where year(product.createTime) = :year and month(product.createTime) = :month and day(product.createTime) = :day and product.slug = :slug and product.status = :status")
	Optional<Product> findBy(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day,
			@Param("slug") String slug, @Param("status") ProductStatus status);
}
