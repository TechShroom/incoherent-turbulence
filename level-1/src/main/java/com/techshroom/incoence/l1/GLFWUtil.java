package com.techshroom.incoence.l1;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import java.io.PrintStream;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techshroom.incoence.l1.log.LineOutputStream;

public class GLFWUtil {

    private static GLFWErrorCallback errorCallback;

    public static void init() {
        Logger glfwErrorLogger = LoggerFactory.getLogger("glfw.error");
        PrintStream loggingStream = new PrintStream(new LineOutputStream(glfwErrorLogger::error));
        errorCallback = GLFWErrorCallback.createPrint(loggingStream).set();

        if (!glfwInit()) {
            throw glfwError();
        }
    }

    public static RuntimeException glfwError() {
        // the actual error is printed automatically
        return new RuntimeException("GLFW error occurred, please check your logs!");
    }

    public static void terminate() {
        glfwTerminate();
        if (errorCallback != null) {
            errorCallback.free();
            errorCallback = null;
        }
    }

    private GLFWUtil() {
    }

}
