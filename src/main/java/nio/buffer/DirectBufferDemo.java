package nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * NIO为什么能提高效率？
 *
 * 直接缓冲区(只有ByteBuffer支持，其他的都不支持)和非直接缓冲区.
 *
 * 非直接缓冲区：通过allocate()方法分配的缓冲区，它是将缓冲区建立在JVM的内存中->{@linkplain HeapByteBuffer},堆缓冲区
 * 直接缓冲区: 通过allocateDirect()方法分配的缓冲区，它是将缓冲区建立在物理内存中，可以提高效率->DirectByteBuffer
 *            弊端：1、线程不安全
 *                 2、应用程序写入数据之后，何时写入磁盘不受应用程序控制，完全由操作系统控制，
 *                 所以内存的分配和销毁就很耗费资源。销毁就靠gc.
 *
 */
public class DirectBufferDemo {

    @Test
    public void test1(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        buffer.put("i ame riane".getBytes());
        buffer.flip();

        byte[] bytes = new byte[buffer.limit()];

        buffer.get(bytes);

        System.out.println(new String(bytes));
    }
}
