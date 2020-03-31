import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Huffman {

	
	public static void encode(String fileName) throws IOException {
		File inFile = new File(fileName);
		FileInputStream in = new FileInputStream(inFile);
		long len = inFile.length();
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
		
		
		
	}
}
