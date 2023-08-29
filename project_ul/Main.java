import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
          public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("0. Stop");
        System.out.println("1. Search File");
        System.out.println("2. LZW Compression");
        System.out.println("3. GZIP Compression");
        System.out.println("4. GZIP Decompression");
        System.out.println("5. Sender");
        System.out.println("6. Receiver");
        while(true)
        {
        int choice = scanner.nextInt();
        if(choice==0)break;
        switch (choice) {
            case 1:
            { FileSearch_1 fs=new FileSearch_1();
                fs.file();
                break;
            }
            case 2:
                {
                     Scanner sc=new Scanner(System.in);
        File file;
        String filePath;
        int operation;
        int algorithm;
        compress lzwCompression =new compress();
        decompression decompression=new decompression();

        System.out.println("Enter the file path");
        filePath=sc.nextLine();
        file=new File(filePath);

        if(file.exists()){
            System.out.println("File is ready for operation");
        }
        else{
            System.out.println("Invalid file path");
        }

        System.out.println("Select operation:---");
        System.out.println("1. File Compression");
        System.out.println("2. File Decompression");
        operation=sc.nextInt();

        if(operation==1){
            System.out.println("Select an algorithm:---");
            System.out.println("1. LZW Compression");
            algorithm=sc.nextInt();

            switch (algorithm){
                case 1:
                    lzwCompression.compressFile(file);
                    break;

            }


        } else if (operation==2) {
            System.out.println("Select an algorithm:---");
            System.out.println("1. LZW Decompression");
            algorithm=sc.nextInt();

            switch (algorithm){
                case 1:
                    decompression.decompressed(file);
                    break;

            }
        }
        break;
                }
            case 3:
               { GzipCompressionExample gz=new GzipCompressionExample();
                gz.gzipc();
                break;
            }
            case 4:
            {
            GzipDecompressionExample gzde=new GzipDecompressionExample();
            gzde.gzipd();
                break;
            }
            case 5:
                {
                    Sender sd=new Sender();
                sd.sent();
                break;
            }
            case 6:
           { 
            Receiver re=new Receiver();
            re.receive();
                break;
            }
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }
    }
}