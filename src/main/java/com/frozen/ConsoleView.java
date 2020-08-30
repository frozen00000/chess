package com.frozen;

import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@RequiredArgsConstructor
class ConsoleView implements View {

    private final Scanner scanner;
    private final PrintStream outputStream;

    ConsoleView(InputStream inputStream, PrintStream outputStream) {
        scanner = new Scanner(inputStream);
        this.outputStream = outputStream;
    }

    @Override
    public void showMessage(String message) {
        outputStream.println(message);
    }

    @Override
    public void showBoard(BoardView boardView) {
        printLabels();
        printLine();
        for (byte y = 0; y < BoardImpl.SIZE; y++) {
            String v = Integer.toString(8 - y);
            outputStream.print(v + "|");
            for (byte x = 0; x < BoardImpl.SIZE; x++) {
                outputStream.print(boardView.getPiece(Point.of(x, y)).getSymbol() + "|");
            }
            outputStream.println(v);
            printLine();
        }
        printLabels();
    }

    private void printLine() {
        outputStream.print(" ");
        for (int k = 0; k < BoardImpl.SIZE; k++) {
            outputStream.print("+－");
        }
        outputStream.println("+");
    }

    private void printLabels() {
        outputStream.print(" ");
        for (int y = 0; y < BoardImpl.SIZE; y++) {
            outputStream.print(" ");
            outputStream.print((char)('Ａ' + y));
        }
        outputStream.println();
    }

    @Override
    public String waitUserInput(Color player) {
        outputStream.print(player.toString() + " >");
        return scanner.nextLine();
    }

}
