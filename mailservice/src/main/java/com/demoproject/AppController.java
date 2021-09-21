package com.demoproject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AppController {

	@GetMapping("/index")
	public String getLoginPage() {
		
		System.out.println("Home Page Call");
		return "index";
	}
	
	@GetMapping("/home")
	public String getHomePage() {
		
		System.out.println("mail");
		return "home";
	}
}
