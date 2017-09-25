package com.a8tiyu.caizhan_kotlin.util

import org.apache.commons.lang3.StringUtils
import org.apache.http.NameValuePair
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

object HttpUtils {

    private var httpclient: CloseableHttpClient? = null
    val IPHONE5_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X;" +
            " en-us) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53"
    val ENCONDING = "utf-8"

    init {
        val requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(800)
                .setSocketTimeout(2000)
                .setConnectTimeout(800)
                .build()
        httpclient = HttpClients.custom()
                .setMaxConnPerRoute(50)
                .setMaxConnTotal(500)
                .setDefaultRequestConfig(requestConfig).build()
    }


    /**
     * Get方法请求数据
     * @return Pair<状态码, 响应内容>
     */
    fun get(url: String, params: Map<String, String>?,
            headers: MutableMap<String, String>? = null): Pair<Int, String> {


        if (StringUtils.isEmpty(url)) {
            throw IllegalArgumentException("url required!")
        }
        val nvps = ArrayList<NameValuePair>()
        if (params != null && !params.isEmpty()) {
            for ((key, value) in params) {
                nvps.add(BasicNameValuePair(key, value))
            }
        }
        val builder = URIBuilder(url)
        if (!nvps.isEmpty()) {
            builder.setParameters(nvps)
        }
        // httpPost.addHeader("Content-type", "text/json; charset=" + encoding);
        val httpGet = HttpGet(builder.build())
        if (headers != null) {
            for ((key, value) in headers) {
                httpGet.setHeader(key, value)
            }
        }


        return doExecute(httpGet)


    }


    fun post(url: String,
             params: Map<String, String>?, encoding: String?): Pair<Int, String> {
        val httpPost = HttpPost(url)
        val nvps = ArrayList<NameValuePair>()
        val encode = if (encoding == "" || encoding == null) ENCONDING else encoding

        if (params != null && !params.isEmpty()) {
            for ((key, value) in params) {
                nvps.add(BasicNameValuePair(key, value))
            }
        }

        httpPost.entity = UrlEncodedFormEntity(nvps, encode)
        // httpPost.addHeader("Content-type", "text/json; charset=" + encoding);
        return doExecute(httpPost)

    }

    private fun doExecute(httpEntit: HttpUriRequest): Pair<Int, String> {
        httpclient!!.execute(httpEntit).use { response ->
            val statusCode = response.statusLine.statusCode

            val entity = response.entity

            val baos = ByteArrayOutputStream()

            entity.writeTo(baos)

            val responseBody = String(baos.toByteArray())

            EntityUtils.consume(entity)

            return Pair(statusCode, responseBody)
        }
    }


}
