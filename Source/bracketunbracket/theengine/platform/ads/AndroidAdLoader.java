package bracketunbracket.theengine.platform.ads;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author Michael Eldred
 */
public class AndroidAdLoader implements AdLoader {

	private final RelativeLayout child;
	public final String adString = "ca-app-pub-3940256099942544/6300978111";
	private AdView adView;
	private RelativeLayout parent;
	
	public AndroidAdLoader( Activity activity , RelativeLayout layout  ) {
		MobileAds.initialize( activity , adString );
		
		adView = new AdView( activity );
		adView.setAdSize( AdSize.SMART_BANNER );
		adView.setAdUnitId( adString );
		
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.FILL_PARENT, 
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		lp.addRule( RelativeLayout.ALIGN_PARENT_BOTTOM , RelativeLayout.TRUE );
		lp.addRule( RelativeLayout.CENTER_HORIZONTAL , RelativeLayout.TRUE );
		
		child = new RelativeLayout( activity );
		child.addView( adView , lp );
		this.parent = layout;
		parent.addView( child );
		adView.setVisibility( View.INVISIBLE );
	}
	
	@Override
	public void load() {
		AdRequest request = new AdRequest.Builder().build();
		adView.loadAd( request );
	}
	
	@Override
	public void showAd() {
		adView.setVisibility( View.VISIBLE );
	}

	@Override
	public void hideAd() {
		adView.setVisibility( View.INVISIBLE );
	}

}
