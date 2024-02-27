package com.blinked.entities.dto;

import java.util.Date;

import com.blinked.entities.Tag;
import com.blinked.repositories.base.OutputConverter;

import lombok.Data;

/**
 * Tag output dto.
 *
 * @author ssatwa
 * @author ssatwa
 * @date 2019-03-19
 */
@Data
public class TagDTO implements OutputConverter<TagDTO, Tag> {

    private Integer id;

    private String name;

    private String slug;

    private String thumbnail;

    private Date createTime;

    private String fullPath;
}
