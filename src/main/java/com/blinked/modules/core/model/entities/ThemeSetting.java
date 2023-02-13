package com.blinked.modules.core.model.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Theme setting entity.
 *
 * @author ssatwa
 * @date 4/8/19
 */
@Data
@Entity
@Table(name = "theme_settings", indexes = {
        @Index(name = "theme_settings_setting_key", columnList = "setting_key"),
        @Index(name = "theme_settings_theme_id", columnList = "theme_id")})
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ThemeSetting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.blinked.modules.core.model.entities.CustomIdGenerator")
    private Integer id;

    /**
     * Setting key.
     */
    @Column(name = "setting_key", nullable = false)
    private String key;

    /**
     * Setting value
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Type(type="text")
    @Column(name = "setting_value", nullable = false)
    private String value;

    /**
     * Theme id.
     */
    @Column(name = "theme_id", nullable = false)
    private String themeId;
}
