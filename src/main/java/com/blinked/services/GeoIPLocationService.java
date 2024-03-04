package com.blinked.services;

import java.io.IOException;

import com.blinked.apis.responses.GeoIpVO;
import com.maxmind.geoip2.exception.GeoIp2Exception;

import jakarta.servlet.http.HttpServletRequest;

public interface GeoIPLocationService {
    GeoIpVO getIpLocation(String ip, HttpServletRequest request) throws IOException, GeoIp2Exception;
    
    GeoIpVO getIpLocation(HttpServletRequest request) throws IOException, GeoIp2Exception;
}
