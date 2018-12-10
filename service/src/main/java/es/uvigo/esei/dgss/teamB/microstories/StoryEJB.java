package es.uvigo.esei.dgss.teamB.microstories;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Author;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class StoryEJB {

	@PersistenceContext
	private EntityManager em;

	@Inject
	private Principal currentUser;
	/**
	 * Returns the recent list of stories.
	 * 
	 * @return the recent list of stories to all users.
	 */

	@PermitAll
	public List<Story> listRecentStories() {

		return em.createQuery("SELECT story " + "FROM Story story " + "WHERE publicationDate IS NOT NULL "
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
	public List<Story> mostPopular (){
		List<Story> toRet = new ArrayList<>();

		toRet.addAll(em.createQuery(
				"SELECT s FROM Story s where s.genre LIKE 'STORY' ORDER BY s.views DESC",
				Story.class).setMaxResults(2).getResultList());
		
		toRet.addAll(em.createQuery(
				"SELECT s FROM Story s where s.genre LIKE 'NANOSTORY' ORDER BY s.views DESC",
				Story.class).setMaxResults(2).getResultList());
		
		toRet.addAll(em.createQuery(
				"SELECT s FROM Story s where s.genre LIKE 'POETRY' ORDER BY s.views DESC",
				Story.class).setMaxResults(2).getResultList());
		
		Collections.sort(toRet, new Comparator<Story>() {

	        public int compare(Story s1, Story s2) {

	            int sComp = s2.getViews().compareTo(s1.getViews());

	            if (sComp != 0) {
	               return sComp;
	            } 

	            return s2.getPublicationDate().compareTo(s1.getPublicationDate());
	    }});

		return toRet;
	}
	
	@PermitAll
	public List<Story> getByText(String text, Integer pageNumber, Integer pageSize) {

		String query = "SELECT s FROM Story s WHERE s.title LIKE :t OR s.text LIKE :t ORDER BY s.publicationDate DESC";

		return em.createQuery(query, Story.class).setParameter("t", "%" + text + "%").setMaxResults(pageSize)
				.setFirstResult((pageNumber - 1) * pageSize).getResultList();

	}

	@PermitAll
	public List<Story> listSearchPagination(Integer nPagination, Integer nStories, String genre, String theme, Date date) {
		
		String insertDate="";
		String insertGenre="";
		String insertTheme="";
		
		if(date != null) { 
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = formatter.format(date);
			
			insertDate="AND publicationDate>'"+ formattedDate +"' ";
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
	
	@PermitAll
	public Integer getByTextTotalOfPagination(String text, Integer nStories) {

		String query = "SELECT s FROM Story s WHERE s.title LIKE :t OR s.text LIKE :t ORDER BY s.publicationDate DESC";

		int nTotalStories = em.createQuery(query, Story.class).setParameter("t", "%" + text + "%").getResultList()
				.size();

		if (nTotalStories % nStories != 0) {
			nTotalStories = (nTotalStories / nStories) + 1;
		} else {
			nTotalStories = (nTotalStories / nStories);
		}

		return nTotalStories;
	}

	@PermitAll
	public Integer listSearchTotalOfPagination(Integer nStories, String genre, String theme, Date date) {

		String insertDate = "";
		String insertGenre = "";
		String insertTheme = "";

		if (date != null) {

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String formattedDate = formatter.format(date);

			insertDate = "AND publicationDate>'" + formattedDate + "' ";
		}
		if (genre != null) {
			insertGenre = "AND genre='" + genre + "' ";
		}
		if (theme != null) {
			insertTheme = "AND (primaryTheme='" + theme + "' OR secondaryTheme='" + theme + "') ";
		}

		int nTotalStories = em
				.createQuery("SELECT story " + "FROM Story story " + "WHERE publicationDate IS NOT NULL " + insertDate
						+ insertGenre + insertTheme + "ORDER BY publicationDate DESC ", Story.class)
				.getResultList().size();

		if (nTotalStories % nStories != 0) {
			nTotalStories = (nTotalStories / nStories) + 1;
		} else {
			nTotalStories = (nTotalStories / nStories);
		}

		return nTotalStories;
	}

	@RolesAllowed("AUTHOR")
	public List<Story> listMyStories() {

		final Author author = em.find(Author.class, currentUser.getName());

		return em.createQuery("SELECT story " + "FROM Story story "
				+ "WHERE publicationDate IS NOT NULL AND story.author=:a ORDER BY publicationDate DESC", Story.class)
				.setParameter("a", author)
				.getResultList();

	}


	@RolesAllowed("AUTHOR")
	public Story createStory(Story story) {

		final Author author = em.find(Author.class, currentUser.getName());

		if (story.getAuthor() != null && !story.getAuthor().getLogin().equals(author.getLogin())) {
			throw new EJBAccessException("Story's author is not the user logged");
		} else {
			story.setAuthor(author);

			em.persist(story);

			return story;
		}
	}

	@RolesAllowed("AUTHOR")
	public Story updateStory(Story story) {
		if (story.getAuthor() == null)
			throw new IllegalArgumentException("Story must have an author");

		if (story.getAuthor().getLogin().equals(this.currentUser.getName())) {
			return em.merge(story);
		} else {
			throw new EJBAccessException("Story's author is not the user logged");
		}
	}
}
