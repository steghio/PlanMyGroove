package scheduler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "jobs", path = "jobs")
public interface JobRepository extends CrudRepository<Job, Long>{

	//id is unique
	Job findById(@Param("id") long id);
	
	//jobId might be duplicated
	List<Job> findByJobId(@Param("jobId") String jobId);
	
	List<Job> findByJobStatus(@Param("jobStatus") String jobStatus);
	
	@Query("select j from Job j")
	List<Job> getAllJobs();
	
	//newly uploaded jobs always have NULL status
	@Query("select j from Job j where j.jobStatus is null")
	List<Job> getJobsToSubmit();
	
	@Query("select j from Job j where j.jobStatus = 'SUBMITTED'")
	List<Job> getSubmittedJobs();
	
	@Query("select j from Job j where j.jobStatus = 'RUNNING'")
	List<Job> getRunningJobs();
	
	@Query("select j from Job j where j.jobStatus = 'COMPLETED'")
	List<Job> getCompletedJobs();
	
	@Query("select j from Job j where j.jobStatus = 'ERROR'")
	List<Job> getErrorJobs();

}
