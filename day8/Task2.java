import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;



class Node{
    public String left;
    public String right;
    public String name;
    public boolean finish = false;

    public Node(String name, String left, String right){
        this.name = name;
        this.left = left;
        this.right = right;

        if(name.charAt(name.length() - 1) == 'Z'){
            finish = true;
        }
    }
}

class Task2{

    //copied form https://www.baeldung.com/java-least-common-multiple
    public static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day8_input.txt");
            Scanner input = new Scanner(inputFile);
          
            HashMap<String, Node> nameToNode = new HashMap<>();

            String instructions = input.nextLine();
            //skip empty line
            input.nextLine();

            ArrayList<Node> curNodes = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();

                String nodeName = line.split(" = ")[0];

                String children = line.split(" = ")[1];
                String leftChild = children.split(", ")[0];
                leftChild = leftChild.substring(1);
                String rightChild = children.split(", ")[1];
                rightChild = rightChild.substring(0, rightChild.length() - 1);

                Node node = new Node(nodeName, leftChild, rightChild);
                nameToNode.put(nodeName, node);

                //found a starting node
                if(nodeName.charAt(nodeName.length() - 1) == 'A'){
                    curNodes.add(node);
                }
			}
			input.close();

            long ans = 1;
            for(int i = 0; i < curNodes.size(); i++){
                int steps = 0;
                int instrInd = 0;
                Node curNode = curNodes.get(i);
                HashMap<String, Integer> visited = new HashMap<String, Integer>();
                boolean looped = false;
                
                while(!looped){
                    visited.put(curNode.name + instrInd, steps);
                    if(instructions.charAt(instrInd) == 'L'){
                        curNode = nameToNode.get(curNode.left);
                    }
                    else{
                        curNode = nameToNode.get(curNode.right);
                    }
                    steps++;
                    instrInd = (instrInd + 1) % instructions.length();

                    if(visited.containsKey(curNode.name + instrInd) && curNode.finish){
                        looped = true;
                        int loopLen = steps - visited.get(curNode.name + instrInd);
                        System.out.println(curNode.name + instrInd + " : " + loopLen);
                        ans = lcm(ans, loopLen);
                    }
                }
            }

            System.out.println(ans);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
