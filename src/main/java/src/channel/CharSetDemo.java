package nio.channel;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.SortedMap;

/**
 * 字符集：CharSet
 * 编码：字符串->字节码
 * 解码：字节码—>字符串
 */
public class CharSetDemo {

    /**
     * 系统支持的所有编码
     */
    @Test
    public void allChasrtSet() {
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : charsets.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    /**
     * 乱码问题
     */
    @Test
    public void luanMaWenti() throws Exception {
        //默认编码是utf-8
        Charset defaultCharset = Charset.defaultCharset();
        System.out.println(defaultCharset.name());

        Charset gbk = Charset.forName("GBK");

        //获取编码器和解码器
        CharsetEncoder encoder = gbk.newEncoder();
        CharsetDecoder decoder = gbk.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(100);
        charBuffer.put("我是中国人");
        charBuffer.flip();
        ByteBuffer encodeBuffer = encoder.encode(charBuffer);

        CharBuffer decode = decoder.decode(encodeBuffer);
        System.out.println(decode.toString());

    }

}
