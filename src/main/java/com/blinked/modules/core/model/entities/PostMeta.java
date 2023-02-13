package com.blinked.modules.core.model.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * PostMeta entity.
 *
 * @author ssatwa
 * @author ikaisec
 * @date 2019-08-04
 */
@Entity(name = "PostMeta")
@DiscriminatorValue("0")
@EqualsAndHashCode(callSuper = true)
public class PostMeta extends BaseMeta {
}
