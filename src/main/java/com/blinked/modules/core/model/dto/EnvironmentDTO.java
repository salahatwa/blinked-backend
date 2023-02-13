package com.blinked.modules.core.model.dto;

import com.blinked.modules.core.model.enums.Mode;

import lombok.Data;

/**
 * Theme controller.
 *
 * @author ssatwa
 * @date 2019/5/4
 */
@Data
public class EnvironmentDTO {

	private String database;

	private Long startTime;

	private String version;

	private Mode mode;
}
