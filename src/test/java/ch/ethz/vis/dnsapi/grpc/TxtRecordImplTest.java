package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.netcenter.types.CreateTxtRecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.JsonResponse;
import ch.ethz.vis.dnsapi.netcenter.types.TxtRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.StatusRuntimeException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

import java.io.IOException;

public class TxtRecordImplTest extends DnsImplBase {
    private static final String ID = "123456";
    private static final String VALUE = "txt-record-value";
    private static final String TXT_NAME = "some-txt-name";
    private static final String SUBDOMAIN = "some-subdomain.test";
    private static final String CUSTOM_ISG = "custom-isg";

    private static final ObjectMapper mapper = new ObjectMapper();

    @org.junit.Test
    public void successfullyCreateTxtRecordWithDefaultIsg() throws InterruptedException, IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"id\":\"" + ID + "\",\"fqName\":\"" + TXT_NAME + "." + SUBDOMAIN + "\",\"value\":\"" + VALUE + "\"}"));

        Dnsapi.CreateTxtRecordRequest request = defaultCreateTxtRecordRequest().build();

        Dnsapi.TxtResponse response = stub.createTxtRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest rr = mockWebServer.takeRequest();
        CreateTxtRecordRequest generatedRequest = mapper.readValue(rr.getBody().inputStream(), CreateTxtRecordRequest.class);
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(DEFAULT_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test
    public void successfullyCreateTxtRecordWithCustomIsg() throws InterruptedException, IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"id\":\"" + ID + "\",\"fqName\":\"" + TXT_NAME + "." + SUBDOMAIN + "\",\"value\":\"" + VALUE + "\"}"));

        Dnsapi.CreateTxtRecordRequest request = defaultCreateTxtRecordRequest()
                .setOptions(Dnsapi.RecordOptions.newBuilder().setIsgGroup(CUSTOM_ISG).build())
                .build();

        Dnsapi.TxtResponse response = stub.createTxtRecord(request);
        org.junit.Assert.assertNotNull(response);
        org.junit.Assert.assertEquals(ID, response.getId());

        RecordedRequest rr = mockWebServer.takeRequest();
        CreateTxtRecordRequest generatedRequest = mapper.readValue(rr.getBody().inputStream(), CreateTxtRecordRequest.class);
        org.junit.Assert.assertNotNull(generatedRequest);

        assertRequiredFieldsSet(generatedRequest);
        org.junit.Assert.assertEquals(CUSTOM_ISG, generatedRequest.getIsgGroup());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryCreateTxtRecordWithErrorFromBackend() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"errors\": [{\"rowId\":\"undef\",\"field\":\"name\",\"errMsg\":\"Some error happened\"}]}"));

        Dnsapi.CreateTxtRecordRequest request = defaultCreateTxtRecordRequest().build();

        Dnsapi.TxtResponse response = stub.createTxtRecord(request);
    }

    @org.junit.Test
    public void successfullyDeleteTxtRecord() throws InterruptedException, IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"id\":\"" + ID + "\",\"fqName\":\"" + TXT_NAME + "." + SUBDOMAIN + "\",\"value\":\"" + VALUE + "\",\"netsupName\":\"" + DEFAULT_ISG + "\"}"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"status\":\"deleted\"}"));

        Dnsapi.DeleteTxtRecordRequest request = defaultDeleteTxtRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteTxtRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest findIdRequest = mockWebServer.takeRequest();
        TxtRecord record = mapper.readValue(findIdRequest.getBody().inputStream(), TxtRecord.class);
        org.junit.Assert.assertNotNull(record);
        org.junit.Assert.assertEquals(VALUE, record.getValue());
        org.junit.Assert.assertEquals(TXT_NAME + "." + SUBDOMAIN, record.getFqName());

        RecordedRequest deletionRequest = mockWebServer.takeRequest();
        org.junit.Assert.assertEquals(DEFAULT_PATH + "txt/" + ID, deletionRequest.getPath());
        org.junit.Assert.assertEquals("DELETE", deletionRequest.getMethod());
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryDeleteTxtRecordWithErrorInSecondRequest() throws InterruptedException, IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"id\":\"" + ID + "\",\"fqName\":\"" + TXT_NAME + "." + SUBDOMAIN + "\",\"value\":\"" + VALUE + "\",\"netsupName\":\"" + DEFAULT_ISG + "\"}"));
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"errors\": [{\"rowId\":\"undef\",\"field\":\"name\",\"errMsg\":\"Some error happened\"}]}"));

        Dnsapi.DeleteTxtRecordRequest request = defaultDeleteTxtRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteTxtRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest findIdRequest = mockWebServer.takeRequest();
        TxtRecord record = mapper.readValue(findIdRequest.getBody().inputStream(), TxtRecord.class);
        org.junit.Assert.assertNotNull(record);
        org.junit.Assert.assertEquals(ID, record.getId());
        org.junit.Assert.assertEquals(VALUE, record.getValue());
        org.junit.Assert.assertEquals(TXT_NAME + "." + SUBDOMAIN, record.getFqName());

        RecordedRequest deletionRequest = mockWebServer.takeRequest();
        JsonResponse deletionResponse = mapper.readValue(deletionRequest.getBody().inputStream(), JsonResponse.class);
    }

    @org.junit.Test(expected = StatusRuntimeException.class)
    public void tryDeleteTxtRecordWithErrorInFirstRequest() throws InterruptedException, IOException {
        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody("{\"errors\": [{\"rowId\":\"undef\",\"field\":\"name\",\"errMsg\":\"Some error happened\"}]}"));

        Dnsapi.DeleteTxtRecordRequest request = defaultDeleteTxtRecordRequest().build();

        Dnsapi.EmptyResponse response = stub.deleteTxtRecord(request);
        org.junit.Assert.assertNotNull(response);

        RecordedRequest findIdRequest = mockWebServer.takeRequest();
        TxtRecord record = mapper.readValue(findIdRequest.getBody().inputStream(), TxtRecord.class);
        org.junit.Assert.assertNotNull(record);
        org.junit.Assert.assertEquals(ID, record.getId());
        org.junit.Assert.assertEquals(VALUE, record.getValue());
        org.junit.Assert.assertEquals(TXT_NAME + "." + SUBDOMAIN, record.getFqName());

        RecordedRequest deletionRequest = mockWebServer.takeRequest();
        JsonResponse deletionResponse = mapper.readValue(deletionRequest.getBody().inputStream(), JsonResponse.class);
    }

    private Dnsapi.CreateTxtRecordRequest.Builder defaultCreateTxtRecordRequest() {
        return Dnsapi.CreateTxtRecordRequest.newBuilder()
                .setValue(VALUE)
                .setTxtName(TXT_NAME)
                .setSubdomain(SUBDOMAIN);
    }

    private Dnsapi.DeleteTxtRecordRequest.Builder defaultDeleteTxtRecordRequest() {
        return Dnsapi.DeleteTxtRecordRequest.newBuilder()
                .setValue(VALUE)
                .setFqName(TXT_NAME + "." + SUBDOMAIN);
    }

    private void assertRequiredFieldsSet(CreateTxtRecordRequest request) {
        org.junit.Assert.assertEquals(VALUE, request.getValue());
        org.junit.Assert.assertEquals(TXT_NAME, request.getTxtName());
        org.junit.Assert.assertEquals(SUBDOMAIN, request.getSubdomain());
    }
}
