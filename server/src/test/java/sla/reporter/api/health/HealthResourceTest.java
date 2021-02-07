package sla.reporter.api.health;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Test whether the endpoint <i>/api/health</i> works properly.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public class HealthResourceTest extends JerseyTest {

    /**
     * The maximum number of clients.
     */
    private static final int MAX_CLIENTS = 10;

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        // Find first available port.
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return new Application () {
            public Set getSingletons() {
                final Set<Object> set = new HashSet<>();
                set.add(new HealthResource());
                set.add(new JacksonFeature());
                set.add(new SseFeature());
                return set;
            }
        };
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.property(ClientProperties.ASYNC_THREADPOOL_SIZE, MAX_CLIENTS + 2);
    }

    /**
     * Checks whether the check health is responded with status 200.
     */
    @Test
    public void checkHealth() {

        final Response response = target("/api/health").request().get();

        assertEquals("Http Response should be 200: ", Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
