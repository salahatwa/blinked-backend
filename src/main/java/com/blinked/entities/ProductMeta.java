package com.blinked.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ssatwa
 * @date 2024-08-04
 */
@Entity(name = "ProductMeta")
@DiscriminatorValue("0")
@EqualsAndHashCode(callSuper = true)
public class ProductMeta extends BaseMeta {
}
