package com.blinked.constants;

import static com.blinked.constants.Type.LIGHT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class Toasts {

	private Toasts() {
	}

	private static final String TOAST_FIELD = "toasts";

	public static void add(Model model, String message, Type type) {

		model.addAttribute(TOAST_FIELD, Arrays.asList(Toast.of(message, type)));
	}

	public static void add(Model model, String message) {
		model.addAttribute(TOAST_FIELD, Arrays.asList(Toast.of(message, LIGHT)));
	}

	public static void add(Model model, List<Toast> toasts) {
		model.addAttribute(TOAST_FIELD, toasts);
	}

	public static void add(RedirectAttributes redirect, String message, Type type) {
		redirect.addFlashAttribute(TOAST_FIELD, Arrays.asList(Toast.of(message, type)));
	}

	public static void add(RedirectAttributes redirect, String message) {
		redirect.addFlashAttribute(TOAST_FIELD, Arrays.asList(Toast.of(message, LIGHT)));
	}

	public static void add(RedirectAttributes redirect, List<Toast> toasts) {
		redirect.addFlashAttribute(TOAST_FIELD, toasts);
	}

	public static void add(RedirectAttributes redirect, BindingResult result) {
		List<ObjectError> errors = result.getAllErrors();
		redirect.addFlashAttribute(TOAST_FIELD, toasts(errors));
	}

	public static void add(Model model, BindingResult result) {
		List<ObjectError> errors = result.getAllErrors();
		model.addAttribute(TOAST_FIELD, toasts(errors));
	}

	private static List<Toast> toasts(List<ObjectError> errors) {
		return errors.stream().map(Toast::of).collect(Collectors.toList());
	}
}
