package com.blinked.modules.profile.sitemap;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class SitemapEntry {
	/**
	 * <p>
	 * Parent tag for each URL entry. The remaining tags are children of this tag.
	 * </p>
	 * required.
	 */
	@NonNull
	private String loc;

	private String lastmod;

	private ChangeFreqEnum changefreq;

	private Double priority;
}
