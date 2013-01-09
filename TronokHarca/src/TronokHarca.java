import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ReplicateScaleFilter;
import java.applet.*;
import java.net.*;
import java.util.*;

public class TronokHarca extends Applet implements Runnable {
	

private static final long serialVersionUID = 3080533471226900117L;
Applet got = this;
Thread kicker = null;
Image imgTeruletek[] ,offScrImage, imgDefTerkep, imgTerkep;
Graphics offScrGr;
MediaTracker tracker = new MediaTracker(this);

mousewheelListener wheelListener;
mousemotionListener motionListener;
//a térkép korrdinátája
int terkepX, terkepY = 0;
double terkepXYRatio;
//az egér aktuális koordinátái
int mX, mY = 0;
//térkép zoom mértéke
double terkepZoom = 3;
//Zene
AudioClip au;
	
	public void init()
      {
    	  URL fileGot = null;
    	  
    	  try{ URL u1=new URL(getCodeBase(),"res/zene.au");
    	    au=getAudioClip(u1);
    	    //au.loop();
    	  }
    	  catch(Exception  e){
    		   System.out.println(e);
    		    }
    	  
    	  try {
    		  fileGot = new URL(getCodeBase(), "file.got");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	InputStream in = null;
		try 
		{
			
		in = fileGot.openStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
    	
		String line;
    	  
		while((line = bf.readLine()) != null)
		{
			 
			if (line.equals("")) continue;
			else if (line.equals("<vastron>"))
			{
				
				while (true)
				{
					
					line = bf.readLine();
					if (line.equals("</vastron>")) break;
					
					Image kep = getImage(getCodeBase(), "res/"+line+".jpg");
					Haz haz = new Haz(line,kep);
					Tabla.vastron.add(haz);
				
				}
			}
			
			else if (line.equals("<kard>"))
			{
				while (true)
				{
					
					line = bf.readLine();
					if (line.equals("</kard>")) break;
					
					Haz haz = Tabla.getHaz(line);
					if (haz==null) break;
					
					Tabla.kard.add(haz);
				}
			}
			
			else if (line.equals("<hollo>"))
			{
				while (true)
				{
					
					line = bf.readLine();
					if (line.equals("</hollo>")) break;
					
					Haz haz = Tabla.getHaz(line);
					if (haz==null) break;
					
					Tabla.hollo.add(haz);
				
				}
			}
			
			else if (line.equals("<terulet>"))
			{
				while(true)
				{
					
					line = bf.readLine();
					if (line.equals("</terulet>")) break;
					String nev = line;
					
					Image kep = getImage(getCodeBase(),"res/"+nev+".png");
			        
					line = bf.readLine();
				    int varak = Integer.parseInt(line);
				    
				    line = bf.readLine();
				    int hordok = Integer.parseInt(line);
				    
				    line = bf.readLine();
				    int korona = Integer.parseInt(line);
				    
				    
				    Haz tulajdonos = Tabla.getHaz(bf.readLine());
				    
				    Terulet terulet = new Terulet(nev, varak, hordok, korona, tulajdonos, kep);
				    Tabla.teruletek.add(terulet);
				    
				    line = bf.readLine();
				    int gyalogos = Integer.parseInt(line);
				    for (int i= 0; i<gyalogos; i++)
				    {
				    	Image gyalogosKep = getImage(getCodeBase(),"res/Gyalogos.png");
				    	Egyseg gyalogosE = new Egyseg("Gyalogos",tulajdonos,gyalogosKep);
				    	terulet.addEgyseg(gyalogosE);
				    }
				    
				    line = bf.readLine();
				    int lovag = Integer.parseInt(line);
				    for (int i= 0; i<lovag; i++)
				    {
				    	Image lovagKep = getImage(getCodeBase(),"res/Lovag.png");
				    	Egyseg lovagE = new Egyseg("Lovag",tulajdonos,lovagKep);
				    	terulet.addEgyseg(lovagE);
				    }
				    
				    line = bf.readLine();
				    
				    while (true)
				    {
				    	line = bf.readLine();
				    	if(line.equals("</szomszed>")) break;
				    	
				    	terulet.addSzomszed(line);
				    	
				    }
				    
				}
			}
			
			else if (line.equals("<tenger>"))
			{
				while(true)
				{
					
					line = bf.readLine();
					if (line.equals("</tenger>")) break;
					String nev = line;
					
					Image kep = getImage(getCodeBase(),"res/"+nev+".png");
			        
					 Haz tulajdonos = Tabla.getHaz(bf.readLine());
					
					
				    Tenger tenger = new Tenger(nev, kep, tulajdonos);
				    Tabla.teruletek.add(tenger);
				    
				    line = bf.readLine();
				   
				    
				    line = bf.readLine();
				    
				    
				    line = bf.readLine();
				    int hajo = Integer.parseInt(line);
				    for (int i= 0; i<hajo; i++)
				    {
				    	Image hajoKep = getImage(getCodeBase(),"res/Hajo.png");
				    	Egyseg hajoE = new Egyseg("Hajo",tulajdonos,hajoKep);
				    	tenger.addEgyseg(hajoE);
				    }
				    
				    while (true)
				    {
				    	line = bf.readLine();
				    	if(line.equals("</szomszed>")) break;
				    	tenger.addSzomszed(line);
				    	
				    }
				    
				    
				}
			}
		
		}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Szomszédok legenerálása
		Iterator<Tenger> teruletek = Tabla.teruletek.iterator();		
		while (teruletek.hasNext())
		{
			teruletek.next().generateSzomszedok();
		}
		
		//System.out.println(Tabla.teruletek.toString());
		
		Tabla.setHordo();
		
		
		setSize(800, 600);
		initImgTeruletek();
		offScrImage = createImage(this.getWidth(), imgDefTerkep.getHeight(this));

	    offScrGr = offScrImage.getGraphics();
	    
	    wheelListener = new mousewheelListener();
	    this.addMouseWheelListener(wheelListener);
	    this.addMouseListener(mouseListener);
	    motionListener = new mousemotionListener();
	    this.addMouseMotionListener(motionListener);
	    keyListener kbListener = new keyListener();
	    this.addKeyListener(kbListener);
	    
		
      }
	
	public void paint(Graphics g)
	  {
		
		//Kirajzoljuk az alaptérképet
		offScrGr.drawImage(imgTerkep,0-terkepX,0-terkepY,this);
		/*for (int i=0; i<terkep.length;i++)
		{
			offScrGr.drawImage(terkep[i],0, 0, 100, 100,this);
		}*/
		//offScrGr.drawImage(terkep[0],0, 0,(int)terkep[0].getWidth(this)/4,(int)terkep[0].getHeight(this)/4,this);
		
		//offScrGr.drawImage(terkepZoom,0,0,this);
		
		g.drawImage(offScrImage, 0, 0, this);
		
	  
	  }

	
	public void initImgTeruletek()
	{
		imgDefTerkep = getImage(getCodeBase(), "res/hatar2.jpg");
		tracker.addImage(imgDefTerkep, 0);
		
		imgTeruletek = new Image[Tabla.teruletek.size()];
        Iterator<Tenger> it = Tabla.teruletek.iterator();
	    
	    for (int i=0; i<imgTeruletek.length; i++)
	    {
	    	imgTeruletek[i] = it.next().getKep();
	    	tracker.addImage(imgTeruletek[i], 0);

	         // Start downloading the image and wait until it finishes loading. 
	         try { 
	             tracker.waitForAll(); 
	         } 
	         catch(InterruptedException e) {}
	    }
	    imgTerkep = imgDefTerkep;
	    terkepZoom=1;
	    terkepXYRatio = (double)imgDefTerkep.getWidth(this)/(double)imgDefTerkep.getHeight(this);
	    newTerkep();
	}
		
	class mousewheelListener implements MouseWheelListener {
		
		private static final int UP = 1;

	    private static final int DOWN = 2;
	    
	    private int direction;

	    public void mouseWheelMoved(MouseWheelEvent e) {
	      int count = e.getWheelRotation();
	      direction = (count > 0) ? UP : DOWN;
	      performZoom(direction);
	      
	    }
	    
	    public void performZoom(int direction) {

	      if (direction == UP) {
	    	  
	    	  if (terkepZoom<4){
	    		  
	    		  terkepZoom += 0.1;
	    		  newTerkep();
	    		  
	    	  }  	  
	        
	      } else {
	    	  
	    	  if (terkepZoom>1){
	    		  
	    		  terkepZoom -= 0.1;
	    		  newTerkep();
	    		   
	    	  }
	    	        
	      }

	      
	    }
	  };
	  
	MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}};
		
