package shiba.commands;

import shiba.exceptions.ShibaException;
import shiba.tasks.ShibaTask;
import shiba.tasks.TaskList;
import shiba.ui.Replier;

public class DeleteCommand extends ShibaCommand {
    private final String[] params;

    /**
     * Constructor for DeleteCommand, which deletes a task from the task list
     *
     * @param tasks Current state of task list
     * @param cmd Full command string
     */
    public DeleteCommand(TaskList tasks, String cmd) {
        super(tasks);
        params = cmd.split(" ");
    }

    @Override
    public void execute() throws ShibaException {
        int taskNumber = checkTaskNumber(params);
        ShibaTask task = tasks.removeIndex(taskNumber - 1);

        Replier.printHorizontalLine();
        Replier.printWithLevel2Indent("Woof! I've deleted this task:");
        Replier.printWithLevel3Indent(task.toString());
        String taskWord = tasks.size() == 1 ? " task" : " tasks";
        Replier.printWithLevel2Indent("You now have " + tasks.size() + taskWord + " in the list. Some headpats please?");
        Replier.printHorizontalLine();
        tasks.save();
    }
}