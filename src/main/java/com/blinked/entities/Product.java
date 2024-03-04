package com.blinked.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Product entity.
 *
 * @author ssatwa
 */
@Entity(name = "Product")
@DiscriminatorValue(value = "0")
public class Product extends BaseProduct {

}
