package com.afterlife.wow_afterlife_api.service;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class AzerothCoreSRP6 {

    private static final int SALT_LENGTH = 32;
    private static final int VERIFIER_LENGTH = 32;

    private static final BigInteger G = BigInteger.valueOf(7);

    private static final BigInteger N = new BigInteger(
            "894B645E89E1535BBDAD5B8B290650530801B18EBFBF5E8FAB3C82872A3E9BB7",
            16
    );

    private static final SecureRandom RANDOM = new SecureRandom();

    private AzerothCoreSRP6() {
    }

    public record RegistrationData(
            byte[] salt,
            byte[] verifier
    ) {
    }

    public static RegistrationData makeRegistrationData(
            String username,
            String password
    ) {

        String normalizedUsername = username.toUpperCase();
        String normalizedPassword = password.toUpperCase();

        byte[] salt = new byte[SALT_LENGTH];

        RANDOM.nextBytes(salt);

        byte[] verifier = calculateVerifier(
                normalizedUsername,
                normalizedPassword,
                salt
        );

        return new RegistrationData(salt, verifier);
    }

    private static byte[] calculateVerifier(
            String username,
            String password,
            byte[] salt
    ) {

        /*
         * h1 = SHA1("USERNAME:PASSWORD")
         */
        byte[] usernamePassword = (
                username + ":" + password
        ).getBytes(StandardCharsets.UTF_8);

        byte[] h1 = sha1(usernamePassword);

        /*
         * h2 = SHA1(salt || h1)
         */
        byte[] saltAndH1 = new byte[
                salt.length + h1.length
        ];

        System.arraycopy(
                salt,
                0,
                saltAndH1,
                0,
                salt.length
        );

        System.arraycopy(
                h1,
                0,
                saltAndH1,
                salt.length,
                h1.length
        );

        byte[] h2 = sha1(saltAndH1);

        /*
         * AzerothCore interpreta h2 como little-endian.
         */
        byte[] littleEndianH2 = reverse(h2);

        BigInteger x = new BigInteger(
                1,
                littleEndianH2
        );

        /*
         * v = g^x mod N
         */
        BigInteger verifier = G.modPow(x, N);

        /*
         * AzerothCore almacena el resultado
         * en little-endian.
         */
        return toLittleEndian(
                verifier,
                VERIFIER_LENGTH
        );
    }

    private static byte[] sha1(byte[] data) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-1");

            return digest.digest(data);

        } catch (NoSuchAlgorithmException e) {

            throw new IllegalStateException(
                    "SHA-1 no está disponible",
                    e
            );
        }
    }

    private static byte[] reverse(byte[] input) {

        byte[] output = input.clone();

        for (int i = 0; i < output.length / 2; i++) {

            byte temp = output[i];

            output[i] =
                    output[output.length - 1 - i];

            output[output.length - 1 - i] =
                    temp;
        }

        return output;
    }

    private static byte[] toLittleEndian(
            BigInteger value,
            int length
    ) {

        byte[] result = new byte[length];

        byte[] bigEndian =
                value.toByteArray();

        int start = 0;

        /*
         * BigInteger puede agregar un byte 0
         * al principio para indicar signo positivo.
         */
        if (bigEndian.length > 1
                && bigEndian[0] == 0) {

            start = 1;
        }

        int bytesToCopy =
                bigEndian.length - start;

        if (bytesToCopy > length) {

            throw new IllegalStateException(
                    "El verifier excede " + length + " bytes"
            );
        }

        for (int i = 0; i < bytesToCopy; i++) {

            result[i] =
                    bigEndian[
                            bigEndian.length - 1 - i
                    ];
        }

        return result;
    }
}