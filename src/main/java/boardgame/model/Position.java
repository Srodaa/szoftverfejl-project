package boardgame.model;

/**
 * A record representing a position on a 2D board.
 * @param row the row index
 * @param col the column index
 */
public record Position(int row, int col) {

    /**
     * {@return a string representation of the position}
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", row, col);
    }
}
