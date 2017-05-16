package cz.cvut.fel.pauliada.iot.runtime;

import io.silverspoon.bulldog.core.io.bus.i2c.I2cBus;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.BoardFactory;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.devices.sensors.BH1750LightIntensitySensor;
import io.silverspoon.bulldog.raspberrypi.RaspberryPiBoardFactory;
import io.silverspoon.bulldog.raspberrypi.RaspiNames;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.json.JSONObject;

import java.io.IOException;

public class RaspberryPiBh1750RouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);

        rest("/")
                .get("/device/bh1750/readLuminanceNormalized").route().process(exchange -> {

            Board board = Platform.createBoard();
            I2cBus bus = board.getI2cBus(RaspiNames.I2C_1);
            BH1750LightIntensitySensor sensor = new BH1750LightIntensitySensor(bus, 0x23);
            sensor.initMode(BH1750LightIntensitySensor.MODE_HIGH_RES_05_LX_CONTINUOUS);
            double luminosity = sensor.readLuminanceNormalized();
            board.shutdown();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("return", luminosity);
            exchange.getOut().setBody(jsonObject);
        }).onException(IOException.class).process(exchange -> {
            exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, 503);
            exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_TEXT, "backendError");
            exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "plain/text");
            exchange.getOut().setBody("We encountered an internal error. Please try again using truncated exponential backoff.");
        }).onException(Exception.class).process(exchange -> {
            exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_CODE, 500);
            exchange.getOut().getHeader(Exchange.HTTP_RESPONSE_TEXT, "backendError");
            exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "plain/text");
            exchange.getOut().setBody("We encountered an internal error. Please try again using truncated exponential backoff.");
        });
    }
}
