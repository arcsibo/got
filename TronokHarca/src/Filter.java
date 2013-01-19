import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class Filter extends RGBImageFilter
{

		 Tenger jomagam;
		 Egyseg e;
		
		/*
		 * 1-es típus : Terület színezõ filter
		 * 2-es típus : Terület határfigyelõ filter
		 */
		int tipus = 0; 
		int mX, mY;
		
		public Filter(Tenger t)
		{
			super();
			jomagam = t;
			tipus = 1;
		}
		
		public Filter(Egyseg e)
		{
			super();
			this.e = e;
			tipus = 3;
		}
		
		public Filter(Tenger t,int Mx, int My)
		{
			super();
			jomagam = t;
			tipus = 2;
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
		
		if (tipus == 3) {	
			
			if (rgb == 0) return rgb;  
			
			if (e.tulajdonos == null) return rgb;
				
				Color color = e.tulajdonos.getColor();
				
				color = new Color(color.getRed()/2,color.getGreen()/2,color.getBlue()/2);
				
				rgb +=color.getRGB();

				return rgb;
			}
		
		
		else {
			System.out.println("Mibevagy????");
			return rgb;
		}
		
		
		}
			
	};