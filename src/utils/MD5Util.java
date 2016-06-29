package utils;
import java.security.MessageDigest;
public class MD5Util {
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ����Ƶ��ַ���ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String gbEncoding(final String gbString) {   
        char[] utfBytes = gbString.toCharArray();   
              String unicodeBytes = "";   
               for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {   
                    String hexB = Integer.toHexString(utfBytes[byteIndex]);   
                      if (hexB.length() <= 2) {   
                          hexB = "00" + hexB;   
                     }   
                      unicodeBytes = unicodeBytes + "\\u" + hexB;   
                  }   
                  System.out.println("unicodeBytes is: " + unicodeBytes);   
                  return unicodeBytes;   
             }   
            
            public static String decodeUnicode(final String dataStr) {   
               int start = 0;   
                 int end = 0;   
                final StringBuffer buffer = new StringBuffer();   
                 while (start > -1) {   
                    end = dataStr.indexOf("\\u", start + 2);   
                     String charStr = "";   
                     if (end == -1) {   
                         charStr = dataStr.substring(start + 2, dataStr.length());   
                    } else {   
                        charStr = dataStr.substring(start + 2, end);   
                     }   
                     char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。   
                   buffer.append(new Character(letter).toString());   
                   start = end;   
                 }   
                 return buffer.toString();   
             }   
         
}