import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Task1{

    public static void main(String[] args){

        try{
            File inputFile = new File("day6_test.txt");
            Scanner input = new Scanner(inputFile);
          
            String timeLine = input.nextLine();
            String distLine = input.nextLine();

            input.close();

            timeLine = timeLine.split(":")[1];
            distLine = distLine.split(":")[1];

            String[] timesStr = timeLine.split(" ");
            String[] distStr = distLine.split(" ");

            ArrayList<Integer> times = new ArrayList<>();
            ArrayList<Integer> dists = new ArrayList<>();

            for(int i = 0; i < timesStr.length; i++){
                if(timesStr[i].length() > 0){
                    times.add(Integer.parseInt(timesStr[i]));
                }
            }

            for(int i = 0; i < distStr.length; i++){
                if(distStr[i].length() > 0){
                    dists.add(Integer.parseInt(distStr[i]));
                }
            }

            int res = 1;

            for(int i = 0; i < times.size(); i++){
                //System.out.println("time: " + times.get(i) + " - dist: " + dists.get(i));

                double sqr_D = Math.sqrt(times.get(i) * times.get(i) - 4 * dists.get(i));

                final double EPS = 0.0001;
                double t1_double = (times.get(i) - sqr_D) / 2;
                int t1 = (int)Math.ceil(t1_double);
                if(Math.abs(t1_double - t1) < EPS){
                    t1 += 1;
                }

                double t2_double = (times.get(i) + sqr_D) / 2;
                int t2 = (int)Math.floor(t2_double);
                if(Math.abs(t2_double - t2) < EPS){
                    t2 -= 1;
                }

                res = res * (t2 - t1 + 1);
            }

            System.out.println(res);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
