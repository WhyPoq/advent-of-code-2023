import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

import java.util.ArrayList;

class Task2{

    public static void main(String[] args){
        try{
            ArrayList<String> lines = new ArrayList<String>();

            File inputFile = new File("day3_input.txt");
            Scanner input = new Scanner(inputFile);
          
            while (input.hasNextLine()) {
                String line = input.nextLine();
                lines.add(line);
            }
            input.close();

            int W = lines.get(0).length();
            int H = lines.size();

            char[][] map = new char[W][H];
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                }
            }

            int sum = 0;
            String curStr = "";
            boolean isNear = false;
            boolean readingNum = false;

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    char curC = map[x][y];

                    if('0' <= curC && curC <= '9'){
                        curStr += curC;
                        readingNum = true;
                    }
                    else{
                        if(curStr.length() > 0 && isNear && readingNum){
                            sum += Integer.parseInt(curStr);
                        }
                        curStr = "";
                        isNear = false;
                        readingNum = false;
                    }
                    
                    if(readingNum && !isNear){
                        for(int i = -1; i <= 1; i++){
                            for(int j = -1; j <= 1; j++){
                                if(x + i >= 0 && x + i < W && y + j >= 0 && y + j < H){
                                    char testC = map[x + i][y + j];
                                    if(testC != '.' && !('0' <= testC && testC <= '9')){
                                        isNear = true;
                                    }
                                }
                            }
                        }
                    }

                }
                if(curStr.length() > 0 && isNear && readingNum){
                    sum += Integer.parseInt(curStr);
                }
                curStr = "";
                isNear = false;
                readingNum = false;
            }
            if(curStr.length() > 0 && isNear && readingNum){
                sum += Integer.parseInt(curStr);
            }
            
            curStr = "";
            isNear = false;
            readingNum = false;

            System.out.println(sum);

        
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}