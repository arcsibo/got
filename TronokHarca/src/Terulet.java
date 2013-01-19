import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

public class Terulet extends Tenger{
	
	
	private int varak;
	private int hordok;
	private int korona;
	private Hazjelzo hazjelzo;
	
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
	
	public void korona()
	{
		this.tulajdonos.addHazjelzo();
		for(int i = 0; i < this.korona; i++)
		{
			this.tulajdonos.addHazjelzo();
		}
		this.tulajdonos.addParancs(this.parancsjelzo);
		this.parancsjelzo = null;
	}
	
	public void performclick(int x, int y)
	{
		boolean talalat = false;
		if (hazjelzo != null) talalat = change.szomszedClick(x, y, hazjelzo);
		if (talalat) return;
		
		super.performClick(x, y);
	}
	
	
	
	

}
