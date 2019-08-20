package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.dto.XmlCreateCNameRecordRequestWrapper;
import ch.ethz.vis.dnsapi.netcenter.dto.GetCNameRecordResponse;
import ch.ethz.vis.dnsapi.netcenter.dto.XmlSuccess;
import retrofit2.Call;
import retrofit2.http.*;

public interface CNameRecordManager {
    @POST("alias")
    Call<XmlSuccess> CreateCNameRecord(@Body XmlCreateCNameRecordRequestWrapper request);

    @GET("alias/{fqdn}")
    Call<GetCNameRecordResponse> GetCNameRecord(@Path("fqdn") String fqdn);

    @DELETE("alias/{fqdn}")
    Call<XmlSuccess> DeleteCNameRecord(@Path("fqdn") String fqdn);
}
