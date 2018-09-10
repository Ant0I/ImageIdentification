package RunMain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.arcsoft.AFR_FSDK_FACEMODEL;
import com.arcsoft.ASVLOFFSCREEN;

import CompFace.Comp;
import DBTools.JDBCPool;
import FaceFeature.ARFEngine;
import FaceFeature.FacePool;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("---Lastest Version---");
		ARFEngine arf=new ARFEngine();
		try {
			List<String> list=new ArrayList<String>();
			ASVLOFFSCREEN img1;AFR_FSDK_FACEMODEL fe1;
			for(int i=0;i<300;i++) {
				list.add(Integer.toString(i+1));
				System.out.println("AddFace"+(i%20+1));
				//img1  From DB
				img1= arf.LoadImg(new FileInputStream((i%20+1)+".jpg"));
				
				fe1 = arf.getFaceFeature(img1);
				FacePool.AddFaceFeature(Integer.toString(i+1), fe1);
			}
			//imga等待比较的图片
			ASVLOFFSCREEN imga = arf.LoadImg(new FileInputStream("20.jpg"));
			AFR_FSDK_FACEMODEL fea = arf.getFaceFeature(imga);
			Comp comp=new Comp();
			
			comp.init(fea,list);
			comp.comp();

			arf.closeEngine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
