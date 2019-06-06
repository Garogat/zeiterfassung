package zeiterfassung.xml;

import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Helper Class for filling Tasks with Random WorkChunks
 *
 */
public class TaskRandomizedTimeFiller {
    private int minIntervalSizeSeconds = 20;
    private int maxIntervalSizeSeconds = 36000;
    private int seed = 753;
    private int numberPosition;
    private Random random = new Random(seed);
    private int minValues;
    private int maxValues;
    private LocalDateTime lastTime;

    /**
     *
     * @param time start time for the first generated workChunks + randomized Pause @NotNull
     * @param nStart number used for the description of the WorkChunks, it's value is automatically incremented
     *               during the generation additional of WorkChunks
     * @param minValues minimal number of WorkChunks to generate ( >= 0)
     * @param maxValues max number of WorkChunks to generate ( >= minValues)
     */
    TaskRandomizedTimeFiller(LocalDateTime time, int nStart, int minValues, int maxValues) {
        this.minValues = minValues;
        this.maxValues = maxValues;
        lastTime = time;
        numberPosition = nStart;
    }

    /**
     *
     * @param time start time for the first generated workChunks + randomized Pause
     * @param nStart number used for the internal description of the WorkChunks, it's value is automatically incremented
     *      *               during the generation of additional WorkChunks
     * @param nValues exact number of WorkChunks to generate
     */
    TaskRandomizedTimeFiller(LocalDateTime time, int nStart, int nValues) {
        this.minValues = nValues;
        this.maxValues = nValues;
        lastTime = time;
        numberPosition = nStart;
    }

    /**
     * method for filling the task
     * can be used for an arbitrary number of tasks
     * @param @NotNull task
     */
        public void fill(Task task) {
        int n = random.nextInt(maxValues - minValues) + minValues;
        for(int i=0; i< n; ++i) {
            task.addWorkChunk(createTimeProgressedWorkChunk());
        }
    }
    private LocalDateTime progressTime() {
        int rValue = random.nextInt((maxIntervalSizeSeconds - minIntervalSizeSeconds) + minIntervalSizeSeconds);
        lastTime = lastTime.plusSeconds(rValue);
        return lastTime;
    }

    /**
     * the inner TimeState of the generator
     * @return lastTime
     */
    public LocalDateTime getTime() {
        return lastTime;
    }

    /**
     * creates a WorkChunks with randomly progressed start & end time
     * @return a new WorkChunk
     */
    public WorkChunk createTimeProgressedWorkChunk() {
        WorkChunk wChunk = new WorkChunk();
        wChunk.setDescription("back to work for the "+(numberPosition++)+" time");
        //take break
        wChunk.setStartTime(progressTime());
        //actual work time
        wChunk.setEndTime(progressTime());
        return wChunk;
    }
}
