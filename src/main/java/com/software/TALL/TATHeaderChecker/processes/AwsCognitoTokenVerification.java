package com.software.TALL.TATHeaderChecker.processes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AwsCognitoTokenVerification {
    private static String awsCognitoRegion = "us-west-2";
    private static String awsUserPoolId = "us-west-2_f4HZvDzpj";
    private static AwsCognitoRSAKeyProvider keyProvider = new AwsCognitoRSAKeyProvider(awsCognitoRegion, awsUserPoolId);
    private static Algorithm algorithm = Algorithm.RSA256(keyProvider);
    private static JWTVerifier jwtVerifier = JWT.require(algorithm).build();

    public static DecodedJWT verify(String token) {
        return jwtVerifier.verify(token);
    }
}