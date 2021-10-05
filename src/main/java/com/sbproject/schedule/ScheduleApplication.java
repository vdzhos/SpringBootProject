package com.sbproject.schedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleApplication {

	@Value("${do.something}")
	private static String g;

//	@Autowired
//	private static HelloService helloService;

	public static void main(String[] args) {
		System.out.println(g);
		SpringApplication.run(ScheduleApplication.class, args);

//		helloService.helloService();
	}

}
