import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.Scanner;

public class Sender {

    public  void sent(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the receiver's IP address: ");
        String receiverIp = scanner.nextLine();

        System.out.print("Enter the path of the file to send: ");
        String filePath = scanner.nextLine();

        scanner.close();

        int port = 1234;
        try {
            // Establish a connection to the receiver
            Socket socket = new Socket(receiverIp, port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Specify the file you want to send
            File file = new File(filePath);

            // Get the file size and send it to the receiver
            long fileSize = file.length();
            dos.writeLong(fileSize);

            // Read the file and send it in chunks
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, bytesRead);
            }

            // Close resources
            fis.close();
            dos.close();
            socket.close();

            System.out.println("File sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
