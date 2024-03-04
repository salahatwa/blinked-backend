package com.blinked.apis;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.apis.requests.RecoveryConfirmParam;
import com.blinked.apis.requests.RecoveryParam;
import com.blinked.apis.requests.RecoveryUpdateParam;
import com.blinked.services.RecoveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Public Recovery Password")
@RequestMapping("/api/recoveries")
public class RecoveriesController {

	@Autowired
	private RecoveryService recoveryService;

	@PostMapping
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Step1 - Starts recovery password process", description = "Sends a email to user with recovery code")
	public void recovery(@RequestBody RecoveryParam request) {
		recoveryService.recovery(request.getEmail());
	}

	@PostMapping("/confirm")
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Step2 - Confirm recovery code")
	public void confirm(@RequestBody RecoveryConfirmParam confirm) {
		recoveryService.confirm(confirm.getEmail(), confirm.getCode());
	}

	@PostMapping("/update")
	@ResponseStatus(NO_CONTENT)
	@Operation(summary = "Step3 - Update user password")
	public void update(@RequestBody RecoveryUpdateParam update) {
		recoveryService.update(update.getEmail(), update.getCode(), update.getPassword());
	}
}
