package main.java.dev.davi.ws;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import main.java.dev.davi.model.Message;
import main.java.dev.davi.model.User;


@Path("/message")
@Transactional
public class MessageWS {

   @GET
   @Path("/save/{text}/{idUser}")
   @Produces(MediaType.APPLICATION_JSON)
   public Message save(@PathParam("text") String text, @PathParam("idUser")
    Long idUser) {

      Message message = new Message();
      message.setText(text);
      message.persistAndFlush();

      User user = User.findById(idUser);
      if (user == null){
        throw new BadRequestException("User not found");
      }

      user.addMessage(message);
      user.persistAndFlush();

      return message;
   }

   @DELETE
  @Path("/delete/{text}")
  @Transactional
  public List<Message> delete(//
      @Parameter(description = "Mensagem", required = true) //
      @PathParam("text") String text) {
    Message.delete("text", text);
    return Message.listAll();
  }
    
}
