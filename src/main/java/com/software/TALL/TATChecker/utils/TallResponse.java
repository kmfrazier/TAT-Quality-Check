package com.software.TALL.TATChecker.utils;

import javax.ws.rs.core.Response;

public class TallResponse {

    public static Response ok(Object o) {
        return Response.status(Response.Status.OK).entity(o).build();
    }


    public static Response accepted(Object o) {
        return Response.status(Response.Status.ACCEPTED).entity(o).build();
    }


    public static Response unauthorized(String message) {
        ResponseContainer response = new ResponseContainer();

        if (message != null) {
            response.put("message", message);
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity(response.getResponseBody()).build();
    }


    public static Response badRequest(String message, Object o) {
        ResponseContainer response = new ResponseContainer();

        if (message != null) {
            response.put("message", message);
        }
        if (o != null) {
            response.put("object", o);
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(response.getResponseBody()).build();
    }


    public static Response internalServerError(String message, Object o) {
        ResponseContainer response = new ResponseContainer();

        if (message != null) {
            response.put("message", message);
        }
        if (o != null) {
            response.put("object", o);
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getResponseBody()).build();
    }


    public static Response forbidden(String message) {
        ResponseContainer response = new ResponseContainer();

        if (message != null) {
            response.put("message", message);
        }

        return Response.status(Response.Status.FORBIDDEN).entity(response.getResponseBody()).build();
    }


    public static Response notModified(String message) {
        ResponseContainer response = new ResponseContainer();

        if (message != null) {
            response.put("message", message);
        }

        response.put("modified", false);

        return Response.status(Response.Status.NOT_MODIFIED).entity(response.getResponseBody()).build();
    }

}
