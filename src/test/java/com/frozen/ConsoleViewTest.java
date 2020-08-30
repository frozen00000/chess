package com.frozen;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class ConsoleViewTest {

    @Test
    public void showBoard() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(System.in, new PrintStream(outContent));

        view.showBoard(new BoardImpl(new PieceFactoryImpl(), true));
        String ls = System.lineSeparator();
        String expected =
                "  Ａ Ｂ Ｃ Ｄ Ｅ Ｆ Ｇ Ｈ" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "8|♜|♞|♝|♛|♚|♝|♞|♜|8" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "7|♟|♟|♟|♟|♟|♟|♟|♟|7" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "6|　|　|　|　|　|　|　|　|6" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "5|　|　|　|　|　|　|　|　|5" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "4|　|　|　|　|　|　|　|　|4" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "3|　|　|　|　|　|　|　|　|3" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "2|♙|♙|♙|♙|♙|♙|♙|♙|2" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "1|♖|♘|♗|♕|♔|♗|♘|♖|1" + ls +
                " +－+－+－+－+－+－+－+－+" + ls +
                "  Ａ Ｂ Ｃ Ｄ Ｅ Ｆ Ｇ Ｈ" + ls;
        assertEquals(expected, outContent.toString());
    }
}