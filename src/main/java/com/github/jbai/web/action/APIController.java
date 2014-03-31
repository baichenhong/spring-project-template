package com.github.jbai.web.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.jbai.APIException;
import com.github.jbai.entity.User;
import com.github.jbai.service.UserService;

@RestController
@RequestMapping(value = "/api",  produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

	private static final Logger logger = LoggerFactory.getLogger(APIController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping (value = "/users", method = RequestMethod.GET)
    public @ResponseBody List<User> findAllUsers() {
         return userService.findAllUsers();
    }

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> registerUser(@RequestBody User user, Model model) {
		User u = userService.addUser(user);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", "200");
		responseHeaders.set("x-message", "success");
		return new ResponseEntity<String>(String.valueOf(u.getId()), responseHeaders, HttpStatus.OK);
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> profile(@RequestParam("userId") long userId,  @RequestPart("image") MultipartFile file) throws Exception {
		userService.updateProfile(userId, file);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", "200");
		responseHeaders.set("x-message", "success");
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		int errorCode = 500;
		String errorMessage = e.getMessage();
		if(e instanceof APIException) {
			errorCode = ((APIException)e).getErrorCode();
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("x-code", String.valueOf(errorCode));
		responseHeaders.set("x-message", errorMessage);
		return new ResponseEntity<String>("", responseHeaders, HttpStatus.OK);
	}

}
