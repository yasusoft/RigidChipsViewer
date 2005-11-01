import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

import javavl.consts.*;
import javavl.lower.*;

public class RCView extends Applet implements Runnable, MouseListener, MouseMotionListener, KeyListener
{
	public static final String Copyright = "Yasu software - http://www.yasu.nu/";
	public static final String Message = "DO NOT DISASSEMBLE !!";

	public static final Object SYNC = new Object();

	private jVLDevice  device;
	private jVLSurface surface;
	private jVLViewport viewport;
	private Image img;
	private Thread thread;

	private float camX, camY, camZ;
	private float camAngleH, camAngleV;

	private RigidChipCore core;

	private int size;
	private boolean mouseLeft, mouseMiddle, mouseRight;
	private int mouseX, mouseY;
	private float mouseCAH, mouseCAV;
	private float mouseCX, mouseCY, mouseCZ;

	private boolean[] keys = new boolean[17];

	public RCView()
	{
	}

	public void init()
	{
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		size = Math.max(getSize().width, getSize().height);
		camX =  0.0f;
		camY = 10.0f;
		camZ =  0.0f;
		camAngleH = 0.0f;
		camAngleV = 0.0f;

		device = new jVLDevice();

		surface = new jVLSurface(this, jVLBufferType.PIXEL | jVLBufferType.Z);
		surface.setClearColor(0);
		device.setSurface(surface);

		device.setViewport(viewport = new jVLViewport(this, 0.1f, 1000f));

		jVLLight light = new jVLLight();
		light.type = jVLLightType.DIRECTIONAL;
		light.ambient.set(1f, 1f, 1f);
		light.diffuse.set(1f, 1f, 1f);
		light.direction.set(0.0f, -1.0f, 0.0f);
		device.setLight(0, light);
		device.lightEnable(0, true);

		//	レンダリングステートを設定する
		device.setRenderState(jVLRS.TRANSFORMENABLE, 1);
		device.setRenderState(jVLRS.LIGHTINGENABLE, 1);
		device.setRenderState(jVLRS.ZSORTENABLE, 1);
		device.setRenderState(jVLRS.ZTESTENABLE, 1);
		//device.setRenderState(jVLRS.AMBIENT, 0);
		device.setRenderState(jVLRS.FILLMODE, jVLFillMode.SOLID);
		device.setRenderState(jVLRS.CULLMODE, jVLCullMode.NONE);
		device.setRenderState(jVLRS.SHADEMODE, jVLShadeMode.FLAT);

		// テクスチャ
		RigidChip.loadTextures(this);

		// モデル読み込み
		RCDLoader loader = new RCDLoader();
		String filename = getParameter("FileName");
		if (filename == null) filename = "basic.txt";
		try {
			core = loader.loadFromURL(new java.net.URL(getDocumentBase(), filename));
			if (core == null)
				System.err.println(filename + ": " + loader.getError());
		} catch (Exception e) { }
		if (core == null)
			core = new RigidChipCore();
		
		try {
			int bgc = Integer.decode(getParameter("BGColor")).intValue();
			surface.setClearColor(bgc);
		} catch (Exception e) { }

		try {
			camX = Float.valueOf(getParameter("CameraX")).floatValue();
		} catch (Exception e) { }
		try {
			camY = Float.valueOf(getParameter("CameraY")).floatValue();
		} catch (Exception e) { }
		try {
			camZ = Float.valueOf(getParameter("CameraZ")).floatValue();
		} catch (Exception e) { }
		try {
			camAngleH = Float.valueOf(getParameter("AngleH")).floatValue();
		} catch (Exception e) { }
		try {
			camAngleV = Float.valueOf(getParameter("AngleV")).floatValue();
		} catch (Exception e) { }

		display();
	}
	
	public void display()
	{ synchronized (RCView.SYNC) {
		if (core == null)
			return;

		jVLMatrix mat = new jVLMatrix();

		// 色
		jVLMaterial material = new jVLMaterial();
		material.ambient.set(0.0f, 1.0f, 0.0f);
		material.diffuse.set(0.0f, 1.0f, 0.0f, 1.0f);
		device.setMaterial(material);

		jVLMatrix matWorld = new jVLMatrix();
		matWorld.identity();
		mat.rotationY(camAngleH);
		matWorld = matWorld.multiply(mat);
		mat.rotationX(camAngleV);
		matWorld = matWorld.multiply(mat);

		jVLMatrix matView = new jVLMatrix();
		matView.lookAt(new jVLVector(camX, camY, camZ), new jVLVector(camX, 0.0f, camZ), new jVLVector(0.0f, 0.0f, -1.0f));

		jVLMatrix matProj = new jVLMatrix();
		matProj.perspectiveFov((float)Math.PI / 10, viewport);

		//	サーフェスをクリアする
		surface.clear(jVLBufferType.PIXEL | jVLBufferType.Z);

		//	シーンを開始する
		device.beginScene();

		device.setTransform(jVLTransformType.WORLD, matWorld, 0);
		device.setTransform(jVLTransformType.VIEW, matView, 0);
		device.setTransform(jVLTransformType.PROJECTION, matProj, 0);

		core.draw(device, null);
		core.drawTrans(device, null);

		//	シーンを終了する
		img = device.endScene(this);

		repaint();
	} }

	public void update(Graphics g)
	{
		paint(g);
	}
	
	public void paint(Graphics g)
	{
		if (img != null)
			g.drawImage(img, 0, 0, this);
	}

