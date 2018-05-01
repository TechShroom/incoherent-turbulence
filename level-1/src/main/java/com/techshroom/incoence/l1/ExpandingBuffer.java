package com.techshroom.incoence.l1;

import java.nio.ByteBuffer;
import java.util.function.IntUnaryOperator;

import org.lwjgl.system.MemoryUtil;

/**
 * {@link ByteBuffer} that expands as needed.
 * <p>
 * Must be explicitly freed!
 * </p>
 */
public class ExpandingBuffer {

    private static final int DEFAULT_INITIAL_ALLOC = 512;
    private static final IntUnaryOperator DEFAULT_EXPAND_SIZE_FUNCTION = oldSize -> oldSize + oldSize >> 1;

    public static ExpandingBuffer alloc() {
        return alloc(DEFAULT_INITIAL_ALLOC);
    }

    public static ExpandingBuffer alloc(int initialAlloc) {
        return alloc(initialAlloc, DEFAULT_EXPAND_SIZE_FUNCTION);
    }

    public static ExpandingBuffer alloc(int initialAlloc, IntUnaryOperator expandSizeFunction) {
        return new ExpandingBuffer(initialAlloc, expandSizeFunction);
    }

    private final IntUnaryOperator expandSizeFunction;
    // position = last written area
    // limit = capacity = maximum write size
    private ByteBuffer buffer;

    private ExpandingBuffer(int initialAlloc, IntUnaryOperator expandSizeFunction) {
        this.buffer = MemoryUtil.memAlloc(initialAlloc);
        this.expandSizeFunction = expandSizeFunction;
    }

    public int position() {
        return buffer.position();
    }

    public int size() {
        return buffer.capacity();
    }

    private ByteBuffer expand(int minCapacity) {
        if (buffer.capacity() < minCapacity) {
            buffer = doExpand(minCapacity);
        }
        return buffer;
    }

    private ByteBuffer doExpand(int minCapacity) {
        int newCapacity = Math.max(minCapacity, expandSizeFunction.applyAsInt(buffer.capacity()));
        ByteBuffer newBuffer = MemoryUtil.memRealloc(buffer, newCapacity);
        // flip buffer before putting it for correct sizing
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }

    public ByteBuffer byteView(int minSize) {
        return expand(minSize);
    }

    public void free() {
        MemoryUtil.memFree(buffer);
        buffer = null;
    }

}
