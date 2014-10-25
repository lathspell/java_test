package de.lathspell.test.crypt3;

/**
 * GNU libc crypt(3) compatible hash method.
 *
 * See {@link #crypt(java.lang.String, java.lang.String)} for futher details.
 *
 * Written 2012 by Christian Hammers &lt;ch@lathspell.de&gt; and put into the
 * public domain.
 */
public class Crypt {

    /**
     * Calculates the digest using the strongest crypt(3) algorithm.
     *
     * A random salt and the default algorithm (currently SHA-512) are used.
     * See {@link #crypt(java.lang.String, java.lang.String)} for details.
     *
     * @param key The plaintext password.
     * @return The hash value.
     */
    public static String crypt(String key) throws Exception {
        return crypt(key, null);
    }

    /**
     * Encrypts a password in a crypt(3) compatible way.
     *
     * <p> The exact algorithm depends on the format of the salt string:
     * <ul>
     * <li>SHA-512 salts start with $6$ and are up to 16 chars long.
     * <li>SHA-256 salts start with $5$ and are up to 16 chars long
     * <li>MD5 salts start with "$1$" and are up to 8 chars long
     * <li>DES, the traditional UnixCrypt algorithm is used else with only 2 chars
     * <li>Only the first 8 chars of the passwords are used in the DES algorithm!
     * </ul>
     * The magic strings "$apr1$" and "$2a$" are not recognised by this method
     * as its output should be identical with that of the libc implementation.
     *
     * <p> The rest of the salt string is drawn from the set [a-zA-Z0-9./] and
     * is cut at the maximum length of if a "$" sign is encountered. It is
     * therefore valid to enter a complete hash value as salt to e.g. verify a
     * password with: storedPwd.equals(crypt(enteredPwd, storedPwd))
     *
     * <p> The resulting string starts with the marker string ($6$), continues
     * with the salt value and ends with a "$" sign followed by the actual hash
     * value. For DES the string only contains the salt and actual hash. It's
     * toal length is dependend on the algorithm used:
     * <ul>
     * <li>SHA-512: 106 chars
     * <li>SHA-256: 63 chars
     * <li>MD5: 34 chars
     * <li>DES: 13 chars
     * </ul>
     *
     * <p> Example:
     * <pre>
     *      crypt("secret", "$1$xxxx") => "$1$xxxx$aMkevjfEIpa35Bh3G4bAc."
     *      crypt("secret", "xx") => "xxWAum7tHdIUw"
     * </pre>
     *
     * This method comes in a variation that accepts a byte[] array to support
     * input strings that are not encoded in UTF-8 but e.g. in ISO-8859-1 where
     * equal characters result in different byte values.
     *
     * @see "The man page of the libc crypt (3) function."
     * @param key The plaintext password as entered by the used.
     * @param salt The salt value
     * @return The hash value i.e. encrypted password including the salt string
     */
    public static String crypt(String key, String salt) throws Exception {
        return crypt(key.getBytes(), salt);
    }

    /**
     * Encrypts a password in a crypt(3) compatible way.
     *
     * A random salt and the default algorithm (currently SHA-512) are used.
     * See {@link #crypt(java.lang.String, java.lang.String)} for details.
     *
     * @param key_bytes The plaintext password.
     * @return The hash value.
     */
    public static String crypt(byte[] key_bytes) throws Exception {
        return crypt(key_bytes, null);
    }

    /**
     * Encrypts a password in a crypt(3) compatible way.
     *
     * A random salt and the default algorithm (currently SHA-512) are used.
     * See {@link #crypt(java.lang.String, java.lang.String)} for details.
     *
     * @param key_bytes The plaintext password.
     * @param salt The salt value
     * @return The hash value.
     */
    public static String crypt(byte[] key_bytes, String salt) throws Exception {
        if (salt == null) {
            return Sha2Crypt.sha512Crypt(key_bytes);
        } else if (salt.startsWith(Sha2Crypt.SHA512_PREFIX)) {
            return Sha2Crypt.sha512Crypt(key_bytes, salt);
        } else if (salt.startsWith(Sha2Crypt.SHA256_PREFIX)) {
            return Sha2Crypt.sha256Crypt(key_bytes, salt);
        } else if (salt.startsWith(Md5Crypt.MD5_PREFIX)) {
            return Md5Crypt.md5Crypt(key_bytes, salt);
        } else {
            throw new Exception("TODO: UnixCrypt");
        }
    }
}
