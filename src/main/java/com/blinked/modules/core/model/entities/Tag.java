package com.blinked.modules.core.model.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Tag entity
 *
 * @author ssatwa
 * @date 2019-03-12
 */
@Data
@Entity
@Table(name = "tags", indexes = {@Index(name = "tags_name", columnList = "name")})
@ToString
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.blinked.modules.core.model.entities.CustomIdGenerator")
    private Integer id;

    /**
     * Tag name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Tag slug name.
     */
    @Deprecated
    @Column(name = "slug_name")
    private String slugName;

    /**
     * Tag slug.
     */
    @Column(name = "slug", unique = true)
    private String slug;

    /**
     * Cover thumbnail of the tag.
     */
    @Column(name = "thumbnail", length = 1023)
    private String thumbnail;
}
