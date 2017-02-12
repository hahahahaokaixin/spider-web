package com.guo.base.http;

import com.guo.base.util.Constant;
import com.guo.base.util.ProxyIp;
import com.guo.base.util.ProxyPool;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by guoyu on 2016/12/1.
 */
public class HttpsBase {
    private static Logger logger = Logger.getLogger(HttpsBase.class);
    private static final HttpsBase instance = new HttpsBase();
    private static final java.util.Random RANDOM = new java.util.Random();

    private HttpsBase() {
    }

    public static HttpsBase getInstance() {
        return instance;
    }

    private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

    static {
        Integer MAX_TIME_OUT = 5000;
        Integer MAX_CONN = 5000;
        connectionManager.closeIdleConnections(MAX_TIME_OUT);
        connectionManager.getParams().setParameter("http.connection-manager.max-total", MAX_CONN);
    }

    public static Response get(String url, String charset) throws Exception {
        return get(url, charset, null);
    }

    /**
     * 默认请求
     *
     * @return
     * @throws Exception
     */
    public synchronized static Response get(String url, String charset, Map<String, String> headers) throws Exception {
        ProtocolSocketFactory fcty = new SecureProtocolSocketFactory();
        Protocol.registerProtocol("https", new Protocol("https", fcty, 443));

        HttpClient client = getClient(charset);
        CustomGetMethod getMethod = new CustomGetMethod(url);// 创建一个get方法，类似在浏览器地址栏中输入一个地址
        if (headers != null) {
            Set<String> keys = headers.keySet();
            for (String k : keys) {
                getMethod.setRequestHeader(k, headers.get(k));
            }
        }
        getMethod.setRequestHeader("accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
        getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
        getMethod.setRequestHeader("Upgrade-Insecure-Requests", "1");

        getMethod.setRequestHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0." + (RANDOM.nextInt(2920) + 1) + ".28 Safari/537.36");
        /*
        getMethod.setRequestHeader("User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0.1; SM-G935F Build/MMB29K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/51.0."+(RANDOM.nextInt(2850) + 1)+".81 Mobile " +
                        "Safari/537.36");//MicroMessenger/6.3.22.821 NetType/WIFI Language/zh_CN*/

        int statusCode = client.executeMethod(getMethod);

        Header[] _map = getMethod.getResponseHeaders();
        Map<String, String> map = new HashMap<String, String>();
        for (Header h : _map) {
            map.put(h.getName(), h.getValue());
        }
        if (statusCode == 200) {
            String body = getMethod.getResponseBodyAsString();
            getMethod.releaseConnection();
            Response r = new Response();
            r.setResult(body);
            r.setHeader(map);
            r.setStatus(statusCode);
            getMethod.abort();
            getMethod.releaseConnection();// 释放，记得收拳哦
            return r;
        }
        if (statusCode == 500) {
            String body = getMethod.getResponseBodyAsString();
            getMethod.releaseConnection();
            if (StringUtils.isNotEmpty(body)) {
                Response r = new Response();
                r.setResult(body);
                r.setHeader(map);
                r.setStatus(statusCode);
                getMethod.abort();
                getMethod.releaseConnection();
                return r;
            }
            getMethod.abort();
            getMethod.releaseConnection();
            throw new java.net.ConnectException("请求错误,status code is[" + statusCode + "]");
        }
        String body = getMethod.getResponseBodyAsString();
        Response r = new Response();
        r.setResult(body);
        r.setHeader(map);
        r.setStatus(statusCode);
        getMethod.abort();
        getMethod.releaseConnection();
        return r;
    }

    public synchronized static Response post(String url, String charset, Map<String, String> header,
                                             Map<String, String> data) throws Exception {
        CustomPostMethod postMethod = new CustomPostMethod(url);
        try {
            ProtocolSocketFactory fcty = new SecureProtocolSocketFactory();
            Protocol.registerProtocol("https", new Protocol("https", fcty, 443));

            HttpClient client = getClient(charset);
            Set<String> headers = header.keySet();
            for (String key : headers) {
                postMethod.setRequestHeader(key, header.get(key));
            }
            Set<String> keys = data.keySet();
            NameValuePair[] ps = new NameValuePair[keys.size()];
            int index = 0;
            for (String k : keys) {
                ps[index++] = new NameValuePair(k, data.get(k));
            }

            postMethod.setRequestBody(ps);

            int statusCode = client.executeMethod(postMethod);

            Header[] _map = postMethod.getResponseHeaders();
            Map<String, String> map = new HashMap<String, String>();
            for (Header h : _map) {
                map.put(h.getName(), h.getValue());
            }
            if (statusCode == 200) {
                String result = postMethod.getResponseBodyAsString();

                Response r = new Response();
                r.setResult(result);
                r.setHeader(map);
                r.setStatus(statusCode);
                postMethod.abort();
                return r;
            }
            postMethod.abort();
            throw new java.net.ConnectException("请求错误,status code is[" + statusCode + "]");
        } catch (Exception e) {
            postMethod.abort();
            throw e;
        } finally {

            postMethod.releaseConnection();
        }

    }

    private static HttpClient getClient(String charset) {
        HttpClient client = new HttpClient(connectionManager);
        if (Constant.proxy) {
            // ProxyIp ip = new ProxyIp("120.24.184.166", 16816,
            // "20160323174944");
            ProxyIp ip = ProxyPool.getRandomProxy();
            client.getHostConfiguration().setProxy(ip.getIp(), ip.getPort());
            logger.info("########代理ip:" + ip.getIp() + ",端口:" + ip.getPort() + "注册时间：" + ip.getTime() + "########");
        }
        HttpConnectionManagerParams params = client.getHttpConnectionManager().getParams();
        params.setConnectionTimeout(30000);
        params.setSoTimeout(30000);
        params.setDefaultMaxConnectionsPerHost(500);
        params.setMaxTotalConnections(1500);
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        HttpClientParams hcp = client.getParams();
        hcp.setParameter("http.connection.timeout", 20 * 1000L);
        hcp.setParameter("http.connection-manager.timeout", 20 * 1000L);
        hcp.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        hcp.setAuthenticationPreemptive(true);
        return client;
    }

    public static void main(String args[]) {
        String url = "https://www.zhanqi.tv/api/static/live.hots/30-2.json";
        String html;
        try {
            Map<String, String> header = new HashMap<String, String>();
            header.put("Host", "www.zhanqi.tv");

            html = HttpsBase.get(url, "utf-8", header).getResult();

            System.out.println(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
