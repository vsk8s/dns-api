package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.dto.*;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jaxb.JaxbConverterFactory;

import javax.xml.bind.JAXBContext;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ARecordManagerTest {
    private static MockWebServer server;
    private static ARecordManager aRecordManager;

    @BeforeAll
    public static void setup() throws Exception {
        server = new MockWebServer();
        server.requireClientAuth();

        // Testing GET
        server.enqueue(new MockResponse().setResponseCode(200).setBody("<usedIps><usedIp><ip>192.0.2.10</ip><ipSubnet>192.0.2.0</ipSubnet><fqname>some.server.some.tld</fqname><forward>Y</forward><reverse>Y</reverse><ttl>3600</ttl><dhcp>N</dhcp><ddns>N</ddns><isgGroup>some-isg</isgGroup><lastDetection>2019-07-23 22:06</lastDetection><ipVersion>v4</ipVersion><views><view>extern</view><view>intern</view></views></usedIp></usedIps>"));
        server.enqueue(new MockResponse().setResponseCode(200).setBody("<usedIps></usedIps>"));
        server.enqueue(new MockResponse().setResponseCode(500).setBody("<errors><error>Some internal error occurred!</error></errors>"));

        // Testing POST
        server.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));
        server.enqueue(new MockResponse().setResponseCode(400).setBody("<errors><error>Missing some field</error></errors>"));

        // Testing DELETE
        server.enqueue(new MockResponse().setResponseCode(200).setBody("<success>Done!</success>"));
        server.enqueue(new MockResponse().setResponseCode(400).setBody("<errors><error>Some error occurred</error></errors>"));

        JAXBContext context = JAXBContext.newInstance(GetARecordResponse.class, XmlCreateARecordRequestWrapper.class,
                GetCNameRecordResponse.class, XmlCreateCNameRecordRequestWrapper.class, XmlSuccess.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(server.url("/netcenter/rest/"))
                .addConverterFactory(JaxbConverterFactory.create(context))
                .build();

        aRecordManager = retrofit.create(ARecordManager.class);
    }

    @Test
    @Order(1)
    public void successfullyGetARecordWithRecord() throws Exception {
        Response<GetARecordResponse> response = aRecordManager.GetARecord("some.server.some.tld").execute();
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());

        GetARecordResponse resp = response.body();
        ARecord first = resp.getRecords().get(0);
        assertEquals("some.server.some.tld", first.getFqName());
        assertEquals("v4", first.getIpVersion());
        assertEquals("192.0.2.10", first.getIp());
        assertEquals("192.0.2.0", first.getIpSubnet());
        assertEquals("some-isg", first.getIsgGroup());
        assertTrue(first.getForward());
        assertTrue(first.getReverse());
        assertFalse(first.getDdns());
        assertFalse(first.getDdns());
        assertTrue(first.getViews().size() == 2
                && first.getViews().contains("intern")
                && first.getViews().contains("extern"));
    }

    @Test
    @Order(2)
    public void successfullyGetARecordWithEmptyResponse() throws IOException {
        Response<GetARecordResponse> response = aRecordManager.GetARecord("doesnotexist.some.tld").execute();
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertTrue(response.body().getRecords().isEmpty());
    }

    @Test
    @Order(3)
    public void errorDuringGetARecord() throws IOException {
        Response<GetARecordResponse> response = aRecordManager.GetARecord("something.some.tld").execute();
        assertFalse(response.isSuccessful());
        assertNull(response.body());
        assertNotNull(response.errorBody());
    }

    @Test
    @Order(4)
    public void successfullyCreateARecord() throws IOException {
        XmlCreateARecordRequestWrapper request = makeCreateARecordRequest();

        Response<XmlSuccess> response = aRecordManager.CreateARecord(request).execute();
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertEquals("Done!", response.body().getMessage());
    }

    @Test
    @Order(5)
    public void errorDuringCreateARecord() throws IOException {
        XmlCreateARecordRequestWrapper request = makeCreateARecordRequest();

        Response<XmlSuccess> response = aRecordManager.CreateARecord(request).execute();
        assertFalse(response.isSuccessful());
        assertNull(response.body());
    }

    @Test
    @Order(6)
    public void successfullyDeleteARecord() throws IOException {
        Response<XmlSuccess> response = aRecordManager.DeleteARecord("192.0.2.10", "some-fqdn").execute();
        assertTrue(response.isSuccessful());
        assertNotNull(response.body());
        assertEquals("Done!", response.body().getMessage());
    }

    @Test
    @Order(7)
    public void errorDuringDeleteARecord() throws IOException {
        Response<XmlSuccess> response = aRecordManager.DeleteARecord("192.0.2.10", "some-fqdn").execute();
        assertFalse(response.isSuccessful());
        assertNull(response.body());
    }

    private XmlCreateARecordRequestWrapper makeCreateARecordRequest() {
        return new XmlCreateARecordRequestWrapper(
                CreateARecordRequest.Builder.newBuilder()
                        .withIp("192.0.2.10")
                        .withIpName("example-ip-record")
                        .withSubdomain("some-subdomain")
                        .withIsgGroup("some-isg-group")
                        .build());
    }
}
