package com.software.TALL.TATHeaderChecker.utils;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.SecurityContext;

public class AuthUtils {

    public static String getAuthenticatedUserId(HttpServletRequest req) {
        String userId = (String) req.getAttribute("embark_user_id");

        if (userId == null) {
            return "";
        }
        else {
            return userId;
        }
    }
}
