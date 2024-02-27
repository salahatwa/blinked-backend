package com.blinked.apis.requests;

import com.blinked.entities.ProductMeta;

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
public class ProductMetaParam extends BaseMetaParam<ProductMeta> {
}
