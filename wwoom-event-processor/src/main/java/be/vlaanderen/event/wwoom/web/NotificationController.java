package be.vlaanderen.event.wwoom.web;

import be.vlaanderen.burgerprofiel.wwoom.security.client.oauth.geosecure.GeoSecure;
import be.vlaanderen.event.wwoom.applications.Application;
import be.vlaanderen.event.wwoom.applications.ApplicationRepository;
import be.vlaanderen.event.wwoom.model.NotificationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import lombok.CustomLog;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@CustomLog
@EnableWebMvc
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final GeoSecure geoSecure;
    private final ObjectMapper objectMapper;
    private final ApplicationRepository applicationRepository;

    @GetMapping("/")
    public String handleNotification(NotificationEvent notificationEvent) throws JsonProcessingException, NoSuchAlgorithmException, ParseException, JOSEException {
        log.info("Got notification event {}", notificationEvent);

        String token = geoSecure.getAccessToken("legallogging");
        log.info("Obtained access token {}", token);

        String applicationId = notificationEvent.getDetail().get("applicationId").toString();
        Application application = applicationRepository.getApplication(applicationId);

        String payload = objectMapper.writeValueAsString(notificationEvent.getDetail());
        String encryptedPayload = encrypt(payload, application.getPublicKey());

        log.info("Encrypted payload: " + encryptedPayload);
        return encryptedPayload;
    }

    private String encrypt(String payload, String publicKey) throws NoSuchAlgorithmException, ParseException, JOSEException {
        JWK parsedJWK = JWK.parse(publicKey);

        // TODO: can this be fixed?
        JWEAlgorithm alg = JWEAlgorithm.RSA_OAEP_256;
        EncryptionMethod enc = EncryptionMethod.A128CBC_HS256;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(enc.cekBitLength());
        SecretKey cek = keyGenerator.generateKey();

        JWEObject jwe = new JWEObject(
                new JWEHeader(alg, enc),
                new Payload(payload));
        // TODO: Can this be other than a RSA encrypter?
        jwe.encrypt(new RSAEncrypter(parsedJWK.toRSAKey().toRSAPublicKey(), cek));

        return jwe.serialize();
    }
}
