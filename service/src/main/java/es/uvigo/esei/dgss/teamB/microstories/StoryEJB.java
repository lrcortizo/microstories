package es.uvigo.esei.dgss.teamB.microstories;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

@Stateless
public class StoryEJB {
	
	@PersistenceContext
	private EntityManager em;
	
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
   

	@PermitAll
	public List<Story> listSearchPagination(Integer nPagination, Integer nStories, String genre, String theme, Date date) {
		
		String insertDate="";
		String insertGenre="";
		String insertTheme="";
		
		if(date != null) { 
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = formatter.format(date);
			
			insertDate="AND publicationDate<'"+ formattedDate +"' ";
		}
		if(genre != null) { insertGenre="AND genre='"+ genre +"' "; }
		if(theme != null) { insertTheme="AND (primaryTheme='"+ theme +"' OR secondaryTheme='"+ theme +"') "; }
		
		
		return em.createQuery("SELECT story "
				+ "FROM Story story "
				+ "WHERE publicationDate IS NOT NULL "
				+ insertDate
				+ insertGenre
				+ insertTheme
				+ "ORDER BY publicationDate DESC "
				, Story.class).setMaxResults(nStories).setFirstResult((nPagination-1)*nStories).getResultList();
	}
	
	
}
