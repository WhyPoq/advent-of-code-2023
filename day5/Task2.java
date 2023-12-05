import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

class Range{
    public long start;
    //not including
    public long end;

    public Range(long start, long end){
        this.start = start;
        this.end = end;
    }
}

class Task2{

    public static void main(String[] args){
        try{
            File inputFile = new File("day5_input.txt");
            Scanner input = new Scanner(inputFile);

            ArrayList<String> inputLines = new ArrayList<>();

            while (input.hasNextLine()) {
                String line = input.nextLine();
                
                inputLines.add(line);
            }

            String[] seedsStr = inputLines.get(0).split(": ")[1].split(" ");

            ArrayList<Range> curRanges = new ArrayList<>();
            ArrayList<Range> newRanges = new ArrayList<>();

            for(int i = 0; i < seedsStr.length - 1; i += 2){
                long start = Long.parseLong(seedsStr[i]);
                long len = Long.parseLong(seedsStr[i + 1]);
                long end = start + len;
                curRanges.add(new Range(start, end));
            }

            for(int i = 3; i < inputLines.size(); i++){
                String line = inputLines.get(i);

                if(line.length() == 0){
                    for(int k = 0; k < newRanges.size(); k++){
                        curRanges.add(newRanges.get(k));
                    }
                    newRanges.clear();

                    /*System.out.println("===cur step===");
                    for(int k = 0; k < curRanges.size(); k++){
                        System.out.println(curRanges.get(k).start + " : " + curRanges.get(k).end);
                    }
                    System.out.println();*/

                    i++; //skip the map heading
                }
                else{
                    String[] numsSplit = line.split(" ");
                    long destStart = Long.parseLong(numsSplit[0]);
                    long sourceStart = Long.parseLong(numsSplit[1]);
                    long range = Long.parseLong(numsSplit[2]);

                    long sourceEnd = sourceStart + range;
                    long transition = destStart - sourceStart;

                    ArrayList<Range> updatedRanges = new ArrayList<Range>();

                    for(int k = 0; k < curRanges.size(); k++){
                        Range curRange = curRanges.get(k);
                        //System.out.println("looking at range " + curRange.start + " : " + curRange.end);

                        //middle
                        long leftPass = Math.max(sourceStart, curRange.start);
                        long rightPass = Math.min(sourceEnd, curRange.end);

                        //if intersects with cur map range
                        if(rightPass - leftPass >= 1){
                            newRanges.add(new Range(leftPass + transition, rightPass + transition));
                            //left
                            if(leftPass - curRange.start >= 1){
                                updatedRanges.add(new Range(curRange.start, leftPass));
                            }
                            //right
                            if(curRange.end - rightPass >= 1){
                                updatedRanges.add(new Range(rightPass, curRange.end));
                            }
                        }
                        else{
                            updatedRanges.add(new Range(curRange.start, curRange.end));
                        }
                    }
                    //System.out.println("new ranges:");

                    curRanges.clear();
                    for(int k = 0; k < updatedRanges.size(); k++){
                        curRanges.add(updatedRanges.get(k));
                    }

                    /*for(int k = 0; k < newRanges.size(); k++){
                        System.out.println(newRanges.get(k).start + " : " + newRanges.get(k).end);
                    }
                    System.out.println("====");*/
                }
                
            }

            for(int k = 0; k < newRanges.size(); k++){
                curRanges.add(newRanges.get(k));
            }

            long lowest = curRanges.get(0).start;
            for(int i = 1; i < curRanges.size(); i++){
                if(curRanges.get(i).start < lowest){
                    lowest = curRanges.get(i).start;
                }
            }

            System.out.println(lowest);

            input.close();

        
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}