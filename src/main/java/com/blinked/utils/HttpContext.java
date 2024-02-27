package com.blinked.utils;

import com.blinked.config.secuirty.PublicRoutes;

public class HttpContext {
	private static final PublicRoutes publicRoutes = new PublicRoutes();

	public static PublicRoutes publicRoutes() {
		return HttpContext.publicRoutes;
	}
}
