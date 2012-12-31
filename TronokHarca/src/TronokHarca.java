import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.util.*;

public class TronokHarca extends Applet implements Runnable {
	
	
  Thread kicker = null;

  Image terkep[], hazak[], tokenek[] ,offScrImage;

  Graphics offScrGr;

	
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
				    	Egyseg gyalogosE = new Egyseg("Gyalogos",gyalogosKep);
				    	terulet.addEgyseg(gyalogosE);
				    }
				    
				    line = bf.readLine();
				    int lovag = Integer.parseInt(line);
				    for (int i= 0; i<lovag; i++)
				    {
				    	Image lovagKep = getImage(getCodeBase(),"res/Lovag.png");
				    	Egyseg lovagE = new Egyseg("Lovag",lovagKep);
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
				    	Egyseg hajoE = new Egyseg("Hajo",hajoKep);
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
			//System.out.println(Tabla.teruletek.toString());
		
		
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
       
		Tabla.setHordo();
      }
	
	public void paint(Graphics g)
	  {
		  
	  
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
	
	public void scaleTerkep(Component component, float scale) {

	    terkep = new Image[Tabla.teruletek.size()];
        Iterator<Tenger> it = Tabla.teruletek.iterator();
	    
	    for (int i=1; i<=terkep.length; i++)
	    {

	      Image base_image = it.next().getKep();
	      ImageFilter kicsinyit = new ReplicateScaleFilter((int)(base_image.getWidth(this)/scale*100),(int)(base_image.getHeight(this)/scale*100));

	      terkep[i-1] = component.createImage(

	            new FilteredImageSource(base_image.getSource(), kicsinyit));

	      component.prepareImage(terkep[i-1], component);

	      }

	    }  
	}
