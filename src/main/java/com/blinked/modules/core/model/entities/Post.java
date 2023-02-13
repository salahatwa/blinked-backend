package com.blinked.modules.core.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Post entity.
 *
 * @author ssatwa
 */
@Entity(name = "Post")
@DiscriminatorValue(value = "0")
public class Post extends BasePost {

}
