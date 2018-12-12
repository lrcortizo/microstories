package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.entities.Author;
import es.uvigo.esei.dgss.teamB.microstories.service.util.security.RoleCaller;
import es.uvigo.esei.dgss.teamB.microstories.service.util.security.TestPrincipal;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;

import static es.uvigo.esei.dgss.teamB.microstories.entities.IsEqualToStory.equalToStoryWithoutRelations;
import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.mostPopularStories;
import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.storyToCreate;
import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.storyToUpdate;
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
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

@PermitAll
@RunWith(Arquillian.class)
@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
public class StoryEJBIntegrationTest {

	@Inject
	private StoryEJB storyEjb;

	@EJB(beanName = "author-caller")
	private RoleCaller asAuthor;

	@Inject
	private TestPrincipal principal;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(StoryEJB.class, Story.class, Author.class)
				.addPackage(RoleCaller.class.getPackage()).addPackage(Author.class.getPackage())
				.addPackage(Story.class.getPackage()).addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("jboss-web.xml").addAsResource("arquillian.extension.persistence.properties")
				.addAsResource("arquillian.extension.persistence.dbunit.properties")
				.addAsWebInfResource("beans.xml", "beans.xml");
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
	
	@Test(expected = javax.ejb.EJBException.class)
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

	// mostPopular

	@Test
	public void testMostPopularNull() {

		assertThat(storyEjb.mostPopular(), anyOf(nullValue(), empty()));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void testMostPopularMaxElementsAs6() {

		List<Story> list = storyEjb.mostPopular();

		assertThat(list.size(), lessThan(7));
	}
	
	@Test
	public void listSearchTotalOfPaginationDBEmpty() {

		Integer nStories = 9;

		assertThat(storyEjb.listSearchTotalOfPagination(nStories, null, null, null), is(0));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void listSearchTotalOfPagination() {

		Integer nStories = 9;

		assertThat(storyEjb.listSearchTotalOfPagination(nStories, null, null, null), is(3));
	}

	@Test
	public void getByTextTotalOfPaginationDBEmpty() {

		Integer nStories = 9;

		assertThat(storyEjb.getByTextTotalOfPagination("Microrrelato 1", nStories), is(0));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void getByTextTotalOfPagination() {

		Integer nStories = 9;

		assertThat(storyEjb.getByTextTotalOfPagination("Microrrelato 1", nStories), is(2));
	}

	@Test(expected = javax.ejb.EJBException.class)
	public void testListMyStoriesNull() {
		principal.setName("pepe");
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null,null)), anyOf(nullValue(), empty()));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testListMyStoriesAsUser() {

		assertThat(storyEjb.listMyStories(null,null), is(not(equalTo(nullValue()))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testListMyStoriesAsAuthor() {

		principal.setName("ana");
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null,null)), is(not(equalTo(nullValue()))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testListMyStoriesAsAuthorMaxElementsAsNormal() {

		principal.setName("pepe");
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null, null).size()), lessThan(11));

	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testListMyStoriesAsAuthorMaxElementsAs9() {

		Integer nStories = 9;

		principal.setName("pepe");
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null, nStories).size()), lessThan(nStories + 1));

	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testCreateStoryAsUser() {

		assertThat(storyEjb.createStory(storyToCreate()), is(not(equalTo(nullValue()))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testCreateStoryAsAuthor() {

		principal.setName("ana");
		int numStoriesBefore = asAuthor.call(() -> storyEjb.listMyStories(null,null).size());

		assertThat(asAuthor.call(() -> storyEjb.createStory(storyToCreate())), is(not(equalTo(nullValue()))));
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null,null).size()), is((equalTo(numStoriesBefore+1))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testUpdateStoryAsUser() {

		assertThat(storyEjb.updateStory(storyToUpdate()), is(not(equalTo(nullValue()))));
	}

	@Test
	@UsingDataSet("stories.xml")
	public void testUpdateStoryAsAuthor() {

		principal.setName("ana");
		assertThat(asAuthor.call(() -> storyEjb.updateStory(storyToUpdate())), is(not(equalTo(nullValue()))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testUpdateStoryAsDifferentAuthor() {

		principal.setName("pepe");
		assertThat(asAuthor.call(() -> storyEjb.updateStory(storyToUpdate())), is(not(equalTo(nullValue()))));
	}

	@Test(expected = IllegalArgumentException.class)
	@UsingDataSet("stories.xml")
	public void testUpdateStoryAuthorNull() {
		try {
			principal.setName("ana");
			Story storyToUpdate = storyToUpdate();
			storyToUpdate.setAuthor(null);
			asAuthor.call(() -> storyEjb.updateStory(storyToUpdate));
		}catch(EJBTransactionRolledbackException e) {
			throw (IllegalArgumentException) e.getCause();
		}
	}
	
	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testRemoveStoryAsUser() {

		storyEjb.removeStory(1);
	}

	@Test(expected=EJBTransactionRolledbackException.class)
	@UsingDataSet("stories.xml")
	public void testRemoveStoryAsAuthor() {

		principal.setName("pepe");
		int numStoriesBefore = asAuthor.call(() -> storyEjb.listMyStories(null,null).size());

		asAuthor.call(() -> storyEjb.removeStory(1));
		assertThat(asAuthor.call(() -> storyEjb.listMyStories(null,null).size()), is((equalTo(numStoriesBefore-1))));
	}

	@Test(expected = javax.ejb.EJBException.class)
	@UsingDataSet("stories.xml")
	public void testRemoveStoryAsDifferentAuthor() {

		principal.setName("ana");
		asAuthor.call(() -> storyEjb.removeStory(1));
	}
}
