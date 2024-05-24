package boardgame.util;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link GameResultManager} that stores game results in a JSON file.
 */
public class JsonGameResultManager implements GameResultManager {

    private Path filePath;

    /**
     * Constructs a new {@link JsonGameResultManager} with the specified file path.
     * @param filePath the path to the JSON file
     */
    public JsonGameResultManager(@NonNull Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Adds a new game result to the JSON file.
     * @param result the game result to add
     * @return the list of all game results
     * @throws IOException if an I/O error occurs
     */
    @Override
    public List<GameResult> add(@NonNull GameResult result) throws IOException {
        var results = getAll();
        results.add(result);
        try (var out = Files.newOutputStream(filePath)) {
            JacksonHelper.writeList(out, results);
        }
        return results;
    }

    /**
     * {@return the list of all game results}
     *
     * @throws IOException if an I/O error occurs
     */
    public List<GameResult> getAll() throws IOException {
        if (!Files.exists(filePath)) {
            return new ArrayList<GameResult>();
        }
        try (var in = Files.newInputStream(filePath)) {
            return JacksonHelper.readList(in, GameResult.class);
        }
    }

}
