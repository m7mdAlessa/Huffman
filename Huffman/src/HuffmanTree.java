import java.io.*;
import java.util.*;

public class HuffmanTree {
	HuffmanNode root;
	public HuffmanTree(HuffmanNode root) {
		this.root = root;
	}
	public HuffmanTree(byte[] tree) {
		HuffmanNode r = new HuffmanNode();
		readFromArr(r,tree,0);
		root = r;
	}
	public HashMap<Byte,Pair<Long,Integer>> getMap(){
		HashMap<Byte,Pair<Long,Integer>> m = new HashMap<Byte,Pair<Long,Integer>>();
		getCodes(root,m,0,0);
		return m;
	}
	public void getCodes(HuffmanNode n, Map<Byte,Pair<Long,Integer>> m,long l,int size) {
		if(n.leaf) {
			m.put(n.b, new Pair<Long,Integer>(l,size));
			return;
		}
		getCodes(n.left,m,l<<1,size+1);
		getCodes(n.right,m,(l<<1)|1,size+1);
	}
	public void writeToFile(OutputStream out) throws IOException {
		byte[] tree = new byte[size()*2];
		writeToArr(root,tree,0);
		out.write(tree);
	}
	private int writeToArr(HuffmanNode n,byte[] t,int index){
		if(n.leaf) {
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
	public int size() {
		return recursiveSize(root);
	}
	private int recursiveSize(HuffmanNode n) {
		if(n.leaf) {
			return 1;
		}
		return recursiveSize(n.left)+recursiveSize(n.right)+1;
	}
}
