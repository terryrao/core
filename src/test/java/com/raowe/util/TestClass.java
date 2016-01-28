package com.raowe.util;

import org.junit.Test;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.fail;

public class TestClass {

    //	@Test
    public void test() {

        Object object = new Object();
        Object object2 = new Object();
        Object object3 = object;

        System.out.println(object == object2);
        System.out.println(object == object3);
        fail("Not yet implemented");
    }

    //	@Test
    public void testNameGenerator() {
        String[] aStrings = new String[]{"sdfd", "sdfa", "sdfa"};
        String name = TestClass.unqualifiedClassName(aStrings.getClass());
        System.out.println(name);
        System.out.println(unqualifiedClassName(String.class));
        System.out.println(String.class.getComponentType());
    }

    @Test
    public void testAssignAbleFrom() {
        int a = 1;
        System.out.println(Throwable.class.isAssignableFrom(Exception.class));
    }

    public static String unqualifiedClassName(Class type) {
        if (type.isArray()) {
            return unqualifiedClassName(type.getComponentType()) + "Array";
        }
        String name = type.getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }

    @Test
    public void ChangeSystemout() {
        PrintWriter pw = new PrintWriter(System.out, true);
        pw.println("hello world");
    }

    /**
     * 打开控制台输入命令
     */
    @Test
    public void testProcess() {
        String command = "javap java.lang.String";//"java -version";
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader result = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;

            while ((s = result.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("ERROR LOG : --->");
            BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = err.readLine()) != null) {
                System.err.println(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
            if (!command.startsWith("CMD /C")) {

            }
        }
    }

    /**
     * java 7 打开文件
     */
    @Test
    public void openFile() {
        Path file = Paths.get("D:\\apache-maven-3.2.1\\conf\\settings.xml");
        try {
            BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println("line : " + ++count + "  " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     */
    public void writeFile() {
        Path file = Paths.get("d:/temp/write.txt");
        try {
            BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
            writer.write("Hello World");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile() {
        Path file = Paths.get("D:\\apache-maven-3.2.1\\conf\\settings.xml");
        try {
            List<String> strings = Files.readAllLines(file);
            byte[] bytes = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void watchService() {
        boolean shutdown = false;
        try {
            WatchService watcher = FileSystems.getDefault().newWatchService();
            Path dir = FileSystems.getDefault().getPath("d:/temp"); //必须是目录
            WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            while (!shutdown) {
                key = watcher.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        System.out.print("Home dir changes");
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 异步IO
     */
    @Test
    public void asynchronousIo() {

        Path path = Paths.get("E:\\迅雷下载\\[阳光电影www.ygdy8.com].有一个地方只有我们知道.HD.720p.国语中字.rmvb");
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
            ByteBuffer buffer = ByteBuffer.allocate(100_000);
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Bytes read [" + result + "]");
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.out.println(exc.getMessage());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("我应该先出来");
    }

    /**
     * 网络通道
     */
    @Test
    public void networkChannel() {
        SelectorProvider provider = SelectorProvider.provider();
        try {
            NetworkChannel channel = provider.openSocketChannel();
            SocketAddress address = new InetSocketAddress(3080);
            channel = channel.bind(address);
            Set<SocketOption<?>> socketOptions = channel.supportedOptions();
            System.out.println(socketOptions);
            channel.setOption(StandardSocketOptions.IP_TOS, 3);
            Boolean keepAlive = channel.getOption(StandardSocketOptions.SO_KEEPALIVE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 多点通道
     */
    @Test
    public void multicastChannel() {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("net1");
            DatagramChannel dc = DatagramChannel.open(StandardProtocolFamily.INET);
            dc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            dc.bind(new InetSocketAddress(8080));
            dc.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);
            InetAddress group = InetAddress.getByName("180.90.4.12");
            MembershipKey key = dc.join(group, networkInterface);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试1.4 newIo 中的Channel
     */
    @Test
    public void getChannel() {
        final int SIZE = 1024;
        try {
            FileChannel fileChannel = new FileOutputStream("d:/temp/data.txt").getChannel();
            fileChannel.write(ByteBuffer.wrap("Some text".getBytes()));
            fileChannel.close();

            fileChannel = new RandomAccessFile("d:/temp/data.txt","rw").getChannel();
            fileChannel.position(fileChannel.size());
            fileChannel.write(ByteBuffer.wrap(" some more".getBytes()));

            fileChannel = new FileInputStream("d:/temp/data.txt").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(SIZE);
            fileChannel.read(buffer);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用通道 注意flip 和clear方法的使用
     */
    @Test
    public void copyFileWithChannel () {
        String source = "d:/temp/data.txt";
        String target = "d:/temp/data-copy.txt";
        try {
            FileChannel s = new FileInputStream(source).getChannel(),
                    t = new FileOutputStream(target).getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (s.read(byteBuffer) != -1) {
                byteBuffer.flip(); // prepare to writing
                t.write(byteBuffer);
                byteBuffer.clear(); //prepare to read
            }
            s.transferTo(0, s.size(), t); //直接与输出流对接
            //OR
            t.transferFrom(s, 0, s.size()); //直接与输入流对接
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将byte转成char 输出
     */
    @Test
    public void bufferToText () {
        final int SIZE = 1024;
        String source = "d:/temp/indata.txt";
        String target = "d:/temp/outdata.txt";

        try {
            FileChannel  fc = new FileOutputStream(source).getChannel();
            fc.write(ByteBuffer.wrap("write data ".getBytes()));
            fc.close();
            fc = new FileInputStream(source).getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(SIZE);
            fc.read(buffer);
            buffer.flip();
            //Doesn't work output is Garbled 乱码
            System.out.println(buffer.asCharBuffer());

            //Decode using this system's default Charset
            buffer.rewind();
            String encoding = System.getProperty("file.encoding");
            System.out.println("Decoded usring " + encoding + " : " + Charset.forName(encoding).decode(buffer));

            //once again

            fc = new FileOutputStream(target).getChannel();
            fc.write(ByteBuffer.wrap("test data ".getBytes("UTF-16BE")));
            fc.close();
            fc = new FileInputStream(target).getChannel();
            buffer.clear();
            fc.read(buffer);
            buffer.flip();
            //show nothing
            System.out.println(buffer.asCharBuffer());
            //user a charBuffer to write through
            fc = new FileOutputStream(target).getChannel();
            buffer = ByteBuffer.allocate(24);
            buffer.asCharBuffer().put("more data");
            fc.write(buffer);
            fc.close();

            fc = new FileInputStream(target).getChannel();
            buffer.clear();
            fc.read(buffer);
            buffer.flip();
            System.out.println(buffer.asCharBuffer());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * test view buffers
     */
    @Test
    public void testIntBuffer() {
        ByteBuffer bb = ByteBuffer.allocate(1024);
        IntBuffer ib = bb.asIntBuffer();
        ib.put(new int[]{11, 22, 33, 55, 66, 77, 44, 999});
        System.out.println(ib.get(3));
        ib.put(3, 1111);
        ib.flip();
        while (ib.hasRemaining()) {
            System.out.println(ib.get());
        }
    }

    /**
     * test mark position limit  capacity
     */
    @Test
    public void usingBuffer() {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        System.out.println(cb.rewind());
        this.symmetricScramble(cb);
        System.out.println(cb.rewind());
        symmetricScramble(cb);
        System.out.println(cb.rewind());
    }

    private static void symmetricScramble(CharBuffer buffer) {
        while(buffer.hasRemaining()) {
            buffer.mark();
            char c1 = buffer.get();
            char c2 = buffer.get();
            buffer.reset();
            buffer.put(c2).put(c1);
        }

    }

    /**
     * 测试大文件的操作 以内存映射的方式读取
     * 可以快速读取修改大文件
     */
    @Test
    public void testMemoryMappedFiles () {
        final int length = 0x8FFFFFF; //128m
        try {
            MappedByteBuffer out = new RandomAccessFile("test.dat","rw").getChannel().map(FileChannel.MapMode.READ_WRITE,0,length);
            for (int i = 0; i < length; i++) {
                out.put((byte)'X');
            }
            System.out.println("Finish writing ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectStream() {
        String path = "d:/temp/obj.file";
//        TestMode mode = new TestMode("alkd",123);
//        try {
//            ObjectOutput out = new ObjectOutputStream(new FileOutputStream(path));
//            out.writeObject(mode);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            ObjectInput in = new ObjectInputStream(new FileInputStream(path));
            Object readMode = in.readObject();
            System.out.println(readMode.getClass().getName());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testOptional() {
        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("fileName is set ? " + fullName.isPresent());
        System.out.println("fileName : " + fullName.orElseGet(() -> "[NON]"));
        System.out.println("fullName :" + fullName.map(s -> "Hey ->" + s +"!").orElse("NUll???"));

        Optional<String> fullName2 = Optional.of("Tom");
        System.out.println("fileName is set ? " + fullName2.isPresent());
        System.out.println("fileName : " + fullName2.orElseGet(() -> "[NON]"));
        System.out.println("fullName :" + fullName2.map(s -> "Hey ->" + s + "!").orElse("NUll???"));
    }


    @Test
    public void tsetClock() {
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant()); //2016-01-18T02:19:04.656Z
        System.out.println(clock.millis()); //1453083544712


        //本地时间
        final LocalDate date = LocalDate.now();
        System.out.println(date); //2016-01-18 只有日期部分

        final LocalTime time = LocalTime.now();
        System.out.println(time); //10:20:23.733 只有时间部分


        final LocalDate dateFromLock = LocalDate.now(clock);
        System.out.println(dateFromLock);
        final LocalTime timeFromClock = LocalTime.now(clock);
        System.out.println(timeFromClock);


        //格式化
        DateTimeFormatter isoDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2015-01-18 12:23:15", isoDateTime);
        String s = dateTime.format(isoDateTime);
        System.out.println(s);

    }

    @Test
    public void testArrayParalle() {
        long[] arrays = new long[1000000];
        Arrays.parallelSetAll(arrays,index -> arrays[index] = ThreadLocalRandom.current().nextInt(10000));
        Arrays.stream(arrays).limit(10).forEach(i -> System.out.print(i + " "));
        System.out.println();
        Arrays.parallelSort(arrays);

        Arrays.stream(arrays).limit(10).forEach(i -> System.out.print(i + " " ));


    }
}
