import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


class Task2{

    public static void main(String[] args){

        try{
            File inputFile = new File("day9_input.txt");
            Scanner input = new Scanner(inputFile);
          
            int sum = 0;

			while (input.hasNextLine()) {
				String line = input.nextLine();

                ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
                String[] lineSplit = line.split(" ");

                ArrayList<Integer> firstRow = new ArrayList<>();
                for(int i = 0; i < lineSplit.length; i++){
                    firstRow.add(Integer.parseInt(lineSplit[i]));
                }
                rows.add(firstRow);

                boolean allZeros = false;
                while(!allZeros){
                    allZeros = true;

                    ArrayList<Integer> lastRow = rows.get(rows.size() - 1);
                    ArrayList<Integer> newRow = new ArrayList<>();

                    for(int i = 0; i < lastRow.size() - 1; i++){
                        int diff = lastRow.get(i + 1) - lastRow.get(i);
                        newRow.add(diff);

                        if(diff != 0){
                            allZeros = false;
                        }
                    }
                    rows.add(newRow);
                }

                //rows.get(rows.size() - 1).add(0);
                int prevVal = 0;
                for(int i = rows.size() - 1; i >= 0; i--){
                    ArrayList<Integer> curRow = rows.get(i);
                    prevVal = curRow.get(0) - prevVal;
                }
                sum += prevVal;
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
