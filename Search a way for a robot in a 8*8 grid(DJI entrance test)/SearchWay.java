
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Shen Hu
 *
 */
public class SearchWay {
	
	private List<Coordinate> rst;
	private List<Coordinate> grid;
	private Coordinate startPoint;
	private int count;
	private double countTime;
	
	public SearchWay(int startX, int startY, int[][] grids){
		this.grid = new ArrayList<Coordinate>();
		this.rst = new ArrayList<Coordinate>();
		this.count = Integer.MAX_VALUE;
		this.countTime = Double.MAX_VALUE;
		this.startPoint = new Coordinate(startX, startY);
		
		int row = grids.length, col = grids[0].length;
		for (int i = 0 ; i < row; i++){
			for (int j = 0; j < col; j++){
				if (grids[i][j]!= 0){
					this.grid.add(new Coordinate(i,j,grids[i][j]));
				}
			}
		}
	}
	
	public void search1(){	
		List<Integer> visited = new ArrayList<Integer>();
		this.grid.add(0,this.startPoint);
		visited.add(0);
		List<Coordinate> result = new ArrayList<Coordinate>();
		result.add(startPoint);
		helper1(visited, 0, result);
	}
	
	public void search2(){	
		List<Integer> visited = new ArrayList<Integer>();
		this.grid.add(0,this.startPoint);
		visited.add(0);
		List<Coordinate> result = new ArrayList<Coordinate>();
		result.add(startPoint);
		helper2(visited, 0, 1, result);
	}
	
	public void printStepCount(){
		System.out.println("Step count is:" + this.count);
	}
	
	public void printTimeCount(){
		System.out.println("Time count is:" + this.countTime + " s");
	}
	
	public void printResult(){
		System.out.print("("+rst.get(0).x+"," + rst.get(0).y + ")");
        for (int i= 1; i< rst.size(); i++){
        	System.out.print("->("+rst.get(i).x+"," + rst.get(i).y + ")");
        }
        System.out.print("\n");
	}
	
	private void helper1(List<Integer> visited, int steps, List<Coordinate> result){
		if (visited.size() == this.grid.size()){
			count = Math.min(count, steps);
			this.rst = new ArrayList<Coordinate>(result);
			return;
		}
		int curGridint = visited.get(visited.size() -1);
		for (int i = 1; i<  this.grid.size(); i++){
			if (!visited.contains(i)){
				visited.add(i);
				Coordinate oldGrid = this.grid.get(curGridint);
				Coordinate newGrid = this.grid.get(i);
				result.add(newGrid);
				int step = Math.abs(oldGrid.x - newGrid.x) + Math.abs(oldGrid.y - newGrid.y) ;
				
                if(step + steps < this.count){
                    helper1(visited, steps + step, result);
				}
                result.remove(result.size() -1);
				visited.remove(visited.size() -1);
			}
		}
	}
	
	private void helper2(List<Integer> visited, double time, int speed, List<Coordinate> result){
		if (visited.size() == this.grid.size()){
			countTime = Math.min(countTime, time);
			this.rst = new ArrayList<Coordinate>(result);
			return;
		}
		int curGridint = visited.get(visited.size() -1);
		for (int i = 1; i<  this.grid.size(); i++){
			if (!visited.contains(i)){
				visited.add(i);
				Coordinate oldGrid = this.grid.get(curGridint);
				Coordinate newGrid = this.grid.get(i);
				result.add(newGrid);
				double thistime = (double)(Math.abs(oldGrid.x - newGrid.x) + Math.abs(oldGrid.y - newGrid.y))/speed ;
				if(thistime + time < this.countTime){
					helper2(visited, thistime + time, newGrid.speed, result);
				}
				result.remove(result.size() -1);
				visited.remove(visited.size() -1);
			}
		}
	}
}
