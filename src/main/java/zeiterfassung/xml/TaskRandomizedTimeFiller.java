package zeiterfassung.xml;

import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;
import java.time.LocalDateTime;
import java.util.Random;

public class TaskRandomizedTimeFiller {
    private int minIntervalSizeSeconds = 20;
    private int maxIntervalSizeSeconds = 36000;
    private int seed = 20;
    private int numberPosition;
    private Random random = new Random(seed);

    private int nValues;
    private LocalDateTime lastTime;

    TaskRandomizedTimeFiller(LocalDateTime time, int nStart, int nValues) {
        this.nValues = nValues;
        lastTime = time;
        numberPosition = nStart;
    }

    public void fill(Task task) {
        for(int i=0; i<nValues; ++i) {
            task.addWorkChunk(createTimeProgressedWorkChunk());
        }
    }
    private LocalDateTime progressTime() {
        int rValue = random.nextInt((maxIntervalSizeSeconds - minIntervalSizeSeconds) + minIntervalSizeSeconds);
        lastTime = lastTime.plusSeconds(rValue);
        return lastTime;
    }

    public LocalDateTime getTime() {
        return lastTime;
    }
    WorkChunk createTimeProgressedWorkChunk() {
        WorkChunk wChunk = new WorkChunk();
        wChunk.setDescription("back to work for the "+(numberPosition++)+" time");
        wChunk.setStartTime(progressTime());
        wChunk.setEndTime(progressTime());
        return wChunk;
    }
}
