import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

class HuffmanNode {
  char key;
  int value;

  HuffmanNode left;
  HuffmanNode right;
  
  public HuffmanNode(char p, int q) {
    this.key = p;
    this.value = q;
    left = null;
    right = null;
  }

  public char getKey() {
    return key;
  }

  public int getValue() {
    return value;
  }
}

public class Compress {

    public static HashMap<Character, String> huffmanCodes = new HashMap<>();
    
        public static void printCodes(HuffmanNode root, StringBuilder code) {
          
           if(root == null) return;  
    
           if(root.key != '$') {
               huffmanCodes.put(root.key, code.toString());
           }
    
           if(root.left != null) {
            printCodes(root.left, code.append('0'));
            code.deleteCharAt(code.length()-1);
           }
    
           if(root.right != null) {
            printCodes(root.right, code.append('1'));
            code.deleteCharAt(code.length()-1);
           }
        }
    
        public static void main(String[] args) throws IOException {
            System.out.println("Welcome to Compression Tool");
    
            String inputFile = "input.txt";
            StringBuilder inputsb = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                String line;
                while ((line = br.readLine()) != null) {
                    inputsb.append(line);
                } 
                br.close();
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
    
            String s = inputsb.toString();
          
            PriorityQueue<HuffmanNode> pq = new PriorityQueue<>((a,b) -> a.getValue() - b.getValue());
            HashMap<Character, Integer> hmap = new HashMap<>();
    
            for(Character c : s.toCharArray()) {
              hmap.put(c, hmap.getOrDefault(c, 0)+1);
            }
    
            for(Map.Entry<Character, Integer> entry : hmap.entrySet()) {
              HuffmanNode pair = new HuffmanNode(entry.getKey(), entry.getValue());
              pq.add(pair);
            }
    
            //encoding
            while(pq.size() > 1) {
              HuffmanNode left = pq.poll();
              HuffmanNode right = pq.poll();
    
              HuffmanNode newNode = new HuffmanNode('$', left.getValue() + right.getValue());
    
              newNode.left = left;
              newNode.right = right;
              pq.add(newNode);
            }
    
            HuffmanNode root = pq.poll();
            System.out.println("Huffman Codes ");
            printCodes(root, new StringBuilder());

            StringBuilder compressedString = new StringBuilder();
            for(char c : s.toString().toCharArray()) {
                 compressedString.append(huffmanCodes.get(c));
            }
        
            try(FileOutputStream fos = new FileOutputStream("output.bin")) {
              BitSet bitSet = new BitSet(compressedString.length());
              for(int i = 0; i<compressedString.length(); i++) {
                  if(compressedString.charAt(i) == '1') {
                    bitSet.set(i);
                  }
              }

              fos.write(bitSet.toByteArray());
            }

        System.out.println("Thanks for using our tool!");
    }

}