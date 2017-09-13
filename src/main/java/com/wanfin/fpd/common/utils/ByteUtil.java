package com.wanfin.fpd.common.utils;

import java.util.Random;

public class ByteUtil {
	/**
	 * bytes转换成十六进制字符串
	 */
	public static String bytes2HexStr(byte[] b) {
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++) {
			stmp=(Integer.toHexString(b[n] & 0XFF));
			if (stmp.length()==1) hs=hs+"0"+stmp;
			else hs=hs+stmp;
		}
		return hs.toUpperCase();
	}

	private static byte uniteBytes(String src0, String src1) {
		byte b0 = Byte.decode("0x" + src0).byteValue();
		b0 = (byte) (b0 << 4);
		byte b1 = Byte.decode("0x" + src1).byteValue();
		byte ret = (byte) (b0 | b1);
		return ret;
	}

	/**
	 * 十六进制字符串换转成bytes
	 */
	public static byte[] hexStr2Bytes(String src) {
		int m=0,n=0;
		int l=src.length()/2;
		byte[] ret = new byte[l];
		for (int i = 0; i < l; i++) {
			m=i*2+1;
			n=m+1;
			ret[i] = uniteBytes(src.substring(i*2, m),src.substring(m,n));
		}
		return ret;
	}
	
	/** 
     * 合并两个byte数组 
     * @param pByteA 
     * @param pByteB 
     * @return 
     */  
    public static byte[] mergeBytes(byte[] pByteA, byte[] pByteB){  
        int aCount = pByteA.length;  
        int bCount = pByteB.length;  
        byte[] b = new byte[aCount + bCount];  
        for(int i=0;i<aCount;i++){  
            b[i] = pByteA[i];  
        }  
        for(int i=0;i<bCount;i++){  
            b[aCount + i] = pByteB[i];  
        }  
        return b;  
    } 
    
    /**
	 * 截取byte数据
	 * @param b	是byte数组
	 * @param j	是大小
	 * @return
	 */
	public static byte[] cutOutByte(byte[] b,int j){
		if(b.length==0 || j==0){
			return null;
		}
		byte[] bjq = new byte[j];
		for(int i = 0; i<j;i++){
			bjq[i]=b[i];
		}
		return bjq;
	}

	/**
	 * 
	 * 功能：获取随机长度的数组
	 *
	 * @param length
	 * @return
	 */
	public static byte[] getRandomBytes(int length){
        if (length < 1) {
            return null;
        }
        byte[] data = new byte[length];
        Random ran = new Random();
        for (int i = 0; i < length; i++) {
            data[i] = (byte)ran.nextInt(256);
        }
        return data;
    }
}
