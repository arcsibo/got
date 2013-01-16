import java.awt.*;


public class Egyseg {
	
	private boolean vizi;
	private int ertek;
	private String tipus;
	private Image kep;
	private Haz tulajdonos;
	
	public Egyseg(String tipus,Haz tulajdonos, Image kep)
	{
		this.tipus = tipus;
		this.tulajdonos = tulajdonos;
		this.kep = kep;
		
		if (tipus.equals("Hajó"))
		{
			
			this.vizi = true;
			this.ertek = 1;
			
		}
		
		else if (tipus.equals("Gyalogos"))
		{
			
			this.vizi = false;
			this.ertek = 1;
			
		}
		
		else if (tipus.equals("Lovag"))
		{
			
			this.vizi = false;
			this.ertek = 2;
			
		}
		
		else return;
		
	}
	public int getEro()
	{
		return this.ertek;
	}
	public Haz getHaz(){
		return this.tulajdonos;
	}
	
	public String toString()
	{
		String s = "";
			
		s += this.tipus;
			
		return s;
	}
		
		
	

}

