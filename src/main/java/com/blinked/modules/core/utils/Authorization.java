package com.blinked.modules.core.utils;

import static com.blinked.modules.core.utils.SecurityEnvironments.ACCEPTABLE_TOKEN_TYPE;
import static com.blinked.modules.core.utils.SecurityEnvironments.AUTHORIZATION_HEADER;
import static com.blinked.modules.core.utils.SecurityEnvironments.BEARER_WORD_LENGTH;
import static com.blinked.modules.core.utils.SecurityEnvironments.SECURITY_TYPE;
import static java.util.Objects.isNull;

import javax.servlet.http.HttpServletRequest;

public class Authorization {
    private Authorization() { }

    public static String extract(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        if (tokenIsNull(token)) {
            return null;
        }

        if (token.substring(BEARER_WORD_LENGTH).equals(SECURITY_TYPE)) {
            return null;
        }

        System.out.println("22Token:"+token.substring(BEARER_WORD_LENGTH));
        return token.substring(BEARER_WORD_LENGTH);
    }

    public static boolean tokenIsNull(String token) {
        return isNull(token) || !token.startsWith(ACCEPTABLE_TOKEN_TYPE);
    }
}
