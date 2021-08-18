package be.vlaanderen.event.wwoom;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.text.ParseException;
import java.util.UUID;

public class Encryption {

    public String[] generateKeys() throws JOSEException {
        // Generate 2048-bit RSA key pair in JWK format, attach some metadata
        RSAKey jwk = new RSAKeyGenerator(2048)
                .keyUse(KeyUse.ENCRYPTION) // indicate the intended use of the key
                .keyID(UUID.randomUUID().toString()) // give the key a unique ID
                .generate();

        // Output the private and public RSA JWK parameters

        return new String[] {
                jwk.toString(),
                jwk.toPublicJWK().toString()
        };
    }

    public String encrypt(String payload, String publicKey) throws NoSuchAlgorithmException, ParseException, JOSEException {
        JWK parsedJWK = JWK.parse(publicKey);

        JWEAlgorithm alg = JWEAlgorithm.RSA_OAEP_256;
        EncryptionMethod enc = EncryptionMethod.A128CBC_HS256;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(enc.cekBitLength());
        SecretKey cek = keyGenerator.generateKey();

        JWEObject jwe = new JWEObject(
                new JWEHeader(alg, enc),
                new Payload("Hello, world!"));
        jwe.encrypt(new RSAEncrypter(parsedJWK.toRSAKey().toRSAPublicKey(), cek));

        return jwe.serialize();
    }

    public String decrypt(String jweString, String key) throws ParseException, JOSEException {
        RSAPrivateKey rsaPrivateKey = JWK.parse(key).toRSAKey().toRSAPrivateKey();
        JWEObject jwe = JWEObject.parse(jweString);
        jwe.decrypt(new RSADecrypter(rsaPrivateKey));
        return jwe.getPayload().toString();
    }

    public static void main(String[] args) {
        int key = 0;
        int publicKey = 1;
        try {
            Encryption encryption = new Encryption();
            String[] keys = encryption.generateKeys();

            System.out.println("Key: " + keys[key]);
            System.out.println("Public Key " + keys[publicKey]);

            String secureMessage = encryption.encrypt("Hello World", keys[publicKey]);
            System.out.println(">>> " + secureMessage);

            String clearMessage = encryption.decrypt(secureMessage, keys[key]);
            System.out.println("<<< " + clearMessage);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
