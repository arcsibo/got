import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class change {

	
	public static void szinez(Tenger t)
	{
		
		Image resultImage;
		
		Filter filter = new Filter(t);
        ImageProducer producer = new FilteredImageSource(
                                        t.kep.getSource(),
                                        filter);
        resultImage = t.createImage(producer);
        
        t.setIcon(new ImageIcon(resultImage));
		
	}
	
	public static boolean checkClick(int x, int y, Tenger t)
	{
		
		Image resultImage;
		Filter filter = new Filter(t,x,y);
	
        ImageProducer producer = new FilteredImageSource(
                                        t.kep.getSource(),
                                        filter);
        resultImage = t.createImage(producer);
        ImageIcon dump = new ImageIcon(resultImage);
        
        
        if (t.katt) {
        	t.katt = false;
        	return true;
        }
        
        else return false;
	}
	
}
