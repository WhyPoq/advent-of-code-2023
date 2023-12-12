import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task2{

    public static void main(String[] args){

        try{
            File inputFile = new File("day12_input.txt");
            Scanner input = new Scanner(inputFile);
          

            long sum = 0;

			while (input.hasNextLine()) {
				String line = input.nextLine();
                
                String mapInital = line.split(" ")[0];
                String map ="";

                final int cloneCount = 5;

                for(int i = 0; i < cloneCount; i++){
                    map += mapInital;
                    if(i != cloneCount - 1){
                        map += '?';
                    }
                }

                String numsStr = line.split(" ")[1];

                ArrayList<Integer> numsinitial = new ArrayList<>();
                for(String numStr : numsStr.split(",")){
                    numsinitial.add(Integer.parseInt(numStr));
                }

                ArrayList<Integer> nums = new ArrayList<>();
                for(int i = 0; i < cloneCount; i++){
                    for(int k = 0; k < numsinitial.size(); k++){
                        nums.add(numsinitial.get(k));
                    }   
                }


                //dp[i][k][m] how many ways there is to cover k nums while using the first i chars in map, m = 0 - the i - 1 th element was picked as a dot
                long dp[][][] = new long[map.length() + 1][nums.size() + 1][2];

                dp[0][0][0] = 1;

                for(int i = 1; i < map.length() + 1; i++){
                    for(int k = 0; k < nums.size() + 1; k++){
                        if(map.charAt(i - 1) == '.' || map.charAt(i - 1) == '?'){
                            dp[i][k][0] += dp[i - 1][k][0] + dp[i - 1][k][1];
                        }

                        //?
                        if(k != nums.size()){
                            if(map.charAt(i - 1) == '#' || map.charAt(i - 1) == '?'){
                                int curNum = nums.get(k);
                                boolean fits = i - 1 + curNum - 1 < map.length();
                                if(fits){
                                    for(int t = i; t < i + curNum; t++){
                                        if(map.charAt(t - 1) == '.'){
                                            fits = false;
                                            break;
                                        }
                                    }
                                }

                                if(fits){
                                    dp[i + curNum - 1][k + 1][1] += dp[i - 1][k][0];    
                                }
                            }
                        }
                    }
                }

                sum += dp[map.length()][nums.size()][0] + dp[map.length()][nums.size()][1];
                
			}
            input.close();

            System.out.println(sum); //28757034204

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
