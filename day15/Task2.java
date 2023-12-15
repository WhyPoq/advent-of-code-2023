import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

class Lens{

    private String label;
    private int focalLength;
    
    public Lens(String label, int focalLength){
        this.label = label;
        this.focalLength = focalLength;
    }

    public String getLabel(){
        return label;
    }

    public int getFocalLength(){
        return focalLength;
    }
    
    public void setFocalLength(int focalLength){
        this.focalLength = focalLength;
    }
}

class Box{
    private ArrayList<Lens> lenses;
    private int boxNumber;


    public Box(int boxNumber){
        lenses = new ArrayList<>();
        this.boxNumber = boxNumber;

    }

    private boolean hasLens(String label){
        for(int i = 0; i < lenses.size(); i++){
            if(lenses.get(i).getLabel().equals(label)){
                return true;
            }
        }

        return false;
    }

    public void addLens(String label, int focalLength){
        for(int i = 0; i < lenses.size(); i++){
            if(lenses.get(i).getLabel().equals(label)){
                lenses.get(i).setFocalLength(focalLength);
                return;
            }
        }

        lenses.add(new Lens(label, focalLength));
    }

    public void removeLens(String label){
        int ind = -1;
        for(int i = 0; i < lenses.size(); i++){
            if(lenses.get(i).getLabel().equals(label)){
                ind = i;
                break;
            }
        }
        if(ind != -1){
            lenses.remove(ind);
        }
    }

    public int calcFocusingPower(){
        int power = 0;
        for(int i = 0; i < lenses.size(); i++){
            power += (i + 1) * lenses.get(i).getFocalLength();
        }

        return power * (boxNumber + 1);
    }

    public String toString(){
        String s = "Box " + boxNumber + ": ";
        for(int i = 0; i < lenses.size(); i++){
            s += "[" + lenses.get(i).getLabel() + " " + lenses.get(i).getFocalLength() + "] ";
        }
        return s;
    }
}

class Task2{

    public static int hash(String str){
        int curHash = 0;
        for(int i = 0; i < str.length(); i++){
            curHash = ((curHash + str.charAt(i)) * 17) % 256;
        }

        return curHash;
    }

    public static void main(String[] args){

        try{
            File inputFile = new File("day15_input.txt");
            Scanner input = new Scanner(inputFile);
          

            String line = input.nextLine();
            input.close();
			
            String[] steps = line.split(",");

            ArrayList<Box> boxes = new ArrayList<>();
            for(int i = 0; i < 256; i++){
                boxes.add(new Box(i));
            }

            for(String step : steps){
                if(step.contains("=")){
                    String lable = step.split("=")[0];
                    int focalLength = Integer.parseInt(step.split("=")[1]);

                    boxes.get(hash(lable)).addLens(lable, focalLength);

                }
                else{
                    String lable = step.split("-")[0];
                    boxes.get(hash(lable)).removeLens(lable);
                }
            }

            long sum = 0;

            for(int i = 0; i < boxes.size(); i++){
                sum += boxes.get(i).calcFocusingPower();
            }

            System.out.println(sum);

        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
