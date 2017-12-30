package bracketunbracket.theengine.resources;

import org.teavm.jso.ajax.ReadyStateChangeHandler;
import org.teavm.jso.ajax.XMLHttpRequest;

/**
 * @author Michael
 */
public class WebFilePlatform implements FilePlatform {

	public class WebTextData extends TextData implements ReadyStateChangeHandler {
		
		private XMLHttpRequest request;
		
		public WebTextData( String filename ) {
			request = XMLHttpRequest.create();
			request.open( "GET" , filename );
			request.setOnReadyStateChange( this );
			request.send();
		}

		@Override
		public void stateChanged() {
			if( request.getReadyState() == XMLHttpRequest.DONE ) {
				this.data = request.getResponseText();
				finished();
			}
		}
	}
	
	/**
	 * @see bracketunbracket.theengine.resources.FilePlatform#getTextData()
	 */
	@Override
	public TextData getTextData( String filename ) {
		return new WebTextData( filename );
	}

}
