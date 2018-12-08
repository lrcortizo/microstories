package es.uvigo.esei.dgss.teamB.microstories;

import static es.uvigo.esei.dgss.teamB.microstories.entities.IsEqualToStory.containsStoriesInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.extension.rest.client.Header;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.persistence.Cleanup;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.ShouldMatchDataSet;
import org.jboss.arquillian.persistence.TestExecutionPhase;
import org.jboss.arquillian.persistence.UsingDataSet;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static es.uvigo.esei.dgss.teamB.microstories.http.util.HasHttpStatus.hasOkStatus;
import es.uvigo.esei.dgss.teamB.microstories.GenericTypes.ListStoryType;
import es.uvigo.esei.dgss.teamB.microstories.entities.Author;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

@RunWith(Arquillian.class)
public class AuthorResourceRestTest {
	private final static String BASE_PATH = "user/";
	//User ana : anapass
	private static final String BASIC_AUTHORIZATION = "Basic YW5hOmFuYXBhc3M=";
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
			.addClass(StoryResource.class)
			.addClasses(CORSFilter.class, IllegalArgumentExceptionMapper.class, SecurityExceptionMapper.class)
			.addPackage(StoryEJB.class.getPackage())
			.addPackage(Story.class.getPackage())
			.addPackage(Author.class.getPackage())
			.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
			.addAsWebInfResource("jboss-web.xml")
			.addAsWebInfResource("web.xml")
            .addAsResource("arquillian.extension.persistence.properties")
            .addAsResource("arquillian.extension.persistence.dbunit.properties")
			.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
}
