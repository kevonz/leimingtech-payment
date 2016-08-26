package com.leimingtech.extend.module.payment.wechat.h5.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.extend.module.payment.wechat.h5.entity.response.Article;
import com.leimingtech.extend.module.payment.wechat.h5.entity.response.NewsMessage;
import com.leimingtech.extend.module.payment.wechat.h5.entity.response.TextMessage;
import com.leimingtech.extend.module.payment.wechat.h5.util.MessageUtil;



/** 
 * 核心服务类 
 */  
public class CoreService { 
	
    /** 
     * 处理微信发来的请求 
     *  
     * @param eventType 
     * @param fromUserName
     * @param toUserName
     * @param msgType
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null; 
        try {
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";  
  
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  
  
            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
            // 回复文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
           // textMessage.setFuncFlag(0);  
  
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            	// 文本消息内容 
                String content = requestMap.get("Content");
                if("发货".equals(content)||"下单".equals(content)||"我想发货".equals(content)){
                    respContent = "您好，光速侠24小时为您服务。您有需要可随时下单。下单步骤：光速侠有四种方式可以下单，1：通过光速侠微信服务号下方左侧菜单----快递下单-----进行下单。2：通过电话：400-686-1776 由客服人员进行下单。3：下载我方APP----光速侠----进行下单，4：通过网站：www.inpingo.com 进行下单。取货时间：取件人会在10-20分钟内上门取件，请您保持手机的畅通。送达时间： 1-4小时，根据发件的距离与道路拥堵情况，送达时间可能会有所缩短或延迟，请您耐心等待。感谢您对光速侠的关注与支持，祝您生活愉快。";
                }
                
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "<a href='http://www.baidu.com'>您发送的是链接消息！</a>";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
//                    respContent = "谢谢您的关注！";

                	//判断是否已经创建菜单
//                	member = memberManager.checkMember();
//                	if(member == null){
                		//创建菜单，绑定微信号
//                    	MenuManager mm = new MenuManager();
//                    	mm.createMenu();
//                	}
                    	respContent = "亲真有眼光！此刻起，您将同600万小伙伴一起，享受我们快如光速的同城快递服务了！我们可是亦今为止，最快、最安全、最精准的同城速递呢！美食，鲜花，合同，衣服，文件瞬间即到！还不赶紧发件试试？光速侠是国内首创的轻快递商业模式，属同城众包平台。以光的速度连接客户供需两端，提高个人与oto商家运营效率，是光速侠的目标。再次感谢您的惠顾！";
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    //  取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
//                	if(StringUtils.isNotEmpty(fromUserName)&&StringUtils.isNotEmpty(toUserName)){
//    					member = memberManager.weixinMember(fromUserName,toUserName);
//    					if(member != null){
//    						member.setWechatID("");
//    						member.setWechatPlatformID("");
//    						memberManager.updateMemberMp(member);
//    					}
//    				}
                }  
                // 自定义菜单点击事件  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {  
                    //  自定义菜单权没有开放，暂不处理该类消息  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
  
                    if (eventKey.equals("14")) {  
                    // 创建图文消息  
	                  NewsMessage newsMessage = new NewsMessage();  
	                  newsMessage.setToUserName(fromUserName);  
	                  newsMessage.setFromUserName(toUserName);  
	                  newsMessage.setCreateTime(new Date().getTime());  
	                  newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
	                  newsMessage.setFuncFlag(0);  
	    
	                  List<Article> articleList = new ArrayList<Article>();
	                  
	                  Article article1 = new Article();  
	                  article1.setTitle("服务热线 : 400-6861-776");  
	                  article1.setDescription("亲爱的，如果您遇到任何问题需要我们帮助，可以随时联系我们，我们致力于为您提供最好的服务！");  
	                  article1.setPicUrl("http://www.inpingo.com/pinwu/pinwuForWeixin/img/0.jpg");  
	                  article1.setUrl("http://www.inpingo.com");  
	                  
	                  articleList.add(article1);  
	                  newsMessage.setArticleCount(articleList.size());  
	                  newsMessage.setArticles(articleList);  
	                  respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    } else if (eventKey.equals("15")) {
                    	// 创建图文消息  
  	                  NewsMessage newsMessage = new NewsMessage();  
  	                  newsMessage.setToUserName(fromUserName);  
  	                  newsMessage.setFromUserName(toUserName);  
  	                  newsMessage.setCreateTime(new Date().getTime());  
  	                  newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
  	                  newsMessage.setFuncFlag(0);  
  	    
  	                  List<Article> articleList = new ArrayList<Article>();
  	                  
  	                  Article article1 = new Article();  
  	                  article1.setTitle("问题反馈");  
  	                  article1.setDescription("针对亲常提的问题，我们进行了汇总并解答，希望能为您解疑答惑，只要回复关键词，比如： 下单，发货，投诉，咨询，到哪了? 重量，时间 等，都会有更详细的帮助回复给您。");  
  	                  article1.setPicUrl("http://www.inpingo.com/pinwu/pinwuForWeixin/img/1.jpg");  
  	                  article1.setUrl("http://www.inpingo.com");  
  	                  
  	                  articleList.add(article1);   
  	                  newsMessage.setArticleCount(articleList.size());  
  	                  newsMessage.setArticles(articleList);  
  	                  respMessage = MessageUtil.newsMessageToXml(newsMessage);
                    }
                }  
            }  
            if(!"请求处理异常，请稍候尝试！".equals(respContent)){
            	textMessage.setContent(respContent);  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        return respMessage;  
    }
    
    /** 
     * emoji表情转换(hex -> utf-16) 
     *  
     * @param hexEmoji 
     * @return 
     */  
    public static String emoji(int hexEmoji) {  
        return String.valueOf(Character.toChars(hexEmoji));  
    }

}
