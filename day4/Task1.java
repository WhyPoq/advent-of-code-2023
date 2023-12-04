import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashSet;


class Task1{

    public static void main(String[] args){
        try{

            int sum = 0;

            File inputFile = new File("day4_input.txt");
            Scanner input = new Scanner(inputFile);

            while (input.hasNextLine()) {
                String line = input.nextLine();
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

                int cardScore = 0;
                for(String nStr : hasPart.split(" ")){
                    if(nStr.length() > 0){
                        int curNum = Integer.parseInt(nStr);
                        if(winningNums.contains(curNum)){
                            if(cardScore == 0){
                                cardScore = 1;
                            }
                            else{
                                cardScore = cardScore * 2;
                            }
                        }
                    }
                }

                sum += cardScore;
            }

            System.out.println(sum);

            input.close();

        
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}