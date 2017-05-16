package cz.cvut.fel.pauliada.iot.runtime;

import io.silverspoon.bulldog.raspberrypi.RaspberryPiBoardFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public class App {
    public static void main(String[] args) throws Exception {
        if (new RaspberryPiBoardFactory().isCompatibleWithPlatform()) {
            CamelContext context = new DefaultCamelContext();
            context.addRoutes(new RaspberryPiBh1750RouteBuilder());
            context.addRoutes(new RaspberryPiGpioRouteBuilder());
            context.start();
            Thread.sleep(Long.MAX_VALUE);
        }
    }
}
