import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class compress {
    File compressFile(File file) throws IOException {
        
        String fileName= file.getName()+".lzw";
        File outFile=new File("D:/"+fileName);

        FileInputStream fInput = new FileInputStream(file.getAbsolutePath());
        BufferedOutputStream fOutput = new BufferedOutputStream(new FileOutputStream(outFile));
        long fileSize = new File(file.getAbsolutePath()).length();
        byte[] allBytes = new byte[(int) fileSize];
        char[] uncompressed = new char[(int) fileSize];

        fInput.read(allBytes);
        for (int i = 0; i < fileSize; i++) {
            uncompressed[i] = (char) (allBytes[i] & 0xFF);}
        int dSize = 256;
        long x = uncompressed.length, y = 0;
        long percent;
        List<Integer> encoded = new ArrayList<Integer>();
        Map<String, Integer> dict = new HashMap<String, Integer>();
        for (int i = 0; i < 256; i++)
            dict.put("" + (char) i, i);
        String s = "";
        for (char l : uncompressed) {
            y++; // out ProgressBar
            percent = (y * 50) / x;
            String sc = s + l;
            if (dict.containsKey(sc))
                s = sc;
            else {
                encoded.add(dict.get(s));
                dict.put(sc, dSize++);
                s = "" + l;}}
        if (s != "")
            encoded.add(dict.get(s));
        x = encoded.size(); // out ProgressBar
        fOutput.write("LZW".getBytes());
        StringBuffer toWrite = new StringBuffer();
        int i;
        for (i = 0; i < encoded.size(); i++) {
            percent = 50 + ((i * 50) / x);
            if (encoded.get(i) != null) {
                if ((encoded.get(i) > 255) && (encoded.get(i) <= 65535)) {
                    toWrite.append("1");
                    toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) >>> 8)).replace(' ', '0'));
                    toWrite.append("0");
                    toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));
                } else if (encoded.get(i) > 65535) {
                    toWrite.append("1");
                    toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) >>> 16)).replace(' ', '0'));
                    toWrite.append("1");
                    toWrite.append(String.format("%8s", Integer.toBinaryString((encoded.get(i) >>> 8) & 0xFF)).replace(' ', '0'));
                    toWrite.append("0");
                    toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));} else {
                    toWrite.append("0");
                    toWrite.append(String.format("%8s", Integer.toBinaryString(encoded.get(i) & 0xFF)).replace(' ', '0'));
                }} else {toWrite.append("000000000");}}
        int iLeftovers = toWrite.length() % 8;
        for (i = 0; i < 8 - iLeftovers; i++) {
            toWrite.append('0');}
        toWrite.append(String.format("%8s", Integer.toBinaryString((8 - iLeftovers) & 0xFF)).replace(' ', '0'));
        int h, b = 0;
        byte[] toFile = new byte[toWrite.length() / 8];
        for (h = 7; h < toWrite.length(); h = h + 8) {
            toFile[b] = (byte) (Integer.parseInt(toWrite.substring(h - 7, h + 1), 2));
            b++;
        }fOutput.write(toFile);
        fInput.close();
        fOutput.close();

        System.out.println("Compression Successful");

        return outFile;}}
