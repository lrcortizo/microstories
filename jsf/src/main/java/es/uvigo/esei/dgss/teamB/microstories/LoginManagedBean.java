package es.uvigo.esei.dgss.teamB.microstories;

import java.security.Principal;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Named("login")
@RequestScoped
public class LoginManagedBean {
	@Inject
	private Principal currentUserPrincipal;
	
	@Inject
	private HttpServletRequest request;
	
	private String login;
	private String password;
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String doLogin() {
		System.out.println(this.getLogin());
		try {
			request.login(this.getLogin(), this.getPassword());
			return redirectTo("/explore.xhtml");
			
		} catch (ServletException e) {
			return this.getViewId();
		}
	}
	
	public String doLogout() throws ServletException {
		request.logout();
		
		return redirectTo("/index.xhtml");
	}
	
	public Principal getCurrentUser() {
		return this.currentUserPrincipal;
	}
	
	private String redirectTo(String url) {
		return url  + "?faces-redirect=true";
	}
	
	private String getViewId() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}
	
}