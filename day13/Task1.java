import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task1{

    public static int processLines(ArrayList<String> prevLines){
        int W = prevLines.get(0).length();
        int H = prevLines.size();
        char[][] map = new char[W][H];

        for(int y = 0; y < H; y++){
            for(int x = 0; x < W; x++){
                map[x][y] = prevLines.get(y).charAt(x);
            }
        }
        prevLines.clear();

        int ySym = -1;
        for(int x = 1; x < W; x++){
            int i = 0;
            boolean symmetrical = true;
            while(x - i - 1 >= 0 && x + i < W && symmetrical){
                for(int y = 0; y < H; y++){
                    if(map[x - i - 1][y] != map[x + i][y]){
                        symmetrical = false;
                        break;
                    }
                }
                i++;
            }

            if(symmetrical){
                ySym = x;
                return ySym;
            }
        }

        int xSym = -1;
        for(int y = 1; y < H; y++){
            int i = 0;
            boolean symmetrical = true;
            while(y - i - 1 >= 0 && y + i < H && symmetrical){
                for(int x = 0; x < W; x++){
                    if(map[x][y - i - 1] != map[x][y + i]){
                        symmetrical = false;
                        break;
                    }
                }
                i++;
            }

            if(symmetrical){
                xSym = y;
                return xSym * 100;
            }
        }
       

        return -1;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day13_test.txt");
            Scanner input = new Scanner(inputFile);
          

            int sum = 0;

            ArrayList<String> prevLines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                
                if(line.length() > 0){
                    prevLines.add(line);
                }
                else if(prevLines.size() > 0){
                    sum += processLines(prevLines);
                }
			}
            input.close();

            sum += processLines(prevLines);

            System.out.println(sum);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
