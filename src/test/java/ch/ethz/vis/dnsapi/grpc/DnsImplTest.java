package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import io.grpc.StatusRuntimeException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DnsImplTest {

    private DnsImpl uut;

    @Before
    public void setup() throws JAXBException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.requireClientAuth();

        NetcenterAPI netcenterAPI = new NetcenterAPI(
                mockWebServer.url(DnsImplBase.DEFAULT_PATH).toString(), "fake", "credentials");
        uut = new DnsImpl(netcenterAPI, DnsImplBase.DEFAULT_ISG, Arrays.asList("domain.example", "subdomain.domain.example"));
    }

    @Test
    public void splitValidDomain() {
        String input = "test.domain.example";

        DnsImpl.DnsName result = uut.splitOffZone(input);

        assertEquals("test", result.name);
        assertEquals("domain.example", result.domain);
    }

    @Test
    public void longerDomainGetsPrecedence() {
        String input = "test.subdomain.domain.example";

        DnsImpl.DnsName result = uut.splitOffZone(input);

        assertEquals("test", result.name);
        assertEquals("subdomain.domain.example", result.domain);
    }

    @Test(expected = StatusRuntimeException.class)
    public void invalidDomainIsRejected() {
        String input = "test.unknown.example";

        uut.splitOffZone(input);
    }
}