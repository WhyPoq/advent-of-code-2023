import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import javax.sound.midi.Soundbank;

import java.util.ArrayList;

class Task1{

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
            int[][] colors = new int[W][H];

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                }
            }
            String curStr = "";
            boolean readingNum = false;
            int curColor = 1;
            ArrayList<Integer> nums = new ArrayList<Integer>();
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    char curC = map[x][y];

                    if('0' <= curC && curC <= '9'){
                        curStr += curC;
                        readingNum = true;
                        colors[x][y] = curColor;
                    }
                    else{
                        if(curStr.length() > 0 && readingNum){
                            nums.add(Integer.parseInt(curStr));
                            curColor++;
                        }
                        curStr = "";
                        readingNum = false;
                    }


                }
                if(curStr.length() > 0 && readingNum){
                    nums.add(Integer.parseInt(curStr));
                    curColor++;
                }
                curStr = "";
                readingNum = false;
            }
            if(curStr.length() > 0 && readingNum){
                nums.add(Integer.parseInt(curStr));
                curColor++;
            }
            
            curStr = "";
            readingNum = false;

            int sum = 0;

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    HashSet<Integer> neightbors = new HashSet<Integer>();
                    int curV = 1;
                    if(map[x][y] == '*'){
                        for(int i = -1; i <= 1; i++){
                            for(int j = -1; j <= 1; j++){
                                if(x + i >= 0 && x + i < W && y + j >= 0 && y + j < H){
                                    int col = colors[x + i][y + j];
                                    if(col != 0 && !neightbors.contains(col)){
                                        neightbors.add(col);
                                        curV = curV * nums.get(col - 1);
                                    }
                                }
                            }
                        }
                    }
                    if(neightbors.size() == 2){
                        sum += curV;
                    }
                }
            }
            
            System.out.println(sum);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}