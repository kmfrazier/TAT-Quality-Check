package com.software.TALL.TATHeaderChecker.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.auth.oauth2.ServiceAccountCredentials;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtGenerator {

    /**
     * https://cloud.google.com/endpoints/docs/openapi/service-account-authentication#making_an_authenticated_request
     *
     * Generates a signed JSON Web Token using a Google API Service Account
     * utilizes com.auth0.jwt.
     *
     * The function creates a JWT, signs it by using the private key file,
     * and returns the signed JWT.
     */
    public static String generateJwt(final String saKeyfile, final String saEmail,
                                     final String audience, final int expiryLength)
            throws FileNotFoundException, IOException {

        Date now = new Date();
        Date expTime = new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiryLength));

        // Build the JWT payload
        JWTCreator.Builder token = JWT.create()
                .withIssuedAt(now)
                // Expires after 'expiryLength' seconds
                .withExpiresAt(expTime)
                // Must match 'issuer' in the security configuration in your
                // swagger spec (e.g. service account email)
                .withIssuer(saEmail)
                // Must be either your Endpoints service name, or match the value
                // specified as the 'x-google-audience' in the OpenAPI document
                .withAudience(audience)
                // Subject and email should match the service account's email
                .withSubject(saEmail)
                .withClaim("email", saEmail);

        // Sign the JWT with a service account
        FileInputStream stream = new FileInputStream(saKeyfile);
        ServiceAccountCredentials cred = ServiceAccountCredentials.fromStream(stream);
        RSAPrivateKey key = (RSAPrivateKey) cred.getPrivateKey();
        Algorithm algorithm = Algorithm.RSA256(null, key);
        return token.sign(algorithm);
    }


    /**
     * https://cloud.google.com/endpoints/docs/openapi/service-account-authentication#making_an_authenticated_request
     *
     * Makes an authorized request to the endpoint.
     *
     * add the following function to send the signed JWT in the
     * Authorization: Bearer header in the request to the API
     */
    public static String makeJwtRequest(final String signedJwt, final URL url)
            throws IOException, ProtocolException {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + signedJwt);

        InputStreamReader reader = new InputStreamReader(con.getInputStream());
        BufferedReader buffReader = new BufferedReader(reader);

        String line;
        StringBuilder result = new StringBuilder();
        while ((line = buffReader.readLine()) != null) {
            result.append(line);
        }
        buffReader.close();
        return result.toString();
    }

}
