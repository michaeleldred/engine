package viewcontrollers;

import java.nio.Buffer;
import java.nio.IntBuffer;

import org.robovm.rt.bro.Bro;
import org.robovm.rt.bro.Struct;
import org.robovm.rt.bro.annotation.Bridge;
import org.robovm.rt.bro.annotation.Library;
import org.robovm.rt.bro.ptr.BytePtr;
import org.robovm.rt.bro.ptr.BytePtr.BytePtrPtr;
import org.robovm.rt.bro.ptr.IntPtr;

@Library("OpenGLES")
public class GLES20 {

    public static final int GL_DEPTH_BUFFER_BIT = 0x00000100;
    public static final int GL_STENCIL_BUFFER_BIT = 0x00000400;
    public static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    public static final int GL_FALSE = 0;
    public static final int GL_TRUE = 1;

    private static final int MAX_INFO_LOG_LENGTH = 10*1024;

    private static final ThreadLocal<IntPtr> SINGLE_VALUE =
        new ThreadLocal<IntPtr>() {
            @Override
            protected IntPtr initialValue() {
                return Struct.allocate(IntPtr.class, 1);
            }
        };

    private static final ThreadLocal<BytePtr> INFO_LOG =
        new ThreadLocal<BytePtr>() {
            @Override
            protected BytePtr initialValue() {
                return Struct.allocate(BytePtr.class, MAX_INFO_LOG_LENGTH);
            }
        };

    static {
        Bro.bind(GLES20.class);
    }

    @Bridge
    public static native void glClearColor(float red, float green, float blue, float alpha);

    @Bridge
    public static native void glClear(int mask);

    @Bridge
    public static native void glGetIntegerv(int pname, IntPtr params);

    // DO NOT CALL THE NEXT METHOD WITH A pname THAT RETURNS MORE THAN ONE VALUE!!!
    public static int glGetIntegerv(int pname) {
        IntPtr params = SINGLE_VALUE.get();
        glGetIntegerv(pname, params);
        return params.get();
    }

    @Bridge
    private static native int glGetUniformLocation(int program, BytePtr name);

    public static int glGetUniformLocation(int program, String name) {
        return glGetUniformLocation(program, BytePtr.toBytePtrAsciiZ(name));
    }

    @Bridge
    public static native int glGenFramebuffers(int n, IntPtr framebuffers);

    public static int glGenFramebuffer() {
        IntPtr framebuffers = SINGLE_VALUE.get();
        glGenFramebuffers(1, framebuffers);
        return framebuffers.get();
    }

    @Bridge
    private static native void glShaderSource(int shader, int count, BytePtrPtr string, IntPtr length);

    public static void glShaderSource(int shader, String code) {
        glShaderSource(shader, 1, new BytePtrPtr().set(BytePtr.toBytePtrAsciiZ(code)), null);
    }

    @Bridge
    private static native void glGetShaderInfoLog(int shader, int maxLength, IntPtr length, BytePtr infoLog);

    public static String glGetShaderInfoLog(int shader) {
        BytePtr infoLog = INFO_LOG.get(); 
        glGetShaderInfoLog(shader, MAX_INFO_LOG_LENGTH, null, infoLog);
        return infoLog.toStringAsciiZ();
    }

    @Bridge
    public static native void glGetShaderPrecisionFormat(int shaderType, int precisionType, IntBuffer range, IntBuffer precision);

    @Bridge
    public static native void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer data);

    @Bridge
    private static native void glVertexAttribPointer(int index, int size, int type, int normalized, int stride, Buffer pointer);

    public static void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, Buffer pointer) {
        glVertexAttribPointer(index, size, type, normalized ? GL_TRUE : GL_FALSE, stride, pointer);
    }
}