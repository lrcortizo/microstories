package es.uvigo.esei.dgss.teamB.microstories;

import java.util.List;

import es.uvigo.esei.dgss.teamB.microstories.Story;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StoryEJB {
	
	@PersistenceContext
	private EntityManager em;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
	    this.em = em;
	}
	
	/**
	 * Returns the recent list of stories.
	 *  
	 * @return the recent list of stories to all users. 
	 */
	
	@PermitAll
	public List<Story> listRecentStories() {
		
		return em.createQuery("SELECT story "
				+ "FROM Story story "
				+ "WHERE publicationDate IS NOT NULL "
				+ "ORDER BY publicationDate DESC", Story.class).setMaxResults(6).getResultList();

	}
	

    /**
	 * Returns a story identified by the provided id
	 *  
     * @param id the identified of a story.
	 * @return story identified by the provided id. 
	 */
	@PermitAll
	public Story findStory(Integer id) {
		
		return em.find(Story.class, id);
	}
   

}
