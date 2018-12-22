package es.uvigo.esei.dgss.teamB.microstories;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

@Named("favourites")
@RequestScoped
public class MyFavouritesStoriesManagedBean {

	@EJB
	private StoryEJB storyEJB;

	private int nPagination = 1;

	private int nStories = 9;

	private Integer totalPagination;

	public int getnPagination() {
		return nPagination;
	}

	public void setnPagination(int nPagination) {
		this.nPagination = nPagination;
	}

	public int getnStories() {
		return nStories;
	}
	
	public void setnStories(int nStories) {
		this.nStories = nStories;
	}
	
	public Integer getTotalPagination() {
		return totalPagination;
	}

	public void setTotalPagination(Integer totalPagination) {
		this.totalPagination = totalPagination;
	}

	public List<Story> listMyStories() {
		this.setTotalPagination(storyEJB.listFavouriteStoriesTotalOfPagination(nStories));
		return storyEJB.listFavouriteStories(nPagination, nStories);
	}
	
	public String deleteStory(int id) {

		storyEJB.removeStory(id);
		return "/mystories.xhtml?faces-redirect=true";
		
	}

	
}
