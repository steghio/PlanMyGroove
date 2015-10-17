package scheduler;

import groovy.lang.GroovyShell;

public class GroovyRunner implements Runnable{
	
	private Job job;
	
	public GroovyRunner(Job job){
		this.job = job;
	}

	@Override
	public void run() {
		//mark job as running
		JobQueryController.updateJobStatus(job, Job.JOB_STATUS_RUNNING);
		GroovyShell shell = new GroovyShell();
		Object ret = null;
		Boolean is_error = false;
		try{
			//execute Groovy script
			ret = shell.evaluate(job.getJobCode());
		}catch(Exception e){
			//mark job as in error and set exception message as result
			JobQueryController.updateJobResult(job, e.getMessage(), Job.JOB_STATUS_ERROR);
			is_error = true;
		}
		if(!is_error){
			//mark job as completed and set return value as result
			JobQueryController.updateJobResult(job,String.valueOf(ret), Job.JOB_STATUS_COMPLETED);
		}
		
	}

}