	class mousemotionListener implements MouseMotionListener {

			public void mouseDragged(MouseEvent arg0) {
				
				performDrag(arg0.getX(), arg0.getY());
				// TODO Auto-generated method stub
			}
		    	  
		    public void performDrag(int x,int y)
		    {
		    	if (mY>y) terkepY+=5;
		    	  else if (mY<y) terkepY-=5;
		    	  mY = y;
		      
		    	  if (mX>x) terkepX+=5;
		    	  else if (mX<x) terkepX-=5;   
		    	  mX = x;
		    
		      
		      
		      //Térkép Teteje,Alja között mehet csak
		      if (terkepY<=0) terkepY = 0;
		      else if (imgTerkep.getHeight(got)-got.getHeight() <= terkepY) terkepY = (int) Math.round(imgTerkep.getHeight(got)-got.getHeight());
		     
		      
		      if (terkepX<=0) terkepX = 0;
		      else if (imgTerkep.getWidth(got)-got.getWidth() <= terkepX) terkepX = (int) Math.round(imgTerkep.getWidth(got)-got.getWidth());
		      
		   
		    }

			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				mX = arg0.getX();
				mY = arg0.getY();
				
			}};
			
	public class keyListener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
			switch(e.getKeyChar())
			{
			
			case '+':
			{
				wheelListener.performZoom(1);
				break;
			}
			
			case '-':
			{
				wheelListener.performZoom(2);
				break;
			}
			
			case 'w':
			{
				motionListener.performDrag(mX, mY+1);
				break;
			}
			
			case 'a':
			{
				motionListener.performDrag(mX+1, mY);
				break;
			}
			
			case 's':
			{
				motionListener.performDrag(mX, mY-1);
				break;
			}
			
			case 'd':
			{
				motionListener.performDrag(mX-1, mY);
				break;
			}
			
			
			}
			
		}
		
	};
	
    public void newTerkep()
	{
				
    	int x,y;
		if (terkepZoom>1){
			x = terkepX;
			y = terkepY;
			
		}
		else{
			x = 0;
			y = 0;
		} 

	    //Levágás

	    	int w =(int) Math.round((double)imgDefTerkep.getWidth(this)/terkepZoom);
			int h = (int) Math.round((double)imgDefTerkep.getHeight(this)/terkepZoom);
			
			ImageFilter crop = new CropImageFilter(x, y,w,h);
	    	imgTerkep = createImage(new FilteredImageSource(imgDefTerkep.getSource(), crop));
	    
	    //Átméretezés
	    int newX = got.getWidth();
	    int newY = (int) Math.round(newX * terkepXYRatio);
	    ImageFilter scale = new ReplicateScaleFilter(newX,newY);
	    imgTerkep = createImage(new FilteredImageSource(imgTerkep.getSource(),scale));

	}
			
			public void run()
			{
				Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				
				while ( kicker != null )
				{
				      repaint();
			   }
						
			}
			
			public void update(Graphics g) {
				
			    paint(g);

			}
			
			public void start() {

			    if (kicker == null) { kicker = new Thread(this); kicker.start(); }

			}

			public void stop() {

			    kicker = null;

			}
	
	}
