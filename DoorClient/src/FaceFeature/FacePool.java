package FaceFeature;

import java.util.HashMap;

import com.arcsoft.AFR_FSDK_FACEMODEL;
/*
 * ����ͼ����������� AFR_FSDK_FACEMODEL
 * 1�����ж��Ƿ�������
 * 2��ȡ����ֵ
 * 3��Keyƥ�䱣��
 * */
public class FacePool {
	private static HashMap<String ,AFR_FSDK_FACEMODEL> faces=new HashMap<String, AFR_FSDK_FACEMODEL>();
	public static void AddFaceFeature(String key,AFR_FSDK_FACEMODEL value) {
		faces.put(key, value);
	}
	public static AFR_FSDK_FACEMODEL GetFace(String key) {
		return faces.get(key);
	}
	public static int FaceNum() {
		return faces.size();
	}
	public static void CleanFaces() {
		faces.clear();
	}
}
