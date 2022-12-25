package com.blinked.modules.user.controllers;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.modules.user.dtos.RecoveryConfirm;
import com.blinked.modules.user.dtos.RecoveryRequest;
import com.blinked.modules.user.dtos.RecoveryUpdate;
import com.blinked.modules.user.services.RecoveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Password recovery")
@RequestMapping("/api/recoveries")
public class RecoveriesController {

	private final RecoveryService recoveryService;

	@Autowired
	public RecoveriesController(RecoveryService recoveryService) {
		this.recoveryService = recoveryService;
	}

	@PostMapping
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Starts recovery password process", description = "Sends a email to user with recovery code")
	public void recovery(@RequestBody RecoveryRequest request) {
		recoveryService.recovery(request.getEmail());
	}

	@PostMapping("/confirm")
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Confirm recovery code")
	public void confirm(@RequestBody RecoveryConfirm confirm) {
		recoveryService.confirm(confirm.getEmail(), confirm.getCode());
	}

	@PostMapping("/update")
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Update user password")
	public void update(@RequestBody RecoveryUpdate update) {
		recoveryService.update(update.getEmail(), update.getCode(), update.getPassword());
	}
}
