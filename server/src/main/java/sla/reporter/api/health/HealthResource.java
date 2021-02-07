package sla.reporter.api.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * A class that provides health check endpoint.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
@Path("/api/health")
public class HealthResource {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthResource.class);

    /**
     * An get endpoint for health check.
     *
     * @return The response for corresponding request.
     */
    @GET
    public Response healthCheck() {
        LOGGER.trace("received health check");
        return Response.status(Response.Status.OK).build();
    }
}
