package es.uvigo.esei.dgss.teamB.microstories.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static java.util.Arrays.copyOfRange;
import static org.apache.commons.lang3.StringUtils.repeat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class AuthorTest extends UserTest {
	private Story[] stories;
	
	private Story storyOwned;
	private Story storyNotOwned;
	private Story[] storiesWithoutAuthor;

	@Before
	public void setUpAuthor() throws Exception {
		this.stories = new Story[] {
			new Story("Title1", "Text1", null, new Date(), Genre.STORY, Theme.ADVENTURE, Theme.CHILDREN,0),
			new Story("Title2", "Text2", null, new Date(), Genre.POETRY, Theme.THRILLER, Theme.HORROR,0),
		};

		this.storyNotOwned = new Story("Title2", "Text2", null, new Date(), Genre.POETRY, Theme.THRILLER, Theme.HORROR,0);
		this.storyOwned = this.stories[1];
		this.storiesWithoutAuthor = copyOfRange(this.stories, 0, 1);
	}

	@Override
	protected User newUser(String login, String password) {
		return new Author(login, password);
	}

	@Test
	public void testAuthorStringStringCollection() {
		final String[] logins = { login, "A", repeat('A', 100) };

		for (String login : logins) {
			final Author author = new Author(login, password, stories);

			assertThat(author.getLogin(), is(equalTo(login)));
			assertThat(author.getPassword(), is(equalTo(md5Pass)));
			assertThat(author.getStories(), containsInAnyOrder(stories));
		}
	}

	@Test
	public void testAuthorStringStringCollectionEmptyStories() {
		final Author author = new Author(login, password, new Story[0]);

		assertThat(author.getLogin(), is(equalTo(login)));
		assertThat(author.getPassword(), is(equalTo(md5Pass)));
		assertThat(author.getStories(), is(emptyIterable()));
	}

	@Test(expected = NullPointerException.class)
	public void testAuthorStringStringCollectionNullLogin() {
		new Author(null, password, stories);
	}

	@Test(expected = NullPointerException.class)
	public void testAuthorStringStringCollectionNullPassword() {
		new Author(login, null, stories);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAuthorStringStringCollectionLoginTooShort() {
		new Author(shortLogin, password, stories);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAuthorStringStringCollectionLoginTooLong() {
		new Author(longLogin, password, stories);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAuthorStringStringCollectionPasswordTooShort() {
		new Author(login, shortPassword, stories);
	}

	@Test(expected = NullPointerException.class)
	public void testAuthorStringStringCollectionNullStories() {
		new Author(login, password, (Story[]) null);
	}

	@Test(expected = NullPointerException.class)
	public void testAuthorStringStringCollectionPasswordStoriesWithNull() {
		new Author(login, password, new Story[] { storyNotOwned, null });
	}

	@Test
	public void testAddStory() {
		final Author author = new Author(login, password);

		author.addStory(storyNotOwned);

		assertThat(author.getStories(), contains(storyNotOwned));
		assertThat(storyNotOwned.getAuthor(), is(author));
	}

	@Test
	public void testAddStoryAlreadyOwned() {
		final Author author = new Author(login, password, stories);

		author.addStory(storyOwned);

		assertThat(author.getStories(), containsInAnyOrder(stories));
	}

	@Test(expected = NullPointerException.class)
	public void testAddStoryNull() {
		final Author author = new Author(login, password);

		author.addStory(null);
	}

	@Test
	public void testRemoveStory() {
		final Author author = new Author(login, password, stories);

		author.removeStory(storyOwned);
		assertThat(author.getStories(), contains(storiesWithoutAuthor));
		assertThat(storyOwned.getAuthor(), is(nullValue()));
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveStoryNull() {
		final Author author = new Author(login, password);

		author.removeStory(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveStoryNotOwned() {
		final Author author = new Author(login, password);

		author.removeStory(storyNotOwned);
	}

	@Test
	public void testOwnsAuthor() {
		final Author author = new Author(login, password, stories);

		for (Story story : stories) {
			assertThat(author.ownsStory(story), is(true));
		}
		assertThat(author.ownsStory(storyNotOwned), is(false));
	}

	@Test
	public void testOwnsAuthorNotOwned() {
		final Author author = new Author(login, password, stories);

		assertThat(author.ownsStory(storyNotOwned), is(false));
	}

	@Test
	public void testOwnsStoryNull() {
		final Author author = new Author(login, password, stories);

		assertThat(author.ownsStory(null), is(false));
	}

	@Test
	public void testInternalAddStory() {
		final Author author = new Author(login, password);

		author.internalAddStory(storyNotOwned);

		assertThat(author.getStories(), contains(storyNotOwned));
	}

	@Test
	public void testInternalAddStoryAlreadyOwned() {
		final Author author = new Author(login, password, stories);

		author.internalAddStory(storyOwned);

		assertThat(author.getStories(), containsInAnyOrder(stories));
	}

	@Test(expected = NullPointerException.class)
	public void testInternalAddStoryNull() {
		final Author author = new Author(login, password);

		author.internalAddStory(null);
	}

	@Test
	public void testInternalRemoveStory() {
		final Author author = new Author(login, password, stories);

		author.internalRemoveStory(storyOwned);
		assertThat(author.getStories(), contains(storiesWithoutAuthor));
	}

	@Test(expected = NullPointerException.class)
	public void testSetLoginNullValue() {
		final Author author = new Author(login, password);

		author.setLogin(null);
	}

	@Test(expected = NullPointerException.class)
	public void testInternalRemoveStoryNull() {
		final Author author = new Author(login, password, stories);

		author.internalRemoveStory(null);
	}
}
