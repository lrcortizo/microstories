package es.uvigo.esei.dgss.teamB.microstories.entities;


import java.util.*;

import es.uvigo.esei.dgss.teamB.microstories.entities.Genre;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Theme;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toSet;


/**
 * A class that provides data of the entity Story.
 */
public class StoriesDataset {

    public static final Integer EXISTENT_ID = 1;
    public static final Integer NON_EXISTENT_ID = 0;
    public static final String STORY_WITH_TWO_THEMES_TITLE = "Microrrelato 2";
    public static final String STORY_WITH_ONE_THEME_TITLE = "Microrrelato 3";

    public static Story storyWithId(Integer id) {
        return stream(stories())
                .filter(story -> story.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Story[] stories(Genre... genres) {
        final Set<Genre> genresSet = stream(genres).collect(toSet());

        return stream(stories())
        		.filter(story -> genresSet.contains(story.getGenre()))
                .toArray(Story[]::new);
    }

    public static Story[] stories(Theme... themes) {
        final Set<Theme> genresSet = stream(themes).collect(toSet());

        return stream(stories())
                .filter(story -> genresSet.contains(story.getPrimaryTheme()) ||
                        genresSet.contains(story.getSecondaryTheme()))
                .toArray(Story[]::new);
    }

    public static Story[] stories() {
        return new Story[]{
                new Story(EXISTENT_ID, "Microrrelato 1", "Texto del microrrelato 1", "Autor 1", new Date(1339970400000L), Genre.STORY, Theme.HISTORY, Theme.CHILDREN),
                new Story(STORY_WITH_TWO_THEMES_TITLE, "Texto del microrrelato 2", "Autor 2", new Date(917132400000L), Genre.NANOSTORY, Theme.ADVENTURE, Theme.ROMANTIC),
                new Story(STORY_WITH_ONE_THEME_TITLE, "Texto del microrrelato 3", "Autor 3", new Date(1110322800000L), Genre.POETRY, Theme.THRILLER),
        };
    }

    public static Story[] storiesAnd(Story... additionalStories) {
        final Story[] stories = stories();
        final Story[] storiesWithNewStory = new Story[stories.length + additionalStories.length];

        System.arraycopy(stories, 0, storiesWithNewStory, 0, stories.length);
        System.arraycopy(additionalStories, 0, storiesWithNewStory, stories.length, additionalStories.length);

        return storiesWithNewStory;
    }

    public static Story[] storiesWithout(Story... removeStories) {
        final List<Story> stories = new ArrayList<>(asList(stories()));

        for (Story story : removeStories) {
            final Iterator<Story> itStory = stories.iterator();

            while (itStory.hasNext()) {
                if (itStory.next().getId().equals(story.getId())) {
                    itStory.remove();
                    break;
                }
            }
        }

        return stories.toArray(new Story[stories.size()]);
    }

    public static String storyTitleWithTwoThemes() {
        return "Microrrelato 2";
    }

    public static String storyTitleWithOneTheme() {
        return "Microrrelato 3";
    }

    public static Integer anyStoryId() {
        return existentStoryId();
    }

    public static Integer existentStoryId() {
        return EXISTENT_ID;
    }

    public static Integer nonExistentStoryId() {
        return NON_EXISTENT_ID;
    }

    public static Story anyStory() {
        return storyWithId(anyStoryId());
    }

    public static Story existentStory() {
        return storyWithId(existentStoryId());
    }

    public static String newTitleForExistentStory() {
        return "New Title";
    }

    public static Story nonExistentStory() {
        return new Story(nonExistentStoryId(), nonExistentStoryId() + " title", nonExistentStoryId().toString() + "text", nonExistentStoryId().toString() + "author",
                new Date(), Genre.STORY, Theme.THRILLER, Theme.HORROR);
    }

    public static Theme anyThemeOf(Story story) {
        if (story.getSecondaryTheme() == null)
            return story.getPrimaryTheme();

        return story.getSecondaryTheme();
    }
}
