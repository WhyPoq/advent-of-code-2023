import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task1{

    public static int hash(String str){
        int curHash = 0;
        for(int i = 0; i < str.length(); i++){
            curHash = ((curHash + str.charAt(i)) * 17) % 256;
        }

        return curHash;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day15_input.txt");
            Scanner input = new Scanner(inputFile);
          

            String line = input.nextLine();
            input.close();
			
            String[] steps = line.split(",");

            int hashSum = 0;
            for(String step : steps){
                hashSum += hash(step);
            }

            System.out.println(hashSum);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
