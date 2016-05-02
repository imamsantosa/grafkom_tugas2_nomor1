package com.ligthing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Formatter;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.util.Log;
import android.widget.TextView;

public class ESRender implements Renderer {

	private ESText glText;
	private TextView textview;
	private ObjectArena objectarena; // the primitive object to be drawn
	private ObjectBall objectball;
	private ObjectBall objectball2;
	private ObjectBall objectball3;
	private PrimitivesObject primob;
	private TransObject transobject;
	private final MySphere mEarth, mEarth2, mEarth3, bolaBasket;
	
	private boolean isOn=true;
	private boolean lamp1 = true;
	private boolean lamp2 = true;

	private PhotoKubus kotak; // (NEW)
	// private PhotoKubus tembok_blkng;
	private TextureKubus tembok;
	private Context context;
	private kubus kubus;
	private lukisan lukisan;
	private lukisan lukisan1;
	private pintu pintu;

	private kubus_kecil kubus_kecil;
	private kaki_meja kaki_meja;
	private lampu lampu;

	// Context context;
	int Run_Mode = 0;
	float CurrentAngle = 0.0f; // Angle in degrees
	float AnimateStep = -2.0f; // Rotation step per update

	float CurrentAngle1 = 0.0f; // Angle in degrees
	float AnimateStep1 = 2.0f; // Rotation step per update
	
	float radius = 50.0f; // Ball's radius
	float x = radius; // Ball's center (x,y)
	float y = radius;
	float speedX = 5f; // Ball's speed (x,y)
	float speedY = 3f;

	int xMin, xMax, yMin, yMax;

	private int mywidth = 0;
	private int myheight = 0;
	private int jumlah_pantulan = 0;

	// posisi cahaya
	// base transformasi yang ada pada objek
	// gl.glTranslatef(600.0f, 200.0f, 0.0f);
	// gl.glScalef(25.0f, 25.0f, 25.0f);
	// gl.glTranslatef (0.0f, 0.0f, -5.0f);

