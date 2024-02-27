package com.blinked.entities.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base post detail output dto.
 *
 * @author ssatwa
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class BaseProductDetailDTO extends BaseProductSimpleDTO {

	private String template;


	private Long rateCount;
}
