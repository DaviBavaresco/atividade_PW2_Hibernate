package main.java.dev.davi.ws;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.DELETE;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import main.java.dev.davi.model.Message;
import main.java.dev.davi.model.User;


@Path("/user")
@Transactional
public class UserWS {

    @GET
    @Path("/save/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public User save(@PathParam("name") String name) {
        User user = new User();
        user.setName(name);
        user.persist();
        return user;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<User> list() {
        return User.listAll();
    }

    @GET
    @Path("/list/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public User list(@PathParam("id") Long id) {
        return User.findById(id);
    }

    @DELETE
    @Path("/delete/{name}")
    @Transactional
    public List<User> delete(//
        @Parameter(description = "Name", required = true) //
        @PathParam("name") String name) {
        User.delete("name", name);
        return User.listAll();
  }
    
}
