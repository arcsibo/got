import java.awt.*;
import java.util.*;

public class Haz {
	
	private String nev;
	private Color szin;
	private Image kep;
	private Vector<Parancsjelzo> parancsjelzok;
	
	public Haz(String nev, Image kep)
	{
		this.nev = nev;
		this.kep = kep;
		this.parancsjelzok = new Vector<Parancsjelzo>();
		
		
		if (nev.equals("Lennister"))
		{
			this.szin = Color.red;
		}
		
		else if (nev.equals("Stark"))
		{
			this.szin = Color.gray;
		}
		
		else if (nev.equals("Greyjoy"))
		{
			this.szin = Color.black;
		}
		
		else if (nev.equals("Tyrell"))
		{
			this.szin = Color.green;
		}
		
		else if (nev.equals("Baratheon"))
		{
			this.szin = Color.yellow;
		}
		
		else return;
	}
	
	
	public String getNev() { return this.nev; }
	public Image getKep() { return this.kep; }
	
	//Gyuri,Aron
	
	// /Gyuri,Aron
}
