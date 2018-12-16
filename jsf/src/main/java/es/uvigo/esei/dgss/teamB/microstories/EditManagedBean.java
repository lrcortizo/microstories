package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.entities.Genre;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Theme;
import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("edit")
@RequestScoped
public class EditManagedBean {
	
	@EJB
	private StoryEJB storyEJB;

	private int id;
	
	private String title;
	private Genre genre;
	private Theme primaryTheme;
	private Theme secondaryTheme;
	private String text;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return storyEJB.findStory(id).getTitle();
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Story getStory() {
		return storyEJB.findStory(id);
	}
	
	public Genre getGenre() {	
		return storyEJB.findStory(id).getGenre();
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Theme getPrimaryTheme() {
		return storyEJB.findStory(id).getPrimaryTheme();
	}
	public void setPrimaryTheme(Theme primaryTheme) {
		this.primaryTheme = primaryTheme;
	}
	public Theme getSecondaryTheme() {
		return storyEJB.findStory(id).getSecondaryTheme();
	}
	public void setSecondaryTheme(Theme secondaryTheme) {
		this.secondaryTheme = secondaryTheme;
	}
	public String getText() {
		return storyEJB.findStory(id).getText();
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	public String doEdit(int id) {
		Story storyEdit = storyEJB.findStory(id);
		
		storyEdit.setTitle(title);
		storyEdit.setGenre(genre);
		storyEdit.setPrimaryTheme(primaryTheme);
		storyEdit.setSecondaryTheme(secondaryTheme);
		storyEdit.setText(text);
		
		storyEJB.updateStory(storyEdit);
		
		return "/home.xhtml?faces-redirect=true";
		
	}
}

