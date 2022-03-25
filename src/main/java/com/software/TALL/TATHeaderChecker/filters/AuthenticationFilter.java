package com.software.TALL.TATHeaderChecker.filters;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.ClaimsHolder;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.web.bind.annotation.RequestMethod;

import com.software.TALL.TATHeaderChecker.auth.AwsCognitoTokenVerification;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHORIZATION_SCHEME_BASIC = "Basic";
    private static final String AUTHORIZATION_SCHEME_BEARER = "Bearer";

    /**
     *
     * @param requestContext
     * @throws IOException
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod();

        // We need to exempt OPTIONS for CORS preflight requests.  Not sure how to do that other than globally here.
        if (requestContext.getRequest().getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }

        if (requestContext.getUriInfo().getPath().equals("openapi.json")) {
            return;
        }

        // Access allowed for all
        if (! method.isAnnotationPresent(PermitAll.class)) {
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Access blocked for all users.").build());
                return;
            }

            // Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            // Get the Authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            // If the Authorization header is empty, return UNAUTHORIZED
            if (authorization == null || authorization.isEmpty()) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource.").build());
                return;
            }

            HashSet<String> roles = new HashSet<>();

            // If a Bearer token is present, set roles from it.
            if (authorization.get(0).startsWith(AUTHORIZATION_SCHEME_BEARER)) {
                final String bearerToken = authorization.get(0).replaceFirst(AUTHORIZATION_SCHEME_BEARER + " ", "");

                DecodedJWT token = null;
                try {
                    token = AwsCognitoTokenVerification.verify(bearerToken);
                }
                catch (TokenExpiredException ex) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Authentication token expired.").build());
                    return;
                }
                catch (Exception ex) {
                    requestContext.abortWith(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unknown error while attempting to validate authentication token.").build());
                    return;
                }

                Map<String, Claim> claims = token.getClaims();

                roles = getRolesFromToken(token);
                String userId = getUserIdFromToken(token);
                requestContext.setProperty("embark_user_id", userId);

                // NOTE: We don't have access to these fields from the Auth token, use the ID token in Embark instead.
//                requestContext = setCmisId(requestContext, token);
//                requestContext = setMissionaryId(requestContext, token);
//                requestContext = setName(requestContext, token);
//                requestContext = setPreferredLanguage(requestContext, token);
//                requestContext = setEmailAddress(requestContext, token);
//                requestContext = setLdsAccountId(requestContext, token);

//                // Non-empty CMISID infers membership
//                String cmisId = getCmisIdFromToken(token);
//                if (StringUtils.isEmpty(cmisId)) {
//                    requestContext.setProperty("isMember", false);
//                    requestContext.setProperty("cmis_id", "");
//                }
//                else {
//                    requestContext.setProperty("isMember", true);
//                    requestContext.setProperty("cmis_id", cmisId);
//                }
            }
            // If Basic auth is used, set roles from it.
            else if (authorization.get(0).startsWith(AUTHORIZATION_SCHEME_BASIC)) {
                final String encodedUserPass = authorization.get(0).replaceFirst(AUTHORIZATION_SCHEME_BASIC + " ", "");
                String userPass = null;
                try {
                    byte[] decodedUserPass = Base64.getDecoder().decode(encodedUserPass);
                    userPass = new String(decodedUserPass, "UTF-8");
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                roles = getRolesFromUserpass(userPass);
            }

            // Verify the roles
            if (method.isAnnotationPresent(RolesAllowed.class)) {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

                if (! isUserAllowed(roles, rolesSet)) {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource.").build());
                    return;
                }
            }

        }
    }


    ContainerRequestContext setCmisId(ContainerRequestContext requestContext, DecodedJWT token) {
        // Non-empty CMISID infers membership
        String cmisId = getClaimAsStringFromToken(token, CLAIM_CMIS_ID);
        if (StringUtils.isEmpty(cmisId)) {
            requestContext.setProperty("isMember", false);
            requestContext.setProperty("cmis_id", "");
        }
        else {
            requestContext.setProperty("isMember", true);
            requestContext.setProperty("cmis_id", cmisId);
        }

        return requestContext;
    }


    ContainerRequestContext setMissionaryId(ContainerRequestContext requestContext, DecodedJWT token) {
        String missionaryId = getClaimAsStringFromToken(token, CLAIM_LDS_MISSIONARY_ID);
        if (StringUtils.isEmpty(missionaryId)) {
            requestContext.setProperty("missionary_id", "");
        }
        else {
            requestContext.setProperty("missionary_id", missionaryId);
        }

        return requestContext;
    }


    ContainerRequestContext setName(ContainerRequestContext requestContext, DecodedJWT token) {
        String givenName = getClaimAsStringFromToken(token, CLAIM_GIVEN_NAME);
        String familyName = getClaimAsStringFromToken(token, CLAIM_FAMILY_NAME);
        String initials = getClaimAsStringFromToken(token, CLAIM_INITIALS);

        if (StringUtils.isEmpty(givenName)) {
            requestContext.setProperty("given_name", "");
        }
        else {
            requestContext.setProperty("given_name", givenName);
        }

        if (StringUtils.isEmpty(familyName)) {
            requestContext.setProperty("family_name", "");
        }
        else {
            requestContext.setProperty("family_name", familyName);
        }

        if (StringUtils.isEmpty(initials)) {
            requestContext.setProperty("initials", "");
        }
        else {
            requestContext.setProperty("initials", initials);
        }

        return requestContext;
    }


    ContainerRequestContext setPreferredLanguage(ContainerRequestContext requestContext, DecodedJWT token) {
        String preferredLanguage = getClaimAsStringFromToken(token, CLAIM_PREFERRED_LANGUAGE);
        if (StringUtils.isEmpty(preferredLanguage)) {
            requestContext.setProperty("preferred_language", "");
        }
        else {
            requestContext.setProperty("preferred_language", preferredLanguage);
        }

        return requestContext;
    }


    ContainerRequestContext setEmailAddress(ContainerRequestContext requestContext, DecodedJWT token) {
        String email = getClaimAsStringFromToken(token, CLAIM_EMAIL);
        if (StringUtils.isEmpty(email)) {
            requestContext.setProperty("email", "");
        }
        else {
            requestContext.setProperty("email", email);
        }

        return requestContext;
    }


    ContainerRequestContext setLdsAccountId(ContainerRequestContext requestContext, DecodedJWT token) {
        String ldsAccountId = getClaimAsStringFromToken(token, CLAIM_LDSACCOUNTID);
        if (StringUtils.isEmpty(ldsAccountId)) {
            requestContext.setProperty("ldsAccountId", "");
        }
        else {
            requestContext.setProperty("ldsAccountId", ldsAccountId);
        }

        return requestContext;
    }


    /**
     *
     * @param roles
     * @param rolesSet
     * @return
     */
    private boolean isUserAllowed(HashSet<String> roles, Set<String> rolesSet) {
        // If the user has the TALL_SUPER, they have all access.
        if (roles.contains(TallRoles.TALL_SUPERUSER)) {
            return true;
        }
        // If the user has the TALL_ADMIN role, return false if the required role includes TALL_SUPER
        // MTC_TALL_ADMIN role is equivalenet to the TALL_ADMIN role, it was added for legacy compatibility
        // with the old MTC auth server.
        else if (roles.contains(TallRoles.TALL_ADMIN)) {
            return (!rolesSet.contains(TallRoles.TALL_SUPERUSER));
        }
        else if (roles.contains(TallRoles.MTC_TALL_ADMIN)) {
            return (!rolesSet.contains(TallRoles.TALL_SUPERUSER));
        }
        // Otherwise, iterate through the roles and return false if there is one in the roleset not had by the user.
        else {
            for (String r : rolesSet) {
                if (! roles.contains(r))
                    return false;
            }
            return true;
        }
    }


    /**
     *
     * @param token
     * @return
     */
    private HashSet<String> getRolesFromToken(DecodedJWT token) {
        Claim c = token.getClaim("cognito:groups");
        HashSet<String> roles = new HashSet<>(c.asList(String.class));
        return roles;
    }


    private String getUserIdFromToken(DecodedJWT token) {
        Claim c = token.getClaim("username");
        String[] parts = c.asString().split("_");
        if (parts.length > 1) {
            return parts[1];
        }
        else {
            return parts[0];
        }
    }

    private String getClaimAsStringFromToken(DecodedJWT token, String claim) {
        Claim c = token.getClaim(claim);
        if (StringUtils.isEmpty(c.asString())) {
            return "";
        }
        else {
            return c.asString().trim();
        }
    }

