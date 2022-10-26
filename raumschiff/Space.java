package a.raumschiff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.desktop.ScreenSleepEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

public class Space {
	private JFrame frame;
	private JPanel space;
	private Raumschiff orion = new Raumschiff();

	public Space() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setLocation(200, 100);
		
		frame.setLayout(new BorderLayout());
		
		space = new JPanel();
		space.setBackground(new Color(0x000000));
		
		space.setLayout(null); // Absolute (oder null) layout erlaubt pixelgenaue positionierung
		
		//Für Sterne
		for(int i =0; i < 100; i++) {
		JPanel stern = new JPanel();
		stern.setSize(1,1);
		stern.setBackground(new Color(0xFFFFFF));
		
		Random r = new Random();
		stern.setLocation(r.nextInt(600), r.nextInt(600));
		
		space.add(stern);
		}
		space.add(orion);
		
			frame.addKeyListener(new KeyAdapter() {
				
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == e.VK_UP) {
						orion.setY(orion.getY()- orion.getySpeed());
					}
					if(e.getKeyCode() == e.VK_DOWN) {
						orion.setY(orion.getY()+ orion.getySpeed());
					}
					if(e.getKeyCode() == e.VK_LEFT) {
						orion.setX(orion.getX()- orion.getxSpeed());
					}
					if(e.getKeyCode() == e.VK_RIGHT) {
						orion.setX(orion.getX()+ orion.getxSpeed());
					}
				}
			});
		
		frame.add(space, BorderLayout.CENTER);	
		
		//Bewegung des raumschiffs soll in eigenem thread passieren
		
		createThread();
		
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	private void createThread() {
		//hier wird der thread erzeugt, der sich um die bewegung des raumschiffs kümmert
		
		Thread move = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						//Damit das Raumschiff nur 10 mal pro Sekunde bewegt wird
						Thread.sleep(100);
						orion.move();
						orion.setLocation(orion.getX(), orion.getY());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
		move.start();
	}
	
	public static void main(String[] args) {
		new Space();
		
		
	}

}
