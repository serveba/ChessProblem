
/**
 * Class that models the board object with a sequence of operations 
 * The board has to be square
 * 
 */
public class ChessBoard {

    final int rows;
    final int columns;
    int[][] board;
    
    public static final int UNAVAIABLE     = -2;
    public static final int INVALID_POS    = -1;

    static final int EMPTY_CODE            = 0;
    static final int KING_CODE             = 1;
    static final int QUEEN_CODE            = 2;
    static final int BISHOPS_CODE          = 3;
    static final int ROOKS_CODE            = 4;
    static final int KNIGTHS_CODE          = 5;

    /**
     * Constructor
     * 
     * @param  rowsNumber    
     * @param  columnsNumber
     * @return             
     */
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
   
    /**
     * Tries to put a queen on the given cell:
     *  if has success returns the next available position 
     *  that can be [-2,-2] (unavailable) or a valid position 
     *  
     *  otherwise returns [-1, -1] (INVALID)
     * 
     * @param  x
     * @param  y
     * @return  
     */
    public int[] putQueen(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
        System.out.println("isEmpty(" + x + ", " + y + "): " + isEmpty(x, y));
        System.out.println("checkSafeRowAndColumn(" + x + ", " + y + "): " + checkSafeRowAndColumn(x, y));
        System.out.println("checkSafeDiagonals(" + x + ", " + y + "): " + checkSafeDiagonals(x, y));
        System.out.println("checkSafeKnights(" + x + ", " + y + "): " + checkSafeKnights(x, y));

    	if(isEmpty(x, y) && checkSafeRowAndColumn(x, y) 
            &&  checkSafeDiagonals(x, y) && checkSafeKnights(x, y)){

			invalidateRowAndColumn(x, y);
            //printBoard();
			invalidateDiagonals(x, y);
            //printBoard();
			board[x][y] = QUEEN_CODE;    			
			newPos = next(x, y);    		
    	}


    	return newPos;
    }
    
    /**
     * Tries to put a king on the given cell:
     *  if has success returns the next available position 
     *  that can be [-2,-2] (unavailable) or a valid position 
     *  
     *  otherwise returns [-1, -1] (INVALID)
     * 
     * @param  x
     * @param  y
     * @return 
     */
    public int[] putKing(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(isEmpty(x, y) && checkSafeAdjacent(x, y) && checkSafeKnights(x, y)){
			invalidateAdjacent(x, y);
			board[x][y] = KING_CODE;    			
			newPos = next(x, y);
    	}    	
    	return newPos;
    }
    
    /**
     *  Tries to put a bishop on the given cell:
     *  if has success returns the next available position 
     *  that can be [-2,-2] (unavailable) or a valid position 
     *  
     *  otherwise returns [-1, -1] (INVALID)
     * 
     * @param  x
     * @param  y
     * @return 
     */    
    public int[] putBishop(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
        if(isEmpty(x, y) && checkSafeDiagonals(x, y) && checkSafeKnights(x, y)) {
			invalidateDiagonals(x, y);
			board[x][y] = BISHOPS_CODE;        		    			
			newPos = next(x, y);
    	}
    	return newPos;
    }
    
    /**
     * Tries to put a rook on the given cell:
     *  if has success returns the next available position 
     *  that can be [-2,-2] (unavailable) or a valid position 
     *  
     *  otherwise returns [-1, -1] (INVALID)
     * 
     * @param  x
     * @param  y
     * @return 
     */
    public int[] putRook(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(isEmpty(x, y) && checkSafeRowAndColumn(x, y) /*&& checkSafeKnights(x, y)*/ ) {
			invalidateRowAndColumn(x, y);
			board[x][y] = ROOKS_CODE;        		    			
			newPos = next(x, y);
    	}    	
    	return newPos;
    }
    
    /**
     * Tries to put a Knight on the given cell:
     *  if has success returns the next available position 
     *  that can be [-2,-2] (unavailable) or a valid position 
     *  
     *  otherwise returns [-1, -1] (INVALID)
     * 
     * @param  x
     * @param  y
     * @return 
     */
    public int[] putKnight(int x, int y) {
    	int [] newPos = new int []{INVALID_POS, INVALID_POS};
    	if(isEmpty(x, y) && checkSafeKnights(x, y)){
			invalidateKnights(x, y);
			board[x][y] = KNIGTHS_CODE;        		    			
			newPos = next(x, y);
    	}    	    
    	return newPos;
    }
    
    /**
     * Gets the next available positions, if not returns [-2, -2]
     * 
     * @param  x the row where begins the search
     * @param  y the column where begins the search
     * @return
     */
    public int[] next(int x, int y) {
    	int [] newPosAvailable = new int []{UNAVAIABLE, UNAVAIABLE};

        if(x < 0 || x >= rows || y < 0 || y >= columns) 
            return newPosAvailable;        

        for(int j = y; j < columns; j++) 
            if(board[x][j] == EMPTY_CODE) return new int []{x, j};        
    	
    	for (int i = x + 1; i < rows; i++) 
            for (int j = 0; j < columns; j++) 
            	if(board[i][j] == EMPTY_CODE)  return new int []{i, j};          
    	    	    	   
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
        if(isKnight(x-2, y-1)) return false; 
        if(isKnight(x-1, y-2)) return false; 
        //Top-right area
        if(isKnight(x-2, y+1)) return false; 
        if(isKnight(x-1, y+2)) return false; 
        //Down-left area
        if(isKnight(x+1, y-2)) return false; 
        if(isKnight(x+2, y-1)) return false; 
        //Down-right area
        if(isKnight(x+1, y+2)) return false; 
        if(isKnight(x+2, y+1)) return false; 
        return true;
    }
           
