import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

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
    public ArrayList<Integer> poses = new ArrayList<>();
    public boolean active = false;

    public RoundBall(char type){
        super(type);
    }

    public boolean findPattern(){
        final int minTestLen = 10;
        if(poses.size() < 1000){
            return false;
        }

        for(int start = 0; start < poses.size() - minTestLen; start++){
            int testLen = poses.size() - start;
            for(int patternSize = 1; patternSize <= testLen / 2; patternSize++){
                boolean found = true;
                for(int i = start; i < poses.size(); i++){
                    if(poses.get(i) != poses.get(start + (i - start) % patternSize)){
                        found = false;
                        break;
                    }
                }
                if(found){
                    //System.out.println("Start: " + start + " size: " + patternSize);
                    return true;
                }
            }
        }
        return false;
    }

    public void move(int newPos){
        if(active)
            poses.add(newPos);
    }
}

class Task2{

    private static int[] zFunc(ArrayList<Integer> s){
        int z[] = new int[s.size()];
        int left = 0;
        int right = 0;

        for(int i = 0; i < s.size(); i++){
            z[i] = Math.max(0, Math.min(z[i - left], right - i));
            while(i + z[i] < s.size() && s.get(z[i]) == s.get(i + z[i])){
                z[i]++;
                if(i + z[i] > right){
                    left = i;
                    right = i + z[i];
                }
            }
        }

        return z;
    }

    private static int findRepeat(ArrayList<Integer> seq){
        ArrayList<Integer> seqRev = new ArrayList<>();
        for(int i = seq.size() - 1; i >= 0; i--){
            seqRev.add(seq.get(i));
        }

        final int threshold = 10;
        int[] z = zFunc(seqRev);
        for(int i = 1; i < z.length; i++){
            if(z[i] >= threshold){
                // for(int j = i - 1; j >= 0; j--){
                //     System.out.print(seqRev.get(j));
                // }
                return i;
            }
        }

        return -1;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day14_test.txt");
            Scanner input = new Scanner(inputFile);
          

            ArrayList<String> lines = new ArrayList<>();

			while (input.hasNextLine()) {
				String line = input.nextLine();
                lines.add(line);
			}
            input.close();

            int W = lines.get(0).length();
            int H = lines.size();

            MapObj map[][] = new MapObj[W][H];

            int countRound = 0;
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    if(lines.get(y).charAt(x) == 'O'){
                        map[x][y] = new RoundBall(lines.get(y).charAt(x));
                        if(countRound < 10){
                            ((RoundBall)map[x][y]).active = true;
                        }
                        countRound++;
                    }
                    else{
                        map[x][y] = new MapObj(lines.get(y).charAt(x));
                    }
                }
            }
            

            int counter = 0;
            while(counter < 10000){
                //move north
                for(int x = 0; x < W; x++){
                    int freePlaceY = 0;
                    for(int y = 0; y < H; y++){
                        if(map[x][y].getType() == '#'){
                            freePlaceY = y + 1;
                        }
                        else if(map[x][y].getType() == 'O'){
                            RoundBall ball = (RoundBall)(map[x][y]);
                            ball.move(x + freePlaceY * W);
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
                            ball.move(freePlaceX + y * W);
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
                            ball.move(x + freePlaceY * W);
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
                            ball.move(freePlaceX + y * W);
                            MapObj tmp = map[freePlaceX][y];
                            map[freePlaceX][y] = map[x][y];
                            map[x][y] = tmp;
                            freePlaceX--;
                        }
                    }
                }

                counter++;
                

                // for(int y = 0; y < H; y++){
                //     for(int x = 0; x < W; x++){
                //         System.out.print(map[x][y].getType());
                //     }
                //     System.out.println();
                // }
                // System.out.println();
            }
            System.out.println("checkpoint");
            for(int y = 0; y < H; y++){
                for(int x = 0; x < W; x++){
                    if(map[x][y].getType() == 'O'){
                        if(((RoundBall)map[x][y]).active){
                            System.out.println(findRepeat(((RoundBall)map[x][y]).poses));
                        }
                    }
                }
            }
            
            
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
