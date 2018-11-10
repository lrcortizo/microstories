package es.uvigo.esei.dgss.teamb.microstories.entities;

import es.uvigo.esei.dgss.teamB.microstories.Story;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * A matcher that can be used to
 * compare entities Story by their attributes.
 */
public class IsEqualToStory extends IsEqualToEntity<Story> {

    public IsEqualToStory(Story story) {
        super(story);
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
                    && checkAttribute("author", Story::getAuthor, actual)
                    && checkAttribute("publicationDate", Story::getPublicationDate, actual)
                    && checkAttribute("storyGenre", Story::getGenre, actual)
                    && checkAttribute("primaryTheme", Story::getPrimaryTheme, actual)
                    && checkAttribute("secondaryTheme", Story::getSecondaryTheme, actual);
        }
    }


    @Factory
    public static IsEqualToStory equalToStory(Story story) {
        return new IsEqualToStory(story);
    }

    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesInAnyOrder(Story ... stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStory, stories);
    }

    @Factory
    public static Matcher<Iterable<? extends Story>> containsStoriesInAnyOrder(Iterable<Story> stories) {
        return containsEntityInAnyOrder(IsEqualToStory::equalToStory, stories);
    }

}
