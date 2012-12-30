import java.io.*;
import java.awt.*;
import java.applet.*;
import java.net.*;
import java.util.Vector;

public class TronokHarca extends Applet{
	
	public boolean lefutott;
	
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
					
					Image kep = getImage(getCodeBase(), line+".png");
					
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
					
					Image kep = getImage(getCodeBase(),nev+".png");
			        
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
				    	Image gyalogosKep = getImage(getCodeBase(),"Gyalogos.png");
				    	Egyseg gyalogosE = new Egyseg("Gyalogos",gyalogosKep);
				    	terulet.addEgyseg(gyalogosE);
				    }
				    
				    line = bf.readLine();
				    int lovag = Integer.parseInt(line);
				    for (int i= 0; i<lovag; i++)
				    {
				    	Image lovagKep = getImage(getCodeBase(),"Lovag.png");
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
					
					Image kep = getImage(getCodeBase(),nev+".png");
			        
					 Haz tulajdonos = Tabla.getHaz(bf.readLine());
					
					
				    Tenger tenger = new Tenger(nev, kep, tulajdonos);
				    Tabla.teruletek.add(tenger);
				    
				    line = bf.readLine();
				   
				    
				    line = bf.readLine();
				    
				    
				    line = bf.readLine();
				    int hajo = Integer.parseInt(line);
				    for (int i= 0; i<hajo; i++)
				    {
				    	Image hajoKep = getImage(getCodeBase(),"Hajo.png");
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
			
		lefutott = true;
		}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
      }
	
	  public void paint(Graphics g) {
		  
		  if (lefutott) {System.out.println(Tabla.toString1()); lefutott=false; }
		  
	  
	  } 
	}
