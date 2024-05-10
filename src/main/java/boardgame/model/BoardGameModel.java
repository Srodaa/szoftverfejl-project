package boardgame.model;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import puzzle.TwoPhaseMoveState;

import java.util.Set;

public class BoardGameModel implements TwoPhaseMoveState<Position> {

    public static final int BOARD_SIZE_X = 2;
    public static final int BOARD_SIZE_Y = 3;
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE_X][BOARD_SIZE_Y];
    private ReadOnlyIntegerWrapper numberOfMoves = new ReadOnlyIntegerWrapper();
    private ReadOnlyBooleanWrapper solved = new ReadOnlyBooleanWrapper();

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
        numberOfMoves.set(0);

    }
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    public int getNumberOfMoves() {
        return numberOfMoves.get();
    }

    public ReadOnlyIntegerProperty numberOfMovesProperty() {
        return numberOfMoves.getReadOnlyProperty();
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
        if (board[positionTwoPhaseMove.from().row()][positionTwoPhaseMove.from().col()].get() == Square.KING) {
            return isOnBoard(positionTwoPhaseMove.from()) && isOnBoard(positionTwoPhaseMove.to())
                    && !isEmpty(positionTwoPhaseMove.from()) && isEmpty(positionTwoPhaseMove.to())
                    && isKingMove(positionTwoPhaseMove.from(), positionTwoPhaseMove.to());
        } else if (board[positionTwoPhaseMove.from().row()][positionTwoPhaseMove.from().col()].get() == Square.BISHOP) {
            return isOnBoard(positionTwoPhaseMove.from()) && isOnBoard(positionTwoPhaseMove.to())
                    && !isEmpty(positionTwoPhaseMove.from()) && isEmpty(positionTwoPhaseMove.to())
                    && isBishopMove(positionTwoPhaseMove.from(), positionTwoPhaseMove.to());
        } else if (board[positionTwoPhaseMove.from().row()][positionTwoPhaseMove.from().col()].get() == Square.ROOK) {
            return isOnBoard(positionTwoPhaseMove.from()) && isOnBoard(positionTwoPhaseMove.to())
                    && !isEmpty(positionTwoPhaseMove.from()) && isEmpty(positionTwoPhaseMove.to())
                    && isRookMove(positionTwoPhaseMove.from(), positionTwoPhaseMove.to());
        }
        return false;
    }

    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        setSquare(positionTwoPhaseMove.to(), getSquare(positionTwoPhaseMove.from()));
        setSquare(positionTwoPhaseMove.from(), Square.NONE);
        numberOfMoves.set(numberOfMoves.get() + 1);
    }

    public static boolean isKingMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1 || dx * dy == 1;
    }


    public static boolean isBishopMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx * dy == 1;
    }

    public static boolean isRookMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1;
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
