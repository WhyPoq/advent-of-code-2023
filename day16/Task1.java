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
    private boolean[] occupiedDirs;
    private boolean hasLight;

    public Tile(){
        occupiedDirs = new boolean[4];
        hasLight = false;
    }

    public boolean hasDirection(Direction dir){
        return occupiedDirs[dir.getValue()];
    }

    public void addDirection(Direction dir){
        occupiedDirs[dir.getValue()] = true;
        hasLight = true;
    }

    public boolean getHasLight(){
        return hasLight;
    }
}

class LightHead{
    public int x;
    public int y;
    public Direction dir;

    public LightHead(int x, int y, Direction dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void move(){
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

class Task1{

    public static int startRay(LightHead startHead, final int W, final int H, char[][] map){
        Tile visited[][] = new Tile[W][H];
        for(int y = 0; y < H; y++){
            for(int x = 0; x < W; x++){
                visited[x][y] = new Tile();
            }
        }

        Queue<LightHead> lightHeads = new LinkedList<>();
        lightHeads.add(startHead);

        while(lightHeads.size() > 0){
            LightHead lightHead = lightHeads.remove();
            if(lightHead.x < 0 || lightHead.x >= W || lightHead.y < 0 || lightHead.y >= H){
                continue;
            }

            if(!visited[lightHead.x][lightHead.y].hasDirection(lightHead.dir)){
                visited[lightHead.x][lightHead.y].addDirection(lightHead.dir);

                char mapTile = map[lightHead.x][lightHead.y];
                if(mapTile == '/'){
                    if(lightHead.dir == Direction.right){
                        lightHead.dir = Direction.up;
                    }
                    else if(lightHead.dir == Direction.left){
                        lightHead.dir = Direction.down;
                    }
                    else if(lightHead.dir == Direction.up){
                        lightHead.dir = Direction.right;
                    }
                    else if(lightHead.dir == Direction.down){
                        lightHead.dir = Direction.left;
                    }
                }
                else if(mapTile == '\\'){
                    if(lightHead.dir == Direction.right){
                        lightHead.dir = Direction.down;
                    }
                    else if(lightHead.dir == Direction.left){
                        lightHead.dir = Direction.up;
                    }
                    else if(lightHead.dir == Direction.up){
                        lightHead.dir = Direction.left;
                    }
                    else if(lightHead.dir == Direction.down){
                        lightHead.dir = Direction.right;
                    }
                }
                else if(mapTile == '|'){
                    if(lightHead.dir == Direction.right || lightHead.dir == Direction.left){
                        lightHead.dir = Direction.down;
                        LightHead doubledHead = new LightHead(lightHead.x, lightHead.y, Direction.up);
                        lightHeads.add(doubledHead);
                        doubledHead.move();
                    }
                }
                else if(mapTile == '-'){
                    if(lightHead.dir == Direction.up || lightHead.dir == Direction.down){
                        lightHead.dir = Direction.left;
                        LightHead doubledHead = new LightHead(lightHead.x, lightHead.y, Direction.right);
                        lightHeads.add(doubledHead);
                        doubledHead.move();
                    }
                }

                lightHead.move();
                lightHeads.add(lightHead);
            }
        }

        int energy = 0;

        for(int y = 0; y < H; y++){
            for(int x = 0; x < W; x++){
                if(visited[x][y].getHasLight()){
                    energy++;
                }
            }
        }   

        return energy;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day16_input.txt");
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

            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    map[x][y] = lines.get(y).charAt(x);
                }
            }

            int maxEnergy = 0;
            // from top side
            for(int x = 0; x < W; x++){
                int curEneergy = startRay(new LightHead(x, 0, Direction.down), W, H, map);
                if(curEneergy > maxEnergy){
                    maxEnergy = curEneergy;
                }
            } 

            // from bottom side
            for(int x = 0; x < W; x++){
                int curEneergy = startRay(new LightHead(x, H - 1, Direction.up), W, H, map);
                if(curEneergy > maxEnergy){
                    maxEnergy = curEneergy;
                }
            } 

            // from left side
            for(int y = 0; y < H; y++){
                int curEneergy = startRay(new LightHead(0, y, Direction.right), W, H, map);
                if(curEneergy > maxEnergy){
                    maxEnergy = curEneergy;
                }
            } 

            // from right side
            for(int y = 0; y < H; y++){
                int curEneergy = startRay(new LightHead(W - 1, y, Direction.right), W, H, map);
                if(curEneergy > maxEnergy){
                    maxEnergy = curEneergy;
                }
            } 

            System.out.println(maxEnergy);
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
