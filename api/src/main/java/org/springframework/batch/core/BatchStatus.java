package org.springframework.batch.core;

/**
 * Created by wanglijun on 16/11/20.
 */
public enum BatchStatus {
    COMPLETED,
    STARTING,
    STARTED,
    STOPPING,
    STOPPED,
    FAILED,
    ABANDONED,
    UNKNOWN;

    private BatchStatus() {
    }

    public static BatchStatus max(BatchStatus status1, BatchStatus status2) {
        return status1.isGreaterThan(status2)?status1:status2;
    }

    public boolean isRunning() {
        return this == STARTING || this == STARTED;
    }

    public boolean isUnsuccessful() {
        return this == FAILED || this.isGreaterThan(FAILED);
    }

    public BatchStatus upgradeTo(BatchStatus other) {
        return !this.isGreaterThan(STARTED) && !other.isGreaterThan(STARTED)?(this != COMPLETED && other != COMPLETED?max(this, other):COMPLETED):max(this, other);
    }

    public boolean isGreaterThan(BatchStatus other) {
        return this.compareTo(other) > 0;
    }

    public boolean isLessThan(BatchStatus other) {
        return this.compareTo(other) < 0;
    }

    public boolean isLessThanOrEqualTo(BatchStatus other) {
        return this.compareTo(other) <= 0;
    }

    public javax.batch.runtime.BatchStatus getBatchStatus() {
        return this == ABANDONED?javax.batch.runtime.BatchStatus.ABANDONED:(this == COMPLETED?javax.batch.runtime.BatchStatus.COMPLETED:(this == STARTED?javax.batch.runtime.BatchStatus.STARTED:(this == STARTING?javax.batch.runtime.BatchStatus.STARTING:(this == STOPPED?javax.batch.runtime.BatchStatus.STOPPED:(this == STOPPING?javax.batch.runtime.BatchStatus.STOPPING:javax.batch.runtime.BatchStatus.FAILED)))));
    }

    public static BatchStatus match(String value) {
        BatchStatus[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            BatchStatus status = arr$[i$];
            if(value.startsWith(status.toString())) {
                return status;
            }
        }

        return COMPLETED;
    }
}
