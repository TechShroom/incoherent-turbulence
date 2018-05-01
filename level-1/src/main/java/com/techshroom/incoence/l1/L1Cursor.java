package com.techshroom.incoence.l1;

import static org.lwjgl.glfw.GLFW.glfwCreateCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.Pointer;

public final class L1Cursor extends Pointer.Default {

    public static L1Cursor createCursor(GLFWImage image, int xHotspot, int yHotspot) {
        return wrap(glfwCreateCursor(image, xHotspot, yHotspot));
    }

    public static L1Cursor createStandardCursor(int shape) {
        return wrap(glfwCreateStandardCursor(shape));
    }

    public static L1Cursor wrap(long pointer) {
        return new L1Cursor(pointer);
    }

    private L1Cursor(long pointer) {
        super(pointer);
    }

    public void destroy() {
        glfwDestroyCursor(address());
    }

}
