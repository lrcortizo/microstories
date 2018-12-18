package es.uvigo.esei.dgss.teamB.microstories;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import es.uvigo.esei.dgss.teamB.microstories.entities.Genre;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Theme;

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
	
	public void onload() {
		this.title = storyEJB.findStory(id).getTitle();
		this.genre = storyEJB.findStory(id).getGenre();
		this.primaryTheme = storyEJB.findStory(id).getPrimaryTheme();
		this.secondaryTheme = storyEJB.findStory(id).getPrimaryTheme();
		this.text = storyEJB.findStory(id).getText();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {		
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
	public Genre getGenre() {	
		return this.genre; 
	}
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	public Theme getPrimaryTheme() {
		return this.primaryTheme;
		
	}
	public void setPrimaryTheme(Theme primaryTheme) {
		this.primaryTheme = primaryTheme;
	}
	
	public Theme getSecondaryTheme() {
		return this.secondaryTheme;
	}
	
	public void setSecondaryTheme(Theme secondaryTheme) {
		this.secondaryTheme = secondaryTheme;
	}
	public String getText() {
		return this.text;
		
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

