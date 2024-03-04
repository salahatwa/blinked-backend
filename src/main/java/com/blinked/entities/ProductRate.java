package com.blinked.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * ProductRating entity.
 *
 * @author ssatwa
 */
@Entity(name = "ProductRate")
@DiscriminatorValue("0")
public class ProductRate extends BaseRate {

}
