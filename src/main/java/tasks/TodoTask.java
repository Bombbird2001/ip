package tasks;

import exceptions.ShibaException;

public class TodoTask extends ShibaTask {
    /**
     * Parses a TodoTask from a command.
     * @param cmd The command to be parsed.
     * @return The TodoTask parsed from the command, or null if the command is invalid.
     */
    public static TodoTask fromCmd(String cmd) throws ShibaException {
        String[] cmdSplit = cmd.split(" ", 2);
        if (cmdSplit.length != 2) {
            throw new ShibaException("Todo name should not be empty!");
        }

        return new TodoTask(cmdSplit[1]);
    }

    public TodoTask(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}