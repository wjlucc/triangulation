package triangulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Triangulation {

	// ��Žڵ�����
	private Point[] point = new Point[99];
	// value ��ż��������������ֵ
	private double[][] value;
	// prePoint[i][j]��¼��Vi-1��Vj���������εĵ�3�������λ��
	private int[][] prePoint;
	// n����ܵĶ������Ŀ
	private int n = 0;

	public Triangulation(String path)  {
		try {
			readData(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		initValue();
		
	}
	
	/**
	 * ��Ҫ�������Ԫ�س�ʼ��Ϊ0
	 */
	private void initValue() {

		value = new double[n][n];
		prePoint = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				value[i][j] = 0;
				prePoint[i][j] = 0;
			}
		}
	}

	/**
	 * �������������Ԫ�ص�ֵ����ӡ�������ʷֽ����
	 * 
	 * @param Point
	 */
	public void getValue() {
		for (int r = 1; r < n - 1; r++) {
			// ȷ��i�ķ�Χ�� 1 ���� (n-r-1)��
			for (int i = 1; i < (n - r); i++) {
				// ȷ��j��λ�� j = i + r
				int j = i + r;
				value[i][j] = value[i + 1][j]
						+ getWight(point[i - 1], point[i], point[j]);
				prePoint[i][j] = i;
				for (int k = i + 1; k < j; k++) {
					double temp = value[i][k] + value[k + 1][j]
							+ getWight(point[i - 1], point[k], point[j]);
					if (temp < value[i][j]) {
						value[i][j] = temp;
						prePoint[i][j] = k;
					}
				}
			}
		}
		System.out.println("�������ε����������ʷ֣�" + value[1][n - 1]);
	}

	
	/**
	 * ������������㣬���ض�Ӧ�������ε��ܳ���
	 * 
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	private double getWight(Point i, Point j, Point k) {
		double result = 0.0;
		result += Math
				.sqrt(Math.pow((i.x - j.x), 2) + Math.pow((i.y - j.y), 2));
		result += Math
				.sqrt(Math.pow((i.x - k.x), 2) + Math.pow((i.y - k.y), 2));
		result += Math
				.sqrt(Math.pow((k.x - j.x), 2) + Math.pow((k.y - j.y), 2));

		return result;
	}

	/**
	 * ��ӡ���յĽ����
	 */
	public void printResult(){
		printResult(1,n-1);
	}
	
	/**
	 * i ��  j��ʾ ��ʼλ�á�
	 * @param i ����ε����
	 * @param j
	 */
	private void printResult(int i, int j) {
		if (i == j)
			return;		
		printResult(i, prePoint[i][j]);
		printResult(prePoint[i][j] + 1, j);
		System.out.println("�������ʷֶ��㣺 V" + (i - 1) + ",V" + prePoint[i][j] + ",V" + j);
	}
	
	/**
	 * ��ȡ���ݣ����������顣����n��ֵ��
	 * @param path �ļ�·��
	 * @throws IOException
	 */
	public void readData(String path) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = "";
		while((line=br.readLine())!=null){
			String[] temp = line.split(",");
			double x = Double.parseDouble(temp[0].trim());
			double y = Double.parseDouble(temp[1].trim());
//			System.out.println(x+"------"+y);
			point[n]  = new Point(x,y);
			n++;
		}			
	}
}






