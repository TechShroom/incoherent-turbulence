package com.techshroom.incoence.l1;

import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwPostEmptyEvent;
import static org.lwjgl.glfw.GLFW.glfwWaitEvents;
import static org.lwjgl.glfw.GLFW.glfwWaitEventsTimeout;

import org.lwjgl.glfw.GLFW;

public class L1Events {

    /**
     * @see GLFW#glfwPollEvents()
     */
    public static void poll() {
        glfwPollEvents();
    }

    /**
     * @see GLFW#glfwWaitEvents()
     */
    public static void waitFor() {
        glfwWaitEvents();
    }

    /**
     * @see GLFW#glfwWaitEventsTimeout(double)
     */
    public static void waitFor(double timeout) {
        glfwWaitEventsTimeout(timeout);
    }

    /**
     * @see GLFW#glfwPostEmptyEvent()
     */
    public static void postEmpty() {
        glfwPostEmptyEvent();
    }

}
