package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

public class TransObject {
	
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
	
	// list vertices kubus
	private float vertices_kubus[]= {
			0.0f,0.0f,1.0f,
			1.0f,0.0f,1.0f,
			0.0f,1.0f,1.0f, 
			1.0f,1.0f,1.0f, 
			0.0f,0.0f,0.0f, 
			1.0f,0.0f,0.0f, 
			0.0f,1.0f,0.0f, 
			1.0f,1.0f,0.0f
	};
	
	// list color (kombinasi angka nol dan satu)
	private float colors_kubus[] = {
			0.0f,0.0f,0.0f,1.0f,
			1.0f,0.0f,0.0f,1.0f,
			1.0f,1.0f,0.0f,1.0f,
			0.0f,1.0f,0.0f,1.0f,
			0.0f,0.0f,1.0f,1.0f,
			1.0f,0.0f,1.0f,1.0f,
			1.0f,1.0f,1.0f,1.0f,
			0.0f,1.0f,1.0f,1.0f
	};
	private byte[] indices_kubus = {
			0,1,3, // depan 1
			3,2,0, // depan 2
			
			4,5,7, // belakang 1
			7,6,4, // belakang 2
			
			2,3,7, // atas 1
			7,6,2, // atas 2
			
			0,1,5, // bawah 1
			5,4,0, // bawah 2
			
			1,5,7, // kanan 1
			7,3,1, // kanan 2
			
			0,4,6, // kiri 1
			6,4,0, // kiri 2
			
			0,2,6, // kiri 3
			6,2,0 // kiri 4	
			
	};
   
	private byte[] indices_kubus2 = { // Vertex indices of the 6 kotak		

			0,1,3, // depan 1
			3,2,0, // depan 2
			
			4,5,7, // belakang 1
			7,6,4, // belakang 2
			
			2,3,7, // atas 1
			7,6,2, // atas 2
			
			0,1,5, // bawah 1
			5,4,0, // bawah 2
			
			1,5,7, // kanan 1
			7,3,1, // kanan 2
	
			0,4,6, // kiri 1
			6,2,0, // kiri 3
			4,6,2,
			2,0,4
			
			
	};
	
	private ByteBuffer mIndexBuffer; // Buffer for index-array
	private float vertices_circle[]={0.0f,0.0f,0.0f};
	private float vertices_circle_color[]={0.0f,0.0f,0.0f,0.5f};
	private float vertices_line[]={0.0f,0.0f,0.0f};
	private float vertices_line_color[]={0.0f,0.0f,0.0f,1.0f};
	private int batas_sudut=360;
	float jari_jari;
	float a,b;
	float x,y;
	float step=3.0f,step_line=0.2f;
	float x1,y1;
	float x2,y2;
	private int loop,loop_color,loop_line,loop_line_color;

