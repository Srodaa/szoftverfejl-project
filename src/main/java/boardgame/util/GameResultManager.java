package boardgame.util;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Manages game results.
 */
public interface GameResultManager {
    /**
     * Adds a new game result.
     * @param result the game result to add
     * @return the list of all game results
     * @throws IOException if an I/O error occurs
     */
    List<GameResult> add(GameResult result) throws IOException;

    /**
     * {@return the list of all game results}
     *
     * @throws IOException if an I/O error occurs
     */
    List<GameResult> getAll() throws IOException;

    /**
     * {@return the list of the best game results}
     *
     * @param limit the maximum number of results to return
     * @throws IOException if an I/O error occurs
     */
    default List<GameResult> getBest(int limit) throws IOException {
        return getAll()
                .stream()
                .sorted(Comparator.comparingInt(GameResult::getSteps))
                .limit(limit)
                .toList();
    }

}
