import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

enum Dir{
    north(0),
    west(1),
    south(2),
    east(3);

    private int val;
    private Dir(int val){
        this.val = val;
    }

    public int getValue(){
        return val;
    }
}

class MapObj{
    private char type;
    public MapObj(char type){
        this.type = type;
    }

    public char getType(){
        return type;
    }
}

class RoundBall extends MapObj{
    public int[] hasPoses;
    int sameCount = 0;

    public RoundBall(char type){
        super(type);
        hasPoses = new int[4];
        for(int i = 0; i < 4; i++){
            hasPoses[i] = -1;
        }
    }

    public boolean move(int newPos, Dir dir){
        //System.out.println(sameCount);
        if(hasPoses[dir.getValue()] == newPos){
            sameCount++;
            if(sameCount >= 4){
                return true;
            }
        }
        else{
            hasPoses[dir.getValue()] = newPos;
            sameCount = 0;
        }
        return false;  
    }
}

class Task2{

    public static void main(String[] args){

        try{
            File inputFile = new File("day14_input.txt");
            Scanner input = new Scanner(inputFile);
          

            ArrayList<String> lines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                lines.add(line);
			}
            input.close();

            int W = lines.get(0).length();
            int H = lines.size();

            System.out.println(W * H);

            MapObj map[][] = new MapObj[W][H];
            MapObj prevMap[][] = new MapObj[W][H];



            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    if(lines.get(y).charAt(x) == 'O'){
                        map[x][y] = new RoundBall(lines.get(y).charAt(x));
                    }
                    else{
                        map[x][y] = new MapObj(lines.get(y).charAt(x));
                    }
                }
            }

            boolean allLooped = false;
            while(!allLooped){
                allLooped = true;

                for(int y = 0; y < H; y++){
                    for(int x = 0; x < W; x++){
                        prevMap[x][y] = map[x][y];
                    }
                }

                //move north
                for(int x = 0; x < W; x++){
                    int freePlaceY = 0;
                    for(int y = 0; y < H; y++){
                        if(map[x][y].getType() == '#'){
                            freePlaceY = y + 1;
                        }
                        else if(map[x][y].getType() == 'O'){
                            RoundBall ball = (RoundBall)(map[x][y]);
                            if(!ball.move(x + freePlaceY * W, Dir.north)){
                                //allLooped = false;
                            }
                            MapObj tmp = map[x][freePlaceY];
                            map[x][freePlaceY] = map[x][y];
                            map[x][y] = tmp;
                            freePlaceY++;
                        }
                    }
                }

                //move west
                for(int y = 0; y < H; y++){
                    int freePlaceX = 0;
                    for(int x = 0; x < W; x++){
                        if(map[x][y].getType() == '#'){
                            freePlaceX = x + 1;
                        }
                        else if(map[x][y].getType() == 'O'){
                            RoundBall ball = (RoundBall)(map[x][y]);
                            ball.move(freePlaceX + y * W, Dir.west);
                            MapObj tmp = map[freePlaceX][y];
                            map[freePlaceX][y] = map[x][y];
                            map[x][y] = tmp;
                            freePlaceX++;
                        }
                    }
                }

                //move south
                for(int x = 0; x < W; x++){
                    int freePlaceY = H - 1;
                    for(int y = H - 1; y >= 0; y--){
                        if(map[x][y].getType() == '#'){
                            freePlaceY = y - 1;
                        }
                        else if(map[x][y].getType() == 'O'){
                            RoundBall ball = (RoundBall)(map[x][y]);
                            ball.move(x + freePlaceY * W, Dir.south);
                            MapObj tmp = map[x][freePlaceY];
                            map[x][freePlaceY] = map[x][y];
                            map[x][y] = tmp;
                            freePlaceY--;
                        }
                    }
                }

                //move east
                for(int y = 0; y < H; y++){
                    int freePlaceX = W - 1;
                    for(int x = W - 1; x >= 0; x--){
                        if(map[x][y].getType() == '#'){
                            freePlaceX = x - 1;
                        }
                        else if(map[x][y].getType() == 'O'){
                            RoundBall ball = (RoundBall)(map[x][y]);
                            ball.move(freePlaceX + y * W, Dir.east);
                            MapObj tmp = map[freePlaceX][y];
                            map[freePlaceX][y] = map[x][y];
                            map[x][y] = tmp;
                            freePlaceX--;
                        }
                    }
                }
                
                for(int y = 0; y < H; y++){
                    for(int x = 0; x < W; x++){
                        if(map[x][y].getType() == 'O'){
                            if(prevMap[x][y].getType() != 'O'){
                                allLooped = false;
                                break;
                            }
                        }
                    }
                }

                // for(int y = 0; y < H; y++){
                //     for(int x = 0; x < W; x++){
                //         System.out.print(map[x][y].getType());
                //     }
                //     System.out.println();
                // }
                // System.out.println();
            }
            System.out.println("looped");
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
