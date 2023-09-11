package com.nikit.rest.calladapter

import com.nikit.core.network.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiCall<N : Any>(
    private val delegate: Call<N>,
    private val responseTransformer: ApiResponseTransformer
//    Можно интегрировать обработку ошибок для каждого ответа
//    private val errorConverter: Converter<ResponseBody, E>
) : Call<ApiResponse<N>> {

    companion object {
        private const val TOTAL_RETRIES = 3
        private const val DEFAULT_DELAY = 500L
    }

    private var retryCount = 0
    private val scope = CoroutineScope(Dispatchers.IO)
    private var retryDelay = DEFAULT_DELAY

    override fun enqueue(callback: Callback<ApiResponse<N>>) {
        return delegate.enqueue(object : Callback<N> {
            override fun onResponse(call: Call<N>, response: Response<N>) {

                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(responseTransformer.transform(response))
                    )
                } else {
                    //Можно интегрировать обработку ошибок для ответа
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(responseTransformer.transform(response))
                    )
                }
            }

            override fun onFailure(call: Call<N>, t: Throwable) {
                if (retryCount++ < TOTAL_RETRIES) {
                    val localCallback = this
                    scope.launch {
                        delay(retryDelay)
                        retryDelay += DEFAULT_DELAY
                        call.clone().enqueue(localCallback)
                    }
                } else {
                    scope.cancel()
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(responseTransformer.transform(t))
                    )
                }
            }
        })
    }

    override fun timeout() = delegate.timeout()

    override fun isExecuted() = delegate.isExecuted

    override fun clone() = ApiCall(delegate.clone(), responseTransformer)

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ApiResponse<N>> {
        throw UnsupportedOperationException("ApiCall doesn't support execute")
    }

    override fun request() = delegate.request()

}
