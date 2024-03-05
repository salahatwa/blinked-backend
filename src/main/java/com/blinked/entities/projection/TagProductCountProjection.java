package com.blinked.entities.projection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssatwa
 * @date 3/26/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagProductCountProjection {

	/**
	 */
	private Long productCount;

	/**
	 * Tag id
	 */
	private String tagId;

}
