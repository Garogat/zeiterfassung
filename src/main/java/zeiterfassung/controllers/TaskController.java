package zeiterfassung.controllers;

import zeiterfassung.models.Task;

public class TaskController {
    private Task task;

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
}
