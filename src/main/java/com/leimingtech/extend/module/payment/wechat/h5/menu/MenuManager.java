package com.leimingtech.extend.module.payment.wechat.h5.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leimingtech.extend.module.payment.wechat.h5.config.WachatContent;
import com.leimingtech.extend.module.payment.wechat.h5.pojo.AccessToken;
import com.leimingtech.extend.module.payment.wechat.h5.pojo.Button;
import com.leimingtech.extend.module.payment.wechat.h5.pojo.CommonButton;
import com.leimingtech.extend.module.payment.wechat.h5.pojo.ComplexButton;
import com.leimingtech.extend.module.payment.wechat.h5.pojo.Menu;
import com.leimingtech.extend.module.payment.wechat.h5.util.WeixinUtil;


/** 
 * 菜单管理器类
 * 
 *  @author gyh
 */  
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        String appId =WachatContent.appid;  
        // 第三方用户唯一凭证密钥  
        String appSecret =WachatContent.appsecret;  
  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken()); 
//            String jg = WeixinUtil.deleteMenu(at.getToken());
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result); 
            // 判断菜单删除结果  
//            if ("OK".equals(jg))  
//                log.info("菜单删除成功！");  
//            else  
//                log.info("菜单删除失败！");
        }  
        System.exit(0);
    }
    
    
    public void onstart(){
    	  // 第三方用户唯一凭证  
        String appId =WachatContent.appid;  
        // 第三方用户唯一凭证密钥  
        String appSecret =WachatContent.appsecret;  
  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(getMenu(), at.getToken()); 
//            String jg = WeixinUtil.deleteMenu(at.getToken());
  
            // 判断菜单创建结果  
            if (0 == result)  
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result); 
            // 判断菜单删除结果  
//            if ("OK".equals(jg))  
//                log.info("菜单删除成功！");  
//            else  
//                log.info("菜单删除失败！");
        }  
        System.exit(0);
    }
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    private static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("微信微店");  
        btn11.setType("view");  
        btn11.setKey("11");  
        btn11.setUrl("b2b2c.leimingtech.com/leimingtech-front/weChat/load.html");
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("品牌街");  
        btn12.setType("view");  
        btn12.setKey("12");  
        btn12.setUrl("http://b2b2c.leimingtech.com/leimingtech-front/m/brand/brand");
  
        CommonButton btn13 = new CommonButton();  
        btn13.setName("商城类目");  
        btn13.setType("view");  
        btn13.setKey("13"); 
        btn13.setUrl("http://b2b2c.leimingtech.com/leimingtech-front/m/category/category");   
  
        CommonButton btn14 = new CommonButton();  
        btn14.setName("个人中心");  
        btn14.setType("click");  
        btn14.setKey("14"); 
        btn13.setUrl("http://b2b2c.leimingtech.com/leimingtech-front/m/buyer/center"); 
  
        CommonButton btn15 = new CommonButton();  
        btn15.setName("搜索商品");  
        btn15.setType("click");  
        btn15.setKey("15"); 
        btn13.setUrl("http://b2b2c.leimingtech.com/leimingtech-front/m/category/category"); 
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("促销活动");  
        btn21.setType("view");  
        btn21.setKey("21");  
        btn21.setUrl("http://mp.weixin.qq.com/s?__biz=MzI1OTAzMDc1MQ==&mid=221088187&idx=1&sn=1b478b3222498437e2bbd4b979c46ce9&scene=18#wechat_redirect");
  
        CommonButton btn22 = new CommonButton();  
        btn22.setName("团购");  
        btn22.setType("view");  
        btn22.setKey("22"); 
        btn22.setUrl("http://mp.weixin.qq.com/s?__biz=MzI1OTAzMDc1MQ==&mid=221088071&idx=1&sn=b25997fb46d4c796fcd8cacaa31e4b6c&scene=18#wechat_redirect"); 
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("积分商城");  
        btn23.setType("view");  
        btn23.setKey("23");
        btn23.setUrl("http://mp.weixin.qq.com/s?__biz=MzI1OTAzMDc1MQ==&mid=221088312&idx=1&sn=2bef48e1b185a86a71bd3498864500e2&scene=18#wechat_redirect"); 

        //三个一级菜单
        //CommonButton mainBtn1 = new CommonButton(); 
        ComplexButton mainBtn1 = new ComplexButton(); //http://www.inpingo.com
        mainBtn1.setName("雷铭商城");  
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12});
        //mainBtn1.setType("view");
//        mainBtn1.setKey("1");   
        //mainBtn1.setUrl("http://b2b2c.leimingtech.com/leimingtech-front/m/index/index");

        
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("我的服务");  
        mainBtn2.setSub_button(new CommonButton[] {btn13, btn14, btn15 });  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("促销活动");  
        mainBtn3.setSub_button(new CommonButton[] { btn21, btn22, btn23 });   
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }
}
