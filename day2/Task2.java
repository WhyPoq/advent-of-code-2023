import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


class Task2{

    public static void main(String[] args){

        try{
            int answer = 0;

            File inputFile = new File("day2.txt");
            Scanner input = new Scanner(inputFile);
          
            int curGame = 1;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] gameSplit = line.split(": ");
                StringBuilder sb = new StringBuilder();
                for(int i = 1; i < gameSplit.length; i++){
                    sb.append(gameSplit[i]);
                }
                line = sb.toString();

                String[] rounds = line.split("; ");
                
                HashMap<String, Integer> curMinColors = new HashMap<String, Integer>();
                curMinColors.put("red", 0);
                curMinColors.put("green", 0);
                curMinColors.put("blue", 0);

                for(int i = 0; i < rounds.length; i++){
                    String[] items = rounds[i].split(", ");

                    for(int j = 0; j < items.length; j++){
                        int curBallsCount = Integer.parseInt(items[j].split(" ")[0]);
                        String colorName = items[j].split(" ")[1];
                        if(curMinColors.get(colorName) < curBallsCount){
                            curMinColors.put(colorName, curBallsCount);
                        }
                        
                    }
                }
                int gamePower = 1;
                for (int colorCount : curMinColors.values()) {
                    gamePower = gamePower * colorCount;
                }
                answer += gamePower;

                curGame++;
            }

            System.out.println(answer);
            input.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}