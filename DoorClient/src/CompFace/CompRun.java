package CompFace;

import com.arcsoft.AFR_FSDK_FACEMODEL;
import FaceFeature.ARFEngine;

public class CompRun implements Runnable {

	private AFR_FSDK_FACEMODEL face1;
	private AFR_FSDK_FACEMODEL face2;
	private ARFEngine engine;
	private String compkey;
	public void SetFaces(AFR_FSDK_FACEMODEL face1,AFR_FSDK_FACEMODEL face2,String compkey) {
		
		this.face1=face1;
		this.face2=face2;
		this.compkey=compkey;
		this.engine=new ARFEngine();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.print(".");
		if (engine.compareSource(face2, face1)>0.6) 
			System.out.println("Face :"+compkey);
		engine.closeEngine();
	}

}
