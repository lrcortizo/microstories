package es.uvigo.esei.dgss.teamB.microstories;

import static es.uvigo.esei.dgss.teamB.microstories.entities.StoriesDataset.existentStory;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import static es.uvigo.esei.dgss.teamB.microstories.entities.IsEqualToStory.containsStoriesInAnyOrder;

@RunWith(Arquillian.class)
public class AuthorResourceRestTest {
	private final static String BASE_PATH = "user/";
	//User pepe : pepepass
	private static final String BASIC_AUTHORIZATION = "Basic cGVwZTpwZXBlcGFzcw==";
	
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
			.addClass(AuthorResource.class)
			.addClasses(CORSFilter.class, IllegalArgumentExceptionMapper.class, SecurityExceptionMapper.class)
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
	public void beforeListMyStories() {}

	@Test @InSequence(2)
	@RunAsClient
	@Header(name = "Authorization", value = BASIC_AUTHORIZATION)
	public void testListMyStories(
		@ArquillianResteasyResource(BASE_PATH + "pepe/microstory") ResteasyWebTarget webTarget
		) throws Exception {
		    final Response response = webTarget.request().get();

		    assertThat(response, hasOkStatus());

		    final List<Story> list = ListStoryType.readEntity(response);

		    assertThat(list, is(not(equalTo(nullValue()))));
	}

	@Test @InSequence(3)
	@ShouldMatchDataSet("stories.xml")
	@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
	public void afterListMyStories() {}

}
