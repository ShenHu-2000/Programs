package Mypackage;
import org.ujmp.jmatio.ImportMatrixMAT;
import org.ujmp.core.Matrix;
import java.io.File;
import java.io.IOException;
public class bpTest{
    public  static void main(String[] args)throws IOException{
        //相对路径的根目录是当前工程的目录（C:\Users\hfz\Desktop\test）。另外相对路径的起始处无“/”
        ImportMatrixMAT test=new ImportMatrixMAT();
        File file=new File("src/Mypackage/spectra_data.mat");
        Matrix testMatrix=test.fromFile(file);
        testMatrix.showGUI();
        System.out.println("ss");
    }


}
