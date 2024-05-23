package boardgame.model;

/**
 * Represents the different types of squares on the board.
 * The board can contain a king, a bishop, a rook, or no piece.
 */
public enum Square {
    /**
     * The {@code NONE} represents an empty square.
     */
    NONE,
    /**
     * The {@code KING} represents a king piece.
     */
    KING,
    /**
     * The {@code BISHOP} represents a bishop piece.
     */
    BISHOP,
    /**
     * The {@code ROOK} represents a rook piece.
     */
    ROOK
}
