package es.uvigo.esei.dgss.teamB.microstories;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
	AuthorResourceRestTest.class,
	StoryResourceRestTest.class
})
@RunWith(Suite.class)
public class ResourceIntegrationTestSuite {
}
