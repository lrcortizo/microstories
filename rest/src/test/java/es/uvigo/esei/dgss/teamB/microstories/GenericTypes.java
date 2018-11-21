package es.uvigo.esei.dgss.teamB.microstories;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import es.uvigo.esei.dgss.teamB.microstories.entities.Story;

import java.util.List;




public final class GenericTypes {
	private GenericTypes() {}
	
	public static class ListStoryType extends GenericType<List<Story>> {
		public static ListStoryType INSTANCE = new ListStoryType();
		
		public static List<Story> readEntity(Response response) {
			return response.readEntity(INSTANCE);
		}
	}
}
