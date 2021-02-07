package sla.reporter;

import io.netty.channel.Channel;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import sla.reporter.api.health.HealthResource;
import sla.reporter.api.subscription.SubscriptionResource;

import javax.ws.rs.core.Application;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/**
 * Main server process.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public class Main {

    /**
     * Method called from the operating system.
     */
    public static void main(final String[] args) {
        startRestfulServer();
    }

    /**
     * Starts the server.
     */
    private static void startRestfulServer() {
        final ResourceConfig resourceConfig = ResourceConfig.forApplication(
            new Application () {
                public Set getSingletons() {
                    final Set<Object> set = new HashSet<>();
                    set.add(new HealthResource());
                    set.add(new SubscriptionResource());
                    return set;
                }
            }
        ).register(new JacksonFeature());

        final URI uri = URI.create("http://localhost:8080/");
        final Channel server = NettyHttpContainerProvider.createHttp2Server(uri, resourceConfig, null);
        Runtime.getRuntime().addShutdownHook(new Thread((server::close)));
    }
}
