package com.blinked.apis;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blinked.apis.responses.GeoIpVO;
import com.blinked.services.GeoIPLocationService;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Geo IP Information")
@RequestMapping("/api/geo")
public class GeoIPController {

	@Autowired
	private GeoIPLocationService geoIPLocationService;

	@GetMapping("/ip/{ipAddress}")
	@Operation(summary = "Get My Geo IP")
	public GeoIpVO getMyLocation(HttpServletRequest request, @PathVariable String ipAddress)
			throws IOException, GeoIp2Exception {

		if (StringUtils.isNotBlank(ipAddress))
			return geoIPLocationService.getIpLocation(ipAddress, request);

		return geoIPLocationService.getIpLocation(request);
	}

}
