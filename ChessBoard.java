

/**
 * Class that models the board object with a sequence of operations 
 * The board has to be square
 * 
 */
public class ChessBoard {

    private int rows;
    
    private int columns;
    
    private int[][] board;
    
    private StringBuilder hashCode;

    public static final int UNAVAIABLE     = -2;
    public static final int INVALID_POS    = -1;

    public static final int EMPTY_CODE            = 0;
    public static final int KING_CODE             = 1;
    public static final int QUEEN_CODE            = 2;
    public static final int BISHOP_CODE          = 3;
    public static final int ROOK_CODE            = 4;
    public static final int KNIGHT_CODE          = 5;
    
    
    /**
     * Constructor for copy objects 
     * 
     * @param cb
     */
    public ChessBoard(ChessBoard cb) {        
        this.rows = cb.getRowCount();
        this.columns = cb.getColumnCount();
        this.board = new int [this.rows][this.columns];
        
        int [][] auxBoard = cb.getBoard();        
        for(int i=0; i<this.rows; i++) {
            for(int j=0; j<this.columns; j++) {
                this.board[i][j] = auxBoard[i][j];
            }
        }
        
        this.hashCode = new StringBuilder(cb.getHashCode());        
    }

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
        hashCode= new StringBuilder();
        
        // empty board
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = EMPTY_CODE;
                hashCode.append(EMPTY_CODE); 
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
    public boolean putQueen(int x, int y) {
    	boolean result = false;

        if (isEmpty(x, y) && checkSafeRowAndColumn(x, y) 
                && checkSafeDiagonals(x, y) && checkSafeKnights(x, y)) {
            invalidateRowAndColumn(x, y);
            invalidateDiagonals(x, y);
            board[x][y] = QUEEN_CODE;
            hashCode.setCharAt(x * rows + y, Character.forDigit(QUEEN_CODE, 10));
            result = true;
        }


    	return result;
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
    public boolean putKing(int x, int y) {
    	boolean result = false;
    	if(isEmpty(x, y) && checkSafeAdjacent(x, y) && checkSafeKnights(x, y)){
			invalidateAdjacent(x, y);
			board[x][y] = KING_CODE;  
            hashCode.setCharAt(x*rows + y, Character.forDigit(KING_CODE, 10));			
			result = true;
    	}    	
    	return result;
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
    public boolean putBishop(int x, int y) {
        boolean result = false;
        if(isEmpty(x, y) && checkSafeDiagonals(x, y) && checkSafeKnights(x, y)) {
			invalidateDiagonals(x, y);
			board[x][y] = BISHOP_CODE;     
            hashCode.setCharAt(x*rows + y, Character.forDigit(BISHOP_CODE, 10));
            result = true;
    	}
    	return result;
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
    public boolean putRook(int x, int y) {
        boolean result = false;
    	if(isEmpty(x, y) && checkSafeRowAndColumn(x, y) && checkSafeKnights(x, y) ) {
			invalidateRowAndColumn(x, y);
			board[x][y] = ROOK_CODE;        
			hashCode.setCharAt(x*rows + y, Character.forDigit(ROOK_CODE, 10));
			result = true;
    	}    	
    	return result;
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
    public boolean putKnight(int x, int y) {
        boolean result = false;
    	if(isEmpty(x, y) && checkSafeKnights(x, y)){
			invalidateKnights(x, y);
			board[x][y] = KNIGHT_CODE;
			hashCode.setCharAt(x*rows + y, Character.forDigit(KNIGHT_CODE, 10));
			result = true;
    	}    	    
    	return result;
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

        return board[x][y] != EMPTY_CODE && board[x][y] != INVALID_POS;
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

        return board[x][y] == KNIGHT_CODE;
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
                System.out.printf("| %c ", getCharFromPiece(board[i][j])); 
                //System.out.print("| " + board[i][j]);
            }
            System.out.println("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print("+---");
            }
            System.out.println("+");
        }
        System.out.println("hashCode: " + getHashCode());
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
            case BISHOP_CODE:
                charCode = 'B';
                break;
            case ROOK_CODE:
                charCode = 'R';
                break;
            case KNIGHT_CODE:
                charCode = 'N';
                break;
            default:
            	charCode = ' ';
                break;
        }

        return charCode;
    }
    
    /**
     * Matrix transposition
     * 
     * @param m
     */
    private void transpose(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = i; j < m[0].length; j++) {
                int x = m[i][j];
                m[i][j] = m[j][i];
                m[j][i] = x;
            }
        }
    }
    
    /**
     * Matrix row swapping 
     * 
     * @param m
     */
    private static void swapRows(int[][] m) {
        for (int  i = 0, k = m.length - 1; i < k; ++i, --k) {
            int[] x = m[i];
            m[i] = m[k];
            m[k] = x;
        }
    }
    
    /**
     * Rotates the board matrix 90 degrees to the right 
     */
    public void rotate() {
        swapRows(board);
        transpose(board);        
        buildHashCode();
    }
    
    private void buildHashCode() {        
        for(int i=0; i < rows; i++) {
            for(int j=0; j < columns; j++) {
                char c = (board[i][j] == INVALID_POS)? '0' : Character.forDigit(board[i][j], 10);
                hashCode.setCharAt(i*rows + j, c); 
            }
        }
    }

    /**
     * Gets the hash code that identifies the board.
     * This is usefull for comparing boards
     * 
     * @return [description]
     */
    public String getHashCode() {
    	return hashCode.toString();
    }    
    
    public int getRowCount() {
    	return rows;
    }
    
    public int getColumnCount() {
        return columns;
    }
    
    public int[][] getBoard() {
        return board;
    }	
}
