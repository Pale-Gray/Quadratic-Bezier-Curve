package me.pale.main;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import me.pale.math.Maths;

public class Main {
	
	float p1x, p1y, p2x, p2y, p3x, p3y;
	float f;
	float h1x, h1y, h2x, h2y;
	float f1x, f1y;
	
	int animate = 0;
	boolean reverse = true;
	
	ArrayList<Float> px = new ArrayList<Float>();
	ArrayList<Float> py = new ArrayList<Float>();
	
	ArrayList<Float> p = new ArrayList<Float>();

	public Main() throws LWJGLException {
		
		float fac = 125;
		
		p1x = -fac-50;
		p1y = -fac;
		p3x = fac+50;
		p3y = -fac;
		p2x = 0;
		p2y = fac+50;
		
		px.clear();
		py.clear();
		
		f = 0;
		
		init();
		
	}
	
	public void render() {
		
		linesOfPoints1();
		pointsOfPoints1();
		
		linesOfPoints2();
		pointsOfPoints2();
		
		finalPoint();
		
		bezier();
		
	}
	
	public void updateMaths() {
		
		if (animate == 1) {
			
			if (reverse) {
				
				f += 0.001f;
				
			}
			if (reverse == false) {
				
				f -= 0.001f;
				
			}
			
			if (f >= 1) {
				
				reverse = false;
				
			}
			if (f <= 0) {
				
				reverse = true;
				
			}
			
		}
		
		if (Keyboard.next()) {
			
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				
				System.out.println("pressed");
				
				animate++;
				
				if (animate > 1) {
					
					animate = 0;
					
				}
				
			}
			
		}
		
		h1x = Maths.lerp(p1x, p2x, f);
		h1y = Maths.lerp(p1y, p2y, f);
		
		h2x = Maths.lerp(p2x, p3x, f);
		h2y = Maths.lerp(p2y, p3y, f);
		
		f1x = Maths.lerp(h1x, h2x, f);
		f1y = Maths.lerp(h1y, h2y, f);
		
	}
	
	public void bezier() {
		
		px.add(f1x);
		py.add(f1y);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			
			px.clear();
			py.clear();
			
		}
		
		GL11.glPointSize(2);
		GL11.glBegin(GL11.GL_POINTS);
		for (int i = 0; i < px.size(); i++) {
			GL11.glColor3f(0, 0, 0);
			GL11.glVertex2f(px.get(i), py.get(i));	
			
		}
		GL11.glEnd();
		
	}
	
	public void finalPoint() {
		
		GL11.glPointSize(8);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3f(0.9f, 0.4f, 0);
		GL11.glVertex2f(f1x, f1y);
		GL11.glEnd();
		
	}
	
	public void linesOfPoints2() {
		
		GL11.glLineWidth(3);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(0.4f, 0, 0);
		GL11.glVertex2f(h1x, h1y);
		GL11.glVertex2f(h2x, h2y);
		GL11.glEnd();
		
	}
 	
	public void linesOfPoints1() {
		
		GL11.glLineWidth(3);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(0.4f, 0.4f, 0.4f);
		GL11.glVertex2f(p1x, p1y);
		GL11.glVertex2f(p2x, p2y);
		GL11.glVertex2f(p2x, p2y);
		GL11.glVertex2f(p3x, p3y);
		GL11.glEnd();
		
	}
	
	public void pointsOfPoints2() {
		
		GL11.glPointSize(5);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3f(0.5f, 0, 0);
		GL11.glVertex2f(h1x, h1y);
		GL11.glVertex2f(h2x, h2y);
		GL11.glEnd();
		
	}
	
	public void pointsOfPoints1() {
		
		float mousex = Mouse.getX() - (Display.getWidth()/2);
		float mousey = Mouse.getY() - (Display.getHeight()/2);
		float bounds = 15;
		
		// System.out.println(Mouse.getX() - (Display.getWidth()/2));
		
		if (Mouse.isButtonDown(0) && mousex > p1x - bounds && mousex < p1x + bounds && mousey > p1y - bounds && mousey < p1y + bounds) {
			
			px.clear();
			py.clear();
			
			System.out.println("Mouse down");
			p1x += Mouse.getDX();
			p1y += Mouse.getDY();
			
		}
		
	if (Mouse.isButtonDown(0) && mousex > p2x - bounds && mousex < p2x + bounds && mousey > p2y - bounds && mousey < p2y + bounds) {
				
				px.clear();
				py.clear();
				
				System.out.println("Mouse down");
				p2x += Mouse.getDX();
				p2y += Mouse.getDY();
				
			}
	
	if (Mouse.isButtonDown(0) && mousex > p3x - bounds && mousex < p3x + bounds && mousey > p3y - bounds && mousey < p3y + bounds) {
		
		px.clear();
		py.clear();
		
		System.out.println("Mouse down");
		p3x += Mouse.getDX();
		p3y += Mouse.getDY();
		
	}
		
		GL11.glPointSize(5);
		GL11.glBegin(GL11.GL_POINTS);
		GL11.glColor3f(0, 0, 0);
		GL11.glVertex2f(p1x, p1y);
		GL11.glVertex2f(p2x, p2y);
		GL11.glVertex2f(p3x, p3y);
		GL11.glEnd();
		
	}
	
	public void update() {
		
		while (!Display.isCloseRequested()) {
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GL11.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
			
			render();
			updateMaths();
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				
				f += 0.0005f;
				
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				
				f -= 0.0005f;
				
			}
			if (f > 1) {
				
				f=1;
				
			}
			if (f < 0) {
				
				f=0;
				
			}
			
			Display.update();
			
		}
		Display.destroy();
		System.exit(0);
		
	}
	
	public void init() throws LWJGLException {
		
		Display.setDisplayMode(new DisplayMode(640, 480));
		Display.setTitle("quadratic bezier curve");
		Display.create();
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(-640/2, 640/2, -480/2, 480/2, 0, 1);
		GL11.glViewport(0, 0, 640, 480);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		update();
		
	}
	
	public static void main(String[] args) throws LWJGLException {
		
		new Main();
		
	}
	
}
