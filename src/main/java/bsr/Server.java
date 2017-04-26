package bsr;

import bsr.rs.TransferResource;
import bsr.util.Auth;
import bsr.util.DBUtil;
import bsr.ws.Bank;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
//import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import javax.ws.rs.core.UriBuilder;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Paweł on 2017-01-12.
 */
public class Server
{
    public static final String WSEndpoint = "http://localhost:8888/ws/bank";
    public static final int RSPort = 8088;

    public static void main(String[] args) throws IOException, URISyntaxException {

        //DBUtil.createDB();
        Endpoint.publish(WSEndpoint, new Bank());
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(RSPort).build();
        ResourceConfig config = new ResourceConfig(TransferResource.class);
        config.packages("com.fasterxml.jackson.jaxrs");
        config.register(Auth.class);
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(baseUri, config);
        httpServer.start();
    }


}
