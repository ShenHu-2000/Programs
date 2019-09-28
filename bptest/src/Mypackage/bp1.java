package Mypackage;

public class bp1 {
    public static final int IM = 401;
    public static final int RM = 5;
    public static final int RM1 = 5;
    public static final int OM = 1;
    public double learnRate = 0.007;
    public double W[][] = new double[IM][RM];
    public double cW[][] = new double[IM][RM];
    public double W1[][] = new double[RM][RM1];
    public double cW1[][] = new double[RM][RM1];
    public double Wout[][] = new double[RM1][OM];
    public double cWout[][] = new double[RM1][OM];
    public double X1[] = new double[IM];
    public double X2[] = new double[RM];
    public double X2Active[] = new double[RM];
    public double X3[] = new double[RM1];
    public double X3Active[] = new double[RM1];
    public double X4[] = new double[OM];
    public double Er[] = new double[OM];
    public double E = 0.01;
    public double[] off2 = new double[RM];
    public double[] off3 = new double[RM1];
    public double[] off4 = new double[OM];
    public double[] coff2 = new double[RM];
    public double[] coff3 = new double[RM1];

    public bp1() {
        for(int i=0;i<IM;i++)
            for(int j=0;j<RM;j++)
            {
                W[i][j]=Math.random();
                X2[j]=0;
            }

        for(int j=0;j<RM;j++)
            for(int k=0;k<RM1;k++){
                W1[j][k]=Math.random();
                X3[k]=0;
            }

        for(int k=0;k<RM1;k++)
            for(int l=0;l<OM;l++){
                Wout[k][l]=Math.random();
                X4[l]=0;
            }

            for(int j=0;j<RM;j++)
                off2[j]=Math.random();

            for(int k=0;k<RM1;k++)
                off3[k]=Math.random();

            for(int l=0;l<OM;l++)
                off4[l]=Math.random();
    }

    public void bpNetForwardProcess(double[] input,double[] output){
        for(int i=0;i<IM;i++)
            X1[i]=input[i];

        for(int j=0;j<RM;j++){
            X2[j]=0;
            for(int i=0;i<IM;i++)
                X2[j]+=X1[i]*W[i][j];
        }

        for(int j=0;j<RM;j++)
            X2Active[j]=1/(Math.exp(-(X2[j]+off2[j]))+1);

        for(int k=0;k<RM1;k++){
            X3[k]=0;
            for(int j=0;j<RM;j++)
                X3[k]+=X2Active[j]*W1[j][k];
        }

        for(int k=0;k<RM1;k++)
            X3Active[k]=1/(Math.exp(-(X3[k]+off3[k]))+1);

        for(int l=0;l<OM;l++){
            X4[l] = 0;
            for (int k = 0; k < RM1; k++)
                X4[l] += X3Active[k] * Wout[k][l];
            X4[l] += off4[l];
        }


        for(int l=0;l<OM;l++)
            Er[l]=output[l]-X3[l];

        E=0;

        for(int l=0;l<OM;l++)
            E+=Er[l]*Er[l]/2;

    }

    public void bpNetReturnProcess(double[] output){
        /*隐含层到输出层的权值修改。每改变一次权值都更新一次偏置。然后再修改输入层到隐含层的权值，每改变一次权值都更新一次偏置。
         */
        int count = 0;
        int count1 = 0;
        int count2 = 0;

        for(int k=0;k<RM1;k++){
            for(int l=0;l<OM;l++){
                cWout[k][l]=learnRate*Er[k]*X2Active[l];
                Wout[k][l]+=cWout[k][l];
                count1++;
                if(count1<=OM)
                    off4[l]+=learnRate*Er[l];
            }
        }

        for(int i=0;i<IM;i++){
            for(int j=0;j<RM;j++){
                cW[i][j]=0;
                coff2[j]=0;
                for(int k=0;k<RM1;k++){
                    cW[i][j]+=learnRate*X2Active[j]*(1-X2Active[j])*X1[i]*Wout[j][k]*Er[k];
                    coff2[j]+=learnRate*X2Active[j]*(1-X2Active[j])*Wout[j][k]*Er[k];
                }
                W[i][j]+=cW[i][j];
                count++;
                if(count<=RM)
                    off2[j]+=coff2[j];
            }

        }








    }

}
