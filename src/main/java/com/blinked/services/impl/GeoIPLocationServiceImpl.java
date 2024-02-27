package com.blinked.services.impl;

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.blinked.apis.responses.GeoIpVO;
import com.blinked.services.GeoIPLocationService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import ua_parser.Client;
import ua_parser.Parser;

@Service
public class GeoIPLocationServiceImpl implements GeoIPLocationService {

	private final DatabaseReader databaseReader;
	private static final String UNKNOWN = "UNKNOWN";

	public GeoIPLocationServiceImpl(DatabaseReader databaseReader) {
		this.databaseReader = databaseReader;
	}

	/**
	 * Get device info by user agent
	 *
	 * @param userAgent user agent http device
	 * @return Device info details
	 * @throws IOException if not found
	 */
	private String getDeviceDetails(String userAgent) throws IOException {
		String deviceDetails = UNKNOWN;

		Parser parser = new Parser();

		Client client = parser.parse(userAgent);
		if (nonNull(client)) {
			deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor
					+ " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
		}

		return deviceDetails;
	}

	/**
	 * get user position by ip address
	 *
	 * @param ip String ip address
	 * @return UserPositionDTO model
	 * @throws IOException     if local database city not exist
	 * @throws GeoIp2Exception if cannot get info by ip address
	 */
	@Override
	public GeoIpVO getIpLocation(String ip, HttpServletRequest request) throws IOException, GeoIp2Exception {

		GeoIpVO position = new GeoIpVO();
		String location;

		InetAddress ipAddress = InetAddress.getByName(ip);

		CityResponse cityResponse = databaseReader.city(ipAddress);
		if (nonNull(cityResponse) && nonNull(cityResponse.getCity())) {

			String continent = (cityResponse.getContinent() != null) ? cityResponse.getContinent().getName() : "";
			String country = (cityResponse.getCountry() != null) ? cityResponse.getCountry().getName() : "";

			location = String.format("%s, %s, %s", continent, country, cityResponse.getCity().getName());
			position.setCity(cityResponse.getCity().getName());
			position.setCountry(country);
			position.setPostal(cityResponse.getPostal().getCode());
			position.setIso(cityResponse.getRegisteredCountry().getIsoCode());
			position.setIsInEuroupeUnion(cityResponse.getRegisteredCountry().isInEuropeanUnion());
			position.setFullLocation(location);
			position.setLatitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLatitude() : 0);
			position.setLongitude((cityResponse.getLocation() != null) ? cityResponse.getLocation().getLongitude() : 0);
			position.setDevice(getDeviceDetails(request.getHeader("user-agent")));

			position.setIpAddress(ip);

		}
		return position;
	}

	@Override
	public GeoIpVO getIpLocation(HttpServletRequest request) throws IOException, GeoIp2Exception {
		return this.getIpLocation(getClientIpAddress(request), request);
	}

	// Nginx
	// proxy_set_header X-Real-IP $remote_addr;
	private static final String[] IP_HEADER_CANDIDATES = { "X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP",
			"WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP",
			"HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	private static String getClientIpAddress(HttpServletRequest request) {
		for (String header : IP_HEADER_CANDIDATES) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

}