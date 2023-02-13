package com.blinked.modules.core.model.entities;

import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * SheetMeta entity.
 *
 * @author ssatwa
 * @author ikaisec
 * @date 2019-08-04
 */
@Entity(name = "SheetMeta")
@DiscriminatorValue("1")
@EqualsAndHashCode(callSuper = true)
public class SheetMeta extends BaseMeta {
}
