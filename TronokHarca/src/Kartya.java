
//Gyuri,Aron
import java.util.*;

public class Kartya {
	
	//ennyi darab k�rtya van
	private static final int DB=6;
	
	
	private static void toborzas( ) {System.out.println("Toborzas");};
	private static void licitalas() {System.out.println("Licitalas");};
	private static void vadak() {System.out.println("Vadak tamadasa");};
	private static void hazjelzok() {System.out.println("Hazjelzok osztasa");};
	private static void hoki() { System.out.println("csitt-csatt"); };
	
	
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
	
	private static void utanpotlas() 
	{
		System.out.println("Utanpotlas");
				
		
	}
	
}

// /Gyuri,Aron




