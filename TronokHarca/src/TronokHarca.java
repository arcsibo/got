import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class TronokHarca extends Applet{
		
//Az alkalmazás méretei
final int W = 1300;
final int H = 600;
final int korrigalas = 626;

//Skálázható minden grafikai elem, mindent méretet az ablak méretébõl számolunk
final double hazR = W/12;
final double parancsjR = W/25;
final double hazjR = W/40;
final double tronR = W/30;
double teruletR1, teruletR2;
final double egysegR = W/45;
final double cuccosR = W/20;
final double vhkR = W/45;
final double iR = W/200;
private int hjelzoPos;

final double aktHazPanelR = hazR+iR*2;
final double jatekPanelR = W/8;

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

//res mappa behúzása, file.got feldolgozásam, etc // kezdeti feltöltés
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
	  kardKep = scaledImage(kardKep,hazR);
	  
	  holloKep = getImage(getCodeBase(), "res/hollo.png");
	  tracker.addImage(holloKep, 0);
	  loading();
	  holloKep = scaledImage(holloKep,hazR);
	  
	  vastronKep = getImage(getCodeBase(), "res/tron.png");
	  tracker.addImage(vastronKep, 0);
	  loading();
	  vastronKep = scaledImage(vastronKep,tronR);
	  
	  Image var1Kep = getImage(getCodeBase(), "res/var1.png");
	  tracker.addImage(var1Kep, 0);
	  loading();
	  var1Kep = scaledImage(var1Kep,vhkR);
	  
	  Image var2Kep = getImage(getCodeBase(), "res/var2.png");
	  tracker.addImage(var2Kep, 0);
	  loading();
	  var2Kep = scaledImage(var2Kep,vhkR);
	  
	  Image hordoKep = getImage(getCodeBase(), "res/hordo.png");
	  tracker.addImage(hordoKep, 0);
	  loading();
	  hordoKep = scaledImage(hordoKep,vhkR);
	  
	  Image koronaKep = getImage(getCodeBase(), "res/korona.png");
	  tracker.addImage(koronaKep, 0);
	  loading();
	  koronaKep = scaledImage(koronaKep,vhkR);
	  
	  
	  for (int i=1; i<=3;i++)
	  {
		  Image kep0 =   getImage(getCodeBase(),"res/mozgas"+i+".png");
		  tracker.addImage(kep0, 0);
		  Tabla.parancsjelzok.add(kep0);
		  Image kep1 = getImage(getCodeBase(),"res/vedekez"+i+".png");
		  tracker.addImage(kep0, 0);
		  Tabla.parancsjelzok.add(kep1);
		  Image kep2 = getImage(getCodeBase(),"res/tamogatas"+i+".png");
		  tracker.addImage(kep0, 0);
		  Tabla.parancsjelzok.add(kep2);
		  Image kep3 = getImage(getCodeBase(),"res/korona"+i+".png");
		  tracker.addImage(kep0, 0);
		  Tabla.parancsjelzok.add(kep3);
		  Image kep4 = getImage(getCodeBase(),"res/portya"+i+".png");
		  tracker.addImage(kep0, 0);
		  Tabla.parancsjelzok.add(kep4);
	  }
	  loading();
	  
	  
	  for (int i=0; i<15; i++)
	  {
		  Image img = Tabla.parancsjelzok.get(i);
		  img = scaledImage(Tabla.parancsjelzok.get(i),parancsjR);
		  Tabla.parancsjelzok.remove(i);
		  Tabla.parancsjelzok.add(i, img);
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
				
				Image kep = getImage(getCodeBase(), "res/"+line+".jpg");//ház icon
				Image kepp = getImage(getCodeBase(), "res/"+line+"p.png");//parancsjelzõ lefele fordított
				Image keph = getImage(getCodeBase(),"res/"+line+"h.png");
				
			    tracker.addImage(kep, 0);
			    tracker.addImage(kepp, 0);
			    tracker.addImage(keph, 0);
			    loading();
			    kep = scaledImage(kep,hazR);
			    kepp = scaledImage(kepp,parancsjR);
			    keph = scaledImage(keph,hazjR);
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
				
				int x = Integer.parseInt(bf.readLine()) - korrigalas;
				
				double X = x / origW;
				double Y = Integer.parseInt(bf.readLine()) / origH;
				
				URL url = new URL(getCodeBase(),"res/"+nev+".png");
				Image kep = getImage(url);
				tracker.addImage(kep, 0);
				loading();
				if (tracker.isErrorAny()) System.out.println(nev);
				kep = scaledImage2(kep,teruletR1, teruletR2);
		        
				
				Vector<VHK> vhk = new Vector<VHK>();
				
				line = bf.readLine();
			    int varak = Integer.parseInt(line);
			    
			    if (varak==1)
			    {
			    	double varX = Double.parseDouble(bf.readLine());
			    	double varY = Double.parseDouble(bf.readLine());
			    	vhk.add(new VHK(varX,varY,var1Kep));
			    }
			    
			    else if (varak==2)
			    {
			    	double varX = Double.parseDouble(bf.readLine());
			    	double varY = Double.parseDouble(bf.readLine());
			    	vhk.add(new VHK(varX,varY,var2Kep));
			    }
			    	
			    line = bf.readLine();
			    int hordok = Integer.parseInt(line);
			    
			    for (int i= 0; i<hordok; i++)
			    {
			    	
			    	double hordoX = Double.parseDouble(bf.readLine());
			    	double hordoY = Double.parseDouble(bf.readLine());
			    	vhk.add(new VHK(hordoX,hordoY,hordoKep));
			    	
			    }
			    
			    line = bf.readLine();
			    int korona = Integer.parseInt(line);
			    
			    for (int i= 0; i<korona; i++)
			    {
			    	
			    	double koronaX = Double.parseDouble(bf.readLine());
			    	double koronaY = Double.parseDouble(bf.readLine());
			    	vhk.add(new VHK(koronaX,koronaY,koronaKep));
			    	
			    }
			    
			    
			    Haz tulajdonos = Tabla.getHaz(bf.readLine());
			    
			    Terulet terulet = new Terulet(nev, varak, hordok, korona, tulajdonos, kep,X,Y,vhk);
			    terulet.getNev();
			    Tabla.teruletek.add(terulet);
			    
			    
			    line = bf.readLine();
			    int gyalogos = Integer.parseInt(line);
			    for (int i= 0; i<gyalogos; i++)
			    {
			    	double gyalogosX = Double.parseDouble(bf.readLine());
			    	double gyalogosY = Double.parseDouble(bf.readLine());
			    	Egyseg gyalogosE = new Egyseg("Gyalogos",tulajdonos,gyalogosKep,gyalogosX,gyalogosY);
			    	terulet.addEgyseg(gyalogosE,true);
			    }
			    
			    line = bf.readLine();
			    int lovag = Integer.parseInt(line);
			    for (int i= 0; i<lovag; i++)
			    {    	
			    	double lovagX = Double.parseDouble(bf.readLine());
			    	double lovagY = Double.parseDouble(bf.readLine());
			    	Egyseg lovagE = new Egyseg("Lovag",tulajdonos,lovagKep,lovagX,lovagY);
			    	terulet.addEgyseg(lovagE,true);
			    }
			    
			    line = bf.readLine();
			    
			    terulet.placeVHK();
			    
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
				
				int x =  Integer.parseInt(bf.readLine()) - korrigalas;
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
			    	double hajoX = Double.parseDouble(bf.readLine());
			    	double hajoY = Double.parseDouble(bf.readLine());
			    	Egyseg hajoE = new Egyseg("Hajo",tulajdonos,hajoKep,hajoX,hajoY);
			    	tenger.addEgyseg(hajoE,true);
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

//beállítjuk az appletet, elhelyezzük a grafikai elemeket //kezdeti beállítások
public void init()
{
	
	Tabla.got = this;
	Tabla.H = H;
	initRes();
	Tabla.kovHaz();
	
	setSize(new Dimension(W,H));
	setPreferredSize(new Dimension(W,H));
	setLayout(null);
	
	aktHazPanel = new JPanel();
	aktHazPanel.setLayout(null);
	
	updateHaz();
	
	jatekPanel = new JPanel();
	jatekPanel.setLayout(null);
	
	updateJatekPanel();
	
	aktHazPanel.setBounds(0,0,(int) Math.round(aktHazPanelR),H);
	
	jatekPanel.setBounds((int) Math.round(aktHazPanelR)+tabla.kep.getWidth(null),0,(int)Math.round(jatekPanelR), H);
	
	tabla.setBounds((int) Math.round(aktHazPanelR), 0, tabla.kep.getWidth(null), tabla.kep.getHeight(null));
	tabla.placeTeruletek();
	

	add(aktHazPanel);
	add(tabla);
	add(jatekPanel);
	
    
    System.out.println("INDUL A JÁTÉK");
    System.out.println("TERVEZÉS");


}

//kovhaz gombnak az akciója
ActionListener kovGombAction = new ActionListener() {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// TODO Auto-generated method stub
		if (Tabla.parancsjelzoLerakas || Tabla.hazjelzoLerakas) return;
		if(Tabla.AKCIO && Tabla.portyazas)
		{//
			Tabla.tamadoTerulet = null;
			Tabla.tamadTer.removeAllElements();
			Tabla.segedEgy.removeAllElements();
		}
		if (Tabla.AKCIO && Tabla.tamadas)
		{
			Tabla.tamadoTerulet = null;
			Tabla.segedEgy.removeAllElements();
			Tabla.tamadTer.removeAllElements();
		}
		Tabla.vizsgalatKorok();
		Tabla.kovHaz();
		updateHaz();
		
	}
	
};

public void updateHaz()
{
	aktHazPanel.removeAll();
	aktHazPanel.repaint();
	
	JButton kovGomb = new JButton(new ImageIcon(Tabla.aktHaz.getKep()));
	kovGomb.addActionListener(kovGombAction);
	kovGomb.setBounds((int)iR, 0, Tabla.aktHaz.getKep().getWidth(null),Tabla.aktHaz.getKep().getHeight(null));
	
	aktHazPanel.add(kovGomb);
	
	//JLabel aktHazKep = new JLabel(new ImageIcon(Tabla.aktHaz.getKep()));
	//aktHazKep.setBounds(0, 0, Tabla.aktHaz.getKep().getWidth(null),Tabla.aktHaz.getKep().getHeight(null));
	//aktHazPanel.add(aktHazKep);
	updatePJelzok();
	updateHJelzok();

	
	
}

public void updatePJelzok()
{
	int y = Tabla.aktHaz.getKep().getHeight(null);
	int x = (int) Math.round(iR);
	
	Iterator<Parancsjelzo> itPJelzo = Tabla.aktHaz.getPjelzok().iterator();
	
	int szamok[] = {0,0,0,0,0,0,0,0,0,0,0};
	while(itPJelzo.hasNext())
	{
		Parancsjelzo aktJelzo = itPJelzo.next();
		aktJelzo.setBounds((int)Math.round(aktJelzo.X*W)+x, (int)Math.round(aktJelzo.Y*H), aktJelzo.getKep().getWidth(null), aktJelzo.getKep().getHeight(null));
		aktHazPanel.add(aktJelzo);
		
		y += aktJelzo.getKep().getHeight(null);
		
		if (aktJelzo.getKep() == Tabla.parancsjelzok.get(0)) szamok[0]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(5)) szamok[1]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(10)) szamok[2]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(1)) szamok[3]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(11)) szamok[4]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(2)) szamok[5]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(12)) szamok[6]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(3)) szamok[7]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(13)) szamok[8]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(4)) szamok[9]++;
		else if (aktJelzo.getKep() == Tabla.parancsjelzok.get(14)) szamok[10]++;
		

	}
	x = 0;
	y=Tabla.aktHaz.getKep().getHeight(null);
	
	for (int i=0; i<6;i++)
	{
		
		JLabel label= new JLabel(String.valueOf(szamok[i]));
		label.setBounds(x, y+Tabla.parancsjelzok.firstElement().getHeight(null)/2, W/30, H/30);
		if (szamok[i] != 0) aktHazPanel.add(label);
		
		y += Tabla.parancsjelzok.firstElement().getHeight(null);
		
	}
	y=Tabla.aktHaz.getKep().getHeight(null);
	x=(int) (Tabla.parancsjelzok.firstElement().getWidth(null)+iR);
	for (int i=6; i<11;i++)
	{
		JLabel label= new JLabel(String.valueOf(szamok[i]));
		label.setBounds(x, y+Tabla.parancsjelzok.firstElement().getHeight(null)/2, W/30, H/30);
		if (szamok[i] != 0) aktHazPanel.add(label);
		
		y += Tabla.parancsjelzok.firstElement().getHeight(null);
		
	}
	hjelzoPos = y;
	
	
	
	
}

