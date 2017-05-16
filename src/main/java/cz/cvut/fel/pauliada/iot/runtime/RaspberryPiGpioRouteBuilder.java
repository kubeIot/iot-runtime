package cz.cvut.fel.pauliada.iot.runtime;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;
import io.silverspoon.bulldog.raspberrypi.RaspiNames;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RaspberryPiGpioRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        restConfiguration().component("restlet").host("localhost").port(8080).bindingMode(RestBindingMode.auto);

        //rest("/").get("/gpio/1").route().from("bulldog://gpio?pin=P1_3");

        String[] pinsArray = new String[]{RaspiNames.P1_3,
                RaspiNames.P1_5,
                RaspiNames.P1_7,
                RaspiNames.P1_8,
                RaspiNames.P1_10,
                RaspiNames.P1_11,
                RaspiNames.P1_12,
                RaspiNames.P1_13,
                RaspiNames.P1_15,
                RaspiNames.P1_16,
                RaspiNames.P1_18,
                RaspiNames.P1_19,
                RaspiNames.P1_21,
                RaspiNames.P1_22,
                RaspiNames.P1_23,
                RaspiNames.P1_24,
                RaspiNames.P1_26};

        rest("/").get("/gpio").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();

            Board board = Platform.createBoard();
            List<Boolean> pins = Arrays.stream(pinsArray)
                    .map(pin -> board.getPin(pin).as(DigitalOutput.class).isHigh())
                    .collect(Collectors.toList());
            board.shutdown();

            JSONArray jsonArray = new JSONArray(pins);
            jsonObject.put("pins", jsonArray);
            exchange.getOut().setBody(jsonObject.toString());
        });

        rest("/").get("/gpio/1").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_3).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });

        rest("/").post("/gpio/1").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_3&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_3&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/2").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_5).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/2").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_5&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_5&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/3").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_7).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/3").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_7&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_7&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/4").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_8).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/4").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_8&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_8&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/5").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_10).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/5").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_10&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_10&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/6").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_11).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/6").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_11&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_11&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/7").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_12).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/7").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_12&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_12&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/8").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_13).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/8").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_13&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_13&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/9").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_15).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/9").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_15&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_15&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/10").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_16).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/10").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_16&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_16&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/11").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_18).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/11").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_18&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_18&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/12").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_19).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/12").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_19&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_19&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/13").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_21).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/13").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_21&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_21&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/14").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_22).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/14").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_22&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_22&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/15").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_23).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/15").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_23&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_23&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/16").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_24).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/16").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_24&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_24&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

        rest("/").get("/gpio/17").route().process(exchange -> {
            JSONObject jsonObject = new JSONObject();
            Board board = Platform.createBoard();
            boolean status = board.getPin(RaspiNames.P1_26).as(DigitalOutput.class).isHigh();
            board.shutdown();
            jsonObject.put("state", status);
            exchange.getOut().setBody(jsonObject.toString());
        });
        rest("/").post("/gpio/17").route()
                .choice()
                .when().jsonpath("[?($.state == true)]").to("bulldog://gpio?pin=P1_26&value=HIGH")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                })
                .when().jsonpath("[?($.state == false)]").to("bulldog://gpio?pin=P1_26&value=LOW")
                .process(exchange -> {
                    exchange.getIn().setBody(null);
                    exchange.getOut().setBody(null);
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, constant(204));
                    exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_TEXT, constant("No Content"));
                });

    }
}
