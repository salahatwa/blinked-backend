package com.blinked.modules.core.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.blinked.modules.core.model.dto.base.OutputConverter;

/**
 * Statistic with user info DTO.
 *
 * @author ssatwa
 * @date 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StatisticWithUserDTO extends StatisticDTO implements OutputConverter<StatisticWithUserDTO, StatisticDTO> {

    private UserDTO user;
}
