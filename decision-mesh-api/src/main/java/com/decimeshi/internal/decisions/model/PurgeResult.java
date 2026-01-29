package com.decimeshi.internal.decisions.model;

public class PurgeResult {

    public int purgedCount;
    public boolean dryRun;

    public PurgeResult() {}

    public PurgeResult(int purgedCount, boolean dryRun) {
        this.purgedCount = purgedCount;
        this.dryRun = dryRun;
    }
}
