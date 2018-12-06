package es.uvigo.esei.dgss.teamB.microstories.entities;

import static java.util.Arrays.stream;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Objects.requireNonNull;

/**
 * A story author.
 */
@Entity
@DiscriminatorValue("AUTHOR")
@XmlRootElement(name = "author", namespace = "http://entities.microstories.teamB.dgss.esei.uvigo.es")
public class Author extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(
		mappedBy = "author",
		targetEntity = Story.class,
		cascade = CascadeType.ALL,
		orphanRemoval = true,
		fetch = FetchType.EAGER
	)
	private Collection<Story> stories;
	
	// Required for JPA
	Author() {}
	
	public Author(String login, String password) {
		super(login, password);
		this.stories = new ArrayList<>();
	}

	public Author(String login, String password, Story ... stories) {
		super(login, password);
		this.stories = new ArrayList<>();
		
		stream(stories).forEach(this::addStory);
	}

	public Collection<Story> getStories() {
		return unmodifiableCollection(stories);
	}
	
	public void addStory(Story story) {
		requireNonNull(story, "story can't be null");
		
		if (!this.ownsStory(story)) {
			story.setAuthor(this);
		}
	}
	
	public void removeStory(Story story) {
		requireNonNull(story, "story can't be null");
		
		if (this.ownsStory(story)) {
			story.setAuthor(null);
		} else {
			throw new IllegalArgumentException("story doesn't belong to this owner");
		}
	}
	
	public boolean ownsStory(Story story) {
		return this.stories.contains(story);
	}
	
	void internalAddStory(Story story) {
		requireNonNull(story, "story can't be null");
		
		if (!this.ownsStory(story))
			this.stories.add(story);
	}
	
	void internalRemoveStory(Story story) {
		requireNonNull(story, "story can't be null");
		
		this.stories.remove(story);
	}
}