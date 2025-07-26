package refresh.workers.cwlib.jobs;

import refresh.workers.WorkContext;
import refresh.workers.WorkerJob;
import refresh.workers.cwlib.state.AssetListState;

public class TestJob extends WorkerJob {
    @Override
    protected Class<?> getJobStateType() {
        return AssetListState.class;
    }

    @Override
    public void executeJob(WorkContext context) {
        AssetListState state = (AssetListState)this.jobState;
    }
}
