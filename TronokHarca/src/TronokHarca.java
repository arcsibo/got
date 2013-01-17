import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class TronokHarca extends Applet{
	

//Az alkalmaz�s m�retei
final int W = 800;
final int H = 600;

//Sk�l�zhat� minden grafikai elem, mindent m�retet az ablak m�ret�b�l sz�molunk
final double hazR = W/20;
final double parancsjR = W/20;
final double hazjR = W/20;
final double terkepR = W/2;
double teruletR = W/20;
final double egysegR = W/20;
final double cuccosR = W/20;
	

//3 panelre oszthat� a j�t�k k�perny�je
JPanel aktHazPanel, jatekPanel;	
	
//L�geci 
//Zene
AudioClip zene;

Image kardKep, holloKep, vastronKep;

Tabla tabla;

//A k�pek bet�lt�d�s�t figyelhetj�k vele
MediaTracker tracker;

//res mappa beh�z�sa, file.got feldolgoz�sam, etc
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
	  tracker.addImage(tablakep, 0);
	  loading();
	  double origW = tablakep.getWidth(null);  
	  tablakep = scaledImage(tablakep,terkepR);
	  tracker.addImage(tablakep, 0);
	  loading();
	  teruletR = terkepR*(double)tablakep.getWidth(null)/origW;
	  

	  tabla = new Tabla(tablakep);
	  
	  Tabla.dummyKep = tablakep.getScaledInstance(1, 1, Image.SCALE_SMOOTH);
	  
	  Image gyalogosKep = getImage(getCodeBase(), "res/gyalog.png");
	  tracker.addImage(gyalogosKep, 0);
	  loading();
	  gyalogosKep = scaledImage(gyalogosKep,egysegR);
	  
	  Image lovagKep = getImage(getCodeBase(), "res/lovag.png");
	  tracker.addImage(lovagKep, 0);
	  loading();
	  lovagKep = scaledImage(lovagKep,egysegR);
	  
	  Image hajoKep = getImage(getCodeBase(), "res/hajo.png");
	  tracker.addImage(hajoKep, 0);
	  loading();
	  hajoKep = scaledImage(hajoKep,egysegR);
	  
	  kardKep = getImage(getCodeBase(), "res/gyalog.png");
	  tracker.addImage(kardKep, 0);
	  loading();
	  kardKep = scaledImage(kardKep,egysegR);
	  
	  holloKep = getImage(getCodeBase(), "res/gyalog.png");
	  tracker.addImage(holloKep, 0);
	  loading();
	  holloKep = scaledImage(holloKep,egysegR);
	  
	  vastronKep = getImage(getCodeBase(), "res/gyalog.png");
	  tracker.addImage(vastronKep, 0);
	  loading();
	  vastronKep = scaledImage(vastronKep,egysegR);
	  
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
			    loading();
			    kep = scaledImage(kep,hazR);
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
				loading();
				kep = scaledImage(kep,teruletR);
		        
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
				loading();
				kep = scaledImage(kep,teruletR);
				
		        
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
	
	//Szomsz�dok legener�l�sa
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
	if (tracker.isErrorAny()) System.out.println("Hiba a k�pek bet�lt�sekor!");
	
	//Egy kis takar�t�s
	tracker = null;
	fileGot = null;
	in = null;
	teruletek = null;
	System.gc();
	
}

//be�ll�tjuk az appletet, elhelyezz�k a grafikai elemeket
public void init()
{
	
	initRes();
	
	setSize(new Dimension(W,H));
	setPreferredSize(new Dimension(W,H));
	setLayout(new BorderLayout());
	
	aktHazPanel = new JPanel();
	aktHazPanel.setLayout(new BorderLayout());
	
	JLabel aktHazKep = new JLabel(new ImageIcon(Tabla.aktHaz.getKep()));
	aktHazPanel.add(aktHazKep,"North");
	
	jatekPanel = new JPanel();
	jatekPanel.setLayout(new FlowLayout());
	
	Iterator<Haz> itHaz = Tabla.vastron.iterator();
	
	//Az aktu�lis h�z ide nem kell
	
	while (itHaz.hasNext())
	{	
		
		JLabel aktLabel = new JLabel(new ImageIcon(itHaz.next().getKep()));
		jatekPanel.add(aktLabel,"West");
	}
		
	
	add(aktHazPanel,"West");
	tabla.placeTeruletek();
	add(tabla,"Center");
	add(jatekPanel,"East");

}

public Image scaledImage(Image img,double ratio) {
	
	
	double WHratio = (double)img.getWidth(null)/(double)img.getHeight(null);
	
	Image newImage = img.getScaledInstance((int)Math.round(ratio), (int)Math.round(ratio/WHratio), Image.SCALE_SMOOTH);
	
	return newImage;
}

//Megv�rjuk, am�g bet�lt�dik a k�p
public void loading() {
	try {
		tracker.waitForAll();
		
	} catch ( InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}