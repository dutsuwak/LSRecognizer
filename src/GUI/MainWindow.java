package GUI;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import audioManage.MicrophoneListener;

@SuppressWarnings("serial")
public class MainWindow extends JPanel implements ActionListener{
	
	private JFrame frame;
	private SpringLayout bgLayout;
	private JButton StartButton;
	private Canvas GraphCanvas;
	private DrawCanvas graph;
	
	private int lastHeight = 0;
	private int newHeight = 0;
	boolean startUp = true;
	boolean running = false;
	MicrophoneListener newMic;
	
	Thread listeningLoop = new Thread(new Runnable() {
	     public void run() {
	    	 
	    	 Thread graphicThread = new Thread(new Runnable() {
			     public void run() {
			    	 while(running){
			    		 int input = (newMic.getSoundLevel() *20 )/100;
				          //System.out.println(input);
				          graph.rect(input, GraphCanvas.getGraphics());
				          try {
							Thread.sleep(1000);
				          } 
				          catch (InterruptedException e) {
							e.printStackTrace();
				          }
			    	 }
			    	 newMic.stop();
			     }
			}); 
	    	
	    	running = true;
			newMic = new MicrophoneListener(44100, 16, 2);
			graphicThread.start();
	   	 	newMic.run();
	     }
	});
	
	
	public MainWindow(){
		super();
		this.setBackground(new Color(95,151,69));
		bgLayout = new SpringLayout();
		this.setLayout(bgLayout);
		
		buildUpGUI();
		
		frame = new JFrame("Light Sound Recognizer");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension d = new Dimension(); //Objeto para obtener tamaño de la pantalla
        frame.setResizable(false);
        frame.setLocation((int)((d.getWidth()+2)+420),120);
        frame.add(this);
        frame.setSize(600,400);
        frame.setVisible(true);
		
	}
	
	private void buildUpGUI(){
		StartButton = new JButton();
		StartButton.setPreferredSize(new Dimension(115,30));
        StartButton.setBackground(new Color(193,213,184));
        StartButton.setText("Start");
        StartButton.setActionCommand("start");
		StartButton.addActionListener(this);
		
		GraphCanvas = new Canvas();
		GraphCanvas.setSize(150,300);
		GraphCanvas.setBackground(Color.white);
		
		
		
		add(StartButton);
		add(GraphCanvas);
		
		bgLayout.putConstraint(SpringLayout.NORTH, StartButton, 350, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, StartButton, 50, SpringLayout.WEST, this);
		bgLayout.putConstraint(SpringLayout.NORTH, GraphCanvas, 10, SpringLayout.NORTH, this);
		bgLayout.putConstraint(SpringLayout.WEST, GraphCanvas, 10, SpringLayout.WEST, this);
		
		
	}
	public void startLoop(){
		listeningLoop.start();
		
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if("start".equals(evt.getActionCommand())){
			graph = new DrawCanvas();
			for(int i = 0; i<11; i++){
				graph.rect(i,GraphCanvas.getGraphics());
			}
			startUp = false;
			startLoop();
			
		}
		
	}
	
	/**
    * Define a inner class called DrawCanvas which is a JPanel used for custom drawing
    */
   private class DrawCanvas extends JPanel {
      // Override paintComponent to perform your own paintingue
	  
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);     // paint parent's background
         setBackground(Color.WHITE);  // set background color for this JPanel
         
         if(startUp == false){
	         int y1 = (int)(295-(lastHeight*14.5));
	         g.clearRect(25, y1, 100, 295);
	         
	         g.setColor(Color.GREEN);    // set the drawing color
	         int y = (int)(295-(newHeight*14.5));
	         System.out.println(y);
	         g.fillRect(25, y, 100, 295);
         }
         else{
        	 int y = (int)(300-(newHeight*29));
	         g.drawString(Integer.toString(newHeight*10), 2, y);
         }
      }
      public void rect(int x,Graphics g){
    	  newHeight = x;
    	  paintComponent(g);
    	  lastHeight = x;
      }
   }
}


