package net.discussions.core.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({
	@NamedQuery(
			name = "Post.listPostsByUserEmail",
			query = "SELECT	p "
				+ 	"FROM	Post	p "
				+ 	"WHERE	p.id.user.email	= :email "
				+ 	"AND	p.superiorPost.id.user.email	is null "
				+ 	"AND	p.superiorPost.id.dateTime		is null"
	),
	
	@NamedQuery(
			name = "Post.listPostsBySuperPostId",
			query = "SELECT	p "
				+ 	"FROM	Post	p "
				+ 	"WHERE	p.superiorPost.id.user.email	= :email "
				+ 	"AND	p.superiorPost.id.dateTime		= :dateTime"
	),
	
	@NamedQuery(
			name = "Post.countResponse",
			query = "SELECT	count(p) "
				+ 	"FROM	Post	p "
				+ 	"WHERE	p.superiorPost.id.user.email	= :email "
				+ 	"AND	p.superiorPost.id.dateTime		= :dateTime"
	),
})

@Entity
@Table(schema = "renanpe_discussions", name = "post")
public class Post implements Serializable {

	private static final long serialVersionUID = 1L;
	private PostId id;
	private String content;
	private Location location;
	private Long currentTemperature;
	private List<Post> responses;
	private Post superiorPost;
	private Long responseAmount;
	
	public Post() {
	}
	
	public Post(String email) {
		this.id = new PostId(email);
	}
	
	@EmbeddedId
	public PostId getId() {
		return id;
	}

	public void setId(PostId id) {
		this.id = id;
	}
	
	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "location_id", referencedColumnName = "id", insertable = true, updatable = false)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	@Column(name = "current_temperature")
	public Long getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(Long currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "superiorPost", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	public List<Post> getResponses() {
		return responses;
	}

	public void setResponses(List<Post> responses) {
		this.responses = responses;
	}
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "superior_post_user_email", referencedColumnName = "user_email", insertable = true, updatable = false),
		@JoinColumn(name = "superior_post_date_time", referencedColumnName = "date_time", insertable = true, updatable = false),
	})
	public Post getSuperiorPost() {
		return superiorPost;
	}
	
	public void setSuperiorPost(Post superiorPost) {
		this.superiorPost = superiorPost;
	}

	@Transient
	public Long getResponseAmount() {
		return responseAmount;
	}

	public void setResponseAmount(Long responseAmount) {
		this.responseAmount = responseAmount;
	}
	
}
