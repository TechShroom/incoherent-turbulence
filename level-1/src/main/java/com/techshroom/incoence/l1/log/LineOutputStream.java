package com.techshroom.incoence.l1.log;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;

import com.techshroom.incoence.l1.ExpandingBuffer;

/**
 * {@link OutputStream} implementation that forwards to a {@link Logger} at a
 * specific level.
 */
public class LineOutputStream extends OutputStream {

    @FunctionalInterface
    public interface LineConsumer {

        void onLine(String line) throws IOException;

    }

    private final ExpandingBuffer buffer = ExpandingBuffer.alloc();
    private final LineConsumer lineConsumer;

    public LineOutputStream(LineConsumer lineConsumer) {
        this.lineConsumer = lineConsumer;
    }

    @Override
    public synchronized void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        buffer.byteView(buffer.size() + len)
                .put(b, off, len);
        for (int i = off; i < off + len; i++) {
            if (b[i] == '\n') {
                // note: buffer.size() can change as a result of cutLine
                // must access it again every loop
                cutLine(buffer.size() - len + i);
            }
        }
    }

    @Override
    public synchronized void write(int b) throws IOException {
        buffer.byteView(buffer.size() + 1)
                .put((byte) b);
        if (b == '\n') {
            cutLine(buffer.size());
        }
    }

    private void cutLine(int endIndex) throws IOException {
        ByteBuffer lineBytes = buffer.byteView(buffer.size()).duplicate();
        lineBytes.position(0).limit(endIndex);
        String line = StandardCharsets.UTF_8.decode(lineBytes).toString();
        lineConsumer.onLine(line);
    }
}
