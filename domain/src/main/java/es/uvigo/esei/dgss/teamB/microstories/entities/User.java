package es.uvigo.esei.dgss.teamB.microstories.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.inclusiveBetween;
import static org.apache.commons.lang3.Validate.matchesPattern;

@Entity
@Inheritance
@DiscriminatorColumn(
	name = "role",
	discriminatorType = DiscriminatorType.STRING,
	length = 6
)
public abstract class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length = 100, nullable = false)
	protected String login;
	
	@Column(length = 32, nullable = false)
	protected String password;

	User() {}

	public User(String login, String password) {
		this.setLogin(login);
		this.changePassword(password);
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		requireNonNull(login, "login can't be null");
		inclusiveBetween(1, 100, login.length(), "login must have a length between 1 and 100");
		
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		requireNonNull(password, "password can't be null");
		matchesPattern(password, "[a-zA-Z0-9]{32}", "password must be a valid MD5 string");
		
		this.password = password.toUpperCase();
	}

	public void changePassword(String password) {
		requireNonNull(password, "password can't be null");
		if (password.length() < 6)
			throw new IllegalArgumentException("password can't be shorter than 6");
		
		try {
			final MessageDigest digester = MessageDigest.getInstance("MD5");
			final HexBinaryAdapter adapter = new HexBinaryAdapter();
	
			this.password = adapter.marshal(digester.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("MD5 algorithm not found", e);
		}
	}
	
}