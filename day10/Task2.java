import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


enum Direction{
    north,
    south,
    east,
    west;

    public static Direction reverse(Direction dir){
        if(dir == Direction.north){
            return Direction.south;
        }
        else if(dir == Direction.south){
            return Direction.north;
        }
        else if(dir == Direction.east){
            return Direction.west;
        }
        else{
            return Direction.east;
        }
    }
}

class Coords{
    public int x;
    public int y;

    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coords add(Coords toAdd){
        return new Coords(this.x + toAdd.x, this.y + toAdd.y);
    }

    public Coords add(Direction dir){
        return add(Coords.DirToCoords(dir));
    }

    public boolean isInsideRect(int width, int height){
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    public String toString(){
        return "x: " + x + " y: " + y;
    }

    public static Coords DirToCoords(Direction dir){
        if(dir == Direction.north){
            return new Coords(0, -1);
        }
        else if(dir == Direction.south){
            return new Coords(0, 1);
        }
        else if(dir == Direction.east){
            return new Coords(1, 0);
        }
        else{
            return new Coords(-1, 0);
        }
    }
    
}

class Path{
    public ArrayList<PathVertex> vertices;
    PathVertex headVertex;

    public Path(Coords start, Direction dir){
        headVertex = new PathVertex(start.add(dir));
        headVertex.from = Direction.reverse(dir);

        vertices = new ArrayList<>();
        vertices.add(headVertex);
    }

    public void move(Direction dir){
        headVertex.to = dir;
        PathVertex movedPath = new PathVertex(headVertex.pos.add(dir));
        movedPath.from = Direction.reverse(headVertex.to);
        headVertex = movedPath;
        vertices.add(headVertex);
    }

    public void closeLoop(){
        headVertex.to = Direction.reverse(vertices.get(0).from);
    }

}

class PathVertex{
    public Coords pos;
    public Direction from;
    public Direction to;

    public PathVertex(Coords pos){
        this.pos = pos;
    }

    private char getTypeHelper(Direction f,  Direction t){
        if(f == Direction.east && t == Direction.west){
            return '-';
        }
        else if(f == Direction.east && t == Direction.north){
            return 'j';
        }
        else if(f == Direction.east && t == Direction.south){
            return '7';
        }
        else if(f == Direction.west && t == Direction.north){
            return 'L';
        }
        else if(f == Direction.west && t == Direction.south){
            return 'F';
        }
        else if(f == Direction.north && t == Direction.south){
            return '|';
        }
        return ' ';
    }

    public ArrayList<Coords> findRightAddsHelper(Direction from, Direction to){
        ArrayList<Coords> adds = new ArrayList<>();

        if(from == Direction.west && to == Direction.east){
            adds.add(new Coords(0, 1));
        }
        else if(from == Direction.east && to == Direction.west){
            adds.add(new Coords(0, -1));
        }
        else if(from == Direction.north && to == Direction.west){
            adds.add(new Coords(-1, -1));
        }
        else if(from == Direction.west && to == Direction.north){
            adds.add(new Coords(1, 0));
            adds.add(new Coords(1, 1));
            adds.add(new Coords(0, 1));
        }
        else if(from == Direction.west && to == Direction.south){
            adds.add(new Coords(-1, 1));
        }
        else if(from == Direction.south && to == Direction.west){
            adds.add(new Coords(1, 0));
            adds.add(new Coords(1, -1));
            adds.add(new Coords(0, -1));
        }
        else if(from == Direction.north && to == Direction.east){
            adds.add(new Coords(-1, 0));
            adds.add(new Coords(-1, 1));
            adds.add(new Coords(0, 1));
        }
        else if(from == Direction.east && to == Direction.north){
            adds.add(new Coords(1, -1));
        }
        else if(from == Direction.south && to == Direction.east){
            adds.add(new Coords(1, 1));
        }
        else if(from == Direction.east && to == Direction.south){
            adds.add(new Coords(0, -1));
            adds.add(new Coords(-1, -1));
            adds.add(new Coords(-1, 0));
        }
        else if(from == Direction.south && to == Direction.north){
            adds.add(new Coords(1, 0));
        }
        else if(from == Direction.north && to == Direction.south){
            adds.add(new Coords(-1, 0));
        }

        return adds;
    }

    public ArrayList<Coords> findRightAdds(){
        return findRightAddsHelper(from, to);
    }

