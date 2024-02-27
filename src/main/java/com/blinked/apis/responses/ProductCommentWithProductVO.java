package com.blinked.apis.responses;

import com.blinked.entities.dto.BaseProductMinimalDTO;
import com.blinked.entities.dto.BaseRateDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PostComment list with post vo.
 *
 * @author ssatwa
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class ProductCommentWithProductVO extends BaseRateDTO {

	private BaseProductMinimalDTO post;
}