	public void run()
	{
		try
		{
System.out.println("thread start");
			while (thread == Thread.currentThread())
			{
				long t = System.currentTimeMillis();

				for (Enumeration e = core.variables.elements(); e.hasMoreElements(); )
					((RCVariable)e.nextElement()).reset();
				for (int i = 0; i < keys.length && i < core.keys.length; i ++)
					if (keys[i])
						core.keys[i].act();
				boolean stop = true;
				for (Enumeration e = core.variables.elements(); e.hasMoreElements(); )
				{
					RCVariable var = (RCVariable)e.nextElement();
					var.act();
					if (var.modified)
						stop = false;
				}

				core.act();
				display();
				repaint();

				if (stop) thread = null;
				Thread.sleep(50 - (System.currentTimeMillis() - t));
			}
System.out.println("thread end");
		}
		catch (Exception e)
		{
		}
	}

	public void mouseDragged(MouseEvent e)
	{
		if (mouseRight)
		{
			camAngleH = mouseCAH + (e.getX() - mouseX) * 5.0f / size;
			camAngleV = mouseCAV + (e.getY() - mouseY) * 5.0f / size;
			display();
		}
		else if (mouseLeft)
		{
			camX = mouseCX + (mouseX - e.getX()) * 3.0f / size;
			camZ = mouseCZ + (mouseY - e.getY()) * 3.0f / size;
			display();
		}
		else if (mouseMiddle)
		{
			if (mouseY > e.getY() || camY > 2.0f)
				camY = mouseCY + (mouseY - e.getY()) * 20.0f / size;
			display();
		}
	}
	public void mouseMoved(MouseEvent e){}

	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e)
	{
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) mouseLeft = true;
		if ((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) mouseMiddle = true;
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) mouseRight = true;
		mouseCAH = camAngleH;
		mouseCAV = camAngleV;
		mouseCX = camX;
		mouseCY = camY;
		mouseCZ = camZ;
		mouseX = e.getX();
		mouseY = e.getY();
	}
	public void mouseReleased(MouseEvent e)
	{
		if ((e.getModifiers() & MouseEvent.BUTTON1_MASK) != 0) mouseLeft = false;
		if ((e.getModifiers() & MouseEvent.BUTTON2_MASK) != 0) mouseMiddle = false;
		if ((e.getModifiers() & MouseEvent.BUTTON3_MASK) != 0) mouseRight = false;
		while (camAngleV < 0) camAngleV += 2*Math.PI;
		while (camAngleV > 2*Math.PI) camAngleV -= 2*Math.PI;
		while (camAngleH < 0) camAngleH += 2*Math.PI;
		while (camAngleH > 2*Math.PI) camAngleH -= 2*Math.PI;
		display();
		System.out.println("cameraX = " + camX);
		System.out.println("cameraY = " + camY);
		System.out.println("cameraZ = " + camZ);
		System.out.println("AngleH = " + camAngleH);
		System.out.println("AngleV = " + camAngleV);
	}

	//public void mouseWheelMoved(MouseWheelEvent e)
	//{
	//	if (e.getWheelRotation() > 0 && camZ < 50)
	//		camZ += camZ * 0.1 + 0.1;
	//	if (e.getWheelRotation() < 0 && camZ > 2.5)
	//		camZ -= camZ * 0.1 + 0.1;
	//	display();
	//}

	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		final boolean flag = true;
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:	keys[0] = flag; break;
			case KeyEvent.VK_DOWN:	keys[1] = flag; break;
			case KeyEvent.VK_LEFT:	keys[2] = flag; break;
			case KeyEvent.VK_RIGHT:	keys[3] = flag; break;
			case KeyEvent.VK_Z:		keys[4] = flag; break;
			case KeyEvent.VK_X:		keys[5] = flag; break;
			case KeyEvent.VK_C:		keys[6] = flag; break;
			case KeyEvent.VK_A:		keys[7] = flag; break;
			case KeyEvent.VK_S:		keys[8] = flag; break;
			case KeyEvent.VK_D:		keys[9] = flag; break;
			case KeyEvent.VK_V:		keys[10] = flag; break;
			case KeyEvent.VK_B:		keys[11] = flag; break;
			case KeyEvent.VK_F:		keys[12] = flag; break;
			case KeyEvent.VK_G:		keys[13] = flag; break;
			case KeyEvent.VK_Q:		keys[14] = flag; break;
			case KeyEvent.VK_W:		keys[15] = flag; break;
			case KeyEvent.VK_E:		keys[16] = flag; break;
		}
		if (thread == null)
			(thread = new Thread(this)).start();
	}
	public void keyReleased(KeyEvent e)
	{
		final boolean flag = false;
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:	keys[0] = flag; break;
			case KeyEvent.VK_DOWN:	keys[1] = flag; break;
			case KeyEvent.VK_LEFT:	keys[2] = flag; break;
			case KeyEvent.VK_RIGHT:	keys[3] = flag; break;
			case KeyEvent.VK_Z:		keys[4] = flag; break;
			case KeyEvent.VK_X:		keys[5] = flag; break;
			case KeyEvent.VK_C:		keys[6] = flag; break;
			case KeyEvent.VK_A:		keys[7] = flag; break;
			case KeyEvent.VK_S:		keys[8] = flag; break;
			case KeyEvent.VK_D:		keys[9] = flag; break;
			case KeyEvent.VK_V:		keys[10] = flag; break;
			case KeyEvent.VK_B:		keys[11] = flag; break;
			case KeyEvent.VK_F:		keys[12] = flag; break;
			case KeyEvent.VK_G:		keys[13] = flag; break;
			case KeyEvent.VK_Q:		keys[14] = flag; break;
			case KeyEvent.VK_W:		keys[15] = flag; break;
			case KeyEvent.VK_E:		keys[16] = flag; break;
		}
	}
}
