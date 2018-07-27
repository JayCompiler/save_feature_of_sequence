package RF_SEQ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class WriteFile {

	public static void main(String[] args) {
		File file = new File("src/output/1.txt");
		boolean b = false;
		try {
			b = createFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(b);
		double[][] data = new double[10][10];
		String[] name = new String[10];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				data[i][j] = new Random().nextDouble();
			}
		}
		for (int i = 0; i < name.length; i++) {
			name[i] = Integer.toString(i);
		}
		try {
			writeFile(data, name, "1.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * д���ļ�
	 * 
	 * @param matrix
	 * @param name
	 * @throws IOException
	 */
	public static void writeFile(double[][] data, String[] dataname, String filename) throws IOException {
		// �����ļ�
		File pathname = new File("src/output/" + filename);
		try {
			boolean b = createFile(pathname);
			if (!b) {
				System.out.println("�����ļ�ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// д���ݵ��ļ�
		int m = data.length;
		int n = data[0].length;
		BufferedWriter filewrite = new BufferedWriter(new FileWriter(pathname));
		filewrite.write(m+"\r");
		for (int i = 0; i < m; i++) {
			String tmp = dataname[i];
			for (int j = 0; j < n; j++) {
				tmp = tmp + "\t" + data[i][j];
			}
			tmp = tmp + "\r";
			filewrite.write(tmp);
			filewrite.flush();
		}
		filewrite.close();
	}
	
	/**
	 * д��ROC����
	 * @param data
	 * @param dataname
	 * @param filename
	 * @throws IOException
	 */
	public static void writeCood(double[][] data,String filename) throws IOException {
		// �����ļ�
		File pathname = new File("src/ROC1/" + filename);
		try {
			boolean b = createFile(pathname);
			if (!b) {
				System.out.println("�����ļ�ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// д���ݵ��ļ�
		int m = data.length;
		int n = data[0].length;
		BufferedWriter filewrite = new BufferedWriter(new FileWriter(pathname));
		for (int i = 0; i < m; i++) {
			String tmp = "";
			for (int j = 0; j < n; j++) {
				tmp = tmp + "\t" + data[i][j];
			}
			tmp = tmp + "\r";
			filewrite.write(tmp);
			filewrite.flush();
		}
		filewrite.close();
	}
	/**
	 * �ַ������뵽�ı�����
	 * @param data
	 * @param dataname
	 * @param filename
	 * @throws IOException
	 */
	public static void writeFileS(String[][] data, String[] dataname, String filename) throws IOException {
		// �����ļ�
		File pathname = new File("src/output/" + filename);
		try {
			boolean b = createFile(pathname);
			if (!b) {
				System.out.println("�����ļ�ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// д���ݵ��ļ�
		int m = data.length;
		int n = data[0].length;
		BufferedWriter filewrite = new BufferedWriter(new FileWriter(pathname));
		filewrite.write("\t"+m+"\r");
		for (int i = 0; i < m; i++) {
			String tmp = dataname[i];
			for (int j = 0; j < n; j++) {
				tmp = tmp + "\t" + data[i][j];
			}
			tmp = tmp + "\r";
			filewrite.write(tmp);
			filewrite.flush();
		}
		filewrite.close();

	}

	/**
	 * �����ļ�
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean createFile(File fileName) throws Exception {
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void writeFeature(double[][] data,String filename) throws IOException {
		// �����ļ�
		File pathname = new File(filename);
		try {
			boolean b = createFile(pathname);
			if (!b) {
				System.out.println("�����ļ�ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// д���ݵ��ļ�
		int m = data.length;
		int n = data[0].length;
		BufferedWriter filewrite = new BufferedWriter(new FileWriter(pathname));
		for (int i = 0; i < m; i++) {
			String tmp = "";
			for (int j = 0; j < n; j++) {
				tmp = tmp + "\t" + data[i][j];
			}
			tmp = tmp + "\r";
			filewrite.write(tmp);
			filewrite.flush();
		}
		filewrite.close();
	}
	public static void writeLabel(String[] data,String filename) throws IOException {
		// �����ļ�
		File pathname = new File("src/label/" + filename);
		try {
			boolean b = createFile(pathname);
			if (!b) {
				System.out.println("�����ļ�ʧ�ܣ�");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// д���ݵ��ļ�
		int m = data.length;
		BufferedWriter filewrite = new BufferedWriter(new FileWriter(pathname));
		for (int i = 0; i < m; i++) {
			String tmp = "";
			tmp = tmp + data[i]+ "\r";
			filewrite.write(tmp);
			filewrite.flush();
		}
		filewrite.close();
	}

}
