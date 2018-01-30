package bracketunbracket.theengine.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.renderer.Renderer;
import bracketunbracket.theengine.renderer.Shader;

/**
 * @author Michael
 */
public class ShaderParser extends ResourceParser {
	private final Renderer renderer;
	
	private HashMap< String , ShaderPartResource > shaders = new HashMap< String , ShaderPartResource >();
	
	private class ShaderPartResource extends Resource implements EventListener {
		
		private List<EventListener> listeners = new ArrayList<EventListener>();
		
		public String data;
		
		public ShaderPartResource( HashMap<String, String> values ) {
			super(values);
			FileSystem.loadData( "Shaders/" + values.get( "filename" ) , this );
		}

		@Override
		public void receive(Event event) {
			Data data = null;
			
			if( event instanceof DataHandledEvent ) {
				data = ((DataHandledEvent)event).data;
			} else {
				return;
			}
			
			this.data = ((TextData)data).getData();
			
			// Create a shader
			for( EventListener listener : listeners ) {
				listener.receive( new ShaderHandledEvent( values.get( "type" ) , data ) );
			}
			finished();
		}
		
		public void addEventListener( EventListener listener ) {
			listeners.add( listener );
		}
	}
	
	private class FinishedShaderResource extends Resource implements EventListener {

		private String vertText;
		private String fragText;
		
		public FinishedShaderResource(HashMap<String, String> values , ShaderPartResource vert , ShaderPartResource frag ) {
			super(values);
			
			// If the resources have already been loaded, just use the cached
			// data. Otherwise. Wait for the load event.
			if( vert.isLoaded )
				this.vertText = vert.data;
			else
				vert.addEventListener( this );
			
			if( frag.isLoaded )
				this.fragText = frag.data;
			else
				frag.addEventListener( this );
		}

		@Override
		public void receive(Event event) {
			ShaderHandledEvent shader = (ShaderHandledEvent)event;
			if( shader.type.equals( "frag" ) ) {
				fragText = ((TextData)shader.data).getData();
			}
			
			if( shader.type.equals( "vert" ) ) {
				vertText = ((TextData)shader.data).getData();
			}
			
			if( vertText != null && fragText != null ) {
				Shader s = renderer.context.newShader( vertText , fragText );
				s.load();
				
				System.out.println( "Loaded: " + values.get( "name" ) );
				renderer.shaders.put( values.get( "name" ) , s );
				finished();
			}
		}
		
	}
	public ShaderParser( Renderer context ) {
		super( "vert" , "frag" , "shader" );
		this.renderer = context;
	}
	
	@Override
	public Resource load(HashMap<String, String> values) {
		
		if( values.get( "type" ).equalsIgnoreCase( "shader" ) ) {
			ShaderPartResource vert = shaders.get( values.get( "vert" ) );
			ShaderPartResource frag = shaders.get( values.get( "frag" ) );
			return new FinishedShaderResource( values , vert , frag );
		}
			
		ShaderPartResource returnVal = new ShaderPartResource( values );
		shaders.put( values.get( "name" ), returnVal );
		return returnVal;
			
	}

}

class ShaderHandledEvent extends DataHandledEvent {

	public final String type;
	
	public ShaderHandledEvent( String type , Data data) {
		super(data);
		this.type = type;
	}
	
}
