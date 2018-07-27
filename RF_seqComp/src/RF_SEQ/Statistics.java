package RF_SEQ;
/* ͳ��ѧ��һЩ��ؼ���*/
public class Statistics {
	
	public static void main(String[] args) {
		double [] X= {5.0, 3.0, 2.5};
		double [] Y= {4.0, 3.0, 2.0};
		System.out.println(mean(X));
		System.out.println(var(X));
		System.out.println(sd(X));
		System.out.println(cov(X, Y));
	}
	/**
	 * @param X
	 * @return
	 * ����ƽ����
	 */
	public static double mean(double[] X) {
		double sum=0.0;
		for (double d : X) {
			sum+=d;
		}
		return sum/X.length;
	}
	/**
	 * ������ƫ����
	 * @param X
	 * @return
	 */
	public static double var(double[] X) {
		double sum=0.0;
		double mean=mean(X);
		for (double d : X) {
			sum+=Math.pow((d-mean),2);
		}
		return sum/(X.length-1);
	}
	/**
	 * ������ƫ��׼��
	 * @param X
	 * @return
	 */
	public static double sd(double[] X) {
		double sd=Math.sqrt(var(X));
		return sd;	
	}
	/**
	 * ����Э����
	 * @param X
	 * @param Y
	 * @return
	 */
	public static double cov(double[] X,double [] Y) {
		double sum=0.0;
		double meanX=mean(X);
		double meanY=mean(Y);
		for (int i=0;i<X.length;i++) {
			sum+=(X[i]-meanX)*(Y[i]-meanY);
		}
		return sum/(X.length-1);
	}
	/**
	 * �������
	 * @param base
	 * @param value
	 * @return
	 */
	public static double logarithm(double base,double value) {
		return Math.log(value)/Math.log(base);
	}

}
