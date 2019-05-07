package com.abedafnan.exercise2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        HashMap<Character, Integer> charsCount = new HashMap<>();
        String fileContent = readFile();

        // Print the content of the file
        System.out.println("File Content: ");
        System.out.println(fileContent);

        // Count and print the occurrence of each letter in the file content
        Character[] contentCharArray = fileContent.chars()
                .mapToObj(c -> (char)c)
                .toArray(Character[]::new);

        Stream.of(contentCharArray).forEach(character -> {
            if (!charsCount.containsKey(character)) {
                charsCount.put(character, 1);
            } else {
                int newCount = charsCount.get(character) + 1;
                charsCount.replace(character, newCount);
            }
        });

        System.out.printf("\n\n%-10s %10s\n----------------------------", "Character", "Count");
        charsCount.forEach((key, val) -> System.out.printf("\n%-10s %10d", key, val));
    }

    /**
     *
     * @return The content of the file to be read as a string
     */
    public static String readFile() {
        String fileContent = "";
        try {
            File file = new File("exercise2.txt");
            FileInputStream inputStream = new FileInputStream(file);

            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) {
                fileContent = fileContent + scanner.next() + " ";
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return fileContent;
    }
}
