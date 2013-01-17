import java.awt.Color;
import java.awt.image.RGBImageFilter;

public class Filter extends RGBImageFilter
{

		Tenger jomagam;
		
		public Filter(Tenger t)
		{
			super();
			jomagam = t;
		}
	
		public Filter()
		{
			canFilterIndexColorModel = true;
		}
	
		@Override
		public int filterRGB(int x, int y, int rgb) {
			// TODO Auto-generated method stub
			
			if (jomagam.tulajdonos == null) return rgb;
			
			int szin = jomagam.tulajdonos.getColor().getRGB();

			if (jomagam.tulajdonos != null) return ((rgb | 0x00ffffff )& szin );
			else return rgb;
			
		}
		
	};