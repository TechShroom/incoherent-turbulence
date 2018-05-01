package com.techshroom.incoence.l1;

import static org.lwjgl.glfw.GLFW.glfwGetMonitors;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;

import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.immutable.primitive.ImmutableLongListFactoryImpl;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.Pointer;

import com.google.common.collect.ImmutableList;

public final class L1Monitor extends Pointer.Default {

    public static ImmutableLongList getMonitors() {
        MutableLongList list = new LongArrayList();
        PointerBuffer monitors = glfwGetMonitors();
        while (monitors.hasRemaining()) {
            list.add(monitors.get());
        }
        return ImmutableLongListFactoryImpl.INSTANCE.ofAll(list);
    }

    public static ImmutableList<L1Monitor> getWrappedMonitors() {
        return ImmutableList.copyOf(getMonitors().collect(L1Monitor::wrap));
    }

    public static L1Monitor wrapPrimaryMonitor() {
        return wrap(glfwGetPrimaryMonitor());
    }

    public static L1Monitor wrap(long pointer) {
        return new L1Monitor(pointer);
    }

    private L1Monitor(long pointer) {
        super(pointer);
    }

}
