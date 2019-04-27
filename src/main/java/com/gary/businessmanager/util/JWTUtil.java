package com.gary.businessmanager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JWTUtil {

    public static void main(String[] args) {
        String token = sign("gary", "123456");
        System.out.println(verify(token));
    }

    private static final long TOKEN_EXPIRE_TIME = 30;
    private static final String TOKEN_SECRET_SALT = "1686cd0bfabf4403977e2403a8581426";

    public static String sign(String username, String userId) {
        try {
            // Token过期时间
            LocalDateTime expireTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_TIME);
            // 私钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET_SALT);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            return JWT.create()
                    .withHeader(header)
                    .withClaim("username", username)
                    .withClaim("userId", userId)
                    .withExpiresAt(toDateTime(expireTime))
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET_SALT);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            log.info("JWT TOKEN =======> Header:{}, Payload:{}, Signature:{} ", jwt.getHeader(), jwt.getPayload(), jwt.getSignature());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date toDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }
}
