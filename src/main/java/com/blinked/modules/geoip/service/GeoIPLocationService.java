package com.blinked.modules.geoip.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.maxmind.geoip2.exception.GeoIp2Exception;

public interface GeoIPLocationService {
    GeoIP getIpLocation(String ip, HttpServletRequest request) throws IOException, GeoIp2Exception;
    
    GeoIP getIpLocation(HttpServletRequest request) throws IOException, GeoIp2Exception;
}
