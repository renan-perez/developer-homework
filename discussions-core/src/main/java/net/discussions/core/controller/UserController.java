package net.discussions.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.discussions.core.business.UserBusiness;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Response;
import net.discussions.core.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired private UserBusiness userBusiness;
	
	@RequestMapping(value = "/getUser/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Response<User> getUser(@PathVariable String email) {
		try {
			return new Response<User>(HttpStatus.OK, userBusiness.getUser(email));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<User>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to find user! Try againg latter.");
		}
	}
	

	@RequestMapping(value = "/listUsers", method = RequestMethod.GET)
	public Response<List<User>> listUsers() {
		try {
			return new Response<List<User>>(HttpStatus.OK, userBusiness.listUsers());
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to list users! Try againg latter.");
		}
	}
	
	@RequestMapping(value = "/saveOrUpdateUser", method = RequestMethod.POST)
	public Response<User> saveOrUpdateUser(@RequestBody User user) {
		try {
			return new Response<User>(HttpStatus.OK, userBusiness.saveOrUpdateUser(user));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<User>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to save user! Try againg latter.");
		}
	}
		
}
