package edu.kpi.comsys.model;

class Task {
    private Generator generator;
    private int startTime;
    private int processTime;
    private int deadLine;

    Task(int now, Generator generator) {
        this.generator= generator;
        startTime = generator.generateStartTime(now);
        processTime = generator.generateProcessTime();
        deadLine = startTime + processTime * 4;
    }

    void reinitialize(int now) {
        startTime = generator.generateStartTime(now);
        processTime = generator.generateProcessTime();
        deadLine = startTime + processTime * 4;
    }

    Task(Task source) {
        this.generator = source.generator;
        this.startTime = source.startTime;
        this.processTime = source.processTime;
        this.deadLine = source.deadLine;
    }

    int getStartTime() {
        return startTime;
    }

    int getProcessTime() {
        return processTime;
    }

    int getDeadLine() {
        return deadLine;
    }
}
