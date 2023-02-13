package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.entities.SheetMeta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Sheet meta param.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SheetMetaParam extends BaseMetaParam<SheetMeta> {
}
