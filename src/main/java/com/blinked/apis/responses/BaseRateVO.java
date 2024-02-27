package com.blinked.apis.responses;

import java.util.List;

import com.blinked.entities.dto.BaseRateDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base comment vo.
 *
 * @author ssatwa
 * @date 19-4-24
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseRateVO extends BaseRateDTO {

    List<BaseRateVO> children;
}
