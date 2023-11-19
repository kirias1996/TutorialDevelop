package com.sutaruhin.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sutaruhin.entity.User;
import com.sutaruhin.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService service;


	@GetMapping("/list")
	public String getList(Model model) {

		model.addAttribute("userlist", service.getUserList());
		return "user/list";
	}

	@GetMapping("/register")
	public String getRegister(@ModelAttribute User user) {
		return "user/register";
	}

	@PostMapping("/register")
	public String postRegister(@Validated User user,BindingResult res, Model model) {
		if(res.hasErrors()) {
			return getRegister(user);
		}
		service.saveUser(user);

		return "redirect:/user/list";
	}

	@GetMapping("/update/{id}/")
	public String getUser(@PathVariable("id") Integer id,User user,Model model) {
		if (id!=null) {
			model.addAttribute("user",service.getUser(id));
		}else {
			model.addAttribute("user",user);
		}


		return "user/update";
	}

	@PostMapping("/update/{id}/")
	public String postUser(@Validated User user, BindingResult res,Model model) {
		if(res.hasErrors()) {
			getUser(null,user, model);
			return "user/update";
		}
		service.saveUser(user);

		return "redirect:/user/list";
	}

	@PostMapping(path="list",params = "deleteRun")
	public String deleteRun(@RequestParam(name ="idck")Set <Integer> idck,Model model) {
		service.deleteUser(idck);

		return "redirect:/user/list";
	}
}
