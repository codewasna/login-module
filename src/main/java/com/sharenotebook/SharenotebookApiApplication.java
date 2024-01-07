package com.sharenotebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SharenotebookApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SharenotebookApiApplication.class, args);
	}
	
	@GetMapping("/")
	public String app() {
		return "Share Notebook App";
	}
	
	@GetMapping("/admin/test")
	public String admin() {
		return "Admin : Share Notebook App";
	}
	
	@GetMapping("/user/test")
	public String user() {
		return "User : Share Notebook App";
	}

}
