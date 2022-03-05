package de.rmh78.filter;

import static javax.ws.rs.core.Response.Status.CREATED;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;

@Path("/filter-events")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilterEventResource {

    @GET
    public Uni<List<FilterEvent>> getAllEvents() {
        return FilterEvent.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<FilterEvent> getEventById(long id) {
        return FilterEvent.findById(id);
    }

    @POST
    public Uni<Response> createEvent(FilterEvent event) {
        return Panache.withTransaction(event::persist)
            .replaceWith(Response.ok(event).status(CREATED)::build);

    }
}
