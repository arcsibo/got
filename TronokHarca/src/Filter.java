import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class Filter extends RGBImageFilter
{

		 Tenger jomagam;
		 Egyseg e;
		 Parancsjelzo p;
		 Hazjelzo h;
		
		/*
		 * 1-es típus : Terület színezõ filter
		 * 2-es típus : Terület határfigyelõ filter
		 * 3 : Egység szinezés
		 * 4:  Egység figyelés
		 * 5: Pjelzõ figy
		 * 6: Hjelzõ figy
		 */
		int tipus = 0; 
		int mX, mY;
		
		public Filter(Tenger t)
		{
			super();
			jomagam = t;
			tipus = 1;
		}
		
		public Filter(Tenger t,int Mx, int My)
		{
			super();
			jomagam = t;
			tipus = 2;
			this.mX = Mx;
			this.mY = My;

		}
		
		public Filter(Egyseg e)
		{
			super();
			this.e = e;
			tipus = 3;
		}
		
		public Filter(Egyseg e,int Mx, int My)
		{
			super();
			this.e = e;
			tipus = 4;
			this.mX = Mx;
			this.mY = My;

		}
		
		public Filter(Parancsjelzo p,int Mx, int My)
		{
			super();
			this.p = p;
			tipus = 5;
			this.mX = Mx;
			this.mY = My;

		}
		
		public Filter(Hazjelzo h,int Mx, int My)
		{
			super();
			this.h = h;
			tipus = 6;
			this.mX = Mx;
			this.mY = My;

		}
	
		public Filter()
		{
			canFilterIndexColorModel = true;
		}
	
		
		public int filterRGB(int x, int y, int rgb) {
			
			if (tipus == 1) {	
			
			
			if (rgb == 0) return rgb;  
			if (jomagam.tulajdonos == null) return rgb;
				

			Color color = jomagam.tulajdonos.getColor();
			
			if (jomagam instanceof Terulet)
			{
				
				Color teruletSzin = new Color((rgb));
				teruletSzin = new Color(teruletSzin.getRed()/2,teruletSzin.getGreen()/2,teruletSzin.getBlue()/2);
				rgb = teruletSzin.getRGB();
				color = new Color(color.getRed()/2,color.getGreen()/2,color.getBlue()/2);
				rgb += color.getRGB();
				
			}
			else
			{
				color = new Color(color.getRed()/4,color.getGreen()/4,color.getBlue()/4);
				rgb +=color.getRGB();
			}

			return rgb;
			
			
		}
		
		else if (tipus == 2){
			
			if (x == mX && y == mY)
			{
				
				if (rgb == 0)
				{
					jomagam.katt = false;
				}
				else {
					
					jomagam.katt = true;
					
					
				}
				
			}
			
			return rgb;
			
		}
			
		else if (tipus == 3) {	
			
			if (rgb == 0) return rgb;  
			
			if (e.tulajdonos == null) return rgb;
				
				Color color = e.tulajdonos.getColor();
				Color sotetit = new Color(rgb);
				
				rgb = new Color(sotetit.getRed()/3,sotetit.getGreen()/3,sotetit.getBlue()/3).getRGB();
				
				
				
				color = new Color(color.getRed()/3,color.getGreen()/3,color.getBlue()/3);
				
				rgb +=color.getRGB();

				return rgb;
			}
			
		else if (tipus == 4){
			
			if (x == mX && y == mY)
			{
				
				if (rgb == 0)
				{
					e.katt = false;
				}
				else {
					
					e.katt = true;
					
					
				}
				
			}
			
			return rgb;
			
		}	
			
		else if (tipus == 5){
			
			if (x == mX && y == mY)
			{
				
				if (rgb == 0)
				{
					p.katt = false;
				}
				else {
					
					p.katt = true;
					
					
				}
				
			}
			
			return rgb;
			
		}	
			
		else if (tipus == 6){
			
			if (x == mX && y == mY)
			{
				
				if (rgb == 0)
				{
					h.katt = false;
				}
				else {
					
					h.katt = true;
					
					
				}
				
			}
			
			return rgb;
			
		}	
		
		
		
		
		else {
			System.out.println("Mibevagy????");
			return rgb;
		}
		
		
		}
			
	};