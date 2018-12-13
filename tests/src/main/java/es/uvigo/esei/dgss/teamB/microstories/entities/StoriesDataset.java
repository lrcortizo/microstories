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
    
    public static Story newStory() {
    	
        return new Story(1,"Nuevo Microrrelato", "Texto del nuevo microrrelato", new Author("ana", "anapass"), new Date(), Genre.POETRY, Theme.HORROR, 32);
    
    }    
    

    public static Story storyToCreate() {

        return new Story("Nuevo Microrrelato", "Texto del nuevo microrrelato", null, new Date(), Genre.POETRY, Theme.HORROR, 32);

    }

    public static Story storyToUpdate() {

        return new Story(1,"Nuevo Microrrelato", "Texto del nuevo microrrelato", new Author("ana", "anapass"), new Date(), Genre.POETRY, Theme.HORROR, 32);

    }

    public static Story[] stories() {
        return new Story[]{
                new Story(EXISTENT_ID, "Microrrelato 1", "Texto del microrrelato 1", new Author("pepe", "pepepass"), new Date(1339970400000L), Genre.STORY, Theme.HISTORY, Theme.CHILDREN, 59),
                new Story(STORY_WITH_TWO_THEMES_TITLE, "Texto del microrrelato 2", new Author("pepe", "pepepass"), new Date(917132400000L), Genre.NANOSTORY, Theme.ADVENTURE, Theme.ROMANTIC, 19),
                new Story(STORY_WITH_ONE_THEME_TITLE, "Texto del microrrelato 3", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HISTORY, 21),
                new Story("Microrrelato 4", "Texto del microrrelato 4", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.CHILDREN, 34),
                new Story("Microrrelato 5", "Texto del microrrelato 5", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 55),
                new Story("Microrrelato 6", "Texto del microrrelato 6", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 76),
                new Story("Microrrelato 7", "Texto del microrrelato 7", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 94),
                new Story("Microrrelato 8", "Texto del microrrelato 8", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HISTORY, 0),
                new Story("Microrrelato 9", "Texto del microrrelato 9", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.ADVENTURE, 0),
                new Story("Microrrelato 10", "Texto del microrrelato 10", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.THRILLER, 2),
                new Story("Microrrelato 11", "Texto del microrrelato 11", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.CHILDREN, 0),
                new Story("Microrrelato 12", "Texto del microrrelato 12", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 32),
                new Story("Microrrelato 13", "Texto del microrrelato 13", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 13),
                new Story("Microrrelato 14", "Texto del microrrelato 14", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 81),
                new Story("Microrrelato 15", "Texto del microrrelato 15", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HISTORY, 8),
                new Story("Microrrelato 16", "Texto del microrrelato 16", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.ADVENTURE, 23),
                new Story("Microrrelato 17", "Texto del microrrelato 17", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.THRILLER, 1),
                new Story("Microrrelato 18", "Texto del microrrelato 18", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.CHILDREN, 3),
                new Story("Microrrelato 19", "Texto del microrrelato 19", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 34),
                new Story("Microrrelato 20", "Texto del microrrelato 20", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 2),
                new Story("Microrrelato 21", "Texto del microrrelato 21", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 12),

        };
    }

    public static Story[] mostPopularStories() {
        return new Story[]{
                new Story(EXISTENT_ID, "Microrrelato 1", "Texto del microrrelato 1", new Author("pepe", "pepepass"), new Date(1339970400000L), Genre.STORY, Theme.HISTORY, Theme.CHILDREN, 59),
                new Story(3, STORY_WITH_ONE_THEME_TITLE, "Texto del microrrelato 3", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.THRILLER, 21),
                new Story(4,"Microrrelato 4", "Texto del microrrelato 4", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.STORY, Theme.CHILDREN, 34),
                new Story(5,"Microrrelato 5", "Texto del microrrelato 5", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.NANOSTORY, Theme.HORROR, 55),
                new Story(6,"Microrrelato 6", "Texto del microrrelato 6", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 76),
                new Story(7,"Microrrelato 7", "Texto del microrrelato 7", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 94),
                new Story(12,"Microrrelato 12", "Texto del microrrelato 12", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.NANOSTORY, Theme.HORROR, 32),
                new Story(14,"Microrrelato 14", "Texto del microrrelato 14", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.POETRY, Theme.HORROR, 81),
                new Story(16,"Microrrelato 16", "Texto del microrrelato 16", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.NANOSTORY, Theme.ADVENTURE, 23),
                new Story(19,"Microrrelato 19", "Texto del microrrelato 19", new Author("pepe", "pepepass"), new Date(1110322800000L), Genre.NANOSTORY, Theme.HORROR, 34),

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
        return new Story(nonExistentStoryId(), nonExistentStoryId() + " title", nonExistentStoryId().toString() + "text",
                new Author(nonExistentStoryId().toString() + "authorlogin", nonExistentStoryId().toString() + "authorpass"),
                new Date(), Genre.STORY, Theme.THRILLER, Theme.HORROR, 0);
    }

    public static Theme anyThemeOf(Story story) {
        if (story.getSecondaryTheme() == null)
            return story.getPrimaryTheme();

        return story.getSecondaryTheme();
    }
}