	public TransObject() {

		// ============ start to generate vertices to circle ==========================
		//Inisialisasi
        jari_jari=1.0f;
        
    	// Titik Pusat
    	a = 0.0f; b = 0.0f ;
    	x=a+jari_jari; y=b;

        loop=3;
        loop_color=4;
        vertices_circle=new float[(int)(3*batas_sudut/step)*3];
        vertices_circle_color=new float[(int)(3*batas_sudut/step)*4];
        for(float teta=0;teta<=2*batas_sudut;teta+=step){
        	vertices_circle[loop] = (float) ((x-a)*Math.cos((teta/180)*(22/7)) - ((y-b)*Math.sin((teta/180)*(22/7))) + a);
        	vertices_circle[loop+1] = (float) ((x-a)*Math.sin((teta/180)*(22/7)) - ((y-b)*Math.cos((teta/180)*(22/7))) + b);
        	vertices_circle[loop+2]=0;
        	loop+=3;
        	
        	//mengenerate warna untuk setiap vertex
        	vertices_circle_color[loop_color]=(float) ((x-a)*Math.cos((teta/180)*(22/7)) - ((y-b)*Math.sin((teta/180)*(22/7))) + a);
        	vertices_circle_color[loop_color+1]=(float) ((x-a)*Math.sin((teta/180)*(22/7)) - ((y-b)*Math.cos((teta/180)*(22/7))) + b);
        	vertices_circle_color[loop_color+2]=0.5f;
        	vertices_circle_color[loop_color+3]=0.5f;
        	loop_color+=4;
		}        
        // ============= end for generate vertices to circle ====================
        
        // ============ start to generate vertices to line ==========================
        x1 = -1.0f; y1 = -1.0f;
    	x2= 1.0f; y2 = 1.0f;
    	
        loop_line=3;
        loop_line_color=4;
        vertices_line=new float[(int)(2*(x2-x1)/step_line)*3];
        vertices_line_color=new float[(int)(2*(x2-x1)/step_line)*4];
        
        float m = (y2-y1)/(x2-x1);
        for(x=x1;x<=x2;x+=step_line){
        	vertices_line[loop_line] = (float) (x);
        	vertices_line[loop_line+1] = (float) (m*(x-x1)+y1);
        	vertices_line[loop_line+2]=0;
        	loop_line+=3;
        	
        	//mengenerate warna untuk setiap vertex
        	vertices_line_color[loop_line_color]=(float) (0.5*x);
        	vertices_line_color[loop_line_color+1]=(float) (0.5*m*(x-x1)+y1);
        	vertices_line_color[loop_line_color+2]=1.0f;
        	vertices_line_color[loop_line_color+3]=1.0f;
        	loop_line_color+=4;
		}
        // ============= end for generate vertices to line ====================
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
	
	public void draw_kubus(GL10 gl) {
		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
										// orientation
		// Enable arrays and define their buffers
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_kubus));
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(colors_kubus));
		gl.glDrawElements(GL10.GL_TRIANGLES, indices_kubus.length,
				GL10.GL_UNSIGNED_BYTE, makeByteBuffer(indices_kubus));
		//gl.glDrawElements(GL10.GL_LINE_STRIP, indices_kubus2.length,
				//GL10.GL_UNSIGNED_BYTE, makeByteBuffer(indices_kubus2));
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	public void draw_points(GL10 gl) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the points (pemberian warna untuk titik)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		
		// Point to our vertex buffer (mendata nilai lokasi/posisi titik)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float [] {
				1.0f, 1.0f,  0.0f,		// V1 - first vertex (x,y,z)
				1.0f, 0.8f,  0.0f,		// V2 
				1.0f, 0.6f,  0.0f,		// V3
				1.0f, 0.4f,  0.0f,		// V4 
				1.0f, 0.2f,  0.0f,		// V5
				1.0f, 0.0f,  0.0f,		// V6
				1.0f, -0.2f,  0.0f,		// V7
				1.0f, -0.4f,  0.0f,		// V8
				1.0f, -0.6f,  0.0f,		// V9
				1.0f, -0.8f,  0.0f,		// V10
				1.0f, -1.0f,  0.0f,		// V11
				
				0.8f, -1.0f,  0.0f,		// V12
				0.6f, -1.0f,  0.0f,		// V13
				0.4f, -1.0f,  0.0f,		// V14
				0.2f, -1.0f,  0.0f,		// V15
				0.0f, -1.0f,  0.0f,		// V16
				-0.2f, -1.0f,  0.0f,	// V17
				-0.4f, -1.0f,  0.0f,	// V18
				-0.6f, -1.0f,  0.0f,	// V19
				-0.7f, -1.0f,  0.0f,	// V20
				-0.8f, -1.0f,  0.0f,	// V21
				-1.0f, -1.0f,  0.0f,	// V22		
		}));
		
		// Draw the vertices as points (menggambar titik-titik)
		gl.glDrawArrays(GL10.GL_POINTS, 0, 22);
					
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
	public void draw_line(GL10 gl) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the line (pemberian warna untuk garis)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		
		// Point to our vertex buffer (mendata nilai lokasi/posisi titik yang menyusun garis)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float [] {
				1.0f, 1.0f,  0.0f,		// V1 - first vertex (x,y,z)
				-1.0f, -1.0f,  0.0f,	// V2 - second vertex				
			}));
		
		// Draw the vertices as lines (menggambar garis dari titik-titik)
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		/*gl.glDrawElements(GL10.GL_LINES, 2,
        GL10.GL_UNSIGNED_SHORT, makeFloatBuffer(new float [] {
 				1.0f, 1.0f,  0.0f,		// V1 - first vertex (x,y,z)
				-1.0f, -1.0f,  0.0f,	// V2 - second vertex				
			}));*/
					
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
	
	public void draw_line_color(GL10 gl) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the line (pemberian warna untuk garis)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		
		// Point to our vertex buffer (mendata nilai lokasi/posisi titik yang menyusun garis)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_line));
		
		//memetakan warna untuk setiap vertex
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_line_color));
		
		// Draw the vertices as lines (menggambar garis dari titik-titik)
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, (int) (2*(x2-x1)/step_line));
		/*gl.glDrawElements(GL10.GL_LINES, 2,
        GL10.GL_UNSIGNED_SHORT, makeFloatBuffer(new float [] {
 				1.0f, 1.0f,  0.0f,		// V1 - first vertex (x,y,z)
				-1.0f, -1.0f,  0.0f,	// V2 - second vertex				
			}));*/
					
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
	
	public void draw_circle(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the object circle
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle));
	    
	    //draw circle as filled shape
	    //gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, (int) ((int) 2*batas_sudut/step));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step)); 
	    //gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		//gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
	public void draw_circle_color(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour edge for the object circle
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		//create VBO from buffer with glBufferData()
	    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle));
	    
	    //memetakan warna untuk setiap vertex
	    gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_circle_color));
	    
	    //draw circle as filled shape
	    gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step));
	    //gl.glDrawElements(GL10.GL_TRIANGLES, (int) ((int) 2*batas_sudut/step),
        //        GL10.GL_UNSIGNED_SHORT, makeFloatBuffer(vertices_circle));
	    
	    //draw circle contours
	    //gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step)); // membuat garis putus-putus pada tepi lingkaran
	    //gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int) 2*batas_sudut/step)); 
	    //gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));
	    
	    
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
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
	
	public void draw_segitiga(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		// set the colour for the triangle
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float [] {
			-0.5f, -0.5f,  0.0f,		// V1 - first vertex (x,y,z)
			 0.5f, -0.5f,  0.0f,		// V2 - second vertex
			 0.0f,  0.5f,  0.0f			// V3 - third vertex
			
		}));		

		// Draw the vertices as triangle
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
		gl.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
}
