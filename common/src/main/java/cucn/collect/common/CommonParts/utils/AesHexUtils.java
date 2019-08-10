package cucn.collect.common.CommonParts.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public final class AesHexUtils {
    /*key 要求：1长度：16位、24位、32位
               2字符：只能在{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'}内生成
    * */
    private static final String PRIVATE_KEY = "a5bC4Fe6f71A09AcF3bc8dBAf71DE2aC";
    private static final String AES = "AES";
    private static final String UTF8 = "UTF-8";

    /**
     * AES加密
     *
     * @param content
     * @return
     * @throws DecoderException
     */
    private static byte[] encrypt(String content) throws DecoderException {
        try {
            byte[] encodeFormat = null;
            try {
                //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
                encodeFormat = Hex.decodeHex(PRIVATE_KEY.toCharArray());
            } catch (DecoderException e) {
                e.printStackTrace();
            }
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 加密内容进行编码
            byte[] byteContent = content.getBytes(UTF8);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 正式执行加密操作
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param contents
     * @return
     * @throws DecoderException
     */
    private static byte[] decrypt(String contents) throws DecoderException {
        try {
            //密文使用Hex解码
            byte[] content = Hex.decodeHex(contents.toCharArray());
            //秘钥 Hex解码为什么秘钥要进行解码，因为秘钥是某个秘钥明文进行了Hex编码后的值，所以在使用的时候要进行解码
            byte[] encodeFormat = Hex.decodeHex(PRIVATE_KEY.toCharArray());
            SecretKeySpec key = new SecretKeySpec(encodeFormat, AES);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(AES);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 正式执行解密操作
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Aes加密
     *
     * @param context 明文
     * @return
     * @throws DecoderException
     */
    public static String encryption(String context) {
        //加密后的明文也就变成了密文
        byte[] encryptResult = new byte[0];
        try {
            encryptResult = encrypt(context);
        } catch (DecoderException e) {

        }
        //密码文Hex编码
        String encryptResultStr = Hex.encodeHexString(encryptResult);
        return encryptResultStr;
    }

    /**
     * Aes解密
     *
     * @param context 密文
     * @return
     * @throws DecoderException
     * @throws UnsupportedEncodingException
     */
    public static String decryption(String context) throws DecoderException, UnsupportedEncodingException {
        //这里的密文解密前先进行了Hex解码
        byte[] decryptResult = decrypt(context);
        String result = new String(decryptResult, UTF8);
        return result;
    }

    public static void main(String[] args) throws DecoderException, UnsupportedEncodingException {
        String context = "{\"areaCode\":\"025\",\"broadbandNum\":\"02513945401\"}";
        System.out.println(context);
        String encryption = AesHexUtils.encryption(context);
        System.out.println(encryption);
        String decryption = AesHexUtils.decryption(encryption);
        System.out.println(decryption);
    }
}
