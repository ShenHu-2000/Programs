
import java.util.*;

/**
 * 
 * @author Shen Hu
 *
 */
public class RobotRoute {
	
    public static void main(String[] args)
    {
        System.out.println("Static Speed Robot Routing...");
        int[][] grid = { {1,0,2,0,0,0,0,2},
						 {0,0,0,0,3,0,1,0},
						 {0,1,0,0,0,0,0,0},
						 {0,0,0,1,0,0,2,0},
						 {0,0,2,0,0,0,0,0},
						 {0,0,0,0,1,0,3,0},
						 {0,0,3,0,0,0,0,0},
						 {0,0,0,0,2,0,0,1} };
        int startX = 7, startY = 0;
        
        SearchWay search1 = new SearchWay(startX, startY, grid);
        search1.search1();
        search1.printResult();
        search1.printStepCount();
        
        System.out.println("\nDynamic Speed Robot Routing...");       
        SearchWay search2 = new SearchWay(startX, startY, grid);
        search2.search2();
        search2.printResult();
        search2.printTimeCount();
       
     }

}
