package com.blinked.entities.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Category with PRODUCT count dto.
 *
 * @author ssatwa
 * @date 19-4-23
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryWithProductCountDTO extends CategoryDTO {

	private Long productCount;
}
