package boardgame.util;

/**
 * Represents the result of a game.
 * @param playerName the name of the player
 * @param getSteps the number of steps the player took to win the game
 */
public record GameResult(String playerName, int getSteps) {
}
