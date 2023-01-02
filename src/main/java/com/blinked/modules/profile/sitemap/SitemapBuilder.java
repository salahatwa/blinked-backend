package com.blinked.modules.profile.sitemap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 * Builder class to generate xml for sitemap.
 *
 * @author ssatwa
 */
public class SitemapBuilder {

	public String withXMLTemplate(String content) {
		return String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "            <urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\"\n"
				+ "                    xmlns:news=\"http://www.google.com/schemas/sitemap-news/0.9\"\n"
				+ "                    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
				+ "                    xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd\"\n"
				+ "                    xmlns:mobile=\"http://www.google.com/schemas/sitemap-mobile/1.0\"\n"
				+ "                    xmlns:image=\"http://www.google.com/schemas/sitemap-image/1.1\"\n"
				+ "                    xmlns:video=\"http://www.google.com/schemas/sitemap-video/1.1\">\n"
				+ "            %s\n" + "            </urlset>", content);
	}

	/**
	 * Normalize sitemap field keys to stay consistent with <xsd:sequence> order.
	 *
	 * @see <a href="https://www.w3schools.com/xml/el_sequence.asp">el_sequence</a>
	 */
	public LinkedHashMap<String, String> normalizeSitemapEntry(SitemapEntry sitemapEntry) {
		LinkedHashMap<String, String> sitemapEntryMap = new LinkedHashMap<>(4, 1);
		// Return keys in following order
		sitemapEntryMap.put("loc", sitemapEntry.getLoc());
		if (StringUtils.isNotBlank(sitemapEntry.getLastmod())) {
			sitemapEntryMap.put("lastmod", sitemapEntry.getLastmod());
		}
		ChangeFreqEnum changefreq = sitemapEntry.getChangefreq();
		if (changefreq != null) {
			sitemapEntryMap.put("changefreq", changefreq.name().toLowerCase());
		}
		if (sitemapEntry.getPriority() != null) {
			sitemapEntryMap.put("priority", sitemapEntry.getPriority().toString());
		}
		return sitemapEntryMap;
	}

	public String buildSitemapXml(List<SitemapEntry> sitemapEntries) {
		String content = sitemapEntries.stream().map(sitemapEntry -> {
			LinkedHashMap<String, String> entryMap = normalizeSitemapEntry(sitemapEntry);
			String urlFields = entryMap.entrySet().stream().map(entry -> {
				String key = entry.getKey();
				String value = entry.getValue();
				return String.format("<%s>%s</%s>", key, value, key);
			}).collect(Collectors.joining(""));
			return String.format("<url>%s</url>", urlFields);
		}).collect(Collectors.joining(""));
		return withXMLTemplate(content);
	}
}
