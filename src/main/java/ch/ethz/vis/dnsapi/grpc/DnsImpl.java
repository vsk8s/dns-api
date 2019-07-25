package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.grpc.DnsGrpc.DnsImplBase;
import ch.ethz.vis.dnsapi.netcenter.ARecordManager;
import ch.ethz.vis.dnsapi.netcenter.types.CreateARecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.XmlCreateARecordRequestWrapper;
import ch.ethz.vis.dnsapi.netcenter.types.XmlSuccess;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;

public class DnsImpl extends DnsImplBase {
    private static final Logger LOGGER = LogManager.getLogger(DnsImpl.class);

    private final ARecordManager aRecordManager;
    private final String defaultIsg;

    public DnsImpl(ARecordManager aRecordManager, String defaultIsg) {
        this.aRecordManager = aRecordManager;
        this.defaultIsg = defaultIsg;
    }

    @Override
    public void createARecord(Dnsapi.CreateARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        LOGGER.debug("Got createArecord request");
        try {
            responseObserver.onNext(createARecord(
                    request.getIp(),
                    request.getIpName(),
                    request.getSubdomain(),
                    request.getOptions()));
            LOGGER.debug("Successfully created A record");
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createARecord(String ip, String ipName, String subdomain, Dnsapi.RecordOptions options) throws StatusException {
        if (ip == null || ipName == null || subdomain == null) {
            LOGGER.debug("ip, ipName and subdomain not given to createARecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("ip, ipName and subdomain required!"));
        }

        LOGGER.debug("Processing create A record request for " + ip + "(" + ipName + "." + subdomain + ")");

        CreateARecordRequest request = CreateARecordRequest.Builder.newBuilder()
                .withIp(ip)
                .withIpName(ipName)
                .withSubdomain(subdomain)
                .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                .withReverse(false) // FIXME: Find sensible defaults.
                .build();

        try {
            Response<XmlSuccess> response = aRecordManager.CreateARecord(new XmlCreateARecordRequestWrapper(request)).execute();
            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected exception: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }
}
