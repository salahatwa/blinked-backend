package com.blinked.apis.requests;

import org.thymeleaf.context.Context;

public interface Email {
    String getDestination();
    String getSubject();
    String getTemplate();
    Context getContext();
}
