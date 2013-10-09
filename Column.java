
public class Column extends Row{

    public Column (Tile[] row) {
        super(row);
    }

    
    public Column (Integer index, Tile[][] tiles) {
        
        super(calcColumn(index, tiles), index);
    }
    
    private static Tile[] calcColumn(Integer index, Tile[][] tiles){
        Tile[] col = new Tile[9];
        for(int i = 0 ; i < tiles.length ; i++){
            Tile[] row = tiles[i];
            col[i] = row[index];
        }
        return col;
        
    }
}
