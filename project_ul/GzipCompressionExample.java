import java.io.*;
import java.util.zip.GZIPOutputStream;
import java.util.Scanner;

public class GzipCompressionExample {
    public void gzipc() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String filePath = scanner.nextLine();
        File inputFile = new File(filePath);
        File compressedFile = new File(inputFile.getAbsolutePath() + ".gz");

        try (
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = new FileOutputStream(compressedFile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            GZIPOutputStream gzOut = new GZIPOutputStream(bos);
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                gzOut.write(buffer, 0, bytesRead);
            }
            System.out.println("File compressed successfully. Compressed file: " + compressedFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error occurred during compression: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
