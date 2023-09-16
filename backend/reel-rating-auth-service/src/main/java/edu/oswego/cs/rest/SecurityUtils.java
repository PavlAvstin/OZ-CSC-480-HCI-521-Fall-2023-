/*
 * Class to hold methods relating to security for the user login microservice
 *
 * Salting and Hashing Passwords
 *      Passwords will be salted hashed and converted into a string using this format. To start we are using
 *          SHA 512 hashing and a 64 byte password unique salt (See below).
 *          [ 64 bytes 			        	| 64 bytes 		       ]
 * 	        [ Salted and hashed password	| Password unique salt ]
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

package edu.oswego.cs.rest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtils {

    /*
     * Salting and hashing functions
     */

    // generatePassword() returns a salted and hashed version of the password to be stored in form
    //      [saltedHashedPassword][salt] in the form of a String
    static public String generatePassword(String password) throws NoSuchAlgorithmException {
        // generate a password unique 64 byte salt
        byte[] salt = getSalt();

        // return hash the password with the salt
        return hashPassword(password, salt);
    }

    // validatePassword
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

    // hashPassword() - takes in a plaintext password and a salt. Combines and hashes them returning the new
    //      salted and hashed password
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

    // getSalt() generates a new secure pseudorandom salt to be used in hashing
    static byte[] getSalt(){
        // use Java.SecureRandom to get a secure pseudorandom
        SecureRandom secureRandom = new SecureRandom();
        // 64-bit salt since we are using SHA-512 (matches number of bits)
        byte[] salt = new byte[64];
        // fill the byte buffer and return
        secureRandom.nextBytes(salt);
        return salt;
    }

    // extractSalt() returns byte[64] of the salt taken from a previously stored password
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