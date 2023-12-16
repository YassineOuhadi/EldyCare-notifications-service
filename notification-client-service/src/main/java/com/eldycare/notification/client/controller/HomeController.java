package com.eldycare.notification.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Yassine Ouhadi
 *
 */
@Controller
@RequestMapping(value = "/notifications")
public class HomeController {

	@GetMapping
	public String index(Model model) {
		return "index";
	}

}
