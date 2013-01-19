import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class change {

	
	public static boolean szomszedClick(int x, int y,Tenger t)
    {
       int relX;
       relX = x - t.getLocation().x;
       int relY;
       relY = y - t.getLocation().y;
       
       if (change.checkClick(relX,relY,t))
       {
    	   t.performClick(relX,relY);
    	   return true;
       }
       
       return false;
    }
	
	
	public static boolean szomszedClick(int x, int y,Egyseg e)
    {
       int relX;
       relX = x - e.getLocation().x;
       int relY;
       relY = y - e.getLocation().y;
       
       if (change.checkClick(relX,relY,e))
       {
    	   e.performClick();
    	   return true;
       }
       
       return false;
    }
	
	public static boolean szomszedClick(int x, int y,Parancsjelzo p)
    {
       int relX;
       relX = x - p.getLocation().x;
       int relY;
       relY = y - p.getLocation().y;
       
       if (change.checkClick(relX,relY,p))
       {
    	   p.performClick(x,y);
    	   return true;
       }
       
       return false;
    }
	
	
	
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
	
	public static void szinez(Egyseg e)
	{
		
		Image resultImage;
		
		Filter filter = new Filter(e);
        ImageProducer producer = new FilteredImageSource(
                                        e.kep.getSource(),
                                        filter);
        resultImage = e.createImage(producer);
        
        e.setIcon(new ImageIcon(resultImage));
		
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
	
	public static boolean checkClick(int x, int y, Egyseg e)
	{
		
		Image resultImage;
		Filter filter = new Filter(e,x,y);
	
        ImageProducer producer = new FilteredImageSource(
                                        e.kep.getSource(),
                                        filter);
        resultImage = e.createImage(producer);
        ImageIcon dump = new ImageIcon(resultImage);
        
        
        if (e.katt) {
        	e.katt = false;
        	return true;
        }
        
        else return false;
	}
	
	public static boolean checkClick(int x, int y, Parancsjelzo p)
	{
		
		Image resultImage;
		Filter filter = new Filter(p,x,y);
	
        ImageProducer producer = new FilteredImageSource(
                                        p.getKep().getSource(),
                                        filter);
        resultImage = p.createImage(producer);
        ImageIcon dump = new ImageIcon(resultImage);
        
        
        if (p.katt) {
        	p.katt = false;
        	return true;
        }
        
        else return false;
	}
	
}
