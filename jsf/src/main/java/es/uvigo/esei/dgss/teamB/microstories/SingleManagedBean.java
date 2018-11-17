package es.uvigo.esei.dgss.teamB.microstories;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("single")
@RequestScoped
public class SingleManagedBean {
	
	@EJB
	private StoryEJB storyEJB;

	public Story findStory(int id) {
		return storyEJB.findStory(id);
	}

}
