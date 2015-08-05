
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;


public class ChessChallenge {

	private List<ChessBoard> solutions;
	
	private int [] configuration;
	
	private int rowsNumber;
	
	private int colsNumber;
	
	private static final int CONF_QUEEN_INDEX = 0;
	
	private static final int CONF_BISHOP_INDEX = 1;
	
	private static final int CONF_ROOKS_INDEX = 2;
	
	private static final int CONF_KING_INDEX = 3;
	
	private static final int CONF_KNIGHT_INDEX = 4;
	
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
    	this.configuration = new int[]{nQueens, nBishops, nRooks, nKings, nKnights};
    	solutions = new ArrayList<ChessBoard>();
    	ChessBoard board = new ChessBoard(rowsNumber, columnsNumber);
        
    	Stack<ChessBoard> stackChess = new Stack<ChessBoard>();
    	stackChess.push(board);
        solutions = solve(this.solutions, new int[]{nQueens, nBishops, nRooks, nKings, nKnights}, stackChess, 0, 0);   
    }    
    
    /**
     * True when there are no pieces to put into the board
     * 
     * @param configuration
     * @return boolean
     * @param configuration
     * @return
     */
    private boolean weDontHavePieces(int [] configuration) {
    	return configuration.length == 5 &&
    		   configuration[ChessChallenge.CONF_QUEEN_INDEX] == 0 &&
    		   configuration[ChessChallenge.CONF_BISHOP_INDEX] == 0 &&
    		   configuration[ChessChallenge.CONF_ROOKS_INDEX] == 0 &&
    		   configuration[ChessChallenge.CONF_KING_INDEX] == 0 &&
    		   configuration[ChessChallenge.CONF_KNIGHT_INDEX] == 0;
    }
    
    public List<ChessBoard> solve(List<ChessBoard> solutions, 
    							  int []conf, 
    							  Stack<ChessBoard> stack, 
    							  int rowIndex, 
    							  int colIndex) {
    	
    	ChessBoard board = stack.peek();
    	int [] newPos = new int []{ChessBoard.INVALID_POS, ChessBoard.INVALID_POS};
    	
    	//We get a solution!!
    	if(weDontHavePieces(conf)) {
    		solutions.add(board);
    		return solutions;
    	}
    	
    	//wrong path, we have to do backtracking
    	if(board == null ){
    		return solutions;
    	}
    	
    	
    	// Order pieces by aggressiveness
    	if(conf[CONF_QUEEN_INDEX] > 0) {      		    		    
    		newPos = board.putQueen(rowIndex, colIndex);

    		if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
        		conf[CONF_QUEEN_INDEX]--;
    			stack.push(board);
    			solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    		}else {
    			newPos = board.advance(rowIndex, colIndex);							
    			if(newPos[0] != ChessBoard.INVALID_POS) {    				
    				solve(solutions, conf, stack, newPos[0], newPos[1]);
    			}else {
    				if(weDontHavePieces(conf)) {
    		    		solutions.add(board);		    		
    		    	}	
    				else{
    					//stack.pop();
    					//solve(solutions, conf, stack, newPos[0], newPos[1]);
    				}
    				return solutions;
    			}				
    		}
    	}
    	
    	if(conf[CONF_BISHOP_INDEX] > 0) { 
    		newPos = board.putBishop(rowIndex, colIndex);
    		
    		if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
        		conf[CONF_BISHOP_INDEX]--;
    			stack.push(board);
    			solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    		}else {
    			newPos = board.advance(rowIndex, colIndex);							
    			if(newPos[0] != ChessBoard.INVALID_POS) {    				
    				solve(solutions, conf, stack, newPos[0], newPos[1]);
    			}else {
    				if(weDontHavePieces(conf)) {
    		    		solutions.add(board);		    		
    		    	}	
    				else{
    					//stack.pop();
    					//solve(solutions, conf, stack, newPos[0], newPos[1]);
    				}
    				return solutions;
    			}				
    		}
    	}
    	
        if(conf[CONF_ROOKS_INDEX] > 0) {
        	newPos = board.putRook(rowIndex, colIndex);
        	
        	
        	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
        		conf[CONF_ROOKS_INDEX]--;
    			stack.push(board);
    			solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    		}else {
    			newPos = board.advance(rowIndex, colIndex);							
    			if(newPos[0] != ChessBoard.INVALID_POS) {    				
    				solve(solutions, conf, stack, newPos[0], newPos[1]);
    			}else {
    				if(weDontHavePieces(conf)) {
    		    		solutions.add(board);		    		
    		    	}	
    				else{
    					//stack.pop();
    					//solve(solutions, conf, stack, newPos[0], newPos[1]);
    				}
    				return solutions;
    			}				
    		}
    	}
        
        if(conf[CONF_KING_INDEX] > 0) {
        	newPos = board.putKing(rowIndex, colIndex);
        	
        	
        	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
        		conf[CONF_KING_INDEX]--;
    			stack.push(board);
    			solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    		}else {
    			newPos = board.advance(rowIndex, colIndex);							
    			if(newPos[0] != ChessBoard.INVALID_POS) {    				
    				solve(solutions, conf, stack, newPos[0], newPos[1]);
    			}else {
    				if(weDontHavePieces(conf)) {
    		    		solutions.add(board);		    		
    		    	}			
    				else{
    					//stack.pop();
    					//solve(solutions, conf, stack, newPos[0], newPos[1]);
    				}
    				return solutions;
    			}				
    		}
    	}
        
        if(conf[CONF_KNIGHT_INDEX] > 0) {
        	newPos = board.putKnight(rowIndex, colIndex);

        	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
        		conf[CONF_KNIGHT_INDEX]--;
    			stack.push(board);
    			solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    		}else {
    			newPos = board.advance(rowIndex, colIndex);							
    			if(newPos[0] != ChessBoard.INVALID_POS) {    				
    				solve(solutions, conf, stack, newPos[0], newPos[1]);
    			}else {
    				if(weDontHavePieces(conf)) {
    		    		solutions.add(board);		    		
    		    	}			
    				else{
    					//stack.pop();
    					//solve(solutions, conf, stack, newPos[0], newPos[1]);
    				}
    				return solutions;
    			}				
    		}
    	}
                       
        return solutions;
    }
    
    /**
     * Helper function for print all the solutions to the given configuration
     */
    public void printSolutions() {
    	int numberOfSolutions = solutions.size();
        printConfiguration(configuration, rowsNumber, colsNumber);        
        if(numberOfSolutions > 0) {
        	if(numberOfSolutions == 1) {
        		System.out.println("There is only one solution :)");
        	}else {
        		System.out.println("There are " + numberOfSolutions + "  solutions :)");
        	}
        	for (ChessBoard solution : solutions) {
				solution.printBoard();
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
    

    /**
     * Run the program from command line 
     * rm *.class && javac ChessBoard.java && javac ChessChallenge.java && java ChessChallenge
     * 
     * @param args [description]
     */
    public static void main(String[] args) {
    	
    	Properties properties = Utils.getResourceProps("conf/tableAndPieces.properties", true, ChessChallenge.class);
    	    		    
        int rows = Integer.parseInt(properties.getProperty("rows"));         
        int columns = Integer.parseInt(properties.getProperty("columns"));             
        int nKings = Integer.parseInt(properties.getProperty("nKings"));        
        int nQueens = Integer.parseInt(properties.getProperty("nQueens"));        
        int nBishops = Integer.parseInt(properties.getProperty("nBishops"));         
        int nKnights = Integer.parseInt(properties.getProperty("nKnights"));            
       int nRooks = Integer.parseInt(properties.getProperty("nRooks"));        
//        
//        int rows = 2;
//        int columns = 2;                      
//        int nKings = 1;
//        int nQueens = 0;
//        int nBishops = 0;
//        int nKnights = 0;
//        int nRooks = 1;
        
        if (rows == columns){
        	 ChessChallenge problem = new ChessChallenge(rows, columns, nKings, nQueens, nBishops, nRooks, nKnights);        	
             problem.printSolutions();
        }
        else{
        	System.out.println("The dimensions of the board are not square");
        }     
    }
	
}
