package boardgame.model;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import puzzle.State;

import java.util.Set;

public class BoardGameModel implements puzzle.State {

    public static final int BOARD_SIZE_X = 2;
    public static final int BOARD_SIZE_Y = 3;
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE_X][BOARD_SIZE_Y];

    public BoardGameModel() {
        for (var i = 0; i < BOARD_SIZE_X; i++) {
            board[i][0] = new ReadOnlyObjectWrapper<Square>(
                    switch (i) {
                        case 0 -> Square.KING;
                        case BOARD_SIZE_X - 1 -> Square.ROOK;
                        default -> Square.NONE;
                    });

            board[i][1] = new ReadOnlyObjectWrapper<Square>(
                    switch (i) {
                        case 0 -> Square.BISHOP;
                        case BOARD_SIZE_X - 1 -> Square.ROOK;
                        default -> Square.NONE;
                    });

            board[i][2] = new ReadOnlyObjectWrapper<Square>(
                    switch (i) {
                        case 0 -> Square.BISHOP;
                        case BOARD_SIZE_X - 1 -> Square.NONE;
                        default -> Square.NONE;
                    });

        }
    }
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    @Override
    public boolean isSolved() {
        return false;
    }

    @Override
    public boolean isLegalMove(Object o) {
        return false;
    }

    @Override
    public void makeMove(Object o) {

    }

    @Override
    public Set getLegalMoves() {
        return Set.of();
    }

    @Override
    public State clone() {
        return null;
    }
}
