/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class LWJGLRenderContext extends RenderContext {

	private Map<String,LWJGLTexture> textures = new HashMap<String, LWJGLTexture>();
	
	public final long window;
	private GameWindow gameWindow;
	
	private final static int MAX_BATCH_SPRITES = RenderCommand.MAX_OBJECTS;
	
	private LWJGLShader progTex;
	private LWJGLShader progNoTex;
	
	private Vector2 offset = new Vector2( 0 , 0 );
	
	private float winwidth;
	private float winheight;
	
	@SuppressWarnings("unused")
	private GLFWWindowSizeCallback callback;

	public LWJGLRenderContext( GameWindow gameWin , float winwidth , float winheight ) {
		this.winwidth = winwidth;
		this.winheight = winheight;
		
		glfwSetErrorCallback( GLFWErrorCallback.createPrint( System.err ) );
		
		glfwInit();
		glfwDefaultWindowHints();
		
		window = glfwCreateWindow( (int)winwidth , (int)winheight , "TEST" , NULL , NULL );
		
		if( window == NULL ) {
			throw new RuntimeException( "window == NULL" );
		}
		
		glfwSetWindowPos( window , 20 , 50 );
		
		glfwMakeContextCurrent( window );
		glfwSwapInterval( 1 );
		glfwShowWindow( window );
		glfwSetWindowSizeCallback( window , callback = new GLFWWindowSizeCallback() {
			
			@Override
			public void invoke( long window , int width , int height ) {
				// TODO: Make this work
				// gameWindow.resize( width , height );
			}
		});
		
		GL.createCapabilities();
		
		
		this.gameWindow = gameWin;
		gameWin.resize( winwidth , winheight );
		
		Vector2 dim = gameWindow.getScaledDimensions();
		
		float width = dim.x;
		float height = dim.y;
		
		//System.out.println( "( " + width + " , " + height + " ) " );
		//System.out.println( ( width / height ) + " = " + ((float)winwidth/(float)winheight));
		
		glOrtho( -(width/2) , (width/2) , (height/2) , -(height/2) , -1 , 1 );
		glDisable( GL_DEPTH_TEST );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA , GL_ONE_MINUS_SRC_ALPHA );
		glClearColor( 1.0f , 1.0f , 1.0f , 1.0f );
		
		progTex = new LWJGLShader( GLRenderer.vertexShaderCode , GLRenderer.fragmentShaderCode );
		
		progNoTex = new LWJGLShader( GLRenderer.vertexShaderCode , GLRenderer.fragmentNoTexShaderCode );
		
		for( LWJGLTexture tex : textures.values() ) {
			tex.load();
		}
	}
	
	public GameWindow getWindow() {
		return gameWindow;
	}

	@Override
	public void execute( RenderCommand command ) {
		float vertData[] = new float[ 12 * MAX_BATCH_SPRITES ];
		float colorData[] = new float[ 24 * MAX_BATCH_SPRITES ];
		float texData[] = new float[ 12 * MAX_BATCH_SPRITES ];
		float rotData[] = new float[ 18 * MAX_BATCH_SPRITES ];
		
		float w,h;
		
		int vert = 0;
		int color = 0;
		int tex = 0;
		int rot = 0;
		
		FloatBuffer buffer = getOrtho();
		
		for( int i = 0; i < command.getObjects().size(); i++ ) {
			
			RenderObject current = command.getObjects().get( i );
			
			Vector2 pos = current.getAbsolutePosition();
			
			float r,g,b,a;
			r = current.getColor().red;
			g = current.getColor().green;
			b = current.getColor().blue;
			a = current.getColor().alpha;
			
			Image image = current.image;
			
			float rotation = (float)Math.toRadians( current.rotation );
			
			// Get the width and height of the new object
			if( image != null ) {
				w = image.w / 2.0f;
				h = image.h / 2.0f;
			} else {
				w = current.width / 2.0f;
				h = current.height / 2.0f;
			}
			
			// Set the scale
			w *= current.scale;
			h *= current.scale;
			
			vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y + h;
			vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y - h;
			vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y - h;
			
			vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y + h;
			vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y - h;
			vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y + h;
			
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
			
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
			
			if( image != null ) {
				texData[ tex++ ] = image.x1; texData[ tex++ ] = image.y2;
				texData[ tex++ ] = image.x1; texData[ tex++ ] = image.y1;
				texData[ tex++ ] = image.x2; texData[ tex++ ] = image.y1;
				
				texData[ tex++ ] = image.x1; texData[ tex++ ] = image.y2;
				texData[ tex++ ] = image.x2; texData[ tex++ ] = image.y1;
				texData[ tex++ ] = image.x2; texData[ tex++ ] = image.y2;
			}
		}
		
		// Send the data to opengl
		FloatBuffer vertBuffer = ByteBuffer.allocateDirect( vertData.length * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		vertBuffer.put( vertData );
		vertBuffer.position( 0 );
		
		FloatBuffer colorBuffer = ByteBuffer.allocateDirect( colorData.length * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		colorBuffer.put( colorData );
		colorBuffer.position( 0 );
		
		FloatBuffer texBuffer = ByteBuffer.allocateDirect( texData.length * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		texBuffer.put( texData );
		texBuffer.position( 0 );
		
		FloatBuffer rotBuffer = ByteBuffer.allocateDirect( rotData.length * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		rotBuffer.put( rotData );
		rotBuffer.position( 0 );
		
		LWJGLShader prog = progNoTex;
		
		
		if( command.texture != null ) {
			LWJGLTexture t = (LWJGLTexture) command.texture;
			
			glActiveTexture( GL_TEXTURE0 );
			glBindTexture( GL_TEXTURE_2D , t.texID );
			prog = progTex;
		}
		
		if( command.shader != null ) {
			prog = (LWJGLShader)command.shader;
		}
		
		if( !prog.loaded ) {
			prog.load();
		}
		
		glUseProgram( prog.program );
		
		int loc = glGetUniformLocation( prog.program , "ortho" );
		glUniformMatrix4fv( loc , false , buffer );
		
		loc = glGetUniformLocation( prog.program , "screen" );
		glUniform2f( loc , winwidth , winheight ); 
		
		int verthandle = glGetAttribLocation( prog.program , "pos" );
		glEnableVertexAttribArray( verthandle );
		glVertexAttribPointer( verthandle , 2 , GL_FLOAT , false , 8 , vertBuffer );
		
		int colorHandle = glGetAttribLocation( prog.program , "color" );
		glEnableVertexAttribArray( colorHandle );
		glVertexAttribPointer( colorHandle , 4 , GL_FLOAT , false , 16 , colorBuffer );
		
		int rotHandle = glGetAttribLocation( prog.program , "rot" );
		glEnableVertexAttribArray( rotHandle );
		glVertexAttribPointer( rotHandle , 3 , GL_FLOAT , false , 12 , rotBuffer );
		
		int texHandle = glGetAttribLocation( prog.program , "texCoord" );
		glEnableVertexAttribArray( texHandle );
		glVertexAttribPointer( texHandle , 2 , GL_FLOAT , false , 8 , texBuffer );
		
		int texLoc = glGetUniformLocation( prog.program , "img" );
		
		// Check to see if the location is found
		if( texLoc >= 0 )
			glUniform1i( texLoc , 0 );
		
		glDrawArrays( GL_TRIANGLES , 0 , command.getObjects().size() * 6 );
		
		glDisableVertexAttribArray( texHandle );
		glDisableVertexAttribArray( rotHandle );
		glDisableVertexAttribArray( colorHandle );
		glDisableVertexAttribArray( verthandle );
		
	}
	
	@Override
	public void render() {
		
		glfwSwapBuffers( window );
		
		glfwPollEvents();
		
		glClear( GL_COLOR_BUFFER_BIT );
	}

	public void destroy() {
		glfwDestroyWindow( window );
		glfwTerminate();
	}

	@Override
	public Texture create( String filename ) {
		if( textures.containsKey( filename ) ) {
			return textures.get( filename );
		}
		
		LWJGLTexture t = new LWJGLTexture( filename );
		t.load();
		textures.put( filename , t );
		
		return t;
	}
	
	public FloatBuffer getOrtho() {
		
		Vector2 dim = gameWindow.getScaledDimensions();
		float zFar = -1.0f;
		float zNear = 1.0f;
		
		float[] ortho = new float[ 16 ];
		float ri = dim.x / 2.0f + offset.x;
		float le = -dim.x / 2.0f - offset.x;
		
		float t = dim.y / 2.0f + offset.y;
		float b = -dim.y / 2.0f - offset.y;
		
		ortho[ 0 ] = 2.0f / ( ri - le );
		ortho[ 3 ] = -( ri + le ) / ( ri - le );
		ortho[ 5 ] = -2.0f / ( t - b ) ;
		ortho[ 7 ] = - ( t + b ) / ( t - b );
		ortho[ 10 ] = 2.0f / ( zFar - zNear );
		ortho[ 11 ] = ( zNear + zFar ) / ( zNear - zFar );
		ortho[ 15 ] = 1.0f;
		
		FloatBuffer buffer = ByteBuffer.allocateDirect( 4 * 16 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		buffer.put( ortho );
		buffer.position( 0 );
		
		return buffer;
	}

	@Override
	public void setOffset(Vector2 offset) {
		this.offset.x = offset.x;
		this.offset.y = offset.y;
	}

	@Override
	public Shader newShader( String vert , String frag ) {
		return new LWJGLShader( vert , frag );
	}
}
