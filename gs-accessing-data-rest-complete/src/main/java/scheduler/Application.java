package scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		//start poller thread for submitted jobs
		//TODO add heartbeat logic or similar to detect failures and restart the thread
		JobRunner jr = new JobRunner();
		jr.run();
	}
}
