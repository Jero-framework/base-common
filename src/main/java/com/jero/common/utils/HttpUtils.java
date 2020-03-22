package com.jero.common.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Description Http请求工具类
 * 使用Unirest请求的数据一般是 JsonNode，若返回类型报错，一般为String，最后得到的为.asString();
 * 1.header用了设置header的各种参数，包括token
 * 2.routeParam用于设置路径中带有参数的如{cid}之类的
 * 3.paramString用于设置get命令中 &的键值对
 * 4.field用于设置post的参数，也可以直接用一个map,.fields(prams)    //prams是一个map，put了很多参数进去，和直接多个fields一样的效果
 * 5.返回的结果打印一般用，response.getBody( ).getObject( )    得到的JSON对象，之后的JSON解析出需要的内容都是以此为基础分层剥离。
 * 6.返回的状态用response.getStatus(),即返回的状态码，注意有个别成功码并不一样，如前台是200，后台是302
 * @Author lixuetao
 * @Date 2020/3/22
 **/
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    private static final String ACCEPT = "Accept";
    private static final String APPLICATION_JASON = "application/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    private static final String HTTP_REQUEST_FAILED_S = "http request failed %s";
    private static final String JSON_RESPONSE_IS_NULL = "jsonResponse is null";

    private HttpUtils() {
        throw new IllegalStateException("HttpUtils Utility class");
    }

    public static String post(String baseUrl, Map<String, Object> paramMap) {
        return post(baseUrl, (Map)null, (String)null, (String)null, paramMap, (String)null, false);
    }

    public static String post(String baseUrl, String json) {
        return post(baseUrl, (Map)null, (String)null, (String)null, (Map)null, json, false);
    }

    public static String post(String baseUrl, Map<String, String> headers, String json, boolean contentType) {
        return post(baseUrl, headers, (String)null, (String)null, (Map)null, json, contentType);
    }

    public static String post(String baseUrl, Map<String, String> headerMap, String routekey, String routevalue, Map<String, Object> paramMap, String body, boolean contentType) {
        HttpResponse<String> jsonResponse = null;
        HttpRequestWithBody httpRequestWithBody = null;

        try {
            if (contentType && !MapUtils.isEmpty(headerMap)) {
                httpRequestWithBody = Unirest.post(baseUrl).headers(headerMap);
            } else {
                httpRequestWithBody = Unirest.post(baseUrl).header(ACCEPT, APPLICATION_JASON).header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
            }

            if (StringUtils.isNotEmpty(routekey)) {
                httpRequestWithBody.routeParam(routekey, routevalue);
            }

            if (!MapUtils.isEmpty(paramMap)) {
                httpRequestWithBody.queryString(paramMap);
            }

            if (StringUtils.isNotEmpty(body)) {
                httpRequestWithBody.body(body);
            }

            jsonResponse = httpRequestWithBody.asString();
        } catch (UnirestException exception) {
            log.error(String.format(HTTP_REQUEST_FAILED_S, baseUrl), exception);
        }

        if (jsonResponse != null) {
            if (isOKStatusCode(jsonResponse.getStatus())) {
                log.info((String)jsonResponse.getBody());
                return (String)jsonResponse.getBody();
            } else {
                log.error("http request failed {}，{}，{}", new Object[]{baseUrl, jsonResponse.getStatus(), jsonResponse.getBody()});
                return "";
            }
        } else {
            log.error(JSON_RESPONSE_IS_NULL);
            return "";
        }
    }

    public static String post(String baseUrl, String fileName, InputStream uploadFile, Map<String, Object> paramMap) {
        HttpResponse jsonResponse = null;

        try {
            jsonResponse = Unirest.post(baseUrl).fields(paramMap).field(fileName, uploadFile, fileName).asString();
        } catch (UnirestException var6) {
            log.error(String.format(HTTP_REQUEST_FAILED_S, baseUrl), var6);
        }

        return checkJsonResponse(jsonResponse, baseUrl);
    }

    public static String post(String baseUrl, Map<String, Object> paramMap, String routeKey, String routeValue) {
        return post(baseUrl, (String)null, (InputStream)null, (Map)paramMap, (String)routeKey, routeValue);
    }

    public static String post(String baseUrl, String fileName, InputStream uploadFile, Map<String, Object> paramMap, String routeKey, String routeValue) {
        if (!StringUtils.isEmpty(routeKey) || !StringUtils.isEmpty(routeValue)) {
            throw new IllegalArgumentException(String.valueOf("routeKey|routeValue can't be empty."));
        }

        HttpResponse jsonResponse = null;

        try {
            if (!StringUtils.isEmpty(fileName) && uploadFile != null) {
                jsonResponse = Unirest.post(baseUrl).routeParam(routeKey, URLEncoder.encode(routeValue, "UTF-8")).fields(paramMap).field(fileName, uploadFile, fileName).asString();
            } else {
                jsonResponse = Unirest.post(baseUrl).routeParam(routeKey, URLEncoder.encode(routeValue, "UTF-8")).fields(paramMap).asString();
            }
        } catch (UnirestException | UnsupportedEncodingException var8) {
            log.error(String.format(HTTP_REQUEST_FAILED_S, baseUrl), var8);
        }

        return checkJsonResponse(jsonResponse, baseUrl);
    }

    public static String put(String baseUrl, Map<String, Object> paramMap) {
        return put(baseUrl, (Map)null, (String)null, (String)null, paramMap, (String)null);
    }

    public static String put(String baseUrl, String json) {
        return put(baseUrl, (Map)null, (String)null, (String)null, (Map)null, json);
    }

    public static String put(String baseUrl, Map<String, String> headerMap, String routekey, String routevalue, Map<String, Object> paramMap, String body) {
        String response = null;
        HttpRequestWithBody httpRequestWithBody = null;
        httpRequestWithBody = Unirest.put(baseUrl).header(ACCEPT, APPLICATION_JASON).header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);

        try {
            if (!MapUtils.isEmpty(headerMap)) {
                httpRequestWithBody = Unirest.post(baseUrl).headers(headerMap);
            }

            if (StringUtils.isNotEmpty(routekey)) {
                httpRequestWithBody.routeParam(routekey, routevalue);
            }

            if (!MapUtils.isEmpty(paramMap)) {
                httpRequestWithBody.queryString(paramMap);
            }

            if (StringUtils.isNotEmpty(body)) {
                httpRequestWithBody.body(body);
            }

            HttpResponse<String> httpResponse = httpRequestWithBody.asString();
            if (isOKStatusCode(httpResponse.getStatus())) {
                return (String)httpResponse.getBody();
            }
        } catch (UnirestException var9) {
            log.error(String.format("http request failed %s %s", baseUrl, response));
        }

        return (String)response;
    }

    public static String get(String baseUrl, Map<String, Object> paramMap) {
        return get(baseUrl, paramMap, (String)null, (String)null);
    }

    public static String get(String baseUrl, Map<String, Object> paramMap, String routeKey, String routeValue) {
        HttpResponse jsonResponse = null;

        try {
            if (!StringUtils.isEmpty(routeKey) && !StringUtils.isEmpty(routeValue)) {
                jsonResponse = Unirest.get(baseUrl).header(ACCEPT, APPLICATION_JASON).header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8).routeParam(routeKey, URLEncoder.encode(routeValue, "UTF-8")).queryString(paramMap).asString();
            } else {
                jsonResponse = Unirest.get(baseUrl).header(ACCEPT, APPLICATION_JASON).header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8).queryString(paramMap).asString();
            }
        } catch (UnsupportedEncodingException | UnirestException var6) {
            log.error(String.format(HTTP_REQUEST_FAILED_S, baseUrl), var6);
        }

        return checkJsonResponse(jsonResponse, baseUrl);
    }

    public static String delete(String baseUrl, Map<String, Object> paramMap) {
        return delete(baseUrl, paramMap, (String)null, (String)null, (String)null);
    }

    public static String delete(String baseUrl, String body) {
        return delete(baseUrl, (Map)null, body, (String)null, (String)null);
    }

    public static String delete(String baseUrl, Map<String, Object> paramMap, String body, String routeKey, String routeValue) {
        HttpResponse<String> jsonResponse = null;
        HttpRequestWithBody httpRequestWithBody = Unirest.delete(baseUrl).header(ACCEPT, APPLICATION_JASON).header(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);

        try {
            if (!StringUtils.isEmpty(routeKey) && !StringUtils.isEmpty(routeValue)) {
                httpRequestWithBody.routeParam(routeKey, URLEncoder.encode(routeValue, "UTF-8"));
            }

            if (!MapUtils.isEmpty(paramMap)) {
                httpRequestWithBody.queryString(paramMap);
            }

            if (StringUtils.isNotEmpty(body)) {
                httpRequestWithBody.body(body);
            }

            jsonResponse = httpRequestWithBody.asString();
        } catch (UnirestException | UnsupportedEncodingException var8) {
            log.error(String.format(HTTP_REQUEST_FAILED_S, baseUrl), var8);
        }

        return checkJsonResponse(jsonResponse, baseUrl);
    }

    private static boolean isOKStatusCode(int statuscode) {
        switch(statuscode) {
            case 200:
                return true;
            case 201:
                return true;
            case 202:
                return true;
            case 302:
                return true;
            default:
                return false;
        }
    }

    private static String checkJsonResponse(HttpResponse<String> jsonResponse, String baseUrl) {
        if (jsonResponse != null) {
            if (isOKStatusCode(jsonResponse.getStatus())) {
                return (String)jsonResponse.getBody();
            } else {
                log.error("http post request failed {}，{}", baseUrl, jsonResponse.getStatus());
                return "";
            }
        } else {
            log.error(JSON_RESPONSE_IS_NULL);
            return "";
        }
    }

}
