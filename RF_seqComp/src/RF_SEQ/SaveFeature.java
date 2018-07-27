package RF_SEQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SaveFeature {

	public static void main(String[] args) throws IOException {
		saveFeature(1);
	}

	public static void saveFeature(int code) throws IOException {
		int r=0;
		for (int k = 2; k <= 6; k++) {
			boolean flag = true;
			String nam="";
			if(code ==1) {
				nam="D2";
			}else if(code==2) {
				nam="D2S";
			}else {
				nam="D2*";
			}
			// 保存特征数据的文件名：featureFileName,标签的文件名：labelFileName;
			String featureFileName = "src/feature1/" + nam + k;
			String labelFileName = "label1";
			String[] allk_mer = SeqComp.getK_mer(k);
			// 读取目录
			ArrayList<String> filename = ReadFile.readAllFile("src/dataset/");
			// 查询序列数据
			String querySequence = ReadFile.readFileData("src/dataset/HSLIPAS.fasta");
			// 获取查询序列的特征向量
			double [] queryFeature =new double[(int) Math.pow(4, k)];
			if(code==1) {
				 queryFeature = SeqComp.seqFeatureCount(querySequence, k, allk_mer, flag);
			}else if(code==2){
				queryFeature=SeqComp.D2SCount(querySequence, k, allk_mer, r);
			}else {
				queryFeature=SeqComp.D2starCount(querySequence, k, allk_mer, r);
			}
			querySequence = ReadFile.standData(querySequence);
			// 序列名称信息
			String[] other = new String[39];
			double[][] otherFeatures = new double[40][(int) Math.pow(4, k)];
			HashMap<String, String> map = new HashMap<>();
			for (int i = 0; i < filename.size(); i++) {
				String tmp1 = filename.get(i).split("\\.")[0].trim();
				// System.out.println(tmp1);
				if (tmp1.equals("HSLIPAS")) {

				} else {
					String tmp = "src/dataset/" + filename.get(i);
					String sequence = ReadFile.readFileData(tmp);
					map.put(filename.get(i).split("\\.")[0], sequence);
				}
			}
			/**
			 * 获取其余序列的特征向量
			 */
			Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
			int count = 0;
			while (it.hasNext()) {
				Map.Entry<String, String> entry = it.next();
				String pos = "";
				if (entry.getKey().indexOf("+") != -1) {
					pos = "1";
				} else {
					pos = "0";
				}
				String seq = ReadFile.standData(entry.getValue());
				if(code==1) {
					otherFeatures[count]= SeqComp.seqFeatureCount(seq, k, allk_mer, flag);
				}else if(code==2){
					otherFeatures[count]=SeqComp.D2SCount(seq, k, allk_mer, r);
				}else {
					otherFeatures[count]=SeqComp.D2starCount(seq, k, allk_mer, r);
				}
//				otherFeatures[count] = SeqComp.seqFeaturefreq(seq, k, allk_mer, flag);
				other[count] = pos; // + ":" + entry.getKey();
				count++;
			}
			// 最后一条为查询序列特征
			otherFeatures[39] = queryFeature;
			for (int i = 0; i < otherFeatures.length; i++) {
				otherFeatures[i] = SeqComp.normdata(otherFeatures[i]);
			}
			// double[][] positive =new double[20][];
			//
			// for(int i=0;i<other.length;i++) {
			//
			// }
			WriteFile.writeFeature(otherFeatures, featureFileName);
			WriteFile.writeLabel(other, labelFileName);
		}
	}
}
