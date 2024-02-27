package com.blinked.apis.requests;

import org.thymeleaf.context.Context;

public class RecoveryEmailParam implements Email {
	private final String destination;
	private final String subject;
	private final String name;
	private final String code;

	public RecoveryEmailParam(String destination, String subject, String name, String recoveryCode) {
		this.destination = destination;
		this.subject = subject;
		this.name = name;
		this.code = recoveryCode;
	}

	@Override
	public String getDestination() {
		return destination;
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public String getTemplate() {
		return "recovery-password";
	}

	@Override
	public Context getContext() {
		Context context = new Context();

		context.setVariable("name", this.name);
		context.setVariable("code", this.code);

		return context;
	}

}
