package sla.reporter.database.nosql;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.reporter.model.Subscriber;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Enum singleton with a single-element used for file data storage.
 */
public enum JsonFileManager {

    /**
     * The instance.
     */
    INSTANCE;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileManager.class);

    private final String folder = System.getProperty("user.dir") + File.separator + "storage";

    private final HashMap<String, Subscriber> memoization = new HashMap<>();

    /**
     * Adds the subscriber to the file.
     *
     * @param subscriber The subscriber to be written.
     */
    public boolean writeObject(final Subscriber subscriber) {
        final JSONObject jsonObject = subscriber.getJsonObject();
        createFolderIfNotExist();
        if (isSubscriberExist(subscriber.getEmail())) {
            LOGGER.info("Subscriber: {} already existed", subscriber);
            return false;
        }
        synchronized (this) {
            try (final FileWriter file = new FileWriter(folder + File.separator + subscriber.getEmail())) {
                file.write(jsonObject.toJSONString());
                return true;
            } catch (final IOException e) {
                return false;
            }
        }
    }

    /**
     * Gets the subscriber from the file by given email.
     *
     * @param email The email.
     */
    public Subscriber readObject(final String email) throws Exception {
        if (memoization.containsKey(email)) {
            return memoization.get(email);
        }
        final JSONParser parser = new JSONParser();
        final JSONObject jsonObject;
        synchronized (this) {
            try (final FileReader file = new FileReader(folder + File.separator + email)) {
                jsonObject = (JSONObject) parser.parse(file);
            }
        }
        final Subscriber subscriber = new Subscriber.Builder()
                .fromJsonObject(jsonObject)
                .build();
        memoization.put(email, subscriber);
        return subscriber;
    }

    /**
     * Checks whether a subscriber with given email exists.
     * @param email The expexted email.
     * @return {@code true} if the subscriber exists, {@code false} otherwise.
     */
    private boolean isSubscriberExist(final String email) {
        final File subscriber = new File(folder + File.separator + email);
        return subscriber.isFile();
    }

    /**
     * Creates the storage folder if not exist.
     */
    private void createFolderIfNotExist() {
        final File directory = new File(folder);
        if (!directory.exists()){
            directory.mkdir();
        }
    }
}
