import java.io.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.applet.*;
import java.net.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TronokHarca extends Applet implements Runnable {
	

Thread kicker = null;
//Zene
AudioClip zene;

Tabla tabla;

//Akkor indul csak el a progi ha minden betöltõdött
MediaTracker tracker;
	
	
//res mappa behúzása, file.got feldolgozása
public void initRes()
{
	
    int locX=0,locY=0;
	tracker = new MediaTracker(this);
    
	URL fileGot = null;
	  
	  try{ URL u1=new URL(getCodeBase(),"res/zene.au");
	    zene=getAudioClip(u1);
	    //zene.loop();
	  }
	  catch(Exception  e){
		   System.out.println(e);
		    }
	  
	  
	  Tabla.kep = getImage(getCodeBase(), "res/hatar.png");
	  tracker.addImage(Tabla.kep, 0);
	  
	  Image gyalogosKep = getImage(getCodeBase(), "res/gyalog.png");
	  tracker.addImage(gyalogosKep, 0);
	  Image lovagKep = getImage(getCodeBase(), "res/lovag.png");
	  tracker.addImage(lovagKep, 0);
	  Image hajoKep = getImage(getCodeBase(), "res/hajo.png");
	  tracker.addImage(hajoKep, 0);
	  
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
			    tracker.addImage(kep, 0);
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
				
				URL url = new URL(getCodeBase(),"res/"+nev+".png");
				BufferedImage kep = ImageIO.read(url);
				tracker.addImage(kep, 0);
		        
				line = bf.readLine();
			    int varak = Integer.parseInt(line);
			    
			    line = bf.readLine();
			    int hordok = Integer.parseInt(line);
			    
			    line = bf.readLine();
			    int korona = Integer.parseInt(line);
			    
			    
			    Haz tulajdonos = Tabla.getHaz(bf.readLine());
			    
			    Terulet terulet = new Terulet(nev, varak, hordok, korona, tulajdonos, kep);
			    Tabla.teruletek.add(terulet);
			    //add(terulet);
			    
			    line = bf.readLine();
			    int gyalogos = Integer.parseInt(line);
			    for (int i= 0; i<gyalogos; i++)
			    {
		
			    	Egyseg gyalogosE = new Egyseg("Gyalogos",tulajdonos,gyalogosKep);
			    	terulet.addEgyseg(gyalogosE);
			    }
			    
			    line = bf.readLine();
			    int lovag = Integer.parseInt(line);
			    for (int i= 0; i<lovag; i++)
			    {
	
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
				
				URL url = new URL(getCodeBase(),"res/"+nev+".png");
				BufferedImage kep = ImageIO.read(url);
				tracker.addImage(kep, 0);
				
		        
				Haz tulajdonos = Tabla.getHaz(bf.readLine());
				
			    Tenger tenger = new Tenger(nev, kep, tulajdonos);
			    Tabla.teruletek.add(tenger);	
			    add(tenger);
			    tenger.setSize(new Dimension(100,100));
			    tenger.setLocation(locX, locY);
			    locX += tenger.getSize().width;
			    locY += tenger.getSize().height;
			    
			    
			    line = bf.readLine();
			   
			    
			    line = bf.readLine();
			    
			    
			    line = bf.readLine();
			    int hajo = Integer.parseInt(line);
			    for (int i= 0; i<hajo; i++)
			    {
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
	
	Tabla.setHordo();
	
	try {
		tracker.waitForAll();
		
	} catch ( InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if (tracker.isErrorAny()) System.out.println("Hiba a képek betöltésekor!");
	
	//Egy kis takarítás
	tracker = null;
	fileGot = null;
	in = null;
	teruletek = null;
	System.gc();
	
}


//az applet elõkészítése	  
public void init()
     {

	   setLayout(null);
	   
		tabla = new Tabla();
	
	   initRes();
	   setPreferredSize(new Dimension(800,600));
	   setSize(800,600);
	   
	   

	   
	   
	  
	   
      }
	
public void paint(Graphics g)
	  {
		
	  	//g.drawImage(Tabla.kep, 0, 0, this);
	  }


public void run()
	{
				//Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				
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
