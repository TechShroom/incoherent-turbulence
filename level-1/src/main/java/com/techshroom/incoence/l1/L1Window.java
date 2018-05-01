package com.techshroom.incoence.l1;

import static com.techshroom.incoence.l1.GLFWUtil.glfwError;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;
import static org.lwjgl.glfw.GLFW.glfwGetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwGetWindowContentScale;
import static org.lwjgl.glfw.GLFW.glfwGetWindowFrameSize;
import static org.lwjgl.glfw.GLFW.glfwGetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetWindowOpacity;
import static org.lwjgl.glfw.GLFW.glfwGetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwHideWindow;
import static org.lwjgl.glfw.GLFW.glfwIconifyWindow;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwMaximizeWindow;
import static org.lwjgl.glfw.GLFW.glfwRequestWindowAttention;
import static org.lwjgl.glfw.GLFW.glfwRestoreWindow;
import static org.lwjgl.glfw.GLFW.glfwSetClipboardString;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAspectRatio;
import static org.lwjgl.glfw.GLFW.glfwSetWindowAttrib;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIcon;
import static org.lwjgl.glfw.GLFW.glfwSetWindowMonitor;
import static org.lwjgl.glfw.GLFW.glfwSetWindowOpacity;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeLimits;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.annotation.Nullable;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Pointer;

import com.google.common.collect.ImmutableList;

public final class L1Window extends Pointer.Default {

    // ensure GLFWUtil initialized for error handling
    static {
        GLFWUtil.init();
    }

    /**
     * Create a window from the parameters.
     * 
     * <p>
     * This resets all window hints to {@linkplain GLFW#glfwDefaultWindowHints()
     * default}, then applies all {@linkplain L1WindowParameters#windowHints()
     * window hints}. All other parameters are passed to
     * {@link GLFW#glfwCreateWindow(int, int, CharSequence, long, long)
     * glfwCreateWindow}.
     * </p>
     * 
     * @param params
     *            - the window parameters
     * @return the new window
     * @see GLFW#glfwDefaultWindowHints()
     * @see GLFW#glfwWindowHint(int, int)
     * @see GLFW#glfwCreateWindow(int, int, CharSequence, long, long)
     */
    public static L1Window create(L1WindowParameters params) {
        glfwDefaultWindowHints();
        params.windowHints().forEachKeyValue(GLFW::glfwWindowHint);
        long pointer = glfwCreateWindow(params.width(), params.height(), params.title(), params.monitor(), params.share());
        if (pointer == MemoryUtil.NULL) {
            throw glfwError();
        }
        return wrap(pointer);
    }

    /**
     * Get the {@linkplain GLFW#glfwGetCurrentContext() current context}, and
     * {@link #wrap(long) wrap} it.
     * 
     * @return the wrapper for the current context
     * @see GLFW#glfwGetCurrentContext()
     */
    public static L1Window wrapCurrentContext() {
        return wrap(glfwGetCurrentContext());
    }

    /**
     * Provides the wrapper for the given pointer.
     * 
     * @param pointer
     *            - the window pointer to wrap
     * @return the wrapper
     */
    public static L1Window wrap(long pointer) {
        return new L1Window(pointer);
    }

    private L1Window(long pointer) {
        super(pointer);
    }

    /**
     * @see GLFW#glfwDestroyWindow(long)
     */
    public void destroy() {
        glfwDestroyWindow(address());
    }

    /**
     * @see GLFW#glfwWindowShouldClose(long)
     */
    public boolean isCloseRequested() {
        return glfwWindowShouldClose(address());
    }

    /**
     * @see GLFW#glfwSetWindowShouldClose(long, boolean)
     */
    public void setCloseRequested(boolean requested) {
        glfwSetWindowShouldClose(address(), requested);
    }

    /**
     * @see GLFW#glfwSetWindowTitle(long, CharSequence)
     */
    public void setTitle(CharSequence title) {
        glfwSetWindowTitle(address(), title);
    }

    /**
     * @see GLFW#glfwSetWindowIcon(long, org.lwjgl.glfw.GLFWImage.Buffer)
     */
    public void setIcon(GLFWImage.Buffer images) {
        glfwSetWindowIcon(address(), images);
    }

    /**
     * @see GLFW#glfwGetWindowPos(long, IntBuffer, IntBuffer)
     */
    public void getPos(@Nullable IntBuffer xpos, @Nullable IntBuffer ypos) {
        glfwGetWindowPos(address(), xpos, ypos);
    }

