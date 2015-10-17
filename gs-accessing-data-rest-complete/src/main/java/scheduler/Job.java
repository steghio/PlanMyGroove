package scheduler;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Job {
	
	public static final String JOB_STATUS_SUBMITTED = "SUBMITTED";
	public static final String JOB_STATUS_RUNNING = "RUNNING";
	public static final String JOB_STATUS_COMPLETED = "COMPLETED";
	public static final String JOB_STATUS_ERROR = "ERROR";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String jobId;
	/*TODO disable user ability to submit a job WITH jobStatus during creation. 
	 * No negative effects, but it's not useful now to let users submit this field at creation time */
	private String jobResult;
	//TODO either use a file or a different type for jobCode to allow for more complex scripts
	private String jobCode;
	private String jobStatus;
	
	//required since we're using Spring REST with JPA and we added custom constructors
	public Job(){}
	
	public Job(String jobId, String jobResult, String jobCode, String jobStatus){
		this.jobId = jobId;
		this.jobResult = jobResult;
		this.jobCode = jobCode;
		this.jobStatus = jobStatus;
	}

	public String getjobId() {
		return jobId;
	}

	public void setjobId(String jobId) {
		this.jobId = jobId;
	}

	public String getjobResult() {
		return jobResult;
	}

	public void setjobResult(String jobResult) {
		this.jobResult = jobResult;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
}
