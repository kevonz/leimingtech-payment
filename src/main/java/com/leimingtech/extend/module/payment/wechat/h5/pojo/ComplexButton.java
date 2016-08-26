package com.leimingtech.extend.module.payment.wechat.h5.pojo;
/** 
 * 复杂按钮（父按钮） 
 */  
public class ComplexButton extends Button {  
    private Button[] sub_button;  
  
    public Button[] getSub_button() {  
        return sub_button;  
    }  
  
    public void setSub_button(Button[] sub_button) {  
        this.sub_button = sub_button;  
    } 
}
