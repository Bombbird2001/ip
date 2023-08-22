package tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadlineTask extends ShibaTask {
    private static final Pattern deadlineRegex = Pattern.compile("(.+?) /by (.+)");

    /**
     * Parses a DeadlineTask from a command.
     * @param cmd The command to be parsed.
     * @return The DeadlineTask parsed from the command, or null if the command is invalid.
     */
    public static DeadlineTask fromCmd(String cmd) {
        String[] cmdSplit = cmd.split(" ", 2);
        if (cmdSplit.length != 2) {
            return null;
        }

        Matcher matcher1 = deadlineRegex.matcher(cmdSplit[1]);
        if (matcher1.find()) {
            return new DeadlineTask(matcher1.group(1), matcher1.group(2));
        }

        return null;
    }

    private final String deadline;

    public DeadlineTask(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
}
