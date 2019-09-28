package Mypackage;
import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLDouble;
import java.io.IOException;
public class bpTest1 {
    public static void main(String[] args)throws IOException {
        MatFileReader read = new MatFileReader("src/Mypackage/spectra_data.mat");
        MLArray mlArray = read.getMLArray("NIR");//mat存储的就是img矩阵变量的内容
        MLDouble d = (MLDouble) mlArray;
        double[][] matrix = (d.getArray());//只有jmatio v0.2版本中才有d.getArray方法
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print(matrix[i][j] + "  ");
            System.out.println("\n");
        }

        MatFileReader read1 = new MatFileReader("src/Mypackage/spectra_data.mat");
        MLArray mlArray1 = read1.getMLArray("octane");//mat存储的就是img矩阵变量的内容
        MLDouble d1 = (MLDouble) mlArray1;
        double[][] matrix1 = (d1.getArray());//只有jmatio v0.2版本中才有d.getArray方法

        System.out.println("The length of the NIR array is " + matrix[0].length + "*" + matrix.length);

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[0].length; j++)
                System.out.print(matrix1[i][j] + "  ");
            System.out.println("\n");
        }

        System.out.println("The length of the octane array is " + matrix1[0].length + "*" + matrix1.length);

        modification(matrix);
        modification1(matrix1);

        double[][] matrix2 = new double[60][20];
        for(int i=0;i<matrix2.length;i++)
            for(int j=0;j<matrix2[0].length;j++)
                matrix2[i][j]=matrix[i][j];



        for(int i=0;i<matrix2.length;i++) {
            for (int j = 0; j < matrix2[0].length; j++) {
                System.out.print(matrix2[i][j] + " ");
            }
        System.out.println("\n");
        }
        /*
        bp a = new bp();
        double[][] testarray = new double[2][401];
        for(int i=0;i<401;i++){
            testarray[0][i]=matrix[0][i];
        }
        for(int i=0;i<401;i++){
            testarray[1][i]=matrix[1][i];
        }
        double[][] testarray2 = new double[2][1];
        testarray2[0][0]=matrix1[0][0];
        testarray2[1][0]=matrix1[1][0];
        */
/*
        double[][] testarray = new double[1][401];
        for(int i=0;i<401;i++){
            testarray[0][i]=matrix[2][i];
        }
        double[][] testarray2 = new double[1][1];
        testarray2[0][0]= matrix1[2][0];

        a.train(testarray,testarray2);
        a.bpNetOutput(matrix,matrix1);
*/

       bp a;
        for(int i=0;i<60;i+=10){
            a = new bp();
            a.train(cut(matrix2,i,i+9), cut(matrix1,i,i+9));
            a.bpNetOutput(matrix2,matrix1);
            System.out.println("\n\n\n\n\n");
        }



        /*double[][] matrix = {{0,0},{0,1},{1,0},{1,1}};
        double[][] matrix1 = {{1},{1},{1},{0}};
        bp a = new bp();
        a.train(matrix,matrix1);
        for(int i=0;i<matrix.length;i++){
            System.out.println(a.bpNetOut(matrix[i],matrix1[i])[0] + "has an error of" + a.E);
        }*/
    }
        public static void modification(double[][] arr){
            int i = 0;
            while(i!=arr.length){
                double min = 100;
                double max = -100;
                for(int j=0;j<arr[0].length;j++) {
                    if(arr[i][j]<min)
                        min=arr[i][j];
                    if(arr[i][j]>max)
                        max=arr[i][j];
                }

                for(int j=0;j<arr[0].length;j++){
                    arr[i][j]=(arr[i][j]-min)/(max-min);
                }

                i++;

            }
        }

        public static void modification1(double[][] arr){
            int i = 0;
            while(i!=arr[0].length){
                double min = 100;
                double max = -100;
                for(int j=0;j<arr.length;j++){
                    if(arr[j][i]<min)
                        min=arr[j][i];
                    if(arr[j][i]>max)
                        max=arr[j][i];
                }

                for(int j=0;j<arr.length;j++){
                    arr[j][i]=(arr[j][i]-min)/(max-min);
                }

                i++;
            }
        }
/*
把一个二维数组裁剪成一个小的二维数组，方便训练。
 */
        public static double[][] cut(double[][] arr,int low, int up){
            double[][] newarr = new double[up-low+1][arr[0].length];
            for(int i=low;i<=up;i++){
                for(int j=0;j<arr[0].length;j++){
                    newarr[i-low][j]=arr[i][j];
                }
            }

            return newarr;
        }
}
