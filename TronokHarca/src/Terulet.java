import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;

public class Terulet extends Tenger{
	
	
	private int varak;
	private int hordok;
	private int korona;
	private Hazjelzo hazjelzo;
	
	public Terulet(String nev, int varak, int hordok,int korona, Haz tulajdonos, Image kep)
	{
		super(nev, kep,tulajdonos);
		this.vizi = false;
		this.varak = varak;
		this.hordok = hordok;
		this.korona = korona;
		this.tulajdonos = tulajdonos;
		this.hazjelzo = null;
	}
	
	public int getHordo(){return this.hordok; }
	public int getVarak(){ return this.varak; }
	public int Korona(){ return this.korona; }
	public Hazjelzo getHazjelzo(){ return this.hazjelzo; }
	
	public void addHazjelzo(Hazjelzo jelzo)
	{
		this.hazjelzo = jelzo;
	}
	public void removeHazjelzo()
	{
		this.hazjelzo = null;
	}
	
	public void setTulaj(){
		if(this.hazjelzo != null){
			this.tulajdonos = this.hazjelzo.getHaz();
		}else if(this.egysegek != null){
			this.tulajdonos = this.egysegek.get(0).getHaz();
		}else {
			this.tulajdonos = null;
		}
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
		
		s += "\n    Szomszedok:\n";
		
		Iterator<Tenger> szIt = szomszedok.iterator();
		while (szIt.hasNext())
		{
			s += "    " + szIt.next().getNev();
			s += "\n";
		}
		
		return s;
	}
	
	

}
