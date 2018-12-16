package es.uvigo.esei.dgss.teamB.microstories.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Class Favourite
 * It represents many to many relation between User and Story
 * Setters methods implements several validations
 */
@Entity
@IdClass(FavouriteId.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Favourite {
	
	@Id
	@ManyToOne
	private Author author;
	
	@Id
	@ManyToOne
	private Story story;

	public Author geAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}
	
}
