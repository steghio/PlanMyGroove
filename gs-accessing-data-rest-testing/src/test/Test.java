package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//TODO move this to a proper JUnit test
public class Test {

	public static void main(String[] args) {
		
	/*URL: http://localhost:8181/jobs
	 * add sample jobs
POST {  "jobId" : "myJob",  "jobCode" : "return true"}
POST {  "jobId" : "myOtherJob",  "jobCode" : "return 2**2"}
POST {  "jobId" : "myFailedJob"}
	 * */
		String input1 = "{  \"jobId\" : \"myJob\",  \"jobCode\" : \"return true\"}";
		String input2 = "{  \"jobId\" : \"myOtherJob\",  \"jobCode\" : \"return 2**2\"}";
		String input3 = "{  \"jobId\" : \"myFailedJob\"}";
		
		String[] sample_inputs = {input1, input2, input3};
		
		HttpURLConnection post_conn, get_conn;
		OutputStream os;
	  try {

		//create sample jobs
		String base_url = "http://localhost:8181/jobs";
		
		for(int i=0; i<sample_inputs.length; i++){
			URL post_url = new URL(base_url);
			post_conn = (HttpURLConnection) post_url.openConnection();
			post_conn.setDoOutput(true);
			post_conn.setRequestMethod("POST");
			post_conn.setRequestProperty("Content-Type", "application/json");

			os = post_conn.getOutputStream();
			os.write(sample_inputs[i].getBytes());
			os.flush();
			
			if (post_conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "+ post_conn.getResponseCode());
			}

			post_conn.disconnect();
		}
		
		//allow for the scripts to end
		Thread.sleep(5000);
		

		/* list current jobs
		 * GET http://localhost:8181/jobs/search/getSubmittedJobs
		 * list completed jobs
		 * GET http://localhost:8181/jobs/search/getCompletedJobs
		 * check if myOtherJob returned 4
		 * GET http://localhost:8181/jobs/search/getJobResultById?id=2
		 * delete myFailedJob
		 * DELETE http://localhost:8181/jobs/3
		 * check if myFailedJob still exists
		 * GET http://localhost:8181/jobs/search/findByJobId?jobId=myFailedJob
		 * */
		//list current jobs
		URL get_url = new URL(base_url+"/search/getSubmittedJobs");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ get_conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		String output;
		System.out.println("Get submitted jobs: \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		get_conn.disconnect();
		
		//list completed jobs
		get_url = new URL(base_url+"/search/getCompletedJobs");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ get_conn.getResponseCode());
		}

		br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		System.out.println("Get completed jobs: \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		get_conn.disconnect();
		
		//retrieve job result
		get_url = new URL(base_url+"/search/getJobResultById?id=2");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ get_conn.getResponseCode());
		}

		br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		System.out.println("Check if myOtherJob returned 4\n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		get_conn.disconnect();
		
		//delete job
		get_url = new URL(base_url+"/3");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("DELETE");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 204) {
			throw new RuntimeException("Failed : HTTP error code : "+ get_conn.getResponseCode());
		}

		get_conn.disconnect();
		
		//check if job is actually deleted
		get_url = new URL(base_url+"/search/findByJobId?jobId=myFailedJob");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "+ get_conn.getResponseCode());
		}

		br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		System.out.println("Check if myFailedJob still exists after deletion\n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		get_conn.disconnect();
		
	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	 } catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
}
