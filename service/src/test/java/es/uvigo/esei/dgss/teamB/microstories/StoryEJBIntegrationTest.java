package es.uvigo.esei.dgss.teamB.microstories;



import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.Story;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
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
		return ShrinkWrap.create(WebArchive.class, "test.war")
			.addClasses(StoryEJB.class,Story.class)
			.addPackage(Story.class.getPackage())
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
			.addAsWebInfResource("jboss-web.xml")
            .addAsResource("arquillian.extension.persistence.properties")
            .addAsResource("arquillian.extension.persistence.dbunit.properties")
			.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	//ListRecentStories
	
	@Test
	public void testListRecentStoriesNull(){
		
		assertThat(storyEjb.listRecentStories(), anyOf(nullValue(), empty()));
	}

	
	@Test
	@UsingDataSet("stories.xml")
	public void testListRecentStoriesMaxElementsAs6(){
		
		List<Story> list = storyEjb.listRecentStories();
		assertThat(list.size(), lessThan(7));
	}


	//findStory
	
    @Test(expected=javax.ejb.EJBException.class)
    public void findStoryNull(){
    	
    	storyEjb.findStory(null);
    }
    
	@Test
    public void findStoryBadId(){
    	
    	assertThat(storyEjb.findStory(1), is(nullValue()));
    }
	
	@Test
	@UsingDataSet("stories.xml")
    public void findStory(){
    	
    	assertThat(storyEjb.findStory(1), is(not(equalTo(nullValue()))));
    }


}
