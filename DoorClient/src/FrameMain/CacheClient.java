package FrameMain;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CacheClient {
	private static ClientGUI cgui=new ClientGUI();
	private static byte[] ImageCache={0};
	private static CamRun camrun;
	private static TimerRun timrun;
	private static Thread camthr;
	private static ScheduledExecutorService timthr;
	public static void  Load() {
		cgui.setVisible(true);
		Init();
	}
	public static void Init() {
		camrun=new CamRun();
		timrun=new TimerRun();
		camthr=new Thread(camrun);
		timthr=Executors.newSingleThreadScheduledExecutor();
		
	}
	public static void StartTimer() {
		timthr.scheduleAtFixedRate(timrun, 3000, 100, TimeUnit.MILLISECONDS);
	}
	public static void StopTimer() {
		timthr.shutdownNow();
	}
	public static ClientGUI  GetGUI() {
		return cgui;
	}
	public static byte[] getImageCache() {
		return ImageCache;
	}
	public static void setImageCache(byte[] imageCache) {
		ImageCache = imageCache;
	}

	public static Thread getCamthr() {
		return camthr;
	}

	public static ScheduledExecutorService getTimthr() {
		return timthr;
	}


	
}
