package es.uvigo.esei.dgss.teamB.microstories.entities;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class StoryTest {

	private String title;
	private String text;
	private Author author;
	private Date publicationDate;
	private Genre nanostory;
	private Genre poetry;
	private Genre story;

	private Theme primaryTheme;
	private Theme secondaryTheme;
	private Integer views;

	private String newTitle;
	private String newText;
	private Author newAuthor;
	private Date newPublicationDate;
	private Date futureDate;
	private Genre newStoryGenre;
	private Theme newPrimaryTheme;
	private Theme newSecondaryTheme;

	@Before
	public void setUp() throws Exception {
		this.title = "Initial Title";
		this.text = "Initial Text";
		this.author = new Author("pepe", "pepepass");
		this.publicationDate = new Date();
		this.nanostory = Genre.NANOSTORY;
		this.poetry = Genre.POETRY;
		this.story = Genre.STORY;
		this.primaryTheme = Theme.ADVENTURE;
		this.secondaryTheme = Theme.CHILDREN;
		this.views = 0;

		this.newTitle = "New Title";
		this.newText = "New Text";

		this.newAuthor = new Author("juan", "juanpass");
		this.newStoryGenre = Genre.POETRY;
		this.newPrimaryTheme = Theme.HORROR;
		this.newSecondaryTheme = Theme.ROMANTIC;
		final int oneDay = 24 * 60 * 60 * 1000;
		this.newPublicationDate = new Date(System.currentTimeMillis() - oneDay);
		this.futureDate = new Date(System.currentTimeMillis() + oneDay);
	}

	@Test
	public void testStoryTitleTextAuthorGenreThemes() {
		final String[] textsStory = { text, "A", StringUtils.repeat("A", 1000) };

		for (String text : textsStory) {
			final Story newStory = new Story(title, text, author, publicationDate, story, primaryTheme, secondaryTheme, views);

			assertThat(newStory.getTitle(), is(equalTo(title)));
			assertThat(newStory.getText(), is(equalTo(text)));
			assertThat(newStory.getAuthor(), is(equalTo(author)));
			assertThat(newStory.getPublicationDate(), is(equalTo(publicationDate)));
			assertThat(newStory.getGenre(), is(equalTo(story)));
			assertThat(newStory.getPrimaryTheme(), is(equalTo(primaryTheme)));
			assertThat(newStory.getSecondaryTheme(), is(equalTo(secondaryTheme)));
			assertThat(newStory.getViews(), is(equalTo(views)));
			assertThat(author.ownsStory(newStory), is(true));

		}
	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullTitle() {
		new Story(null, text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTitleTooShort() {
		new Story("", text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTitleTooLong() {
		new Story(repeat('A', 101), text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test
	public void testStoryTitleTextAuthorGenreThemesTitleValid() {
		Story newStory = new Story("A", text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getTitle().length(), is(equalTo(1)));
		newStory = new Story(repeat('A', 100), text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getTitle().length(), is(equalTo(100)));
	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullText() {

		new Story(title, null, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooShortForNanoStory() {
		new Story("", text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooShortForPoetry() {
		new Story("", text, author, publicationDate, poetry, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooShortForStory() {
		new Story("", text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooLongForNanoStory() {
		new Story(repeat('A', 151), text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooLongForPoetry() {
		new Story(repeat('A', 501), text, author, publicationDate, poetry, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesTextTooLongForStory() {
		new Story(repeat('A', 1001), text, author, publicationDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test
	public void testStoryTitleTextAuthorGenreThemesTextValid() {
		// Nanostory valid values, min and max
		Story newStory = new Story(title, "A", author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(1)));
		newStory = new Story(title, repeat('A', 150), author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(150)));

		// Poetry valid values, min and max
		newStory = new Story(title, "A", author, publicationDate, poetry, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(1)));
		newStory = new Story(title, repeat('A', 500), author, publicationDate, poetry, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(500)));

		// Story valid values, min and max
		newStory = new Story(title, "A", author, publicationDate, story, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(1)));
		newStory = new Story(title, repeat('A', 1000), author, publicationDate, story, primaryTheme, secondaryTheme, views);
		assertThat(newStory.getText().length(), is(equalTo(1000)));

	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullDate() {

		new Story(title, text, author, null, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStoryTitleTextAuthorGenreThemesDateAfterCurrent() {
		new Story(title, text, author, futureDate, story, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullGenre() {

		new Story(title, text, author, publicationDate, null, primaryTheme, secondaryTheme, views);
	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullPrimaryTheme() {

		new Story(title, text, author, publicationDate, story, null, secondaryTheme, views);
	}

	@Test(expected = NullPointerException.class)
	public void testStoryTitleTextAuthorGenreThemesNullViews() {

		new Story(title, text, author, publicationDate, story, primaryTheme, secondaryTheme, null);
	}

	@Test
	public void testSetTitle() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setTitle(newTitle);

		assertThat(newStory.getTitle(), is(equalTo(newTitle)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetTitleNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setTitle(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTitleTooShort() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setTitle("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTitleTooLong() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setTitle(repeat('A', 101));
	}

	@Test
	public void testSetText() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setText(newText);

		assertThat(newStory.getText(), is(equalTo(newText)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetTextNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setText(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooShortForNanoStory() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setText("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooLongForNanoStory() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setText(repeat('A', 151));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooShortForPoetry() {
		final Story newStory = new Story(title, text, author, publicationDate, poetry, primaryTheme, secondaryTheme, views);

		newStory.setText("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooLongForPoetry() {
		final Story newStory = new Story(title, text, author, publicationDate, poetry, primaryTheme, secondaryTheme, views);

		newStory.setText(repeat('A', 501));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooShortForStory() {
		final Story newStory = new Story(title, text, author, publicationDate, story, primaryTheme, secondaryTheme, views);

		newStory.setText("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetTextTooLongForStory() {
		final Story newStory = new Story(title, text, author, publicationDate, story, primaryTheme, secondaryTheme, views);

		newStory.setText(repeat('A', 1001));
	}

	@Test
	public void testSetAuthor() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setAuthor(newAuthor);

		assertThat(newStory.getAuthor(), is(equalTo(newAuthor)));

		assertThat(newAuthor.ownsStory(newStory), is(true));
		assertThat(author.ownsStory(newStory), is(false));
	}

	@Test
	public void testSetAuthorNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setAuthor(null);

		assertThat(newStory.getAuthor(), is(nullValue()));
		assertThat(author.ownsStory(newStory), is(false));
	}

	@Test
	public void testSetGenre() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setGenre(newStoryGenre);

		assertThat(newStory.getGenre(), is(equalTo(newStoryGenre)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetGenreNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setGenre(null);
	}

	@Test
	public void testSetPrimaryTheme() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setPrimaryTheme(newPrimaryTheme);

		assertThat(newStory.getPrimaryTheme(), is(equalTo(newPrimaryTheme)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetPrimaryThemeNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setPrimaryTheme(null);
	}

	@Test
	public void testSetSecondaryTheme() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setSecondaryTheme(newSecondaryTheme);

		assertThat(newStory.getSecondaryTheme(), is(equalTo(newSecondaryTheme)));
	}

	@Test
	public void testSetSecondaryThemeNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setSecondaryTheme(null);

		assertThat(newStory.getSecondaryTheme(), is(equalTo(null)));

	}

	@Test
	public void testSetPublicationDate() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setPublicationDate(newPublicationDate);

		assertThat(newStory.getPublicationDate(), is(equalTo(newPublicationDate)));
	}

	@Test(expected = NullPointerException.class)
	public void testSetPublicationDateNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setPublicationDate(null);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetPublicationDateAfterCurrent() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setPublicationDate(futureDate);
	}

	@Test(expected = NullPointerException.class)
	public void testSetViewsNull() {
		final Story newStory = new Story(title, text, author, publicationDate, nanostory, primaryTheme, secondaryTheme, views);

		newStory.setViews(null);
	}
}
