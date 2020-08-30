package com.frozen;

interface View {

    void showMessage(String message);

    void showBoard(BoardView boardView);

    String waitUserInput(Color player);

}
