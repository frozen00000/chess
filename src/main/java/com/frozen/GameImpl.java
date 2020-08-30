package com.frozen;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * The controller of game.
 * It uses View for interaction with user and Board as encapsulated game object.
 * Supports several command.
 */
public class GameImpl implements Game {

    private static final String HELP = "/help";
    private static final String GIVE_UP = "/giveup";
    private static final String EXIT = "/exit";
    private static final Map<String, String> SUPPORTED_COMMANDS = Map.of(
            "/help", "Prints this help.",
            "/giveup", "Give up.",
            "/exit", "Close this game."
    ); // Command -> Description
    private static final String HELP_MESSAGE =
            " *** Help *** " + System.lineSeparator() +
                    "Valid format for move: [a-h]{1}[1-8]{1}\\s[a-h]{1}[1-8]{1}" + System.lineSeparator() +
                    "Example: 'a2 a3'" + System.lineSeparator() +
                    "Supported commands:" + System.lineSeparator() +
                    SUPPORTED_COMMANDS.entrySet().stream().map(e -> String.format("%s - %s", e.getKey(), e.getValue()))
                            .collect(Collectors.joining(System.lineSeparator()));

    private final Board board;
    private Color currentPlayer = Color.WHITE;
    private final View view;
    private boolean finished = false;

    GameImpl(View view, Board board) {
        this.view = view;
        this.board = board;
    }

    private void showHelp() {
        view.showMessage(HELP_MESSAGE);
    }

    private void finish(Color winner) {
        finished = true;
        view.showMessage(String.format("Game is over. %s player has won!", winner.toString()));
    }

    private void finish() {
        finished = true;
        view.showMessage("Game is over. Stalemate!");
    }

    private void handleCommand(String command) {
        switch (command) {
            case HELP:
                showHelp();
                break;
            case GIVE_UP:
                finish(currentPlayer.opposite());
                break;
            case EXIT:
                finished = true;
                break;
            default:
                view.showMessage(String.format("Unsupported command: '%s'. Enter '/help' for more info.", command));
        }
    }

    private void handleMove(String userInput) {
        try {
            Move move = new Move(userInput);
            if (board.getPiece(move.getFrom()).getColor() != currentPlayer) {
                throw new IllegalArgumentException("Illegal access to piece. User can move only own pieces.");
            }
            MoveResult action = board.move(move);
            switch (action) {
                case OK:
                    switchPlayer();
                    break;
                case FAILED:
                    view.showMessage("Move is not valid.");
                    break;
                case CHECK:
                    switchPlayer();
                    view.showMessage("Check.");
                    break;
                case STALEMATE:
                    finish();
                    break;
                case CHECKMATE:
                    finish(currentPlayer);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("Action %s does not supported.", action));
            }
        } catch (IllegalArgumentException e) {
            view.showMessage(e.getMessage());
        }
    }

    @Override
    public void start() {
        showHelp();
        while (!finished) {
            view.showBoard(board);
            String userInput = view.waitUserInput(currentPlayer);
            if (userInput.startsWith("/")) {
                handleCommand(userInput);
            } else {
                handleMove(userInput);
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.opposite();
    }

}
