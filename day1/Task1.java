import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day1_input.txt");
            Scanner input = new Scanner(inputFile);
          
            int sum = 0;
            while (input.hasNextLine()) {
                String line = input.nextLine();
                int first = -1;
                int last = 0;

                for(int i = 0; i < line.length(); i++){
                    int val = line.charAt(i) - '0';
                    if(0 <= val && val <= 9){
                        if(first == -1){
                            first = val;
                        }

                        last = val;
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
