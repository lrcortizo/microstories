package es.uvigo.esei.dgss.teamB.microstories;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

	@EJB
    private StoryEJB storyEjb;

	@Context
	private UriInfo uriInfo;

	@Path("{login}/microstory")
    @GET
    public Response getListMyStories(@QueryParam("pagination") Integer pagination, @QueryParam("items") Integer items) {

		if(pagination == null) {
			if(items==null) return Response.ok(storyEjb.listMyStories(1,10)).build();
			if(items < 101) return Response.ok(storyEjb.listMyStories(1,items)).build();
			else return Response.ok("You cant get more than 100 stories").build();
		}else{
			if(items==null) return Response.ok(storyEjb.listMyStories(pagination,10)).build();
			if(items < 101) return Response.ok(storyEjb.listMyStories(pagination,items)).build();
			else return Response.ok("You cant get more than 100 stories").build();
		}
    }
}
