
public class HuffmanNode {
	boolean leaf;
	int freq;
	byte b;
	HuffmanNode left,right;
	
	public HuffmanNode(byte b,int frequency){
		leaf = true;
		this.b = b;
		freq = frequency;
		left = null;
		right = null;
	}
	public HuffmanNode(int frequency,HuffmanNode r,HuffmanNode l){
		leaf = false;
		freq = frequency;
		left = r;
		right = l;
	}
}
