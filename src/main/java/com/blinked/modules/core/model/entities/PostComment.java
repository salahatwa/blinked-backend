package com.blinked.modules.core.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * PostComment entity.
 *
 * @author ssatwa
 */
@Entity(name = "PostComment")
@DiscriminatorValue("0")
public class PostComment extends BaseComment {

}
