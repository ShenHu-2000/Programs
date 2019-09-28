package Mypackage;

public class Testmethod1{
    public static void main(String[] args){
		/*int[][] arr = new int[8][8];
		ArrayList<String> a = new ArrayList<String>();
		for(int i=0;i<args[0].length();i++){
			if(!args[0].substring(i,i+1).equals(",") && !args[0].substring(i,i+1).equals(";"))
				a.add(args[0].substring(i,i+1));
			}

		
		int count = 0;
		for(int row=0;row<arr.length;row++)
			for(int col=0;col<arr[0].length;col++){
				arr[row][col] = Integer.parseInt(a.get(count));
				count++;
			}
		
			Pair.toStringforarray(arr);
*/

		int[][] arr= {{4,1,0,2,0,0,0,0},
					  {0,0,0,0,0,2,0,0},
					  {3,0,1,0,0,3,0,0},
					  {0,0,4,0,0,0,0,0},
					  {0,0,0,0,1,0,0,0},
					  {0,0,0,0,0,0,0,0},
					  {0,2,0,1,4,0,0,4},
					  {2,3,0,0,0,0,0,3}};
		dfse d = new dfse(arr);
		d.dfs(0);
		System.out.println(Pair.toStringforarray(d.arr));
    }

    
}