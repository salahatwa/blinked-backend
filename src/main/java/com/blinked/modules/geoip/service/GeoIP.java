package com.blinked.modules.geoip.service;

import lombok.Data;

@Data
public class GeoIP {

	private String ipAddress;
	private String device;
	private String city;
	private String country;
	private String postal;
	private String iso;
	private Boolean isInEuroupeUnion;
	private String fullLocation;
	private Double latitude;
	private Double longitude;

}
