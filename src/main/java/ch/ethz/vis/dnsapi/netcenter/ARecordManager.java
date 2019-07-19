package ch.ethz.vis.dnsapi.netcenter;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.net.Inet4Address;

public class ARecordManager {

    @POST("nameToIP")
    public Call<SuccessResponse> CreateARecord(String src, Inet4Address dest) {
        return null;
    }

    //@GET("nameToIP/fqName/{name}")
    //public Call<ARecord> GetARecord(@Path("name") String src);

    public void DeleteARecord() {

    }
}
