import java.util.Collections;


public class Row implements Numbers{
    
    Tile[] myNums = new Tile[9];
    int myIndex = 0;
    public Row(Tile[] row) {
        myNums = row;
    }
    
    public Row(Tile[] row, int index){
        this(row);
        myIndex = index;
    }
    
    
    public boolean contains(int a){
        for (Tile num : myNums ){
            if(num.value() == a){
                return true;
            }
        }
        return false;
    }
    
    public int index(){
        return myIndex;
    }
    
    public Tile[] getNums(){
        return myNums;
    }
    
    public void setNums(Tile[] nums){
        myNums = nums;
    }
    
    public String toString(){
        String str = "";
        for(Tile num : myNums){
            str += num.value()+",";
        }
        return str;
    }
    
    public void calculatePossibilities(){
        for(Tile t : myNums){
            t.calculatePossibilities();
        }
    }
    
    public void removePossibility(int num){
        for(Tile t: myNums){
            t.removePossibility(num);
        }
    }
    
    

}
