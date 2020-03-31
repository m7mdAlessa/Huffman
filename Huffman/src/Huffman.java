import java.io.*;
import java.util.*;

public class Huffman {

	
	public static void encode(String fileName) throws IOException {
		File inFile = new File(fileName);
		File outFile = new File(fileName+".hc");
		FileInputStream in = new FileInputStream(inFile);
		OutputStream out = new FileOutputStream(outFile);
		long len = inFile.length();
		HuffmanOutputStream h = new HuffmanOutputStream(out,(int)len);
		byte[] bArr = new byte[(int) len];
		in.read(bArr);
		HashMap<Byte,Integer> freqMap = new HashMap<Byte,Integer>();
		for(byte b :bArr) {
			if(freqMap.containsKey(b)) {
				freqMap.put(b, freqMap.get(b)+1);
			}else {
				freqMap.put(b, 1);
			}
		}
		HuffmanTree tree = (new HuffmanHeap(freqMap)).constructTree();
		HashMap<Byte,Pair<Long,Integer>> codes = tree.getMap();
		for(byte b : bArr) {
			writeToStream(h,codes.get(b));
		}
		int tSize = tree.size() * 2;
		int encSize = h.sizeInByte();
		int bitSize = h.size();
		int size = tSize + encSize + 12;
		writeInt(out,size);
		writeInt(out,tSize);
		writeInt(out,bitSize);
		tree.writeToFile(out);
		in.close();
		h.flushByte();
		h.close();
		
	}
	public static void decode(String fileName) throws IOException {
		File inFile = new File(fileName);
		File outFile = new File(fileName.substring(0, fileName.length()-3));
		FileInputStream in = new FileInputStream(inFile);
		OutputStream out = new FileOutputStream(outFile);
		long len = inFile.length();
		byte[] bArr = new byte[(int) len];
		in.read(bArr);
		int size = arrToInt(Arrays.copyOfRange(bArr, 0, 4));
		int tSize = arrToInt(Arrays.copyOfRange(bArr, 4, 8));
		int bitSize = arrToInt(Arrays.copyOfRange(bArr, 8, 12));
		byte[] tree = Arrays.copyOfRange(bArr, 12,12+tSize);
		byte[] encoded = Arrays.copyOfRange(bArr, 12+tSize,size);
		HuffmanTree hTree = new HuffmanTree(tree);
		hTree.decode(encoded, out, bitSize);
		
		in.close();
		out.close();
		
	}
	private static int arrToInt(byte[] b) {
		int i = ((int) b[3] & 0xff) << 24
			       | ((int) b[2] & 0xff) << 16
			       | ((int) b[1] & 0xff) << 8
			       | ((int) b[0] & 0xff);
		return i;
	}
	private static void writeInt(OutputStream out,int num) throws IOException {
		byte[] b = new byte[] {
			       (byte) num,
			       (byte) (num >> 8),
			       (byte) (num >> 16),
			       (byte) (num >> 24)};
		out.write(b);
	}
	private static void writeToStream(HuffmanOutputStream h,Pair<Long,Integer> p) throws IOException{
		long code = p.first;
		int size = p.second;
		int numOfBytes = (int) Math.ceil(((double)size)/8);
		int remainder = (size % 8==0)?8:size%8;
		byte[] b = longToByteArr(code);
		h.writeBits(b[numOfBytes-1], remainder);
		for(int i = numOfBytes-2;i>=0;i--) {
			h.writeBits(b[i], 8);
		}
	}
	private static byte[] longToByteArr(long lng) {
		byte[] b = new byte[] {
			       (byte) lng,
			       (byte) (lng >> 8),
			       (byte) (lng >> 16),
			       (byte) (lng >> 24),
			       (byte) (lng >> 32),
			       (byte) (lng >> 40),
			       (byte) (lng >> 48),
			       (byte) (lng >> 56)};
		return b;
	}
	
}
