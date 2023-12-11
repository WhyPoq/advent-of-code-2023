import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Point{

    public int x;
    public int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int distTo(Point p){
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }
}

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day11_input.txt");
            Scanner input = new Scanner(inputFile);
          
            ArrayList<String> lines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                lines.add(line);   
			}
            input.close();

            int W = lines.get(0).length();
            int H = lines.size();
            char[][] initialMap = new char[W][H];
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    initialMap[x][y] = lines.get(y).charAt(x);
                }
            }

            int emptyRows = 0;
            int emptyCols = 0;
            //finding empty rows number 
            for(int y = 0; y < H; y++){
                boolean emptyRow = true;
                for(int x = 0; x < W; x++){
                    if(initialMap[x][y] == '#'){
                        emptyRow = false;
                        break;
                    }
                }
                if(emptyRow){
                    emptyRows++;
                }
            }
            //finding empty empty cols number
            for(int x = 0; x < W; x++){
                boolean emptyCol = true;
                for(int y = 0; y < H; y++){
                    if(initialMap[x][y] == '#'){
                        emptyCol = false;
                        break;
                    }
                }
                if(emptyCol){
                    emptyCols++;
                }
            }

            char[][] expandedRowsMap = new char[W][H + emptyRows];
            for(int y = 0; y < H + emptyRows; y++){
                for(int x = 0; x < W; x++){
                    expandedRowsMap[x][y] = '.';
                }
            }

            //filling rows
            int expandedMapPtrRow = 0;
            for(int y = 0; y < H; y++){
                boolean emptyRow = true;
                for(int x = 0; x < W; x++){
                    if(initialMap[x][y] == '#'){
                        emptyRow = false;
                        break;
                    }
                }
                if(emptyRow){
                    expandedMapPtrRow++; //skipping the next row in the expanded map
                }
                else{
                    for(int x = 0; x < W; x++){
                        expandedRowsMap[x][expandedMapPtrRow] = initialMap[x][y];
                    }
                }
                expandedMapPtrRow++;
            }


            char[][] expandedMap = new char[W + emptyCols][H + emptyRows];
            for(int y = 0; y < H + emptyRows; y++){
                for(int x = 0; x < W + emptyCols; x++){
                    expandedMap[x][y] = '.';
                }
            }
            
            //filling columns
            int expandedMapPtrCol = 0;
            for(int x = 0; x < W; x++){
                boolean emptyCol = true;
                for(int y = 0; y < H + emptyRows; y++){
                    if(expandedRowsMap[x][y] == '#'){
                        emptyCol = false;
                        break;
                    }
                }
                if(emptyCol){
                    expandedMapPtrCol++; //skipping the next col in the expanded map
                }
                else{
                    for(int y = 0; y < H + emptyRows; y++){
                        expandedMap[expandedMapPtrCol][y] = expandedRowsMap[x][y];
                    }
                }
                expandedMapPtrCol++;
            }


            W = W + emptyCols;
            H = H + emptyRows;

            ArrayList<Point> points = new ArrayList<>();

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    if(expandedMap[x][y] == '#'){
                        points.add(new Point(x, y));
                    }
                }
            }

            int sumDists = 0;
            for(int i = 0; i < points.size(); i++){
                Point point1 = points.get(i);
                for(int k = i + 1; k < points.size(); k++){
                    Point point2 = points.get(k);
                    sumDists += point1.distTo(point2);
                }
            }

            System.out.println(sumDists);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
