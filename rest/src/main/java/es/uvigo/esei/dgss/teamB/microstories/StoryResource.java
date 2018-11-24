package es.uvigo.esei.dgss.teamB.microstories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    
    @GET
    public Response getByText(@QueryParam("contains") String contains) {
    	return Response.ok(storyEjb.getByText(contains, 10, 10)).build();
    }
    
    @GET 
    public Response getlistSearchPagination(@QueryParam("genre") String genre, @QueryParam("theme") String theme, @QueryParam("publication") String publication, @QueryParam("pagination") Integer pagination, @QueryParam("items") Integer items) throws ParseException{
	    	
    	if(publication != null) {
        Date date;
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        try {
            date = new Date(publication); // yes, I know this is a deprecated method
        } catch(Exception e) {
            date = df.parse(publication);
        }
	        if(pagination == null) {
				if(items==null) return Response.ok(storyEjb.listSearchPagination(1,10, genre, theme, date)).build();
				if(items < 101) return Response.ok(storyEjb.listSearchPagination(1,items, genre, theme, date)).build();
				else return Response.ok("You cant get more than 100 stories").build();
			}else{
				if(items==null) return Response.ok(storyEjb.listSearchPagination(pagination,10, genre, theme, null)).build();	
				if(items < 101) return Response.ok(storyEjb.listSearchPagination(pagination,items, genre, theme, date)).build();
				else return Response.ok("You cant get more than 100 stories").build();
			}
    	}else {
    	
			if(pagination == null) {
				if(items==null) return Response.ok(storyEjb.listSearchPagination(1,10, genre, theme, null)).build();
				if(items < 101) return Response.ok(storyEjb.listSearchPagination(1,items, genre, theme, null)).build();
				else return Response.ok("You cant get more than 100 stories").build();
			}else{
				if(items==null) return Response.ok(storyEjb.listSearchPagination(pagination,10, genre, theme, null)).build();	
				if(items < 101) return Response.ok(storyEjb.listSearchPagination(pagination,items, genre, theme, null)).build();
				else return Response.ok("You cant get more than 100 stories").build();
			}
    	}
		
    }
    	
    
}
