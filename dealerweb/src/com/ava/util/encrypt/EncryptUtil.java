package com.ava.util.encrypt;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ava.resource.GlobalConfig;
import com.ava.util.TextUtil;

public class EncryptUtil {

    private static MessageDigest digest;
    private static MessageDigest md5;
    private static final Object DIGEST_LOCK = new Object();
    private static Blowfish cipher = null;

    static {
        // Create a message digest instance.
        try {
            digest = MessageDigest.getInstance("SHA");
			md5 = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            //Log.error(LocaleUtils.getLocalizedString("admin.error"), e);
        }

    }
  

    /**
     * Returns a digest given a token and password, according to JEP-0078.
     *
     * @param token the token used in the digest.
     * @param password the plain-text password to be digested.
     * @return the digested result as a hex string.
     */
    public static String createDigest(String token, String password) {
        synchronized (DIGEST_LOCK) {
            digest.update(token.getBytes());
            return TextUtil.encodeHex(digest.digest(password.getBytes()));
        }
    }

    /**
     * Returns an encrypted version of the plain-text password. Encryption is performed
     * using the Blowfish algorithm. The encryption key is stored as the Jive property
     * "passwordKey". If the key is not present, it will be automatically generated.
     *
     * @param password the plain-text password.
     * @return the encrypted password.
     * @throws UnsupportedOperationException if encryption/decryption is not possible;
     *      for example, during setup mode.
     */
    public static String encryptPassword(String password) {
        if (password == null) {
            return null;
        }
        Blowfish cipher = getCipher();
        if (cipher == null) {
            throw new UnsupportedOperationException();
        }
        return cipher.encryptString(password);
    }

    /**
     * Returns a decrypted version of the encrypted password. Encryption is performed
     * using the Blowfish algorithm. The encryption key is stored as the Jive property
     * "passwordKey". If the key is not present, it will be automatically generated.
     *
     * @param encryptedPassword the encrypted password.
     * @return the encrypted password.
     * @throws UnsupportedOperationException if encryption/decryption is not possible;
     *      for example, during setup mode.
     */
    public static String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null) {
            return null;
        }
        Blowfish cipher = getCipher();
        if (cipher == null) {
            throw new UnsupportedOperationException();
        }
        return cipher.decryptString(encryptedPassword);
    }

    /**
     * Returns a Blowfish cipher that can be used for encrypting and decrypting passwords.
     * The encryption key is stored as the Jive property "passwordKey". If it's not present,
     * it will be automatically generated.
     *
     * @return the Blowfish cipher, or <tt>null</tt> if Openfire is not able to create a Cipher;
     *      for example, during setup mode.
     */
    private static synchronized Blowfish getCipher() {
        if (cipher != null) {
            return cipher;
        }
        // Get the password key, stored as a database property. Obviously,
        // protecting your database is critical for making the
        // encryption fully secure.
        String keyString;
        try {
            keyString = GlobalConfig.getPasswordKey();
            if (keyString == null) {
                keyString = "kingsh";
            }
            cipher = new Blowfish(keyString);
        }catch (Exception e) {
            //Log.error(e);
        }
        return cipher;
    }
    
	/**	Java标准包的MD5加密，长度为32位字符串，返回的a-f是小写	*/
	public static String getMD5(String inStr) {
		if (inStr==null){
			return null;
		}
		byte[] digest = md5.digest(inStr.getBytes());//返回的是byet[]，要转化为String存储比较方便
		return TextUtil.encodeHex(digest);
	}

    public static String encodeBase64(byte[] data) {
        return encodeBase64(data, false);
    }

    public static String encodeBase64(byte[] data, boolean lineBreaks) {
        return encodeBase64(data, 0, data.length, lineBreaks);
    }

    public static String encodeBase64(byte[] data, int offset, int len, boolean lineBreaks) {
        return Base64.encodeBytes(data, offset, len, (lineBreaks ?  Base64.NO_OPTIONS : Base64.DONT_BREAK_LINES));
    }
    
	public static void main(String[] args) {
		System.out.println("MD5=" + getMD5("111111"));
		System.out.println("keyString=" + GlobalConfig.getPasswordKey());
		System.out.println("result=" + encryptPassword("111111"));
		System.out.println("length=" + encryptPassword("111111").length());
		System.out.println("original=" + decryptPassword("43c656bfd985184af3e157bcf8282140764d915a48d87472"));
	}
}