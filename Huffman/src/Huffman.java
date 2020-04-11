import java.io.*;
import java.util.*;

public class Huffman {
	
	//encode the file "inFile" and writes the encoded file into "outFile
	public static void encode(String inFile,String outFile) throws IOException {
		File file = new File(inFile);
		FileInputStream in = new FileInputStream(file);
		HuffmanOutputStream out = new HuffmanOutputStream(outFile);
		
		//creates a byte array with size equal to inFile size in bytes
		byte[] bArr = new byte[(int) file.length()];
		
		//reads the inFile and stores its contents in bArr
		in.read(bArr);
		
		//creates a map that contains the byte as a key and its frequency as a value
		HashMap<Byte,Integer> freqMap = new HashMap<Byte,Integer>();
		
		//counts the frequencies of all bytes in bArr
		for(byte b :bArr) {
			if(freqMap.containsKey(b)) {
				freqMap.put(b, freqMap.get(b)+1);
			}else {
				freqMap.put(b, 1);
			}
		}
		
		//creates a heap from the freqMap
		HuffmanHeap hHeap = new HuffmanHeap(freqMap);
		
		//creates a Huffman tree from the heap
		HuffmanTree tree = hHeap.constructTree();
		
		//creates a map that contains the original byte as a key and a pair that contains the encoded code and the number of bits used in that code
		HashMap<Byte,Pair<Long,Integer>> codes = tree.getMap();
		
		// calculate the size of the tree
		int tSize = tree.size() * 2;
		// calculate the size of the encoded codes in bits
		int encBitsSize = (int) tree.getEncodedSize();
		// calculate the size of the encoded codes in bytes
		int encSize = (int) Math.ceil((double)encBitsSize/8);
		// calculate the total size of the output file
		int size = tSize + encSize + 12;
		
		//writes the sizes into the file
		writeInt(out,size);
		writeInt(out,tSize);
		writeInt(out,encBitsSize);
		
		//write the tree into the file
		tree.writeToFile(out);
		
		//encodes each byte in bArr and the write the encoded code into the output file
		for(byte b : bArr) {
			writeToStream(out,codes.get(b));
		}
		
		//close the streams
		in.close();
		out.flushByte();
		out.close();
		
	}
	//decode the file "inFile" and writes the decoded file into "outFile
	public static void decode(String inFile,String outFile) throws IOException {
		File file = new File(inFile);
		FileInputStream in = new FileInputStream(inFile);
		OutputStream out = new FileOutputStream(outFile);
		
		//creates a byte array with size = file size in bytes
		byte[] bArr = new byte[(int) file.length()];
		
		//reads the inFile and stores its contents in bArr
		in.read(bArr);
		
		//reads the sizes from the file
		int size = arrToInt(Arrays.copyOfRange(bArr, 0, 4));
		int tSize = arrToInt(Arrays.copyOfRange(bArr, 4, 8));
		int bitSize = arrToInt(Arrays.copyOfRange(bArr, 8, 12));
		
		//store the tree data into a byte array
		byte[] tree = Arrays.copyOfRange(bArr, 12,12+tSize);
		
		//store the encoded data into a byte array
		byte[] encoded = Arrays.copyOfRange(bArr, 12+tSize,size);
		
		//creates a huffman tree from the tree data
		HuffmanTree hTree = new HuffmanTree(tree);
		
		//decode the encoded data and write it into the output file
		hTree.decode(encoded, out, bitSize);
		
		//close the streams
		in.close();
		out.close();
		
	}
	
	//converts the byte array b into int and returns it
	private static int arrToInt(byte[] b) {
		int i = ((int) b[0] & 0xff) << 24
			       | ((int) b[1] & 0xff) << 16
			       | ((int) b[2] & 0xff) << 8
			       | ((int) b[3] & 0xff);
		return i;
	}
	
	//write int num into the file
	private static void writeInt(OutputStream out,int num) throws IOException {
		byte[] b = new byte[] {(byte) (num >> 24),
				(byte) (num >> 16),
				(byte) (num >> 8),
				(byte) num};
		
		out.write(b);
	}
	
	//writes the code in p to the file
	private static void writeToStream(HuffmanOutputStream h,Pair<Long,Integer> p) throws IOException{
		long code = p.first;
		int size = p.second;
		
		//number of bytes in long
		int numOfBytes = (int) Math.ceil(((double)size)/8);
		//0000000 111 00000000
		//number of bits used in the leftmost byte
		int remainder = (size % 8==0)?8:size%8;
		
		//converts the code into a byte array
		byte[] b = longToByteArr(code);
		
		//write the rightmost (remainder) bits in the leftmost byte into the file
		h.writeBits(b[8-numOfBytes], remainder);
		
		//write the remaining bytes into the file
		for(int i =9-numOfBytes;i<8;i++) {
			h.writeBits(b[i], 8);
		}
		
	}
	//converts the long lng to a byte array and returns it
	private static byte[] longToByteArr(long lng) {
		byte[] b = new byte[] {(byte) (lng >> 56),
			       (byte) (lng >> 48),
			       (byte) (lng >> 40),
			       (byte) (lng >> 32),
			       (byte) (lng >> 24),
			       (byte) (lng >> 16),
			       (byte) (lng >> 8),
			       (byte) lng};
		return b;
	}
	
}
