package es.uvigo.esei.dgss.teamB.microstories;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.dgss.teamB.microstories.StoryEJB;
/**
 * Resource that represents the stories in the application.
 */

@Path("microstory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoryResource {
    @EJB
    private StoryEJB storyEjb;

    @Path("recent")
    @GET
    public Response get() {
        return Response.ok(storyEjb.listRecentStories()).build();
    }
    
    @Path("{id}")
    @GET
    public Response getStory(@PathParam("id") Integer id) {
    	return Response.ok(storyEjb.findStory(id)).build();
    }
}
