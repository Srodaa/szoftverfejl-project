package boardgame.model;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import puzzle.TwoPhaseMoveState;
import puzzle.solver.BreadthFirstSearch;

import java.util.HashSet;
import java.util.Set;

/**
 * A model of a board game.
 * The game is played on a 2x3 board.
 */
public class BoardGameModel implements TwoPhaseMoveState<Position> {

    private static final int BOARD_SIZE_X = 2;
    private static final int BOARD_SIZE_Y = 3;
    private ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[BOARD_SIZE_X][BOARD_SIZE_Y];
    private ReadOnlyIntegerWrapper numberOfMoves = new ReadOnlyIntegerWrapper();

    /**
     * Constructs a new {@link BoardGameModel} with the initial state.
     * The board is initialized with the following configuration:
     * <pre>
     *     KING  BISHOP  BISHOP
     *     ROOK  ROOK  NONE
     * </pre>
     */
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

    /**
     * @param i the row index
     * @param j the column index
     * @return the square at the specified position in the board
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * @return the number of moves made so far
     */
    public ReadOnlyIntegerProperty numberOfMovesProperty() {
        return numberOfMoves.getReadOnlyProperty();
    }

    private Square getSquare(Position position) {
        return board[position.row()][position.col()].get();
    }

    private void setSquare(Position position, Square square) {
        board[position.row()][position.col()].set(square);
    }

    private static boolean isOnBoard(Position p) {
        return 0<=p.row() && p.row() < BOARD_SIZE_X && 0<=p.col() && p.col() < BOARD_SIZE_Y;
    }

    private boolean isEmpty(Position p) {
        return getSquare(p) == Square.NONE;
    }

    /**
     * {@return {@code true} if the puzzle is solved; {@code false} otherwise}
     */
    @Override
    public boolean isSolved() {
        return board[0][0].get() == Square.BISHOP && board[0][1].get() == Square.BISHOP && board[0][2].get() == Square.NONE
                && board[1][0].get() == Square.ROOK && board[1][1].get() == Square.ROOK && board[1][2].get() == Square.KING;
    }

    /**
     * @param positionTwoPhaseMove the move
     * {@return {@code true} if the move is legal; {@code false} otherwise}
     */
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

    /**
     * Makes the specified move.
     * The move is assumed to be legal.
     * @param positionTwoPhaseMove the move
     */
    @Override
    public void makeMove(TwoPhaseMove<Position> positionTwoPhaseMove) {
        setSquare(positionTwoPhaseMove.to(), getSquare(positionTwoPhaseMove.from()));
        setSquare(positionTwoPhaseMove.from(), Square.NONE);
        numberOfMoves.set(numberOfMoves.get() + 1);
    }

    private static boolean isKingMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1 || dx * dy == 1;
    }


    private static boolean isBishopMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx * dy == 1;
    }

    private static boolean isRookMove(Position from, Position to) {
        var dx = Math.abs(to.row() - from.row());
        var dy = Math.abs(to.col() - from.col());
        return dx + dy == 1;
    }

    /**
     * {@return the legal moves}
     */
    @Override
    public Set<TwoPhaseMove<Position>> getLegalMoves() {
        Set<TwoPhaseMove<Position>> legalMoves = new HashSet<>();
        for (int rwfrom = 0; rwfrom < BOARD_SIZE_X; rwfrom++){
            for (int clfrom = 0; clfrom <BOARD_SIZE_Y; clfrom++){
                Position fromPos = new Position(rwfrom, clfrom);
                for (int rwto = 0; rwto < BOARD_SIZE_X; rwto++){
                    for (int clto = 0; clto < BOARD_SIZE_Y; clto++) {
                        Position toPos = new Position(rwto, clto);
                        TwoPhaseMove<Position> move = new TwoPhaseMove<>(fromPos, toPos);
                        if (isLegalMove(move)){
                            legalMoves.add(move);
                        }
                    }
                }
            }
        }


        return legalMoves;
    }

    /**
     * @param from the position
     * {@return {@code true} if it is legal to move from the specified position; {@code false} otherwise}
     */
    @Override
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from);
    }

    /**
     * {@return a string representation of the board}
     */
    @Override
    public TwoPhaseMoveState<Position> clone() {
        BoardGameModel copy;
        try {
            copy = (BoardGameModel) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        copy.board = new ReadOnlyObjectWrapper[BOARD_SIZE_X][BOARD_SIZE_Y];
        for (int rw = 0; rw < BOARD_SIZE_X; rw++){
            for (int cl = 0; cl < BOARD_SIZE_Y; cl++){
                copy.board[rw][cl] = new ReadOnlyObjectWrapper<>(this.board[rw][cl].get());
            }
        }
        return copy;
    }

    /**
     * @param o the object to compare
     * {@return a string representation of the board}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardGameModel that)) return false;
        for (int rw = 0; rw < BOARD_SIZE_X; rw++){
            for (int cl = 0; cl < BOARD_SIZE_Y; cl++){
                if (!this.getSquare(new Position(rw, cl)).equals(that.getSquare(new Position(rw, cl))))
                    return false;
            }
        }
        return true;

    }



    public static void main(String[] args) {
        /**
         * Solves the puzzle using breadth-first search and prints the solution.
         */
        new BreadthFirstSearch<TwoPhaseMove<Position>>()
                .solveAndPrintSolution(new BoardGameModel());
    }
}
