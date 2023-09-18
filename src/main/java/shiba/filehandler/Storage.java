package shiba.filehandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shiba.exceptions.ShibaException;
import shiba.parsers.SpaceSeparatedValuesParser;
import shiba.tasks.ShibaTask;
import shiba.ui.Replier;

/**
 * Handles the saving and reading of tasks to and from the disk.
 */
public class Storage {
    private final String dataPath;

    /**
     * Creates a new Storage object.
     *
     * @param dataPath Path to the file to be used for saving and reading tasks.
     */
    public Storage(String dataPath) {
        this.dataPath = dataPath;
    }

    /**
     * Saves the tasks to the disk, creating the file if it does not exist.
     *
     * @param tasks List of tasks to be saved.
     * @throws ShibaException If there is an error saving the tasks.
     */
    public void saveTasks(List<ShibaTask> tasks) throws ShibaException {
        try {
            File file = new File(dataPath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            assert file.exists();
        } catch (IOException e) {
            throw new ShibaException("Error creating save file!");
        }

        try (FileWriter fw = new FileWriter(dataPath)) {
            boolean isFirstLineWritten = false;
            for (ShibaTask task : tasks) {
                if (isFirstLineWritten) {
                    fw.write("\n");
                }
                fw.write(task.toSaveString());
                isFirstLineWritten = true;
            }
        } catch (IOException e) {
            throw new ShibaException("Error saving tasks to file!");
        }
    }

    /**
     * Reads the saved tasks from the disk.
     *
     * @return List of tasks read from the disk.
     * @throws ShibaException If there is an error reading the tasks.
     */
    public List<ShibaTask> readSavedTasks() throws ShibaException {
        try {
            File file = new File(dataPath);

            ArrayList<ShibaTask> tasks = new ArrayList<>();
            if (!file.exists()) {
                return tasks;
            }

            Scanner scanner = new Scanner(file);
            boolean isErrorEncountered = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ShibaTask taskParsed = ShibaTask.fromSaveParams(SpaceSeparatedValuesParser.parse(line));
                if (taskParsed != null) {
                    tasks.add(taskParsed);
                } else {
                    isErrorEncountered = true;
                }
            }

            if (isErrorEncountered) {
                Replier.printWithNoIndents("Woof! One or more tasks could not be read from save file!");
            }

            return tasks;
        } catch (IOException e) {
            throw new ShibaException("Error reading tasks from save file! Consider running this program "
                    + "from another directory.");
        }
    }
}
