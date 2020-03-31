import java.util.*;
import java.util.Map.Entry;

public class HuffmanHeap {
    private HuffmanNode[] Heap; 
    private int size; 
  
    private static final int FRONT = 0; 
  
    public HuffmanHeap(HashMap<Byte,Integer> h) 
    { 
        int n = h.entrySet().size();
        size = 0;
        Set<Entry<Byte,Integer>> set = h.entrySet();
        Heap = new HuffmanNode[n];
        for(Entry<Byte,Integer> entry : set) {
        	HuffmanNode p = new HuffmanNode(entry.getKey(),entry.getValue());
        	Heap[size++] = p;
        }
        minHeap();
    } 
  
    public HuffmanTree constructTree(){
    	while(size >1) {
    		HuffmanNode n1 = remove();
    		HuffmanNode n2 = remove();
    		HuffmanNode n3 = new HuffmanNode(n1.freq+n2.freq , n1 , n2);
    		insert(n3);
    	}
    	return new HuffmanTree(remove());
    }
    private int parent(int pos) 
    { 
    	 if (pos % 2 == 1) {
             return pos / 2;
         }

         return (pos - 1) / 2;
    } 
  

    private int leftChild(int pos) 
    { 
        return (2 * pos)+1; 
    } 
  

    private int rightChild(int pos) 
    { 
        return (2 * pos) + 2; 
    } 

    private boolean isLeaf(int pos) 
    { 
        if (pos >= (int)(size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
  

    private void swap(int fpos, int spos) 
    { 
        HuffmanNode tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
  

    private void minHeapify(int pos) 
    { 
  
        if (!isLeaf(pos)) { 
            if (Heap[pos].freq > Heap[leftChild(pos)].freq 
                || Heap[pos].freq > Heap[rightChild(pos)].freq) { 
  
                if (Heap[leftChild(pos)].freq < Heap[rightChild(pos)].freq) { 
                    swap(pos, leftChild(pos)); 
                    minHeapify(leftChild(pos)); 
                }else { 
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
            } 
        } 
    } 
  
    public void insert(HuffmanNode element) 
    { 
        Heap[size++] = element; 
        int current = size-1; 
  
        while (Heap[current].freq < Heap[parent(current)].freq) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } 
  

    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + Heap[i] 
                             + " LEFT CHILD : " + Heap[2 * i] 
                             + " RIGHT CHILD :" + Heap[2 * i + 1]); 
            System.out.println(); 
        } 
    } 
  
    public void minHeap() 
    { 
        for (int pos = (size / 2)-1; pos >= 0; pos--) { 
            minHeapify(pos); 
        } 
    } 
  

    public HuffmanNode remove() 
    { 
        HuffmanNode popped = Heap[FRONT]; 
        Heap[FRONT] = Heap[--size]; 
        minHeapify(FRONT); 
        return popped; 
    } 

}