    /**
     * @see GLFW#glfwSetWindowPos(long, int, int)
     */
    public void setPos(int xpos, int ypos) {
        glfwSetWindowPos(address(), xpos, ypos);
    }

    /**
     * @see GLFW#glfwGetWindowSize(long, IntBuffer, IntBuffer)
     */
    public void getSize(@Nullable IntBuffer width, @Nullable IntBuffer height) {
        glfwGetWindowSize(address(), width, height);
    }

    /**
     * @see GLFW#glfwSetWindowSize(long, int, int)
     */
    public void setSize(int width, int height) {
        glfwSetWindowSize(address(), width, height);
    }

    /**
     * @see GLFW#glfwSetWindowSizeLimits(long, int, int, int, int)
     */
    public void setSizeLimits(int minWidth, int minHeight, int maxWidth, int maxHeight) {
        glfwSetWindowSizeLimits(address(), minWidth, minHeight, maxWidth, maxHeight);
    }

    /**
     * @see GLFW#glfwSetWindowAspectRatio(long, int, int)
     */
    public void setAspectRatio(int numerator, int denominator) {
        glfwSetWindowAspectRatio(address(), numerator, denominator);
    }

    /**
     * @see GLFW#glfwGetFramebufferSize(long, IntBuffer, IntBuffer)
     */
    public void getFramebufferSize(@Nullable IntBuffer width, @Nullable IntBuffer height) {
        glfwGetFramebufferSize(address(), width, height);
    }

    /**
     * @see GLFW#glfwGetWindowFrameSize(long, IntBuffer, IntBuffer, IntBuffer,
     *      IntBuffer)
     */
    public void getFrameSize(@Nullable IntBuffer left, @Nullable IntBuffer top, @Nullable IntBuffer right, @Nullable IntBuffer bottom) {
        glfwGetWindowFrameSize(address(), left, top, right, bottom);
    }

    /**
     * @see GLFW#glfwGetWindowContentScale(long, FloatBuffer, FloatBuffer)
     */
    public void getContentScale(@Nullable FloatBuffer xscale, @Nullable FloatBuffer yscale) {
        glfwGetWindowContentScale(address(), xscale, yscale);
    }

    /**
     * @see GLFW#glfwGetWindowOpacity(long)
     */
    public float getOpacity() {
        return glfwGetWindowOpacity(address());
    }

    /**
     * @see GLFW#glfwSetWindowOpacity(long, float)
     */
    public void setOpacity(float opacity) {
        glfwSetWindowOpacity(address(), opacity);
    }

    /**
     * @see GLFW#glfwIconifyWindow(long)
     */
    public void iconify() {
        glfwIconifyWindow(address());
    }

    /**
     * @see GLFW#glfwRestoreWindow(long)
     */
    public void restore() {
        glfwRestoreWindow(address());
    }

    /**
     * @see GLFW#glfwMaximizeWindow(long)
     */
    public void maximize() {
        glfwMaximizeWindow(address());
    }

    /**
     * @see GLFW#glfwShowWindow(long)
     */
    public void show() {
        glfwShowWindow(address());
    }

    /**
     * @see GLFW#glfwHideWindow(long)
     */
    public void hide() {
        glfwHideWindow(address());
    }

    /**
     * @see GLFW#glfwFocusWindow(long)
     */
    public void focus() {
        glfwFocusWindow(address());
    }

    /**
     * @see GLFW#glfwRequestWindowAttention(long)
     */
    public void requestAttention() {
        glfwRequestWindowAttention(address());
    }

    /**
     * @see GLFW#glfwGetWindowMonitor(long)
     */
    public long getMonitor() {
        return glfwGetWindowMonitor(address());
    }

    /**
     * @see GLFW#glfwSetWindowMonitor(long, long, int, int, int, int, int)
     */
    public void setMonitor(@NativeType("GLFWmonitor *") long monitor, int xpos, int ypos, int width, int height, int refreshRate) {
        glfwSetWindowMonitor(address(), monitor, xpos, ypos, width, height, refreshRate);
    }

    /**
     * @see GLFW#glfwGetWindowAttrib(long, int)
     */
    public int getAttribute(int attribute) {
        return glfwGetWindowAttrib(address(), attribute);
    }

    /**
     * @see GLFW#glfwSetWindowAttrib(long, int, int)
     */
    public void setAttribute(int attribute, int value) {
        glfwSetWindowAttrib(address(), attribute, value);
    }

