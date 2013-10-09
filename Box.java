import java.util.ArrayList;
import java.util.List;


public class Box implements Numbers{

    
    Tile[][] myNums = new Tile[3][3];
    List<Column> myCols = new ArrayList<Column>();
    List<Row> myRows = new ArrayList<Row>();
    int myIndex;
    public Box(Tile[][] nums){
        myNums = nums;
    }
    
    public Box(List<Column> cols, List<Row> rows, int index){
        myCols = cols;
        myRows = rows;
        myIndex = index;
        updateNums();
    }
    
    
    public boolean contains(Integer a){
        for(int i = 0 ; i < myNums.length ; i++){
            Tile[] li = myNums[i];
            for(int j = 0 ; j < li.length ; j++){
                if (li[j].value() == a) {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    private void updateNums(){
        int x = myCols.get(0).index();
        for(int i = 0 ; i < myNums.length ; i++){
            Tile[] row = myNums[i];
            for(int j = 0 ; j < row.length ; j++){
                myNums[i][j] = myRows.get(i).getNums()[x+j];
            }
        }
    }
    
    
    public Tile[][] getPureNums(){
        return myNums;
    }
    
    public Tile[] getNums(){
        Tile[] all = new Tile[9];
        for(int i = 0 ; i < myNums.length ; i++){
            Tile[] row = myNums[i];
            for(int j = 0 ; j < row.length ; j++){
                all[i*3 + j] = myNums[i][j];
            }
        }
        return all;
    }
    
    public String toString(){
        String str = "";
        for(Tile[] lst : myNums){
            str+= "\n";
            for(Tile a : lst){
                str+= a.value() + ",";
            }
        }
        return str;
    }
    
    public void calculatePossibilities(){
        for(Tile[] ta : myNums){
            for(Tile t: ta){
                t.calculatePossibilities();
            }
        }
    }
    
    public void removePossibility(int num){
        for(Tile[] ta : myNums){
            for(Tile t: ta){
                t.removePossibility(num);
            }
        }
    }
}
