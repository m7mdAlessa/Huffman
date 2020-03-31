import java.util.HashMap;
import java.util.Map;

public class HuffmanTree {
	HuffmanNode root;
	public HuffmanTree(HuffmanNode root) {
		this.root = root;
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
}
