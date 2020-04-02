import java.io.*;
import java.util.*;

public class HuffmanTree {
	HuffmanNode root;
	
	//creates a huffman tree from a root node
	public HuffmanTree(HuffmanNode root) {
		this.root = root;
	}
	
	//creates a huffman tree form the tree data
	public HuffmanTree(byte[] tree) {
		HuffmanNode r = new HuffmanNode();
		readFromArr(r,tree,0);
		root = r;
	}
	
	//get a map that contains all the encoded codes of all the bytes
	public HashMap<Byte,Pair<Long,Integer>> getMap(){
		HashMap<Byte,Pair<Long,Integer>> m = new HashMap<Byte,Pair<Long,Integer>>();
		getCodes(root,m,0,0);
		return m;
	}
	public void getCodes(HuffmanNode n, Map<Byte,Pair<Long,Integer>> m,long l,int size) {
		if(n.isLeaf) {
			m.put(n.b, new Pair<Long,Integer>(l,size));
			return;
		}
		getCodes(n.left,m,l<<1,size+1);
		getCodes(n.right,m,(l<<1)|1,size+1);
	}
	
	//get the total encoded size of the encoded data
	public long getEncodedSize() {
		return getEncodedSize(root,0);
	}
	public long getEncodedSize(HuffmanNode n,int size){
		if(n.isLeaf) {
			return n.freq * size;
		}
		return getEncodedSize(n.left,size+1) + getEncodedSize(n.right,size+1);
	}
	
	//decode all the bitSize bits in arr and write it into the output file
	public void decode(byte[] bArr,OutputStream out,int bitSize) throws IOException {
		HuffmanNode n = root;
		
		//go over each bit
		for(int i = 0;i<bitSize;i++) {
			//the byte that contains the bit i in the byte array bArr
			int j = i/8;
			
			//get the current bit
			byte current = bitAt(bArr[j],i%8);
			
			//if the bit is 0 we go left and if its 1 go right
			if(current == 0) {
				n = n.left;
			}else if(current == 1){
				n = n.right;
			}
			
			//if n is a leaf node write the decoded byte into the file and reset n to the root
			if(n.isLeaf) {
				out.write(n.b);
				n = root;
			}
		}
	}
	
	//gets the bit at i in the byte b
	private static byte bitAt(byte b,int i) {
		b = (byte) (b>>>(7-i));
		b = (byte) (b&1);
		return b;
	}
	
	//writes the tree data into the output file
	public void writeToFile(OutputStream out) throws IOException {
		byte[] tree = new byte[size()*2];
		
		//writes the tree data into the byte array tree
		writeToArr(root,tree,0);
		
		//writes the tree data into the output file
		out.write(tree);
	}
	//writes the tree data into t using preorder traversal
	private int writeToArr(HuffmanNode n,byte[] t,int index){
		if(n.isLeaf) {
			t[index++] = 1;
			t[index++] = n.b;
			return index;
		}
		t[index++] = 0;
		t[index++] = 0;
		index = writeToArr(n.left,t,index);
		index = writeToArr(n.right,t,index);
		return index;
	}
	
	//reads the tree from the byte array t
	private int readFromArr(HuffmanNode n,byte[] t,int index) {
		if(index == 0) {
			index = 2;
		}
		if(t[index++] == 1) {
			n.left = new HuffmanNode(t[index++],0);
		}else {
			n.left = new HuffmanNode();
			index++;
			index = readFromArr(n.left,t,index);
		}
		if(t[index++] == 1) {
			n.right = new HuffmanNode(t[index++],0);
		}else {
			n.right = new HuffmanNode();
			index++;
			index = readFromArr(n.right,t,index);
		}
		
		return index;
	}
	
	//gets the number of nodes in the tree
	public int size() {
		return recursiveSize(root);
	}
	private int recursiveSize(HuffmanNode n) {
		if(n.isLeaf) {
			return 1;
		}
		return recursiveSize(n.left)+recursiveSize(n.right)+1;
	}
	
}
