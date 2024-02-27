package com.blinked.apis.requests;

import com.blinked.entities.ProductRate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * PostComment param.
 *
 * @author ssatwa
 * @date 3/22/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ProductRateParam extends BaseRateParam<ProductRate> {

}
