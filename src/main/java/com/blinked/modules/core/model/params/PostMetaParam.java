package com.blinked.modules.core.model.params;

import com.blinked.modules.core.model.entities.PostMeta;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Post meta param.
 *
 * @author ssatwa
 * @date 2019-08-04
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PostMetaParam extends BaseMetaParam<PostMeta> {
}
