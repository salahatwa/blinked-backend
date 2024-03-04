package com.blinked.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import com.api.common.repo.CrudService;
import com.blinked.apis.responses.CategoryTreeVO;
import com.blinked.entities.Category;
import com.blinked.entities.dto.CategoryDTO;

/**
 * Category service.
 *
 * @author ssatwa
 * @date 2019-03-14
 */
@Transactional(readOnly = true)
public interface CategoryService extends CrudService<Category, String> {

    /**
     * Lists as category tree.
     *
     * @param sort sort info must not be null
     * @return a category tree
     */
    @NonNull
    List<CategoryTreeVO> listAsTree(@NonNull Sort sort);

    /**
     * Get category by slug
     *
     * @param slug slug
     * @return Category
     */
    @NonNull
    Category getBySlug(@NonNull String slug);

    /**
     * Get category by slug
     *
     * @param slug slug
     * @return Category
     */
    @NonNull
    Category getBySlugOfNonNull(String slug);

    /**
     * Get Category by name.
     *
     * @param name name
     * @return Category
     */
    @Nullable
    Category getByName(@NonNull String name);

    /**
     * Removes category and post categories.
     *
     * @param categoryId category id must not be null
     */
    @Transactional
    void removeCategoryAndPostCategoryBy(String categoryId);

    /**
     * List categories by parent id.
     *
     * @param id parent id.
     * @return list of category.
     */
    List<Category> listByParentId(@NonNull String id);

    /**
     * Converts to category dto.
     *
     * @param category category must not be null
     * @return category dto
     */
    @NonNull
    CategoryDTO convertTo(@NonNull Category category);

    /**
     * Converts to category dto list.
     *
     * @param categories category list
     * @return a list of category dto
     */
    @NonNull
    List<CategoryDTO> convertTo(@Nullable List<Category> categories);
}