	// float[] position = { 0.0f, 0.0f, 1.5f, 1.0f }; // original
	float[] position = { ((0.0f) * 25.0f) + 600.0f, ((0.0f) * 25.0f) + 200.0f,
			((1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };
	float[] position41 = { ((0.0f) * 25.0f) + 800.0f,
			((0.0f) * 25.0f) + 400.0f, ((1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };
	float[] position42 = { ((0.0f) * 25.0f) + 800.0f,
			((0.0f) * 25.0f) + 400.0f, ((-1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };

	float[] position1 = { ((0.0f) * 25.0f) + 800.0f, ((0.0f) * 25.0f) + 200.0f,
			((1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };

	// float[] position2 = { 0.0f, 0.0f, -1.5f, 0.0f}; //{x,y,z,w}
	float[] position2 = { ((0.0f) * 25.0f) + 800.0f, ((0.0f) * 25.0f) + 200.0f,
			((-1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };
	float[] position3 = { ((0.0f) * 25.0f) + 800.0f, ((0.0f) * 25.0f) + 200.0f,
			((-1.5f - 5.0f) * 25.0f) + 0.0f, 1.0f };

	float black[] = new float[] { 0.0f, 0.0f, 0.0f, 1.0f };
	float yellow[] = new float[] { 1.0f, 1.0f, 0.0f, 1.0f };
	float cyan[] = new float[] { 0.0f, 1.0f, 1.0f, 1.0f };
	float white[] = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
	float orange[] = new float[] { 1.0f, 0.5f, 0.0f, 0.0f };
	float diffuseMaterial[] = new float[] { 0.73f, 0.13f, 0.86f, 1.0f }; // set
																			// cahaya
																			// warna
																			// ungu
	float diffuseMaterial2[] = new float[] { 0.5f, 0.5f, 0.5f, 1.0f }; // set
																		// cahaya
																		// warna
																		// ungu
	float lightAmbient[] = new float[] { 0.2f, 0.3f, 0.6f, 1.0f };

	float mat_specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float light_position[] = { -1.5f, 0.0f, 0.0f, 0.0f };

	/** Constructor to set the handed over context */
	public ESRender(Context context) {
		// super();
		this.context = context;
		this.objectarena = new ObjectArena();
		this.objectball = new ObjectBall();
		this.objectball2 = new ObjectBall();
		this.objectball3 = new ObjectBall();
		this.primob = new PrimitivesObject();
		this.mEarth = new MySphere(5, 3);
		this.mEarth2 = new MySphere(5, 3);
		this.mEarth3 = new MySphere(5, 3);
		this.bolaBasket = new MySphere(5, 3);
		this.transobject = new TransObject();
		kotak = new PhotoKubus(context); // (NEW)
		// tembok_blkng = new PhotoKubus(context);
		tembok = new TextureKubus(context);

		lukisan = new lukisan(context);//
		lukisan1 = new lukisan(context);//

		// this.context = context; // Get the application context (NEW)
		kubus = new kubus();
		pintu = new pintu(context);
		kubus_kecil = new kubus_kecil(context);
		kaki_meja = new kaki_meja(context);
		lampu = new lampu(context);
	}

	@Override
	public void onDrawFrame(GL10 gl) {

		// Draw background color
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_NORMALIZE);

		// gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_AMBIENT_AND_DIFFUSE,
		// makeFloatBuffer(cyan));
		
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SPECULAR, white, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_SHININESS, white, 0);
		gl.glMaterialfv(GL10.GL_FRONT, GL10.GL_DIFFUSE, white, 0);

		// gl.glMatrixMode( GL10.GL_MODELVIEW ); // Activate Model View Matrix
		// gl.glLoadIdentity(); // Load Identity Matrix
		if(isOn){
			gl.glDisable(GL10.GL_LIGHTING);
		}
		
		// bawah lantai
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		// gl.glScalef(50.0f, 50.0f, 50.0f);
		gl.glRotatef(90, 1, 0, 0);
		kotak.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// atap
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(180, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		// gl.glRotatef(CurrentAngle, 0.1f, 1.0f, 0.2f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		// gl.glScalef(2.0f, 2.0f, 2.0f);
		kotak.draw_atap(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// tembok belakang
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		kotak.draw_tembok_blk(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// tembok samping kanan
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.0f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(180, 1.0f, 0.0f, 1.0f);
		tembok.draw_tembok_smp(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// tembok samping kiri
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(-1.0f, 0.0f, -5.0f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		// gl.glRotatef(CurrentAngle, 0.1f, 1.0f, 0.2f); // Rotate
		// gl.glRotatef(270, 0.0f, 0.0f, 1.0f);
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(180, 1.0f, 0.0f, 0.0f);
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		// gl.glScalef(2.0f, 2.0f, 2.0f);
		tembok.draw_tembok_smp(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// lukisan
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.5f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f);
		gl.glScalef(0.5f, 0.5f, 0.5f);
		// gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f);
		lukisan.draw_lukisan(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		
		// lukisan2
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.5f, 0.0f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -5.5f);
		gl.glRotatef(180, 0.0f, 1.0f, 0.0f);
		gl.glScalef(0.3f, 0.5f, 0.1f);
		// gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f);
		lukisan.draw_lukisan1(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		
		// PINTU
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(-1.3f, -0.2f, 0.0f);
		gl.glTranslatef(0.0f, 0.0f, -5.5f);
		gl.glRotatef(180, 0.0f, 1.0f, 0.0f);
		gl.glScalef(0.5f, 0.8f, 0.5f);
		// gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f);
		pintu.draw_pintu(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// // buat meja
		// gl.glPushMatrix();
		//
		// gl.glTranslatef(0.0f, 0.0f, -5.0f);
		// gl.glScalef(0.5f, 0.5f, 0.5f);
		// gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// kotak.draw_meja(gl);
		// gl.glPopMatrix();

		// MEJA
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.5f, -0.6f, -5.0f);
		//
		gl.glScalef(0.4f, 0.05f, 1.0f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		// gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f); // Rotate
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// KAKI MEJA
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.6f, -0.8f, -5.0f);
		gl.glScalef(0.05f, 0.17f, 0.05f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
// Meja 2

		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, -0.6f, -5.0f);
		//
		gl.glScalef(0.4f, 0.05f, 1.0f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		// gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f); // Rotate
		gl.glRotatef(90, 1.0f, 0.0f, 0.0f);
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// KAKI MEJA
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, -0.8f, -5.0f);
		gl.glScalef(0.05f, 0.17f, 0.05f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// kubus di atas meja
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, -0.4f, -5.0f);
		gl.glScalef(0.1f, 0.1f, 0.1f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kubus_kecil.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		// kubus di atas meja 2
		gl.glPushMatrix();
//				gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(-0.1f, -0.4f, -4.5f);
		gl.glScalef(0.1f, 0.1f, 0.1f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(45, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kubus_kecil.draw(gl);
//				gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		// bola 1
		gl.glPushMatrix();
//		 gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.6f, -0.4f, -5.0f);
		gl.glScalef(0.05f, 0.05f, 0.05f);
		//gl.glRotatef(CurrentAngle, 0, 1, 0);

		mEarth.draw(gl);
//		 gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		// bola 1
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(1.0f, -0.4f, -5.0f);
		gl.glScalef(0.05f, 0.05f, 0.05f);
		gl.glRotatef(CurrentAngle, 1.0f, 0.0f, 0.0f);
		mEarth.draw(gl);
//		 gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		// bola basket
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(-1.0f, -0.8f, -5.0f);
		gl.glScalef(0.05f, 0.05f, 0.05f);
		gl.glRotatef(CurrentAngle, 1.0f, 0.0f, 0.0f);

		bolaBasket.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		// LAMPU
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_LIGHT1);
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, 0.7f, -5.0f);
//		gl.glRotatef(180, 0.0f, 0.0f, 1.0f); // Rotate
//		gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
//		gl.glRotatef(0, 0.0f, 1.0f, 0.0f); // Rotate
		gl.glRotatef(CurrentAngle, 0.0f, 1.0f, 0.0f );
//		gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		
//		gl.glPushMatrix();
//		gl.glRotatef(180, 1.0f, 0.0f, 0.0f);
		gl.glMaterialf(GL10.GL_BACK, GL10.GL_SHININESS, 256.0f);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, light_position, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, white, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, white, 0);
//		gl.glTranslatef (0.0f, 0.0f, -0.5f);
//		   gl.glDisable (GL10.GL_LIGHTING);			   		
//		   		gl.glScalef(0.01f, 0.01f, 0.01f);
//		   		transobject.draw_kubus(gl);
//		   gl.glEnable (GL10.GL_LIGHTING);
//		gl.glPopMatrix();
		
		gl.glScalef(0.04f, 0.04f, 0.04f);		
		
		mEarth3.draw(gl);
		if(lamp1)
			gl.glEnable(GL10.GL_LIGHT1);
		gl.glPopMatrix();
		
		// LAMPU2
		gl.glPushMatrix();
		gl.glDisable(GL10.GL_LIGHT0);
		gl.glTranslatef(0.5f, 0.7f, -5.0f);
//		gl.glRotatef(180, 0.0f, 0.0f, 1.0f); // Rotate
//		gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
//		gl.glRotatef(0, 0.0f, 1.0f, 0.0f); // Rotate
		gl.glRotatef(-CurrentAngle, 0.0f, 1.0f, 0.0f );
//		gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		
//		gl.glPushMatrix();
//		gl.glRotatef(180, 1.0f, 0.0f, 0.0f);
		gl.glMaterialf(GL10.GL_BACK, GL10.GL_SHININESS, 256.0f);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_position, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, white, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, lightAmbient, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, white, 0);
//		gl.glTranslatef (0.0f, 0.0f, -0.5f);
//		   gl.glDisable (GL10.GL_LIGHTING);			   		
//		   		gl.glScalef(0.01f, 0.01f, 0.01f);
//		   		transobject.draw_kubus(gl);
//		   gl.glEnable (GL10.GL_LIGHTING);
//		gl.glPopMatrix();
		
		gl.glScalef(0.04f, 0.04f, 0.04f);		
		
		mEarth3.draw(gl);
		if(lamp2)
			gl.glEnable(GL10.GL_LIGHT0);
		gl.glPopMatrix();
		
		// penyangga lampu
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.0f, 0.8f, -5.0f);
		gl.glScalef(0.03f, 0.2f, 0.05f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();
		
		// penyangga lampu2
		gl.glPushMatrix();
//		gl.glDisable(GL10.GL_LIGHTING);
		gl.glTranslatef(0.5f, 0.8f, -5.0f);
		gl.glScalef(0.03f, 0.2f, 0.05f);
		// gl.glTranslatef(0.0f, 0.0f, -6.0f); // Translate into the screen
		gl.glRotatef(90, 0.0f, 1.0f, 0.0f); // Rotate
		// gl.glRotatef(CurrentAngle, 1.0f, 1.0f, 1.0f );
		// gl.glTranslatef(-0.5f,-0.5f,-0.5f);
		kaki_meja.draw(gl);
//		gl.glEnable(GL10.GL_LIGHTING);
		gl.glPopMatrix();

		if(isOn){
			gl.glEnable(GL10.GL_LIGHTING);
		}
		// Update the rotational angle after each refresh
		// re-Calculate animation parameters
		CurrentAngle1 += AnimateStep1;
		if (CurrentAngle1 < -360.0) {
			// CurrentAngle -= 360.0*Math.floor(CurrentAngle/360.0);
			CurrentAngle1 = 0.0f;
			CurrentAngle1 += AnimateStep1;
		}
		
		CurrentAngle += AnimateStep;
		if (CurrentAngle < -360.0) {
			// CurrentAngle -= 360.0*Math.floor(CurrentAngle/360.0);
			CurrentAngle = 0.0f;
			CurrentAngle += AnimateStep;
		}
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f); // Set color's clear-value to
													// black
		gl.glClearDepthf(1.0f); // Set depth's clear-value to farthest
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables depth-buffer for hidden
		// surface removal
		gl.glDepthFunc(GL10.GL_LEQUAL); // The type of depth testing to do
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); // nice
		// perspective
		// view
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable smooth shading of color
		gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
		// performance
		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_LIGHT1);

		// Create the GLText
		glText = new ESText(gl, context.getAssets());

		// Load the font from file (set size + padding), creates the texture
		// NOTE: after a successful call to this the font is ready for
		// rendering!
		glText.load("Roboto-Regular.ttf", 14, 2, 2); // Create Font (Height: 14
														// Pixels / X+Y Padding
														// 2 Pixels)

		// gl.glDisable(GL10.GL_DITHER); // Disable dithering for better
		// performance

		// Setup Blending (NEW)
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f); // Full brightness, 50% alpha
												// (NEW)
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE); // Select blending
														// function (NEW)

		// Setup Texture, each time the surface is created (NEW)
		objectball2.loadBallTexture(gl, context);
		mEarth.loadGLTexture(gl, context, 2);
		bolaBasket.loadGLTexture(gl, context, 3);
		// mEarth2.loadGLTexture(gl, context,6);
		mEarth3.loadGLTexture(gl, context, 5);

		kotak.loadTexture(gl, context); // Load images into textures (NEW)
		tembok.loadTexture(gl, context);
		//
		kubus.loadTexture(gl, context);
		lukisan.loadTexture(gl, context);
		pintu.loadTexture(gl, context);
		kubus_kecil.loadTexture(gl, context);
		kaki_meja.loadTexture(gl, context);
		lampu.loadTexture(gl, context);
		gl.glEnable(GL10.GL_TEXTURE_2D); // Enable texture (NEW)

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

		// mywidth = width;
		// myheight = height;

		if (height == 0)
			height = 1; // To prevent divide by zero
		float aspect = (float) width / height;

		gl.glViewport(0, 0, width, height);

		// Setup orthographic projection
		gl.glMatrixMode(GL10.GL_PROJECTION); // Activate Projection Matrix
		gl.glLoadIdentity(); // Load Identity Matrix
		// gl.glOrthof( // Set Ortho Projection
		// (Left,Right,Bottom,Top,Front,Back)
		// 0, width, 0, height, 500.0f, -500.0f);
		// gl.glOrthof(arg0, arg1, arg2, arg3, arg4, arg5);
		// GLU.gluPerspective(gl, fovy, aspect, zNear, zFar);

		// Use perspective projection
		GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);
		// GLU.gluPerspective(gl, 45, aspect, 500.0f, -500.f);
		// Save width and height
		// this.width = width; // Save Current Width
		// this.height = height; // Save Current Height

		gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
		gl.glLoadIdentity(); // Reset

	}

	public float getxMax() {
		return xMax;
	}

	public void setxMax(int xmax) {
		xMax = xmax;
	}

	public float getxMin() {
		return xMin;
	}

	public void setxMin(int xmin) {
		xMin = xmin;
	}

	public float getyMax() {
		return yMax;
	}

	public void setyMax(int ymax) {
		yMax = ymax;
	}

	public float getyMin() {
		return yMin;
	}

	public void setyMin(int ymin) {
		yMin = ymin;
	}

	public float getspeedX() {
		return speedX;
	}

	public void setspeedX(float speedX_) {
		speedX = speedX_;
	}

	public float getspeedY() {
		return speedY;
	}

	public void setspeedY(float speedY_) {
		speedY = speedY_;
	}
	
	public boolean getLamp(){
		return isOn;
	}
	
	public boolean setOff(){
		return isOn =false;
	}
	
	public boolean setOn(){
		return isOn = true;
	}
	
	public void lamp1On(){
		lamp1 = true;
	}
	
	public void lamp1Off(){
		lamp1 = false;
	}
	
	public void lamp1(){
		if(lamp1) lamp1Off();
		else lamp1On();
	}
	
	public boolean lamp2On(){
		return lamp2 = true;
	}
	
	public boolean lamp2Off(){
		return lamp2 = false;
	}
	
	public void lamp2(){
		if(lamp2) lamp2Off();
		else lamp2On();
	}

	public void moveWithCollisionDetection(ESRender esRender) {
		// Get new (x,y) position
		x += speedX;
		y += speedY;
		// Detect collision and react
		if (x + radius > esRender.getxMax()) {
			speedX = -speedX;
			x = esRender.getxMax() - radius;
			this.jumlah_pantulan += 1;
		} else if (x - radius < esRender.getxMin()) {
			speedX = -speedX;
			x = esRender.getxMin() + radius;
			this.jumlah_pantulan += 1;
		}
		if (y + radius > esRender.getyMax()) {
			speedY = -speedY;
			y = esRender.getyMax() - radius;
			this.jumlah_pantulan += 1;
		} else if (y - radius < esRender.getyMin()) {
			speedY = -speedY;
			y = esRender.getyMin() + radius;
			this.jumlah_pantulan += 1;
		}

		// pengkondisian penghitungan pantulan mulai dari nol
		if (Run_Mode == 0) {
			this.jumlah_pantulan -= 4;
			this.Run_Mode = 1;
		}
	}

	public void set(int x, int y, int width, int height) {
		xMin = x;
		// xMax = x + width - 1;
		xMax = x + width;
		yMin = y;
		// yMax = y + height - 1;
		yMax = y + height;
	}
}