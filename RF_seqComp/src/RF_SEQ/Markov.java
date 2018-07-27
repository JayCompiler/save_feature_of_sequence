package RF_SEQ;

import java.util.HashMap;

/**
 * һ������·���ģ��
 * 
 * @author zy
 *
 */
public class Markov {

	public static HashMap<String, Integer> dict = new HashMap<String, Integer>();

	public static void init() {
		dict.put("A", 0);
		dict.put("T", 1);
		dict.put("C", 2);
		dict.put("G", 3);
	}
	public static void init2() {
		dict.put("AA", 0);
		dict.put("AT", 1);
		dict.put("AC", 2);
		dict.put("AG", 3);
		dict.put("TA", 4);
		dict.put("TT", 5);
		dict.put("TC", 6);
		dict.put("TG", 7);
		dict.put("CA", 8);
		dict.put("CT", 9);
		dict.put("CC", 10);
		dict.put("CG", 11);
		dict.put("GA", 12);
		dict.put("GT", 13);
		dict.put("GC", 14);
		dict.put("GG", 15);
	}
	public static void main(String[] args) {
		String xString="ATCGTTAGCG";
		double[] a=k_merMarkovProb(xString,2, 0);
		for (double d : a) {
			System.out.println(d+" ");
		}
	}
	/**
	 * ����1������ת�Ƹ��ʾ���
	 * 
	 * @param sequence
	 *            ����
	 * @return
	 */
	public static double[][] transMatrix(String sequence) {
		int k = 2;
		init();
		String[] seqk_mer = SeqComp.getseqk_mer(sequence, k);
		String[] allk_mer = SeqComp.getK_mer(k);
		HashMap<String, Double> pm = SeqComp.countkmer(seqk_mer, allk_mer, false);
		/* ����һ��markov��ת�Ƹ��ʾ��� */
		double[] count = SeqComp.xcount2double(pm, allk_mer);
		double[][] trans = new double[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (count[i * 4 + j] == 0) {
					trans[i][j] = 0.0;
				} else {
					trans[i][j] = count[i * 4 + j]
							/ (count[i * 4] + count[i * 4 + 1] + count[i * 4 + 2] + count[i * 4 + 3]);
				}
			}
		}
		return trans;
	}
	
	
	
	/**
	 * ���ض���markov״̬ת�ƾ���
	 * @param sequence
	 * @param r
	 * @return
	 */
	public static double[][] transMatrix2(String sequence){
		int k = 3;
		int size=16;
		/*�������k-mer*/
		String[] seqk_mer3 = SeqComp.getseqk_mer(sequence, k);

		/*���ȫ��k-mer*/
		String[] allk_mer3 = SeqComp.getK_mer(k);
		//String[] allk_mer2=SeqComp.getK_mer(k-1);
		/*hashmap��ʽ�Ĵʼ���*/
		HashMap<String, Double> pm3 = SeqComp.countkmer(seqk_mer3, allk_mer3, false);
		/*ת����double[]��*/
		double[] count = SeqComp.xcount2double(pm3, allk_mer3);
		/* ����һ��markov��ת�Ƹ��ʾ��� */
		double[][] trans = new double[size][4];
		for(int i=0;i<size;i++) {
			for(int j=0;j<4;j++) {
				if(count[i*4+j]==0) {
					trans[i][j]=0.0;
				}else {
					trans[i][j]= count[i * 4 + j]
							/ (count[i * 4] + count[i * 4 + 1] + count[i * 4 + 2] + count[i * 4 + 3]);	
				}
			}
		}
	
		return trans;
	}

	/**
	 * ���س�ʼ״̬����
	 * 
	 * @param sequence
	 *            ����
	 * @return
	 */
	public static double[] initProb(String sequence) {
		init();
		HashMap<Character, Double> init = new HashMap<>();
		int length = sequence.length();
		init.put('A', 0.0);
		init.put('T', 0.0);
		init.put('C', 0.0);
		init.put('G', 0.0);
		for (int i = 0; i < sequence.length(); i++) {
			char tmp = sequence.charAt(i);
			init.put(tmp, init.get(tmp) + 1);
		}
		double[] pro = new double[4];
		pro[0] = init.get('A') / length;
		pro[1] = init.get('T') / length;
		pro[2] = init.get('C') / length;
		pro[3] = init.get('G') / length;
		return pro;
	}
	

	/**
	 * 1��2��0������·�ģ�͵�k-mer����
	 * 
	 * @param sequence
	 * @param k
	 * @return
	 */
	public static double[] k_merMarkovProb(String sequence, int k, int r) {
		
		/*һ��ת�ƾ���*/
		double[][] transMatrix = transMatrix(sequence);
		/*����ת�ƾ���*/
		double[][] transMatrix2 = transMatrix2(sequence);
		//һ�׳�ʼ����
		double[] initProb = initProb(sequence);
		String[] allkmer = SeqComp.getK_mer(k);
		double[] k_merMarkovProb = new double[(int) Math.pow(4, k)];
		/**
		 * 1��
		 */
		if (r == 1) {
			init();
			for (int i = 0; i < allkmer.length; i++) {
				String start = allkmer[i].substring(0, 1);
				k_merMarkovProb[i] = initProb[dict.get(start)];
				for (int j = 0; j < k - 1; j++) {
					int st = dict.get(allkmer[i].substring(j, j + 1));
					int ed = dict.get(allkmer[i].substring(j + 1, j + 2));
					k_merMarkovProb[i] *= transMatrix[st][ed];
				}
			}
		}
		/**
		 * 0��
		 */
		if(r==0) {
			for (int i = 0; i < allkmer.length; i++) {
				k_merMarkovProb[i] =1.0;
				for (int j = 0; j < k ; j++) {
					int st = dict.get(allkmer[i].substring(j, j + 1));
					k_merMarkovProb[i] *= initProb[st];
				}
			}
		}
		/**
		 * 2��
		 */
		if(r==2) {
			if(k==2) {
				return k_merMarkovProb(sequence, k, 1);
			}else {
				init2();
				init();
				for (int i = 0; i < allkmer.length-1; i++) {
					String start1 = allkmer[i].substring(0, 1);
					String start2 = allkmer[i].substring(1, 2);
					k_merMarkovProb[i] = initProb[dict.get(start1)]*initProb[dict.get(start2)];
					for (int j = 0; j < k - 2; j++) {
						String tmp=allkmer[i].substring(j, j + 1)+allkmer[i].substring(j+1, j + 2);
						int st = dict.get(tmp);
						int ed = dict.get(allkmer[i].substring(j + 2, j + 3));
						k_merMarkovProb[i] *= transMatrix2[st][ed];
					}
				}
				
			}
		}
		
		
		return k_merMarkovProb;
	}

}
