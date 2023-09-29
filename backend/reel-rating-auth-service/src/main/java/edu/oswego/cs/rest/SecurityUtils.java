package edu.oswego.cs.rest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * This class holds methods relating to security for the user login microservice.
 *
 * <p>The main purpose is to generate salted and hashed passwords for the database to store along
 * with validating user passwords upon login attempts. The plain text passwords are transformed into
 * a Base64 encoded String of form <code>[salted and hashed password | password unique salt]</code>.
 * Doing so allows us to store and retrieve the password and its salt easily without losing any
 * security.</p>
 *
 * <p>Typical callers of SecurityUtils can would invoke the generatePassword() or validatePassword()
 * methods: </p>
 *
 * <blockquote><pre>
 * String encryptedPassword = SecurityUtils.generatePassword(user.getPassword());
 * if (SecurityUtils.validatePassword(user.getPassword(), db.getPassword(username)))
 * </blockquote>
 *
 *      Full list of methods
 *      - String generatePassword(String password)
 *      - boolean validatePassword(String proposedPassword, String expectedHashedPassword)
 *      - String hashPassword(String password, byte[] salt)
 *      - byte[] getSalt()
 *      - byte[] extractSalt(String expectedHashedPassword)
 *
 *      When to use methods
 *      - generatePassword is used when a user is setting their password.
 *          String hashedPassword = SecurityUtils.generatePassword(userPassword);
 *      - validatePassword is used when a user is logging in and returns a boolean representing whether the
 *          provided password matches the hashed password stored in the database.
 *          boolean correctPassword = SecurityUtils.validatePassword(userPassword)
 *      - The remaining functions are helper functions called by the generatePassword and validatePassword
 *          methods. You should not need to call them.
 */

public class SecurityUtils {

    /*
     * Salting and hashing functions
     */

    /**
     * Creates and returns a new salted and hashed password from a users plaintext password.
     * To accomplish this a password unique salt is also created. This password is then stored
     * and later used to validate the user when logging in.
     *
     * @param password the users plaintext password
     * @return      Base64 encoded string version of the salt appended to the salted and hashed password
     * @throws NoSuchAlgorithmException
     */
    static public String generatePassword(String password) throws NoSuchAlgorithmException {
        // generate a password unique 64 byte salt
        byte[] salt = getSalt();

        // return hash the password with the salt
        return hashPassword(password, salt);
    }

    /**
     * Encrypts via Salting and hashing a plaintext password and compares to the given salted and hashed password.
     * @param proposedPassword plaintext password
     * @param expectedHashedPassword Base64 encoded String version of the password unique salt appended
     *                               to the salted and hashed correct password
     * @return <code>true</code> if the salted and hashed version of the <code>proposedPassword</code>
     *      is equal to the <code>expectedHashedPassword</code>;
     *      <code>false</code> otherwise.
     * @throws NoSuchAlgorithmException
     */
    static public boolean validatePassword(String proposedPassword, String expectedHashedPassword) throws NoSuchAlgorithmException {
        // extract password unique hash from expectedHashedPassword
        byte[] salt = extractSalt(expectedHashedPassword);

        // find hash of proposed password
        String proposedHashedPassword = hashPassword(proposedPassword, salt);

        // if the two hashes match return true otherwise return false
        if (proposedHashedPassword.equals(expectedHashedPassword)){
            return true;
        } else { return false; }
    }

    /*
     * Salting and hashing helper functions
     */
    /**
     * Converts plaintext password to a base64 encoded String representation of the password unique salt
     * appended to the salted and hashed password. The passwords are hashed using the SHA-512 algorithmn
     * implemented via the MessageDigest from java.security.
     * @param password plaintext password
     * @param salt size 64 array of random bytes
     * @return base64 encoded String to be stored for future login attempts
     * @throws NoSuchAlgorithmException when the algorithm param for MessageDigest does not exist
     */
    static String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException{
        // create string to return
        String hashedPassword = null;
        try {
            // create a message digest to do the hashing
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            // add the password unique salt to the messageDigest
            messageDigest.update(salt);
            // have the message digest hash the password
            byte[] hashedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            // create a byte buffer to combine hashedPassword and salt
            ByteBuffer hashedPasswordBytes = ByteBuffer.allocate(128);
            // put hashedPassword and salt into the ByteBuffer
            hashedPasswordBytes.put(hashedBytes);
            hashedPasswordBytes.put(64, salt, 0, 64);

            // convert ByteArray to String using base64 encoder so we can decode later
            hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes.array());
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashedPassword;
    }

    /**
     * Generates a new size 64 array filled with securely pseudorandom bits referred to as salt.
     * This salt is then used to help introduce randomness into password to protect against precalculated
     * dictionary attacks also known as rainbow table attacks
     * @return array of random bytes
     */
    static byte[] getSalt(){
        // use Java.SecureRandom to get a secure pseudorandom
        SecureRandom secureRandom = new SecureRandom();
        // 64-bit salt since we are using SHA-512 (matches number of bits)
        byte[] salt = new byte[64];
        // fill the byte buffer and return
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Splits stored password into salt and salted and hashed password. The seperated salt is used
     * to validate passwords during login.
     * @param expectedHashedPassword Base64 encoded String version of salt appended to salted
     *                               and hashed password
     * @return size 64 byte array of password unique salt
     */
    static byte[] extractSalt(String expectedHashedPassword){
        // turn String back into as size 128 byte array
        ByteBuffer expectedPasswordBuffer = ByteBuffer.wrap(Base64.getDecoder().decode(expectedHashedPassword));
        // create a ByteBuffer and put the second half of the expectedHashedPassword in
        ByteBuffer saltBuffer = ByteBuffer.allocate(64).put(expectedPasswordBuffer.array(), 64, 64);
        // convert ByteBuffer into byte[64]
        byte[] saltBytes = saltBuffer.array();
        return saltBytes;
    }
}