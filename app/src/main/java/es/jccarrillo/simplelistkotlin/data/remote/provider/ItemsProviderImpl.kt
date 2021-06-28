package es.jccarrillo.simplelistkotlin.data.remote.provider

import es.jccarrillo.simplelistkotlin.data.model.ItemsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

class ItemsProviderImpl private constructor(val retrofitImpl: RetrofitImpl) : ItemsProvider {


    override fun getItems(page: Int, limit: Int): ItemsResponse {
        try {
            var response = retrofitImpl.getItems(page, limit).execute()
            if (response.isSuccessful)
                response.body()?.let {
                    return it
                }

            throw Exception()

        } catch (ex: Exception) {
            return ItemsResponse()
        }
    }

    companion object Factory {
        fun build(retrofit: Retrofit): ItemsProviderImpl {
            return ItemsProviderImpl(retrofit.create(RetrofitImpl::class.java));
        }
    }

    interface RetrofitImpl {

        @GET("repos")
        fun getItems(@Query("page") page: Int, @Query("limit") limit: Int): Call<ItemsResponse>
    }
}