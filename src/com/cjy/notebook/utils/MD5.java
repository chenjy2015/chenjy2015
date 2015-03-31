package com.cjy.notebook.utils;

import java.security.MessageDigest;


public class MD5 {
	
	 public final static String getMD5String(String s) {  
	        char hexDigits[] = { '0', '1', '2', '3', '4',  
	                '5', '6', '7', '8', '9',  
	                'A', 'B', 'C', 'D', 'E', 'F' };  
	        try {  
	            byte[] btInput = s.getBytes();  
	            //获得MD5摘要算法的 MessageDigest 对象  
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
	            //使用指定的字节更新摘要  
	            mdInst.update(btInput);  
	            //获得密文  
	            byte[] md = mdInst.digest();  
	            //把密文转换成十六进制的字符串形式  
	            int j = md.length;  
	            char str[] = new char[j * 2];  
	            int k = 0;  
	            for (int i = 0; i < j; i++) {  
	                byte byte0 = md[i];  
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	                str[k++] = hexDigits[byte0 & 0xf];  
	            }  
	            return new String(str);  
	        }  
	        catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	 

	static final byte a[] = { -128, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0 };

	private long b[];

	private long c[];

	private byte d[];

	public String digestHexStr;

	private byte e[];

	/**
	 * 获得字符串s的MD5加密串
	 * 
	 * @param s
	 *            ：需加密的串
	 * @return
	 */
	public String getMD5(String s) {
		a();
		a(s.getBytes(), s.length());
		b();
		digestHexStr = "";
		for (int i = 0; i < 16; i++)
			digestHexStr += byteHEX(e[i]);

		return digestHexStr.toLowerCase();
	}
	
	
	
	
	/**
	 * 获得字符串s的MD5加密串
	 * 
	 * @param s
	 *            ：需加密的串
	 * @return
	 */
	public String getPasswordMD5ofStr(String s) {
		
		a();
		a(s.getBytes(), s.length());
		b();
		digestHexStr = "";
		for (int i = 0; i < 16; i++)
			digestHexStr += byteHEX(e[i]);

		return digestHexStr.toLowerCase();
	}


	public byte[] getMD5(byte abyte0[]) {
		a();
		a(abyte0, abyte0.length);
		b();
		return e;
	}

	public MD5() {
		b = new long[4];
		c = new long[2];
		d = new byte[64];
		e = new byte[16];
		a();
	}

	private void a() {
		c[0] = 0L;
		c[1] = 0L;
		b[0] = 0x67452301L;
		b[1] = 0xefcdab89L;
		b[2] = 0x98badcfeL;
		b[3] = 0x10325476L;
	}

	private static long a(long l, long l1, long l2) {
		return l & l1 | ~l & l2;
	}

	private static long b(long l, long l1, long l2) {
		return l & l2 | l1 & ~l2;
	}

	private static long c(long l, long l1, long l2) {
		return l ^ l1 ^ l2;
	}

	private static long d(long l, long l1, long l2) {
		return l1 ^ (l | ~l2);
	}

	private long a(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		return l = (l = (int) (l += a(l1, l2, l3) + l4 + l6) << (int) l5
				| (int) l >>> (int) (32L - l5))
				+ l1;
	}

	private long b(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		return l = (l = (int) (l += b(l1, l2, l3) + l4 + l6) << (int) l5
				| (int) l >>> (int) (32L - l5))
				+ l1;
	}

	private long c(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		return l = (l = (int) (l += c(l1, l2, l3) + l4 + l6) << (int) l5
				| (int) l >>> (int) (32L - l5))
				+ l1;
	}

	private long d(long l, long l1, long l2, long l3, long l4, long l5, long l6) {
		return l = (l = (int) (l += d(l1, l2, l3) + l4 + l6) << (int) l5
				| (int) l >>> (int) (32L - l5))
				+ l1;
	}

	private void a(byte abyte0[], int i) {
		byte abyte1[] = new byte[64];
		int k = (int) (c[0] >>> 3) & 0x3f;
		if ((c[0] += i << 3) < (long) (i << 3))
			c[1]++;
		c[1] += i >>> 29;
		int l = 64 - k;
		int j;
		if (i >= l) {
			a(d, abyte0, k, 0, l);
			a(d);
			for (j = l; j + 63 < i; j += 64) {
				a(abyte1, abyte0, 0, j, 64);
				a(abyte1);
			}

			k = 0;
		} else {
			j = 0;
		}
		a(d, abyte0, k, j, i - j);
	}

	private void b() {
		byte abyte0[];
		a(abyte0 = new byte[8], c, 8);
		int i;
		int j = (i = (int) (c[0] >>> 3) & 0x3f) >= 56 ? 120 - i : 56 - i;
		a(a, j);
		a(abyte0, 8);
		a(e, b, 16);
	}

	private static void a(byte abyte0[], byte abyte1[], int i, int j, int k) {
		for (int l = 0; l < k; l++)
			abyte0[i + l] = abyte1[j + l];

	}

	private void a(byte abyte0[]) {
		long l = b[0];
		long l1 = b[1];
		long l2 = b[2];
		long l3 = b[3];
		long al[];
		a(al = new long[16], abyte0, 64);
		l = a(l, l1, l2, l3, al[0], 7L, 0xd76aa478L);
		l3 = a(l3, l, l1, l2, al[1], 12L, 0xe8c7b756L);
		l2 = a(l2, l3, l, l1, al[2], 17L, 0x242070dbL);
		l1 = a(l1, l2, l3, l, al[3], 22L, 0xc1bdceeeL);
		l = a(l, l1, l2, l3, al[4], 7L, 0xf57c0fafL);
		l3 = a(l3, l, l1, l2, al[5], 12L, 0x4787c62aL);
		l2 = a(l2, l3, l, l1, al[6], 17L, 0xa8304613L);
		l1 = a(l1, l2, l3, l, al[7], 22L, 0xfd469501L);
		l = a(l, l1, l2, l3, al[8], 7L, 0x698098d8L);
		l3 = a(l3, l, l1, l2, al[9], 12L, 0x8b44f7afL);
		l2 = a(l2, l3, l, l1, al[10], 17L, 0xffff5bb1L);
		l1 = a(l1, l2, l3, l, al[11], 22L, 0x895cd7beL);
		l = a(l, l1, l2, l3, al[12], 7L, 0x6b901122L);
		l3 = a(l3, l, l1, l2, al[13], 12L, 0xfd987193L);
		l2 = a(l2, l3, l, l1, al[14], 17L, 0xa679438eL);
		l1 = a(l1, l2, l3, l, al[15], 22L, 0x49b40821L);
		l = b(l, l1, l2, l3, al[1], 5L, 0xf61e2562L);
		l3 = b(l3, l, l1, l2, al[6], 9L, 0xc040b340L);
		l2 = b(l2, l3, l, l1, al[11], 14L, 0x265e5a51L);
		l1 = b(l1, l2, l3, l, al[0], 20L, 0xe9b6c7aaL);
		l = b(l, l1, l2, l3, al[5], 5L, 0xd62f105dL);
		l3 = b(l3, l, l1, l2, al[10], 9L, 0x2441453L);
		l2 = b(l2, l3, l, l1, al[15], 14L, 0xd8a1e681L);
		l1 = b(l1, l2, l3, l, al[4], 20L, 0xe7d3fbc8L);
		l = b(l, l1, l2, l3, al[9], 5L, 0x21e1cde6L);
		l3 = b(l3, l, l1, l2, al[14], 9L, 0xc33707d6L);
		l2 = b(l2, l3, l, l1, al[3], 14L, 0xf4d50d87L);
		l1 = b(l1, l2, l3, l, al[8], 20L, 0x455a14edL);
		l = b(l, l1, l2, l3, al[13], 5L, 0xa9e3e905L);
		l3 = b(l3, l, l1, l2, al[2], 9L, 0xfcefa3f8L);
		l2 = b(l2, l3, l, l1, al[7], 14L, 0x676f02d9L);
		l1 = b(l1, l2, l3, l, al[12], 20L, 0x8d2a4c8aL);
		l = c(l, l1, l2, l3, al[5], 4L, 0xfffa3942L);
		l3 = c(l3, l, l1, l2, al[8], 11L, 0x8771f681L);
		l2 = c(l2, l3, l, l1, al[11], 16L, 0x6d9d6122L);
		l1 = c(l1, l2, l3, l, al[14], 23L, 0xfde5380cL);
		l = c(l, l1, l2, l3, al[1], 4L, 0xa4beea44L);
		l3 = c(l3, l, l1, l2, al[4], 11L, 0x4bdecfa9L);
		l2 = c(l2, l3, l, l1, al[7], 16L, 0xf6bb4b60L);
		l1 = c(l1, l2, l3, l, al[10], 23L, 0xbebfbc70L);
		l = c(l, l1, l2, l3, al[13], 4L, 0x289b7ec6L);
		l3 = c(l3, l, l1, l2, al[0], 11L, 0xeaa127faL);
		l2 = c(l2, l3, l, l1, al[3], 16L, 0xd4ef3085L);
		l1 = c(l1, l2, l3, l, al[6], 23L, 0x4881d05L);
		l = c(l, l1, l2, l3, al[9], 4L, 0xd9d4d039L);
		l3 = c(l3, l, l1, l2, al[12], 11L, 0xe6db99e5L);
		l2 = c(l2, l3, l, l1, al[15], 16L, 0x1fa27cf8L);
		l1 = c(l1, l2, l3, l, al[2], 23L, 0xc4ac5665L);
		l = d(l, l1, l2, l3, al[0], 6L, 0xf4292244L);
		l3 = d(l3, l, l1, l2, al[7], 10L, 0x432aff97L);
		l2 = d(l2, l3, l, l1, al[14], 15L, 0xab9423a7L);
		l1 = d(l1, l2, l3, l, al[5], 21L, 0xfc93a039L);
		l = d(l, l1, l2, l3, al[12], 6L, 0x655b59c3L);
		l3 = d(l3, l, l1, l2, al[3], 10L, 0x8f0ccc92L);
		l2 = d(l2, l3, l, l1, al[10], 15L, 0xffeff47dL);
		l1 = d(l1, l2, l3, l, al[1], 21L, 0x85845dd1L);
		l = d(l, l1, l2, l3, al[8], 6L, 0x6fa87e4fL);
		l3 = d(l3, l, l1, l2, al[15], 10L, 0xfe2ce6e0L);
		l2 = d(l2, l3, l, l1, al[6], 15L, 0xa3014314L);
		l1 = d(l1, l2, l3, l, al[13], 21L, 0x4e0811a1L);
		l = d(l, l1, l2, l3, al[4], 6L, 0xf7537e82L);
		l3 = d(l3, l, l1, l2, al[11], 10L, 0xbd3af235L);
		l2 = d(l2, l3, l, l1, al[2], 15L, 0x2ad7d2bbL);
		l1 = d(l1, l2, l3, l, al[9], 21L, 0xeb86d391L);
		b[0] += l;
		b[1] += l1;
		b[2] += l2;
		b[3] += l3;
	}

	private static void a(byte abyte0[], long al[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			abyte0[k] = (byte) (int) (al[j] & 255L);
			abyte0[k + 1] = (byte) (int) (al[j] >>> 8 & 255L);
			abyte0[k + 2] = (byte) (int) (al[j] >>> 16 & 255L);
			abyte0[k + 3] = (byte) (int) (al[j] >>> 24 & 255L);
			j++;
		}

	}

	private static void a(long al[], byte abyte0[], int i) {
		int j = 0;
		for (int k = 0; k < i; k += 4) {
			al[j] = b2iu(abyte0[k]) | b2iu(abyte0[k + 1]) << 8
					| b2iu(abyte0[k + 2]) << 16 | b2iu(abyte0[k + 3]) << 24;
			j++;
		}

	}

	public static long b2iu(byte byte0) {
		return (long) (byte0 >= 0 ? byte0 : byte0 & 0xff);
	}

	public static String byteHEX(byte byte0) {
		char ac[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char ac1[];
		(ac1 = new char[2])[0] = ac[byte0 >>> 4 & 0xf];
		ac1[1] = ac[byte0 & 0xf];
		String s = null;
		s = new String(ac1);
		return s;
	}
}
