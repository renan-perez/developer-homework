package net.discussions.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.discussions.core.business.PostBusiness;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Post;
import net.discussions.core.model.PostId;
import net.discussions.core.model.Response;
import net.discussions.core.util.DateUtil;

@RestController
@RequestMapping("/posts")
public class PostController {
	
	@Autowired 
	private PostBusiness postBusiness;
	
	@RequestMapping(value = "/saveOrUpdatePost", method = RequestMethod.POST)
	public Response<Post> saveOrUpdatePost(@RequestBody Post post) {
		try {
			return new Response<Post>(HttpStatus.OK, postBusiness.saveOrUpdatePost(post));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<Post>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to save post! Try again latter.");
		}
	}
	
	@RequestMapping(value = "/deletePost", method = RequestMethod.POST)
	public Response<Post> deletePost(@RequestBody PostId id) {
		try {
			postBusiness.deletePost(id);
			return new Response<Post>(HttpStatus.OK);
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<Post>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to save post! Try again latter.");
		}
	}
	
	@RequestMapping(value = "/getPostById", method = RequestMethod.GET)
	public Response<Post> getPostById(@RequestBody PostId postId) {
		try {
			return new Response<Post>(HttpStatus.OK, postBusiness.getPostById(postId));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<Post>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to find post! Try again latter.");
		}
	}
	
	@RequestMapping(value = "/listPostsByUserEmail/{email}", method = RequestMethod.GET)
	public Response<List<Post>> listPostsByUserEmail(@PathVariable String email) {
		try {
			return new Response<List<Post>>(HttpStatus.OK, postBusiness.listPostsByUserEmail(email));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<List<Post>>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying find post(s)! Try again latter.");
		}
	}
	
	@RequestMapping(value = "/listResponses/{email}/{dateTime}", method = RequestMethod.GET)
	public Response<List<Post>> listResponses(@PathVariable String email, @PathVariable String dateTime) {
		try {
			return new Response<List<Post>>(HttpStatus.OK, postBusiness.listResponses(
																new PostId(email, DateUtil.stringToLocalDateTime(dateTime))));
		} catch (SystemException e) {
			e.printStackTrace();
			return new Response<List<Post>>(HttpStatus.INTERNAL_SERVER_ERROR, "Error trying to find response(s)! Try again latter.");
		}
	}
	
	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public Response<String> listResponses() {
		return new Response<String>(HttpStatus.OK, "", new String("SUCCESS!"));
	}
		
}
