package boardgame.model;

import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.Test;
import puzzle.TwoPhaseMoveState;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    BoardGameModel gameModel;
    BoardGameModel gameModel1;

    @Test
    void isSolved_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(1, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 2), new Position(0, 2)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 2)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 0), new Position(0, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(0, 0)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(0, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(0, 2)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 2), new Position(0, 1)));
        assertFalse(gameModel.isSolved());
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(1, 2)));
        assertFalse(gameModel.isSolved());
    }

    @Test
    void isSolved_True() {
        gameModel = new BoardGameModel();
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(1, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 2), new Position(0, 2)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 2)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 0), new Position(0, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(0, 0)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(0, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(0, 2)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 2), new Position(0, 1)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 2), new Position(1, 2)));
        assertTrue(gameModel.isSolved());
    }

    @Test
    void isLegalMoveKing_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 0), new Position(1, 2))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,0 ), new Position(0, 1))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,0 ), new Position(-1, 0))));
    }

    @Test
    void isLegalMoveKing_True() {
        gameModel = new BoardGameModel();
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertTrue(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 0), new Position(1, 1))));
   }

    @Test
    void isLegalMoveBishop_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(0, 0))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,1), new Position(1, 1))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,1), new Position(-1, 0))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,1), new Position(1, 0))));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0,1), new Position(1, 1))));
    }

    @Test
    void isLegalMoveBishop_True() {
        gameModel = new BoardGameModel();
        assertTrue(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 2))));
    }

    @Test
    void isLegalMoveRook_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 0), new Position(1, 2))));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,0), new Position(1, -1))));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 2)));
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1,0), new Position(0, 1))));
    }

    @Test
    void isLegalMoveRook_True() {
        gameModel = new BoardGameModel();
        assertTrue(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2))));
    }

    @Test
    void isLegalMove_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isLegalMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 2), new Position(1, 1))));
    }


    @Test
    void getLegalMoves_True() {
        gameModel = new BoardGameModel();
        Set<TwoPhaseMoveState.TwoPhaseMove<Position>> legalMoves = new HashSet<>();
        legalMoves.add(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(0, 1), new Position(1, 2)));
        legalMoves.add(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertEquals(legalMoves, gameModel.getLegalMoves());
    }

    @Test
    void isLegalToMoveFrom_False() {
        gameModel = new BoardGameModel();
        assertFalse(gameModel.isLegalToMoveFrom(new Position(1, 2)));
        assertFalse(gameModel.isLegalToMoveFrom(new Position(-1, 0)));
    }
    @Test
    void isLegalToMoveFrom_True() {
        gameModel = new BoardGameModel();
        assertTrue(gameModel.isLegalToMoveFrom(new Position(1, 1)));
        assertTrue(gameModel.isLegalToMoveFrom(new Position(0, 1)));
    }

    @Test
    void equals_True(){
        gameModel = new BoardGameModel();
        gameModel1 = new BoardGameModel();
        assertEquals(gameModel, gameModel1);
        gameModel1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertEquals(gameModel, gameModel1);
    }
    @Test
    void equals_False(){
        gameModel = new BoardGameModel();
        gameModel1 = new BoardGameModel();
        gameModel1.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertNotEquals(gameModel, gameModel1);
    }
    @Test
    void testClone() {
        gameModel = new BoardGameModel();
        assertEquals(gameModel, gameModel.clone());
    }

    @Test
    void squareProperty_True(){
        gameModel = new BoardGameModel();
        ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[2][3];
        board[0][1] = new ReadOnlyObjectWrapper<>(Square.BISHOP);
        assertEquals(board[0][1].getValue(), gameModel.squareProperty(0, 1).getValue());
    }

    @Test
    void squareProperty_False(){
        gameModel = new BoardGameModel();
        ReadOnlyObjectWrapper<Square>[][] board = new ReadOnlyObjectWrapper[2][3];
        board[0][1] = new ReadOnlyObjectWrapper<>(Square.ROOK);
        assertNotEquals(board[0][1].getValue(), gameModel.squareProperty(0, 1).getValue());
    }

    @Test
    void numberOfMovesProperty_True(){
        gameModel = new BoardGameModel();
        ReadOnlyObjectWrapper<Integer> numberOfMoves = new ReadOnlyObjectWrapper<>(1);
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertEquals(numberOfMoves.getValue(), gameModel.numberOfMovesProperty().getValue());
    }

    @Test
    void numberOfMovesProperty_False(){
        gameModel = new BoardGameModel();
        ReadOnlyObjectWrapper<Integer> numberOfMoves = new ReadOnlyObjectWrapper<>(0);
        gameModel.makeMove(new TwoPhaseMoveState.TwoPhaseMove<>(new Position(1, 1), new Position(1, 2)));
        assertNotEquals(numberOfMoves.getValue(), gameModel.numberOfMovesProperty().getValue());
    }

}