    private interface GLFWSetCallback<G> {

        void accept(long window, G cb);
    }

    private <L, G> void setListener(@Nullable L listener, G glfwListenener, GLFWSetCallback<G> set) {
        G cb = listener == null ? null : glfwListenener;
        set.accept(address(), cb);
    }

    /**
     * @see GLFW#glfwSetWindowPosCallback(long,
     *      org.lwjgl.glfw.GLFWWindowPosCallbackI)
     */
    public void setPositionListener(@Nullable L1PositionListener listener) {
        setListener(listener, (win, x, y) -> listener.onPositionChanged(this, x, y), GLFW::glfwSetWindowPosCallback);
    }

    /**
     * @see GLFW#glfwSetWindowSizeCallback(long,
     *      org.lwjgl.glfw.GLFWWindowSizeCallbackI)
     */
    public void setSizeListener(@Nullable L1SizeListener listener) {
        setListener(listener, (win, width, height) -> listener.onSizeChanged(this, width, height), GLFW::glfwSetWindowSizeCallback);
    }

    /**
     * @see GLFW#glfwSetWindowCloseCallback(long,
     *      org.lwjgl.glfw.GLFWWindowCloseCallbackI)
     */
    public void setCloseListener(@Nullable L1CloseListener listener) {
        setListener(listener, (win) -> listener.onClose(this), GLFW::glfwSetWindowCloseCallback);
    }

    /**
     * @see GLFW#glfwSetWindowRefreshCallback(long,
     *      org.lwjgl.glfw.GLFWWindowRefreshCallbackI)
     */
    public void setRefreshListener(@Nullable L1RefreshListener listener) {
        setListener(listener, (win) -> listener.onRefresh(this), GLFW::glfwSetWindowRefreshCallback);
    }

    /**
     * @see GLFW#glfwSetWindowFocusCallback(long,
     *      org.lwjgl.glfw.GLFWWindowFocusCallbackI)
     */
    public void setFocusListener(@Nullable L1FocusListener listener) {
        setListener(listener, (win, focused) -> listener.onFocusChanged(this, focused), GLFW::glfwSetWindowFocusCallback);
    }

    /**
     * @see GLFW#glfwSetWindowIconifyCallback(long,
     *      org.lwjgl.glfw.GLFWWindowIconifyCallbackI)
     */
    public void setIconifyListener(@Nullable L1IconifyListener listener) {
        setListener(listener, (win, iconified) -> listener.onIconifyChanged(this, iconified), GLFW::glfwSetWindowIconifyCallback);
    }

    /**
     * @see GLFW#glfwSetWindowMaximizeCallback(long,
     *      org.lwjgl.glfw.GLFWWindowMaximizeCallbackI)
     */
    public void setMaximizeListener(@Nullable L1MaximizeListener listener) {
        setListener(listener, (win, maximized) -> listener.onMaximizeChanged(this, maximized), GLFW::glfwSetWindowMaximizeCallback);
    }

    /**
     * @see GLFW#glfwSetFramebufferSizeCallback(long,
     *      org.lwjgl.glfw.GLFWFramebufferSizeCallbackI)
     */
    public void setFramebufferSizeListener(@Nullable L1FramebufferSizeListener listener) {
        setListener(listener, (win, width, height) -> listener.onFramebufferSizeChanged(this, width, height), GLFW::glfwSetFramebufferSizeCallback);
    }

    /**
     * @see GLFW#glfwSetWindowContentScaleCallback(long,
     *      org.lwjgl.glfw.GLFWWindowContentScaleCallbackI)
     */
    public void setContentScaleListener(@Nullable L1ContentScaleListener listener) {
        setListener(listener, (win, xScale, yScale) -> listener.onContentScaleChanged(this, xScale, yScale), GLFW::glfwSetWindowContentScaleCallback);
    }

    /**
     * @see GLFW#glfwGetInputMode(long, int)
     */
    public int getInputMode(int mode) {
        return glfwGetInputMode(address(), mode);
    }

    /**
     * @see GLFW#glfwSetInputMode(long, int, int)
     */
    public void setInputMode(int mode, int value) {
        glfwSetInputMode(address(), mode, value);
    }

    /**
     * @see GLFW#glfwGetKey(long, int)
     */
    public int getKey(int key) {
        return glfwGetKey(address(), key);
    }

    /**
     * @see GLFW#glfwGetMouseButton(long, int)
     */
    public int getMouseButton(int button) {
        return glfwGetMouseButton(address(), button);
    }

