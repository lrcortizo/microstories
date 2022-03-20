package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import java.util.List;


import javax.ejb.EJB;
import javax.inject.Named;

@Named("index")
public class StoryManagedBean {
	
	@EJB
	private StoryEJB storyEJB;

	
	public List<Story> listRecentStories() {
		return storyEJB.listRecentStories();
	}
	
	
	public List<Story> mostPopularStories() {
		return storyEJB.mostPopular();
	}
	
	
}
