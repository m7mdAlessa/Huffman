
public class HuffmanNode {
	boolean isLeaf;
	int freq;
	byte value;
	HuffmanNode left,right;
	
	public HuffmanNode(byte b,int frequency){
		isLeaf = true;
		this.value = b;
		freq = frequency;
		left = null;
		right = null;
	}
	public HuffmanNode(int frequency,HuffmanNode r,HuffmanNode l){
		isLeaf = false;
		freq = frequency;
		left = r;
		right = l;
	}
	public HuffmanNode(){
		isLeaf = false;
		left = null;
		right = null;
	}
}
