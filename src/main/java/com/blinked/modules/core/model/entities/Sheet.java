package com.blinked.modules.core.model.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Page entity.
 *
 * @author ssatwa
 * @date 3/22/19
 */
@Entity(name = "Sheet")
@DiscriminatorValue("1")
public class Sheet extends BasePost {

}
