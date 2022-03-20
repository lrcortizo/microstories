package es.uvigo.esei.dgss.teamB.microstories.entities;

import java.io.Serializable;

public class FavouriteId implements Serializable {
	
	private static final long serialVersionUID = -4888995728683884024L;

	private String author;
	
	private Integer story;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getStory() {
		return story;
	}

	public void setStory(Integer story) {
		this.story = story;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((story == null) ? 0 : story.hashCode());
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FavouriteId other = (FavouriteId) obj;
		if (story == null) {
			if (other.story != null)
				return false;
		} else if (!story.equals(other.story))
			return false;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		return true;
	}

}
