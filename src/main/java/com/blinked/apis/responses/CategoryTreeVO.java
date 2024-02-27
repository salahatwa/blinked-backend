package com.blinked.apis.responses;

import java.util.List;

import com.blinked.entities.dto.CategoryDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category vo.
 *
 * @author ssatwa
 * @date 3/21/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryTreeVO extends CategoryDTO {

    private List<CategoryTreeVO> children;
}
