package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.CreateARecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.GetARecordResponse;
import retrofit2.Call;
import retrofit2.http.*;

// FIXME: This can actually map both A and AAAA records. Rename appropriately
public interface ARecordManager {
    @POST("nameToIP")
    Call<String> CreateARecord(@Body CreateARecordRequest createARecordRequest);

    @GET("nameToIP/fqName/{name}")
    Call<GetARecordResponse> GetARecord(@Path("name") String name);

    @DELETE("nameToIP/{ipv4}")
    Call<String> DeleteARecord(@Path("ipv4") String ipv4);

    @DELETE("nameToIP/{ipv4}/{fqdn}")
    Call<String> DeleteARecord(@Path("ipv4") String ipv4, @Path("fqdn") String fqdn);
}
