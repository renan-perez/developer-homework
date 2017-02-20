package net.discussions.core.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.discussions.core.dao.LocationDAO;
import net.discussions.core.dao.PostDAO;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Post;
import net.discussions.core.model.PostId;

@Service
public class PostBusiness {
	
	@Autowired
	private PostDAO postDAO;
	@Autowired
	private LocationDAO locationDAO;
	
	@Transactional
	public Post saveOrUpdatePost(final Post post) throws SystemException {
		if (post.getLocation().getId() == null)
			locationDAO.saveOrUpdate(post.getLocation());
		return postDAO.saveOrUpdate(post);
	}
	
	@Transactional
	public void deletePost(final PostId id) throws SystemException {
		postDAO.delete(id);
	}
	
	public Post getPostById(final PostId id) throws SystemException {
		return postDAO.get(id);
	}
	
	public List<Post> listPostsByUserEmail(final String email) throws SystemException {
		List<Post> responses = (List<Post>) postDAO.listPostsByUserEmail(email);
		return countPostResponses(responses);
	}
	
	public List<Post> listResponses(final PostId superPostId) throws SystemException {
		List<Post> responses = (List<Post>) postDAO.listPostsBySuperPostId(superPostId);
		return countPostResponses(responses);
	}
	
	private List<Post> countPostResponses(List<Post> responses) {
		responses.forEach(r -> {
			Long amount = postDAO.countResponses(r.getId());
			r.setResponseAmount(amount == null ? 0 : amount);
		});
		return responses;
	}
	
}
