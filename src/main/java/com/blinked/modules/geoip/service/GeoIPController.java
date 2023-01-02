package com.blinked.modules.geoip.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maxmind.geoip2.exception.GeoIp2Exception;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "GEO IP Information")
@RequestMapping("/api/geo-info")
public class GeoIPController {

	@Autowired
	private GeoIPLocationService geoIPLocationService;

	@GetMapping("/geoIP")
	@Operation(summary = "Get My Geo IP")
	public GeoIP getMyLocation(HttpServletRequest request) throws IOException, GeoIp2Exception {
		return geoIPLocationService.getIpLocation(request);
	}

	@GetMapping("/geoIP/{ipAddress}")
	@Operation(summary = "Get Geo info By IP")
	public GeoIP getLocationByIp(@PathVariable String ipAddress, HttpServletRequest request)
			throws IOException, GeoIp2Exception {
		return geoIPLocationService.getIpLocation(ipAddress, request);
	}

}
