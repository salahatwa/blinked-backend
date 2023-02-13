package com.blinked.modules.core.services;

import com.blinked.modules.core.model.dto.StatisticDTO;
import com.blinked.modules.core.model.dto.StatisticWithUserDTO;

/**
 * Statistic service interface.
 *
 * @author ssatwa
 * @date 2019-12-16
 */
public interface StatisticService {

	/**
	 * Get blog statistic.
	 *
	 * @return statistic dto.
	 */
	StatisticDTO getStatistic();

	/**
	 * Get blog statistic with user info.
	 *
	 * @return statistic with user info dto.
	 */
	StatisticWithUserDTO getStatisticWithUser();
}
