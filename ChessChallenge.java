
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.lang.Thread;
import java.lang.InterruptedException;


public class ChessChallenge {

	HashSet<String> solutions;
	
	private int [] configuration;
	
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
    	this.configuration = new int[]{nQueens, nBishops, nRooks, nKings, nKnights};
    	solutions = new HashSet<String>();
    	ChessBoard board = new ChessBoard(rowsNumber, columnsNumber);    	
        int solutionsCount = 0;
        solutionsCount = solve(new int[]{nQueens, nBishops, nRooks, nKings, nKnights}, board, 0, 0, solutionsCount);   
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
    public int solve(int []conf, ChessBoard board, int rowIndex, int colIndex, int solutionsCount) {    	

        //board.printBoard();
    	
    	int [] newPos = new int []{ChessBoard.INVALID_POS, ChessBoard.INVALID_POS};
    	
    	String hashCode = board.getHashCode();
    	if(countPieces(conf) == 0 && !solutions.contains(hashCode)) {
    		//We get a solution!!
            board.printBoard();
    		solutions.add(hashCode);
    		return 1;
    	}

        //We don't have more free positions
        if(rowIndex == ChessBoard.INVALID_POS || rowIndex == ChessBoard.UNAVAIABLE ) {
            return 0;
        }

        for(int j = colIndex; j < board.getColumnCount(); j++) {            
            if(conf[CONF_QUEEN_INDEX] > 0) {
                
                newPos = board.putQueen(rowIndex, j);
                System.out.println("rowIndex: "  + rowIndex + " colIndex: " + j);

                System.out.println("newPos[x]: "  + newPos[0] + " newPos[y]: " + newPos[1]);


                if(newPos[0] != ChessBoard.INVALID_POS) {
                    
                    conf[CONF_QUEEN_INDEX]--;

                    //board.printBoard();
                    System.out.println("Queen put");

                    try {
                        Thread.sleep(2000);                 
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    solutionsCount += solve(conf, board, newPos[0], newPos[1], solutionsCount);
                }else {
                    try {
                        Thread.sleep(2000);                 
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }

                //solutionsCount += solve(conf, board, rowIndex, j, solutionsCount);
            }
        }

        for( int i = rowIndex + 1;  i < board.getRowCount(); i++) {
            for(int j = 0; j < board.getColumnCount(); j++) {
                if(conf[CONF_QUEEN_INDEX] > 0) {
                    newPos = board.putQueen(rowIndex, j);

                    if(newPos[0] != ChessBoard.INVALID_POS) {
                        conf[CONF_QUEEN_INDEX]--;
                        //we have put the queen
                        solutionsCount += solve(conf, board, newPos[0], newPos[1], solutionsCount);
                    }
                    //we try to put the queen in the next cell
                    solutionsCount += solve(conf, board, i, j, solutionsCount);
                }
            }
        }

        System.out.println("no deberia salir por aqui");
        return 0;
    	
    	// Order pieces by aggressiveness
        // 
     //    if(conf[CONF_QUEEN_INDEX] > 0) {      		    

    	// 	newPos = board.putQueen(rowIndex, colIndex);


     //        return solutionsCount + solve(solutions, conf, stack, newPos[0], newPos[1], solutionsCount);

    	// 	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
     //        	conf[CONF_QUEEN_INDEX]--;
    	// 		stack.push(board);

     //            return solutionsCount + 
    	// 		solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    	// 	}else {
    	// 		newPos = board.lookForNewPosAvailable(rowIndex);							
    	// 		if(newPos[0] != ChessBoard.INVALID_POS) {    				
    	// 			solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 		}else {
    	// 			if(weDontHavePieces(conf)) {
    	// 	    		solutions.add(board);		    		
    	// 	    	}	
    	// 			else{
    	// 				//stack.pop();
    	// 				//solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 			}
    	// 			return solutions;
    	// 		}				
    	// 	}
    	// }
    	
    	// if(conf[CONF_BISHOP_INDEX] > 0) { 
    	// 	newPos = board.putBishop(rowIndex, colIndex);
    		
    	// 	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
     //    		conf[CONF_BISHOP_INDEX]--;
    	// 		stack.push(board);
    	// 		solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    	// 	}else {
    	// 		newPos = board.advance(rowIndex, colIndex);							
    	// 		if(newPos[0] != ChessBoard.INVALID_POS) {    				
    	// 			solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 		}else {
    	// 			if(weDontHavePieces(conf)) {
    	// 	    		solutions.add(board);		    		
    	// 	    	}	
    	// 			else{
    	// 				//stack.pop();
    	// 				//solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 			}
    	// 			return solutions;
    	// 		}				
    	// 	}
    	// }
    	
     //    if(conf[CONF_ROOKS_INDEX] > 0) {
     //    	newPos = board.putRook(rowIndex, colIndex);
        	
        	
     //    	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
     //    		conf[CONF_ROOKS_INDEX]--;
    	// 		stack.push(board);
    	// 		solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    	// 	}else {
    	// 		newPos = board.advance(rowIndex, colIndex);							
    	// 		if(newPos[0] != ChessBoard.INVALID_POS) {    				
    	// 			solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 		}else {
    	// 			if(weDontHavePieces(conf)) {
    	// 	    		solutions.add(board);		    		
    	// 	    	}	
    	// 			else{
    	// 				//stack.pop();
    	// 				//solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 			}
    	// 			return solutions;
    	// 		}				
    	// 	}
    	// }
        
     //    if(conf[CONF_KING_INDEX] > 0) {
     //    	newPos = board.putKing(rowIndex, colIndex);
        	
        	
     //    	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
     //    		conf[CONF_KING_INDEX]--;
    	// 		stack.push(board);
    	// 		solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    	// 	}else {
    	// 		newPos = board.advance(rowIndex, colIndex);							
    	// 		if(newPos[0] != ChessBoard.INVALID_POS) {    				
    	// 			solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 		}else {
    	// 			if(weDontHavePieces(conf)) {
    	// 	    		solutions.add(board);		    		
    	// 	    	}			
    	// 			else{
    	// 				//stack.pop();
    	// 				//solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 			}
    	// 			return solutions;
    	// 		}				
    	// 	}
    	// }
        
     //    if(conf[CONF_KNIGHT_INDEX] > 0) {
     //    	newPos = board.putKnight(rowIndex, colIndex);

     //    	if(newPos[0] !=  ChessBoard.INVALID_POS) {//if we can put the piece	
     //    		conf[CONF_KNIGHT_INDEX]--;
    	// 		stack.push(board);
    	// 		solve(solutions, conf, stack, newPos[0], newPos[1]);       			    		
    	// 	}else {
    	// 		newPos = board.advance(rowIndex, colIndex);							
    	// 		if(newPos[0] != ChessBoard.INVALID_POS) {    				
    	// 			solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 		}else {
    	// 			if(weDontHavePieces(conf)) {
    	// 	    		solutions.add(board);		    		
    	// 	    	}			
    	// 			else{
    	// 				//stack.pop();
    	// 				//solve(solutions, conf, stack, newPos[0], newPos[1]);
    	// 			}
    	// 			return solutions;
    	// 		}				
    	// 	}
    	// }
                       
     //    return solutions;
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
//        	for (ChessBoard solution : solutions) {
//				solution.printBoard();
//			}
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
