package com.frozen;


class Main {

    public static void main(String[] args) {
        new GameImpl(
                new ConsoleView(System.in, System.out),
                new BoardImpl(new PieceFactoryImpl(), true)
        ).start();
    }

}


