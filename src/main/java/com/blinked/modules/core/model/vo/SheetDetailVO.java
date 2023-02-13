package com.blinked.modules.core.model.vo;

import java.util.List;
import java.util.Set;

import com.blinked.modules.core.model.dto.BaseMetaDTO;
import com.blinked.modules.core.model.dto.post.BasePostDetailDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sheet detail VO.
 *
 * @author ssatwa
 * @date 2019-12-10
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetDetailVO extends BasePostDetailDTO {

	private Set<Long> metaIds;

	private List<BaseMetaDTO> metas;
}
