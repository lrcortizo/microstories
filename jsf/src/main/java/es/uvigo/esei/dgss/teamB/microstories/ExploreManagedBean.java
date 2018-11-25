package es.uvigo.esei.dgss.teamB.microstories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

@Named("explore")
@RequestScoped
public class ExploreManagedBean {
	
	@EJB
	private StoryEJB storyEJB;

	private int nPagination=1;
	
	private int nStories= 9;
	
	private String genre="All";
	
	private String theme="All";
	
	private String date;


	public int getnPagination() {
		return nPagination;
	}

	public void setnPagination(int nPagination) {
		this.nPagination = nPagination;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public int getnStories() {
		return nStories;
	}

	public void setnStories(int nStories) {
		this.nStories = nStories;
	}

	public List<Story> listSearchPagination() {

		List<Story>  listStory = new ArrayList();
		String genreQ = genre;
		String themeQ = theme;
		
			
		if (genre.equals("All")){
			genreQ = null;
		}
		
		if (theme.equals("All")){
			themeQ = null;
		}
		
		Date date1 = new Date();
	    try {
	    	
	    	if ( date != null && !date.equals("")  ) {
	    		date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
	    	}
	    		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    listStory = storyEJB.listSearchPagination(nPagination,nStories,genreQ,themeQ,date1);
	    return listStory;
		
	}

}
