import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;


class Node{
    public String left;
    public String right;
    public String name;

    public Node(String name, String left, String right){
        this.name = name;
        this.left = left;
        this.right = right;
    }
}

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day8_input.txt");
            Scanner input = new Scanner(inputFile);
          
            HashMap<String, Node> nameToNode = new HashMap<>();

            String instructions = input.nextLine();
            //skip empty line
            input.nextLine();

			while (input.hasNextLine()) {
				String line = input.nextLine();

                String nodeName = line.split(" = ")[0];

                String children = line.split(" = ")[1];
                String leftChild = children.split(", ")[0];
                leftChild = leftChild.substring(1);
                String rightChild = children.split(", ")[1];
                rightChild = rightChild.substring(0, rightChild.length() - 1);

                nameToNode.put(nodeName, new Node(nodeName, leftChild, rightChild));
			}
			input.close();

            int steps = 0;
            int instrInd = 0;
            Node curNode = nameToNode.get("AAA");
            while(!curNode.name.equals("ZZZ")){
                char curInstr = instructions.charAt(instrInd % instructions.length());
                if(curInstr == 'L'){
                    curNode = nameToNode.get(curNode.left);
                }
                else{
                    curNode = nameToNode.get(curNode.right);
                }
                instrInd++;
                steps++;
            }

            System.out.println(steps);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
