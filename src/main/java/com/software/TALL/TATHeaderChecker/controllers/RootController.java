package com.software.TALL.TATHeaderChecker.controllers;

import com.software.TALL.TATHeaderChecker.utils.AuthUtils;
import com.software.TALL.TATHeaderChecker.utils.TallResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

// this file is for testing

@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Path("/courseroot")
public class RootController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PermitAll
    @GET
    @Path("/version")
    public Response getVersion() {
        return Response.ok("v2.0.0").build();
    }

    @GET
    @Path("/userinfo")
    public Response getUserInfo(@Context HttpServletRequest req,
                                @Context SecurityContext sec) {

        String response = AuthUtils.getAuthenticatedUserId(req);
        return TallResponse.ok(response);
    }


    @PermitAll
    @GET
    @Path("/test/1")
    public Response getTest1() {
        return Response.ok("v2.0.0, test 1").build();
    }

    @PermitAll
    @GET
    @Path("/test/2")
    public Response getTest2() {
        return Response.ok("v2.0.0, test 2").build();
    }

    @PermitAll
    @GET
    @Path("/test/3")
    public Response getTest3() {
        return Response.ok("v2.0.0, test 3").build();
    }

    @PermitAll
    @GET
    @Path("/test/4")
    public Response getTest4() {
        return Response.ok("v2.0.0, test 4").build();
    }


}

