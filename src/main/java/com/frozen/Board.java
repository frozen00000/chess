package com.frozen;

interface Board extends BoardView {

   /**
    * Performs move of piece on the board.
    * @param move move to perform.
    * @return instance of enum MoveResult.
    */
   MoveResult move(Move move);

}
