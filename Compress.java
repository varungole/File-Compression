import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    public static void printCodes(HuffmanNode root, StringBuilder code, StringBuilder finalCode) {
       if(root == null) return;  

       if(root.key != '$') {
           finalCode.append(code);
       }

       if(root.left != null) {
        printCodes(root.left, code.append('0'), finalCode);
        code.deleteCharAt(code.length()-1);
       }

       if(root.right != null) {
        printCodes(root.right, code.append('1'), finalCode);
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
        StringBuilder finalCode = new StringBuilder();
        printCodes(root, new StringBuilder(), finalCode);

        System.out.println("Compressed file is created in the same directory titled input.txt");

        File newFile = new File("output.txt");
        BufferedWriter writer = null;
        try {
          writer = new BufferedWriter(new FileWriter(newFile));
          writer.append(finalCode);
        } finally {
          if(writer != null) {
            writer.close();
          }
        }


        System.out.println("Thanks for using our tool!");
    }

}