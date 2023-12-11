import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Point{

    public long x;
    public long y;

    public Point(long x, long y){
        this.x = x;
        this.y = y;
    }

    public long distTo(Point p){
        return Math.abs(x - p.x) + Math.abs(y - p.y);
    }
}

class Task2{

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

            final long scale = 1000000;

            int W = lines.get(0).length();
            int H = lines.size();
            char[][] map = new char[W][H];
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                }
            }

            Point[][] pointsMap = new Point[W][H];
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    pointsMap[x][y] = new Point(x, y);
                }
            }

            long emptyRows = 0;
            //deal with rows
            for(int y = 0; y < H; y++){
                boolean emptyRow = true;
                for(int x = 0; x < W; x++){
                    if(map[x][y] == '#'){
                        emptyRow = false;
                        break;
                    }
                }
                if(emptyRow){
                    emptyRows++;
                }
                else{
                    for(int x = 0; x < W; x++){
                        pointsMap[x][y].y += emptyRows * scale - emptyRows;
                    }
                }
            }

            //deal with columns
            long emptyCols = 0;
            for(int x = 0; x < W; x++){
                boolean emptyCol = true;
                for(int y = 0; y < H; y++){
                    if(map[x][y] == '#'){
                        emptyCol = false;
                        break;
                    }
                }
                if(emptyCol){
                    emptyCols++;
                }
                else{
                    for(int y = 0; y < H; y++){
                        pointsMap[x][y].x += emptyCols * scale - emptyCols;
                    }
                }
            }

            ArrayList<Point> points = new ArrayList<>();

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    if(map[x][y] == '#'){
                        points.add(pointsMap[x][y]);
                    }
                }
            }

            long sumDists = 0;
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
