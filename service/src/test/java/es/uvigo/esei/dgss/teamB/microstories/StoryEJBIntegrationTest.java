package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.mostPopularStories;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.CoreMatchers.anyOf;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

@PermitAll
@RunWith(Arquillian.class)
@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
public class StoryEJBIntegrationTest {

	@Inject
	private StoryEJB storyEjb;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addClasses(StoryEJB.class, Story.class)
				.addPackage(Story.class.getPackage()).addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("jboss-web.xml").addAsResource("arquillian.extension.persistence.properties")
				.addAsResource("arquillian.extension.persistence.dbunit.properties")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	// ListRecentStories

	@Test
	public void testListRecentStoriesNull() {

		assertThat(storyEjb.listRecentStories(), anyOf(nullValue(), empty()));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void testListRecentStoriesMaxElementsAs6() {

		List<Story> list = storyEjb.listRecentStories();
		assertThat(list.size(), lessThan(7));
	}

	// findStory

	@Test(expected = javax.ejb.EJBException.class)
	public void findStoryNull() {

		storyEjb.findStory(null);
	}

	@Test
	public void findStoryBadId() {

		assertThat(storyEjb.findStory(1), is(nullValue()));
	}

	@Test
	@UsingDataSet("stories.xml")
    public void findStory(){
    	
    	assertThat(storyEjb.findStory(1), is(not(equalTo(nullValue()))));
    }

	
	// getByText
	
	@Test
	public void testGetByTextNull() {

		assertThat(storyEjb.getByText(null, null, null), anyOf(nullValue(), empty()));
	}
	
	@Test
	@UsingDataSet("stories.xml")
	public void testGetByTextMaxElementsAsAsked() {

		Integer pageSize = 9; 
		List<Story> list = storyEjb.getByText(null, 1, pageSize);
		assertThat(list.size(), lessThan(pageSize+1));
	}
	
	@Test 
	public void testGetByTextBadSearch() {
		Integer pageSize = 9; 

		assertThat(storyEjb.getByText("Microrrelato 1", 1, pageSize), anyOf(nullValue(), empty()));
	}
	
	@Test 
	@UsingDataSet("stories.xml")
	public void testGetByText() {
		
		Integer pageSize = 9; 
	
		assertThat(storyEjb.getByText("Microrrelato 1", 1, pageSize), is(not(equalTo(nullValue()))));
	}
	
	// getSearchPagination
	
	@Test(expected = javax.ejb.EJBException.class)
	public void testListSearchPaginationNull() {

		assertThat(storyEjb.listSearchPagination(null,null,null,null,null), anyOf(nullValue(), empty()));
	}
	
	@Test
	@UsingDataSet("stories.xml")
	public void testListSearchPaginationMaxElementsAsAsked() {

		Integer nStories = 9; 
		List<Story> list = storyEjb.listSearchPagination(1,nStories,null,null,null);
		assertThat(list.size(), lessThan(nStories+1));
	}
	
	@Test 
	public void testListSearchPaginationBadSearch() {
		Integer nStories = 9; 

		assertThat(storyEjb.listSearchPagination(1,nStories,"NANOSTORY","ADVENTURE",null), anyOf(nullValue(), empty()));
	}
	
	@Test 
	@UsingDataSet("stories.xml")
	public void testListSearchPaginationNoDate() {
		
		Integer nStories = 9; 
	
		assertThat(storyEjb.listSearchPagination(1,nStories,"NANOSTORY","CHILDREN",null), is(not(equalTo(nullValue()))));
	}
	
	@Test 
	@UsingDataSet("stories.xml")
	public void testListSearchPaginationNoGenre(){
		
		Integer nStories = 9; 

	
		assertThat(storyEjb.listSearchPagination(1,nStories,null,"CHILDREN",null), is(not(equalTo(nullValue()))));
	}
	
	@Test 
	@UsingDataSet("stories.xml")
	public void testListSearchPaginationNoTheme() {
		
		Integer nStories = 9; 
	
		assertThat(storyEjb.listSearchPagination(1,nStories,"NANOSTORY",null,null), is(not(equalTo(nullValue()))));
	}
	
	@Test 
	@SuppressWarnings("deprecation")
	@UsingDataSet("stories.xml")
	public void testListSearchPaginationWithDate() {
		
		Integer nStories = 9; 
		

		assertThat(storyEjb.listSearchPagination(1,nStories,null,null,new Date(90,4,4)), is(not(equalTo(nullValue()))));

		
	}

	// ListMostPopularStories

	@Test
	public void testTopTenMostPopularNull() {

		assertThat(storyEjb.topTenMostPopular(), anyOf(nullValue(), empty()));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void testTopTenMostPopularMaxElementsAs10() {

		List<Story> list = storyEjb.topTenMostPopular();

		assertThat(list.size(), lessThan(11));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void testTopTenMostPopular() {

		List<Story> list = storyEjb.topTenMostPopular();
		List<Story> mostPopular = Arrays.asList(mostPopularStories());

		int index = 0;
		for (Story story : list){
			assertThat(mostPopular.get(index).getId(), is(equalTo(story.getId())));
			index++;
		}
	}
	
}
