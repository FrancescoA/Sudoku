import java.util.ArrayList;
import java.util.List;


public class Tile {
    
    Integer myValue;
    Box myBox;
    Row myRow;
    Column myColumn;
    List<Integer> possibilities = new ArrayList<Integer>();
    
    public Tile(Integer v){
        myValue= v;
    }
    
    public void setRow(Row r){
        myRow = r;
    }
    public void setCol(Column c){
        myColumn = c;
    }
    public void setBox(Box b){
        myBox = b;
    }
    
    public List<Integer> possibilities(){
        return possibilities;
    }
    
    public List<Integer> possibilitiesClone(){
        List<Integer> ps = new ArrayList<Integer>();
        ps.addAll(possibilities);
        return ps;
    }
    
    public Box box(){
        return myBox;
    }
    
    public Row row(){
        return myRow;
    }
    
    public Column column(){
        return myColumn;
    }
    
    public Integer value(){
        return myValue;
    }
    
    public void setValue(Integer v){
        if(v == 0){
            calculatePossibilities();
        }else{
            if(myValue == 0){
                calculatePossibilities();
            }else{
                this.row().removePossibility(v);
                this.column().removePossibility(v);
                this.box().removePossibility(v);
            }
            
        }

        myValue = v;
    }
    
    public void calculatePossibilities(){
        this.resetPossibilities();
        for(int p = 1 ; p < 10 ; p++){
            if(!this.hasValue() && !this.row().contains(p) && !this.column().contains(p) && !this.box().contains(p)){
                //System.out.println(p);
                this.possibilities().add(p);
            }
        }
    }
    
    public void removePossibility(int num){
        if(possibilities().contains(num)){
            possibilities().remove(possibilities().indexOf(num));
        }
    }
    
    public void resetPossibilities(){
        possibilities.clear();
    }
    
    public boolean hasValue(){
        return myValue > 0;
    }
}
