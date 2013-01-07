import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.util.*;

public class TronokHarca extends Applet implements Runnable {
	
	
  Thread kicker = null;

  Image terkep[] ,offScrImage, hatar;

  Graphics offScrGr;
  
  MediaTracker tracker = new MediaTracker(this); 

	
	public void init()
      {
    	  URL fileGot = null;
    	  
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
		
		Iterator<Tenger> teruletek = Tabla.teruletek.iterator();
		
		while (teruletek.hasNext())
		{
			teruletek.next().generateSzomszedok();
		}
		
		//System.out.println(Tabla.teruletek.toString());
		
		Tabla.setHordo();
		
		initTerkep();
		
		
		setSize(800, 600);
		offScrImage = createImage(800, 600);

	    offScrGr = offScrImage.getGraphics();
	    
	    for (int i=0; i<terkep.length;i++)
		{
			System.out.println(terkep[i].getHeight(this));
		}
		
      }
	
	public void paint(Graphics g)
	  {
		
		
		
		offScrGr.drawImage(hatar,0,0,(int)hatar.getWidth(this)/2,(int)hatar.getHeight(this)/2,this);
		/*for (int i=0; i<terkep.length;i++)
		{
			offScrGr.drawImage(terkep[i],0, 0, 100, 100,this);
		}*/
		
		
		g.drawImage(offScrImage, 0, 0, this);
	  
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
	
	public void initTerkep()
	{
		hatar = getImage(getCodeBase(), "res/hatar1.jpg");
		tracker.addImage(hatar, 0);
		
		terkep = new Image[Tabla.teruletek.size()];
        Iterator<Tenger> it = Tabla.teruletek.iterator();
	    
	    for (int i=0; i<terkep.length; i++)
	    {
	    	terkep[i] = it.next().getKep();
	    	tracker.addImage(terkep[i], 0);

	         // Start downloading the image and wait until it finishes loading. 
	         try { 
	             tracker.waitForAll(); 
	         } 
	         catch(InterruptedException e) {}
	    }
	}
	
	public void start() {

	    if (kicker == null) { kicker = new Thread(this); kicker.start(); }

	    }



	public void stop() {

	    kicker = null;

	    }
	
	
	}
