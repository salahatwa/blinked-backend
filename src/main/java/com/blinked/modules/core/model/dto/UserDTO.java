package com.blinked.modules.core.model.dto;

import java.util.Date;

import com.blinked.modules.core.model.dto.base.OutputConverter;
import com.blinked.modules.core.model.enums.MFAType;
import com.blinked.modules.user.entities.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * User output dto.
 *
 * @author ssatwa
 * @date 3/16/19
 */
@Data
@ToString
@EqualsAndHashCode
public class UserDTO implements OutputConverter<UserDTO, User> {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String description;

    private MFAType mfaType;

    private Date createTime;

    private Date updateTime;
}
