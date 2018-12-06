package es.uvigo.esei.dgss.teamB.microstories.entities;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class IsEqualToAuthor extends IsEqualToEntity<Author> {
	private final boolean checkRelations;
	
	public IsEqualToAuthor(Author author, boolean checkRelations) {
		super(author);
		this.checkRelations = checkRelations;
	}

	@Override
	protected boolean matchesSafely(Author actual) {
		this.clearDescribeTo();
		
		if (actual == null) {
			this.addTemplatedDescription("actual", expected.toString());
			return false;
		} else {
			return checkAttribute("login", Author::getLogin, actual)
				&& checkAttribute("password", Author::getPassword, actual)
				&& (!this.checkRelations || checkIterableAttribute("stories", Author::getStories, actual, IsEqualToStory::containsStoriesWithoutRelationsInAnyOrder));
		}
	}

	@Factory
	public static IsEqualToAuthor equalToAuthor(Author authors) {
		return new IsEqualToAuthor(authors, true);
	}
	
	@Factory
	public static IsEqualToAuthor equalToAuthorWithoutRelations(Author authors) {
		return new IsEqualToAuthor(authors, false);
	}
	
	@Factory
	public static Matcher<Iterable<? extends Author>> containsAuthorsInAnyOrder(Author ... authors) {
		return containsEntityInAnyOrder(IsEqualToAuthor::equalToAuthor, authors);
	}
	
	@Factory
	public static Matcher<Iterable<? extends Author>> containsAuthorsWithoutRelationsInAnyOrder(Author ... authors) {
		return containsEntityInAnyOrder(IsEqualToAuthor::equalToAuthorWithoutRelations, authors);
	}
	
	@Factory
	public static Matcher<Iterable<? extends Author>> containsAuthorsInAnyOrder(Iterable<Author> authors) {
		return containsEntityInAnyOrder(IsEqualToAuthor::equalToAuthor, authors);
	}
	
	@Factory
	public static Matcher<Iterable<? extends Author>> containsAuthorsWithoutRelationsInAnyOrder(Iterable<Author> authors) {
		return containsEntityInAnyOrder(IsEqualToAuthor::equalToAuthorWithoutRelations, authors);
	}
}
