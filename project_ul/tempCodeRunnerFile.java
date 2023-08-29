import java.util.*;
import java.io.*;

public class HuffmanCompression {
    private static StringBuilder sb = new StringBuilder();
    private static Map<Byte, String> huffmap = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java HuffCompression <inputFilePath> <outputFilePath>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        compress(inputFilePath, outputFilePath);

        System.out.println("File compressed using Huffman coding.");
    }

    public static void compress(String src, String dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            byte[] b = new byte[inStream.available()];
            inStream.read(b);
            inStream.close();

            byte[] huffmanBytes = createZip(b);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(new FileOutputStream(dst));
            objectOutStream.writeObject(huffmanBytes);
            objectOutStream.writeObject(huffmap);
            objectOutStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] createZip(byte[] bytes) {
        MinPriorityQueue<ByteNode> nodes = getByteNodes(bytes);
        ByteNode root = createHuffmanTree(nodes);
        Map<Byte, String> huffmanCodes = getHuffCodes(root);
        byte[] huffmanCodeBytes = zipBytesWithCodes(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    private static MinPriorityQueue<ByteNode> getByteNodes(byte[] bytes) {
        Map<Byte, Integer> tempMap = new HashMap<>();
        for (byte b : bytes) {
            Integer value = tempMap.get(b);
            if (value == null)
                tempMap.put(b, 1);
            else
                tempMap.put(b, value + 1);
        }

        MinPriorityQueue<ByteNode> nodes = new MinPriorityQueue<>();
        for (Map.Entry<Byte, Integer> entry : tempMap.entrySet())
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        return nodes;
    }

    private static ByteNode createHuffmanTree(MinPriorityQueue<ByteNode> nodes) {
        while (nodes.len() > 1) {
            ByteNode left = nodes.poll();
            ByteNode right = nodes.poll();
            ByteNode parent = new ByteNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
        }
        return nodes.poll();
    }

    private static Map<Byte, String> getHuffCodes(ByteNode root) {
        getHuffCodes(root.left, "0", sb);
        getHuffCodes(root.right, "1", sb);
        return huffmap;
    }

    private static void getHuffCodes(ByteNode node, String code, StringBuilder sb1) {
        StringBuilder sb2 = new StringBuilder(sb1);
        sb2.append(code);
        if (node != null) {
            if (node.data == null) {
                getHuffCodes(node.left, "0", sb2);
                getHuffCodes(node.right, "1", sb2);
            } else
                huffmap.put(node.data, sb2.toString());
        }
    }

    private static byte[] zipBytesWithCodes(byte[] bytes, Map<Byte, String> huffCodes) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes)
            strBuilder.append(huffCodes.get(b));

        int length = (strBuilder.length() + 7) / 8;
        byte[] huffCodeBytes = new byte[length];
        int idx = 0;
        for (int i = 0; i < strBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > strBuilder.length())
                strByte = strBuilder.substring(i);
            else
                strByte = strBuilder.substring(i, i + 8);
            huffCodeBytes[idx] = (byte) Integer.parseInt(strByte, 2);
            idx++;
        }
        return huffCodeBytes;
    }

    private static class ByteNode implements Comparable<ByteNode> {
        Byte data;
        int frequency;
        ByteNode left;
        ByteNode right;

        ByteNode(Byte data, int frequency) {
            this.data = data;
            this.frequency = frequency;
        }

        public int compareTo(ByteNode other) {
            return Integer.compare(this.frequency, other.frequency);
        }
    }

    private static class MinPriorityQueue<E extends Comparable<E>> {
        private PriorityQueue<E> pq;

        MinPriorityQueue() {
            pq = new PriorityQueue<>();
        }

        void add(E element) {
            pq.add(element);
        }

        E poll() {
            return pq.poll();
        }

        int len() {
            return pq.size();
        }
    }
}