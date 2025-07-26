package refresh.workers;

public abstract class WorkerJob {
    public final String getJobId() {
        return this.getClass().getName();
    }

    protected abstract Class<?> getJobStateType();

    protected Object jobState;

    public void setJobState(Object jobState) {
        if(getJobStateType() != jobState.getClass())
            throw new RuntimeException("Instantiated invalid type");
        this.jobState = jobState;
    }

    public abstract void executeJob(WorkContext context);
}
