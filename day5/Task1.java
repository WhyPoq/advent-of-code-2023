import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;



class Task1{

    public static void main(String[] args){
        try{
            File inputFile = new File("day5_input.txt");
            Scanner input = new Scanner(inputFile);

            ArrayList<String> inputLines = new ArrayList<>();

            while (input.hasNextLine()) {
                String line = input.nextLine();
                
                inputLines.add(line);
            }

            String[] seedsStr = inputLines.get(0).split(": ")[1].split(" ");

            long[] curNums = new long[seedsStr.length];
            long[] newNums = new long[seedsStr.length];

            for(int i = 0; i < seedsStr.length; i++){
                curNums[i] = Long.parseLong(seedsStr[i]);
            }

            for(int i = 3; i < inputLines.size(); i++){
                String line = inputLines.get(i);

                if(line.length() == 0){

                    for(int k = 0; k < curNums.length; k++){
                        if(curNums[k] != -1){
                            newNums[k] = curNums[k];
                        }
                    }

                    for(int k = 0; k < curNums.length; k++){
                        curNums[k] = newNums[k];
                    }

                    i++; //skip the map heading
                }
                else{
                    String[] numsSplit = line.split(" ");
                    long destStart = Long.parseLong(numsSplit[0]);
                    long sourceStart = Long.parseLong(numsSplit[1]);
                    long range = Long.parseLong(numsSplit[2]);

                    for(int k = 0; k < curNums.length; k++){
                        if(sourceStart <= curNums[k] && curNums[k] < sourceStart + range){
                            newNums[k] = destStart + curNums[k] - sourceStart;
                            curNums[k] = -1;
                        }
                    }
                }
                
            }

            for(int k = 0; k < curNums.length; k++){
                if(curNums[k] != -1){
                    newNums[k] = curNums[k];
                }
            }

            for(int k = 0; k < curNums.length; k++){
                curNums[k] = newNums[k];
            }

            long lowest = curNums[0];
            for(int i = 1; i < curNums.length; i++){
                if(curNums[i] < lowest){
                    lowest = curNums[i];
                }
            }

            System.out.println(lowest);

            input.close();

        
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}