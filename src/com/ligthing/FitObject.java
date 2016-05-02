package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;



public class FitObject {
	
	// Point to our vertex buffer, return buffer holding the vertices
	public static FloatBuffer makeFloatBuffer(float[] arr){
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
	}
	
	public void draw_bola(GL10 gl, float[] mat_diff, int shiness) {
		
		// mengaktifkan pencahayaan
		//float[] mat_amb = {0.2f * 1.0f, 0.2f * 0.4f, 0.2f * 0.4f, 1.0f,};
		float[] mat_amb = {0.0f * 1.0f, 0.0f * 0.4f, 0.0f * 0.4f, 1.0f,};
		//float[] mat_diff = {1.0f, 0.4f, 0.4f, 0.5f,};
		//float[] mat_diff = {1.0f, 0.8f, 0.04f, 1.0f};
		float[] mat_spec = {1.0f, 1.0f, 1.0f, 1.0f}; 

    	/*gl.glEnable(GL10.GL_DEPTH_TEST);
    	gl.glEnable(GL10.GL_CULL_FACE);	
    	gl.glShadeModel(GL10.GL_SMOOTH);

    	gl.glEnable(GL10.GL_LIGHTING);	
    	gl.glEnable(GL10.GL_LIGHT0);	*/
		gl.glEnable(GL10.GL_LIGHTING);	
    	gl.glEnable(GL10.GL_LIGHT0);
    	
    	// set the colour edge for the object circle
    	//gl.glColor4f(1.0f, 0.8f, 0.04f, 1.0f);

    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, makeFloatBuffer(mat_amb));	
    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloatBuffer(mat_diff));
    	gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(mat_spec));
    	gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shiness);
    	//gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 128.0f);
    		
		float	theta, pai;
    	float	co, si;
    	float	r1, r2;
    	float	h1, h2;
    	float	step = 2.0f;
    	float[][] v = new float[32][3];
    	ByteBuffer vbb;
    	FloatBuffer vBuf;
    	
		vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();

    	gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    	gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    	
    	for (pai = -90.0f; pai < 90.0f; pai += step) {
    		int	n = 0;

            r1 = (float)Math.cos(pai * Math.PI / 180.0);
    		r2 = (float)Math.cos((pai + step) * Math.PI / 180.0);
    		h1 = (float)Math.sin(pai * Math.PI / 180.0);
    		h2 = (float)Math.sin((pai + step) * Math.PI / 180.0);

    		for (theta = 0.0f; theta <= 360.0f; theta += step) {
    			co = (float)Math.cos(theta * Math.PI / 180.0);
    			si = -(float)Math.sin(theta * Math.PI / 180.0);

    			v[n][0] = (r2 * co);
    			v[n][1] = (h2);
    			v[n][2] = (r2 * si);
    			v[n + 1][0] = (r1 * co);
    			v[n + 1][1] = (h1);
    			v[n + 1][2] = (r1 * si);

    			vBuf.put(v[n]);
    			vBuf.put(v[n + 1]);

    			n += 2;  
    			
    			if(n>31){
    				vBuf.position(0);

    	    		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
    	    		gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
    				gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
    				
    				n = 0;
    				theta -= step;
    			}
    			
    		}
			vBuf.position(0);

    		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
    		gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
    	}
    	gl.glDisable(GL10.GL_LIGHTING);	
    	gl.glDisable(GL10.GL_LIGHT0);
    	gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    	gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}

}


