package RF_SEQ;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SaveFeature {

	public static void main(String[] args) throws IOException {
		int k = 5;
		boolean flag = true;
		//�����������ݵ��ļ�����featureFileName,��ǩ���ļ�����labelFileName;
		String featureFileName="src/feature1/"+"dataset1k"+k;
		String labelFileName="label1";
		String[] allk_mer = SeqComp.getK_mer(k);
		// ��ȡĿ¼
		ArrayList<String> filename = ReadFile.readAllFile("src/dataset/");
		// ��ѯ��������
		String querySequence = ReadFile.readFileData("src/dataset/HSLIPAS.fasta");
		// ��ȡ��ѯ���е���������
		double[] queryFeature = SeqComp.seqFeaturefreq(querySequence, k, allk_mer, flag);
		querySequence = ReadFile.standData(querySequence);
		// ����������Ϣ
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
		 * ��ȡ�������е���������
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
			otherFeatures[count] = SeqComp.seqFeaturefreq(seq, k, allk_mer, flag);
			other[count] = pos; //+ ":" + entry.getKey();
			count++;
		}
		//���һ��Ϊ��ѯ��������
		otherFeatures[39]=queryFeature;
		for(int i=0;i<otherFeatures.length;i++) {
			otherFeatures[i]=SeqComp.normdata(otherFeatures[i]);
		}
//		double[][] positive =new double[20][];
//		
//		for(int i=0;i<other.length;i++) {
//			
//		}
		WriteFile.writeFeature(otherFeatures, featureFileName);
		WriteFile.writeLabel(other, labelFileName);
	}
}
