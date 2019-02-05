package io.github.radd.mgrtestserver.util;

public class Stat {
    private Double CPU;
    private Double memory;
    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getCPU() {
        return CPU;
    }

    public void setCPU(Double CPU) {
        this.CPU = CPU;
    }

    public Double getMemory() {
        return memory;
    }

    public void setMemory(Double memory) {
        this.memory = memory;
    }
}
