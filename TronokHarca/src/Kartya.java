
//Gyuri,Aron
import java.util.*;

public class Kartya {
	
	//ennyi darab k���������rtya van
	private static final int DB=6;
	
	
	private static void toborzas( ) {System.out.println("Toborzas");};
	private static void licitalas() {System.out.println("Licitalas");};
	private static void vadak() {System.out.println("Vadak tamadasa");};
	private static void hoki() { System.out.println("csitt-csatt"); };
	
	private static void hazjelzok() 
	{
		System.out.println("Hazjelzok osztasa");
		Tabla.setHjelzo();
		Iterator<Haz> hazit = Tabla.vastron.iterator();
		
		while (hazit.hasNext())
		{
			
			int hordo = 0;
			Tabla.aktHaz = hazit.next();
			String aktNev = Tabla.aktHaz.getNev();
			
			for(int i = 0; i < Tabla.aktHaz.getHjelzo(); i++)
			{
				
			}
			
			
			//while(terit.hasNext())
			for(int i = 0; i < Tabla.teruletek.size(); i ++)
			{
				Tabla.aktTer = Tabla.teruletek.get(i);// = terit.next();
				if(Tabla.aktTer.getHaz() != null)
				{
					String aktTulj = Tabla.aktTer.getHaz().getNev();
					if (aktNev.equals(aktTulj) && !Tabla.aktTer.getTipus())
					{
						if(Tabla.aktTer instanceof Terulet)
						{
							hordo += ((Terulet) Tabla.aktTer).getHordo();
						}
					}
				}
				
			}
			Tabla.aktHaz.setHordo(hordo);
			
		}
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




