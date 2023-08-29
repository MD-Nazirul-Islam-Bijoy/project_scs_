import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LZWCompression {
    public static class LempelZivWelch {

        public static List<Integer> encode(String text) {
            int dictSize = 256;
            Map<String, Integer> dictionary = new HashMap<>();
            for (int i = 0; i < dictSize; i++) {
                dictionary.put(String.valueOf((char) i), i);
            }

            String foundChars = "";
            List<Integer> result = new ArrayList<>();
            for (char character : text.toCharArray()) {
                String charsToAdd = foundChars + character;
                if (dictionary.containsKey(charsToAdd)) {
                    foundChars = charsToAdd;
                } else {
                    result.add(dictionary.get(foundChars));
                    dictionary.put(charsToAdd, dictSize++);
                    foundChars = String.valueOf(character);
                }
            }
            if (!foundChars.isEmpty()) {
                result.add(dictionary.get(foundChars));
            }
            return result;
        }

        public static String decode(List<Integer> encodedText) {
            int dictSize = 256;
            Map<Integer, String> dictionary = new HashMap<>();
            for (int i = 0; i < dictSize; i++) {
                dictionary.put(i, String.valueOf((char) i));
            }

            String characters = String.valueOf((char) encodedText.remove(0).intValue());
            StringBuilder result = new StringBuilder(characters);
            for (int code : encodedText) {
                String entry = dictionary.containsKey(code)
                        ? dictionary.get(code)
                        : characters + characters.charAt(0);
                result.append(entry);
                dictionary.put(dictSize++, characters + entry.charAt(0));
                characters = entry;
            }
            return result.toString();
        }
    }

    public static void compressFile(String inputFile, String outputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();

            List<Integer> compressed = LempelZivWelch.encode(content.toString());
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(outputFile));
            out.writeObject(compressed);
            out.close();

            System.out.println("File compressed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decompressFile(String inputFile, String outputFile) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(inputFile));
            List<Integer> compressed = (List<Integer>) in.readObject();
            in.close();

            String decompressed = LempelZivWelch.decode(compressed);
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(decompressed);
            writer.close();

            System.out.println("File decompressed successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void LZWCompression() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Compress");
        System.out.println("2. Decompress");
        int choice = scanner.nextInt();

        if (choice == 1) {
            String inputFile = "input.txt"; // Provide the path to your input file
            String compressedFile = "compressed.lzw";

            // Compress the file
            compressFile(inputFile, compressedFile);

            System.out.println("File compressed successfully.");
        } else if (choice == 2) {
            String compressedFile = "compressed.lzw"; // Provide the path to your compressed file
            String decompressedFile = "decompressed.txt";

            // Decompress the file
            decompressFile(compressedFile, decompressedFile);

            System.out.println("File decompressed successfully.");
        } else {
            System.out.println("Invalid choice.");
        }

        scanner.close();
    }
}

