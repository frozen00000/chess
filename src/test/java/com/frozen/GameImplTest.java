package com.frozen;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;

public class GameImplTest {

    @Test
    public void testSimpleGame() {
        testGame("e2 e4\n" +
                        "a1 a6\n" +
                        "a7 a6\n" +
                        "d1 h5\n" +
                        "a6 a5\n" +
                        "f1 c4\n" +
                        "a5 a4\n" +
                        "h5 f7",
                r -> assertTrue(r.endsWith("WHITE player has won!" + System.lineSeparator())));
    }

    @Test
    public void testExit() {
        testGame("invalid input\n/exit",
                r -> assertTrue(r.contains("Unexpected input format: invalid")));
    }

    @Test
    public void testInvalidMove() {
        testGame("a1 h8\n/exit",
                r -> assertTrue(r.contains("Move is not valid.")));
    }

    private void testGame(String input, Consumer<String> resultTester) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ConsoleView view = new ConsoleView(stream, new PrintStream(outContent));
        GameImpl game = new GameImpl(view, new BoardImpl(new PieceFactoryImpl(), true));
        game.start();
        resultTester.accept(outContent.toString());
    }

}