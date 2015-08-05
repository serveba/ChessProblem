
public class ChessBoard {

    final int rows;
    final int columns;
    int[][] board;
    
    public static final int NO_AVAIABLE = -2;
    public static final int INVALID_POS = -1;
    static final int EMPTY_CODE = 0;
    static final int KING_CODE = 1;
    static final int QUEEN_CODE = 2;
    static final int BISHOPS_CODE = 3;
    static final int ROOKS_CODE = 4;
    static final int KNIGTHS_CODE = 5;
    // static final int PAWNS_CODE = 6;

    public ChessBoard(int rowsNumber, int columnsNumber) {
        rows = rowsNumber;
        columns = columnsNumber;
        board = new int[rows][columns];

        // empty board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY_CODE;
            }
        }              
    }
    
    
    public int[] putQueen(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(x < rows && y < columns && board[x][y] == EMPTY_CODE) {
    		if(checkSafeRowAndColumn(x, y) && checkSafeDiagonals(x, y) && checkSafeKnights(x, y)){
    			writeBusyCellRowAndColumn(x, y);
    			writeDiagonals(x, y);
    			board[x][y] = QUEEN_CODE;    			
    			newPos = lookForNewPosAvailable();
    		}
    	}
    	return newPos;
    }
    
    public int[] putKing(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(x < rows && y < columns && board[x][y] == EMPTY_CODE) {
    		if(checkSafeAdjacent(x, y) && checkSafeKnights(x, y)){
    			writeCellsAdjacent(x, y);
    			board[x][y] = KING_CODE;    			
    			newPos = lookForNewPosAvailable();
    		}    	
    	}    	
    	return newPos;
    }
    
    public int[] putBishop(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(x < rows && y < columns && board[x][y] == EMPTY_CODE) {
    		if(checkSafeDiagonals(x, y) && checkSafeKnights(x, y)) {
    			writeDiagonals(x, y);
    			board[x][y] = BISHOPS_CODE;        		    			
    			newPos = lookForNewPosAvailable();
    		}
    	}
    	return newPos;
    }
    
    public int[] putRook(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(x < rows && y < columns && board[x][y] == EMPTY_CODE) {
    		if(checkSafeRowAndColumn(x, y) /*&& checkSafeKnights(x, y)*/ ) {
    			writeBusyCellRowAndColumn(x, y);
    			board[x][y] = ROOKS_CODE;        		    			
    			newPos = lookForNewPosAvailable();
    		}
    	}    	
    	return newPos;
    }
    
    public int[] putKnight(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(x < rows && y < columns && board[x][y] == EMPTY_CODE) {
    		if(checkSafeKnights(x, y)){
    			writeCellsKnights(x, y);
    			board[x][y] = KNIGTHS_CODE;        		    			
    			newPos = lookForNewPosAvailable();
    		}
    	}    	    
    	return newPos;
    }
    
    /**
     * 
     *  Look for a new available position 
     *
     * 
     * @param x
     * @param y
     * @return
     */
    public int[] advance(int x, int y) {
    	int [] newPosAvailable = new int []{INVALID_POS, INVALID_POS};
    	board[x][y] = INVALID_POS; 
    	
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
            	if(board[i][j] == EMPTY_CODE) return new int []{i, j};            	            	           
            }
        }   
    	    	    	   
    	return newPosAvailable;
    }
    
    public int[] lookForNewPosAvailable() {
    	int [] newPosAvailable = new int []{NO_AVAIABLE, NO_AVAIABLE};
    	
    	for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
            	if(board[i][j] == EMPTY_CODE) return new int []{i, j};            	            	           
            }
        }   
    	    	    	   
    	return newPosAvailable;
    }
           
    /**
     * Checks if there is a knigth attacking to the possition
     * x, y
     * 
     * @param x
     * @param y
     * @return boolean
     */
    private boolean checkSafeKnights(int x, int y) {
    	if(x > rows || y > columns || x < 0 || y < 0) return false;
    	
    	//Top-left area
    	if(x-2 >= 0 && y-1 >= 0 && board[x-2][y-1] != INVALID_POS && board[x-2][y-1] != EMPTY_CODE) return false;
    	if(x-1 >= 0 && y-2 >= 0 && board[x-1][y-2] != INVALID_POS && board[x-1][y-2] != EMPTY_CODE) return false;
    	
    	//Top-right area
    	if(x-2 >= 0 && y+1 < columns && board[x-2][y+1] != INVALID_POS && board[x-2][y+1] != EMPTY_CODE) return false;
    	if(x-1 >= 0 && y+2 < columns && board[x-1][y+2] != INVALID_POS && board[x-1][y+2] != EMPTY_CODE) return false;
    	
    	//Down-left area
    	if(x+1 < rows && y-2 >= 0 && board[x+1][y-2] != INVALID_POS && board[x+1][y-2] != EMPTY_CODE) return false;
    	if(x+2 < rows && y-1 >= 0 && board[x+2][y-1] != INVALID_POS && board[x+2][y-1] != EMPTY_CODE) return false;
    	    	
    	//Down-right area
    	if(x+1 < rows && y+2 < columns && board[x+1][y+2] != INVALID_POS && board[x+1][y+2] != EMPTY_CODE) return false;
    	if(x+2 < rows && y+1 < columns && board[x+2][y+1] != INVALID_POS && board[x+2][y+1] != EMPTY_CODE) return false;
    	    	    	   
    	return true;
    }
    
    private void writeCellsKnights(int x, int y) {
    	
    	//Top-left area
    	if(x-2 >= 0 && y-1 >= 0 && board[x-2][y-1] == EMPTY_CODE) board[x-2][y-1] = INVALID_POS;
    	if(x-1 >= 0 && y-2 >= 0 && board[x-1][y-2] == EMPTY_CODE) board[x-1][y-2] = INVALID_POS;
    	
    	//Top-right area
    	if(x-2 >= 0 && y+1 < columns && board[x-2][y+1] == EMPTY_CODE) board[x-2][y+1] = INVALID_POS;
    	if(x-1 >= 0 && y+2 < columns && board[x-1][y+2] == EMPTY_CODE) board[x-1][y+2] = INVALID_POS;
    	
    	//Down-left area
    	if(x+1 < rows && y-2 >= 0 && board[x+1][y-2] == EMPTY_CODE) board[x+1][y-2] = INVALID_POS;
    	if(x+2 < rows && y-1 >= 0 && board[x+2][y-1] == EMPTY_CODE) board[x+2][y-1] = INVALID_POS;
    	    	
    	//Down-right area
    	if(x+1 < rows && y+2 < columns && board[x+1][y+2] == EMPTY_CODE) board[x+1][y+2] = INVALID_POS;
    	if(x+2 < rows && y+1 < columns && board[x+2][y+1] == EMPTY_CODE) board[x+2][y+1] = INVALID_POS;
    }
           
    /**
     * Check if there is a piece in the two diagonals (Queen and bishops)
     * 
     * @param x
     * @param y
     * @return
     */
    private boolean checkSafeDiagonals(int x, int y) {
    	if(x > rows || y > columns || x < 0 || y < 0) return false;
     	
    	//Top-left diagonal
    	for(int i=x; i > rows; i--) {
    		for(int j=y; j > columns; j--) {
    			if(board[i][j] != EMPTY_CODE && board[i][j] != INVALID_POS) return false;
    		}    		
    	}
    	
    	//Top-right diagonal
    	for(int i=x; i > rows; i--) {
    		for(int j = 0; j < columns; j++) {
    			if(board[i][j] != EMPTY_CODE && board[i][j] != INVALID_POS) return false;
    		}    		
    	}
    	
    	//Down-left diagonal
    	for(int i = 0; i < rows; i++) {
    		for(int j=y; j > columns; j--) {
    			if(board[i][j] != EMPTY_CODE && board[i][j] != INVALID_POS) return false;
    		}    		
    	}
    	
    	//Down-right diagonal
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < columns; j++) {
    			if(board[i][j] != EMPTY_CODE && board[i][j] != INVALID_POS) return false;
    		}    		
    	}
    	    	
    	return true;
    }
    
    private void writeDiagonals(int x, int y) {
    	
    	//Top-left diagonal
    	for(int i=x; i > rows; i--) {
    		for(int j=y; j > columns; j--) {
    			if(board[i][j] == EMPTY_CODE) board[i][j] = INVALID_POS;
    		}    		
    	}
    	
    	//Top-right diagonal
    	for(int i=x; i > rows; i--) {
    		for(int j = 0; j < columns; j++) {
    			if(board[i][j] == EMPTY_CODE) board[i][j] = INVALID_POS;
    		}    		
    	}
    	
    	//Down-left diagonal
    	for(int i = 0; i < rows; i++) {
    		for(int j=y; j > columns; j--) {
    			if(board[i][j] == EMPTY_CODE) board[i][j] = INVALID_POS;
    		}    		
    	}
    	
    	//Down-right diagonal
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < columns; j++) {
    			if(board[i][j] == EMPTY_CODE) board[i][j] = INVALID_POS;
    		}    		
    	}
    }
         
    /**
     * Checks if there is a piece in the adjacent positions
     * 
     * This is used for the King
     * 
     * @param x
     * @param y
     * @return boolean
     */
    private boolean checkSafeAdjacent(int x, int y) {
    	if(x > rows || y > columns || x < 0 || y < 0) return false;
		if(x+1 < rows && board[x+1][y] != EMPTY_CODE && board[x+1][y] != INVALID_POS) return false;
		if(x-1 >= 0 && board[x-1][y] != EMPTY_CODE && board[x-1][y] != INVALID_POS) return false;
		if(y+1 < columns && board[x][y+1] != EMPTY_CODE && board[x][y+1] != INVALID_POS) return false; 
		if(y-1 >= 0 && board[x][y-1] != EMPTY_CODE && board[x][y-1] != INVALID_POS) return false; 
		//diag
		if(x+1 < rows && y+1 < columns && board[x+1][y+1] != EMPTY_CODE && board[x+1][y+1] != INVALID_POS) return false;
		if(x+1 < rows && y-1 > 0 && board[x+1][y-1] != EMPTY_CODE && board[x+1][y-1] != INVALID_POS) return false;     		
		if(x-1 >= 0 && y+1 < columns && board[x-1][y+1] != EMPTY_CODE && board[x-1][y+1] != INVALID_POS) return false; 
		if(x-1 >= 0 && y-1 >= 0 && board[x-1][y-1] != EMPTY_CODE && board[x-1][y-1] != INVALID_POS) return false; 
    	return true;
    }
    
    private void writeCellsAdjacent(int x, int y) {    	
		if(x+1 < rows && board[x+1][y] == EMPTY_CODE) board[x+1][y] = INVALID_POS;
		if(x-1 > 0 && board[x-1][y] == EMPTY_CODE) board[x-1][y] = INVALID_POS;
		if(y+1 < columns && board[x][y+1] == EMPTY_CODE) board[x][y+1] = INVALID_POS; 
		if(y-1 >= 0 && board[x][y-1] == EMPTY_CODE) board[x][y-1] = INVALID_POS;
		//diag
		if(x+1 < rows && y+1 < columns && board[x+1][y+1] == EMPTY_CODE) board[x+1][y+1] = INVALID_POS;
		if(x+1 < rows && y-1 >= 0 && board[x+1][y-1] == EMPTY_CODE) board[x+1][y-1] = INVALID_POS;    		
		if(x-1 >= 0 && y+1 < columns && board[x-1][y+1] == EMPTY_CODE) board[x-1][y+1] = INVALID_POS;
		if(x-1 >= 0 && y-1 >= 0 && board[x-1][y-1] == EMPTY_CODE) board[x-1][y-1] = INVALID_POS;    	
    }
    
    /**
     * Checks if there is a piece in the same row
     * or in the same column (Queen and rooks)
     * 
     * @param x
     * @param y
     * @return boolean
     */
    private boolean checkSafeRowAndColumn(int x, int y) {
    	if(x > rows || y > columns || x < 0 || y < 0) return false;
    	
    	// Column
		for(int j = 0; j < columns; j++) {
			if(board[x][j] != EMPTY_CODE && board[x][j] != INVALID_POS) return false;
		}
		
		// Row
		for(int i = 0; i < rows; i++) {
			if(board[i][y] != EMPTY_CODE && board[i][y] != INVALID_POS) return false;
		}
		return true;
    }
    
    private void writeBusyCellRowAndColumn(int x, int y) {    	    
    	// Column
		for(int j = 0; j < columns; j++) {
			if(board[x][j] == EMPTY_CODE) board[x][j] = INVALID_POS;
		}
		
		// Row
		for(int i = 0; i < rows; i++) {
			if(board[i][y] == EMPTY_CODE) board[i][y] = INVALID_POS;
		}		
    }

    /**
     * prints the board into console
     */
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
        	if (i == 0){
        		for (int j = 0; j < board[i].length; j++) {
                    System.out.print("+---");
                }
                System.out.println("+");
        	}
        	        	        
            for (int j = 0; j < board[i].length; j++) {
                System.out.printf("| %c ", getCharFromPiece(board[i][j])); 
            }
            System.out.println("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
    }

    /**
     * Get piece char code from int value
     * 
     * @param code
     * @return
     */
    private static char getCharFromPiece(int code) {
        char charCode;

        switch (code) {
            case KING_CODE:
                charCode = 'K';
                break;
            case QUEEN_CODE:
                charCode = 'Q';
                break;
            case BISHOPS_CODE:
                charCode = 'B';
                break;
            case ROOKS_CODE:
                charCode = 'R';
                break;
            case KNIGTHS_CODE:
                charCode = 'N';
                break;
            default:
            	charCode = ' ';
                break;
        }

        return charCode;
    }
    
    public int getRowCount() {
    	return rows;
    }
    
    public int getColumnCount() {
    	if(rows>1) {
    		return columns;
    	} else {
    		return 0;
    	}
    }
	
}
