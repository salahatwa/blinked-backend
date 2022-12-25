package com.blinked.modules.core.utils;

import com.blinked.modules.core.secuirty.PublicRoutes;

public class HttpContext {
	private static final PublicRoutes publicRoutes = new PublicRoutes();

	public static PublicRoutes publicRoutes() {
		return HttpContext.publicRoutes;
	}
}
