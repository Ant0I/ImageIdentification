package FaceFeature;

import java.io.InputStream;

import com.arcsoft.AFD_FSDKLibrary;
import com.arcsoft.AFD_FSDK_FACERES;
import com.arcsoft.AFR_FSDKLibrary;
import com.arcsoft.AFR_FSDK_FACEINPUT;
import com.arcsoft.AFR_FSDK_FACEMODEL;
import com.arcsoft.ASVLOFFSCREEN;
import com.arcsoft.ASVL_COLOR_FORMAT;
import com.arcsoft.CLibrary;
import com.arcsoft.FaceInfo;
import com.arcsoft.MRECT;
import com.arcsoft._AFD_FSDK_OrientPriority;
import com.arcsoft.utils.BufferInfo;
import com.arcsoft.utils.ImageLoader;
import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.PointerByReference;
/**
 * 图片识别引擎
 * 1初始化
 * 2加载图片   
 * 3判断图片
 * 4比较图片
 * */
public class ARFEngine {
	private static final String    APPID  = "6Z96jRZRJGZgxxFitYsh4QoNi43yTW7SxbUyQWUxfs6b";
	private static final String FD_SDKKEY = "CtQ5BFenHg6KVmmLq85saFsgXa6ayeACGCBpeaUvypgo";
	private static final String FR_SDKKEY = "CtQ5BFenHg6KVmmLq85saFtBBB9FMGxghrBX2BuSyxsc";
	private static final int FD_WORKBUF_SIZE = 20 * 1024 * 1024;
	private static final int FR_WORKBUF_SIZE = 40 * 1024 * 1024;
	private static final int MAX_FACE_NUM = 2;
	
