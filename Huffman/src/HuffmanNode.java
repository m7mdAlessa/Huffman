
public class HuffmanNode {
	boolean isLeaf;//
	int freq;
	byte b;
	HuffmanNode left,right;
	
	public HuffmanNode(byte b,int frequency){
		isLeaf = true;
		this.b = b;
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
	public void setLeft(HuffmanNode l) {
		left = l;
	}
	public void setRight(HuffmanNode r) {
		right = r;
	}
}
