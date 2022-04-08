package in.gov.abdm.uhi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@SpringBootApplication
public class GatewayApplication {

    @Value("${abdm.uhi.gateway.url}")
    private String gatewayHost;

    @Value("${abdm.uhi.requester.url}")
    private String requesterUri;

    @Value("${abdm.uhi.responder.url}")
    private String responderUri;

    @Value("${abdm.uhi.target_prefix}")
    private String targetPrefix;

    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class, args);

    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

        log.info("gateway : " + gatewayHost);
        log.info("requester : " + requesterUri);
        log.info("responder : " + responderUri);
        log.info("target_prefix : " + targetPrefix);

        return builder.routes()
                .route("path_route_on_search", r -> r.method(HttpMethod.POST).and().path(targetPrefix + "/on_search")
                        .uri(requesterUri))
                .route("path_route_search", r -> r.method(HttpMethod.POST).and().path("/search")
                        .uri(responderUri))
                .build();

    }

}