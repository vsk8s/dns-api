package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.TxtResponse;
import ch.ethz.vis.dnsapi.netcenter.types.TxtRecord;
import retrofit2.Call;
import retrofit2.http.*;

public interface TxtRecordManager {
    @POST("txt")
    Call<TxtResponse> CreateTxtRecord(@Body TxtRecord txtRecord);

    @GET("txt/{id}")
    Call<TxtResponse> GetTxtRecord(@Path("id") String id);

    @POST("txt/search")
    Call<TxtResponse> SearchTxtRecord (@Body TxtRecord txtRecord);

    @DELETE("txt/{id}")
    Call<String> DeleteTxtRecord(@Path("id") String id);
}
