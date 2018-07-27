package RF_SEQ;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SeqComp {
	private static final char[] dict = { 'A', 'T', 'C', 'G' };

	/**
	 * ��ȡ��������
	 * 
	 * @param sequence
	 *            ��������
	 * @param k
	 *            k-mer �е�kֵ
	 * @param allk_mer
	 *            ���п��ܵ�k_mer
	 * @param flag
	 * @return ��ȡ����k-mer t ���� Ƶ��
	 */
	public static double[] seqFeaturefreq(String sequence, int k, String[] allk_mer,boolean flag) {
		String[] seqk_mer = getseqk_mer(sequence, k);
		/* ͳ��k_mer Ƶ�� */
		HashMap<String, Double> xfreq = freq(seqk_mer, allk_mer,flag);
		double[] xf = new double[allk_mer.length];
		xf = xfreq2double(xfreq, allk_mer);
		//xf=normdata(xf);
		return xf;
	}
	/**
	 * 
	 * @param sequence ��������
	 * @param k k-mer �е�kֵ
	 * @param allk_mer   ���п��ܵ�k_mer
	 * @param flag  �Ƿ�����ƽ��
	 * @return
	 */
	public static double[] seqFeatureCount(String sequence, int k, String[] allk_mer,boolean flag) {
		String[] seqk_mer = getseqk_mer(sequence, k);
		/* ͳ��k_mer Ƶ�� */
		HashMap<String, Double> xfreq = countkmer(seqk_mer, allk_mer,flag);
		double[] xf = new double[allk_mer.length];
		xf = xcount2double(xfreq, allk_mer);
		return xf;
	}

	/**
	 * json��ʽ������ֵƵ��ת����double����
	 * 
	 * @param xfreq
	 *            hashmap���͵�����ֵ
	 * @param allk_mer
	 *            ���п��ܵ�k-mer
	 * @return ��������ֵdouble����
	 */
	private static double[] xfreq2double(HashMap<String, Double> xfreq, String[] allk_mer) {
		double[] temp = new double[allk_mer.length];
		for (int i = 0; i < allk_mer.length; i++) {
			temp[i] = xfreq.get(allk_mer[i]);
		}
		return temp;
	}

	/**
	 * json��ʽ������ֵƵ��ת����double����
	 * 
	 * @param count
	 *            Ƶ���� hashmap�洢��ʽ
	 * @param allk_mer
	 *            ȫ�����ܵ�k-mer
	 * @return
	 */
	public static double[] xcount2double(HashMap<String, Double> count, String[] allk_mer) {
		double[] temp = new double[allk_mer.length];
		for (int i = 0; i < allk_mer.length; i++) {
			temp[i] = count.get(allk_mer[i]);
		}
		return temp;
	}

	/**
	 * 
	 * @param k_mer
	 *            ��������ȡ���� k-mer
	 * @param allk_mer
	 *            ���п��ܵ�k-mer
	 * @param flag �Ƿ������ƽ��
	 * @return ͳ������k-merƵ�� hashmap��ʽ
	 */
	public static HashMap<String, Double> freq(String[] k_mer, String[] allk_mer, boolean flag) {
		double alpha = Math.pow(10, -8);
		double n1 = k_mer.length + allk_mer.length * alpha;
		double n2 = k_mer.length;
		HashMap<String, Double> kmerCount = countkmer(k_mer, allk_mer, flag);
		Iterator<Entry<String, Double>> iterator = kmerCount.entrySet().iterator();
		HashMap<String, Double> freq = new HashMap<>();
		while (iterator.hasNext()) {
			Map.Entry<String, Double> entry = (Map.Entry<String, Double>) iterator.next();
			if (flag) {
				freq.put(entry.getKey(), ((double) entry.getValue() / n1));
			} else {
				freq.put(entry.getKey(), ((double) entry.getValue() / n2));
			}
		}
		return freq;
	}

	/**
	 * 
	 * @param k_merSet
	 *            ��������ȡ����k-mer
	 * @param allkmer
	 *            ���п��ܵ� k-mer
	 * @param flag 
	 * 			�Ƿ������ƽ��
	 * @return ����hashmap k_mer Ƶ�� ͳ��������k_mer�ĸ��� ,ƽ���Ժ󣬼���ʼÿ��k-mer���ִ���Ϊalpha��������0�������
	 */
	public static HashMap<String, Double> countkmer(String[] k_merSet, String[] allkmer, boolean flag) {
		HashMap<String, Double> count = new HashMap<>();
		/* ��ʼ��hashmap ƽ������alpha */
		double alpha = Math.pow(10, -8);
		if (flag) {
			for (String k_mer : allkmer) 
				count.put(k_mer, alpha);// ��Ϊalpha
		} else {
			for (String k_mer : allkmer) 
				count.put(k_mer, 0.0);// ����ƽ�� ��Ϊ0
		}
		for (String x : k_merSet) {
			if (count.get(x)==null) {
				System.out.println(x);
			}
			count.put(x, count.get(x) + 1.0);
		}
		return count;
	}

	/**
	 * @param dict
	 * @param k
	 * @return ����õ�k_merת�����ַ�������
	 */
	public static String[] getK_mer(int k) {
		String b = new String();
		StringBuffer result = new StringBuffer();
		result = getAllK_mer(dict, k, b, result);
		return result.toString().split(" ");
	}

	/**
	 * 
	 * @param x
	 * @param k
	 *            k-mer�е�k
	 * @return xk_mer ��������еĴ��ڵ�k-mer
	 */
	public static String[] getseqk_mer(String X, int k) {
		int length = X.length();
		String[] xk_mer = new String[length - k + 1];
		for (int i = 0; i < length - k + 1; i++) {
			xk_mer[i] = X.substring(i, i + k);
		}
		return xk_mer;
	}

	/**
	 * 
	 * @param A
	 *            �ַ���
	 * @param k
	 *            k_mer�е�k
	 * @param b
	 *            ��ʱ����
	 * @param result
	 * @return result ��� ���е�k-mer O(4^k)
	 */
	public static StringBuffer getAllK_mer(char[] A, int k, String b, StringBuffer result) {
		if (k == 0) {
			result.append(b + " ");
		} else {
			for (int j = 0; j < A.length; j++) {
				b += A[j];
				getAllK_mer(A, k - 1, b, result);
				b = b.substring(0, b.length() - 1);
			}
		}
		return result;
	}
	
	public static double[] haoFeature(String sequence, int k) {
		/* �õ�k-mer ��allk_mer */
		String[] k_Allmer = getK_mer(k);
		double[] k_1freq =seqFeaturefreq(sequence, k, k_Allmer, false);
		double[] prep=preProhao(sequence, k);
		double[] p=new double[(int)Math.pow(4, k)];
		for(int i=0;i<p.length;i++) {
			if(prep[i]==0) {
				p[i]=0;
			}else {
				p[i]=(k_1freq[i]-prep[i])/prep[i];
			}
		}
	
		return 	normdata(p);
	}

	/**
	 * �õ�Ԥ�����
	 * 
	 * @param sequence
	 *            ��������
	 * @param k
	 * @return
	 */
	public static double[] preProhao(String sequence, int k) {
		/* �õ�k-mer ��allk_mer */
		String[] k_Allmer = getK_mer( k);
		HashMap<String, Double> kfreq = new HashMap<>();
		kfreq = inithashmap(kfreq, k_Allmer);
		/* �õ�(k-1)-mer,��all_k-1mer */
		String[] k_1mer = getseqk_mer(sequence, k - 1);
		String[] k_1Allmer = getK_mer( k - 1);
		HashMap<String, Double> k_1freq = freq(k_1mer, k_1Allmer,false);
		if (k < 1) {
			return null;
		}else if (k == 1) {
			return Markov.initProb(sequence);
		}else if (k == 2) {
			for (int i = 0; i < k_Allmer.length; i++) {
				double tmp = k_1freq.get(k_Allmer[i].substring(0, 1)) * k_1freq.get(k_Allmer[i].substring(1, 2));
				kfreq.put(k_Allmer[i], tmp);
			}
		} else {
			/* �õ�(k-2)-mer��all_k-2mer */
			String[] k_2mer = getseqk_mer(sequence, k - 2);
			String[] k_2Allmer = getK_mer( k - 2);
			HashMap<String, Double> k_2freq = freq(k_2mer, k_2Allmer,true);
			for (int i = 0; i < k_Allmer.length; i++) {
				double tmp1 = k_1freq.get(k_Allmer[i].substring(0, k - 1)) * k_1freq.get(k_Allmer[i].substring(1, k));
				double tmp2 = tmp1 / k_2freq.get(k_Allmer[i].substring(1, k - 1));
				kfreq.put(k_Allmer[i], tmp2);
			}
		}
		int n = (int) Math.pow(4, k);
		double[] prep = new double[n];
		for (int i = 0; i < prep.length; i++) {
			String tmp = k_Allmer[i];
			prep[i] = kfreq.get(tmp);
		}
		return prep;
	}
		
	/**
	 * ������D2S D2*���� ��Ҫƽ������
	 * @param sequence
	 * @param k
	 * @param allk_mer,
	 * @return
	 */
	public static double[] D2SCount(String sequence,int k,String[] allk_mer,int r) {
		int n=sequence.length()-k+1;
		double [] sc=seqFeatureCount(sequence, k, allk_mer, true);
		//double [] sc=seqFeaturefreq(sequence, k, allk_mer, true);
		//���ݱ�׼��
		double [] p=Markov.k_merMarkovProb(sequence, k,r);
		for(int i=0;i<sc.length;i++) {
			sc[i]=sc[i]-n*p[i];
		}
		return sc;
	}
	
	public static double[] D2starCount(String sequence,int k,String[] allk_mer,int r) {
		int n=sequence.length()-k+1;
		double [] sc=D2SCount(sequence, k, allk_mer, r);
		//sc=normdata(sc);
		//���ݱ�׼��
		double [] p=Markov.k_merMarkovProb(sequence, k,r);
		for(int i=0;i<sc.length;i++) {
			//double tmp =Math.sqrt(n*p[i]*(1-p[i]));
			double tmp =Math.sqrt(n*p[i]);
			if(tmp==0) {
				tmp=Math.pow(10, -8);
			}
			sc[i]=sc[i]/tmp;
		}
		return sc;
	}
	/**
	 * �� k��ȡ����,����zero-score
	 * @param sequence
	 * @param flag
	 * @return
	 */
	public static double[] seqAllFeaturefreq(String sequence,int startK,int kmax,boolean flag) {
		double[] feature=null;
//		double totalnum=0.0;
//		for(int k=startK;k<=kmax;k++) {
//			totalnum+=Math.pow(4, k);
//		}
		for(int k=startK;k<=kmax;k++) {
			String[] allk_mer=getK_mer(k);
			//double coe= Math.pow(4,k)/totalnum;
			//coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=seqFeaturefreq(sequence, k, allk_mer, flag);
//			tmp=multiply(coe, tmp);
			tmp=normdata(tmp);
			feature=merge2double(tmp,feature);
		}
		//xf=normdata(xf);
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 * ȫ��Ƶ�ʣ��������ֵ��Сֵ��һ������û�и�������
	 * @param sequence
	 * @param startK
	 * @param kmax
	 * @param flag
	 * @return
	 */
	public static double[] seqAllFeaturefreq2(String sequence,int startK,int kmax,boolean flag) {
		double[] feature=null;
//		double totalnum=0.0;
//		for(int k=startK;k<=kmax;k++) {
//			totalnum+=Math.pow(4, k);
//		}
		for(int k=startK;k<=kmax;k++) {
			String[] allk_mer=getK_mer(k);
			//double coe= Math.pow(4,k)/totalnum;
			//coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=seqFeaturefreq(sequence, k, allk_mer, flag);
//			tmp=multiply(coe, tmp);
			tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//xf=normdata(xf);
		//��
		//feature=normdata(feature);
		return feature;
	}
	
	/**
	 * �� k��ȡ���� D2
	 * @param sequence
	 * @param kmax
	 * @param flag
	 * @return
	 */
	public static double[] seqAllFeatureCount(String sequence,int startk, int kmax,boolean flag) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startk;k<=kmax;k++) {
			String[] allk_mer=getK_mer(k);
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=seqFeatureCount(sequence, k, allk_mer, flag);
			//tmp=multiply(coe, tmp);
			//��1
			tmp=normdata(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 *  ȫ��Ƶ�ʣ��������ֵ��Сֵ��һ������û�и�������
	 * @param sequence
	 * @param startk
	 * @param kmax
	 * @param flag
	 * @return
	 */
	public static double[] seqAllFeatureCount2(String sequence,int startk, int kmax,boolean flag) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startk;k<=kmax;k++) {
			String[] allk_mer=getK_mer(k);
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=seqFeatureCount(sequence, k, allk_mer, flag);
			//tmp=multiply(coe, tmp);
			//��1
			tmp=normdata2(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 * �� k��ȡ���� D2S
	 * @param sequence
	 * @param kmax
	 * @param r
	 * @return
	 */
	public static double[] D2SAllCount(String sequence,int startK,int kmax,int r) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			String[] allk_mer=getK_mer(k);
			double[] tmp=D2SCount(sequence, k, allk_mer, r);
			//tmp=multiply(coe, tmp);
			//�� 1
			tmp=normdata(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 * �������ֵ��Сֵ��һ����������ָ�ֵ
	 * @param sequence
	 * @param startK
	 * @param kmax
	 * @param r
	 * @return
	 */
	public static double[] D2SAllCount2(String sequence,int startK,int kmax,int r) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			String[] allk_mer=getK_mer(k);
			double[] tmp=D2SCount(sequence, k, allk_mer, r);
			//tmp=multiply(coe, tmp);
			//�� 1
			tmp=normdata2(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 * �� k��ȡ����
	 * @param sequence
	 * @param kmax
	 * @return
	 */
	public static double[] haoAllFeature(String sequence,int startK, int kmax) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=haoFeature(sequence, k);
			//tmp=multiply(coe, tmp);
			tmp=normdata(tmp);
			feature=merge2double(tmp,feature);
		}
		return 	feature;
	}
	/**
	 * ���ֵ��Сֵ��һ��
	 * @param sequence
	 * @param startK
	 * @param kmax
	 * @return
	 */
	public static double[] haoAllFeature2(String sequence,int startK, int kmax) {
		double[] feature=null;
		//double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			//double coe=Math.pow(4,k)/totalnum;
			//coe=Statistics.logarithm(2, 1/coe);
			double[] tmp=haoFeature(sequence, k);
			//tmp=multiply(coe, tmp);
			tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		return 	feature;
	}
	/**
	 * @param sequence
	 * @param kmax
	 * @return
	 */
	public static double[] D2starAllCount(String sequence,int startK,int kmax,int r) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			String[] allk_mer=getK_mer(k);
			double[] tmp=D2starCount(sequence, k, allk_mer, r);
			//tmp=multiply(coe, tmp);
			//�� 1
			tmp=normdata(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/**
	 * �������ֵ��Сֵ��һ��
	 * @param sequence
	 * @param startK
	 * @param kmax
	 * @param r
	 * @return
	 */
	public static double[] D2starAllCount2(String sequence,int startK,int kmax,int r) {
		double[] feature=null;
		double totalnum=0.0;
		for(int k=startK;k<=kmax;k++) {
			double coe=Math.pow(4,k)/totalnum;
			coe=Statistics.logarithm(2, 1/coe);
			String[] allk_mer=getK_mer(k);
			double[] tmp=D2starCount(sequence, k, allk_mer, r);
			//tmp=multiply(coe, tmp);
			//�� 1
			tmp=normdata2(tmp);
			//tmp=normdata2(tmp);
			feature=merge2double(tmp,feature);
		}
		//��
		//feature=normdata(feature);
		return feature;
	}
	/*
	 * ���ݳ���ϵ��
	 */
	public static double[] multiply(double k,double[] data) {
		for(int i=0;i<data.length;i++) {
			data[i]=data[i]*k;
		}
		return data;
	}
	/**
	 * �ϲ�����
	 * @param X
	 * @param Y
	 * @return
	 */
	public static double[] merge2double(double[] X,double Y[]) {
		if(X==null&&Y!=null) {
			return Y;
		}else if(Y==null&&X!=null) {
			return X;
		}else if(X.length==0&&Y.length!=0) {
			return Y;
		}else if(Y.length==0&&X.length!=0) {
			return X;
		}else if(X.length!=0&&Y.length!=0) {
			int xlength=X.length;
			int ylength=Y.length;
			int zlength=xlength+ylength;
			int count=0;
			double [] Z=new double[zlength];
			for(int i=0;i<xlength;i++) {
				Z[count++]=X[i];
			}
			for(int j=0;j<ylength;j++) {
				Z[count++]=Y[j];
			}
			return Z;
		}else {
			return null;
		}
	}
	/**
	 * ��׼������
	 * @param feature
	 * @return
	 */
	public static double[] normdata(double[] feature) {
		double mean=Statistics.mean(feature);
		double sd=Statistics.sd(feature);
		for(int i=0;i<feature.length;i++) {
			feature[i]=(feature[i]-mean)/sd;
		}
		return feature;
	}
	/**
	 * ���ֵ��Сֵ��һ��
	 * @param feature
	 * @return
	 */
	public static double[] normdata2(double[] feature) {
		double max=0.0;
		double min=Double.MAX_VALUE;
		for(int i=0;i<feature.length;i++) {
			if(feature[i]>max) {
				max=feature[i];
			}
			if(feature[i]<min) {
				min=feature[i];
			}
		}
		for(int i=0;i<feature.length;i++) {
			feature[i]=(feature[i]-min)/(max-min);
			if(feature[i]<0) {
				System.out.println("���������������������������������");
			}
		}
		return feature;
	}
	/**
	 *  <string,double>hashmap ��ʼ��
	 *  
	 */
	private static HashMap<String, Double> inithashmap(HashMap<String, Double> hashMap, String[] allkmer) {
		for (String string : allkmer) {
			hashMap.put(string, 0.0);
		}
		return hashMap;
	}
	/**
	 * ����Ȩ�� �����������
	 * @param freqs
	 * @return
	 */
	public static double[] getWeight(double[][] freqs) {
		int size=freqs[0].length;
		double[] weight=new double[size];
		double[] sumf=new double[size];
		for(int i=0;i<freqs[0].length;i++) {
			for(int j=0;j<freqs.length;j++) {
				for(int k=0;k<freqs.length;k++) {
					sumf[i]+=Math.abs(freqs[j][i]-freqs[k][i]);
				}
			}
		}
		double total=0.0;
		for(int i=0;i<size;i++) {
			total+=sumf[i];
		}
		for(int j=0;j<size;j++) {
			weight[j]=sumf[j]/total;
		}
		return weight;
	}
	
	
	/**
	 * ����Ȩ�� ������ֵ����
	 * @param freqs
	 * @return
	 */
	public static double[] getWeight1(double[][] freqs) {
		for(int i=0;i<freqs.length;i++) {
			freqs[i]=normdata2(freqs[i]);
		}
		//��
		int row=freqs.length;
		//��
		int col=freqs[0].length;
		double K=1/Statistics.logarithm(Math.E, row);
		//System.out.println(K);
		//����ÿ�к�
		double[] sum=new double[col];
		for (int i=0;i<col;i++) {
			for(int j=0;j<row;j++) {
				sum[i]=sum[i]+freqs[j][i];
			}
			if(sum[i]==0) {
				sum[i]=Math.pow(10, -10);
			}
		}
		double [][] contri=new double[row][col];
		//���㹱�׶�
		for(int i=0;i<col;i++) {
			for(int j=0;j<row;j++) {
				if(freqs[j][i]!=0) {
					contri[j][i]=freqs[j][i]/sum[i];
				}else {
					contri[j][i]=Math.pow(10, -10)/sum[i];
				}
			}
		}
		for(int i=0;i<col;i++) {
			for(int j=0;j<row;j++) {
				contri[j][i]=contri[j][i]*Statistics.logarithm(Math.E, contri[j][i]);
			}
		}

		//�����ܹ��׶�
		double[] contribution=new double [col];
		for(int i=0;i<col;i++) {
			for(int j=0;j<row;j++) {
				contribution[i]+=-contri[j][i]*K;
			}
		}
		//����һ���Գ̶�
		double [] consistency =new double[col];
		for(int i=0;i<col;i++) {
			consistency[i]=1-contribution[i];
		}
		//����Ȩ��
		double [] weight=new double[col];
		double tmpSum=0.0;
		for(int i=0;i<col;i++) {
			tmpSum+=consistency[i];
		}
		for(int i=0;i<col;i++) {
			weight[i]=consistency[i]/tmpSum;
		}
		System.out.println();
		return weight;
	}
}
