package com.leimingtech.extend.module.payment.unionpay.pc.gwj.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.leimingtech.extend.module.payment.unionpay.pc.gwj.conf.GwjConfig;


@Slf4j
public class HttpUtil {

	//private static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	public static String encoding;
	
	private static String GWJ_CID = "gwj-cid";
	private static String GWJ_CODE = "gwj-code";

    private static final HttpConnectionManager connectionManager;
    
    private static final HttpClient client;

    static {

    	HttpConnectionManagerParams params = loadHttpConfFromFile();
    	
    	connectionManager = new MultiThreadedHttpConnectionManager();

        connectionManager.setParams(params);

        client = new HttpClient(connectionManager);
    }
    
    private static HttpConnectionManagerParams loadHttpConfFromFile(){
        Properties p  = new Properties();
        try {
			p.load(HttpUtil.class.getResourceAsStream(HttpUtil.class.getSimpleName().toLowerCase() + ".properties"));
		} catch (IOException e) {
		}
		
		encoding = p.getProperty("http.content.encoding");
		
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
        params.setConnectionTimeout(Integer.parseInt(p.getProperty("http.connection.timeout")));
        params.setSoTimeout(Integer.parseInt(p.getProperty("http.so.timeout")));
        params.setStaleCheckingEnabled(Boolean.parseBoolean(p.getProperty("http.stale.check.enabled")));
        params.setTcpNoDelay(Boolean.parseBoolean(p.getProperty("http.tcp.no.delay")));
        params.setDefaultMaxConnectionsPerHost(Integer.parseInt(p.getProperty("http.default.max.connections.per.host")));
        params.setMaxTotalConnections(Integer.parseInt(p.getProperty("http.max.total.connections")));
        params.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		return params;
    }
    
	public static String post(String url, String encoding, String content) {
		try {
			byte[] resp = post(url, content.getBytes(encoding));
			if (null == resp){
				return null;
			}
			return new String(resp, encoding);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
    
    
	public static String post(String url, String content) {
    	return post(url, encoding, content);
    }


    public static byte[] post(String url, byte[] content) {
		try {
			byte[] ret = post(url, new ByteArrayRequestEntity(content));
			return ret;
		} catch (Exception e) {
			log.warn("Catch exception while post to url [" + url + "]", e);
			return null;
		}
    }

    public static byte[] post(String url, RequestEntity requestEntity) throws Exception {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("Connection", "Keep-Alive");
        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        method.setRequestEntity(requestEntity);
        method.addRequestHeader("Content-Type","application/x-www-form-urlencoded");
        method.setRequestHeader(GWJ_CID, GwjConfig.GWJ_CID); // 添加client id
        method.setRequestHeader(GWJ_CODE, GwjConfig.GWJ_CODE); // 添加授权码
        
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
            	log.warn("Http response status is not OK [" + statusCode + "]");
                return null;
            }
            return method.getResponseBody();

        } finally {
            method.releaseConnection();
        }
    }
    
    public static String getAsString(String url){
    	try {
			return new String(get(url), encoding);
		} catch (Exception e) {
			log.warn("Catch exception while get method to url[" + url + "]", e);
			// ignore
		}
		
		return null;
    }
    
    public static byte[] get(String url) {

    	client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);

        // 创建GET方法的实例
        GetMethod method = new GetMethod(url);
        method.setRequestHeader(GWJ_CID, GwjConfig.GWJ_CID); // 添加client id
        method.setRequestHeader(GWJ_CODE, GwjConfig.GWJ_CODE); // 添加授权码

        try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
            	log.warn("Http response status is not OK [" + statusCode + "]");
                return null;
            }

            // 处理内容
            return method.getResponseBody();

        } catch (Exception e) {
        	log.warn("Catch exception while get to url [" + url + "]", e);
        	return null;
        } finally {
            method.releaseConnection();
        }
    }
}
