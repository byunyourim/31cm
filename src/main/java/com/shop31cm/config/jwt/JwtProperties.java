package com.shop31cm.config.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String issuer;
    private String secretKey;
    private Long expired;

    public Key getSecretKey() {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secretKey);

        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
}
