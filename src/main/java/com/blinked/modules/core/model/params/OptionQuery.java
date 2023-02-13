package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.enums.OptionType;

import lombok.Data;

/**
 * Option query params.
 *
 * @author ssatwa
 * @date 2019-12-02
 */
@Data
public class OptionQuery {

	private String keyword;

	private OptionType type;
}
