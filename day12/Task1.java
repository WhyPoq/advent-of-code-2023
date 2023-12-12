import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day12_input.txt");
            Scanner input = new Scanner(inputFile);
          

            long sum = 0;

			while (input.hasNextLine()) {
				String line = input.nextLine();
                
                String map = line.split(" ")[0];
                String numsStr = line.split(" ")[1];

                ArrayList<Integer> nums = new ArrayList<>();
                for(String numStr : numsStr.split(",")){
                    nums.add(Integer.parseInt(numStr));
                }

                int questionCount = 0;
                for(int i = 0; i < map.length(); i++){
                    if(map.charAt((i)) == '?'){
                        questionCount++;
                    }
                }

                long statesCount = 1 << questionCount;
                for(long state = 0; state < statesCount; state++){
                    long curState = state;
                    int curLen = 0;
                    boolean fits = true;
                    int curNum = 0;
                    for(int i = 0; i < map.length(); i++){
                        char curChar = '.';
                        if(map.charAt(i) == '#'){
                            curChar = '#';
                        }

                        if(map.charAt(i) == '?'){
                            if((curState & 1) == 1){
                                curChar = '#';
                            }
                            else{
                                curChar = '.';
                            }
                            curState = curState >> 1;
                        }

                        if(curChar == '#'){
                            curLen++;
                        }
                        else if(curLen > 0){
                            if(curNum < nums.size() && nums.get(curNum) == curLen){
                                curLen = 0;
                                curNum++;
                            }
                            else{
                                fits = false;
                                break;
                            }
                        }
                    }

                    if(curLen > 0){
                        if(curNum < nums.size() && nums.get(curNum) == curLen){
                            curLen = 0;
                            curNum++;
                        }
                        else{
                            fits = false;
                        }
                    }

                    if(fits && curNum == nums.size()){
                        sum++;
                    }
                }
			}
            input.close();

            System.out.println(sum);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
