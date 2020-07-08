package com.dataweave.Test;


import java.io.*;
import java.security.*;
import javax.crypto.*;
import java.util.*;
public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args)
	throws Exception
	{
		//String password = "<password>";
            String password = "123456";
		
		KeyStore kspkcs12=KeyStore.getInstance("PKCS12");
		//  kspkcs12.load(new FileInputStream("<file>.p12"),password.toCharArray());
                
                kspkcs12.load(new FileInputStream("C:\\Program Files\\OpenSSL-Win64\\bin\\jboss_kystore1.keystore"),password.toCharArray());
		
		Key pk = null;
		java.security.cert.Certificate cert = null;
		
		String alias = "tomcat";
		if (kspkcs12.containsAlias(alias))
		{
			if (kspkcs12.isKeyEntry(alias))
			{
				pk = (PrivateKey)kspkcs12.getKey(alias, password.toCharArray());
				cert = kspkcs12.getCertificate(alias);
			}
		}
		
		if (pk == null || cert == null)
		{
			System.out.println("Can't find cert");
			return;
		}
		
		PrivateKey priv = (PrivateKey)pk;
		PublicKey pub = cert.getPublicKey();

		/* Create the cipher */
		Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

                
                /*System.out.println("the original PublicKey: " + pub);
                System.out.println("the original PrivateKey: " + priv);*/
		
               // String sql= "MIIFWzCCA0OgAwIBAgIEdkxLKTANBgkqhkiG9w0BAQsFADBeMQswCQYDVQQGEwJJTjESMBAGA1UECBMJa2FybmF0YWthMRIwEAYDVQQHEwlrYXJuYXRha2ExCzAJBgNVBAoTAmFzMQwwCgYDVQQLEwNhc2QxDDAKBgNVBAMTA2FzZDAeFw0yMDA2MTcwOTI4NTFaFw0yMDA5MTUwOTI4NTFaMF4xCzAJBgNVBAYTAklOMRIwEAYDVQQIEwlrYXJuYXRha2ExEjAQBgNVBAcTCWthcm5hdGFrYTELMAkGA1UEChMCYXMxDDAKBgNVBAsTA2FzZDEMMAoGA1UEAxMDYXNkMIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmcCCVV/bj0993bjiF9JoA7L5HCE4VY9RGjt+ti43A1vQEoP4qZkGEVmi393o1UVp3Rilcd6S7Zu0nA+gC6Wp26q9Bmik/3opTooC3Qc5p9jkLPp3dZYqgPVLR4d4IA7tjHas8zAfVC+jmCiUHerBmhxfEZ1qWa1ixJk5Wa3SoEdjTrsqK2mSjitNJHIToKfhN8ZsuH4MDwdecxxDBsiHd4m44cyQQxcqS79VYCEedjE11UN8pxXI4bLRuI9PkZ6hkpgUudk3XaHzIPavwwXyOaMPti3MWujM4Dir4Zu994+H0oS7Ud712xBCcuTB3GKJyIkJXLb4TeA/W+wM4VnUJBJeUIk5bc7yS+HjDTG6rIwbhGvYFyGZdVeuBcCQwSwNr2pXLHHmP3b0ohQRz2Nk55IQSm528QsFoc4Bx99397NEk02OAC0xkFthC6pLAhFtcILP6G/PsVCkg41a2rRjdJtF/Os0+05hWKp/rU2fE1OidCAxK71BjT8fg02UBivr4JKhGIVEkSanbrgNdFk+ftv33Cd/55jjfss6iA37Yw3EOHUACkR7OBE4scNO4wRiVEYTP+36MWtApitBRb0agAE49mkHcE4neG0K6QsK0NqrKi8grVLeMMdMHTChboQTK+mhOwkR0To8l5QReGSPtWJ4HY0FX37AR+B9FYmKnBkCAwEAAaMhMB8wHQYDVR0OBBYEFEkL6/6/xW9FbFfxPA8ylSfhYFNSMA0GCSqGSIb3DQEBCwUAA4ICAQA0wNGzQG0hX8miu7b4zdIeXPdH1cC+KFLEFsyBPUl6hS5YuVAVpCWk+pmQRxiDGPFxDQdmODVmdOhux/X02vGqaPUgGGgSDckf8lCy07IEq/KYNxtvBsNkMOxuJ/HZ3erZF2tmd+wauizvpG/SCdXSZUoOYKO9szfUMO0BB8Vc0o5IzzFpaobP8/C1nrye+rsjioUeKjlTqHjqG6MbIPJ5Ar+NAx5hbNZvBCplh9/UsFM5vIRdRI385iFEk8JOV7MlwrXo9MwoVq+esbxTziZKpz/aqRN2h2+a07/3UPR61QQjIKTqmmXtkCKRAdDxUyqWyztS/TS3JKlfCxG+O8NhJI6T+0y73pKFFHwYzm7UHdlCnUr3lKePFeRmDQeuubVNpSYxztdTbGKSwnG8Bsw7KGuH27gsNB2K3X01ezxVS4OUXuLmc7yATSpLefer5TfNwzwwGRw3sxakNPLd+r3a/Oz7EWu0SMI50+zyi4CY8IJOfj3LlyYumWrih6f59MjMWMZLQ+u3yNDQ5Z4zuvNhn1XuJafr3qtJwHeRp7ZTakZOHw5veoRpvDkpQtqgU8+uSjfywfXx3kqQw0G4Vh7Hv2pDohy2LKNO4d/pRP98RBs5d13fOUYJo+lBl3XOfi9WodFJ64z+nVKojbQBthC0CXsV5XjWHgLSOyzhf2qWIw==";
 //System.out.println("sql"+new String(Base64.getDecoder().decode(sql)));
                        //System.out.println("pub"+Base64.getEncoder().encodeToString());
		
      /*  System.out.println("pub"+Base64.getEncoder().encodeToString(pub.getEncoded()));      
        System.out.println("pri"+Base64.getEncoder().encodeToString(priv.getEncoded()));
        */
		String sql=Base64.getEncoder().encodeToString(cert.getEncoded());
        //System.out.println("cert"+Base64.getEncoder().encodeToString(cert.getEncoded()));
		String rs =new String( Base64.getDecoder().decode(sql));
		System.out.println("rs"+rs);
		System.out.println("cert444"+new String(cert.getEncoded()));
        System.out.println("cert"+sql);
        
        
        
       // System.out.println("pri _n"+new String(priv.getEncoded()));
        //System.out.println("pub _n"+new String(pub.getEncoded()));
        //System.out.println("cert"+cert.toString());
        System.out.println("----------------------------------------------------------");
         System.out.println("for"+pub.getFormat());
         System.out.println("for"+priv.getFormat());
         System.out.println("cert"+cert.getType());
         
         //cert.getType()
        
                
		// Initialize the cipher for encryption
		rsaCipher.init(Cipher.ENCRYPT_MODE, pub);

		// Cleartext
		String cleartextin = "Hello World!!";
		byte[] clearbytesin = null;
		clearbytesin = cleartextin.getBytes();
		System.out.println("the original cleartext is: " + cleartextin);

		// Encrypt the cleartext
		byte[] cipherbytes = null;
		cipherbytes = rsaCipher.doFinal(clearbytesin);

		// Initialize the same cipher for decryption
		rsaCipher.init(Cipher.DECRYPT_MODE, priv);

		// Decrypt the ciphertext
		byte[] clearbytesout = rsaCipher.doFinal(cipherbytes);
		String cleartextout = new String(clearbytesout);
		System.out.println("the final cleartext is: " + cleartextout);
	}

}

