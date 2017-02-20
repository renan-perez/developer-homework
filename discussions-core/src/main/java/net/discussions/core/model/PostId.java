package net.discussions.core.model;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Embeddable
public class PostId implements Serializable {

	private static final long serialVersionUID = 1L;
	private User user;
	private LocalDateTime dateTime;
	
	public PostId() {
	}
	
	public PostId(String email, LocalDateTime datetime) {
		this.user = new User(email);
		this.dateTime = datetime;
	}
	
	@ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@DateTimeFormat(iso = DATE_TIME)
	@Column(name = "date_time")
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
}
