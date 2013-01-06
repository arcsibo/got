import java.awt.*;

public class Hazjelzo {
	
	private Image kep;
	private Haz tulajdonos;
	
	public Hazjelzo(Image kep,Haz haz)
	{
		this.kep = kep;
		this.tulajdonos = haz;
	}
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public String toString()
	{
		String s;
		s = tulajdonos.getNev();
		return s;
	}
	
}
