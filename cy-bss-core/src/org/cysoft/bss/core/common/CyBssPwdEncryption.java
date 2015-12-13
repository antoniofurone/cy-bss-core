package org.cysoft.bss.core.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyBssPwdEncryption {
	
private static final Logger logger = LoggerFactory.getLogger(CyBssPwdEncryption.class);

public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws CyBssException{
  // Encrypt the clear-text password using the same salt that was used to
  // encrypt the original password
  logger.info("attemptedPassword="+attemptedPassword+";encryptedPassword="+encryptedPassword+";salt="+salt);	
  byte[] encryptedAttemptedPassword = encryptPwd(attemptedPassword, salt);
  // Authentication succeeds if encrypted password that the user entered
  // is equal to the stored hash
  return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
 }

public static byte[] encryptPwd(String password, byte[] salt) throws CyBssException{
  // PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
  // specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
  String algorithm = "PBKDF2WithHmacSHA1";
  // SHA-1 generates 160 bit hashes, so that's what makes sense here
  int derivedKeyLength = 160;
  // Pick an iteration count that works for you. The NIST recommends at
  // least 1,000 iterations:
  // http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
  // iOS 4.x reportedly uses 10,000:
  // http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
  int iterations = 20000;
  KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
  SecretKeyFactory f=null;
  
  try {
	  f = SecretKeyFactory.getInstance(algorithm);
  } catch (NoSuchAlgorithmException e) {
	// TODO Auto-generated catch block
		logger.error(e.toString());
		throw new CyBssException(e);
  }
  
  try {
	return f.generateSecret(spec).getEncoded();
  } catch (InvalidKeySpecException e) {
	// TODO Auto-generated catch block
		logger.error(e.toString());
		throw new CyBssException(e);
  }
 }

public static byte[] generateSalt() throws CyBssException{
  SecureRandom random=null;
  try {
	  random = SecureRandom.getInstance("SHA1PRNG");
  } catch (NoSuchAlgorithmException e) {
	// TODO Auto-generated catch block
	logger.error(e.toString());
	throw new CyBssException(e);
  }
  // Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
  byte[] salt = new byte[8];
  random.nextBytes(salt);
  return salt;
 }
	
	
public static void main(String[] args) {
	
	byte[] salt=null;
	try {
		salt=generateSalt();
		System.out.println("salt="+CyBssUtility.byteArrayToHex(salt));
		byte[] pwd=encryptPwd("cybss",salt);
		System.out.println("pwd="+CyBssUtility.byteArrayToHex(pwd));
		System.out.println("result="+authenticate("cybss",pwd,salt));
	} catch (CyBssException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
        
    	
    }

}
