import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

enum Direction{
    up(0),
    right(1),
    down(2),
    left(3);

    private int value;
    private Direction(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}

class Tile{
    // minHeatLossDir[dir][stepsWithoutTurns]
    private int[][] minHeatLossDir;

    public Tile(){
        minHeatLossDir = new int[4][11];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 11; j++){
                minHeatLossDir[i][j] = -1;
            }
        }
    }

    // return true if successfully updated
    public boolean update(PathHead pathHead){
        Direction headDir = pathHead.dir;
        int curHeatLoss = minHeatLossDir[headDir.getValue()][pathHead.stepsWithoutTurns];
        if(curHeatLoss == -1 || curHeatLoss > pathHead.heatLoss){
            minHeatLossDir[headDir.getValue()][pathHead.stepsWithoutTurns] = pathHead.heatLoss;
            return true;
        }
        return false;
    }

    public int getMinHeatLoss(){
        int minHeatLoss = -1;

        for(int i = 0; i < 4; i++){
            for(int j = 4; j < 11; j++){
                if(minHeatLossDir[i][j] != -1){
                    if(minHeatLoss == -1 || minHeatLoss > minHeatLossDir[i][j]){
                        minHeatLoss = minHeatLossDir[i][j];
                    }
                }
            }
        }
        return minHeatLoss;
    }

}

class PathHead{
    public int x;
    public int y;
    public Direction dir;
    public int stepsWithoutTurns;
    public int heatLoss;

    public PathHead(int x, int y, Direction dir, int stepsWithoutTurns, int heatLoss){
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.stepsWithoutTurns = stepsWithoutTurns;
        this.heatLoss = heatLoss;
    }

    public void loseHeat(int[][] map){
        heatLoss += map[x][y];
    }

    public void move(){
        stepsWithoutTurns++;
        if(dir == Direction.up){
            y--;
        }
        else if(dir == Direction.right){
            x++;
        }
        else if(dir == Direction.down){
            y++;
        }
        else if(dir == Direction.left){
            x--;
        }
    }
}

class Task2{

    public static void main(String[] args){

        try{
            File inputFile = new File("day17_test.txt");
            Scanner input = new Scanner(inputFile);
          
            ArrayList<String> lines = new ArrayList<>();
            
            while (input.hasNextLine()) {
				String line = input.nextLine();
                
                lines.add(line);
			}
            input.close();

            final int W = lines.get(0).length();
            final int H = lines.size();
            int[][] map = new int[W][H];

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x) - '0';
                }
            }

            Tile visited[][] = new Tile[W][H];
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    visited[x][y] = new Tile();
                }
            }

            Queue<PathHead> pathHeads = new LinkedList<>();
            pathHeads.add(new PathHead(1, 0, Direction.right, 1, 0));
            pathHeads.add(new PathHead(0, 1, Direction.down, 1, 0));

            while(pathHeads.size() > 0){
                PathHead pathHead = pathHeads.remove();
                if(pathHead.x < 0 || pathHead.x >= W || pathHead.y < 0 || pathHead.y >= H){
                    continue;
                }

                pathHead.loseHeat(map);
                if(visited[pathHead.x][pathHead.y].update(pathHead)){
                    if(pathHead.stepsWithoutTurns >= 4){
                        if(pathHead.dir == Direction.right || pathHead.dir == Direction.left){
                            PathHead up = new PathHead(pathHead.x, pathHead.y, Direction.up, 0, pathHead.heatLoss);
                            PathHead down = new PathHead(pathHead.x, pathHead.y, Direction.down, 0, pathHead.heatLoss);
                            up.move();
                            down.move();
                            pathHeads.add(up);
                            pathHeads.add(down);
                        }
                        else if(pathHead.dir == Direction.up || pathHead.dir == Direction.down){
                            PathHead left = new PathHead(pathHead.x, pathHead.y, Direction.left, 0, pathHead.heatLoss);
                            PathHead right = new PathHead(pathHead.x, pathHead.y, Direction.right, 0, pathHead.heatLoss);
                            left.move();
                            right.move();
                            pathHeads.add(left);
                            pathHeads.add(right);
                        }
                    }

                    if(pathHead.stepsWithoutTurns < 10){
                        pathHead.move();
                        pathHeads.add(pathHead);
                    }
                }
            }

            System.out.println(visited[W - 1][H - 1].getMinHeatLoss());
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
