
//Gyuri,Aron
import java.util.*;

public class Kartya {
	
	//ennyi darab k���������rtya van
	private static final int DB=6;
	
	
	private static void vadak() {System.out.println("Vadak tamadasa");};
	private static void hoki() { System.out.println("csitt-csatt"); };
	

	private static void licitalas() 
	{
		
		/////////Debug/////////
		System.out.println("Licit előtt:");
		System.out.println("("+Tabla.vastron.size()+")"+"vastron:");
		for(int i = 0; i < Tabla.vastron.size(); i++)
		{
			System.out.println(Tabla.vastron.get(i).getNev()+": "+Tabla.vastron.get(i).getLicit());
		}
		System.out.println();
		System.out.println("("+Tabla.kard.size()+")"+"kard:");
		for(int i = 0; i < Tabla.kard.size(); i++)
		{
			System.out.println(Tabla.kard.get(i).getNev()+": "+Tabla.kard.get(i).getLicit());
		}
		System.out.println();
		System.out.println("("+Tabla.hollo.size()+")"+"hollo:");
		for(int i = 0; i < Tabla.hollo.size(); i++)
		{
			System.out.println(Tabla.hollo.get(i).getNev()+": "+Tabla.hollo.get(i).getLicit());
		}
		System.out.println();
		/////////Debug/////////
		
		
		System.out.println("Licitalas");
		int[] tmp = new int[Tabla.vastron.size()];
		
		
		System.out.println("Vastron");
		//Tabla.licit();
		Tabla.randomLicit();
		
		for(int i = 0; i < Tabla.vastron.size(); i++)
		{
			tmp[i] = Tabla.vastron.get(i).getLicit();
		}
		
		
		
		for(int j = 1; j < Tabla.vastron.size(); j++)
		{
			int kulcs = tmp[j];
			Tabla.aktHaz = Tabla.vastron.get(j);
			int i = j-1;
			
			while( i>=0 && tmp[i] < kulcs )
			{
				Tabla.vastron.set(i+1, Tabla.vastron.get(i));
				tmp[i+1] = tmp[i];
				i--;
			}
			tmp[i+1] = kulcs;
			Tabla.vastron.set(i+1, Tabla.aktHaz);
			
		}
		
		
		Tabla.zeroLicit();
		
		System.out.println("Kard");
		//Tabla.licit();
		Tabla.randomLicit();
		
		for(int i = 0; i < Tabla.kard.size(); i++)
		{
			tmp[i] = Tabla.kard.get(i).getLicit();
		}
		
		
		for(int j = 1; j < Tabla.kard.size(); j++)
		{
			int kulcs = tmp[j];
			Tabla.aktHaz = Tabla.kard.get(j);
			int i = j-1;
			
			while( i>=0 && tmp[i] < kulcs )
			{
				Tabla.kard.set(i+1, Tabla.kard.get(i));
				tmp[i+1] = tmp[i];
				i--;
			}
			tmp[i+1] = kulcs;
			Tabla.kard.set(i+1, Tabla.aktHaz);
			
		}
		
		
		Tabla.zeroLicit();
		
		System.out.println("Hollo");
		//Tabla.licit();
		Tabla.randomLicit();
		
		for(int i = 0; i < Tabla.hollo.size(); i++)
		{
			tmp[i] = Tabla.hollo.get(i).getLicit();
		}
		
		
		for(int j = 1; j < Tabla.hollo.size(); j++)
		{
			int kulcs = tmp[j];
			Tabla.aktHaz = Tabla.hollo.get(j);
			int i = j-1;
			
			while( i>=0 && tmp[i] < kulcs )
			{
				Tabla.hollo.set(i+1, Tabla.hollo.get(i));
				tmp[i+1] = tmp[i];
				i--;
			}
			tmp[i+1] = kulcs;
			Tabla.hollo.set(i+1, Tabla.aktHaz);
			
		}
		
		Tabla.zeroLicit();
		
		System.out.println("Licit vége");
		
		
		
		System.out.println("Licit előtt:");
		System.out.println("vastron:");
		for(int i = 0; i < Tabla.vastron.size(); i++)
		{
			System.out.println(Tabla.vastron.get(i).getNev()+": "+Tabla.vastron.get(i).getLicit());
		}
		System.out.println();
		System.out.println("kard:");
		for(int i = 0; i < Tabla.kard.size(); i++)
		{
			System.out.println(Tabla.kard.get(i).getNev()+": "+Tabla.kard.get(i).getLicit());
		}
		System.out.println();
		System.out.println("hollo:");
		for(int i = 0; i < Tabla.hollo.size(); i++)
		{
			System.out.println(Tabla.hollo.get(i).getNev()+": "+Tabla.hollo.get(i).getLicit());
		}
	}
	

	private static void toborzas( ) 
	{
		System.out.println("Toborzas");
		
	}
	
	
	private static void hazjelzok() 
	{
		System.out.println("Hazjelzok osztasa");
		Tabla.setHjelzo();
		Iterator<Haz> hazit = Tabla.vastron.iterator();
		
		while (hazit.hasNext())
		{
			Tabla.aktHaz = hazit.next();
			for(int i = 0; i < Tabla.aktHaz.getHjelzo(); i++)
			{
				Tabla.aktHaz.addHazjelzo(new Hazjelzo(Tabla.aktHaz.keph,Tabla.aktHaz));
			}
		}
		//FASZ
	}
	
	private static void utanpotlas() 
	{
		System.out.println("Utanpotlas");
		Tabla.setHordo();
		
	}
	
	public static void kartyaHuzas() 
	{
		/*meghivja a vadakEroRandomot*/
		
		Random generator = new Random();
		
		boolean b = generator.nextBoolean();
		if(b) Tabla.vadakEreje++;
		
		
		int rand = generator.nextInt(DB)+1;
		rand = 3;
		switch(rand)
		{
			case 1:
				toborzas();
				break;
			
			case 2:
				utanpotlas();
				break;
			
			case 3:
				licitalas();
				break;
				
			case 4:
				vadak();
				break;
				
			case 5:
				hazjelzok();
				break;
				
			case 6:
				hoki();
				break;
				
			default:
				System.out.println("DEBUG - HIBA");
				break;
		}
		
	}
	
	
	
}

// /Gyuri,Aron