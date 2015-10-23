package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class JUTest {
	
	String base_url = "http://localhost:8181/jobs";
	
	HttpURLConnection get_conn;
	OutputStream os;
	
	/* GET http://localhost:8181/jobs/search/getSubmittedJobs should return empty list (no submitted jobs)
	 * GET http://localhost:8181/jobs/search/getCompletedJobs should return non empty list (some completed jobs)
	 * GET http://localhost:8181/jobs/search/getJobResultById?id=2 should return 4
	 * DELETE http://localhost:8181/jobs/3 should return 204 (deleted OK)
	 * GET http://localhost:8181/jobs/3 should return 404 (cannot find item because of previous deletion)
	 * */
	
	@Test
	public void testRetrieveSubmitted(){
		try{
		URL get_url = new URL(base_url+"/search/getSubmittedJobs");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			System.err.println("testRetrieveSubmitted Failed : HTTP error code : "+ get_conn.getResponseCode());
			assert(false);
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		String output= br.readLine();
		
		assertEquals("{ }", output);

		get_conn.disconnect();
		}catch(Exception e){
			assert(false);
		}
	}
	
	@Test
	public void testRetrieveCompleted(){
		try{
		URL get_url = new URL(base_url+"/search/getCompletedJobs");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			System.err.println("testRetrieveCompleted Failed : HTTP error code : "+ get_conn.getResponseCode());
			assert(false);
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		String output= br.readLine();

		assertNotEquals("{ }", output);

		get_conn.disconnect();
		}catch(Exception e){
			assert(false);
		}
	}
	
	@Test
	public void testRetrieveResult(){
		try{
		URL get_url = new URL(base_url+"/search/getJobResultById?id=2");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		if (get_conn.getResponseCode() != 200) {
			System.err.println("testRetrieveResult Failed : HTTP error code : "+ get_conn.getResponseCode());
			assert(false);
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader((get_conn.getInputStream())));

		String output= br.readLine();

		assertEquals("4", output);

		get_conn.disconnect();
		}catch(Exception e){
			assert(false);
		}
	}
	
	@Test
	public void testDelete(){
		try{
		URL get_url = new URL(base_url+"/3");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("DELETE");
		get_conn.setRequestProperty("Accept", "application/json");

		assertEquals(204, get_conn.getResponseCode());

		get_conn.disconnect();
		}catch(Exception e){
			assert(false);
		}
	}
	
	
	@Test
	public void testDeletion(){
		try{
		URL get_url = new URL(base_url+"/3");
		get_conn = (HttpURLConnection) get_url.openConnection();
		get_conn.setRequestMethod("GET");
		get_conn.setRequestProperty("Accept", "application/json");

		assertEquals(404, get_conn.getResponseCode());

		get_conn.disconnect();
		}catch(Exception e){
			assert(false);
		}
	}

}
