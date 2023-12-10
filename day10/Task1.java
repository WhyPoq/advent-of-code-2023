import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


enum Direction{
    north,
    south,
    east,
    west
}

class Pos{
    public int x;
    public int y;
    ArrayList<Pos> trail;
    Direction dir = Direction.north;

    public Pos(int x, int y, ArrayList<Pos> trail){
        this.x = x;
        this.y = y;
        this.trail = trail;
    }

    public Pos add(Direction dir){
        trail.add(this);
        Pos nextPos = new Pos(x, y, trail);
        nextPos.dir = dir;

        if(dir == Direction.north){
            nextPos.y += -1;
        }
        else if(dir == Direction.south){
            nextPos.y += 1;
        }
        else if(dir == Direction.east){
            nextPos.x += + 1;
        }
        else if(dir == Direction.west){
            nextPos.x += -1;
        }

        return nextPos;
    }

    public Pos add(Direction dir, Pos startPos){
        ArrayList<Pos> trail = new ArrayList<>();
        //trail.add(startPos);

        trail.add(this);
        Pos nextPos = new Pos(x, y, trail);
        nextPos.dir = dir;

        if(dir == Direction.north){
            nextPos.y += -1;
        }
        else if(dir == Direction.south){
            nextPos.y += 1;
        }
        else if(dir == Direction.east){
            nextPos.x += 1;
        }
        else if(dir == Direction.west){
            nextPos.x += -1;
        }

        return nextPos;
    }

    public String toString(){
        return x + " : " + y;
    }
}

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day10_input.txt");
            Scanner input = new Scanner(inputFile);
          
            ArrayList<String> lines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                lines.add(line);   
			}
            input.close();

            final int W = lines.get(0).length();
            final int H = lines.size();
            char[][] map = new char[W][H];
            Pos sPos = null;
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                    if(map[x][y] == 'S'){
                        sPos = new Pos(x, y, null);
                    }
                }
            }

            Queue<Pos> curPoses = new LinkedList<>();
            if(sPos != null){
                curPoses.add(sPos.add(Direction.north, sPos));
                curPoses.add(sPos.add(Direction.east, sPos));
                curPoses.add(sPos.add(Direction.south,sPos));
                curPoses.add(sPos.add(Direction.west, sPos));
            }

            boolean looped = false;
            ArrayList<Pos> foundLoop = null;
            while(curPoses.size() > 0 && !looped){
                Pos curPos = curPoses.remove();
                if(curPos.x >= 0 && curPos.y >= 0 && curPos.x < W && curPos.y < H){
                    char curTile = map[curPos.x][curPos.y];
                    if(curTile == 'S'){
                        looped = true;
                        System.out.println("looped");
                        foundLoop = curPos.trail;
                    }
                    else if(curTile == '|'){
                        if(curPos.dir == Direction.north){
                            curPoses.add(curPos.add(Direction.north));
                        }
                        else if(curPos.dir == Direction.south){
                            curPoses.add(curPos.add(Direction.south));
                        }
                    }
                    else if(curTile == '-'){
                        if(curPos.dir == Direction.west){
                            curPoses.add(curPos.add(Direction.west));
                        }
                        else if(curPos.dir == Direction.east){
                            curPoses.add(curPos.add(Direction.east));
                        }
                    }
                    else if(curTile == 'L'){
                        if(curPos.dir == Direction.south){
                            curPoses.add(curPos.add(Direction.east));
                        }
                        else if(curPos.dir == Direction.west){
                            curPoses.add(curPos.add(Direction.north));
                        }
                    }
                    else if(curTile == 'J'){
                        if(curPos.dir == Direction.south){
                            curPoses.add(curPos.add(Direction.west));
                        }
                        else if(curPos.dir == Direction.east){
                            curPoses.add(curPos.add(Direction.north));
                        }
                    }
                    else if(curTile == '7'){
                        if(curPos.dir == Direction.east){
                            curPoses.add(curPos.add(Direction.south));
                        }
                        else if(curPos.dir == Direction.north){
                            curPoses.add(curPos.add(Direction.west));
                        }
                    }
                    else if(curTile == 'F'){
                        if(curPos.dir == Direction.west){
                            curPoses.add(curPos.add(Direction.south));
                        }
                        else if(curPos.dir == Direction.north){
                            curPoses.add(curPos.add(Direction.east));
                        }
                    }
                    
                }
            }

            if(foundLoop != null){
                // System.out.println("Loop:");
                // for(int i = 0; i < foundLoop.size(); i++){
                //     System.out.println(foundLoop.get(i).toString());
                // }
                System.out.println(foundLoop.size() / 2);
            }
            else{
                System.out.println("Loop was not found");
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
