import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day14_test.txt");
            Scanner input = new Scanner(inputFile);
          

            ArrayList<String> lines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                lines.add(line);
			}
            input.close();

            int W = lines.get(0).length();
            int H = lines.size();

            char map[][] = new char[W][H];

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                }
            }

            int loadSum = 0;
            for(int x = 0; x < W; x++){
                int freePlace = 0;
                for(int y = 0; y < H; y++){
                    if(map[x][y] == '#'){
                        freePlace = y + 1;
                    }
                    else if(map[x][y] == 'O'){
                        loadSum += H - freePlace;
                        freePlace++;
                    }
                }
            }

            System.out.println(loadSum);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
