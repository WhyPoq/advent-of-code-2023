import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;



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
                    System.out.println(nodeName);
                }
			}
			input.close();

            int steps = 0;
            int instrInd = 0;
            boolean finished = false;
            while(!finished){
                finished = true;
                char curInstr = instructions.charAt(instrInd % instructions.length());
                for(int i = 0; i < curNodes.size(); i++){
                    if(curInstr == 'L'){
                        curNodes.set(i, nameToNode.get(curNodes.get(i).left));
                    }
                    else{
                        curNodes.set(i, nameToNode.get(curNodes.get(i).right));
                    }

                    if(!curNodes.get(i).finish){
                        finished = false;
                    }
                
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
