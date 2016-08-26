package com.leimingtech.extend.module.payment.wechat.h5.entity.request;
/** 
 * 文本消息 
 */  
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }
}
