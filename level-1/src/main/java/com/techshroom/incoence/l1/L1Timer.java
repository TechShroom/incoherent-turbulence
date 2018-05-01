package com.techshroom.incoence.l1;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetTimerFrequency;
import static org.lwjgl.glfw.GLFW.glfwGetTimerValue;
import static org.lwjgl.glfw.GLFW.glfwSetTime;

import org.lwjgl.glfw.GLFW;

public class L1Timer {

    /**
     * @see GLFW#glfwGetTime()
     */
    public static double getTime() {
        return glfwGetTime();
    }

    /**
     * @see GLFW#glfwSetTime(double)
     */
    public static void setTime(double time) {
        glfwSetTime(time);
    }

    /**
     * @see GLFW#glfwGetTimerValue()
     */
    public static long getValue() {
        return glfwGetTimerValue();
    }

    /**
     * @see GLFW#glfwGetTimerFrequency()
     */
    public static long getFrequency() {
        return glfwGetTimerFrequency();
    }

}
