package FrameMain;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class StartListen implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if(CacheClient.getCamthr().isAlive())
		{	
				CacheClient.StopTimer();
				CacheClient.getCamthr().stop();
				CacheClient.Load();
		}
		else {
			CacheClient.getCamthr().start();
			CacheClient.StartTimer();
		}
			
			
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
