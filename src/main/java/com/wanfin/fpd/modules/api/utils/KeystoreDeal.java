package com.wanfin.fpd.modules.api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 获取公钥私钥
 * @author user
 *
 */
public class KeystoreDeal {
	public static final String WISH_YL_PRIVATE_KEY = "payeco.PrivateKey";//Wish项目易联私钥
	public static final String WISH_YL_PUBLIC_KEY  = "payeco.PublicKey";//Wish项目易联公钥
	public static final String WISH_CS_PRIVATE_KEY = "winfin.PrivateKey";//Wish项目万众私钥
	public static final String WISH_CS_PUBLIC_KEY  = "winfin.PublicKey";//Wish项目万众公钥
	
	/**
	 * 生成请求的签名--使用接口方(易联)API的公钥
	 * @param data
	 * @return
	 */
	public static String getApiSign(String data){
		String privateKey = getKeystore(WISH_YL_PUBLIC_KEY);
		if(StringUtils.isNotBlank(privateKey)){
			return RSASignature.sign(data, privateKey);
		}else{
			return "";
		}
	}
	
	/**
	 * 生成服务端的签名--使用服务端(创世)的私钥
	 * @param data
	 * @return
	 */
	public static String getPlatformSign(String data){
		String privateKey = getKeystore(WISH_CS_PRIVATE_KEY);
		if(StringUtils.isNotBlank(privateKey)){
			return RSASignature.sign(data, privateKey);
		}else{
			return "";
		}
	}
	
	/**
	 * 验证响应签名--使用接口方(易联)提供的公钥
	 * @param data
	 * @param sign
	 * @return
	 */
	public static boolean checkApiSign(String data, String sign){
		if(StringUtils.isAnyBlank(data, sign)){
			return false;
		}else{
			String publicKey = getKeystore(WISH_YL_PUBLIC_KEY);
			if(StringUtils.isNotBlank(publicKey)){
				return RSASignature.doCheck(data, sign, publicKey);
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 验证响应签名--使用服务平台(创世)提供的私钥验证
	 * @param data
	 * @param sign
	 * @return
	 */
	public static boolean checkServiceSign(String data, String sign){
		if(StringUtils.isAnyBlank(data, sign)){
			return false;
		}else{
			String publicKey = getKeystore(WISH_CS_PRIVATE_KEY);
			if(StringUtils.isNotBlank(publicKey)){
				return RSASignature.doCheck(data, sign, publicKey);
			}else{
				return false;
			}
		}
	}
	
	/**
	 * 获取公钥或私钥内容
	 * @param keystore 公私钥在配置文件中的key
	 * @return
	 */
	public static String getKeystore(String keystore){
		String keyContent = Global.getMap().get(keystore);
		if(keyContent == null){
			String resourcesPath = Global.getApiConfig(keystore);
			if(StringUtils.isNotBlank(resourcesPath)){
				keyContent = loadPublicKeyByFile(resourcesPath);
				Global.getMap().put(keystore, keyContent != null ? keyContent : StringUtils.EMPTY);
			}
		}
		
		return keyContent;
	}
	
	/**
	 * 读取公钥或私钥文件
	 * @param resourcesPath 在resources下的相对路径
	 * @return 公钥或私钥的内容
	 */
	public static String loadPublicKeyByFile(String resourcesPath){
		URL fileURL=KeystoreDeal.class.getResource(resourcesPath);
		System.out.println("签名公私钥"+resourcesPath+"地址:"+fileURL);
		try {
			StringBuilder sb = new StringBuilder();
			
			InputStream is = fileURL.openStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			isr.close();
			is.close();
			
			return sb.toString();
		} catch (IOException e) {
			System.out.println("公钥数据流读取错误");
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("公钥输入流为空");
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
