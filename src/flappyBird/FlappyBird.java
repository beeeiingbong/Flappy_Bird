package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JFrame;

public class FlappyBird implements ActionListener, MouseListener, KeyListener{

	public static FlappyBird flappyBird;
	public final int width =800, height =800;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public int ticks, yMotion, score, Scr;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	public boolean gameOver,started;
	
	
	public FlappyBird()
	{
		JFrame jframe=new JFrame();
		Timer timer =new Timer(40, this);
		
		renderer =new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setSize(width, height);
		jframe.addMouseListener(this);
		jframe.addKeyListener(this);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		
		bird = new Rectangle(width/2 - 10,height/2 - 10, 20, 20);//(x,y,width,height)
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}
	
	public void repaint(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.cyan);
		g.fillRect(0, 0, width, height);
		
		g.setColor(Color.orange);
		g.fillRect(0,height - 120 , width, 150);

		g.setColor(Color.green);
		g.fillRect(0, height-120, width, 20);
 		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		if(!gameOver) {
			for(Rectangle column : columns)
			{
				paintColumn(g, column);
			}
		}
		

		
		
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1 ,80));
		
		if(!gameOver) {
			if(started)
				g.drawString(String.valueOf(score), width/2 -25,100);
			else
				g.drawString("Click to start", 100, height/2 -50);
			
			Scr =score;
		}
		
		else {
			g.drawString("GameOver", 100 , height/2 -50);
			g.drawString(String.valueOf("Score:- "+Scr),200,100);
		}
		

	}
	
	public void addColumn(boolean start) {
		
		int space = 300;
		int Width = 100;
		int Height = 50 + rand.nextInt(300);
		
		if(start) {
			columns.add(new Rectangle(width + Width + columns.size()* 300, height - Height -120, Width, Height));
			columns.add(new Rectangle(width + Width + (columns.size()-1)*300, 0, Width, height- Height-space));

		}
		
		else
		{
			columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, height - Height -120, Width ,Height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0, Width, height- Height-space));
		}
		
	}
	
	public void paintColumn(Graphics g,Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	public void jump() {
		
		if (gameOver) 
		{
			bird = new Rectangle(width/2 - 10,height/2 - 10, 20, 20);//(x,y,width,height)
			columns.clear();
			yMotion=0;
			score =0;
			
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false; 
		}
		
		if (!started) {
			started =true;
		}
		
		else if (!gameOver) {
			if(yMotion>0)
			{
				yMotion =0;
			}
			
			yMotion=-20;
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int speed =15;
		ticks++;
		
		if(started) {
			for(int i =0; i < columns.size();i++) 
			{
				Rectangle column =columns.get(i);
				column.x -= speed;
			}
			
			if (ticks % 2 == 0 && yMotion <50)
			{
				yMotion +=4;
			}
			
			for(int i =0;i< columns.size(); i++) {
				
				Rectangle column = columns.get(i);
				
				if(column.x + column.width <0)
				{
					columns.remove(column);
					
					if (column.y==0)
					{
						addColumn(false);
					}
				}
			}
			
			bird.y +=yMotion;
			
			for (Rectangle column : columns)
			{
				if(column.y==0 && bird.x + bird.width/2 > column.x + column.width/2 -10  &&
						bird.x + bird.width/2 <column.x + column.width/2 +10) {
					
				score++;
				
				}
				
				if (column.intersects(bird))
				{
					gameOver =true;
					
					bird.x = column.x - bird.width;
				}
			}
			
			if(bird.y > height -120 || bird.y <0) {
				gameOver = true;
			}
			
			if (bird.y + yMotion >= height -120) {
				bird.y = height -120 - bird.height;
			}
		}
		
		renderer.repaint(); //REDRAWING
	}
	
	public static void main(String[] args) 
	{
		flappyBird = new FlappyBird();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	if(e.getKeyCode() == KeyEvent.VK_SPACE) {
		jump();
	}	
	}
}

