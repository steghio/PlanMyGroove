package test;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class JUTestSetUp {
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
	
	String base_url = "http://localhost:8181/jobs";
	
	HttpURLConnection post_conn, get_conn;
	OutputStream os;

	@Test
	public void setUp() {
		try{
		//create sample jobs
		
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
				System.err.println("Failed : HTTP error code : "+ post_conn.getResponseCode());
			}

			post_conn.disconnect();
		}
		//allow some time for creation and execution
		Thread.sleep(5000);
		
		}catch(Exception e){
			System.err.println(e.getLocalizedMessage());
		}
	}

}
