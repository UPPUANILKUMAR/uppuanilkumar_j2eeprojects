package com.dataweave.Test;

//import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    //private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCz1zqQHtHvKczHh58ePiRNgOyiHEx6lZDPlvwBTaHmkNlQyyJ06SIlMU1pmGKxILjT7n06nxG7LlFVUN5MkW/jwF39/+drkHM5B0kh+hPQygFjRq81yxvLwolt+Vq7h+CTU0Z1wkFABcTeQQldZkJlTpyx0c3+jq0o47wIFjq5fwIDAQAB";
    //private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALPXOpAe0e8pzMeHnx4+JE2A7KIcTHqVkM+W/AFNoeaQ2VDLInTpIiUxTWmYYrEguNPufTqfEbsuUVVQ3kyRb+PAXf3/52uQczkHSSH6E9DKAWNGrzXLG8vCiW35WruH4JNTRnXCQUAFxN5BCV1mQmVOnLHRzf6OrSjjvAgWOrl/AgMBAAECgYAgA0YHdZUFL7mmIvwuE/2+Vh7JVKRAhfM7ILNHQBx7wHkOqro9eWp8mGQhUeDvitWb1C4yizJK0Znkx/pqQtFZuoatUsggocjXFl86FElQwrBp08DvfKfd0bGgy0VTFQVmCtxiqhpAmC7xmXNZXfBD41rl9CKbFfZw05QC5BoQ0QJBAO7LSku97NgFBJQ+vbmVDonuvgnQjVNb7SnwrcpJHEUAGbaVq1a50jz+s6n39TOagASaW6pcY0uwiygYu6xDnkMCQQDAzIGNKFKomTI6djcOyHfQ1ZXqyDQ3guX6nHhzZnNHFF8ZD3fPyyIRSZ3JvPK5iEzJLhB7FRtyWkGcdXgJTWoVAkBfx9zKGqkYUJLwn2XcPWRygPdq2mMFb5bmPqqGu+KB7rNhoBD0nV4tpwALifCpPSxiLEPeRmZxoqN+dsU4KHsfAkAyQt4fK3zpAQ8MGJdf3jkGEzhC/bBHLHPB8pqgEvxIcnIcOWEVpbIa6aMd3Yk1fuftpnmbbLQ8CnWCUUlau3jFAkEAk6bOZIWhTYRwIZcwBdkpyLlbatQFoTTM3i444YutXt3FrFfaWBxge+eYKId+J4dCrt/EmHhSfWKEzHibf6N5Sg==";
    private static String publicKey ="MIIDQzCCAiugAwIBAgIEUfxf4zANBgkqhkiG9w0BAQsFADBSMQswCQYDVQQGEwJJTjEMMAoGA1UECBMDa2FyMQwwCgYDVQQHEwNrYXIxCzAJBgNVBAoTAmFzMQwwCgYDVQQLEwNhc2QxDDAKBgNVBAMTA2FzZDAeFw0yMDA2MTYxODI4MjBaFw0yMDA5MTQxODI4MjBaMFIxCzAJBgNVBAYTAklOMQwwCgYDVQQIEwNrYXIxDDAKBgNVBAcTA2thcjELMAkGA1UEChMCYXMxDDAKBgNVBAsTA2FzZDEMMAoGA1UEAxMDYXNkMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA08xM2Jw86YhhYqIZ29+kxVlQnoH/P1mnrhOPZlG6E0OyUS2amXg8M9poZglnhhBDucrrlJAwFk/Lo3mRfqVdeVnCKcRTR2KDlc6WwGDd9z7bue+XV/AQSALZ1mKI3d/8Rp8u4dk3xOho3yPo+/LjI4wHS9lIVH8e31CJMKSYJc+3tAuP53yGELvyhHriO3ZKv3q6rTxE+cExLGBVTZyNw9gO++KSFeEFtphcjGfHQNU86HoDXDQEnaBZejxSGhjH56HQ5mI/fHNiQliPR6he9SXnaySlgxhYsExcYr9IvWlVg5KbgUp8Bi85WABUaI38vBwaLUQeLebilKwwSJRXCwIDAQABoyEwHzAdBgNVHQ4EFgQUcZkOJ9zlffkgKWNVVHMEtZCIRWcwDQYJKoZIhvcNAQELBQADggEBAIhhYqz653Ixs7zkw39qgccwPpnn0Hq02X2ua4dN55emD7eKGiwavAnEJaIc56Z2CP1hhUidYbKikjQYoq3MacyuHcGyzi+EwcEAyRw5acxgghmVov/MUaivoDmYruFlXCECoSpxbNza+XpbVcHYcW+mvCxnSfhLQGGRg/PnQUW55BuJ0QtqtWc2LLlQgrlQRQLOCP+AvZNs9/zC8QhZ41XEEBfzBZBKzAcDLFTqReHpFlmVPQLBlNN3jbj7ZnKoJ6URFSOPjP5qJE65tFNYFwWbCazHpDCpR8DvBQbrklLPGBKAz0adJNjrzaRt8Wseq16wgEjczLG2TgLs9lCrsPQ=";
    private static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDTzEzYnDzpiGFiohnb36TFWVCegf8/WaeuE49mUboTQ7JRLZqZeDwz2mhmCWeGEEO5yuuUkDAWT8ujeZF+pV15WcIpxFNHYoOVzpbAYN33Ptu575dX8BBIAtnWYojd3/xGny7h2TfE6GjfI+j78uMjjAdL2UhUfx7fUIkwpJglz7e0C4/nfIYQu/KEeuI7dkq/erqtPET5wTEsYFVNnI3D2A774pIV4QW2mFyMZ8dA1TzoegNcNASdoFl6PFIaGMfnodDmYj98c2JCWI9HqF71JedrJKWDGFiwTFxiv0i9aVWDkpuBSnwGLzlYAFRojfy8HBotRB4t5uKUrDBIlFcLAgMBAAECggEBAIlgsdOqe8QKhwhgFp/Mj2bGiQlXTsW7lIzP+SqoBibquSX4i5fSKlKD70WIAhMuR4+idG3XnKlMNs0hVGMuRdQpiUxoIJBj8O/hoVzFOBRgPEt9gdAzUYwz9TIelaI837i7FcC+kXLyQNvDwiJyKCxtbmofVNAh6Fd3OJVJnbqfs6G1aeeIPSVzaJ68an+RW3ARdXLwjo+COdj91L+aLL5yQhpPgoNHbBAWc6ZV9A643LtIsfHgKKMUDYBNQAl7yDyw9nEQFWoczUEe5MLuXiTKafFkxhbWrKguDCNJgfHes2x31bMmqFzA+wJusQdtckIXphHhrC4IxinWJq5GtekCgYEA9ClWcQmTSRLO/k6GrU1H5JY0Z4XDru6JWuddZOlPcrPZnnk6V3878QMafUiwngaF977ZzlUac8hrL9b/NryRduBMq05LtiBFoxy3nfD1H1XyK48Aw6NOWr2qSy2zOoOhJscxKd3/zmNg72Z5RONZBztfkdKFxU4agttd/1gDpncCgYEA3hFAGr95R8w1Y6I1soZMWQMYH/+ulauIJEAcfjaZSP8Z+dgud0m8Kwpm2cLulNwFCwwGG7sXpchn/jWDxR3BBgAkDXmNUpk5mKQjJQK7KtaPsfwNxY9TvfZTlVJH/+urYFie1XBRNAtO1EpMIthURUU5oDxL7IkezKBp31LT9Q0CgYBJXgaQju0IHzO97QDBIKDCynPSy7oiXaE+hJlm0kjCUdLJsOncEuqDIIicLvSLd7BX4qx2ONJrCkVTKcqkuwMG8p13OF69ynLCccWnGxm9IclUV3NGVlMcJzP9xSy/H4CUM3TNfNz6OpiNN4CnN52v86JjIhx6Tl7fBWX4bY0jPwKBgHl8PHKln2bM68RHAFNbLzde/vq6eLs+rOsP+8Z8fh02GfLZ9omh2AQ/24Z0Iuti9otVl53iG4zBd0rV4fGbVna+6vpILi0q0ByWZvefZ9+Go/P97VbQE0MQGCfPbL4wIoBB2xrvKC1tpkFzJ/XEHU4MEft8IRkU5rmRZHodKQGdAoGAbPSJN/ZapnlE2apDehrKGoFI7Pym/7YyBSxYqdVZtr98Jrbn9rDvCkkvxKdb9u/utZhjA3gOPLWiC6y+nc2YZ41qdr8WTmkSx9yHpggzoyAfAOQPiGYOW+pRqnNhdshiRhisFbvoS4z13ZWNu6ZJX6W3hzzY5yRGEURaVRymhF0=";
    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String encryptedString = Base64.getEncoder().encodeToString(encrypt("Dhiraj is the author", publicKey));
            System.out.println(encryptedString);
            String decryptedString = RSAUtil.decrypt(encryptedString, privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}