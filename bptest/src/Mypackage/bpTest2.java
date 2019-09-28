package Mypackage;

public class bpTest2 {
    public static void main(String[] args){
        long starttime = System.currentTimeMillis();
    double[][] matrix={{5.1,3.5,1.4,0.2},
                        {4.9,3,1.4,0.2},
                        {4.7,3.2,1.3,0.2},
                        {4.6,3.1,1.5,0.2},
                        {5,3.6,1.4,0.3},
                        {5.4,3.9,1.7,0.4},
                        {4.6,3.4,1.4,0.3},
                        {5,3.4,1.5,0.2},
                        {4.4,2.9,1.4,0.2},
                        {4.9,3.1,1.5,0.1},
                        {5.4,3.7,1.5,0.2},
                        {4.8,3.4,1.6,0.2},
                        {4.8,3,1.4,0.1},
                        {4.3,3,1.1,0.1},
                        {5.8,4,1.2,0.2},
            /*Type1 with the latter five added from testgroup.*/
                        {7,3.2,4.7,1.4},
                        {6.4,3.2,4.5,1.5},
                        {6.9,3.1,4.9,1.5},
                        {5.5,2.3,4,1.3},
                        {6.5,2.8,4.6,1.5},
                        {5.7,2.8,4.5,1.3},
                        {6.3,3.3,4.7,1.6},
                        {4.9,2.4,3.3,1},
                        {6.6,2.9,4.6,1.3},
                        {5.2,2.7,3.9,1.4},
                        {5,2,3.5,1},
                        {5.9,3,4.2,1.5},
                        {6,2.2,4,1},
                        {6.1,2.9,4.7,1.4},
                        {5.6,2.9,3.6,1.3},
            /*Type2 with the latter five added from testgroup.*/
                        {6.3,3.3,6,2.5},
                        {5.8,2.7,5.1,1.9},
                        {7.1,3,5.9,2.1},
                        {6.3,2.9,5.6,1.8},
                        {6.5,3,5.8,2.2},
                        {7.6,3,6.6,2.1},
                        {4.9,2.5,4.5,1.7},
                        {7.3,2.9,6.3,1.8},
                        {6.7,2.5,5.8,1.8},
                        {7.2,3.6,6.1,2.5},
                        {6.5,3.2,5.1,2},
                        {6.4,2.7,5.3,1.9},
                        {6.8,3,5.5,2.1},
                        {5.7,2.5,5,2},
                        {5.8,2.8,5.1,2.4}
                        /*Type3 with the latter five added from testgroup.*/};
                /*double[][] matrixc = new double[matrix.length][matrix[0].length];
                for(int i=0;i<matrix.length;i++)
                    for(int j=0;j<matrix[0].length;j++)
                        matrixc[i][j] = matrix[i][j];*/
                double[][] matrix1 = {{1},{2},{3}};
                /*double[][] matrix1c = new double[matrix1.length][matrix1[0].length];
                for(int i=0;i<matrix1.length;i++)
                     for(int j=0;j<matrix1[0].length;j++)
                matrix1c[i][j] = matrix1[i][j];*/
                modification1(matrix1);
                modification(matrix);
                double[][] matrix2=new double[45][1];
                for(int i=0;i<15;i++)
                    matrix2[i][0]=matrix1[0][0];
                for(int i=15;i<30;i++)
                    matrix2[i][0]=matrix1[1][0];
                for(int i=30;i<45;i++)
                    matrix2[i][0]=matrix1[2][0];
                bp a = new bp();
                a.train(matrix,matrix2);
                a.bpNetOutput(matrix,matrix2);
                long endtime = System.currentTimeMillis();
                System.out.println("Time spent is: " + (endtime-starttime)/1000.0);
                double[][] testgroup =  {
                                        {5.7,4.4,1.5,0.4},
                                        {5.4,3.9,1.3,0.4},
                                        {5.1,3.5,1.4,0.3},
                                        {5.7,3.8,1.7,0.3},
                                        {5.1,3.8,1.5,0.3},/*Type1*/
                                        {6.7,3.1,4.4,1.4},
                                        {5.6,3,4.5,1.5},
                                        {5.8,2.7,4.1,1},
                                        {6.2,2.2,4.5,1.5},
                                        {5.6,2.5,3.9,1.1},/*Type2*/
                                        {6.4,3.2,5.3,2.3},
                                        {6.5,3,5.5,1.8},
                                        {7.7,3.8,6.7,2.2},
                                        {7.7,2.6,6.9,2.3},
                                        {6,2.2,5,1.5}/*Type3*/};
                double[] t1 = {matrix1[0][0]};
                double[] t2 = {matrix1[1][0]};
                double[] t3 = {matrix1[2][0]};
                modification(testgroup);

                for(int i=0;i<5;i++)
                    System.out.println(a.reversemodification(a.bpNetOut(testgroup[i],t1)[0],1,3) + "has error: " + a.E + " with original modified result: " + a.bpNetOut(testgroup[i],t1)[0] + "and original result: " + a.reversemodification1(a.bpNetOut(testgroup[i],t1)[0],1,3));
                for(int i=5;i<10;i++)
                    System.out.println(a.reversemodification(a.bpNetOut(testgroup[i],t2)[0],1,3) + "has error: " + a.E + " with original modified result: " + a.bpNetOut(testgroup[i],t2)[0] + "and original result: " + a.reversemodification1(a.bpNetOut(testgroup[i],t2)[0],1,3));
                for(int i=10;i<15;i++)
                    System.out.println(a.reversemodification(a.bpNetOut(testgroup[i],t3)[0],1,3) + "has error: " + a.E + " with original modified result: " + a.bpNetOut(testgroup[i],t3)[0] + "and original result: " + a.reversemodification1(a.bpNetOut(testgroup[i],t3)[0],1,3));


    }

/*Horizontal modification(By row)*/
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

/*Vertical modification(By column)*/
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
