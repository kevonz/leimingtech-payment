/**
 * 
 */
package com.leimingtech.extend.module.payment.wechat.h5.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.leimingtech.extend.module.payment.wechat.h5.service.CoreService;
import com.leimingtech.extend.module.payment.wechat.h5.util.WeixinUtil;

/**
 *  
 * @company 雷铭智信
 * @author 张威
 * @DateTime 2015-7-17 下午1:13:47
 */
public class PinwuServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");

//		Member member  = null;
        
        PrintWriter out = null; 
        if(StringUtils.isNotEmpty(timestamp)){
        	try {
    	        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
    			request.setCharacterEncoding("UTF-8");
    	        response.setCharacterEncoding("UTF-8"); 
    	        
    	        out = response.getWriter();
    	        if (WeixinUtil.checkSignature(signature, timestamp, nonce)) {
    	        	out.print(echostr);
    	        }
    		} catch (Exception e) {
    			e.printStackTrace();
    		}finally{ 
    	        out.close();
    		}
        }
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
	        request.setCharacterEncoding("UTF-8");  
	        response.setCharacterEncoding("UTF-8");  
	        // 调用核心业务类接收消息、处理消息  
	        String respMessage  = CoreService.processRequest(request) ;  
	        // 响应消息  
	        PrintWriter out = response.getWriter();  
	        out.print(respMessage);  
	        out.close();
//        }
	}

}
