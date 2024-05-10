package boardgame.model;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import puzzle.TwoPhaseMoveState;

import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Set;

public class BoardGameModel implements TwoPhaseMoveState<Position> {

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


    public Square getSquare(Position position) {
        return board[position.row()][position.col()].get();
    }

    public void setSquare(Position position, Square square) {
        board[position.row()][position.col()].set(square);
    }

    public static boolean isOnBoard(Position p) {
        return 0<=p.row() && p.row() < BOARD_SIZE_X && 0<=p.col() && p.col() < BOARD_SIZE_Y;
    }

    public boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    @Override
    public boolean isSolved() {
        return board[0][0].get() == Square.BISHOP && board[0][1].get() == Square.BISHOP && board[0][2].get() == Square.NONE
                && board[1][0].get() == Square.ROOK && board[1][1].get() == Square.ROOK && board[1][2].get() == Square.KING;
    }

    @Override
    public boolean isLegalMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        return true;
    }

    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        setSquare(positionTwoPhaseMove.to(), getSquare(positionTwoPhaseMove.from()));
        setSquare(positionTwoPhaseMove.from(), Square.NONE);
    }

    public static boolean isKingMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1 || dx * dy == 1;
    }


    @Override
    public Set getLegalMoves() {
        return Set.of();
    }

    @Override
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from);
    }

    @Override
    public TwoPhaseMoveState<Position> clone() {
        return null;
    }
}
