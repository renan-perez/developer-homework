package net.discussions.core.dao;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.discussions.core.exception.SystemException;
import net.discussions.core.model.Post;
import net.discussions.core.model.PostId;

@Repository
public class PostDAO extends GenericDAO<Post, PostId> {
	
	public Post saveOrUpdate(final Post post) throws SystemException {
		if (post.getId().getDateTime() == null) {
			post.getId().setDateTime(LocalDateTime.now());
			return super.save(post);
		} else {
			return super.update(post);
		}
	}
	
	@Transactional
	public void delete(final PostId id) throws SystemException {
		super.delete(Post.class, id);
	}
	
	public Post get(final PostId id) throws SystemException {
		return super.get(Post.class, id);
	}
	
	public Collection<Post> listPostsByUserEmail(final String email) throws SystemException {
		Map<String, Object> paramValueMap = new HashMap<>();
		paramValueMap.put("email", email);
		return super.listByNamedQuery("Post.listPostsByUserEmail", paramValueMap, Post.class);
	}
	
	public Collection<Post> listPostsBySuperPostId(final PostId superPostId) throws SystemException {
		Map<String, Object> paramValueMap = new HashMap<>();
		paramValueMap.put("email", superPostId.getUser().getEmail());
		paramValueMap.put("dateTime", superPostId.getDateTime());
		return super.listByNamedQuery("Post.listPostsBySuperPostId", paramValueMap, Post.class);
	}
	
	public Long countResponses(final PostId superPostId) {
		Map<String, Object> paramValueMap = new HashMap<>();
		paramValueMap.put("email", superPostId.getUser().getEmail());
		paramValueMap.put("dateTime", superPostId.getDateTime());
		return super.countByNamedQuery("Post.countResponse", paramValueMap);
	}
	
}
