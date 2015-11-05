package triangulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Triangulation {

	// 存放节点数组
	private Point[] point = new Point[99];
	// value 存放计算的自序列最优值
	private double[][] value;
	// prePoint[i][j]记录了Vi-1和Vj构成三角形的第3个顶点的位置
	private int[][] prePoint;
	// n存放总的顶点的数目
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
	 * 将要计算矩阵元素初始化为0
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
	 * 计算这个矩阵中元素的值，打印出最优剖分结果。
	 * 
	 * @param Point
	 */
	public void getValue() {
		for (int r = 1; r < n - 1; r++) {
			// 确定i的范围从 1 —— (n-r-1)。
			for (int i = 1; i < (n - r); i++) {
				// 确定j的位置 j = i + r
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
		System.out.println("此三角形的最优三角剖分：" + value[1][n - 1]);
	}

	
	/**
	 * 传入三个坐标点，返回对应的三角形的周长。
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
	 * 打印最终的结果。
	 */
	public void printResult(){
		printResult(1,n-1);
	}
	
	/**
	 * i 和  j表示 起始位置。
	 * @param i 多边形的起点
	 * @param j
	 */
	private void printResult(int i, int j) {
		if (i == j)
			return;		
		printResult(i, prePoint[i][j]);
		printResult(prePoint[i][j] + 1, j);
		System.out.println("三角形剖分顶点： V" + (i - 1) + ",V" + prePoint[i][j] + ",V" + j);
	}
	
	/**
	 * 读取数据，存入结点数组。并给n赋值。
	 * @param path 文件路径
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






