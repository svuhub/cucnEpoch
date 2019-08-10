package cucn.collect.common.domain.Test;

public class Testing {
	/**
	 * 密钥
	 */
	private static String secretKey = "";
	/**
	 * 向量
	 */
	private static String iv = "";

	/**
	 * 明文
	 */
	private static String plainText = "";
	/**
	 * 密文
	 */
	private static String encryptStr = "";

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// System.out.println("第三方渠道~微信");

		secretKey = "askgakjgidign399ew3593er";
		iv = "2156ghrb";

		plainText = "{\"userId\":\"\",\"deviceNum\":\"18130864729\",\"deviceType\":\"481\",\"latnId\":\"\",\"province\":\"新疆\",\"city\":\"乌鲁木齐\"}";

		encryptStr = encrypt(secretKey, iv, plainText);
		encryptStr = "MxeuDZZ%2FRTT%2BMfaTVYWhEwGr7h%2BIE4EGKx7weqAuEy1a45vWT3%2Fcyd9TR%2Fop+1QTf1A8po640hGF%2F9P9h4JN7%2F68iJVjGe%2BAWZjkCWuz69W7ZdKcXVewNjHxS+uDtF%2Bt6%2F4tUh%2B9KGewDxWQGdnA5UpU5jZ%2FqOyIv%2F";

		decrypt(secretKey, iv, encryptStr);

	}

	/**
	 * 加密
	 *
	 * @param secretKey
	 *            密钥
	 * @param iv
	 *            向量
	 * @param plainText
	 *            明文
	 * @return 密文
	 */
	public static String encrypt(String secretKey, String iv, String plainText) throws Exception {
		System.out.println("\t需要加密的字符串：" + plainText);
		System.out.println("编码加密");
		encryptStr = DES3Util.encryptStringURLEncoder(secretKey, iv, plainText);
		System.out.println("\tDES3加密后：" + encryptStr);
		return encryptStr;
	}

	/**
	 * 解密
	 *
	 * @param secretKey
	 *            秘钥
	 * @param iv
	 *            向量
	 * @param encryptStr
	 *            密文
	 * @return 明文
	 */
	public static String decrypt(String secretKey, String iv, String encryptStr) throws Exception {
		System.out.println("\n\t需要解密的字符串：\t" + encryptStr);
		System.out.println("解码解密");
		plainText = DES3Util.decryptStringURLDecoder(secretKey, iv, encryptStr);
		System.out.println("\tDES3解密后：" + plainText);
		return plainText;
	}
}