//    private String getCmisIdFromToken(DecodedJWT token) {
//        Claim c = token.getClaim("custom:ldscmisId");
//        if (StringUtils.isEmpty(c.asString())) {
//            return "";
//        }
//        else {
//            return c.asString().trim();
//        }
//    }

//    private String getMissionaryIdFromToken(DecodedJWT token) {
//        Claim c = token.getClaim("custom:ldsmissionaryid");
//        if (StringUtils.isEmpty(c.asString())) {
//            return "";
//        }
//        else {
//            return c.asString().trim();
//        }
//    }

    private final String CLAIM_COUNTRY = "custom:country";
    private final String CLAIM_LDSACCOUNTID = "custom:ldsaccountid";
    private final String CLAIM_INITIALS = "custom:initials";
    private final String CLAIM_CMIS_ID = "custom:ldscmisId";
    private final String CLAIM_GIVEN_NAME = "given_name";
    private final String CLAIM_LDS_MISSIONARY_ID = "custom:ldsmissionaryid";
    private final String CLAIM_PREFERRED_LANGUAGE = "custom:preferredlanguage";
    private final String CLAIM_FAMILY_NAME = "family_name";
    private final String CLAIM_EMAIL = "email";

    private final String TALL_SUPER_BASIC = "super:superman";
    private final String TALL_ADMIN_BASIC = "admin:adman";
    private final String CONTENT_ADMIN_BASIC = "contentadmin:cadman";
    private final String CONTENT_CREATOR_BASIC = "creator:createman";
    private final String CHURCH_ACCOUNT_BASIC = "bws01:welcome";
    private final String MTC_TALL_ADMIN = "mtctall:mtctallman";


    private HashSet<String> getRolesFromUserpass(String userPass) {
        //final StringTokenizer tokenizer = new StringTokenizer(userPass, ":");
        //final String username = tokenizer.nextToken();
        //final String password = tokenizer.nextToken();

        HashSet<String> roles = new HashSet<>();
        if (TALL_SUPER_BASIC.equals(userPass))
            roles.add(TallRoles.TALL_SUPERUSER);
        if (TALL_ADMIN_BASIC.equals(userPass))
            roles.add(TallRoles.TALL_ADMIN);
        if (CONTENT_ADMIN_BASIC.equals(userPass))
            roles.add(TallRoles.CONTENT_ADMIN);
        if (CONTENT_CREATOR_BASIC.equals(userPass))
            roles.add(TallRoles.CONTENT_CREATOR);
        if (CHURCH_ACCOUNT_BASIC.equals(userPass))
            roles.add(TallRoles.CHURCH_ACCOUNT_USER);
        if (MTC_TALL_ADMIN.equalsIgnoreCase(userPass))
            roles.add(TallRoles.MTC_TALL_ADMIN);
        return roles;
    }


}
