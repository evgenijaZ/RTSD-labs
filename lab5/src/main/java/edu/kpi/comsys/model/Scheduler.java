package edu.kpi.comsys.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {
    private int now = 0;

    private List<Integer> rejects = new ArrayList<>();
    private List<Integer> load = new ArrayList<>();
    private List<Integer> finished = new ArrayList<>();
    private List<Integer> times = new ArrayList<>();
    private List<Task> tasks;
    private List<Task> queue = new ArrayList<>();

    public Scheduler() {
        rejects.add(0);
        finished.add(0);
        tasks = Arrays.asList(new Task(now, new Generator(0)), new Task(now, new Generator(1)), new Task(now, new Generator(2)));
    }

    private void rejectTasks() {
        int sizeBefore = queue.size();
        queue = queue.stream().filter(task -> task.getDeadLine() < now).collect(Collectors.toList());
        int reject = rejects.get(rejects.size() - 1) + sizeBefore - queue.size();
        rejects.add(reject);
    }

    private List<Task> taskOnTime() {
        List<Task> result = new ArrayList<>();
        tasks.stream().filter(task -> task.getStartTime() == now).forEach(task -> {
            result.add(new Task(task));
            task.reinitialize(now);
            times.add(task.getProcessTime());
        });
        return result;
    }

    private void incrementTime() {
        now++;
        rejectTasks();
        List<Task> newTasks = taskOnTime();
        queue.addAll(newTasks);
        load.add(queue.size());
    }

    private void process(Task task) {
        for (int i = 0; i < task.getProcessTime(); i++) {
            incrementTime();
        }
    }

    private Task getTaskFromQueue() {
        if (queue.isEmpty()) return null;
        return rateMonotonic();
    }

    private Task earliestDeadLineFirst() {
        long minTaskDeadline = queue.get(0).getDeadLine();
        Task minTask = queue.stream().filter(task -> task.getDeadLine() < minTaskDeadline).findAny().orElse(queue.get(0));
        queue.remove(minTask);
        return minTask;
    }

    private Task rateMonotonic(){
        long minTaskProcessTime = queue.get(0).getDeadLine();
        Task minTask = queue.stream().filter(task -> task.getProcessTime() < minTaskProcessTime).findAny().orElse(queue.get(0));
        queue.remove(minTask);
        return minTask;
    }

    private boolean lookForTask() {
        boolean result;
        Task task = getTaskFromQueue();
        int finished = this.finished.get(this.finished.size() - 1);
        if (task != null) {
            process(task);
            finished++;
            result = true;
        } else result = false;
        this.finished.add(finished);
        return result;
    }

    public void run() {
        for (int i = 0; i < 1000; i++) {
            incrementTime();
            while (lookForTask()) {
                continue;
            }
        }
    }

    public List<Integer> getLoad() {
        return load;
    }

    public List<Integer> getRejects() {
        return rejects;
    }

    public List<Integer> getTimes() {
        return times;
    }

    public List<Integer> getFinished() {
        return finished;
    }
}
