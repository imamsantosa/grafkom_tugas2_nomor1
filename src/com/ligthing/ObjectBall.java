package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11ExtensionPack;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class ObjectBall {
	private float vertices_circle[]={0.0f,0.0f,0.0f};
	private float vertices_circle_color[]={0.0f,0.0f,0.0f,0.5f};
	private float textCoord[];
	private float vertices_circle1[];
		
	
	int[] textures_indek = new int[1];

	private int batas_sudut=360;
	float jari_jari;
	float a,b;
	float x,y;
	float step=3.0f,step_line=0.2f;
	float x1,y1;
	float x2,y2;
	float teta;
	private int loop,loop_color;

	public ObjectBall() {
		
		// ============ start to generate stetch texture coordinat ==========================
		//Inisialisasi
		jari_jari=0.5f;
        
    	// Titik Pusat
    	a = 0.5f; b = 0.5f ;
    	//x=a+jari_jari; y=b;
    	teta = 0;

		// generate stretch texture coordinat
		teta=0;
		textCoord = new float[batas_sudut * 3];
		for (int ii = 0; ii < batas_sudut * 3; ii += 3) {
			// membentuk textCoord
			textCoord[ii] = (jari_jari*((float) Math.cos(-teta)))+a;
			textCoord[ii + 1] = (jari_jari*((float) Math.sin(-teta)))+b;
			textCoord[ii + 2] = 0.0f;			
			teta += Math.PI / 90;			
		}
		
		// ============ start to generate vertices to circle (Cara 1) ==========================
		//Inisialisasi
        jari_jari=50.0f;
        
    	// Titik Pusat
    	a = 50.0f; b = 50.0f ;
		
		vertices_circle1 = new float[batas_sudut * 3];
		for (int ii = 0; ii < batas_sudut * 3; ii += 3) {		
			// membentuk vertices_circle1
			vertices_circle1[ii] = (jari_jari*((float) Math.cos(teta)))+a;
			vertices_circle1[ii + 1] = (jari_jari*((float) Math.sin(teta)))+b;
			vertices_circle1[ii + 2] = 0.0f;			
			teta += Math.PI / 90;			
		}
		
		// ============ start to generate vertices to circle (Cara 2) ==========================
		//Inisialisasi
        jari_jari=50.0f;
        
    	// Titik Pusat
    	a = 50.0f; b = 50.0f ;
    	x=a+jari_jari; y=b;

        loop=0;
        loop_color=0;
        vertices_circle=new float[(int)(3*batas_sudut/step)*3];
        vertices_circle_color=new float[(int)(3*batas_sudut/step)*4];
        for(teta=0;teta<=2*batas_sudut;teta+=step){
        	vertices_circle[loop] = (float) ((x-a)*Math.cos((teta/180)*(22/7)) - ((y-b)*Math.sin((teta/180)*(22/7))) + a);
        	vertices_circle[loop+1] = (float) ((x-a)*Math.sin((teta/180)*(22/7)) - ((y-b)*Math.cos((teta/180)*(22/7))) + b);
        	vertices_circle[loop+2]=0;
        	loop+=3;
        	
        	//mengenerate warna untuk setiap vertex
        	//vertices_circle_color[loop_color]=(float) ((x-a)*Math.cos((teta/180)*(22/7)) - ((y-b)*Math.sin((teta/180)*(22/7))) + a);
        	//vertices_circle_color[loop_color+1]=(float) ((x-a)*Math.sin((teta/180)*(22/7)) - ((y-b)*Math.cos((teta/180)*(22/7))) + b);
        	
        	vertices_circle_color[loop_color]=(float) (Math.cos((teta/180)*(22/7)) );
        	vertices_circle_color[loop_color+1]=(float) (Math.sin((teta/180)*(22/7)));
        	
        	
        	vertices_circle_color[loop_color+2]=0.5f;
        	vertices_circle_color[loop_color+3]=0.5f;
        	loop_color+=4;
		}        
        // ============= end for generate vertices to circle ====================
        
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
	

	// Setup index-array buffer. Indices in byte.
	public static ByteBuffer makeByteBuffer(byte[] arr){
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
	    bb.put(arr);
	    bb.position(0);
	    return bb;
	}
	
	/** The draw method for the primitive object with the GL context */		
	public void draw_circle(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the object circle
		//gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle));
	    
	    //memetakan warna untuk setiap vertex
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle_color));
	    
	    //draw circle as filled shape
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, (int) ((int) 2*batas_sudut/step));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step));
	    gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step)); 
	    //gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	public void draw_circle_color(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour edge for the object circle
		//gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle1));
	    
	    //memetakan warna untuk setiap vertex
	    //gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle_color));
	    
	    //menempelkan tekstur ke objek
	    gl.glEnable(GL10.GL_TEXTURE_2D);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_indek[0]); // 4
		//gl.glTexCoordPointer(3, GL10.GL_FLOAT, 1, makeFloatBuffer(vertices_circle)); // 5
		gl.glTexCoordPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(textCoord)); // 5
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    

	    //GL11ExtensionPack gl_ = (GL11ExtensionPack) gl;
	    //GL11 gl11 = (GL11) gl;
	    //gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_indek[0]);
		
		// Set the face rotation
		//gl.glFrontFace(GL10.GL_CW);
	    
	    //draw circle as filled shape
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, (int) ((int) 2*batas_sudut/step));
	    gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, batas_sudut);
		//gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, 120);
	    //Log.i("Nilai 2*batas_sudut/step : ", ""+2*batas_sudut/step);
	    
	    
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawElements(GL10.GL_TRIANGLES, (int) ((int) 2*batas_sudut/step),
        //        GL10.GL_UNSIGNED_SHORT, makeFloatBuffer(vertices_circle));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step)); 
	    //gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
	    gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
	}
	
	public void draw_circle_color(GL10 gl,float red_in, float green_in,float blue_in, float alpha_in) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour edge for the object circle
		gl.glColor4f(red_in, green_in, blue_in, 0.33f);
		//gl.glColor4f(red_in, green_in, blue_in, alpha_in);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle1));
	    
	    //memetakan warna untuk setiap vertex
	    //gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle_color));
	    
	    //menempelkan tekstur ke objek
	    //gl.glEnable(GL10.GL_TEXTURE_2D);

		//gl.glEnable(GL10.GL_BLEND);
		//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		//gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_indek[0]); // 4
		//gl.glTexCoordPointer(3, GL10.GL_FLOAT, 1, makeFloatBuffer(vertices_circle)); // 5
		//gl.glTexCoordPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(textCoord)); // 5
		
		//gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	    

	    //GL11ExtensionPack gl_ = (GL11ExtensionPack) gl;
	    //GL11 gl11 = (GL11) gl;
	    //gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_indek[0]);
		
		// Set the face rotation
		//gl.glFrontFace(GL10.GL_CW);
	    
	    gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    
	    
	    //draw circle as filled shape
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, (int) ((int) 2*batas_sudut/step));
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, batas_sudut);
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, batas_sudut);
		//gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, 120);
	    //Log.i("Nilai 2*batas_sudut/step : ", ""+2*batas_sudut/step);
	    
	    
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawElements(GL10.GL_TRIANGLES, (int) ((int) 2*batas_sudut/step),
        //        GL10.GL_UNSIGNED_SHORT, makeFloatBuffer(vertices_circle));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step)); 
	    //gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend
	    //gl.glDisable( GL10.GL_TEXTURE_2D );             // Disable Texture Mapping
	}
	
	public void draw_segitiga(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the triangle
		//gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float [] {
				
				
				1.0f, 1.0f,  0.0f,		// V3
				0.0f, 1.0f,  0.0f,		// V2
				0.5f, 0.0f,  0.0f,	// V1 - first vertex (x,y,z)
		}));		


		// Draw the vertices as triangle
		//gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
		
		//menempelkan tekstur ke objek
	    //gl.glEnable(GL10.GL_TEXTURE_2D);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 		
		//gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 3);
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDisable( GL10.GL_BLEND );                  // Disable Alpha Blend

	}

	
	public void loadBallTexture(GL10 gl, Context context) {
		// Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
		// resource);

		Bitmap bitmap = BitmapFactory.decodeStream(context.getResources()
				.openRawResource(R.drawable.nature));

		gl.glGenTextures(1, textures_indek, 0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures_indek[0]);

		/*gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
				*/
		
		//gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE );
		//gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE );
		
		gl.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
		gl.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		
		//gl.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT );
		//gl.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT );
		
		///gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				//GL10.GL_NEAREST);
		//gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				//GL10.GL_LINEAR);
		
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

		bitmap.recycle();
	}
}