    /**
     * Check if there is a piece in the two diagonals (Queens and bishops)
     * 
     * @param x
     * @param y
     * @return
     */
    private boolean checkSafeDiagonals(int x, int y) {
    	if(x > rows || y > columns || x < 0 || y < 0) return false;
        for(int i = 0; i < rows; i++) {
            //top-left diagonal           
            if(isPiece(x-i, y-i)) return false;
            //down-left diagonal
            if(isPiece(x+i, y-i)) return false;
            //top-right diagonal           
            if(isPiece(x-i, y+i)) return false;
            //down-right diagonal           
            if(isPiece(x+i, y+i)) return false;
        }
    	    	
    	return true;
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
        
        return !isPiece(x-1, y) && !isPiece(x-1, y+1) && !isPiece(x-1, y-1) //top row
            && !isPiece(x+1, y+1) && !isPiece(x+1, y) && !isPiece(x+1, y-1) //bottom row
            && !isPiece(x, y+1) && !isPiece(x, y-1); //center row
    }

    /**
     * Invalidate diagonals (Queens and bishops)
     * 
     * @param x 
     * @param y
     */
    private void invalidateDiagonals(int x, int y) {
        for(int i = 0; i < rows; i++) {
            //top-left diagonal           
            invalidateIfEmpty(x-i, y-i);
            //down-left diagonal       
            invalidateIfEmpty(x+i, y-i);
            //top-right diagonal        
            invalidateIfEmpty(x-i, y+i);
            //down-right diagonal    
            invalidateIfEmpty(x+i, y+i);
        }
    }
    
    /**
     * Invalidate the adjacents cell to the given position
     * (The king uses that)
     * 
     * @param x 
     * @param y 
     */
    private void invalidateAdjacent(int x, int y) {
        //top row
        invalidateIfEmpty(x-1, y+1);
        invalidateIfEmpty(x-1, y);
        invalidateIfEmpty(x-1, y-1);

        //center row
        invalidateIfEmpty(x, y+1);
        invalidateIfEmpty(x, y-1);

        //bottom row
        invalidateIfEmpty(x+1, y+1);
        invalidateIfEmpty(x+1, y);
        invalidateIfEmpty(x+1, y-1);
    }

    /**
     * Invalidates all the knights cell movements
     * from a given position
     * 
     * @param x 
     * @param y
     */
    private void invalidateKnights(int x, int y) {
        //Top-left area
        invalidateIfEmpty(x-2, y-1);
        invalidateIfEmpty(x-1, y-2);
        //Top-right area
        invalidateIfEmpty(x-2, y+1);
        invalidateIfEmpty(x-1, y+2);
        
        //Down-left area
        invalidateIfEmpty(x+1, y-2);
        invalidateIfEmpty(x+2, y-1);
                
        //Down-right area
        invalidateIfEmpty(x+1, y+2);
        invalidateIfEmpty(x+2, y+1);
    }

    /**
     * Invalidate row and column of the given position
     * 
     * @param x 
     * @param y 
     */
    private void invalidateRowAndColumn(int x, int y) {
        for(int j = 0; j < columns; j++) {
            invalidateIfEmpty(x, j);
            invalidateIfEmpty(j, y);
        }
    }

    /**
     * Invalidates the given cell if is equal to EMPTY_CODE
     * 
     * @param  x
     * @param  y
     * @return
     */
    private void invalidateIfEmpty(int x, int y) {
        if(x >= rows || y >= columns || x < 0 || y < 0) 
            return;

        if(board[x][y] == EMPTY_CODE) 
            board[x][y] = INVALID_POS;
    }

    /**
     * Returns true if the given position contains a piece
     * 
     * @param  x
     * @param  y
     * @return
     */
    private boolean isPiece(int x, int y) {
        if(x >= rows || y >= columns || x < 0 || y < 0) 
            return false;

        return board[x][y] != EMPTY_CODE && 
               board[x][y] != INVALID_POS  && 
               board[x][y] != UNAVAIABLE;
    }

    /**
     * Returns true if the given position contains a knight
     * @param  x
     * @param  y
     * @return
     */
    private boolean isKnight(int x, int y) {
        if(x >= rows || y >= columns || x < 0 || y < 0) 
            return false;        

        return board[x][y] == KNIGTHS_CODE;
    }

    /**
     * Returns true if the given positions has EMPTY_CODE
     * 
     * @param  x
     * @param  y
     * @return
     */
    private boolean isEmpty(int x, int y) {
        if(x >= rows || y >= columns || x < 0 || y < 0) 
            return false;

        return board[x][y] == EMPTY_CODE;
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
    	if(x > rows || y > columns || x < 0 || y < 0) 
            return false;    	

		for(int j = 0; j < columns; j++) 
            if(isPiece(x, j) || isPiece(j, y)) 
                return false; 		

        return true;
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
                //System.out.printf("| %c ", getCharFromPiece(board[i][j])); 
                System.out.print("| " + board[i][j]);
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
    
    /**
     * Gets the number of rows
     * 
     * @return int
     */
    public int getRowCount() {
    	return rows;
    }
    
    /**
     * Gets the number of columns
     * 
     * @return int
     */
    public int getColumnCount() {
        return columns;
    }
	
}
