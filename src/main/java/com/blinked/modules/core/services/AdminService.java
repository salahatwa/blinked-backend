package com.blinked.modules.core.services;

import org.springframework.lang.NonNull;

import com.blinked.modules.core.model.dto.EnvironmentDTO;
import com.blinked.modules.core.model.dto.LoginPreCheckDTO;
import com.blinked.modules.core.model.dto.StatisticDTO;
import com.blinked.modules.core.model.params.LoginParam;
import com.blinked.modules.core.model.params.ResetPasswordParam;
import com.blinked.modules.user.entities.User;

/**
 * Admin service interface.
 *
 * @author ssatwa
 * @date 2019-04-29
 */
public interface AdminService {

	/**
	 * Expired seconds.
	 */
	int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

	int REFRESH_TOKEN_EXPIRED_DAYS = 30;

	String LOG_PATH = "logs/spring.log";

	/**
	 * Authenticates username password.
	 *
	 * @param loginParam login param must not be null
	 * @return User
	 */
	@NonNull
	User authenticate(@NonNull LoginParam loginParam);

	/**
	 * Clears authentication.
	 */
	void clearToken();

	/**
	 * Send reset password code to administrator's email.
	 *
	 * @param param param must not be null
	 */
	void sendResetPasswordCode(@NonNull ResetPasswordParam param);

	/**
	 * Reset password by code.
	 *
	 * @param param param must not be null
	 */
	void resetPasswordByCode(@NonNull ResetPasswordParam param);

	/**
	 * Get system counts.
	 *
	 * @return count dto
	 */
	@NonNull
	@Deprecated
	StatisticDTO getCount();

	/**
	 * Get system environments
	 *
	 * @return environments
	 */
	@NonNull
	EnvironmentDTO getEnvironments();

	/**
	 * Updates halo admin assets.
	 */
	void updateAdminAssets();

	/**
	 * Get halo logs content.
	 *
	 * @param lines lines
	 * @return logs content.
	 */
	String getLogFiles(@NonNull Long lines);

	/**
	 * Get user login env
	 *
	 * @param username username must not be null
	 * @return LoginEnvDTO
	 */
	LoginPreCheckDTO getUserEnv(@NonNull String username);
}
