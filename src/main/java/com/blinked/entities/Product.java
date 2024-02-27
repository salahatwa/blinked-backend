package com.blinked.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Product entity.
 *
 * @author ssatwa
 */
@Entity(name = "Product")
@DiscriminatorValue(value = "0")
public class Product extends BaseProduct {

}
