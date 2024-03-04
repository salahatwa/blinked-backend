package com.blinked.apis.responses;

import com.api.common.model.ApiRs;

import lombok.Data;

@Data
public class GeoIpVO extends ApiRs{

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