    /**
     * @see GLFW#glfwGetCursorPos(long, DoubleBuffer, DoubleBuffer)
     */
    public void getCursorPos(DoubleBuffer x, DoubleBuffer y) {
        glfwGetCursorPos(address(), x, y);
    }

    /**
     * @see GLFW#glfwSetCursorPos(long, double, double)
     */
    public void setCursorPos(double x, double y) {
        glfwSetCursorPos(address(), x, y);
    }

    /**
     * @see GLFW#glfwSetCursor(long, long)
     */
    public void setCursor(L1Cursor cursor) {
        glfwSetCursor(address(), cursor.address());
    }

    /**
     * @see GLFW#glfwSetKeyCallback(long, org.lwjgl.glfw.GLFWKeyCallbackI)
     */
    public void setKeyListener(@Nullable L1KeyListener listener) {
        setListener(listener, (win, key, scancode, action, mods) -> listener.onKey(this, key, scancode, action, mods), GLFW::glfwSetKeyCallback);
    }

    /**
     * @see GLFW#glfwSetCharCallback(long, org.lwjgl.glfw.GLFWCharCallbackI)
     */
    public void setCharListener(@Nullable L1CharListener listener) {
        setListener(listener, (win, codepoint) -> listener.onChar(this, codepoint), GLFW::glfwSetCharCallback);
    }

    /**
     * @see GLFW#glfwSetMouseButtonCallback(long,
     *      org.lwjgl.glfw.GLFWMouseButtonCallbackI)
     */
    public void setMouseButtonListener(@Nullable L1MouseButtonListener listener) {
        setListener(listener, (win, button, action, mods) -> listener.onMouseButton(this, button, action, mods), GLFW::glfwSetMouseButtonCallback);
    }

    /**
     * @see GLFW#glfwSetCursorPosCallback(long,
     *      org.lwjgl.glfw.GLFWCursorPosCallbackI)
     */
    public void setCursorPosListener(@Nullable L1CursorPosListener listener) {
        setListener(listener, (win, x, y) -> listener.onCursorPosChanged(this, x, y), GLFW::glfwSetCursorPosCallback);
    }

    /**
     * @see GLFW#glfwSetCursorEnterCallback(long,
     *      org.lwjgl.glfw.GLFWCursorEnterCallbackI)
     */
    public void setCursorInsideListener(@Nullable L1CursorInsideListener listener) {
        setListener(listener, (win, entered) -> listener.onCursorInsideChanged(this, entered), GLFW::glfwSetCursorEnterCallback);
    }

    /**
     * @see GLFW#glfwSetScrollCallback(long, org.lwjgl.glfw.GLFWScrollCallbackI)
     */
    public void setScrollListener(@Nullable L1ScrollListener listener) {
        setListener(listener, (win, dx, dy) -> listener.onScroll(this, dx, dy), GLFW::glfwSetScrollCallback);
    }

    /**
     * @see GLFW#glfwSetDropCallback(long, org.lwjgl.glfw.GLFWDropCallbackI)
     */
    public void setFileDropListener(@Nullable L1FileDropListener listener) {
        setListener(listener, (win, count, names) -> {
            // Extract names for convinence
            ImmutableList.Builder<String> namesList = ImmutableList.builder();
            for (int i = 0; i < count; i++) {
                namesList.add(GLFWDropCallback.getName(names, i));
            }
            listener.onFileDrop(this, namesList.build());
        }, GLFW::glfwSetDropCallback);
    }

    /**
     * @see GLFW#glfwSetClipboardString(long, CharSequence)
     */
    public void setClipboardString(String string) {
        glfwSetClipboardString(address(), string);
    }

    /**
     * @see GLFW#glfwGetClipboardString(long)
     */
    public String getClipboardString() {
        return glfwGetClipboardString(address());
    }

    /**
     * @see GLFW#glfwMakeContextCurrent(long)
     */
    public void makeContextCurrent() {
        glfwMakeContextCurrent(address());
    }

    /**
     * @see GLFW#glfwSwapBuffers(long)
     */
    public void swapBuffers() {
        glfwSwapBuffers(address());
    }

    /**
     * Note: this function calls {@link #makeContextCurrent()}.
     * 
     * @see GLFW#glfwSwapInterval(int)
     */
    public void setSwapInterval(int interval) {
        makeContextCurrent();
        glfwSwapInterval(interval);
    }
}
