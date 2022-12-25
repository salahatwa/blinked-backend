package com.blinked.modules.user.dtos;

import org.thymeleaf.context.Context;

public interface Email {
    String getDestination();
    String getSubject();
    String getTemplate();
    Context getContext();
}
