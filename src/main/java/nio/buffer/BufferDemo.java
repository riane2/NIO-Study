package nio.buffer;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * nio的核心之一：缓冲器，负责数据的承载(存取)，缓冲区的底层就是数组，用于存储不同数据类型的数据
 * <p>
 * 根据数据类型的不同，提供了不同类型(boolean除外)的缓冲区
 * <p>
 * ByteBuffer(最常用的缓冲区)
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * ByteBuffer是NIO里用得最多的Buffer，它包含两个实现方式：
 * HeapByteBuffer是基于Java堆的实现，而DirectByteBuffer则使用了unsafe的API进行了堆外的实现
 * <p>
 * ByteBuffer的读写模式是分开的，正常的应用场景是：往ByteBuffer里写一些数据，然后flip()，然后再读出来。
 * <p>
 * 所有缓冲区都有4个属性：capacity、limit、position、mark，并遵循：mark <= position <= limit <= capacity，
 * Capacity	容量，即可以容纳的最大数据量；在缓冲区创建时被设定并且不能改变
 * Limit	缓冲区中可以操作数据的大小，即Limite之后的数据我们是不能进行读写的。
 * 表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且极限是可以修改的
 * Position	位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备
 * Mark	标记，标识当前position的位置，调用mark()来设置mark=position，再调用reset()可以让position恢复到标记的位置
 */
public class BufferDemo {

    @Test
    public void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());

        byteBuffer.put("abc".getBytes());

        System.out.println("放入三个元素之后......");
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());

/**
 * 切换模式：即如果写完数据之后要从里面读取数据，就要flip,重新计算当前buffer的 capacity，position以及limit和mark
 */


        byteBuffer.flip();

        System.out.println("flip切换成读模式之后......");
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());

        byte[] getByte = new byte[byteBuffer.limit()];

        //读取到方法参数指定的数组(指定的这个数组的大小必须和bytebuffer中数据大小一致或者小于，看源码就知道原因了)中去，其内部逻辑就是我下面写的for循环
        byteBuffer.get(getByte);

        System.out.println("get取出所有元素之后......");
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());

        /*for (int i = 0; byteBuffer.remaining() != 0; i++) {
//            if (byteBuffer.remaining() < 0) {
//                break;
//            }
            getByte[i] = byteBuffer.get();
        }*/
        System.out.println(new String(getByte));
        /**
         * rewind:切换重复读模式
         */
        byteBuffer.rewind();

        System.out.println("rewind之后......");
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());

        byteBuffer.hasRemaining();
        for (int i = 0; byteBuffer.remaining() != 0; i++) {
            getByte[i] = byteBuffer.get();
        }

        System.out.println(new String(getByte));

        /**
         * clear:清空缓冲区,但是缓冲区中的数据依然存在，只是其中的数据存在被遗忘状态（指针回到原位而已）
         */
        byteBuffer.clear();
        System.out.println("clear之后......***************");
        System.out.println("缓冲区当前操作位置为：" + byteBuffer.position());
        System.out.println("缓冲区可操作大小为：" + byteBuffer.limit());
        System.out.println("缓冲区容量大小为：" + byteBuffer.capacity());
        System.out.println((char) byteBuffer.get());
    }

    @Test
    public void testMark() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put("abcd".getBytes());

        buffer.flip();

        byte[] bytes = new byte[buffer.limit()];
        //从缓冲区中读取2个元素，放到bytes数组的第二个位置开始的地方
        buffer.get(bytes, 0, 2);
        System.out.println(new String(bytes,0,2));
        System.out.println("取出两个元素之后position的位置：" + buffer.position());
        /**
         *标记当前position的位置
         */
        buffer.mark();
        System.out.println("标记当前position的位置：" + buffer.mark());

        buffer.get(bytes, 2, 2);
        System.out.println("再取出两个元素之后position的位置：" + buffer.position());
        System.out.println(new String(bytes, 2, 2));

        /**
         * 恢复position的位置
         */
        buffer.reset();
        System.out.println("reset之后position的位置：" + buffer.position());
        System.out.println(buffer.position());
    }

}
