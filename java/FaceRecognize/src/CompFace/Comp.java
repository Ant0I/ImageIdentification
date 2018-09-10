package CompFace;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.arcsoft.AFR_FSDK_FACEMODEL;
import FaceFeature.FacePool;

public class Comp {
	private ExecutorService exec=Executors.newFixedThreadPool(10) ;
	private List<String> ToComp=new ArrayList<String>();
	private AFR_FSDK_FACEMODEL tar;
	/**
	 * ��ʼ���ԱȺ�����Ҫ����Ŀ�꣬��Աȵļ���
	 * 1����FacePool��������
	 * 2�������
	 * 3���ñȽ�
	 * */
	public void init(AFR_FSDK_FACEMODEL tar,List<String> list) {
		this.tar=tar;
		this.ToComp=list;
		
	}
	
	public void comp() {
		System.out.println("COMP.Size:"+ToComp.size());
		
		for(int i=0;i<ToComp.size();i++) {
			AFR_FSDK_FACEMODEL face2 = FacePool.GetFace(ToComp.get(i));
			CompRun run=new CompRun();
			run.SetFaces(tar, face2,ToComp.get(i));
			exec.execute(run);
		}
		ToComp.clear();
		exec.shutdown();
		while(true) {
			if(exec.isTerminated())
				break;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}
}
