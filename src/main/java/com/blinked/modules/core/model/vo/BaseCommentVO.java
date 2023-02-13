package com.blinked.modules.core.model.vo;

import java.util.List;

import com.blinked.modules.core.model.dto.BaseCommentDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base comment vo.
 *
 * @author ssatwa
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseCommentVO extends BaseCommentDTO {

	List<BaseCommentVO> children;
}
