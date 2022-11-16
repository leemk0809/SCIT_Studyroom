package net.scit.studyroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
	
	@GetMapping({"","/"})
	public String login() {
		
		return "loginForm";
	}
	
	@PostMapping("main")
	public String main() {
		
		return "loginForm";
	}
}
