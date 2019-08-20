package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.dto.GetARecordResponse;
import ch.ethz.vis.dnsapi.netcenter.dto.XmlCreateARecordRequestWrapper;
import ch.ethz.vis.dnsapi.netcenter.dto.XmlSuccess;
import retrofit2.Call;
import retrofit2.http.*;

// FIXME: This can actually map both A and AAAA records. Rename appropriately
public interface ARecordManager {
    @POST("nameToIP")
    Call<XmlSuccess> CreateARecord(@Body XmlCreateARecordRequestWrapper request);

    @GET("nameToIP/fqName/{name}")
    Call<GetARecordResponse> GetARecord(@Path("name") String name);

    @DELETE("nameToIP/{ipv4}")
    Call<XmlSuccess> DeleteARecord(@Path("ipv4") String ipv4);

    @DELETE("nameToIP/{ipv4}/{fqdn}")
    Call<XmlSuccess> DeleteARecord(@Path("ipv4") String ipv4, @Path("fqdn") String fqdn);
}
