package es.uvigo.esei.dgss.teamB.microstories.entities;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.inclusiveBetween;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


/**
 * Class Story
 * It represents Entity Story in database with its columns as parameters
 * Setters methods implements several validations
 */
@Entity
@XmlAccessorType(XmlAccessType.FIELD)
public class Story {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String text;

    @Column(nullable = false, length = 50)
    private String author;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicationDate = null;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 9)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 14)
    private Theme primaryTheme;

    @Enumerated(EnumType.STRING)
    @Column(length = 14)
    private Theme secondaryTheme;

    @Column(nullable = false)
    private Integer views;

    Story() {
    }

    // For test purposes
    Story(Integer id, String title, String text, String author, Date publicationDate, Genre genre,
                 Theme primaryTheme, Theme secondaryTheme, Integer views) {
        this(title, text, author, publicationDate, genre, primaryTheme, secondaryTheme, views);
        this.id = id;
    }

    // For test purposes
    Story(Integer id, String title, String text, String author, Date publicationDate, Genre genre,
          Theme primaryTheme, Integer views) {
        this(title, text, author, publicationDate, genre, primaryTheme, null, views);
        this.id = id;
    }

    public Story(String title, String text, String author, Date publicationDate, Genre genre, Theme primaryTheme, Integer views) {
        this(title, text, author, publicationDate, genre, primaryTheme, null, views);
    }

    public Story(String title, String text, String author, Date publicationDate, Genre genre, Theme primaryTheme,
                 Theme secondaryTheme, Integer views) {
    	this.setGenre(genre);
        this.setTitle(title);
        this.setText(text);
        this.setAuthor(author);
        this.setPublicationDate(publicationDate);
        this.setPrimaryTheme(primaryTheme);
        this.setSecondaryTheme(secondaryTheme);
        this.setViews(views);
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

        switch (this.genre) {
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

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        requireNonNull(genre, "genre can't be null");
        this.genre = genre;
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

    public Integer getViews() { return views; }

    public void setViews(Integer views) {
        requireNonNull(views, "views can't be null");
        this.views = views;
    }
}
