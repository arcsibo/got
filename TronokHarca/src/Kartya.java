
//Gyuri,Aron
import java.util.*;

public class Kartya {
	
	//ennyi darab kártya van
	private static final int DB=6;
	
	
	private static void toborzas( ) {System.out.println("Toborzas");};
	private static void utanpotlas() {System.out.println("Utanpotlas");};
	private static void licitalas() {System.out.println("Licitalas");};
	private static void vadak() {System.out.println("Vadak tamadasa");};
	private static void hazjelzok() {System.out.println("Hazjelzok osztasa");};
	private static void hoki() { System.out.println("csitt-csatt"); };
	
	private static void vadakEroRandom(boolean b) 
	{ 
		/* Random, ha igaz Tabla.vadekEreje++*/
		if(b) Tabla.vadakEreje++;
	};
	
	public static void kartyaHuzas() 
	{
		/*meghivja a vadakEroRandomot*/
		
		Random generator = new Random();
		
		boolean vadakE = generator.nextBoolean();
		vadakEroRandom(vadakE);
		
		
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




