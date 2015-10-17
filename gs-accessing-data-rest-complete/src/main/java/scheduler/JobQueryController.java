package scheduler;

import java.util.List;
import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobQueryController {

    private static JobRepository jobRepository;
	
    //get access to Spring repository object
	@Inject
    public JobQueryController(JobRepository jobRepo) {
		jobRepository = jobRepo;
    }

	/*These two methods are required as Spring REST with JPA forces us to return an object with the same type as the repo object
	now we can return custom objects instead, aka perform projection queries
	TODO change getJobResult to return all tuples in the form jobId,jobResult if there are multiple jobs with same jobId*/
	
    @RequestMapping(value = "/jobs/search/getJobResult", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String getJobResult(@RequestParam String jobId) {
    	List<Job> res = jobRepository.findByJobId(jobId);
    	//if we ha multiple jobs with the same name, it's best to use the ById method instead
    	//we always return the first object here
    	if(res!=null && !res.isEmpty())return res.get(0).getjobResult();
        return "NONE";
    }
    
    @RequestMapping(value = "/jobs/search/getJobResultById", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public @ResponseBody String getJobResultById(@RequestParam long id) {
    	Job j = jobRepository.findById(id);
    	if(j!=null)return j.getjobResult();
        return "NONE";
    }
    
    public static void updateJobStatus(Job j, String jobStatus){
    	j.setJobStatus(jobStatus);
		jobRepository.save(j);
    }
    
    public static void updateJobResult(Job j, String jobResult, String jobStatus){
    	j.setjobResult(jobResult);
    	j.setJobStatus(jobStatus);
		jobRepository.save(j);
    }
    
    public static List<Job> getJobsToSubmit(){
    	return jobRepository.getJobsToSubmit();
    }

}
