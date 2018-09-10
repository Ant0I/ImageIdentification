package FrameMain;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.arcsoft.AFR_FSDK_FACEMODEL;
import com.arcsoft.ASVLOFFSCREEN;
import FaceFeature.ARFEngine;

public class TimerRun implements Runnable {
	private int num=0;
	private ARFEngine arf;
	private ASVLOFFSCREEN img1;
	private AFR_FSDK_FACEMODEL fe1;
	private byte[] imagebyte;
	private InputStream is;
	
	public TimerRun() {
		super();
		// TODO Auto-generated constructor stub
		CacheClient.GetGUI().CleanNet();
		arf=new ARFEngine();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		num++;
		if(num%3==2) 
			findface();
		if (num==19) 
			ping();
		
		
	}

	public void ping() {
		num=0;
		CacheClient.GetGUI().OpenNet();
	}
	public void findface() {
		
		synchronized (CacheClient.getImageCache()) {
			imagebyte=CacheClient.getImageCache().clone();
		}
		is=new ByteArrayInputStream(imagebyte);
		img1= arf.LoadImg(is);
		if (arf.haveFace(img1)) {
			CacheClient.GetGUI().OpenDoor();
		}
		
	}

}
