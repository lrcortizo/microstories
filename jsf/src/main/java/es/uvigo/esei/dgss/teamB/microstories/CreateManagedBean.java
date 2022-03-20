package es.uvigo.esei.dgss.teamB.microstories;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import es.uvigo.esei.dgss.teamB.microstories.entities.Genre;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Theme;

@Named("create")
@RequestScoped
public class CreateManagedBean {

	@EJB
	private StoryEJB storyEJB;

	private String title = "title";
	private Genre genre;
	private Theme primaryTheme;
	private Theme secondaryTheme;
	private String text;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Theme getPrimaryTheme() {
		return primaryTheme;
	}

	public void setPrimaryTheme(Theme primaryTheme) {
		this.primaryTheme = primaryTheme;
	}

	public Theme getSecondaryTheme() {
		return secondaryTheme;
	}

	public void setSecondaryTheme(Theme secondaryTheme) {
		this.secondaryTheme = secondaryTheme;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String doCreate() {

		Story story = new Story();

		story.setTitle(title);
		story.setGenre(genre);
		story.setPrimaryTheme(primaryTheme);
		story.setSecondaryTheme(secondaryTheme);
		story.setText(text);

		storyEJB.createStory(story);

		return "/mystories.xhtml";

	}
}