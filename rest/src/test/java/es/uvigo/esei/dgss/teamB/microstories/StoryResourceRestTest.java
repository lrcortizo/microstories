package es.uvigo.esei.dgss.teamB.microstories;

import org.jboss.arquillian.container.test.api.Deployment;
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

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import es.uvigo.esei.dgss.teamB.microstories.Story;
import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.StoryResource;

import static es.uvigo.esei.dgss.teamb.microstories.entities.IsEqualToStory.containsStoriesInAnyOrder;
import static es.uvigo.esei.dgss.teamb.microstories.entities.StoriesDataset.stories;
import static es.uvigo.esei.dgss.teamb.microstories.http.util.HasHttpStatus.hasOkStatus;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;

@PermitAll
@Consumes(MediaType.APPLICATION_JSON)
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
	
	@Test(expected=java.lang.NullPointerException.class) @InSequence(2)
	@PermitAll
	@UsingDataSet("stories.xml")
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
}


