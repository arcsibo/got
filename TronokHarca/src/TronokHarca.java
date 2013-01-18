import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class TronokHarca extends Applet{
	

//Az alkalmazás méretei
final int W = 1024;
final int H = 600;
final int korrigálás = 626;

//Skálázható minden grafikai elem, mindent méretet az ablak méretébõl számolunk
final double hazR = W/12;
final double parancsjR = W/20;
final double hazjR = W/20;
double teruletR1, teruletR2;
final double egysegR = W/20;
final double cuccosR = W/20;

final double aktHazPanelR = hazR;
final double jatekPanelR = W/4;

final double terkepR = W - (aktHazPanelR + jatekPanelR);


//3 panelre osztható a játék képernyõje
JPanel aktHazPanel, jatekPanel;	
	
//Lógeci 
//Zene
AudioClip zene;

Image kardKep, holloKep, vastronKep;

Tabla tabla;

//A képek betöltõdését figyelhetjük vele
MediaTracker tracker;

//res mappa behúzása, file.got feldolgozásam, etc
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
	  
	  
	  Image tablakep = getImage(getCodeBase(), "res/hatar2.png");
	  tracker.addImage(tablakep, 0);
	  loading();
	  double origW = tablakep.getWidth(null);
	  double origH = tablakep.getHeight(null); 
	  tablakep = scaledImage(tablakep,terkepR);
	  tracker.addImage(tablakep, 0);
	  loading();

	  tabla = new Tabla(tablakep,(int) Math.round(aktHazPanelR));
	  
	  teruletR1 = (double)Tabla.kep.getWidth(null)/origW;
	  teruletR2 = (double)Tabla.kep.getHeight(null)/origH;
	  
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
	  
	  kardKep = getImage(getCodeBase(), "res/kard.png");
	  tracker.addImage(kardKep, 0);
	  loading();
	  kardKep = scaledImage(kardKep,egysegR);
	  
	  holloKep = getImage(getCodeBase(), "res/hollo.png");
	  tracker.addImage(holloKep, 0);
	  loading();
	  holloKep = scaledImage(holloKep,egysegR);
	  
	  vastronKep = getImage(getCodeBase(), "res/tron.png");
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
				
				Image kep = getImage(getCodeBase(), "res/"+line+".jpg");//ház icon
				Image kepp = getImage(getCodeBase(), "res/"+line+"p.jpg");//parancsjelzõ lefele fordított
				Image keph = getImage(getCodeBase(),"res/"+line+"h.jpg");
				
			    tracker.addImage(kep, 0);
			    loading();
			    kep = scaledImage(kep,hazR);
			    Haz haz = new Haz(line,kep,kepp,keph);
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
				
				int x = Integer.parseInt(bf.readLine()) - korrigálás;
				
				double X = x / origW;
				double Y = Integer.parseInt(bf.readLine()) / origH;
				
				URL url = new URL(getCodeBase(),"res/"+nev+".png");
				Image kep = getImage(url);
				tracker.addImage(kep, 0);
				loading();
				if (tracker.isErrorAny()) System.out.println(nev);
				kep = scaledImage2(kep,teruletR1, teruletR2);
		        
				line = bf.readLine();
			    int varak = Integer.parseInt(line);
			    
			    line = bf.readLine();
			    int hordok = Integer.parseInt(line);
			    
			    line = bf.readLine();
			    int korona = Integer.parseInt(line);
			    
			    
			    Haz tulajdonos = Tabla.getHaz(bf.readLine());
			    
			    Terulet terulet = new Terulet(nev, varak, hordok, korona, tulajdonos, kep,X,Y);
			    Tabla.teruletek.add(terulet);
			    
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
				
				int x =  Integer.parseInt(bf.readLine()) - korrigálás;
				double X = x / origW;
				double Y = Integer.parseInt(bf.readLine()) / origH;
				
				URL url = new URL(getCodeBase(),"res/"+nev+".png");
				Image kep = getImage(url);
				tracker.addImage(kep, 0);
				loading();
				if (tracker.isErrorAny()) System.out.println(nev);
				kep = scaledImage2(kep,teruletR1, teruletR2);
				
		        
				Haz tulajdonos = Tabla.getHaz(bf.readLine());
				
			    Tenger tenger = new Tenger(nev, kep, tulajdonos,X,Y);
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

//beállítjuk az appletet, elhelyezzük a grafikai elemeket
public void init()
{
	
	initRes();
	Tabla.kovHaz();
	
	setSize(new Dimension(W,H));
	setPreferredSize(new Dimension(W,H));
	//setLayout(new BorderLayout());
	setLayout(null);
	
	aktHazPanel = new JPanel();
	aktHazPanel.setLayout(new BorderLayout());
	
	JLabel aktHazKep = new JLabel(new ImageIcon(Tabla.aktHaz.getKep()));
	aktHazPanel.add(aktHazKep,"North");
	
	Vector<Hazjelzo> vectorJelzok = Tabla.aktHaz.getJezok();
	Iterator<Hazjelzo> itJelzo = vectorJelzok.iterator();
	while(itJelzo.hasNext())
	{
		Hazjelzo aktJelzo = itJelzo.next();
		aktJelzo.setBounds(50, 200, aktJelzo.getKep().getWidth(null), aktJelzo.getKep().getHeight(null));
		aktHazPanel.add(aktJelzo);
	}
	
	
	jatekPanel = new JPanel();
	jatekPanel.setLayout(new FlowLayout());
	
	jatekPanel.add(new JLabel(new ImageIcon(vastronKep)));
	Iterator<Haz> itHaz = Tabla.vastron.iterator();
	
	//Az aktuális ház ide nem kell
	
	while (itHaz.hasNext())
	{	
		Haz aktHaz = itHaz.next();
		JLabel aktLabel = new JLabel(new ImageIcon(aktHaz.getKep()));
		if (aktHaz != Tabla.aktHaz) jatekPanel.add(aktLabel,"West");
	}
		
	
	aktHazPanel.setBounds(0,0,(int) Math.round(aktHazPanelR),H);
	tabla.setBounds((int) Math.round(aktHazPanelR), 0, tabla.kep.getWidth(null), tabla.kep.getHeight(null));
	jatekPanel.setBounds((int) Math.round(aktHazPanelR)+tabla.kep.getWidth(null),0,(int)Math.round(jatekPanelR), H);
	
	
	tabla.placeTeruletek();
	

	add(aktHazPanel);
	add(tabla);
	add(jatekPanel);

}

public Image scaledImage(Image img,double ratio) {
	
	
	double WHratio = (double)img.getWidth(null)/(double)img.getHeight(null);
	
	Image newImage = img.getScaledInstance((int)Math.round(ratio), (int)Math.round(ratio/WHratio), Image.SCALE_SMOOTH);
	
	return newImage;
}

public Image scaledImage2(Image img,double ratio1, double ratio2) {
	
	
	Image newImage = img.getScaledInstance((int)Math.round(ratio1*img.getWidth(this)), (int)Math.round(ratio2*img.getHeight(this)), Image.SCALE_SMOOTH);
	
	return newImage;
}


//Megvárjuk, amíg betöltõdik a kép
public void loading() {
	try {
		tracker.waitForAll();
		
	} catch ( InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}