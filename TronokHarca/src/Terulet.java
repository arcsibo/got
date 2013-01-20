import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

public class Terulet extends Tenger{
	
	
	private int varak;
	private int hordok;
	private int korona;
	public Hazjelzo hazjelzo;
	
	private Vector<VHK> vhk;
	
	public Terulet(String nev, int varak, int hordok,int korona, Haz tulajdonos, Image kep, double X, double Y,Vector<VHK> vhk)
	{
		super(nev, kep,tulajdonos,X,Y);
		this.vizi = false;
		this.varak = varak;
		this.hordok = hordok;
		this.korona = korona;
		this.tulajdonos = tulajdonos;
		this.hazjelzo = null;
		
		this.vhk = vhk;
		//placeVHK();
	}
	
	
	public void placeVHK()
	{
		Iterator<VHK> it = vhk.iterator();
		
		while(it.hasNext())
		{
			VHK aktVHK = it.next();
			aktVHK.setBounds((int)Math.round(aktVHK.x*this.kep.getWidth(null))-aktVHK.kep.getWidth(null)/2, (int)Math.round(aktVHK.y*this.kep.getHeight(null))-aktVHK.kep.getHeight(null)/2, aktVHK.kep.getWidth(null), aktVHK.kep.getHeight(null));
			this.add(aktVHK);
		}
	}
	
	public int getHordo(){return this.hordok; }
	public int getVarak(){ return this.varak; }
	public int Korona(){ return this.korona; }
	public Hazjelzo getHazjelzo(){ return this.hazjelzo; }
	
	public void addHazjelzo(Hazjelzo jelzo,int x, int y)
	{
		boolean lehet = true;
		
		if(this.tulajdonos == null) lehet = false; 
		if(this.tulajdonos != Tabla.aktHaz) lehet = false;
		if(this.hazjelzo != null) lehet = false;
		if(this.egysegek.size() == 0) lehet = false;

		if (lehet)
		{
		
			this.hazjelzo = jelzo;
			hazjelzo.setBounds(x-hazjelzo.getKep().getWidth(this)/2,y-hazjelzo.getKep().getHeight(null)/2,hazjelzo.getKep().getWidth(this),hazjelzo.getKep().getHeight(null));
			this.add(hazjelzo);
			tulajdonos.removeHazjelzo(hazjelzo);
			hazjelzo.tablanVan = true;
			Tabla.got.setCursor(Tabla.defCursor);
			Tabla.hazJelzoAmitLeraksz = null;
			Tabla.hazjelzoLerakas = false;
		}
		
		
		

		
	}
	public void removeHazjelzo()
	{
		tulajdonos.addHazjelzo(hazjelzo);
		hazjelzo.tablanVan = false;
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
	
	public void korona()
	{
		this.tulajdonos.addHazjelzo(new Hazjelzo(tulajdonos.keph,tulajdonos));
		for(int i = 0; i < this.korona; i++)
		{
			this.tulajdonos.addHazjelzo(new Hazjelzo(tulajdonos.keph,tulajdonos));
		}
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
	}
	

	public void performClick(int x, int y)
	{
		boolean talalat = false;
		if (hazjelzo != null) talalat = change.szomszedClick(x, y, hazjelzo);
		if (talalat)
		{
			return;
		}
		
		super.performClick(x, y);
	
		if (Tabla.hazjelzoLerakas && Tabla.hazJelzoAmitLeraksz != null)
		{
			this.addHazjelzo(Tabla.hazJelzoAmitLeraksz, x, y);
		}
		
		else /* System.out.println(this.getNev())*/;
		
	}
	
	
	

}
