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
import javax.persistence.TypedQuery;

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
	public List<Story> topTenMostPopular (){
		List<Story> toRet = new ArrayList<>();

		List<Story> listStories = new ArrayList<>();
		String query = "SELECT s FROM Story s ORDER BY s.publicationDate DESC";

		TypedQuery<Story> typedQuery = em.createQuery(query, Story.class);
		listStories.addAll(typedQuery.getResultList());

		if (listStories.size() <= 10){
			return listStories;
		}

		Story lessPopular;
		Integer lessPopularViews;

		for (Story storyToAdd : listStories) {
			if (toRet.size() < 10){
				toRet.add( storyToAdd );
			}
			else{
				lessPopular = storyToAdd;
				lessPopularViews = storyToAdd.getViews();
				for (Story toRetStory : toRet){ //It gets the story with less views of the list
					if (toRetStory.getViews() < lessPopularViews){
						lessPopular = toRetStory;
						lessPopularViews = toRetStory.getViews();
					}
				}
				if (lessPopular.getId() != storyToAdd.getId()){
					toRet.remove(lessPopular);
					toRet.add(storyToAdd);
				}
			}
		}

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
}
