package zeiterfassung.components;

import zeiterfassung.models.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ActiveWorkChunk {
    private TimeRegistrationRoot root;

    private WorkChunk activeWorkChunk;

    private boolean isWorkChunkActive(WorkChunk wc){
        return (wc.getStartTime() != null && wc.getEndTime()==null);
    }

    private void findActiveWorkChunkInTaskList(List<Task> taskList){
        for (Task task : taskList) {
            task.getWorkList(workChunkList -> {
                for (WorkChunk workChunk : workChunkList) {
                    if (isWorkChunkActive(workChunk)) {
                        activeWorkChunk = workChunk;
                        return;
                    }
                }
            });
        }
    }

    private void findActiveWorkChunk(){
        activeWorkChunk = null;
        root.getAreas(areaList -> {
            for (Area area : areaList) {
                area.getProjectList(projectList -> {
                    for (Project project : projectList) {
                        project.getTasks(taskList -> {
                            findActiveWorkChunkInTaskList(taskList);

                        });
                        project.getSubProjects(subProjectList -> {
                            for (SubProject subProject : subProjectList) {
                                subProject.getTasks(taskList -> {
                                    findActiveWorkChunkInTaskList(taskList);
                                });
                            }

                        });
                    }
                });
            }
        });
    }

    public WorkChunk getActiveWorkChunk() {
        findActiveWorkChunk();
        return activeWorkChunk;
    }

    static public boolean isWorkChunkInTask(WorkChunk workChunk, Task task){
        AtomicBoolean result = new AtomicBoolean(false);
        task.getWorkList(list -> {
            for (WorkChunk chunk : list) {
                if (chunk == workChunk) {
                    result.set(true);
                    return;
                }
            }
        });
        return result.get();
    }

    public ActiveWorkChunk(TimeRegistrationRoot root){
        this.root = root;

    }
}
