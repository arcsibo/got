import java.awt.*;
import java.util.Iterator;

public class Terulet extends Tenger{
	
	
	private int varak;
	private int hordok;
	private int korona;
	
	public Terulet(String nev, int varak, int hordok,int korona, Haz tulajdonos, Image kep)
	{
		
		super(nev, kep,tulajdonos);
		this.vizi = false;
		this.varak = varak;
		this.hordok = hordok;
		this.korona = korona;
		this.tulajdonos = tulajdonos;
		
	}
	
	public String toString()
	{
		String s = "Szárazföld: ";
		s += this.nev;
		s += " Tulajdonos:";
		if (tulajdonos != null) s += this.tulajdonos.getNev();
		
		s += "\n";
		
		Iterator<Egyseg> egyIt = egysegek.iterator();
		while (egyIt.hasNext())
		{
			s += "    " + egyIt.next().toString();
			s += "\n";
		}
		return s;
	}

}
