package FrameMain;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter.ToIplImage;
import org.bytedeco.javacv.OpenCVFrameGrabber;

public class CamRun implements Runnable {
	OpenCVFrameGrabber grabber ;
	
	public CamRun() {
		super();
		// TODO Auto-generated constructor stub
		grabber = new OpenCVFrameGrabber(0);
		CacheClient.GetGUI().ReadyCam();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//System.out.println("Start");
		try {
			grabber.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		OpenCVFrameConverter.ToIplImage convert=new OpenCVFrameConverter.ToIplImage(); 
		//byte[] bs;
		//File FileCache =new File("C://Cache.jpg");
		while(true) {
			try {
				//System.out.println("--");
				IplImage Image = convert.convert(grabber.grab()); 
				ByteArrayOutputStream out = new ByteArrayOutputStream(); 
				ImageIO.write(toBufferedImage(Image), "jpg", out);
				InputStream is;
				
				synchronized (CacheClient.getImageCache()) {
					CacheClient.setImageCache(out.toByteArray());
					out.flush();
					out.close();
					is= new ByteArrayInputStream(CacheClient.getImageCache());
					CacheClient.GetGUI().ReFreshImg(is);
				}
				 
				 
				//FileOutputStream fo=new FileOutputStream(FileCache);
				//fo.write(bs);
				//fo.flush();
				//fo.close();
				//FileCache=new File("C://C2ache.jpg");
				//InputStream fi=new FileInputStream(FileCache);
				
					
				CacheClient.GetGUI().OpenCam();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				CacheClient.GetGUI().BusyCam();
			}
			
		}
	}

	private BufferedImage toBufferedImage(IplImage image) {
		// TODO Auto-generated method stub
		ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();  
		Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		BufferedImage bufferedImage = java2dConverter.convert(iplConverter.convert(image));  
		return bufferedImage;  
	}

}
