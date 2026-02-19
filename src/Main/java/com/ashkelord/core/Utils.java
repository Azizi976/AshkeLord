package com.ashkelord.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Utility class for loading files and parsing data.
 */
public class Utils {

    /**
     * Loads a text file from the classpath and returns its entire content as a
     * String.
     * The path should be relative to the resources folder, e.g. "/maps/world1.txt".
     */
    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Utils.class.getResourceAsStream(path)))) {
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading file: " + path);
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * Safely parses an integer from a string, returning 0 on failure.
     */
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
