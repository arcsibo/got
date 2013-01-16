import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class TronokHarca extends Applet{
	

final int W = 800;
final int H = 600;
	
JPanel aktHazPanel, terkepPanel, jatekPanel;	
	
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
	  
	  
	  Image tablakep = getImage(getCodeBase(), "res/hatar.png");
	  tracker.addImage(Tabla.kep, 0);
	  tabla = new Tabla(tablakep);
	  
	  Tabla.dummyKep = tablakep.getScaledInstance(1, 1, Image.SCALE_SMOOTH);
	  
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
				Image kep = getImage(url);
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
				Image kep = getImage(url);
				tracker.addImage(kep, 0);
				
		        
				Haz tulajdonos = Tabla.getHaz(bf.readLine());
				
			    Tenger tenger = new Tenger(nev, kep, tulajdonos);
			    Tabla.teruletek.add(tenger);	
			    
			    
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


public void init()
{
	
	initRes();
	
	setSize(new Dimension(W,H));
	setPreferredSize(new Dimension(W,H));
	setLayout(new BorderLayout());
	
	aktHazPanel = new JPanel();
	aktHazPanel.setLayout(new BorderLayout());
	
	JLabel aktHazKep = new JLabel(new ImageIcon(Tabla.aktHaz.getKep().getScaledInstance(W/10, H/10, Image.SCALE_SMOOTH)));
	aktHazPanel.add(aktHazKep,"North");
	
	
	terkepPanel = new JPanel();
	terkepPanel.setLayout(new BorderLayout());
	
	JLabel terkep = new JLabel(new ImageIcon(Tabla.kep.getScaledInstance(W, H, Image.SCALE_SMOOTH)));
	terkepPanel.add(terkep,"Center");
	
	
	jatekPanel = new JPanel();
	jatekPanel.setLayout(new FlowLayout());
	
	Iterator<Haz> it = Tabla.vastron.iterator();
	
	//Az aktuális ház ide nem kell
	
	while (it.hasNext())
	{	
		Image aktKep = it.next().getKep();
		ImageIcon aktIkon = new ImageIcon(aktKep.getScaledInstance(W/20, H/20, Image.SCALE_SMOOTH));
		
		
		JLabel aktLabel = new JLabel(aktIkon);
		jatekPanel.add(aktLabel,"West");
	}
		
	
	add(aktHazPanel,"West");
	add(terkepPanel,"Center");
	add(jatekPanel,"East");

}

}