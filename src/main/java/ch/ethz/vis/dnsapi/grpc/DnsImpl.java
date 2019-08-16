package ch.ethz.vis.dnsapi.grpc;

import ch.ethz.vis.dnsapi.grpc.DnsGrpc.DnsImplBase;
import ch.ethz.vis.dnsapi.netcenter.NetcenterAPI;
import ch.ethz.vis.dnsapi.netcenter.types.*;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DnsImpl extends DnsImplBase {
    private static final Logger LOGGER = LogManager.getLogger(DnsImpl.class);

    private static final List<String> VIEW_INTERNAL = Collections.singletonList("intern");
    private static final List<String> VIEW_BOTH = Arrays.asList("intern", "extern");
    private static final int DEFAULT_TTL = 600;

    private final NetcenterAPI netcenterAPI;
    private final String defaultIsg;

    public DnsImpl(NetcenterAPI netcenterAPI, String defaultIsg) {
        this.netcenterAPI = netcenterAPI;
        this.defaultIsg = defaultIsg;
    }

    @Override
    public void createARecord(Dnsapi.CreateARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        LOGGER.debug("Got createARecord request");
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
        LOGGER.debug("Create A: " + ipName + "." + subdomain + " -> " + ip);

        if (ip == null || ipName == null || subdomain == null) {
            LOGGER.debug("ip, ipName and subdomain not given to createARecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("ip, ipName and subdomain required!"));
        }

        CreateARecordRequest request = CreateARecordRequest.Builder.newBuilder()
                .withIp(ip)
                .withIpName(ipName)
                .withSubdomain(subdomain)
                .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                .withViews(options.getExternallyViewable() ? VIEW_BOTH : VIEW_INTERNAL)
                .withTtl(options.getTtl() > 0 ? options.getTtl() : 600)
                .withReverse(false) // FIXME: Find sensible defaults.
                .build();

        try {
            Response<XmlSuccess> response = netcenterAPI.getaRecordManager().CreateARecord(
                    new XmlCreateARecordRequestWrapper(request)).execute();
            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void deleteARecord(Dnsapi.DeleteARecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteARecord(request.getIp(), request.getHostname()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse deleteARecord(String ip, String fqName) throws StatusException {
        LOGGER.debug("Delete A: " + fqName + " -> " + ip);
        if (ip == null || fqName == null) {
            LOGGER.debug("ip and fqName not given to deleteARecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("ip and fqName required"));
        }

        try {
            Response<XmlSuccess> response = netcenterAPI.getaRecordManager().DeleteARecord(ip, fqName).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void createCNameRecord(Dnsapi.CreateCNameRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(createCNameRecord(request.getHostname(), request.getAliasName(), request.getSubdomain(), request.getOptions()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createCNameRecord(String hostname, String aliasName, String subdomain, Dnsapi.RecordOptions options) throws StatusException {
        LOGGER.debug("Create CNAME: " + aliasName + "." + subdomain + " -> " + hostname);
        if (hostname == null || aliasName == null || subdomain == null) {
            LOGGER.debug("hostname, aliasName and subdomain not given to createCNameRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("hostname, aliasName and subdomain required"));
        }

        try {
            CreateCNameRecordRequest request = CreateCNameRecordRequest.Builder.newBuilder()
                    .withHostname(hostname)
                    .withAliasName(aliasName)
                    .withSubdomain(subdomain)
                    .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                    .withViews(options.getExternallyViewable() ? VIEW_BOTH : VIEW_INTERNAL)
                    .withTtl(options.getTtl() > 0 ? options.getTtl() : DEFAULT_TTL)
                    .build();

            Response<XmlSuccess> response = netcenterAPI.getcNameRecordManager().CreateCNameRecord(
                    new XmlCreateCNameRecordRequestWrapper(request)).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }


    @Override
    public void deleteCNameRecord(Dnsapi.DeleteCNameRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteCNameRecord(request.getAlias()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse deleteCNameRecord(String alias) throws StatusException {
        LOGGER.debug("Delete CNAME: " + alias);
        if (alias == null) {
            LOGGER.debug("alias not given to deleteCNameRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("alias required"));
        }

        try {
            Response<XmlSuccess> response = netcenterAPI.getcNameRecordManager().DeleteCNameRecord(alias).execute();

            if (!response.isSuccessful()) {
                String error = response.errorBody().string();
                LOGGER.debug("Something went wrong: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Something went wrong: " + error));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void createTxtRecord(Dnsapi.CreateTxtRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(createTxtRecord(request.getTxtName(), request.getSubdomain(), request.getValue(), request.getOptions()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.EmptyResponse createTxtRecord(String txtName, String subdomain, String value, Dnsapi.RecordOptions options) throws StatusException {
        LOGGER.debug("Create TXT: " + txtName + "." + subdomain + " -> " + value);
        if (txtName == null || subdomain == null || value == null) {
            LOGGER.debug("txtName, subdomain and value not given to createTxtRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("txtName, subdomain and value are required"));
        }

        try {
            CreateTxtRecordRequest request = CreateTxtRecordRequest.Builder.newBuilder()
                    .withTxtName(txtName)
                    .withSubdomain(subdomain)
                    .withValue(value)
                    .withIsgGroup(options.getIsgGroup().isEmpty() ? defaultIsg : options.getIsgGroup())
                    .withViews(options.getExternallyViewable() ? VIEW_BOTH : VIEW_INTERNAL)
                    .withTtl(options.getTtl() > 0 ? options.getTtl() : DEFAULT_TTL)
                    .build();

            Response<TxtResponse> response = netcenterAPI.getTxtRecordManager().CreateTxtRecord(request).execute();

            checkTxtJsonResponse(response);
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    @Override
    public void searchTxtRecord(Dnsapi.SearchTxtRecordRequest request, StreamObserver<Dnsapi.TxtResponse> responseObserver) {
        try {
            responseObserver.onNext(searchTxtRecord(request.getFqName()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        }
    }

    private Dnsapi.TxtResponse searchTxtRecord(String fqName) throws StatusException {
        LOGGER.debug("Search TXT: " + fqName);
        if (fqName == null || fqName.isBlank()) {
            LOGGER.debug("fqName is required");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("fqName is required"));
        }

        try {
            SearchTxtRecordRequest request = SearchTxtRecordRequest.Builder.newBuilder()
                    .withFqName(fqName).build();

            Response<TxtResponse> response = netcenterAPI.getTxtRecordManager().SearchTxtRecord(request).execute();

            TxtRecord record = checkTxtJsonResponse(response);
            if (record.getId() == null || record.getFqName() == null || record.getIsgGroup() == null || record.getViews() == null) {
                LOGGER.debug("Got empty record");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            }

            return Dnsapi.TxtResponse.newBuilder()
                    .setFqName(record.getFqName())
                    .setValue(record.getValue())
                    .setOptions(Dnsapi.RecordOptions.newBuilder()
                            .setIsgGroup(record.getIsgGroup())
                            .setTtl(record.getTtl())
                            .setExternallyViewable(record.getViews().contains("extern"))
                            .build())
                    .build();
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("error relaying request to API"));
        }
    }

    @Override
    public void deleteTxtRecord(Dnsapi.DeleteTxtRecordRequest request, StreamObserver<Dnsapi.EmptyResponse> responseObserver) {
        try {
            responseObserver.onNext(deleteTxtRecord(request.getFqName(), request.getValue()));
            responseObserver.onCompleted();
        } catch (StatusException e) {
            responseObserver.onError(e);
        } catch (Exception e) {
            LOGGER.error("Unexpected Exception: " + e);
            responseObserver.onError(new StatusException(Status.INTERNAL.withDescription("Internal error")));
        }
    }

    private Dnsapi.EmptyResponse deleteTxtRecord(String fqName, String value) throws StatusException {
        LOGGER.debug("Delete TXT: " + fqName + " -> " + value);
        if (fqName == null || value == null) {
            LOGGER.debug("fqName and value not given to deleteTxtRecord");
            throw new StatusException(Status.INVALID_ARGUMENT.withDescription("fqName and value are required"));
        }

        SearchTxtRecordRequest request = SearchTxtRecordRequest.Builder.newBuilder()
                .withFqName(fqName)
                .withValue(value)
                .build();

        try {
            Response<TxtResponse> searchTxtRecordResponse = netcenterAPI.getTxtRecordManager().SearchTxtRecord(request).execute();
            TxtRecord record = checkTxtJsonResponse(searchTxtRecordResponse);

            LOGGER.debug("Found TXT record: " + record.getFqName() + " -> " + record.getValue());
            if (record.getId() == null || record.getFqName() == null || record.getValue() == null) {
                LOGGER.warn("TXT record was empty");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            } else if (!record.getFqName().equals(fqName) || !record.getValue().equals(value)) {
                LOGGER.warn("TXT record does not match given parameters");
                throw new StatusException(Status.NOT_FOUND.withDescription("TXT record not found"));
            }

            LOGGER.debug("Got TXT id: " + record.getId());
            Response<JsonResponse> deleteTxtResponse = netcenterAPI.getTxtRecordManager().DeleteTxtRecord(record.getId()).execute();

            if (!deleteTxtResponse.isSuccessful()) {
                String error = deleteTxtResponse.errorBody().string();
                LOGGER.debug("Unsuccessful request: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Error in request: " + error));
            } else if (deleteTxtResponse.body().getErrors() != null) {
                String errorMsg = deleteTxtResponse.body().getErrors().stream().map(JsonError::getErrorMsg).collect(Collectors.joining(", "));
                LOGGER.debug("Got errors from API: " + errorMsg);
                throw new StatusException(Status.INTERNAL.withDescription("Got errors from API:" + errorMsg));
            }
        } catch (IOException e) {
            LOGGER.error("Unexpected IOException: " + e);
            throw new StatusException(Status.INTERNAL.withDescription("Error relaying request to API"));
        }

        return Dnsapi.EmptyResponse.getDefaultInstance();
    }

    private TxtRecord checkTxtJsonResponse(Response<TxtResponse> response) throws IOException, StatusException {
        if (!response.isSuccessful()) {
            if (response.errorBody() != null) {
                String error = response.errorBody().string();
                LOGGER.error("Unsuccessful request: " + error);
                throw new StatusException(Status.INTERNAL.withDescription("Error in request: " + error));
            } else {
                LOGGER.error("Unsuccessful request");
                throw new StatusException(Status.INTERNAL.withDescription("Internal error"));
            }
        } else if (response.body() == null) {
            LOGGER.error("Got empty body from TXT api");
            throw new StatusException(Status.INTERNAL.withDescription("Got invalid response from API"));
        } else if (response.body().getErrors() != null) {
            String errorMsg = response.body().getErrors().stream().map(JsonError::getErrorMsg).collect(Collectors.joining(", "));
            LOGGER.debug("Got errors from API: " + errorMsg);
            throw new StatusException(Status.INTERNAL.withDescription("Got errors from API: " + errorMsg));
        } else if (response.body().getTxtRecord() == null) {
            LOGGER.error("Got null record from API");
            throw new StatusException(Status.INTERNAL.withDescription("Got invalid response from API"));
        }
        return response.body().getTxtRecord();
    }
}
