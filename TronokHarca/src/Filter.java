import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class Filter extends RGBImageFilter
{

		Tenger jomagam;
		
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
			
			if (jomagam.tulajdonos == null) return rgb;
			
			int szin = jomagam.tulajdonos.getColor().getRGB();

			if (jomagam.tulajdonos != null) return ((rgb | 0x00ffffff )& szin );
			else return rgb;
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
		
		else {
			System.out.println("Mibevagy????");
			return rgb;
		}
		
		
		}
			
	};