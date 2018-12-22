package es.uvigo.esei.dgss.teamB.microstories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

/**
 * Resource that represents the stories in the application.
 */

@Path("microstory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StoryResource {
    @EJB
    private StoryEJB storyEjb;
    
	@Context
	private UriInfo uriInfo;

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
    public Response getByText(@QueryParam("contains") String contains, @QueryParam("pagination") Integer pagination, @QueryParam("items") Integer items) {
    	
    	if(pagination == null) {
    		if (items == null) {
    			return Response.ok(storyEjb.getByText(contains, 1, 10)).build();
    		} else if (items < 101) {
    			return Response.ok(storyEjb.getByText(contains, 1, items)).build();
    		} else {
    			return Response.ok("You cant get more than 100 stories").build();
    		}
    	} else {
    		if (items == null) {
    			return Response.ok(storyEjb.getByText(contains, pagination, 10)).build();
    		} else if (items < 101) {
    			return Response.ok(storyEjb.getByText(contains, pagination, items)).build();
    		} else {
    			return Response.ok("You cant get more than 100 stories").build();
    		}
    	}
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
    
    @Path("hottest")
    @GET
    public Response mostPopular() {
    	return Response.ok(storyEjb.mostPopular()).build();
    }
    
	@POST
	public Response createStory(Story story) {
		this.storyEjb.createStory(story);
		return 
				Response.created(
					uriInfo.getAbsolutePathBuilder()
						.path(story.getId().toString()).build())
					.build();
	}
	
	@PUT
	public Response updateStory(Story story) {
		this.storyEjb.updateStory(story);
		
		return Response.ok().build();
	}
	
	@Path("{id}")
	@DELETE
	public Response deleteStory(@PathParam("id") Integer id) {
		if (id == null)
			throw new IllegalArgumentException("id can't be null");
		
		this.storyEjb.removeStory(id);
		
		return Response.ok().build();
	}
	
	@Path("user/{login}/microstory/favourite")
	@GET
	public Response listFavouriteStories(@PathParam("login") String login, @QueryParam("pagination") Integer pagination, @QueryParam("items") Integer items) {
		this.storyEjb.listFavouriteStories(pagination, items);
		
    	if(pagination == null) {
    		if (items == null) {
    			return Response.ok(this.storyEjb.listFavouriteStories(1, 10)).build();
    		} else if (items < 101) {
    			return Response.ok(this.storyEjb.listFavouriteStories(1, items)).build();
    		} else {
    			return Response.ok("You cant get more than 100 stories").build();
    		}
    	} else {
    		if (items == null) {
    			return Response.ok(this.storyEjb.listFavouriteStories(pagination, 10)).build();
    		} else if (items < 101) {
    			return Response.ok(this.storyEjb.listFavouriteStories(pagination, items)).build();
    		} else {
    			return Response.ok("You cant get more than 100 stories").build();
    		}
    	}
	}
}
