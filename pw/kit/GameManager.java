package pw.kit;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

import pw.common.Environment;
import pw.common.GameObject;
import pw.common.RegistryValue;

abstract public class GameManager extends JFrame implements KeyListener {
	private int msdelay = 20;
	Refresh re;
	private HashMap<RegistryValue,String> gameRegistry = new HashMap<RegistryValue,String>();
	GamePanel panel;
    protected GameManager(String title,Point size){
    	this(title, size, false);
    }
    protected GameManager(String title, Point size, boolean undeco) {
    	if (undeco) {
    		setUndecorated(true);
    		setBackground(new Color(0, 0, 0, 0));
    	}
    	setRegistryValue(RegistryValue.MSDELAY, "20");
        this.setTitle(title);
        File f = new File(System.getProperty("user.dir") + "\\src\\pw\\images\\icon.png");
        Image img = null;
		try {
			img = ImageIO.read(f);
			this.setIconImage(img);
		} 
        catch (Exception e) {
        	
		}
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel();
        this.add(panel, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        if (size != null)
        	this.setSize(size.x,size.y);
        else {
        	this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        	this.setUndecorated(true);
        }
        this.setVisible(true);
        this.addKeyListener(this);
        GameObject.SetAllObjectsDefaultSetting();
        re = new Refresh(panel);
        re.start();
    }
    @SuppressWarnings("serial")
	public class GamePanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            setBackground(Environment.skyboxColor);
            DrawScreen(g);
        }
    }
    protected GamePanel getPanel() {
    	return this.panel;
    }
    protected void setRegistryValue(RegistryValue registry,String value) {
    	gameRegistry.put(registry, value);
    }
    protected String getRegistryValue(RegistryValue registry) {
    	return gameRegistry.get(registry);
    }
    protected void DrawScreen(Graphics g) {
    	
    }
    protected abstract void MainLoop();
    public class Refresh extends Thread {
    	GamePanel g;
    	public Refresh(GamePanel g) {
    		this.g = g;
    	}
    	@SuppressWarnings("static-access")
		@Override
    	public void run() { 
    		while(true) {
    			MainLoop();
    			g.repaint();
    			msdelay = Integer.parseInt(getRegistryValue(RegistryValue.MSDELAY));
        		try {
    				this.sleep(msdelay);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
}