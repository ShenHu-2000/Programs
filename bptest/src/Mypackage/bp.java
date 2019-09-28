package Mypackage;

import java.util.Scanner;


public class bp {
	public static final int IM = 4;
	public static final int RM = 5;
	public static final int OM = 1;
	public double learnRate = 0.5;
	public double W[][] = new double[IM][RM];
	public double Wout[][] = new double[RM][OM];
	public double X1[] = new double[IM];
	public double X2[] = new double[RM];
	public double X2Active[] = new double[RM];
	public double X3[] = new double[OM];
	public double X3Active[] = new double[OM];
	public double Er[] = new double[OM];
	public double E = 0.01;
	public double[] off2 = new double[RM];
	public double[] off3 = new double[OM];
	public double[] deltaout = new double[OM];
	public double[] deltahidden = new double[RM];
	
	/*public static void main(String[] args){
		bp network = new bp();
		network.train();
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter the parameter of input:");
		double parameter;
		/*
		while((parameter=s.nextDouble())!=-1){
			System.out.println(parameter + "*2+23=" + network.bpNetOut(parameter/100.0,parameter/100.0*2+23)[0]*100.0);
		*/
		/*while((parameter=s.nextDouble())!=-1){
			System.out.println(parameter + "has output of" + network.bpNetOut(parameter,parameter*parameter+2*parameter+3)[0]);
		}
	}*/

/*random可以乘上不同的值。
*/ 
	public bp() {
		for(int i=0;i<IM;i++)
			for(int j=0;j<RM;j++)
			{
				W[i][j]=Math.random();
				X2[j]=0;
			}
		
		for(int j=0;j<RM;j++)
			for(int k=0;k<OM;k++){
				Wout[j][k]=Math.random();
				X3[k]=0;
			}
		
		for(int j=0;j<RM;j++)
			off2[j]=Math.random();
		
		for(int k=0;k<OM;k++)
			off3[k]=Math.random();
		
	}
/*
* 两个数组，前一个表示10组NIR数据，后一个表示10组相应的octane数据。组的数量可以变化。
* */
	public void train(double[][] input,double[][] output){
		double[] output1=new double[output.length];
		/*int count=0;*/
		int count1=0;
		/*double Etotal = 0;*/
		for(int i=0;i<output1.length;i++){
			output1[i]=output[i][0];
		}

		while(true){
			for(int i=0;i<input.length;i++){
				double[] output2={output1[i]};
				bpNetForwardProcess(input[i],output2);
				bpNetReturnProcess(input[i],output2);
			}

			for(int i=0;i<input.length;i++){
				double[] output2={output1[i]};
				bpNetForwardProcess(input[i],output2);
				/*Etotal += E;*/
				if(E <= Math.pow(10,-4))
					count1++;
				/*count++;*/
			}
			if(count1==input.length) {
				/*System.out.println(count);*/
				break;
		}
			else
				count1=0;

			/*if(Etotal <= Math.pow(10,-3)){
				break;
			}

			else{
				Etotal=0;
			}*/
		}


	}
/*
* 打印所有的网络的出来的输出值。
*
* */
	public void bpNetOutput(double[][] nir,double[][] octane){
		for(int i=0;i<nir.length;i++) {
			double[] output={octane[i][0]};
			double[] arr = bpNetOut(nir[i], output);
			System.out.println("g" + i + " with predicted output" + reversemodification(arr[0],1,3) + "//E = " + E);
		}
	}

	public void bpNetOutput1(double[][] input, double[][] output){
		for(int i=0;i<input.length;i++){
			double[] out = {input[i][0]};
			double[] realout = bpNetOut(input[i],out);
			System.out.println("g" + i + " with predicted output" + realout[0] + "// E= " + E);
		}
		System.out.println("weights:");
		for(int i=0;i<RM;i++)
			System.out.println(W[0][i]);
		System.out.println("offset1s:");
		for(int i=0;i<RM;i++)
			System.out.println(off2[i]);
		System.out.println("outputweights:");
		for(int i=0;i<RM;i++)
			System.out.println(Wout[i][0]);
		System.out.println("offsets2:");
			System.out.println(off3[0]);
	}
	/*public void bpNetForwardProcess(double input,double output){
		double input1[] = {input};
		double output1[] = {output};
		bpNetForwardProcess(input1, output1);
	}*/
	public double reversemodification1(double x, double min,double max){
		return x*(max-min) + min;
	}
	public double reversemodification(double x,double min,double max){
		double ans = x*(max-min) + min;
		if(Math.abs(ans-1)<=0.15)
			return 1;
		else if(Math.abs(ans-2)<=0.15)
			return 2;
		return 3;
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
		
		for(int k=0;k<OM;k++){
			X3[k]=0;
			for(int j=0;j<RM;j++)
				X3[k]+=X2Active[j]*Wout[j][k];
		}

		for(int k=0;k<OM;k++)
		    X3Active[k]=1/(Math.exp(-(X3[k]+off3[k]))+1);

		for(int k=0;k<OM;k++)
			Er[k]=output[k]-X3Active[k];
		

		E=0;
		for(int k=0;k<OM;k++)
			E+=Er[k]*Er[k]/2;
	

	}

	
	
	public void bpNetReturnProcess(double[] input,double[] output){
        for(int k=0;k<OM;k++){
            deltaout[k]=(output[k]-X3Active[k])*X3Active[k]*(1-X3Active[k]);
        }

		for(int j=0;j<RM;j++){
		    double sum=0;
		    for(int k=0;k<OM;k++)
		        sum+=deltaout[k]*Wout[j][k];
		    deltahidden[j]=sum*X2Active[j]*(1-X2Active[j]);
        }

        for(int k=0;k<OM;k++){
            for(int j=0;j<RM;j++){
                Wout[j][k]+=learnRate*deltaout[k]*X2Active[j];
            }
            off3[k]+=learnRate*deltaout[k];
        }

        for(int j=0;j<RM;j++){
            for(int i=0;i<IM;i++){
                W[i][j]+=learnRate*deltahidden[j]*input[i];
            }
            off2[j]+=learnRate*deltahidden[j];
        }

	}

	/*public double[] bpNetOut(double input,double output){
		double[] input1={input};
		double[] output1={output};
		return bpNetOut(input1,output1);
	}*/
/*
* 此时output为真实值，每次计算输出，都会把E改变成这次输出和实际值的误差。
* */
	public double[] bpNetOut(double[] input,double[] output){
		for(int i=0;i<IM;i++)
			X1[i]=input[i];

		for(int j=0;j<RM;j++){
			X2[j]=0;
			for(int i=0;i<IM;i++)
				X2[j]+=X1[i]*W[i][j];
		}

		for(int j=0;j<RM;j++)
			X2Active[j]=1/(Math.exp(-(X2[j]+off2[j]))+1);

		double[] ans = new double[OM];
		for(int k=0;k<OM;k++){
			X3[k]=0;
			for(int j=0;j<RM;j++)
				X3[k]+=X2Active[j]*Wout[j][k];
			X3Active[k]=1/(Math.exp(-(X3[k]+off3[k]))+1);
			ans[k]=X3Active[k];
		}
		for(int k=0;k<OM;k++)
		Er[k]=output[k]-X3Active[k];


		E=0;
		for(int k=0;k<OM;k++)
			E+=Er[k]*Er[k]/2;

		return ans;

	}



}
