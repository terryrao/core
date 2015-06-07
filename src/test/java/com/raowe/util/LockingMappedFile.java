package com.raowe.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by terryrao on 6/6/2015.
 */
public class LockingMappedFile {
    static final int length = 0x888888; //size  128M
    static FileChannel fileChannel;

    public static void main(String[] args) {
        try {
            fileChannel = new RandomAccessFile("d:/temp/test.txt","rw").getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,length);
            for (int i = 0; i < length; i++) {
                mappedByteBuffer.put((byte)'X');
            }
            new LockAndModify(mappedByteBuffer,0,length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class LockAndModify extends Thread {
        private ByteBuffer buffer;
        private int start, end;

        public LockAndModify(ByteBuffer buffer, int start, int end) {
            this.start = start;
            this.end = end;
            buffer.position(start);
            buffer.limit(end);
            this.buffer = buffer.slice();
            start();
        }

        @Override
        public void run() {
            try {
                FileLock fileLock = fileChannel.lock(start,end,false);
                System.out.println("Locked : " + start + " to " + end);
                while (buffer.position() <= buffer.limit() - 1) {
                    buffer.put((byte) (buffer.get() + 1));
                }
                fileLock.release();
                System.out.println("released : " + start + " to " + end);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
