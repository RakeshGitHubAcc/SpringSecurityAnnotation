package com.mytest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mytest.model.User;
import com.mytest.model.UserProfile;
import com.mytest.service.UserProfileService;
import com.mytest.service.UserService;

@Controller
public class HelloWorldController {

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	UserService userService;

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(ModelMap model) {
		model.addAttribute("greeting", "Hi, Welcome to mysite. ");
		return "welcome";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "admin";
	}

	@RequestMapping(value = "/db", method = RequestMethod.GET)
	public String dbaPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "dba";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		// return "welcome";
		return "redirect:/login?logout";
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		return "accessDenied";
	}

	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String newRegistration(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		return "newuser";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String saveRegistration(@Validated User user, BindingResult result,
			ModelMap model) {

		if (result.hasErrors()) {
			System.out.println("There are errors");
			return "newuser";
		}
		userService.save(user);

		System.out.println("First Name : " + user.getFirstName());
		System.out.println("Last Name : " + user.getLastName());
		System.out.println("Username : " + user.getUsername());
		System.out.println("Password : " + user.getPassword());
		System.out.println("Email : " + user.getEmail());
		System.out.println("Checking UsrProfiles....");
		if (user.getUserProfiles() != null) {
			for (UserProfile profile : user.getUserProfiles()) {
				System.out.println("Profile : " + profile.getType());
			}
		}

		model.addAttribute("success", "User " + user.getFirstName()
				+ " has been registered successfully");
		return "registrationsuccess";
	}

	@RequestMapping(value = { "/edit-user-{id}" }, method = RequestMethod.GET)
	public String editUser(@PathVariable int id, ModelMap model) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("edit", true);
		return "newuser";
	}

	@RequestMapping(value = { "/edit-user-{id}" }, method = RequestMethod.POST)
	public String updateUser(User user, ModelMap model, @PathVariable int id) {
		userService.updateUser(user);
		model.addAttribute("success", "User " + user.getFirstName()
				+ " updated successfully");
		return "success";
	}

	@RequestMapping(value = { "/delete-user-{id}" }, method = RequestMethod.GET)
	public String deleteUser(@PathVariable int id) {
		userService.deleteUser(id);
		return "redirect:/list";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String listAllUsers(ModelMap model) {
		List<User> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "allusers";
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@ModelAttribute("roles")
	public List<UserProfile> initializeProfiles() {
		return userProfileService.findAll();
	}
}
