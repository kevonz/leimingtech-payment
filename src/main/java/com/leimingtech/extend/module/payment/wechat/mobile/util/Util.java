package com.leimingtech.extend.module.payment.wechat.mobile.util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class Util {
	
	public static byte [] httpGet(final String url) {
		if(url == null || url.length() == 0) {
			return null;
		}
		
		HttpClient httpClient = getNewHttpClient();
		HttpGet httpGet = new HttpGet(url);
		
		try {
			HttpResponse resp = httpClient.execute(httpGet);
			if(resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static byte [] httpPost(String url , String entityStr) {
		if(url == null || url.length() == 0) {
			return null;
		}
		
		HttpClient httpClient = getNewHttpClient();
		
		HttpPost httpPost = new HttpPost(url);
		
		try {
			httpPost.setEntity(new StringEntity(entityStr,"UTF-8"));
			httpPost.setHeader("Accept" , "application/json");
			httpPost.setHeader("Content-type" , "application/json;charset=UTF-8");
			httpPost.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			
			HttpResponse resp = httpClient.execute(httpPost);
			if(resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static class SSLSocketFactoryEx extends SSLSocketFactory {
		
		SSLContext sslContext = SSLContext.getInstance("TLS");
		
		public SSLSocketFactoryEx (KeyStore truststore ) throws NoSuchAlgorithmException , KeyManagementException , KeyStoreException , UnrecoverableKeyException{
			super(truststore);
			
			TrustManager tm = new X509TrustManager() {
				
				@Override
				public X509Certificate [] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkClientTrusted(X509Certificate [] chain , String authType) throws java.security.cert.CertificateException {
				}
				
				@Override
				public void checkServerTrusted(X509Certificate [] chain , String authType) throws java.security.cert.CertificateException {
				}
			};
			
			sslContext.init(null , new TrustManager []{tm} , null);
		}
		
		@Override
		public Socket createSocket(Socket socket , String host , int port , boolean autoClose) throws IOException , UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket , host , port , autoClose);
		}
		
		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}
	
	private static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null , null);
			
			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params , HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params , HTTP.UTF_8);
			
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http" , PlainSocketFactory.getSocketFactory() , 80));
			registry.register(new Scheme("https" , sf , 443));
			
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params , registry);
			
			return new DefaultHttpClient(ccm , params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
}
