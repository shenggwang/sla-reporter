package sla.reporter.api.subscription;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sla.reporter.database.nosql.JsonFileManager;
import sla.reporter.model.Subscriber;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A class that provides subscription endpoint.
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
@Path("/api/subscription")
public class SubscriptionResource {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionResource.class);

    /**
     * Gets the subscriber by given email from endpoint API.
     *
     * @param email The expected email.
     * @return The response for corresponding request.
     */
    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriber(@PathParam("email") String email) {
        final Subscriber subscriber;
        try {
            subscriber = JsonFileManager.INSTANCE.readObject(email);
            LOGGER.info("Got subscriber: {}", subscriber);
        } catch (final Exception ignored) {
            LOGGER.error("Failed getting subscriber with email: {}", email);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(subscriber).build();
    }

    /**
     * Adds a new subscriber from endpoint API.
     *
     * @param object The expected {@link JSONObject}.
     * @return The response for corresponding request.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSubscriber(final JSONObject object) {
        final Subscriber subscriber = new Subscriber.Builder()
                .fromJsonObject(object)
                .build();
        if (JsonFileManager.INSTANCE.writeObject(subscriber)) {
            LOGGER.info("Added subscriber: {}", object.toString());
            return Response.status(Response.Status.CREATED).entity(subscriber).build();
        }
        LOGGER.error("Failed adding subscriber: {}", object.toString());
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    /**
     * Updates an existing subscriber from endpoint API.
     *
     * @param object The expected {@link JSONObject}.
     * @return The response for corresponding request.
     * @implNote Not implemented yet.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubscriber(final JSONObject object) {
        LOGGER.warn("Tried to update subscriber: {}", object.toString());
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    /**
     * Deletes an existing subscriber by given email from endpoint API.
     *
     * @param email The expected email.
     * @return The response for corresponding request.
     * @implNote Not implemented yet.
     */
    @DELETE
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscriber(@PathParam("email") String email) {
        LOGGER.warn("Tried to remove subscriber with email: {}", email);
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}

