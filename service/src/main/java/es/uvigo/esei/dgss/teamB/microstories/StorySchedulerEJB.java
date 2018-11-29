package es.uvigo.esei.dgss.teamB.microstories;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

@Startup
@Singleton
public class StorySchedulerEJB {

	@PersistenceContext
	private EntityManager em;

	@Schedule(dayOfMonth = "1")
	public void resetViews() {
		List<Story> stories = em.createQuery("SELECT story FROM Story s", Story.class).getResultList();
		
		for(Story story : stories) {
			story.setViews(0);
			em.persist(story);
		}
	}
}