public void updateHJelzok()
{
	Vector<Hazjelzo> vectorHJelzok = Tabla.aktHaz.getHjelzok();
	Iterator<Hazjelzo> itHJelzo = vectorHJelzok.iterator();
	
	int x = (int) (Tabla.aktHaz.getKep().getWidth(null)/2 + iR);
	JLabel label = new JLabel(String.valueOf(Tabla.aktHaz.getHjelzok().size()));
	label.setBounds(x,hjelzoPos,W/30,W/30);
	aktHazPanel.add(label);
	
	while(itHJelzo.hasNext())
	{
		Hazjelzo aktJelzo = itHJelzo.next();
		aktJelzo.setBounds(x+(int)iR*2, hjelzoPos+(int)iR*2,aktJelzo.getKep().getWidth(null), aktJelzo.getKep().getHeight(null));
		aktHazPanel.add(aktJelzo);
		//y += aktJelzo.getHeight();

	}
}
public void updateJatekPanel()
{
	jatekPanel.removeAll();
	jatekPanel.repaint();
	
	int y = Tabla.aktHaz.getKep().getHeight(null);
	int x = Tabla.aktHaz.getKep().getWidth(null)/2;
	
	JLabel vastron = new JLabel(new ImageIcon(scaledImage(vastronKep,cuccosR/2)));
	vastron.setBounds(0, 0, Tabla.aktHaz.getKep().getWidth(null)/2, Tabla.aktHaz.getKep().getHeight(null)/2);
	jatekPanel.add(vastron);
	Iterator<Haz> itHaz = Tabla.vastron.iterator();
	y =  Tabla.aktHaz.getKep().getHeight(null)/2;	
	while (itHaz.hasNext())
	{	
		Haz aktHaz = itHaz.next();
		JLabel aktLabel = new JLabel(new ImageIcon(scaledImage(aktHaz.getKep(),hazR/2)));
		aktLabel.setBounds(0, y, aktHaz.getKep().getWidth(null)/2,aktHaz.getKep().getHeight(null)/2);
		y += aktHaz.getKep().getHeight(null)/2;
	    jatekPanel.add(aktLabel);
	}
	x = (Tabla.aktHaz.getKep().getWidth(null)/2);
	
	//Kard
	JLabel kard = new JLabel(new ImageIcon(scaledImage(kardKep,cuccosR/2)));
	kard.setBounds(x, 0, Tabla.aktHaz.getKep().getWidth(null)/2, Tabla.aktHaz.getKep().getHeight(null)/2);
	jatekPanel.add(kard);
	itHaz = Tabla.kard.iterator();
	y =  Tabla.aktHaz.getKep().getHeight(null)/2;	
	while (itHaz.hasNext())
	{	
		Haz aktHaz = itHaz.next();
		JLabel aktLabel = new JLabel(new ImageIcon(scaledImage(aktHaz.getKep(),hazR/2)));
		aktLabel.setBounds(x, y, aktHaz.getKep().getWidth(null)/2,aktHaz.getKep().getHeight(null)/2);
		y += aktHaz.getKep().getHeight(null)/2;
		jatekPanel.add(aktLabel);
		
	}
	x += Tabla.aktHaz.getKep().getWidth(null)/2;	
	
	//Hollo
		JLabel hollo = new JLabel(new ImageIcon(scaledImage(holloKep,cuccosR/2)));
		hollo.setBounds(x, 0, Tabla.aktHaz.getKep().getWidth(null)/2, Tabla.aktHaz.getKep().getHeight(null)/2);
		jatekPanel.add(hollo);
		itHaz = Tabla.hollo.iterator();
		y =  Tabla.aktHaz.getKep().getHeight(null)/2;	
		while (itHaz.hasNext())
		{	
			Haz aktHaz = itHaz.next();
			JLabel aktLabel = new JLabel(new ImageIcon(scaledImage(aktHaz.getKep(),hazR/2)));
			aktLabel.setBounds(x, y, aktHaz.getKep().getWidth(null)/2,aktHaz.getKep().getHeight(null)/2);
			y += aktHaz.getKep().getHeight(null)/2;
			jatekPanel.add(aktLabel);
			
		}
		
		
		
		
		x = 0;
		y += hazR;
		itHaz = Tabla.vastron.iterator();
		while (itHaz.hasNext())
		{
			Haz aktHaz = itHaz.next();
			JLabel label = new JLabel(new ImageIcon(scaledImage(aktHaz.getKep(),hazR/2)));
			label.setBounds(x,y,aktHaz.getKep().getWidth(null)/2,aktHaz.getKep().getHeight(null)/2);
			jatekPanel.add(label);
			
			
			JLabel label1 = new JLabel(String.valueOf(aktHaz.getHordo()));
			label1.setBounds(x+(int)hazR/2+(int)iR,y,W/30,W/30);
			jatekPanel.add(label1);
			
			y += aktHaz.getKep().getHeight(null)/2;
		}
		
	
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