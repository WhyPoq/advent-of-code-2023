import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Task2{

    public static void main(String[] args){

        String[] digitNames = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};


        try{
            File inputFile = new File("day1_input.txt");
            Scanner input = new Scanner(inputFile);
          
            int sum = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                int first = 0;
                int firstInd = -1;
                int last = 0;
                int lastInd = -1;

                for(int i = 0; i < digitNames.length; i++){
                    int curFirstInd = line.indexOf(digitNames[i]);
                    if(curFirstInd != -1 && (firstInd == -1 || firstInd > curFirstInd)){
                        firstInd = curFirstInd;
                        first = i + 1;
                    }

                    int curLastInd = line.lastIndexOf(digitNames[i]);
                    if(curLastInd != -1 && (lastInd == -1 || lastInd < curLastInd)){
                        lastInd = curLastInd;
                        last = i + 1;
                    }

                }

                for(int i = 0; i < line.length(); i++){
                    int val = line.charAt(i) - '0';
                    if(0 <= val && val <= 9){
                        if(firstInd == -1 || firstInd > i){
                            first = val;
                            firstInd = i;
                        }

                        if(lastInd == -1 || lastInd < i){
                            last = val;
                            lastInd = i;
                        }
                    }
                }
                int lineValue = first * 10 + last;
                sum += lineValue;
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
