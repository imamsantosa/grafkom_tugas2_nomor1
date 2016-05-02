package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

public class ObjectArena {
	private float vertices[] = {
			-0.5f, -0.5f,  0.0f,	// V1 - first vertex (x,y,z)
			-0.5f, 0.5f,  0.0f,		// V2 
			0.5f, 0.5f,  0.0f,		// V3
			 0.5f, -0.5f,  0.0f,	// V4
			-0.5f, -0.5f,  0.0f		// V5
	};
	
	private float vertices_color[] = {
			1.0f, 0.0f,  0.0f, 1.0f,	// CV1 - first color (red,green,blue)
			0.0f, 1.0f,  0.0f, 1.0f,	// CV2
			0.0f, 0.0f,  1.0f, 1.0f,	// CV3
			0.0f, 1.0f, 0.0f,  1.0f,	// CV4
			1.0f, 0.0f,  0.0f, 1.0f		// CV5	 
	};

	public ObjectArena() {
		
	}
	
	// Point to our vertex buffer, return buffer holding the vertices
	public static FloatBuffer makeFloatBuffer(float[] arr){
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
	}
	
	/** The draw method for the primitive object with the GL context */	
	public void draw_kotak(GL10 gl) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// Point to our vertex buffer		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices));
		
		// Draw the vertices as square		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 2, 3);
		
				
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
	
	

}
