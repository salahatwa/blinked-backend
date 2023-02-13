package test.modules.core.services.impl;
//package com.blinked.modules.core.services.impl;
//
//import org.springframework.boot.actuate.trace.http.HttpTrace;
//import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
//import org.springframework.stereotype.Service;
//import com.blinked.modules.core.services.TraceService;
//
//import java.util.List;
//
///**
// * TraceService implementation class.
// *
// * @author ssatwa
// * @date 2019-06-18
// */
//@Service
//public class TraceServiceImpl implements TraceService {
//
//    private final HttpTraceRepository httpTraceRepository;
//
//    public TraceServiceImpl(HttpTraceRepository httpTraceRepository) {
//        this.httpTraceRepository = httpTraceRepository;
//    }
//
//    @Override
//    public List<HttpTrace> listHttpTraces() {
//        return httpTraceRepository.findAll();
//    }
//}
