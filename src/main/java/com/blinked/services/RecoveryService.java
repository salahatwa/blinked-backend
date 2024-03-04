package com.blinked.services;

import com.api.common.repo.CrudService;
import com.blinked.entities.Recovery;

public interface RecoveryService extends CrudService<Recovery, Long> {
	public void recovery(String email);

	public void confirm(String email, String code);

	public void update(String email, String code, String password);

}
