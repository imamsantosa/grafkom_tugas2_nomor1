package com.ligthing;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen.
 * This view can also be used to capture touch events, such as a user
 * interacting with drawn objects.
 */
public class ESSurfaceView extends GLSurfaceView {

    private final ESRender esRender;
	private float previousX;
	private float previousY;
	private float saklar;

    public ESSurfaceView(Context context) {
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        esRender = new ESRender(context);
        setRenderer(esRender);
        
        // To enable keypad
		this.setFocusable(true);
		this.requestFocus();
		
		// To enable touch mode
		this.setFocusableInTouchMode(true);

        // Render the view only when there is a change in the drawing data
        // merender hanya ketika ada perubahan/ event
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    //private final float TOUCH_SCALE_FACTOR = getWidth() / getHeight();
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent v) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

    	float currentX = v.getX();
    	float currentY = v.getY();
 		float deltaX, deltaY;

        switch (v.getAction()) {
        	case MotionEvent.ACTION_DOWN:
        		Log.v("Test Action Down", "action down working");
        		
        		if(esRender.getLamp()){
        			esRender.setOff();
        		}else{
        			esRender.setOn();
        		}
        		
        		break;
            case MotionEvent.ACTION_MOVE:
            	// Modify rotational angles according to movement
    			deltaX = currentX - previousX;
    			deltaY = currentY - previousY;
    			
    			//esRender.setspeedX(esRender.getspeedX()+ (deltaX/getWidth()));
    			//esRender.setspeedY(esRender.getspeedY()+ (deltaY/getHeight()));
    			
    			esRender.setspeedX(esRender.getspeedX()+ deltaX/100);
    			esRender.setspeedY(esRender.getspeedY()+ deltaY/100);  
    			
    			requestRender();
    			
            
        }
        
        // Save current x, y
 		previousX = currentX;
 		previousY = currentY;
 		return true; // Event handled
        //break;
    }
    
    // Key-up event handler
 	@Override
 	public boolean onKeyUp(int keyCode, KeyEvent event) {
 		switch (keyCode) { 		
	 		case KeyEvent.KEYCODE_A: // mengurangi kecepatan object		
	 			if((esRender.getspeedX()- 0.05f >0) ){ 				
					esRender.setspeedX(esRender.getspeedX()- 0.05f);
	 			}
	 			if((esRender.getspeedX()- 0.05f <0) ){ 				
					esRender.setspeedX(0.0f);
	 			}
	 			if((esRender.getspeedY()- 0.05f >=0)){ 				
					esRender.setspeedY(esRender.getspeedY()- 0.05f);
	 			}
	 			if((esRender.getspeedY()- 0.05f <0) ){ 				
					esRender.setspeedY(0.0f);
	 			}
	 			Log.v("Test Action KEYCODE_A", "action working");
	 			break;
	 		case KeyEvent.KEYCODE_Z: 
	 			esRender.lamp2();
	 			Log.v("Test Action KEYCODE_Z", "action working");
	 			break;
	 		case KeyEvent.KEYCODE_X:
	 			esRender.lamp1();
	 			Log.v("Test Action KEYCODE_X", "action working");
	 			break;
 		}
 		return true; // Event handled
 	}
}
