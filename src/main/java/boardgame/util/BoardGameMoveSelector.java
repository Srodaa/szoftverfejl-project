package boardgame.util;

import boardgame.BoardGameController;
import boardgame.model.BoardGameModel;
import boardgame.model.Position;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import puzzle.TwoPhaseMoveState;

public class BoardGameMoveSelector  {

    public enum Phase{
        SELECT_FROM,
        SELECT_TO,
        READY_TO_MOVE
    }

    private BoardGameModel model;
    private static final Logger logger = LogManager.getLogger(BoardGameController.class);
    private ReadOnlyObjectWrapper<Phase> phase = new ReadOnlyObjectWrapper<>(Phase.SELECT_FROM);
    private boolean invalidSelection = false;
    private Position from;
    private Position to;
    TwoPhaseMoveState.TwoPhaseMove<Position> move;

    public BoardGameMoveSelector(BoardGameModel model) {
        this.model = model;
    }

    public Phase getPhase() {
        return phase.get();
    }

    public ReadOnlyObjectProperty<Phase> phaseProperty() {
        return phase.getReadOnlyProperty();
    }

    public boolean isReadyToMove(){
        logger.info(phase.get());
        return phase.get() == Phase.READY_TO_MOVE;
    }

    public void select(Position position){
        logger.info(phase.get() + " - select metódus");
        switch (phase.get()){
            case SELECT_FROM -> selectFrom(position);
            case SELECT_TO -> selectTo(position);
            case READY_TO_MOVE -> throw new IllegalStateException();
        }
    }

    private void selectFrom(Position position) {
        if (model.isLegalToMoveFrom(position)){
            from = position;
            phase.set(Phase.SELECT_TO);
            invalidSelection = false;
        } else {
            invalidSelection = true;
        }
    }

    private void selectTo(Position position) {
        move = new TwoPhaseMoveState.TwoPhaseMove<>(from, position);
        if (model.isLegalMove(move)){
            to = position;
            move = new TwoPhaseMoveState.TwoPhaseMove<>(from, to);
            phase.set(Phase.READY_TO_MOVE);
            invalidSelection = false;
        }else {
            invalidSelection = true;
            phase.set(Phase.SELECT_FROM);
        }
    }

    public void reset(){
        from = null;
        to = null;
        phase.set(Phase.SELECT_FROM);
        invalidSelection = false;
    }

    public void makeMove(){
        logger.info(move);
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        } else {
            model.makeMove(move);
            reset();
        }
    }



}
