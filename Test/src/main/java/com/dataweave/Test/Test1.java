package com.dataweave.Test;


/**
 *
 * @author anil
 */

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import javax.crypto.*;



import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

//import org.apache.commons.codec.binary.Base64;

public class Test1 {

	/**
	 * @param args
	 * @throws Exception 
	 */
	
	
	
public void cert2() {
try {
FileInputStream fm = new FileInputStream("C:\\Program Files\\OpenSSL-Win64\\bin\\jboss_kystore1.keystore");
    KeyStore ks = KeyStore.getInstance("PKCS12");
    try {
        ks.load(fm, "123456".toCharArray());
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("cert2");
    Key key = ks.getKey("tomcat", "123456".toCharArray());
    java.security.cert.Certificate cert = ks.getCertificate("tomcat");
    PublicKey publicKey = cert.getPublicKey();
    System.out.println("Public key");
    System.out.println(Base64.getEncoder().encodeToString(
            publicKey.getEncoded()));
    fm.close();
}catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
}
	
	
	public void cert1(PublicKey pk) { 
	//PublicKey publicKey = keyPair.getPublic();
		try {
		PublicKey publicKey =pk;
	StringWriter writer = new StringWriter();
	PemWriter pemWriter = new PemWriter(writer);
	pemWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
	//pemWriter.writeObject(  publicKey);
	
	pemWriter.flush();
	pemWriter.close();
	System.out.println("writer.toString()"+writer.toString()); 
	//writer.toString();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
	}
	
	
	public void cert() {
	
		try {
	String s="26737026521487158063734685469397836959898197092505868275873180955718547645835782886887025722028554358927579463803644217364907896174172787331593621807352880849660862425263911769136676216888065212432598762339269819061675448166704974647266617106510557480409864698303619388281231222254873651675135129629467451498717321317973263101934260875890855076484405684287899910828987970944179033738830003014451340008253518507990401334893735714101759578755896725456331028161750767654577947705263699124724458925552487510587475573987862317640917803668312166063693160412188843110707252541175110635833000810359338940049750403875696039691";
	
	KeyFactory f = KeyFactory.getInstance("RSA");
	BigInteger modulus = new BigInteger(s, 10);
	BigInteger exp = new BigInteger("65537", 10);
	RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exp);
	PublicKey pub = f.generatePublic(spec);
	byte[] data = pub.getEncoded();
	String base64encoded = new String(Base64.getEncoder().encode(data));
			//encode(data));
	System.out.println("cert method"+base64encoded);
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args)
	throws Exception
	{
		
		new Test1().cert2();
		new Test1().cert();
		//String password = "<password>";
            String password = "123456";
		
		KeyStore kspkcs12=KeyStore.getInstance("PKCS12");
		//  kspkcs12.load(new FileInputStream("<file>.p12"),password.toCharArray());
                
                //kspkcs12.load(new FileInputStream("/usr/Anil_Kumar/SSl_test/Exp/jboss_kystore1.keystore"),password.toCharArray());
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
		
		//new Test1().cert1(pub);

		/* Create the cipher */
		Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

                
                System.out.println("the original PublicKey: " + pub);
                System.out.println("the original PrivateKey: " + priv);
                
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

