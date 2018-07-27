package RF_SEQ;

import java.io.IOException;

public class SaveFeature2 {

	public static void main(String[] args) throws IOException {
		savefeature();
	}
	public static void savefeature() throws IOException {
		int k=6;
		boolean flag=true;
		String[] allk_mer = SeqComp.getK_mer(k);
		String posFileName="src/human_HBBFeature/"+"posk"+k;
		String negFileName="src/human_HBBFeature/"+"negk"+k;
		//String labelFileName="label2";
		// 读取数据
		//String[] data = ReadFile.readFileData2("src/fRS/fly_blastoderm.fa", 164);
		//String[] data = ReadFile.readFileData2("src/fRS/human_muscle.fa", 56);
		//String[] data = ReadFile.readFileData2("src/fRS/fly_pns.fa", 46);
		//String[] data = ReadFile.readFileData2("src/fRS/fly_eye.fa", 34);
		String[] data = ReadFile.readFileData2("src/fRS/human_HBB.fa", 34);
		int size = data.length;
		int halfsize=size/2;
		String[] pos = new String[halfsize];
		String[] neg = new String[halfsize];
		//赋值初始化,提取正例与负例
		for(int i=0;i<halfsize;i++) {
			pos[i]=data[i];
			neg[i]=data[i+halfsize];
		}
		//正例特征
		double[][] posFeature=new double[halfsize][];
		//负例特征
		double[][] negFeature=new double[halfsize][];
		for(int i=0;i<halfsize;i++) {
			posFeature[i]=SeqComp.normdata(SeqComp.seqFeaturefreq(pos[i], k, allk_mer, flag));
			negFeature[i]=SeqComp.normdata(SeqComp.seqFeaturefreq(neg[i], k, allk_mer, flag));
		}	
		WriteFile.writeFeature(posFeature, posFileName);
		WriteFile.writeFeature(negFeature, negFileName);
	}
}
