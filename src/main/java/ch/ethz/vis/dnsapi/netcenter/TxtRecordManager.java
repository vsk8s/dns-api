package ch.ethz.vis.dnsapi.netcenter;

import ch.ethz.vis.dnsapi.netcenter.types.*;
import retrofit2.Call;
import retrofit2.http.*;

public interface TxtRecordManager {
    @POST("txt")
    Call<TxtResponse> CreateTxtRecord(@Body CreateTxtRecordRequest txtRecord);

    @GET("txt/{id}")
    Call<TxtResponse> GetTxtRecord(@Path("id") String id);

    @POST("txt/search")
    Call<TxtResponse> SearchTxtRecord (@Body SearchTxtRecordRequest request);

    @DELETE("txt/{id}")
    Call<JsonResponse> DeleteTxtRecord(@Path("id") String id);
}
