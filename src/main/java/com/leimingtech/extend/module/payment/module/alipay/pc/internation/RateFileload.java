package com.leimingtech.extend.module.payment.module.alipay.pc.internation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leimingtech.extend.module.payment.alipay.pc.internation.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.pc.internation.config.AlipayInternaContents;
import com.leimingtech.extend.module.payment.alipay.pc.internation.util.AlipaySubmit;
@Component
public class RateFileload {
	
	public static  String ratevalue;//汇率O
	public static File fecthFile(String httpUrl, String fileSavePath,
			String fileName) throws MalformedURLException, IOException {

		// 打开输入流
		BufferedInputStream in = new BufferedInputStream(
				getInputStream(httpUrl));

		fileName = fileName + ".txt";
		File file = new File(fileSavePath + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		// 打开输出流
		FileOutputStream out = new FileOutputStream(file);
		byte[] buff = new byte[1];
		// 读取数据
		while (in.read(buff) > 0) {
			out.write(buff);
		}
		out.flush();
		out.close();
		in.close();
		return file;
	}

	/**
	 * 格式化日期
	 */
	public static String getFormatDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dataStr = formatter.format(date).toString();
		return dataStr;
	}

	public static StringBuffer fetchHtml(String httpUrl)
			throws MalformedURLException, IOException {

		StringBuffer data = new StringBuffer();
		String currentLine;
		// 打开输入流
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				getInputStream(httpUrl), "GBK"));
		// 读取数据
		while ((currentLine = reader.readLine()) != null) {
			data.append(currentLine);
		}
		reader.close();
		return data;
	}

	/**
	 * 获取数据流
	 * 
	 * @param httpUrl
	 * @return
	 * @throws IOException
	 */
	private static InputStream getInputStream(String httpUrl)
			throws IOException {
		// 网页Url
		URL url = new URL(httpUrl);
		URLConnection uc = url.openConnection();
		uc.setRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		return uc.getInputStream();
	}

	/**
	 * 从URL中得到JGP文件并写到制定的目录中
	 */
	public static boolean writeImageFile(String URL, String Path,
			String fileName) {
		boolean isflag = true;
		try {
			fecthFile(URL, Path, fileName);
		} catch (MalformedURLException e) {
			isflag = false;
			e.printStackTrace();
		} catch (IOException e) {
			isflag = false;
			e.printStackTrace();
		}
		return isflag;
	}

	
	/**
     * 
     * 每晚11:00执行
     */
    @Scheduled(cron="59 59 10 * * ? ")
	public  void loadfile() {
	  ratefile();//更新汇率文件数据
      readrate();//读取人民币与美元汇率
	}
    /**
     * 读取美元汇率
     */
    public void readrate(){
      String lastLine="";//最后一行记录
   	  File file = new File(AlipayInternaContents.aplipayratefiledir+AlipayInternaContents.aplipayratefilename+".txt"); // 仅一行文字  
   	  long  start = System.currentTimeMillis();  
   	  try {
   		lastLine = readLastLine(file, "gbk");
   	   } catch (IOException e) {
   		e.printStackTrace();
   	   }  
   	  long  delt = System.currentTimeMillis() - start;  
   	  System.out.println(lastLine);
   	  if(!"".equals(lastLine)){
   		 String[] sf=lastLine.split("\\|");
   		 RateFileload.ratevalue=sf[3];
   		 System.out.println(sf[3]);
   	  }
   	  System.out.println("读取时间(毫秒):" + delt);
    }
    
	public void ratefile() {
		String sign = getsign();// 获取商户签名
		try {
			fecthFile(
					"https://mapi.alipay.com/gateway.do?service=forex_rate_file&partner="
							+ AlipayConfig.partner + "&sign=" + sign
							+ "&sign_type=" + AlipayConfig.sign_type,
							AlipayInternaContents.aplipayratefiledir,
					AlipayInternaContents.aplipayratefilename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getsign() {//获取签名
		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("partner", AlipayConfig.partner);// 合作身份者ID
		sParaTemp.put("service", "forex_rate_file");// 支付宝下载汇率文档的接口
		String sign = AlipaySubmit.buildRequestMysign(sParaTemp);// 获取签名
		return sign;
	}
	
	//读取指定文件的最后一行内容
	public static String readLastLine(File file, String charset) throws IOException { 
		  System.out.println("****"+file);
		  if (!file.exists() || file.isDirectory() || !file.canRead()) {  
		    return null;  
		  }  
		  RandomAccessFile raf = null;  
		  try {  
		    raf = new RandomAccessFile(file, "r");  
		    long len = raf.length();  
		    if (len == 0L) {  
		      return "";  
		    } else {  
		      long pos = len - 1;  
		      while (pos > 0) {  
		        pos--;  
		        raf.seek(pos);  
		        if (raf.readByte() == '\n') {  
		          break;  
		        }  
		      }  
		      if (pos == 0) {  
		        raf.seek(0);  
		      }  
		      byte[] bytes = new byte[(int) (len - pos)];  
		      raf.read(bytes);  
		      if (charset == null) {  
		        return new String(bytes);  
		      } else {  
		        return new String(bytes, charset);  
		      }  
		    }  
		  } catch (FileNotFoundException e) {  
		  } finally {  
		    if (raf != null) {  
		      try {  
		        raf.close();  
		      } catch (Exception e2) {  
		      }  
		    }  
		  }  
		  return null;  
		}  
}
