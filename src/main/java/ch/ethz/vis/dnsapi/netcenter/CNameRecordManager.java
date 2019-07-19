package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.CreateCNameRecordRequest;
import ch.ethz.vis.dnsapi.netcenter.types.GetCNameRecordResponse;
import retrofit2.Call;
import retrofit2.http.*;

// FIXME: This can actually map both A and AAAA records. Rename appropriately
public interface CNameRecordManager {
    @POST("alias")
    Call<String> CreateCNameRecord(@Body CreateCNameRecordRequest createcNameRecordRequest);

    @GET("alias/{fqdn}")
    Call<GetCNameRecordResponse> GetCNameRecord(@Path("fqdn") String fqdn);

    @DELETE("alias/{fqdn}")
    Call<String> DeleteCNameRecord(@Path("fqdn") String fqdn);
}
