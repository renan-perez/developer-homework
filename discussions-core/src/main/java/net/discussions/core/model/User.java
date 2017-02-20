package net.discussions.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries({
	@NamedQuery(
			name = "User.getByEmail",
			query = "SELECT	u "
				+ 	"FROM	User	u "
				+ 	"WHERE	u.email = :email")
})

@Entity
@Table(schema = "renanpe_discussions", name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String name;
	
	public User() {
	}
	
	public User(String email) {
		this.email = email;
	}
	
	@Id
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
