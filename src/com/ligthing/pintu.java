package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

/*
 * A photo cube with 6 pictures (textures) on its 6 faces.
 */
public class pintu {
	private FloatBuffer vertexBuffer; // Vertex Buffer
	private FloatBuffer texBuffer; // Texture Coords Buffer
	private int numFaces = 14;
	private int[] imageFileIDs = { // Image file IDs
	R.drawable.caldera, //index 0
	R.drawable.pintuku, //index 1
	R.drawable.mule, //index 2
	R.drawable.glass, //index 3
	R.drawable.leonardo, //index 4
	R.drawable.tmsk, //index 5
	R.drawable.lantai, //index 6
	R.drawable.tembok, //index 7
	R.drawable.tembokx, //index 8
	R.drawable.atap, //index 9
	R.drawable.meja,// index 10
	R.drawable.imam, //index 11
	R.drawable.pintux, // index 12
	R.drawable.wallpaper // index 13
	
	};
	private int[] textureIDs = new int[numFaces];
	private Bitmap[] bitmap = new Bitmap[numFaces];
	private float cubeHalfSize = 1.0f;

	// Constructor - Set up the vertex buffer
	public pintu(Context context) {
		// Allocate vertex buffer. An float has 4 bytes
		ByteBuffer vbb = ByteBuffer.allocateDirect(12 * 4 * numFaces);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asFloatBuffer();
		
		
		
		// Read images. Find the aspect ratio and adjust the vertices
		// accordingly.
		for (int face = 0; face < numFaces; face++) {
			bitmap[face] = BitmapFactory.decodeStream(context.getResources()
					.openRawResource(imageFileIDs[face]));
			int imgWidth = bitmap[face].getWidth();
			int imgHeight = bitmap[face].getHeight();
			float faceWidth = 2.0f;
			float faceHeight = 2.0f;
			
			// Adjust for aspect ratio
			if (imgWidth > imgHeight) {
				faceHeight = faceHeight * imgHeight / imgWidth;
			} else {
				faceWidth = faceWidth * imgWidth / imgHeight;
			}
			float faceLeft = -faceWidth / 2;
			float faceRight = -faceLeft;
			float faceTop = faceHeight / 2;
			float faceBottom = -faceTop;
			
			// Define the vertices for this face
//			float[] vertices = { faceLeft, faceBottom, 0.0f, // 0.
//																// left-bottom-front
//					faceRight, faceBottom, 0.0f, // 1. right-bottom-front
//					faceLeft, faceTop, 0.0f, // 2. left-top-front
//					faceRight, faceTop, 0.0f, // 3. right-top-front
//			};
			float[] vertices = { // Vertices for a face
					-1.0f, -1.0f, 0.0f, // 0. left-bottom-front
							1.0f, -1.0f, 0.0f, // 1. right-bottom-front
							-1.0f, 1.0f, 0.0f, // 2. left-top-front
							1.0f, 1.0f, 0.0f // 3. right-top-front
					};
			vertexBuffer.put(vertices); // Populate
		}
		vertexBuffer.position(0); // Rewind
		// Allocate texture buffer. An float has 4 bytes. Repeat for 6 faces.
		float[] texCoords = { 
				0.0f, 1.0f, // A. left-bottom
				1.0f, 1.0f, // B. right-bottom
				0.0f, 0.0f, // C. left-top
				1.0f, 0.0f // D. right-top
		};
		ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4
				* numFaces);
		tbb.order(ByteOrder.nativeOrder());
		texBuffer = tbb.asFloatBuffer();
		for (int face = 0; face < numFaces; face++) {
			texBuffer.put(texCoords);
		}
		texBuffer.position(0); // Rewind
	}

//	// Render the shape (Buat lantai)
//	public void draw(GL10 gl) {
//		gl.glFrontFace(GL10.GL_CCW);
//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//		
//		// lantai
//		gl.glPushMatrix();
//		
//		gl.glTranslatef(0f, 0f, cubeHalfSize);
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[6]);
//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//		gl.glPopMatrix();
//		
//		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//	}
//	
//	// Render the shape (Buat tembok belakang)
//		public void draw_tembok_blk(GL10 gl) {
//			gl.glFrontFace(GL10.GL_CCW);
//			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//			
//			// tembok
//			gl.glPushMatrix();
//			gl.glTranslatef(0f, 0f, cubeHalfSize);
//			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[7]);
//			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//			gl.glPopMatrix();
//			
//			
//			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//		}
		
		// Render the shape (Buat PINTU)
		public void draw_pintu(GL10 gl) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glFrontFace(GL10.GL_CCW);
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
			
			// tembok
			gl.glPushMatrix();
			gl.glTranslatef(0f, 0f, cubeHalfSize);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[1]);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
			gl.glPopMatrix();
			
			
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);

		}

//		// Render the shape (Buat atap)
//		public void draw_atap(GL10 gl) {
//			gl.glFrontFace(GL10.GL_CCW);
//			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//			
//			// tembok
//			gl.glPushMatrix();
//			gl.glTranslatef(0f, 0f, cubeHalfSize);
//			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[9]);
//			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//			gl.glPopMatrix();
//			
//			
//			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//	
//		}
//		
//		public void draw_meja(GL10 gl) {
//			gl.glFrontFace(GL10.GL_CCW);
//			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//			
//			// meja
//			gl.glPushMatrix();
//			
//			gl.glTranslatef(0f, 0f, cubeHalfSize);
//			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[10]);
//			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//			gl.glPopMatrix();
//			
//			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//		}
//		// buat kaki meja
//		public void draw_kaki_meja(GL10 gl) {
//			gl.glFrontFace(GL10.GL_CCW);
//			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
//			
//			// kaki meja meja
//			gl.glPushMatrix();
//			gl.glTranslatef(0f, 0f, cubeHalfSize);
//			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[10]);
//			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//			gl.glPopMatrix();
//			
//			
//			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//		}
	// Load images into 6 GL textures
	public void loadTexture(GL10 gl, Context c) {
		gl.glGenTextures(14, textureIDs, 0); // Generate texture-ID array for 6
											// IDs
		// Generate OpenGL texture images
		for (int face = 0; face < numFaces; face++) {
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[face]);
			// Build Texture from loaded bitmap for the currently-bind texture
			// ID
			
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[face], 0);
			bitmap[face].recycle();
		}
	}
	
	
	

	
	
}
