package REST;

import DAO.DAO;
import VAO.ParkirnaHisa;
import VAO.Rezervacija;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParkiranjeResource {
	
	@GET
	@Path("/rezervacije")
	public Response vrniVseRezervacije(){
		return Response.ok(DAO.getSeznamRezervacij()).build();
	}
	@GET
	@Path("/parkirneHise")
	public Response vrniVseParkirneHise(){
		return Response.ok(DAO.getSeznamParkirnihHis()).build();
	}
	@GET
	@Path("/parkirneHise/{id}")
	public Response vrniParkirnoHiso(@PathParam("id") long id){
		List<ParkirnaHisa> seznam = DAO.getSeznamParkirnihHis();
		if (seznam != null) {
			return Response.ok(seznam).build();
		} else {
			return Response.status(403).entity("ParkirneHiseMogoceNajtiException").build();
		}
	}
	@GET
	@Path("/rezervacije/{id}")
	public Response vrniRezervacijo(@PathParam("id") long id){
		Rezervacija r = DAO.getRezervacija(id);
		if (r != null) {
			return Response.ok(r).build();
		} else {
			return Response.status(403).entity("RezervacijeNiMogoceNajtiException").build();
		}
	}
	@POST
	@Path("/rezervacije")
	public Response dodajRezervacijo(Rezervacija r){
		DAO.addRezervacija(r);
		return Response.ok(r).build();
	}
	@POST
	@Path("/parkirneHise")
	public Response dodajParkirnoHiso(ParkirnaHisa p){
		DAO.addParkirnaHisa(p);
		return Response.ok(p).build();
	}
}
