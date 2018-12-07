package es.uvigo.esei.dgss.teamB.microstories;

import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
import es.uvigo.esei.dgss.teamB.microstories.entities.Author;
import es.uvigo.esei.dgss.teamB.microstories.entities.Story;
import es.uvigo.esei.dgss.teamB.microstories.service.util.security.RoleCaller;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.persistence.CleanupUsingScript;
import org.jboss.arquillian.persistence.UsingDataSet;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@UsingDataSet("stories.xml")
@CleanupUsingScript({ "cleanup.sql", "cleanup-autoincrement.sql" })
public class StoryEJBIllegalAccessIntegrationTest {

	@Inject
	private StoryEJB storyEjb;
	
	@EJB(beanName = "author-caller")
	private RoleCaller asAuthor;

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addClasses(StoryEJB.class, Story.class, Author.class)
				.addPackage(RoleCaller.class.getPackage())
				.addPackage(Author.class.getPackage())
				.addPackage(Story.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource("jboss-web.xml")
				.addAsResource("arquillian.extension.persistence.properties")
				.addAsResource("arquillian.extension.persistence.dbunit.properties")
				.addAsWebInfResource("beans.xml", "beans.xml");
	}
	
	@Test
	public void testListRecentStoriesNoRole() {
		storyEjb.listRecentStories();
	}

	@Test
	public void testListRecentStoriesRoleAuthor() {
		asAuthor.run(this::testListRecentStoriesNoRole);
	}

	@Test
	public void testFindStoryNoRole() {
		storyEjb.findStory(1);
	}

	@Test
	public void testFindStoryRoleAuthor() {
		asAuthor.run(this::testFindStoryNoRole);
	}

	@Test
	public void testGetByTextNoRole() {

		Integer pageSize = 9;

		storyEjb.getByText("Microrrelato 1", 1, pageSize);
	}

	@Test
	public void testGetByTextRoleAuthor() {
		asAuthor.run(this::testGetByTextNoRole);
	}

	@Test
	public void testListSearchPaginationNoRole() {

		Integer nStories = 9;

		storyEjb.listSearchPagination(1, nStories, null, "CHILDREN", null);
	}

	@Test
	public void testListSearchPaginationRoleAuthor() {
		asAuthor.run(this::testListSearchPaginationNoRole);
	}

	@Test(expected = EJBAccessException.class)
	public void testListMyStoriesNoRole() {
		storyEjb.listMyStories();
	}

	@Test
	public void testListMyStoriesRoleAuthor() {
		asAuthor.run(this::testListMyStoriesNoRole);
	}
}
