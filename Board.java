import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Board {

    Tile[][] myTiles = new Tile[9][9];
    List<Row> myRows = new ArrayList<Row>();
    List<Column> myCols = new ArrayList<Column>();
    List<Box> myBoxes = new ArrayList<Box>();
    int rec_count = 0;
    
    public Board(Integer[][] board){

        //Initialize Tiles
        for(int i = 0 ; i < board.length ; i++){
            Integer[] row = board[i];
            for(int j = 0 ; j < row.length ; j++){
                Tile tile = new Tile(row[j]);
                myTiles[i][j] = tile;
            }
        }
        
        //Initialize Rows and Columns
        for(int i = 0 ; i < myTiles.length ; i++){
            Tile[] row = myTiles[i];
            Row row_obj = new Row(row, i);
            myRows.add(row_obj);
            Column col_obj = new Column(i, myTiles);
            myCols.add(col_obj);
        }
        
        //Initialize boxes
        int row_index = 0;
        int col_index = 0;
        for(int i = 0 ; i < myRows.size() ; i++){
            col_index = (i%3)*3;
            row_index = (i/3)*3;
            List<Row> box_rows = myRows.subList(row_index, row_index+3);
            List<Column> box_cols = myCols.subList(col_index, col_index+3);
            myBoxes.add(new Box(box_cols, box_rows, i));
        }
        
        //Set up dependencies
        for(int i = 0 ; i < myTiles.length ; i++){
            Tile[] tile_row = myTiles[i];
            for(int j = 0 ; j < tile_row.length ; j++){
                Tile tile = myTiles[i][j];
                tile.setRow(myRows.get(i));
                tile.setCol(myCols.get(j));
                
                int box_index = j/3+(i*9+j)/(9*3)*3;
                tile.setBox(myBoxes.get(box_index));
                myTiles[i][j] = tile;
            }
        }
        

               
    }
    
    
    
    public void solve(){
        calculateAllPossibilities();
        while(!isSolved()){
            fillNumbers();
        }
      
    }
    
    private void fillNumbers(){
        int easy_count = setEasyValues();
        if(easy_count == 0){
            int hard_count = setHardValues();
            if(hard_count == 0){
                recursion_solve(0,0, myTiles);
                
            }
        }
    }
    
    public boolean recursion_solve(int i, int j, Tile[][] tiles){
        rec_count++;
        if(i == 9){
            i = 0;
            if(++j == 9){
                return true;
            }
        }
      //skip filled cells
        if(tiles[i][j].value() != 0){
            return recursion_solve(i+1,j,tiles);
        }
        
        
        for(Integer val : tiles[i][j].possibilitiesClone()){
            Tile cur = tiles[i][j]; 
            cur.setValue(val);
            if(recursion_solve(i+1, j, tiles)){
                return true;
            }
            
        }
        

        return false;
    }
    
    private int setHardValues(){
        int count = 0;
        count += extrapolateValues(myRows);
        count += extrapolateValues(myCols);
        count += extrapolateValues(myBoxes);
        return count;
    }
    
    
    private int extrapolateValues(List main){
        //calculateAllPossibilities();
        int count = 0;
        for(int i = 0 ; i < main.size() ; i++){
            Numbers row_i = (Numbers) main.get(i);
            for(int j = 0 ; j < row_i.getNums().length ; j++){
                Set<Integer> other_pos = new HashSet<Integer>();
                Tile tile = row_i.getNums()[j];
                for(int x = 0 ; x < row_i.getNums().length ; x++){
                    if(x != j){
                        Tile other_tile = row_i.getNums()[x];
                        other_pos.addAll(other_tile.possibilities());
                    }
                }

                List<Integer> cur_poss = new ArrayList<Integer>();
                cur_poss.addAll(tile.possibilities());
                for(Integer ps : cur_poss){
                    if(!other_pos.contains(ps)){
                        tile.setValue(ps);
                        count++;
                    }
                }  
            }
        }
        return count;
    }
        
    
    
    private int setEasyValues(){
        int count = 0;
        for(int i = 0 ; i < myTiles.length ; i++){
            for(int j = 0 ; j < myTiles[i].length ; j++){
                Tile tile = myTiles[i][j];
                if(tile.possibilities().size() == 1 && tile.value() == 0){
                    count++;
                    tile.setValue(tile.possibilities().get(0));
                }
            }
        }
        return count;
    }
    
    

    
    private boolean isSolved(){
        for(int i = 0 ; i < myTiles.length ; i++){
            for(int j = 0 ; j < myTiles[i].length ; j++){
                Tile tile = myTiles[i][j];
                if(tile.value() == 0){
                    return false;
                }
            }
        }
        return true;
    }
    
    private void calculateAllPossibilities(){
        for(int i = 0 ; i < myTiles.length ; i++){
            for(int j = 0 ; j < myTiles[i].length ; j++){
                Tile tile = myTiles[i][j];
                tile.calculatePossibilities();
            }
        }
    }
    
    
    
    public void print(){
        System.out.println("\n");
        for(int i = 0 ; i < myTiles.length ; i++){
            for(int j = 0 ; j < myTiles[i].length ; j++){
                System.out.println(myTiles[i][j].possibilities());
            }
        } 
    }
    
    
    public String toString(){
        String str = "";
        for(int i = 0 ; i < myTiles.length ; i++){
            str+= "\n";
            for(int j = 0 ; j < myTiles[i].length ; j++){
                str+= myTiles[i][j].value()+",";
            }
        }
        return str;
    }
    
    public static void main (String[] args) {
        Integer[][] board = {
                             {0,9,0,0,0,7,4,0,5},
                             {0,3,0,0,0,0,0,0,0},
                             {0,0,0,1,0,0,0,8,0},
                             {5,0,0,0,6,4,0,0,7},
                             {0,0,0,0,0,0,0,0,0},
                             {9,0,0,8,2,0,0,0,3},
                             {0,4,0,0,0,5,0,0,0},
                             {0,0,0,0,0,0,0,9,0},
                             {6,0,1,3,0,0,0,2,0},
                        };
        Board sudoku = new Board(board);
        long startTime = System.nanoTime();
        sudoku.solve();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Executed in: " + duration);
        System.out.println("Recursions: "+ sudoku.rec_count);
        System.out.println(sudoku);
        
        
    }
}
