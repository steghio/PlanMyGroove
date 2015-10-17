package scheduler;

import java.util.List;

public class JobRunner implements Runnable{

	@Override
	public void run() {
		//keep polling for new submitted jobs
		while(true){
				List<Job> jobs_to_submit = JobQueryController.getJobsToSubmit();
				if (jobs_to_submit!=null && !jobs_to_submit.isEmpty()){
					for(Job j : jobs_to_submit){
						//mark job as submitted to avoid double processing
						JobQueryController.updateJobStatus(j, Job.JOB_STATUS_SUBMITTED);
						//execute job in separate thread
						//TODO add timeout logic or similar to avoid endless/stuck threads
						GroovyRunner g = new GroovyRunner(j);
						g.run();
					}
				}
		}
		
	}

}
