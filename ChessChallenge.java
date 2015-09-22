
import java.util.HashSet;


public class ChessChallenge {

	HashSet<String> solutions;
	
	private int [] config;
	
	private int rowsNumber;
	
	private int colsNumber;
	
	private static final int CONF_QUEEN_INDEX = 0;
	
	private static final int CONF_BISHOP_INDEX = 1;
	
	private static final int CONF_ROOKS_INDEX = 2;
	
	private static final int CONF_KING_INDEX = 3;
	
	private static final int CONF_KNIGHT_INDEX = 4;

    /**
     * Run the program from command line 
     * rm *.class && javac *.java && java ChessChallenge
     * 
     * @param args [description]
     */
    public static void main(String[] args) {

        int rows = 3;
        int columns = 3;          

        int nKings = 0;
        int nQueens = 2;
        int nBishops = 0;
        int nKnights = 0;
        int nRooks = 0;
        
        if (rows == columns){
             ChessChallenge problem = new ChessChallenge(rows, columns, nKings, nQueens, nBishops, nRooks, nKnights);
             problem.printSolutions();
        } else{
            System.out.println("The dimensions of the board have to be square");
        }     
    }
	
	/**
	 * Problem constructor and entry point of the application    
     * 
	 * 
	 * @param rowsNumber
	 * @param columnsNumber
	 * @param nKings
	 * @param nQueens
	 * @param nBishops
	 * @param nRooks
	 * @param nKnights
	 */
    public ChessChallenge(int rowsNumber, int columnsNumber, int nKings, int nQueens, int nBishops, int nRooks, int nKnights) {
    	this.rowsNumber = rowsNumber;
    	this.colsNumber = columnsNumber;
    	solutions = new HashSet<String>();
    	ChessBoard board = new ChessBoard(rowsNumber, columnsNumber);
    	this.config = new int[]{nQueens, nBishops, nRooks, nKings, nKnights};
        solve(this.config, board);   
    }    
    
    /**
     * Count the number of pieces in the configuration
     * 
     * @param configuration
     * @return int
     */
    private int countPieces(int [] configuration) {
    	return configuration[ChessChallenge.CONF_QUEEN_INDEX] +
    		   configuration[ChessChallenge.CONF_BISHOP_INDEX] +
    		   configuration[ChessChallenge.CONF_ROOKS_INDEX] +
    		   configuration[ChessChallenge.CONF_KING_INDEX] +
    		   configuration[ChessChallenge.CONF_KNIGHT_INDEX];
    }
    
    /**
     * Returns the number of different solutions with the given configuration
     * 
     * @param  []conf   
     * @param  board   
     * @param  rowIndex
     * @param  colIndex
     * @return        
     */
    public void solve(int [] conf, ChessBoard board) {        
        
        //printConfiguration(conf, board.getRowCount(), board.getColumnCount());
        
    	if(countPieces(conf) == 0 && !solutions.contains(board.getHashCode())) {    	    
    	    
    	    //We get a solution!!
    	    //each solution gives us another 3 (board rotations)
    	    addSolutionAndRotations(board);
    	    return;
    	    
    	}
        
        for(int i = 0; i < board.getRowCount(); i++) {
            
            for (int j = 0; j < board.getColumnCount(); j++) {
                
                int [] confCopy = new int[conf.length];   
                System.arraycopy(conf, 0, confCopy, 0, conf.length);
                putQueen(confCopy, board, i, j);
                
            } 
        }
    }
    
    
    /**
     * If we can put the piece then we cut the piece from the conf array
     * and call to the solve method with the new conf array and
     * the copy of the board
     * 
     * @param conf
     * @param board
     * @param rowIndex
     * @param colIndex
     * @param confIndex
     * @param pieceName
     */
    private void putQueen(int []conf, ChessBoard sourceBoard, int rowIndex, int colIndex) {        
        
        if(conf[CONF_QUEEN_INDEX] > 0) {
            
            ChessBoard updatedBoard = new ChessBoard(sourceBoard);
             
            //System.out.println("rowIndex: "  + rowIndex + " colIndex: " + colIndex);
            boolean piecePlaced = updatedBoard.putQueen(rowIndex, colIndex);
            
            if(piecePlaced) {            
                conf[CONF_QUEEN_INDEX]--;                
                //System.out.println("Queen put");                
                solve(conf, updatedBoard);
            }else {
                //solve(conf, sourceBoard);
                return; 
            } 
        }  
    }
    
    
    /**
     * Adds the hashCode of the board solutions and the 3 board rotations. 
     * The contains check is for this scenario (with any piece):
     * 
     *  -1 -1 -1
     *  -1  Q -1
     *  -1 -1 -1
     *  
     *  the three rotations are equal to the first board state
     *  
     * @param board
     */
    private void addSolutionAndRotations(ChessBoard board) {
        
        ChessBoard aux = new ChessBoard(board);
        
        if(!solutions.contains(aux.getHashCode())){
            aux.printBoard();
            solutions.add(aux.getHashCode());
        }
        
        aux.rotate();
        
        if(!solutions.contains(aux.getHashCode())){               
            aux.printBoard();
            solutions.add(aux.getHashCode());
        }
        
        aux.rotate();
        if(!solutions.contains(aux.getHashCode())){
            aux.printBoard();
            solutions.add(aux.getHashCode());
        }
        
        
        aux.rotate();
        if(!solutions.contains(aux.getHashCode())) {
            aux.printBoard();
            solutions.add(aux.getHashCode());
        }
        
        System.out.println(solutions.size() + " solutions found");
    }
    
    /**
     * Helper function for print all the solutions to the given configuration
     */
    public void printSolutions() {
    	int numberOfSolutions = solutions.size();
        printConfiguration(this.config, rowsNumber, colsNumber);        
        if(numberOfSolutions > 0) {
        	if(numberOfSolutions == 1) {
        		System.out.println("There is only one solution :)");
        	}else {
        		System.out.println("There are " + numberOfSolutions + "  solutions :)");
        	}
        }else {
        	System.out.println("There is no solutions for the given configuration :(");
        }
    }
    
    /**
     * Helper function for print the given configuration
     */
    private void printConfiguration(int [] configuration, int rows, int columns) {
    	if(configuration.length == 5 ) {
    		StringBuilder confString = new StringBuilder("Configuration ");
    		confString.append(rows).append("x").append(columns).append(": ");
    		confString.append(configuration[0]).append(" queens, ");
    		confString.append(configuration[1]).append(" bishops, ");
    		confString.append(configuration[2]).append(" rooks, ");
    		confString.append(configuration[3]).append(" kings, ");
    		confString.append(configuration[4]).append(" knights");
    		System.out.println(confString.toString());
    	}
    }  
	
}
