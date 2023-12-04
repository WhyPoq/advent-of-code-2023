import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


class Task1{

    public static void main(String[] args){

        HashMap<String, Integer> colorLimits = new HashMap<String, Integer>();
        colorLimits.put("red", 12);
        colorLimits.put("green", 13);
        colorLimits.put("blue", 14);

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
                boolean good = true;
                for(int i = 0; i < rounds.length; i++){
                    String[] items = rounds[i].split(", ");

                    for(int j = 0; j < items.length; j++){
                        int curBallsCount = Integer.parseInt(items[j].split(" ")[0]);
                        String colorName = items[j].split(" ")[1];
                        if(colorLimits.get(colorName) < curBallsCount){
                            good = false;
                            break;
                        }
                        
                    }
                }

                if(good){
                    answer += curGame;
                }
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