    public ArrayList<Coords> findLeftAdds(){
        return findRightAddsHelper(to, from);
    }

}

class Task2{

    static int ans = 0;

    public static void bfs(Coords cur, int[][] visited, int W, int H){
        if(cur.isInsideRect(W, H)){
            if(visited[cur.x][cur.y] == 0){
                visited[cur.x][cur.y] = 1;
                ans++;

                ArrayList<Coords> dirs = new ArrayList<>();
                dirs.add(new Coords(0, 1));
                dirs.add(new Coords(1, 0));
                dirs.add(new Coords(0, -1));
                dirs.add(new Coords(-1, 0));

                for(int i = 0; i < dirs.size(); i++){
                    Coords to = cur.add(dirs.get(i));
                    bfs(to, visited, W, H);
                }
            }
        }
    }

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

            //filling the map and finding the start
            Coords startCoords = null;
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                    if(map[x][y] == 'S'){
                        startCoords = new Coords(x, y);
                    }
                }
            }


            Queue<Path> paths = new LinkedList<>();
            paths.add(new Path(startCoords, Direction.north));
            paths.add(new Path(startCoords, Direction.east));
            paths.add(new Path(startCoords, Direction.south));
            paths.add(new Path(startCoords, Direction.west));

            Path mainPath = null;
            while(mainPath == null){
                Path curPath = paths.remove();
                PathVertex curHead = curPath.headVertex;
                if(curHead.pos.isInsideRect(W, H)){
                    char curTile = map[curHead.pos.x][curHead.pos.y];
                    if(curTile == 'S'){
                        curPath.closeLoop();
                        mainPath = curPath;
                        System.out.println("looped");
                    }
                    else if(curTile == '|'){
                        if(curHead.from == Direction.south){
                            curPath.move(Direction.north);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.north){
                            curPath.move(Direction.south);
                            paths.add(curPath);
                        }
                    }
                    else if(curTile == '-'){
                        if(curHead.from == Direction.west){
                            curPath.move(Direction.east);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.east){
                            curPath.move(Direction.west);
                            paths.add(curPath);
                        }
                    }
                    else if(curTile == 'L'){
                        if(curHead.from == Direction.north){
                            curPath.move(Direction.east);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.east){
                            curPath.move(Direction.north);
                            paths.add(curPath);
                        }
                    }
                    else if(curTile == 'J'){
                        if(curHead.from == Direction.north){
                            curPath.move(Direction.west);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.west){
                            curPath.move(Direction.north);
                            paths.add(curPath);
                        }
                    }
                    else if(curTile == '7'){
                        if(curHead.from == Direction.west){
                            curPath.move(Direction.south);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.south){
                            curPath.move(Direction.west);
                            paths.add(curPath);
                        }
                    }
                    else if(curTile == 'F'){
                        if(curHead.from == Direction.east){
                            curPath.move(Direction.south);
                            paths.add(curPath);
                        }
                        else if(curHead.from == Direction.south){
                            curPath.move(Direction.east);
                            paths.add(curPath);
                        }
                    }
                    
                }
            }

            ArrayList<PathVertex> path = mainPath.vertices;

            int[][] visited = new int[W][H];

            //marking path vertices
            for(int i = 0; i < path.size(); i++){
                visited[path.get(i).pos.x][path.get(i).pos.y] = 2;
            }

            //seeking for right side
            for(int i = 0; i < path.size(); i++){
                PathVertex curVertex = path.get(i);

                ArrayList<Coords> adds = curVertex.findRightAdds();
                for(int k = 0; k < adds.size(); k++){
                    bfs(curVertex.pos.add(adds.get(k)), visited, W, H);
                }
            }

            System.out.println("To the right: " + ans);

            //reseting answer and visited map
            ans = 0;
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    visited[x][y] = 0;
                }
            }

            //marking path vertices after visited reset
            for(int i = 0; i < path.size(); i++){
                visited[path.get(i).pos.x][path.get(i).pos.y] = 2;
            }

            //seeking for left side
            for(int i = 0; i < path.size(); i++){
                PathVertex curVertex = path.get(i);

                ArrayList<Coords> adds = curVertex.findLeftAdds();
                for(int k = 0; k < adds.size(); k++){
                    bfs(curVertex.pos.add(adds.get(k)), visited, W, H);
                }
            }


            System.out.println("To the left: " + ans);


        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
