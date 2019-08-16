package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import okhttp3.mockwebserver.MockWebServer;

import java.util.Arrays;
import java.util.List;

public class DnsImplBase {
    protected final static String DEFAULT_PATH = "/netcenter/rest/";
    protected final static String DEFAULT_ISG = "default-isg";
    protected final static String DEFAULT_SUBDOMAIN = "some.subdomain.test";
    protected final static int    DEFAULT_TTL = 1234;
    protected final static List<String> DEFAULT_VIEWS = Arrays.asList("intern", "extern");

    protected final static String CUSTOM_ISG = "custom-isg";

    protected MockWebServer mockWebServer;
    protected DnsGrpc.DnsBlockingStub stub;

    @org.junit.Rule
    public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

    @org.junit.Before
    public void resetMockServer() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.requireClientAuth();

        NetcenterAPI netcenterAPI = new NetcenterAPI(
                mockWebServer.url(DEFAULT_PATH).toString(), "fake", "credentials");
        DnsImpl dnsImpl = new DnsImpl(netcenterAPI, DEFAULT_ISG);

        String serverName = InProcessServerBuilder.generateName();
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).addService(dnsImpl).build().start());
        stub = DnsGrpc.newBlockingStub(InProcessChannelBuilder.forName(serverName).directExecutor().build());
    }
}
