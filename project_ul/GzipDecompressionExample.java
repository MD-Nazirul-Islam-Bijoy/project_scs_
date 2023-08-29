import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.Scanner;

public class GzipDecompressionExample {
    public void gzipd() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the compressed file path: ");
        String compressedFilePath = scanner.nextLine();
        File compressedFile = new File(compressedFilePath);
        if (!compressedFile.exists()) {
            System.out.println("The specified file does not exist.");
            return;
        }

        String decompressedFilePath = compressedFile.getAbsolutePath().replace(".gz", "_decompressed");
        File decompressedFile = new File(decompressedFilePath);

        try (
            FileInputStream fis = new FileInputStream(compressedFile);
            GZIPInputStream gzIn = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(decompressedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = gzIn.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            System.out.println("File decompressed successfully. Decompressed file: " + decompressedFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error occurred during decompression: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}