	private Pointer pFDWorkMem;
	private Pointer pFRWorkMem;
	private Pointer hFREngine;
	private Pointer hFDEngine;
	public ARFEngine() {
		super();
		// TODO Auto-generated constructor stub
		pFDWorkMem = CLibrary.INSTANCE.malloc(FD_WORKBUF_SIZE);
        pFRWorkMem = CLibrary.INSTANCE.malloc(FR_WORKBUF_SIZE);
        PointerByReference phFDEngine = new PointerByReference();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_InitialFaceEngine(APPID, FD_SDKKEY, pFDWorkMem, FD_WORKBUF_SIZE, phFDEngine, _AFD_FSDK_OrientPriority.AFD_FSDK_OPF_0_HIGHER_EXT, 32, MAX_FACE_NUM);
        if (ret.longValue() != 0) {
            CLibrary.INSTANCE.free(pFDWorkMem);
            CLibrary.INSTANCE.free(pFRWorkMem);
            System.out.println(String.format("AFD_FSDK_InitialFaceEngine ret 0x%x",ret.longValue()));
            System.exit(0);
        }
        hFDEngine = phFDEngine.getValue();
        PointerByReference phFREngine = new PointerByReference();
        ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_InitialEngine(APPID, FR_SDKKEY, pFRWorkMem, FR_WORKBUF_SIZE, phFREngine);
        if (ret.longValue() != 0) {
            AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFDEngine);
            CLibrary.INSTANCE.free(pFDWorkMem);
            CLibrary.INSTANCE.free(pFRWorkMem);
            System.out.println(String.format("AFR_FSDK_InitialEngine ret 0x%x" ,ret.longValue()));
            System.exit(0);
        }
        hFREngine = phFREngine.getValue();
	}	
	
	
	public void closeEngine() {
		// TODO Auto-generated method stub
        AFD_FSDKLibrary.INSTANCE.AFD_FSDK_UninitialFaceEngine(hFDEngine);
        AFR_FSDKLibrary.INSTANCE.AFR_FSDK_UninitialEngine(hFREngine);

        CLibrary.INSTANCE.free(pFDWorkMem);
        CLibrary.INSTANCE.free(pFRWorkMem);
	}
	/**
	 * 获得脸部特征
	 * */
	public AFR_FSDK_FACEMODEL getFaceFeature(ASVLOFFSCREEN inputImg) {
        AFR_FSDK_FACEINPUT faceinput = new AFR_FSDK_FACEINPUT();
        FaceInfo faceInfo=getFaceInfo(inputImg)[0];
		faceinput.lOrient = faceInfo.orient;
        faceinput.rcFace.left = faceInfo.left;
        faceinput.rcFace.top = faceInfo.top;
        faceinput.rcFace.right = faceInfo.right;
        faceinput.rcFace.bottom = faceInfo.bottom;

        AFR_FSDK_FACEMODEL faceFeature = new AFR_FSDK_FACEMODEL();
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_ExtractFRFeature(hFREngine, inputImg, faceinput, faceFeature);
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_ExtractFRFeature ret 0x%x" ,ret.longValue()));
            return null;
        }

        try {
            return faceFeature.deepCopy();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }	
	}
	/**
	 * 比较特征
	 * */
	public float compareSource( AFR_FSDK_FACEMODEL faceFeatureA, AFR_FSDK_FACEMODEL faceFeatureB) {
		
//        FaceInfo[] faceInfosA = getFaceInfo(hFDEngine, inputImgA);
//        if (faceInfosA.length < 1) {
//            System.out.println("no face in Image A ");
//            return 0.0f;
//        }
//
//        FaceInfo[] faceInfosB = getFaceInfo(hFDEngine, inputImgB);
//        if (faceInfosB.length < 1) {
//            System.out.println("no face in Image B ");
//            return 0.0f;
//        }
//
//        // Extract Face Feature
//        AFR_FSDK_FACEMODEL faceFeatureA = getFaceFeature(hFREngine, inputImgA, faceInfosA[0]);
//        if (faceFeatureA == null) {
//            System.out.println("extract face feature in Image A failed");
//            return 0.0f;
//        }
//
//        AFR_FSDK_FACEMODEL faceFeatureB = getFaceFeature(hFREngine, inputImgB, faceInfosB[0]);
//        
//        if (faceFeatureB == null) {
//            System.out.println("extract face feature in Image B failed");
//            faceFeatureA.freeUnmanaged();
//            return 0.0f;
//        }

        // calc similarity between faceA and faceB
        FloatByReference fSimilScore = new FloatByReference(0.0f);
        NativeLong ret = AFR_FSDKLibrary.INSTANCE.AFR_FSDK_FacePairMatching(hFREngine, faceFeatureA, faceFeatureB, fSimilScore);
        //System.out.println("------");

        //faceFeatureA.freeUnmanaged();
        //faceFeatureB.freeUnmanaged();
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFR_FSDK_FacePairMatching failed:ret 0x%x" ,ret.longValue()));
            return 0.0f;
        }
        return fSimilScore.getValue();
  
	}
	/**
	 * 获得脸部信息
	 * */
	private FaceInfo[] getFaceInfo( ASVLOFFSCREEN inputImg) {
        FaceInfo[] faceInfo = new FaceInfo[0];
        PointerByReference ppFaceRes = new PointerByReference();
        NativeLong ret = AFD_FSDKLibrary.INSTANCE.AFD_FSDK_StillImageFaceDetection(hFDEngine, inputImg, ppFaceRes);
        if (ret.longValue() != 0) {
            System.out.println(String.format("AFD_FSDK_StillImageFaceDetection ret 0x%x" , ret.longValue()));
            return faceInfo;
        }

        AFD_FSDK_FACERES faceRes = new AFD_FSDK_FACERES(ppFaceRes.getValue());
        if (faceRes.nFace > 0) {
            faceInfo = new FaceInfo[faceRes.nFace];
            for (int i = 0; i < faceRes.nFace; i++) {
                MRECT rect = new MRECT(new Pointer(Pointer.nativeValue(faceRes.rcFace.getPointer()) + faceRes.rcFace.size() * i));
                int orient = faceRes.lfaceOrient.getPointer().getInt(i * 4);
                faceInfo[i] = new FaceInfo();
                faceInfo[i].left = rect.left;
                faceInfo[i].top = rect.top;
                faceInfo[i].right = rect.right;
                faceInfo[i].bottom = rect.bottom;
                faceInfo[i].orient = orient;
                //System.out.println(String.format("%d (%d %d %d %d) orient %d", i, rect.left, rect.top, rect.right, rect.bottom, orient));
            }
        }
        return faceInfo;
	}
	/**
	 * 加载图片
	 * */
	public ASVLOFFSCREEN LoadImg(InputStream ins) {
		ASVLOFFSCREEN inputImg = new ASVLOFFSCREEN();
		BufferInfo bufferInfo = ImageLoader.getBGRFromFile(ins);
        inputImg.u32PixelArrayFormat = ASVL_COLOR_FORMAT.ASVL_PAF_RGB24_B8G8R8;
        inputImg.i32Width = bufferInfo.width;
        inputImg.i32Height = bufferInfo.height;
        inputImg.pi32Pitch[0] = inputImg.i32Width * 3;
        inputImg.ppu8Plane[0] = new Memory(inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[0].write(0, bufferInfo.buffer, 0, inputImg.pi32Pitch[0] * inputImg.i32Height);
        inputImg.ppu8Plane[1] = Pointer.NULL;
        inputImg.ppu8Plane[2] = Pointer.NULL;
        inputImg.ppu8Plane[3] = Pointer.NULL;
        inputImg.setAutoRead(false);
        return inputImg;
	}
	/**
	 * 判断图片是否符合要求
	 * */
	public boolean haveFace(ASVLOFFSCREEN inputImgA) {
	FaceInfo[] faceInfosA = getFaceInfo(inputImgA);
      if (faceInfosA.length < 1) {
          System.out.println("no face in Image A ");
          return false;
      }
		return true;
	}
	
}
