package com.blinked.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * ProductRating entity.
 *
 * @author ssatwa
 */
@Entity(name = "ProductRate")
@DiscriminatorValue("0")
public class ProductRate extends BaseRate {

}
