package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import java.util.List;


@Named("search")
@RequestScoped
public class SearchManagedBean {

    @EJB
    private StoryEJB storyEJB;

    private Integer pageNumber = 1;

    private Integer pageSize = 9;

    private String searchInput = "";

    private List<Story> listStories;

    public List<Story> getListStories() {
        return listStories;
    }

    public void setListStories(List<Story> listStories) {
        this.listStories = listStories;
    }

    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Story> searchStories() {
        this.listStories = storyEJB.getByText(searchInput, pageNumber, pageSize);
        return this.listStories;
    }

    public String goToSearchView (){

        return "/search?faces-redirect=true&text=" + this.searchInput + "&pageNumber=1&pageSize=9";
    }

    public String changePage(Integer pageNumber){
        this.pageNumber = pageNumber;

        return "/search?faces-redirect=true&text=" + this.searchInput + "&pageNumber=" + this.pageNumber + "&pageSize=" + this.pageSize;
    }

}
