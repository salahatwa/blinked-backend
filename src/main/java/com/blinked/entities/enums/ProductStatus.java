package com.blinked.entities.enums;

import com.blinked.utils.ValueEnum;

/**
 * Product status.
 *
 * @author ssatwa
 */
public enum ProductStatus implements ValueEnum<Integer> {

    /**
     * Published status.
     */
    PUBLISHED(0),

    /**
     * Draft status.
     */
    DRAFT(1),

    /**
     * Recycle status.
     */
    RECYCLE(2),

    /**
     * Intimate status
     */
    INTIMATE(3);

    private final int value;

    ProductStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
