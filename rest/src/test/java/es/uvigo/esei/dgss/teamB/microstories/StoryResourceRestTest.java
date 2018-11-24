package es.uvigo.esei.dgss.teamB.microstories;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.*;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.uvigo.esei.dgss.teamB.microstories.GenericTypes.ListStoryType;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

import javax.ws.rs.core.Response;
import java.util.List;

import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.StoryResource;

import static es.uvigo.esei.dgss.teamB.microstories.entities.IsEqualToStory.containsStoriesInAnyOrder;
import static es.uvigo.esei.dgss.teamB.microstories.entities.IsEqualToStory.equalToStory;
import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.existentStory;
import static es.uvigo.esei.dgss.teamB.microstories.http.util.HasHttpStatus.hasOkStatus;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class StoryResourceRestTest {
	private final static String BASE_PATH = "microstory/";

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
			.addClass(StoryResource.class)
			.addPackage(StoryEJB.class.getPackage())
			.addPackage(Story.class.getPackage())
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
			.addAsWebInfResource("jboss-web.xml")
			.addAsWebInfResource("web.xml")
			.addAsResource("arquillian.extension.persistence.properties")
			.addAsResource("arquillian.extension.persistence.dbunit.properties")
			.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	

	@Test @InSequence(1)
	@UsingDataSet("stories.xml")
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void beforeListRecent() {}
	
	@Test @InSequence(2)
	@RunAsClient
	public void testListRecent(
		@ArquillianResteasyResource(BASE_PATH +"recent/") ResteasyWebTarget webTarget
	) 
	throws Exception {
		
	    final Response response = webTarget.request().get();

	    assertThat(response, hasOkStatus());
	    
	    final List<Story> list = ListStoryType.readEntity(response);
	    assertThat(list, containsStoriesInAnyOrder(list));
		    
	   
	}

	@Test @InSequence(3)
	@ShouldMatchDataSet("stories.xml")
	@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
	public void afterList() {}
	
	@Test @InSequence(4)
	@UsingDataSet("stories.xml")
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void beforeGetStory() {}
	
	@Test @InSequence(5)
	@RunAsClient
	public void testGetStory(
		@ArquillianResteasyResource(BASE_PATH + "1") ResteasyWebTarget webTarget
	) 
	throws Exception {
		
	    final Response response = webTarget.request().get();

	    assertThat(response, hasOkStatus());
	    
	    final Story story = response.readEntity(Story.class);

	    final Story expected = existentStory();
	    
	    assertThat(story, is(equalToStory(expected)));
		    
	   
	}
	
	@Test @InSequence(6)
	@ShouldMatchDataSet("stories.xml")
	@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
	public void afterGetStory() {}
	
	@Test @InSequence(7)
	@UsingDataSet("stories.xml")
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void beforeGetByText() {}
	
	@Test @InSequence(8)
	@RunAsClient
	public void testGetByText(
		@ArquillianResteasyResource(BASE_PATH +"?contains=ipsum") ResteasyWebTarget webTarget
	) 
	throws Exception {
		
	    final Response response = webTarget.request().get();

	    assertThat(response, hasOkStatus());
	    
	    final List<Story> list = ListStoryType.readEntity(response);
	    assertThat(list, containsStoriesInAnyOrder(list));
	    
	}
	
	@Test @InSequence(9)
	@ShouldMatchDataSet("stories.xml")
	@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
	public void afterGetByText() {}
	
	//ListSearchPagination
	
	@Test @InSequence(10)
	@UsingDataSet("stories.xml")
	@Cleanup(phase = TestExecutionPhase.NONE)
	public void beforeGetListSearchPagination() {}
	
	@Test @InSequence(11)
	@RunAsClient
	public void testGetListSearchPagination(
		@ArquillianResteasyResource(BASE_PATH +"?genre=STORY&theme=CHILDREN&publication=2002-11-10") ResteasyWebTarget webTarget
	) 
	throws Exception {
		
	    final Response response = webTarget.request().get();

	    assertThat(response, hasOkStatus());
	    
	    final List<Story> list = ListStoryType.readEntity(response);
	    assertThat(list, containsStoriesInAnyOrder(list));
		    
	   
	}
	
	@Test @InSequence(12)
	@ShouldMatchDataSet("stories.xml")
	@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
	public void afterGetListSearchPagination() {}
	

}


