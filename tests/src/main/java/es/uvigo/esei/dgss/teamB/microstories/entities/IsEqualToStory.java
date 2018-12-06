package es.uvigo.esei.dgss.teamB.microstories.entities;

import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

/**
 * A matcher that can be used to
 * compare entities Story by their attributes.
 */
public class IsEqualToStory extends IsEqualToEntity<Story> {
    private final boolean checkRelations;

    public IsEqualToStory(Story story, boolean checkRelations) {
        super(story);
        this.checkRelations = checkRelations;
    }

    @Override
    protected boolean matchesSafely(Story actual) {
        this.clearDescribeTo();

        if (actual == null) {
            this.addTemplatedDescription("actual", expected.toString());
            return false;
        } else {
            return checkAttribute("id", Story::getId, actual)
                    && checkAttribute("title", Story::getTitle, actual)
                    && checkAttribute("text", Story::getText, actual)
                    && checkAttribute("publicationDate", Story::getPublicationDate, actual)
                    && checkAttribute("storyGenre", Story::getGenre, actual)
                    && checkAttribute("primaryTheme", Story::getPrimaryTheme, actual)
                    && checkAttribute("secondaryTheme", Story::getSecondaryTheme, actual)
                    && checkAttribute("views", Story::getViews, actual)
         			&& (!this.checkRelations || checkAttribute("author", Story::getAuthor, actual, IsEqualToAuthor::equalToAuthorWithoutRelations));
        }
    }

    @Factory
    public static IsEqualToStory equalToStory(Story story) {
        return new IsEqualToStory(story, true);
    }

    @Factory
    public static IsEqualToStory equalToStoryWithoutRelations(Story story) {
        return new IsEqualToStory(story, false);
    }

    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesInAnyOrder(Story ... stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStory, stories);
    }
    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesWithoutRelationsInAnyOrder(Story ... stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStory, stories);
    }

    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesInAnyOrder(Iterable<Story> stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStory, stories);
    }
    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesWithoutRelationsInAnyOrder(Iterable<Story> stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStoryWithoutRelations, stories);
    }

}
