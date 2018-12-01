package es.uvigo.esei.dgss.teamB.microstories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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

	private Integer totalPagination;

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
		
		System.out.println("Genre:.....");
		System.out.println(genre);
		System.out.println("Theme:.....");
		System.out.println(theme);
		System.out.println("Date:.....");
		System.out.println(date);
		
		if (genre.equals("All")){
			genreQ = null;
		}
		
		if (theme.equals("All")){
			themeQ = null;
		}
		Date date1 = null;
		if (date == null) {
			date = "Any moment";
		}
		switch (date){
			case "Any moment":
				date1 = null;
				break;
			case "Today":
				date1= new Date();
				break;
			case "This week":
				if ( date != null && !date.equals("")  ) {
					Date today = new Date(); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
					LocalDate dateWeek = LocalDate.now().minusDays(dayOfWeek-1);
					date1 = Date.from(dateWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
				}
				break;

			case "This month":
				if ( date != null && !date.equals("")  ) {
					Date today = new Date(); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); 
					LocalDate dateMonth = LocalDate.now().minusDays(dayOfMonth-1);
					date1 = Date.from(dateMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
				}
				break;
			case "This year":
				if ( date != null && !date.equals("")  ) {
					Date today = new Date(); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(today);
					int dayOfYear = cal.get(Calendar.DAY_OF_YEAR); 
					LocalDate dateYear = LocalDate.now().minusDays(dayOfYear-1);
					date1 = Date.from(dateYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
				}
				break;
		}
	      
	    listStory = storyEJB.listSearchPagination(nPagination,nStories,genreQ,themeQ,date1);

	    this.setTotalPagination(storyEJB.listSearchTotalOfPagination(nStories,genreQ,themeQ,date1));

	    return listStory;
		
	}

	public Integer getTotalPagination() {
		return totalPagination;
	}

	public void setTotalPagination(Integer totalPagination) {
		this.totalPagination = totalPagination;
	}

}
