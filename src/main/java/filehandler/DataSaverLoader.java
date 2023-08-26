package filehandler;

import exceptions.ShibaException;
import parsers.SpaceSeparatedValuesParser;
import tasks.ShibaTask;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataSaverLoader {
    private static final String FILE_PATH = "./shibaData/tasks.txt";

    /**
     * Saves the tasks to the disk, creating the file if it does not exist.
     *
     * @param tasks List of tasks to be saved.
     * @throws ShibaException If there is an error saving the tasks.
     */
    public static void saveTasks(List<ShibaTask> tasks) throws ShibaException {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new ShibaException("Error creating save file!");
        }

        try(FileWriter fw = new FileWriter(FILE_PATH)) {
            boolean firstLineWritten = false;
            for (ShibaTask task : tasks) {
                if (firstLineWritten) {
                    fw.write("\n");
                }
                fw.write(task.toSaveString());
                firstLineWritten = true;
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
    public static List<ShibaTask> readSavedTasks() throws ShibaException {
        try {
            File file = new File(FILE_PATH);

            ArrayList<ShibaTask> tasks = new ArrayList<>();
            if (!file.exists()) {
                return tasks;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ShibaTask taskParsed = ShibaTask.fromSaveParams(SpaceSeparatedValuesParser.parse(line));
                if (taskParsed != null) {
                    tasks.add(taskParsed);
                }
            }

            return tasks;
        } catch (IOException e) {
            throw new ShibaException("Error reading tasks from file!");
        }
    }
}