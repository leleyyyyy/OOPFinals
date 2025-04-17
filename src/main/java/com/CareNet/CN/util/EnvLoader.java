package com.CareNet.CN.util;

// To load the .env file that contains the database key

import java.io.File;
import java.util.Scanner;

public class EnvLoader {
    public static void load(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        System.setProperty(parts[0], parts[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
