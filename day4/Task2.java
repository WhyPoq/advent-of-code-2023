import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.ArrayList;


class Task2{

    public static void main(String[] args){
        try{

            File inputFile = new File("day4_input.txt");
            Scanner input = new Scanner(inputFile);
          
            ArrayList<String> inputLines = new ArrayList<String>();
            while (input.hasNextLine()) {
                String line = input.nextLine();
                inputLines.add(line);
            }

            int[] cardCopies = new int[inputLines.size()]; 

            int cardsCount = 0;

            for(int i = 0; i < inputLines.size(); i++){
                String line = inputLines.get(i);
                cardCopies[i] += 1;
                cardsCount += cardCopies[i];

                line = line.split(": ")[1];
                String winningPart = line.split(Pattern.quote(" | "))[0];
                String hasPart = line.split(Pattern.quote(" | "))[1];

                HashSet<Integer> winningNums = new HashSet<Integer>();
                for(String nStr : winningPart.split(" ")){
                    if(nStr.length() > 0){
                        int curNum = Integer.parseInt(nStr);
                        winningNums.add(curNum);
                    }
                }

                int matchingCount = 0;
                for(String nStr : hasPart.split(" ")){
                    if(nStr.length() > 0){
                        int curNum = Integer.parseInt(nStr);
                        if(winningNums.contains(curNum)){
                            matchingCount++;
                        }
                    }
                }

                for(int k = 0; k < matchingCount; k++){
                    cardCopies[i + 1 + k] += cardCopies[i];
                }

            }

            System.out.println(cardsCount);

            input.close();

        
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}