package es.uvigo.esei.dgss.teamB.microstories;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.inclusiveBetween;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * Class Story
 * It represents Entity Story in database with its columns as parameters
 * Setters methods implements several validations 
 */
@Entity
public class Story {

	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, length = 1000)
	private String text;

	@Column(nullable = false, length = 50)
	private String author;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date publicationDate;

	public enum Genre {
		STORY, POETRY, NANOSTORY
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 9)
	private Genre storyGenre;

	public enum Theme {
		ADVENTURE, SCIENCEFICTION, HISTORY, CHILDREN, ROMANTIC, THRILLER, HORROR
	}

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 14)
	private Theme primaryTheme;

	@Enumerated(EnumType.STRING)
	@Column(nullable = true, length = 14)
	private Theme secondaryTheme;

	public Story() {
	}

	// For test purposes
	public Story(Integer id, String title, String text, String author, Date publicationDate, Genre genre,
			Theme primaryTheme, Theme secondaryTheme) {
		this(title, text, author, publicationDate, genre, primaryTheme, secondaryTheme);
		this.id = id;
	}

	public Story(String title, String text, String author, Date publicationDate, Genre genre, Theme primaryTheme,
			Theme secondaryTheme) {
		this.setStoryGenre(genre);
		this.setTitle(title);
		this.setText(text);
		this.setAuthor(author);
		this.setPublicationDate(publicationDate);
		this.setPrimaryTheme(primaryTheme);
		this.setSecondaryTheme(secondaryTheme);
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		requireNonNull(title, "title can't be null");
		inclusiveBetween(1, 100, title.length(), "title must have a length between 1 and 100");

		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		requireNonNull(text, "text can't be null");

		switch (this.storyGenre) {
		case NANOSTORY:
			inclusiveBetween(1, 150, text.length(), "text must have a length between 1 and 150");
			break;
		case POETRY:
			inclusiveBetween(1, 500, text.length(), "text must have a length between 1 and 500");
			break;
		case STORY:
			inclusiveBetween(1, 1000, text.length(), "text must have a length between 1 and 1000");
			break;
		default:

			break;
		}
		this.text = text;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		requireNonNull(author, "author can't be null");
		inclusiveBetween(1, 50, author.length(), "author must have a length between 1 and 50");
		this.author = author;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		requireNonNull(publicationDate, "publicationDate can't be null");
		inclusiveBetween(new Date(0), new Date(), publicationDate,
				"publicationDate must be previous to the current time");
		this.publicationDate = publicationDate;
	}

	public Genre getStoryGenre() {
		return storyGenre;
	}

	public void setStoryGenre(Genre storyGenre) {
		requireNonNull(storyGenre, "storyGenre can't be null");
		this.storyGenre = storyGenre;
	}

	public Theme getPrimaryTheme() {
		return primaryTheme;
	}

	public void setPrimaryTheme(Theme primaryTheme) {
		requireNonNull(primaryTheme, "primaryTheme can't be null");
		this.primaryTheme = primaryTheme;
	}

	public Theme getSecondaryTheme() {
		return secondaryTheme;
	}

	public void setSecondaryTheme(Theme secondaryTheme) {
		this.secondaryTheme = secondaryTheme;
	}

}
