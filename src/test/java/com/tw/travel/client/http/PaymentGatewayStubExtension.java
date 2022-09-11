package com.tw.travel.client.http;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.extension.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PaymentGatewayStubExtension implements ParameterResolver, BeforeAllCallback, AfterAllCallback, BeforeEachCallback {

    private WireMockServer mockServer;

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        mockServer.stop();
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        mockServer = new WireMockServer(8024);
        mockServer.start();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        mockServer.resetAll();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == PaymentGatewayStub.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (PaymentGatewayStub) (url, statusCode) -> {
            mockServer.stubFor(post(urlEqualTo(url))
                    .willReturn(aResponse()
                            .withStatus(statusCode)
                    )
            );
        };
    }
}
