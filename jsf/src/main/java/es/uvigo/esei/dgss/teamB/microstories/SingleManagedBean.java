package es.uvigo.esei.dgss.teamB.microstories;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("single")
@RequestScoped
public class SingleManagedBean {
	
	@EJB
	private StoryEJB storyEJB;

	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Story getStory() {
		return storyEJB.findStory(id);
	}

}
