package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Pesawat {
	int gambar = 1;
	private float[] verticesBadan = { // 6 vertices of the aeroplanebody in (x,y,z)
			//rightBody
			0.0f, 0.0f, 3.0f, //ujung bawah
			0.0f, 0.0f, 0.0f, //bawah
			0.4f, 1f, 0.0f, //atas kanan
			//leftBody
			0.0f, 0.0f, 3.0f, //ujung
			0.0f, 0.0f, 0.0f, //bawah
			-0.4f, 1.f, 0.0f //atas kiri
	};
	private float[] verticesSayap = { // 5 vertices of the pyramid in (x,y,z)
			//rightWing
			0.0f, 0.0f, 3.0f, //ujung
			1.5f, 1f, 0f, //
			0.4f, 1f, 0f,
			//leftWing
			0.0f, 0.0f, 3.0f, //ujung
			-1.5f, 1f, 0f, 
			-0.4f, 1f, 0f,
	};

	// Point to our vertex buffer, return buffer holding the vertices
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	// Setup index-array buffer. Indices in byte.
	public static ByteBuffer makeByteBuffer(byte[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length);
		bb.put(arr);
		bb.position(0);
		return bb;
	}
	
	public void draw_badanPesawat(GL10 gl) {
//		gl.glFrontFace(GL10.GL_CCW); // Front face in counter-clockwise
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// set the global colour for the triangle
//		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
//		 Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(verticesBadan));
		// Draw the vertices as triangle
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 3, 3);
		// set the global colour for wire the trian-gle
//		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		// Draw the wire as edge triangle
//		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 3);
		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	public void draw_sayapPesawat(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// set the global colour for the triangle
//		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
		// Point to our vertex buffer
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(verticesSayap));
		// Draw the vertices as triangle
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 3, 3);
		// set the global colour for wire the trian-gle
//		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		// Draw the wire as edge triangle
//		gl.glDrawArrays(GL10.GL_LINE_LOOP, 0, 3);
		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
	