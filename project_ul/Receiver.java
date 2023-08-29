import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Receiver{

    public void receive() {
        int port = 1234;

        try {
            // Start a server socket to listen for incoming connections
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Receiver is listening...");

            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                // Read the file size
                long fileSize = dis.readLong();

                // Specify the destination path to save the received file
                String savePath = "D:\\receivefile\\t.pdf";
                FileOutputStream fos = new FileOutputStream(savePath);

                // Receive the file in chunks and write to the destination file
                byte[] buffer = new byte[1024];
                int bytesRead;
                long totalBytesRead = 0;
                while (totalBytesRead < fileSize && (bytesRead = dis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    totalBytesRead += bytesRead;
                }

                // Close resources
                fos.close();
                dis.close();
                socket.close();

                System.out.println("File received successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
