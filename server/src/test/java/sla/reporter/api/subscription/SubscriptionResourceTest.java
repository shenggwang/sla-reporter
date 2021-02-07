package sla.reporter.api.subscription;

import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import sla.reporter.model.Gender;
import sla.reporter.model.Subscriber;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test if the request to {@uri /api/subscription} API is working properly.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public class SubscriptionResourceTest extends JerseyTest {

    /**
     * The maximum number of clients for test purpose.
     */
    private static final int MAX_CLIENTS = 10;

    /**
     * A random newsletter id used for test purpose.
     */
    private static final String NEWSLETTER_ID = "fdsavdsasdsda";

    /**
     * A default subscriber for test purpose.
     */
    private final Subscriber subscriber = new Subscriber.Builder()
            .email("jonh@gmail.com")
            .firstName("Jonh")
            .gender(Gender.MALE)
            .birthDay(LocalDate.of(2000, Month.DECEMBER, 25))
            .consent(true)
            .newsletterId(NEWSLETTER_ID)
            .build();

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        // Find first available port.
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return new Application () {
            public Set getSingletons() {
                final Set<Object> set = new HashSet<>();
                set.add(new SubscriptionResource());
                set.add(new JacksonFeature());
                return set;
            }
        };
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.property(ClientProperties.ASYNC_THREADPOOL_SIZE, MAX_CLIENTS + 2);
    }

    /**
     * TODO separate storage folder of application and test.
     * Clean storage produced from each test.
     *
     * @throws IOException if something went wrong.
     */
    @After
    public void cleanStorage() throws IOException {
        FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + File.separator + "storage"));
    }

    /**
     * Ensures that subscriber is addable and gettable.
     */
    @Test
    public void writeAndGetSubscriber() {
        final Response postResponse = target("/api/subscription").request().post(Entity.json(this.subscriber.getJsonObject()));
        assertEquals("Http Response should be 201.", Response.Status.CREATED.getStatusCode(), postResponse.getStatus());
        final Subscriber postSubscriber = new Subscriber.Builder().fromJsonObject(postResponse.readEntity(JSONObject.class)).build();
        assertEquals("The created subscriber should be equals to send one.", this.subscriber, postSubscriber);

        final Response getResponse = target("/api/subscription/" + this.subscriber.getEmail()).request().get();
        assertEquals("Http Response should be 200.", Response.Status.OK.getStatusCode(), getResponse.getStatus());
        final Subscriber getSubscriber = new Subscriber.Builder().fromJsonObject(getResponse.readEntity(JSONObject.class)).build();
        assertEquals("The created subscriber should be equals to send one.", this.subscriber, getSubscriber);
    }

    /**
     * Ensures that no repeated subscriber can be created.
     */
    @Test
    public void writeTwoSameSubscribers() {
        final Response response = target("/api/subscription").request().post(Entity.json(this.subscriber.getJsonObject()));
        assertEquals("Http Response should be 201.", Response.Status.CREATED.getStatusCode(), response.getStatus());
        final Subscriber postSubscriber = new Subscriber.Builder().fromJsonObject(response.readEntity(JSONObject.class)).build();
        assertEquals("The created subscriber should be equals to send one.", this.subscriber, postSubscriber);

        final Response repeatedResponse = target("/api/subscription").request().post(Entity.json(this.subscriber.getJsonObject()));
        assertEquals("Http Response should be 400.", Response.Status.BAD_REQUEST.getStatusCode(), repeatedResponse.getStatus());
        assertNull("There should be no json object returned.", repeatedResponse.readEntity(JSONObject.class));
    }

    /**
     * Ensures that can't get non existing subscriber.
     */
    @Test
    public void getNoExistingSubscriber() {
        final Response response = target("/api/subscription/" + this.subscriber.getEmail()).request().get();
        assertEquals("Http Response should be 404.", Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertNull("There should be no json object returned.", response.readEntity(JSONObject.class));
    }
}
