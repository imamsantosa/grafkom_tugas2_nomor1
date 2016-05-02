package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

public class PrimitivesObject {

	private float vertices[] = { -0.5f, -0.5f, 0.0f, // V1 - first vertex
														// (x,y,z)
			-0.5f, 0.5f, 0.0f, // V2
			0.5f, 0.5f, 0.0f, // V3
			0.5f, -0.5f, 0.0f, // V4
			-0.5f, -0.5f, 0.0f // V5
	};

	private float[] vertices_quad = { // Vertices for the square
	-1.0f, -1.0f, 0.0f, // 0. left-bottom
			1.0f, -1.0f, 0.0f, // 1. right-bottom
			-1.0f, 1.0f, 0.0f, // 2. left-top
			1.0f, 1.0f, 0.0f // 3. right-top
	};

	private float vertices_color[] = { 1.0f, 0.0f, 0.0f, 1.0f, // CV1 - first
																// color
																// (red,green,blue)
			0.0f, 1.0f, 0.0f, 1.0f, // CV2
			0.0f, 0.0f, 1.0f, 1.0f, // CV3
			0.0f, 1.0f, 0.0f, 1.0f, // CV4
			1.0f, 0.0f, 0.0f, 1.0f // CV5
	};

	private float vertices_circle[] = { 0.0f, 0.0f, 0.0f };
	private float vertices_circle_color[] = { 0.0f, 0.0f, 0.0f, 0.5f };
	private float vertices_line[] = { 0.0f, 0.0f, 0.0f };
	private float vertices_line_color[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	private int batas_sudut = 360;
	float jari_jari;
	float a, b;
	float x, y;
	float step = 3.0f, step_line = 0.2f;
	float x1, y1;
	float x2, y2;
	private int loop, loop_color, loop_line, loop_line_color;

	public PrimitivesObject() {

		// ============ start to generate vertices to circle
		// ==========================
		// Inisialisasi
		jari_jari = 1.0f;

		// Titik Pusat
		a = 0.0f;
		b = 0.0f;
		x = a + jari_jari;
		y = b;

		loop = 3;
		loop_color = 4;
		vertices_circle = new float[(int) (3 * batas_sudut / step) * 3];
		vertices_circle_color = new float[(int) (3 * batas_sudut / step) * 4];
		for (float teta = 0; teta <= 2 * batas_sudut; teta += step) {
			vertices_circle[loop] = (float) ((x - a)
					* Math.cos((teta / 180) * (22 / 7))
					- ((y - b) * Math.sin((teta / 180) * (22 / 7))) + a);
			vertices_circle[loop + 1] = (float) ((x - a)
					* Math.sin((teta / 180) * (22 / 7))
					- ((y - b) * Math.cos((teta / 180) * (22 / 7))) + b);
			vertices_circle[loop + 2] = 0;
			loop += 3;

			// mengenerate warna untuk setiap vertex
			vertices_circle_color[loop_color] = (float) ((x - a)
					* Math.cos((teta / 180) * (22 / 7))
					- ((y - b) * Math.sin((teta / 180) * (22 / 7))) + a);
			vertices_circle_color[loop_color + 1] = (float) ((x - a)
					* Math.sin((teta / 180) * (22 / 7))
					- ((y - b) * Math.cos((teta / 180) * (22 / 7))) + b);
			vertices_circle_color[loop_color + 2] = 0.5f;
			vertices_circle_color[loop_color + 3] = 0.5f;
			loop_color += 4;
		}
		// ============= end for generate vertices to circle
		// ====================

		// ============ start to generate vertices to line
		// ==========================
		x1 = -1.0f;
		y1 = -1.0f;
		x2 = 1.0f;
		y2 = 1.0f;

		loop_line = 3;
		loop_line_color = 4;
		vertices_line = new float[(int) (2 * (x2 - x1) / step_line) * 3];
		vertices_line_color = new float[(int) (2 * (x2 - x1) / step_line) * 4];

		float m = (y2 - y1) / (x2 - x1);
		for (x = x1; x <= x2; x += step_line) {
			vertices_line[loop_line] = (float) (x);
			vertices_line[loop_line + 1] = (float) (m * (x - x1) + y1);
			vertices_line[loop_line + 2] = 0;
			loop_line += 3;

			// mengenerate warna untuk setiap vertex
			vertices_line_color[loop_line_color] = (float) (0.5 * x);
			vertices_line_color[loop_line_color + 1] = (float) (0.5 * m
					* (x - x1) + y1);
			vertices_line_color[loop_line_color + 2] = 1.0f;
			vertices_line_color[loop_line_color + 3] = 1.0f;
			loop_line_color += 4;
		}
		// ============= end for generate vertices to line ====================
	}

	// Point to our vertex buffer, return buffer holding the vertices
	public static FloatBuffer makeFloatBuffer(float[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	/** The draw method for the primitive object with the GL context */
	public void draw_points(GL10 gl) {

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour for the points (pemberian warna untuk titik)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		// Point to our vertex buffer (mendata nilai lokasi/posisi titik)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float[] {
				1.0f, 1.0f, 0.0f, // V1 - first vertex (x,y,z)
				1.0f, 0.8f, 0.0f, // V2
				1.0f, 0.6f, 0.0f, // V3
				1.0f, 0.4f, 0.0f, // V4
				1.0f, 0.2f, 0.0f, // V5
				1.0f, 0.0f, 0.0f, // V6
				1.0f, -0.2f, 0.0f, // V7
				1.0f, -0.4f, 0.0f, // V8
				1.0f, -0.6f, 0.0f, // V9
				1.0f, -0.8f, 0.0f, // V10
				1.0f, -1.0f, 0.0f, // V11

				0.8f, -1.0f, 0.0f, // V12
				0.6f, -1.0f, 0.0f, // V13
				0.4f, -1.0f, 0.0f, // V14
				0.2f, -1.0f, 0.0f, // V15
				0.0f, -1.0f, 0.0f, // V16
				-0.2f, -1.0f, 0.0f, // V17
				-0.4f, -1.0f, 0.0f, // V18
				-0.6f, -1.0f, 0.0f, // V19
				-0.7f, -1.0f, 0.0f, // V20
				-0.8f, -1.0f, 0.0f, // V21
				-1.0f, -1.0f, 0.0f, // V22
		}));

		// Draw the vertices as points (menggambar titik-titik)
		gl.glDrawArrays(GL10.GL_POINTS, 0, 22);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

	public void draw_line(GL10 gl) {

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour for the line (pemberian warna untuk garis)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		// Point to our vertex buffer (mendata nilai lokasi/posisi titik yang
		// menyusun garis)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float[] {
				1.0f, 1.0f, 0.0f, // V1 - first vertex (x,y,z)
				-1.0f, -1.0f, 0.0f, // V2 - second vertex
		}));

		// Draw the vertices as lines (menggambar garis dari titik-titik)
		gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		/*
		 * gl.glDrawElements(GL10.GL_LINES, 2, GL10.GL_UNSIGNED_SHORT,
		 * makeFloatBuffer(new float [] { 1.0f, 1.0f, 0.0f, // V1 - first vertex
		 * (x,y,z) -1.0f, -1.0f, 0.0f, // V2 - second vertex }));
		 */

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

	public void draw_line_color(GL10 gl) {

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour for the line (pemberian warna untuk garis)
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		// Point to our vertex buffer (mendata nilai lokasi/posisi titik yang
		// menyusun garis)
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_line));

		// memetakan warna untuk setiap vertex
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,
				makeFloatBuffer(vertices_line_color));

		// Draw the vertices as lines (menggambar garis dari titik-titik)
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0,
				(int) (2 * (x2 - x1) / step_line));
		/*
		 * gl.glDrawElements(GL10.GL_LINES, 2, GL10.GL_UNSIGNED_SHORT,
		 * makeFloatBuffer(new float [] { 1.0f, 1.0f, 0.0f, // V1 - first vertex
		 * (x,y,z) -1.0f, -1.0f, 0.0f, // V2 - second vertex }));
		 */

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

	public void draw_circle(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour for the object circle
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

		// create VBO from buffer with glBufferData()
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				makeFloatBuffer(vertices_circle));

		// draw circle as filled shape
		// gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, (int) ((int)
		// 2*batas_sudut/step));

		// draw circle contours
		// gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2*batas_sudut/step));
		// // membuat garis putus-putus pada tepi lingkaran
		gl.glDrawArrays(GL10.GL_LINES, 1, (int) ((int) 2 * batas_sudut / step));
		// gl.glDrawArrays(GL10.GL_LINE_STRIP, 1, (int) ((int)
		// 2*batas_sudut/step));
		// gl.glDrawArrays(GL10.GL_POINTS, 1, (int) ((int) 2*batas_sudut/step));

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		// gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	public void draw_circle_color(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour edge for the object circle
		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);

		// create VBO from buffer with glBufferData()
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
				makeFloatBuffer(vertices_circle));

		// memetakan warna untuk setiap vertex
		gl.glColorPointer(4, GL10.GL_FLOAT, 0,
				makeFloatBuffer(vertices_circle_color));

		// draw circle as filled shape
		// gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 1, (int) ((int)
		// 2*batas_sudut/step));
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 1,
				(int) ((int) 2 * batas_sudut / step));

		// Disable the client state before leaving
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

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

	public void draw_segitiga(GL10 gl) {
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		// set the colour for the triangle
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(new float[] {
				-0.5f, -0.5f, 0.0f, // V1 - first vertex (x,y,z)
				0.5f, -0.5f, 0.0f, // V2 - second vertex
				0.0f, 0.5f, 0.0f // V3 - third vertex

				}));

		// Draw the vertices as triangle
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_color));
		gl.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

	// Render the shape quad
	public void draw_quad(GL10 gl) {
		// Enable vertex-array and define its buffer
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, makeFloatBuffer(vertices_quad));
		// Draw the primitives from the vertex-array directly
		gl.glPolygonOffset(0.0f, 1.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f); // Set the current color (NEW)
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices_quad.length / 3);
		//gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices_quad.length / 3);
		//gl.glDrawArrays(GL10.GL_LINES, 0, vertices_quad.length / 3);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
