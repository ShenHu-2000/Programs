package Mypackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/*
 * 红色路径：5，10，黄色路径：6，11，蓝色路径：7，12，绿色路径：8，13
 */

public class Pair {
	public int num;
	public int x;
	public int y;
	public static int[][] grid;
	public static boolean[][] decide;
	public Pair(int num, int x, int y) {
		this.num = num;
		this.x = x;
		this.y = y;
	}
				
	public int manhattandistance(Pair other){
		return Math.abs(x-other.x) + Math.abs(y-other.y);
	}
/*
用arr来renew grid，使得grid返回上一状态。
*/
	/*public static int[][] renewgrid(int[][] arr){
		
	}
	*/
	public static String toStringforarray(int[][] p){
		String s = "";
		for(int row =0;row<p.length;row++){
			for(int col=0;col<p[0].length;col++){
				if(p[row][col]<10)
				s += "( " + p[row][col] + " )";
				else
				s += "(" + p[row][col] + " )";
		}
		s += "\n";
	}
	return s;
	}

	public static Pair[][] createPairMap(int[][] arr){
		Pair[][] a=new Pair[arr.length][arr[0].length];
		for(int i=0;i<arr.length;i++)
			for(int j=0;j<arr[0].length;j++) 
				a[i][j]=new Pair(arr[i][j],i,j);
		return a;
	}
/*下面的方法专门让grid中已经提出来的零消失，即用-1标记。
*/
	public static void renewarray(Pair[][] arr){
		for(int row=0;row<arr.length;row++)
			for(int col=0;col<arr[0].length;col++)
				if(arr[row][col]!=null)
					grid[row][col]=-1;

	}

	/*
	t用于标记原图上所有点，此方法旨在创造一个pair的array，其大小和grid一样，但是只有未填充的块是有标记pair(0,x,y)的。走过的地方标记为9.输出的地图不包括0的边缘。
	*/
	/*
	public Pair[][] createBlock(int[][] arr)
	{
		Pair[][] t = createPairMap(arr);
		Pair[][] newmap = new Pair[arr.length][arr[0].length];
		newmap[x][y] = this; 
		Queue<Pair> q = new LinkedList<Pair>();
		q.offer(this);
		Pair temp;
		while(!q.isEmpty()) {
			temp = q.poll();
			t[temp.x][temp.y].num=9;
			boolean fir = temp.x-1>=0 && temp.x-1<t.length;
			boolean sec = temp.x+1>=0 && temp.x+1<t.length;
			boolean thir = temp.y+1>=0 && temp.y+1<t[0].length; 
			boolean four = temp.y-1>=0 && temp.y-1<t[0].length;
			if(fir) {
				if(t[temp.x-1][temp.y].num==0) {
					q.offer(t[temp.x-1][temp.y]);
					newmap[temp.x-1][temp.y]=new Pair(0,temp.x-1,temp.y);
				}
			}
			if(sec) {
				if(t[temp.x+1][temp.y].num==0) {
					q.offer(t[temp.x+1][temp.y]);
					newmap[temp.x+1][temp.y]=new Pair(0,temp.x+1,temp.y);
				}
			}
				
			if(thir) {
				if(t[temp.x][temp.y+1].num==0) {
					q.offer(t[temp.x][temp.y+1]);
					newmap[temp.x][temp.y+1]=new Pair(0,temp.x,temp.y+1);
				}
			}
			if(four) {
				if(t[temp.x][temp.y-1].num==0) {
					q.offer(t[temp.x][temp.y-1]);
					newmap[temp.x][temp.y-1]=new Pair(0,temp.x,temp.y-1);
				}
			}
		}

		return newmap;

	}


/*
填补完后，相应地改变arr。record用于记录走过的点。size是步数上限，step记录步数。
*/
/*
public static int sizeofBlock(Pair[][] block){
	int count = 0;
	for(int row=0;row<block.length;row++)
		for(int col=0;col<block[0].length;col++)
			if(block[row][col] !=null && block[row][col].num == 0)
				count += 1;
	return count;
}


/*当searchandfill一次后，一定要把block改回来。
*/
/*
	public static void fill(int size,Pair[][] block,int[][] arr){
		if(size%2==1){
			filloddBlock(0,size,new ArrayList<Pair>(),block,arr);
		}
		else{
		for(int i=0;i<block.length;i++)
			for(int j=0;j<block[0].length;j++){
				if(block[i][j]!=null && block[i][j].num==0){
					if(i+1<block.length && block[i+1][j]==null){
						if(j-1>=0 && block[i+1][j-1]==null && arr[i+1][j-1]==arr[i+1][j] && block[i][j-1]!=null && block[i][j-1].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i+1][j-1],block[i+1][j],arr);
						if(j+1<block[0].length && block[i+1][j+1]==null && arr[i+1][j+1]==arr[i+1][j] && block[i][j+1]!=null && block[i][j+1].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i+1][j+1],block[i+1][j],arr);
						if(j-1>=0 && block[i][j-1]==null && arr[i+1][j]==arr[i][j-1])
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j-1],block[i+1][j],arr);
						if(j+1<block[0].length && block[i][j+1]==null && arr[i+1][j]==arr[i][j+1])
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j+1],block[i+1][j],arr);
						}
					
					if(i-1>=0 && block[i-1][j]==null){
						if(j-1>=0 && block[i-1][j-1]==null && arr[i-1][j]==arr[i-1][j-1] && block[i][j-1]!=null && block[i][j-1].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i-1][j-1],block[i-1][j],arr);
						if(j+1<block[0].length && block[i-1][j+1]==null && arr[i-1][j]==arr[i-1][j+1] && block[i][j+1]!=null && block[i][j+1].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i-1][j+1],block[i-1][j],arr);
						if(j-1>=0 && block[i][j-1]==null && arr[i-1][j]==arr[i][j-1])
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j-1],block[i-1][j],arr);
						if(j+1<block[0].length && block[i][j+1]==null && arr[i-1][j]==arr[i][j+1])
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j+1],block[i-1][j],arr);
					}	
			
					if(j+1<block[0].length && block[i][j+1]==null){
						if(i-1>=0 && block[i-1][j+1]==null && arr[i][j+1]== arr[i-1][j+1] && block[i-1][j]!=null && block[i-1][j].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j+1],block[i-1][j+1],arr);
						if(i+1<block.length && block[i+1][j+1]==null && arr[i][j+1]==arr[i+1][j+1] && block[i+1][j]!=null && block[i+1][j].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j+1],block[i+1][j+1],arr);
					}

					if(j-1>=0 && block[i][j-1]==null){
						if(i-1>=0 && block[i-1][j-1]==null && arr[i][j-1]==arr[i-1][j-1] && block[i-1][j]!=null && block[i-1][j].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j-1],block[i-1][j-1],arr);
						if(i+1<block.length && block[i+1][j-1]==null && arr[i][j-1]==arr[i+1][j-1] && block[i+1][j]!=null && block[i+1][j].num==0)
							searchandfill(0,size,new ArrayList<Pair>(),block,block[i][j-1],block[i+1][j-1],arr);
					}
					
					
					
					
						}

				}
		}
	} 
/*
找到路径后直接改变arr，左：-1，右：-3，上：-2，下：-4。Pair[][]一定要改回来。p是block的图，st起点（在图外），end终点（在图外）
思路一：先广度搜索，然后再在路径上不断修改。
思路二：用一个arraylist装from和to的点。每走一次，在所有的mark中删去to中的这个点，不满足要求的路径将修改最后一个决定，即修改rec。

*/
/*
public static void searchandfill(int step,int size,ArrayList<Pair> rec,Pair[][] p,Pair st,Pair end,int[][] arr){
	ArrayList<Mark> marks = new ArrayList<>();
	boolean notfound = true;
	Queue<Pair> q = new LinkedList<Pair>();
	for(int i=0;i<p.length;i++)
		for(int j=0;j<p[0].length;j++){
			if(p[i][j]!=null){
				Mark mark = new Mark();
				mark.from = p[i][j];
				if(i-1>=0 && p[i-1][j]!=null){
					mark.to.add(p[i-1][j]);
				}

				if(i+1<p.length && p[i+1][j]!=null){
					mark.to.add(p[i+1][j]);
				}

				if(j-1>=0 && p[i][j-1]!=null){
					mark.to.add(p[i][j-1]);
				}

				if(j+1<p[0].length && p[i][j+1]!=null){
					mark.to.add(p[i][j+1]);
				}
				marks.add(mark);
			}
		}
	
		q.offer(st);
		while(notfound){
			;
		}






}
*/
public boolean Searchequals(Pair p) {
	return x==p.x && y==p.y;
}

public boolean isIn(Queue<Pair> q) {
	for(Pair p:q)
		if(p.x==x && p.y==y)
			return true;
	return false;
}

	public boolean equals(Pair p) {
		return p.x==x && p.y==y && p.num==num;
	}
	
	public String toString() {
		if (this==null)
			return "null";
		else
			return num+", "+x+", "+y;
	}
	/*
	 * num是5,6,7,8为原始的路，9,10,11,12为新路，1，2，3，4为铺好的路（顺序为红黄蓝绿）
	 */
	
	/*
	 * 找到没有障碍物情况下的最短路线。从一个城堡到另一个同色城堡的路径保存在arraylist中，不改变grid。this起点，a终点。
	 */
	/*
	public ArrayList<Pair> searchRedWay(Pair a){
		ArrayList<Pair> l = new ArrayList<>();
		if(a.x>x && a.y>y) {
			for(int i=x;i<=a.x;i++) 
				l.add(new Pair(5,i,y));
			for(int j=y+1;j<=a.y;j++) 
				l.add(new Pair(5,a.x,j));
			return l;
		}
		
		else if(a.x==x && a.y>y) {
			for(int i=y;i<=a.y;i++) {
				l.add(new Pair(5,x,i));
			}
			return l;
		}
		
		else if(a.x<x && a.y>y) {
			for(int i=y;i<=a.y;i++) {
				l.add(new Pair(5,x,i));
			}
			for(int j=x-1;j>=a.x;j--) {
				l.add(new Pair(5,j,a.y));
			}
			return l;	
		}
		
		else if(a.x<x && a.y==y) {
			for(int i=x;i>=a.x;i--) {
				l.add(new Pair(5,i,y));
			}
			return l;
		}
		
		else if(a.x<x && a.y<y) {
			for(int i=x;i>=a.x;i--) {
				l.add(new Pair(5,i,y));
			}
			for(int j=y-1;j>=a.y;j--) {
				l.add(new Pair(5,a.x,j));
			}
			return l;
		}
		
		else if(a.x==x && a.y<y) {
			for(int i=y;i>=a.y;i--) {
				l.add(new Pair(5,x,i));
			}
			return l;
		}
		
		else if(a.x>x && a.y<y) {
			for(int i=y;i>=a.y;i--) {
				l.add(new Pair(5,x,i));
			}
			for(int j=x+1;j<=a.x;j++) {
				l.add(new Pair(5,j,a.y));
			}
			return l;
		}
	
		else {
			for(int i=x;i<=a.x;i++) {
				l.add(new Pair(5,i,y));
			}
			return l;
		}

	}
	
	/*
	 * 探测障碍物，并且用广搜绕过它们，返回最后的路径。m被用于记录创建新路的起点，j是记录l中障碍物的下一个pair，grid不用变。
	 * 搜索终点为空的状态。插入searchroute，使得新的搜索对象绕开了一些障碍物。grid的状态在每构建一条完整的新路都会更新(renew)，在下面的方法中已经中使用。l储存所有路径上的点。有一个小例外就是寻找路径的时候如果找到了路径上的点是合法的。
	 */
	
	/*
	
	public static ArrayList<Pair> SearchRedWayWithObstacle2(ArrayList<Pair> l){
		int j=0;
		for(int i=1;i<l.size()-1;i++) {
			if(grid[l.get(i).x][l.get(i).y]!=0) {
				j = i+1;
				break;
			}
		}
		if(j!=0) {
		for(int n=j;n<l.size()-1;n++) {
			if(grid[l.get(n).x][l.get(n).y]==0) {
				for(int c=n+1;c<l.size()-1;c++) {
					if(grid[l.get(c).x][l.get(c).y]==0) {
						Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
						Collection<Pair> searchroute = m.wideRedSearch(new ArrayList<Pair>(),new Pair(0,l.get(c).x,l.get(c).y),Pair.createPairMap(grid),l);
						if(searchroute==null) {
							Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
							int size = l.size();
							searchroute = st.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(size-1).x,l.get(size-1).y), Pair.createPairMap(grid),l);
							if(searchroute == null) {
								Pair lastsearch = new Pair(l.get(0).num,l.get(0).x,l.get(0).y);
								return lastsearch.wideRedSearch(new ArrayList<Pair>(),new Pair(0,l.get(l.size()-1).x,l.get(l.size()-1).y),Pair.createPairMap(grid),l);					
							}
							int count=0;
							while(count<l.size()-j+2) {
								l.remove(j-2);
								count++;
							}
							l.addAll(j-2, searchroute);
							return SearchRedWayWithObstacle(l);
							
						}
						int count=0;
						while(count<c-j+3) {
							l.remove(j-2);
							count++;
						}
						l.addAll(j-2,searchroute);
						return SearchRedWayWithObstacle(l);
					}
				}
			}
		}
				
				
		Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
		int c = l.size();
		Collection<Pair> searchroute = s.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
		if(searchroute==null) {
			Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
			searchroute = st.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid),l);
			}
		int count=0;
		while(count<c-j+2) {
			l.remove(j-2);
			count++;
		}
		l.addAll(j-2, searchroute);
		return SearchRedWayWithObstacle(l);
		}
			
			Pair.avoidRepeatedPath(l);
			Pair.Redrenew(l,grid);
			return l;
	}
	
	
	
	
	public static ArrayList<Pair> SearchRedWayWithObstacle(ArrayList<Pair> l){
		int j=0;
		for(int i=1;i<l.size()-1;i++) {
			if(grid[l.get(i).x][l.get(i).y]!=0) {
				j = i+1;
				break;
			}
		}
		if(j!=0) {
		for(int n=j;n<l.size()-1;n++) {
			if(grid[l.get(n).x][l.get(n).y]==0) {
				Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
				Collection<Pair> searchroute = m.wideRedSearch(new ArrayList<Pair>(),new Pair(0,l.get(n).x,l.get(n).y),Pair.createPairMap(grid),l);
				if(searchroute==null) {
					Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
					searchroute = st.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(n).x,l.get(n).y), Pair.createPairMap(grid), l);
					if(searchroute == null)
						return SearchRedWayWithObstacle2(l);
				int count=0;
				while(count<n-j+3) {
					l.remove(j-2);
					count++;
				}
				l.addAll(j-3,searchroute);
				return SearchRedWayWithObstacle(l);
				}
				int count=0;
				while(count<n-j+3) {
					l.remove(j-2);
					count++;
				}
				l.addAll(j-2,searchroute);
				return SearchRedWayWithObstacle(l);
			}
		}		
		Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
		int c = l.size();
		Collection<Pair> searchroute = s.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
		if(searchroute==null) {
			Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
			searchroute = st.wideRedSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid), l);
			int count=0;
			while(count<c-j+3) {
				l.remove(j-3);
				count++;
			}
			l.addAll(j-3, searchroute);
			return SearchRedWayWithObstacle(l);
			}
		int count=0;
		while(count<c-j+2) {
			l.remove(j-2);
			count++;
		}
		l.addAll(j-2, searchroute);
		return SearchRedWayWithObstacle(l);
		}
			Pair.avoidRepeatedPath(l);
			Pair.Redrenew(l,grid);
			return l;
	}
	/*
		避免新的道路和老的道路有很多交集。
	*/
	/*
	public static void avoidRepeatedPath(ArrayList<Pair> p) {
		int recordAll = p.size();
		for(int i=0;i<p.size();i++) {
			Pair p1 = p.get(i);
			int record = p.size();
			for(int j=i+1;j<p.size();j++) {
				if(p1.Searchequals(p.get(j))) {
					int count=i+1;
					int count2=j;
					while(count2-count>=0) {
						p.remove(count);
						count2--;
					}
					break;
				}
			}
			if(record!=p.size())
				break;
		}
		if(recordAll!=p.size())
			Pair.avoidRepeatedPath(p);
		
	}
	
	
	/*
	 * 更新地图上的红色路径
	 */
	/*
	public static void Redrenew(ArrayList<Pair> l,int[][] arr) {
		for(int i=0;i<l.size();i++) {
			arr[l.get(i).x][l.get(i).y]=5;
		}
	}
	
	/*
 * 返回最后一层的arraylist，Pair[][]是当前grid的图，用于查看grid的情况，此时不用改变grid的值，只用在当前的Pair[][]中寻找。如果找不到就return null。arraylist p用于记录路劲结果
 * 这里要使用unpaved的数字。traceback是终点。像水一样扩散寻找路径。Debug结果：无误。this为起点。
 */
/*
	
	public ArrayList<Pair> wideRedSearch(ArrayList<Pair> p,Pair a,Pair[][] t,ArrayList<Pair> list) {
		Queue<Pair> q = new LinkedList<Pair>();
		q.offer(t[x][y]);
		Pair temp;
		Pair traceback = null;
		ArrayList<Mark> trace = new ArrayList<Mark>();
		ArrayList<Pair> readroute = new ArrayList<Pair>();
		while(!q.isEmpty()) {
			temp = q.poll();
			t[temp.x][temp.y].num=9;
			boolean fir = temp.x-1>=0 && temp.x-1<t.length;
			boolean sec = temp.x+1>=0 && temp.x+1<t.length;
			boolean thir = temp.y+1>=0 && temp.y+1<t[0].length; 
			boolean four = temp.y-1>=0 && temp.y-1<t[0].length;
			if(((fir) && ((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a)))) || ((sec) && ((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a)))) || ((thir) && ((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a)))) || ((four) && ((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a)))) ) 
					trace.add(new Mark(t[temp.x][temp.y],new ArrayList<Pair>()));
							
			if(fir) {
				if((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a))) {
					q.offer(t[temp.x-1][temp.y]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x-1][temp.y]);
					trace.set(trace.size()-1, m);
				}
			}
			if(sec) {
				if((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a))) {
					q.offer(t[temp.x+1][temp.y]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x+1][temp.y]);
					trace.set(trace.size()-1, m);
				}
			}
				
			if(thir) {
				if((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a))) {
					q.offer(t[temp.x][temp.y+1]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x][temp.y+1]);
					trace.set(trace.size()-1, m);
				}
			}
			if(four) {
				if((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a))) {
					q.offer(t[temp.x][temp.y-1]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x][temp.y-1]);
					trace.set(trace.size()-1, m);
				}
			}
			if(t[a.x][a.y].isIn(q)) {
				List<Pair> l = (LinkedList<Pair>)q;
				for(int i=l.size()-1;i>=l.size()-4;i--) {
					if(l.get(i).x==a.x && l.get(i).y==a.y) {
						traceback=l.get(i);
						t[a.x][a.y].num=9;
						break;
					}
				}
				while(!traceback.Searchequals(this)) {
					
				for(int i=trace.size()-1;i>=0;i--) {
					if(trace.get(i).to.contains(traceback)) {
						readroute.add(traceback);
						traceback=trace.get(i).from;
						break;
					}
				}
			}
				this.num=9;
				readroute.add(this);
				ArrayList<Pair> answer = new ArrayList<Pair>();
				for(int i=readroute.size()-1;i>=0;i--)
					answer.add(readroute.get(i));
				return answer;
			}
	}
		return null;
	}
	
	
	/*输入一个二维array，让原起点和原终点之间的曼哈顿距离成为此数组的element。this为原来的起点，计算路径的顺序为原来的终点到起点。
	 * 
	 */
	/*
	
	public int[][] manhattanarray(Pair[][] t) {
		int[][] arr=new int[t.length][t[0].length];
		for(int i=0;i<arr.length;i++)
			for(int j=0;j<arr[0].length;j++) {
				if(t[i][j].num==9)
					arr[i][j]=Math.abs(i-x)+Math.abs(j-y);
				else
					arr[i][j]=-1;
			}
		return arr;
		
	}
	
	*/
	
	/*
	 * 开始回溯路径，在曼哈顿图已经建立的情况下。走过的点用-1在int[][]中标记。p为终点，a为记录的arraylist，n为有待铺路径的地图，mha为点到1,2,3,4城堡，5,6,7,8路径，9,10,11,12待铺路径。此方法输出的是正确的路径。
	 * mha记录曼哈顿距离，mha[][]==-3是标记走过的点，n[p.x][p.y]==5是标记铺好的路。
	 */
	
/*
	public ArrayList<Pair> RedSearchBack(ArrayList<Pair> a,Pair p,Pair[][] n,int[][] mha) {
		if(p.x==x && p.y==y) {
			p.num=5;
			a.add(p);
			ArrayList<Pair> correctway=new ArrayList<>();
			for(int i=a.size()-1;i>=0;i--)
				correctway.add(a.get(i));
			return correctway;
		}
		
		System.out.println(p);
		if(p.x+1==x && p.y==y) {
			n[p.x][p.y].num=5;
			mha[p.x][p.y]=-3;
			a.add(new Pair(5,p.x,p.y));
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.x-1==x && p.y==y) {
			n[p.x][p.y].num=5;
			mha[p.x][p.y]=-3;
			a.add(new Pair(5,p.x,p.y));
			return RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.x==x && p.y+1==y) {
			n[p.x][p.y].num=5;
			mha[p.x][p.y]=-3;
			a.add(new Pair(5,p.x,p.y));
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.x==x && p.y-1==y) {
			n[p.x][p.y].num=5;
			mha[p.x][p.y]=-3;
			a.add(new Pair(5,p.x,p.y));
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		ArrayList<Integer> count=new ArrayList<>();
		if(p.x+1<mha.length)
		count.add(mha[p.x+1][p.y]);
		if(p.x-1>=0)
		count.add(mha[p.x-1][p.y]);
		if(p.y+1<mha[0].length)
		count.add(mha[p.x][p.y+1]);
		if(p.y-1>=0)
		count.add(mha[p.x][p.y-1]);
		for(int i=0;i<count.size();i++) {
			if(count.get(i)<0) {
				count.remove(i);
				i--;
			}
		}
		int min = count.get(0);
		for(int i=0;i<count.size();i++) {
			if(count.get(i)<min)
				min=count.get(i);
		count.remove(Integer.valueOf(min));
		if(count.size()>0 && count.contains(min))
			count.remove(Integer.valueOf(min));
		}
		
		int secondmin = -2;
		if(count.size()>0) {
			secondmin=count.get(0);
			for(int i=0;i<count.size();i++)
				if(count.get(i)<secondmin)
					secondmin=count.get(i);
			count.remove(Integer.valueOf(secondmin));
			if(count.size()>0 && count.contains(secondmin))
				count.remove(Integer.valueOf(secondmin));
		}
		
		int thirdmin = -2;
		if(count.size()>0) {
			thirdmin=count.get(0);
			for(int i=0;i<count.size();i++)
				if(count.get(i)<thirdmin)
					thirdmin=count.get(i);
			count.remove(Integer.valueOf(thirdmin));
		}
		
		
		if(p.x+1<n.length && p.x-1>=0 && n[p.x+1][p.y].num==9 && n[p.x-1][p.y].num==9 && min==mha[p.x+1][p.y] && min==mha[p.x-1][p.y] ) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.x+1<n.length && p.y+1<n[0].length && n[p.x+1][p.y].num==9 && n[p.x][p.y+1].num==9 && min==mha[p.x+1][p.y] && min==mha[p.x][p.y+1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.x+1<n.length && p.y-1>=0 && n[p.x+1][p.y].num==9 && n[p.x][p.y-1].num==9 && min==mha[p.x+1][p.y] && min==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.x-1>=0 && p.y+1<n[0].length && n[p.x-1][p.y].num==9 && n[p.x][p.y+1].num==9 && min==mha[p.x-1][p.y] && min==mha[p.x][p.y+1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x-1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.x-1>=0 && p.y-1>=0 && n[p.x-1][p.y].num==9 && n[p.x][p.y-1].num==9 && min==mha[p.x-1][p.y] && min==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x-1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.y-1>=0 && p.y+1<n[0].length && n[p.x][p.y+1].num==9 && n[p.x][p.y-1].num==9 && min==mha[p.x][p.y+1] && min==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x][p.y+1],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.x+1<n.length && min==mha[p.x+1][p.y] && n[p.x+1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.x-1>=0 && min==mha[p.x-1][p.y] && n[p.x-1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.y+1<n[0].length && min==mha[p.x][p.y+1] && n[p.x][p.y+1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.y-1>=0 && min==mha[p.x][p.y-1] && n[p.x][p.y-1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		
		
		
		
		if(p.x+1<n.length && p.x-1>=0 && n[p.x+1][p.y].num==9 && n[p.x-1][p.y].num==9 && secondmin==mha[p.x+1][p.y] && secondmin==mha[p.x-1][p.y] ) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.x+1<n.length && p.y+1<n[0].length && n[p.x+1][p.y].num==9 && n[p.x][p.y+1].num==9 && secondmin==mha[p.x+1][p.y] && secondmin==mha[p.x][p.y+1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.x+1<n.length && p.y-1>=0 && n[p.x+1][p.y].num==9 && n[p.x][p.y-1].num==9 && secondmin==mha[p.x+1][p.y] && secondmin==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x+1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.x-1>=0 && p.y+1<n[0].length && n[p.x-1][p.y].num==9 && n[p.x][p.y+1].num==9 && secondmin==mha[p.x-1][p.y] && secondmin==mha[p.x][p.y+1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x-1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.x-1>=0 && p.y-1>=0 && n[p.x-1][p.y].num==9 && n[p.x][p.y-1].num==9 && secondmin==mha[p.x-1][p.y] && secondmin==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x-1][p.y],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.y-1>=0 && p.y+1<n[0].length && n[p.x][p.y+1].num==9 && n[p.x][p.y-1].num==9 && secondmin==mha[p.x][p.y+1] && secondmin==mha[p.x][p.y-1]) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return ((int)(Math.random()*2)==0)? RedSearchBack(a,n[p.x][p.y+1],n,mha):RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		if(p.x+1<n.length && secondmin==mha[p.x+1][p.y] && n[p.x+1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.x-1>=0 && secondmin==mha[p.x-1][p.y] && n[p.x-1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.y+1<n[0].length && secondmin==mha[p.x][p.y+1] && n[p.x][p.y+1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.y-1>=0 && secondmin==mha[p.x][p.y-1] && n[p.x][p.y-1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		
		
		
		if(thirdmin!=-2) {
		if(p.x+1<n.length && thirdmin==mha[p.x+1][p.y] && n[p.x+1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.x-1>=0 && thirdmin==mha[p.x-1][p.y] && n[p.x-1][p.y].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x-1][p.y],n,mha);
		}
		
		if(p.y+1<n[0].length && thirdmin==mha[p.x][p.y+1] && n[p.x][p.y+1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.y-1>=0 && thirdmin==mha[p.x][p.y-1] && n[p.x][p.y-1].num==9) {
			n[p.x][p.y].num=5;
			a.add(new Pair(5,p.x,p.y));
			mha[p.x][p.y]=-3;
			return RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		}

		
		
		
		
		
		if(p.x+1<n.length && n[p.x+1][p.y].num==5) {
			mha[p.x][p.y]=-3;
			n[p.x][p.y].num=0;
			a.remove(a.size()-1);
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.x-1>=0 && n[p.x-1][p.y].num==5) {
			mha[p.x][p.y]=-3;
			a.remove(a.size()-1);
			n[p.x][p.y].num=0;
			return RedSearchBack(a,n[p.x+1][p.y],n,mha);
		}
		
		if(p.y+1<n[0].length && n[p.x][p.y+1].num==5) {
			mha[p.x][p.y]=-3;
			a.remove(a.size()-1);
			n[p.x][p.y].num=0;
			return RedSearchBack(a,n[p.x][p.y+1],n,mha);
		}
		
		if(p.y-1>=0 && n[p.x][p.y-1].num==5) {
			mha[p.x][p.y]=-3;
			a.remove(a.size()-1);
			n[p.x][p.y].num=0;
			return RedSearchBack(a,n[p.x][p.y-1],n,mha);
		}
		
		
	
		return null;
	}
	*/
	
	
	
	
	




/*





public ArrayList<Pair> searchYellowWay(Pair a){
	ArrayList<Pair> l = new ArrayList<>();
	if(a.x>x && a.y>y) {
		for(int i=x;i<=a.x;i++) 
			l.add(new Pair(6,i,y));
		for(int j=y+1;j<=a.y;j++) 
			l.add(new Pair(6,a.x,j));
		return l;
	}
	
	else if(a.x==x && a.y>y) {
		for(int i=y;i<=a.y;i++) {
			l.add(new Pair(6,x,i));
		}
		return l;
	}
	
	else if(a.x<x && a.y>y) {
		for(int i=y;i<=a.y;i++) {
			l.add(new Pair(6,x,i));
		}
		for(int j=x-1;j>=a.x;j--) {
			l.add(new Pair(6,j,a.y));
		}
		return l;	
	}
	
	else if(a.x<x && a.y==y) {
		for(int i=x;i>=a.x;i--) {
			l.add(new Pair(6,i,y));
		}
		return l;
	}
	
	else if(a.x<x && a.y<y) {
		for(int i=x;i>=a.x;i--) {
			l.add(new Pair(6,i,y));
		}
		for(int j=y-1;j>=a.y;j--) {
			l.add(new Pair(6,a.x,j));
		}
		return l;
	}
	
	else if(a.x==x && a.y<y) {
		for(int i=y;i>=a.y;i--) {
			l.add(new Pair(6,x,i));
		}
		return l;
	}
	
	else if(a.x>x && a.y<y) {
		for(int i=y;i>=a.y;i--) {
			l.add(new Pair(6,x,i));
		}
		for(int j=x+1;j<=a.x;j++) {
			l.add(new Pair(6,j,a.y));
		}
		return l;
	}

	else {
		for(int i=x;i<=a.x;i++) {
			l.add(new Pair(6,i,y));
		}
		return l;
	}

}
	
	

public static ArrayList<Pair> SearchYellowWayWithObstacle2(ArrayList<Pair> l){
	int j=0;
	for(int i=1;i<l.size()-1;i++) {
		if(grid[l.get(i).x][l.get(i).y]!=0) {
			j = i+1;
			break;
		}
	}
	if(j!=0) {
	for(int n=j;n<l.size()-1;n++) {
		if(grid[l.get(n).x][l.get(n).y]==0) {
			for(int c=n+1;c<l.size()-1;c++) {
				if(grid[l.get(c).x][l.get(c).y]==0) {
					Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
					Collection<Pair> searchroute = m.wideYellowSearch(new ArrayList<Pair>(),new Pair(0,l.get(c).x,l.get(c).y),Pair.createPairMap(grid),l);
					if(searchroute==null) {
						Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
						int size = l.size();
						searchroute = st.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(size-1).x,l.get(size-1).y), Pair.createPairMap(grid),l);
						if(searchroute == null) {
							Pair lastsearch = new Pair(l.get(0).num,l.get(0).x,l.get(0).y);
							return lastsearch.wideYellowSearch(new ArrayList<Pair>(),new Pair(0,l.get(l.size()-1).x,l.get(l.size()-1).y),Pair.createPairMap(grid),l);					
						}
						int count=0;
						while(count<size-j+2) {
							l.remove(j-2);
							count++;
						}
						l.addAll(j-2, searchroute);
						return SearchYellowWayWithObstacle(l);
						
					}
					int count=0;
					while(count<c-j+3) {
						l.remove(j-2);
						count++;
					}
					l.addAll(j-2,searchroute);
					return SearchYellowWayWithObstacle(l);
				}
			}
		}
	}
			
			
	Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
	int c = l.size();
	Collection<Pair> searchroute = s.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
	if(searchroute==null) {
		Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
		searchroute = st.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid),l);
		}
	int count=0;
	while(count<c-j+2) {
		l.remove(j-2);
		count++;
	}
	l.addAll(j-2, searchroute);
	return SearchYellowWayWithObstacle(l);
	}
		
		Pair.avoidRepeatedPath(l);
		Pair.Yellowrenew(l,grid);
		return l;
}




public static ArrayList<Pair> SearchYellowWayWithObstacle(ArrayList<Pair> l){
	int j=0;
	for(int i=1;i<l.size()-1;i++) {
		if(grid[l.get(i).x][l.get(i).y]!=0) {
			j = i+1;
			break;
		}
	}
	if(j!=0) {
	for(int n=j;n<l.size()-1;n++) {
		if(grid[l.get(n).x][l.get(n).y]==0) {
			Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
			Collection<Pair> searchroute = m.wideYellowSearch(new ArrayList<Pair>(),new Pair(0,l.get(n).x,l.get(n).y),Pair.createPairMap(grid),l);
			if(searchroute==null) {
				Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
				searchroute = st.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(n).x,l.get(n).y), Pair.createPairMap(grid), l);
				if(searchroute == null)
					return SearchYellowWayWithObstacle2(l);
			int count=0;
			while(count<n-j+3) {
				l.remove(j-2);
				count++;
			}
			l.addAll(j-3,searchroute);
			return SearchYellowWayWithObstacle(l);
			}
			int count=0;
			while(count<n-j+3) {
				l.remove(j-2);
				count++;
			}
			l.addAll(j-2,searchroute);
			return SearchYellowWayWithObstacle(l);
		}
	}		
	Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
	int c = l.size();
	Collection<Pair> searchroute = s.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
	if(searchroute==null) {
		Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
		searchroute = st.wideYellowSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid), l);
		int count=0;
		while(count<c-j+3) {
			l.remove(j-3);
			count++;
		}
		l.addAll(j-3, searchroute);
		return SearchYellowWayWithObstacle(l);
		}
	int count=0;
	while(count<c-j+2) {
		l.remove(j-2);
		count++;
	}
	l.addAll(j-2, searchroute);
	return SearchYellowWayWithObstacle(l);
	}
		Pair.avoidRepeatedPath(l);
		Pair.Yellowrenew(l,grid);
		return l;
}

/*
	避免新的道路和老的道路有很多交集。
*/

/*

public static void Yellowrenew(ArrayList<Pair> l,int[][] arr) {
	for(int i=0;i<l.size();i++) {
		arr[l.get(i).x][l.get(i).y]=6;
	}
}

public ArrayList<Pair> wideYellowSearch(ArrayList<Pair> p,Pair a,Pair[][] t,ArrayList<Pair> list) {
		Queue<Pair> q = new LinkedList<Pair>();
		q.offer(t[x][y]);
		Pair temp;
		Pair traceback = null;
		ArrayList<Mark> trace = new ArrayList<Mark>();
		ArrayList<Pair> readroute = new ArrayList<Pair>();
		while(!q.isEmpty()) {
			temp = q.poll();
			t[temp.x][temp.y].num=10;
			boolean fir = temp.x-1>=0 && temp.x-1<t.length;
			boolean sec = temp.x+1>=0 && temp.x+1<t.length;
			boolean thir = temp.y+1>=0 && temp.y+1<t[0].length; 
			boolean four = temp.y-1>=0 && temp.y-1<t[0].length;
			if(((fir) && ((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a)))) || ((sec) && ((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a)))) || ((thir) && ((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a)))) || ((four) && ((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a)))) ) 
					trace.add(new Mark(t[temp.x][temp.y],new ArrayList<Pair>()));
							
			if(fir) {
				if((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a))) {
					q.offer(t[temp.x-1][temp.y]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x-1][temp.y]);
					trace.set(trace.size()-1, m);
				}
			}
			if(sec) {
				if((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a))) {
					q.offer(t[temp.x+1][temp.y]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x+1][temp.y]);
					trace.set(trace.size()-1, m);
				}
			}
				
			if(thir) {
				if((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a))) {
					q.offer(t[temp.x][temp.y+1]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x][temp.y+1]);
					trace.set(trace.size()-1, m);
				}
			}
			if(four) {
				if((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a))) {
					q.offer(t[temp.x][temp.y-1]);
					Mark m=trace.get(trace.size()-1);
					m.Markadd(t[temp.x][temp.y-1]);
					trace.set(trace.size()-1, m);
				}
			}
			
			
			if(t[a.x][a.y].isIn(q)) {
				List<Pair> l = (LinkedList<Pair>)q;
				for(int i=l.size()-1;i>=l.size()-4;i--) {
					if(l.get(i).x==a.x && l.get(i).y==a.y) {
						traceback=l.get(i);
						t[a.x][a.y].num=10;
						break;
					}
				}
				while(!traceback.Searchequals(this)) {
					
				for(int i=trace.size()-1;i>=0;i--) {
					if(trace.get(i).to.contains(traceback)) {
						readroute.add(traceback);
						traceback=trace.get(i).from;
						break;
					}
				}
			}
				this.num=10;
				readroute.add(this);
				ArrayList<Pair> answer = new ArrayList<Pair>();
				for(int i=readroute.size()-1;i>=0;i--)
					answer.add(readroute.get(i));
				return answer;
			}
	}
		return null;
	}
	
	
	
	







public ArrayList<Pair> searchBlueWay(Pair a){
		ArrayList<Pair> l = new ArrayList<>();
		if(a.x>x && a.y>y) {
			for(int i=x;i<=a.x;i++) 
				l.add(new Pair(7,i,y));
			for(int j=y+1;j<=a.y;j++) 
				l.add(new Pair(7,a.x,j));
			return l;
		}
		
		else if(a.x==x && a.y>y) {
			for(int i=y;i<=a.y;i++) {
				l.add(new Pair(7,x,i));
			}
			return l;
		}
		
		else if(a.x<x && a.y>y) {
			for(int i=y;i<=a.y;i++) {
				l.add(new Pair(7,x,i));
			}
			for(int j=x-1;j>=a.x;j--) {
				l.add(new Pair(7,j,a.y));
			}
			return l;	
		}
		
		else if(a.x<x && a.y==y) {
			for(int i=x;i>=a.x;i--) {
				l.add(new Pair(7,i,y));
			}
			return l;
		}
		
		else if(a.x<x && a.y<y) {
			for(int i=x;i>=a.x;i--) {
				l.add(new Pair(7,i,y));
			}
			for(int j=y-1;j>=a.y;j--) {
				l.add(new Pair(7,a.x,j));
			}
			return l;
		}
		
		else if(a.x==x && a.y<y) {
			for(int i=y;i>=a.y;i--) {
				l.add(new Pair(7,x,i));
			}
			return l;
		}
		
		else if(a.x>x && a.y<y) {
			for(int i=y;i>=a.y;i--) {
				l.add(new Pair(7,x,i));
			}
			for(int j=x+1;j<=a.x;j++) {
				l.add(new Pair(7,j,a.y));
			}
			return l;
		}

		else {
			for(int i=x;i<=a.x;i++) {
				l.add(new Pair(7,i,y));
			}
			return l;
		}

	}
	
	
	public static ArrayList<Pair> SearchBlueWayWithObstacle2(ArrayList<Pair> l){
		int j=0;
		for(int i=1;i<l.size()-1;i++) {
			if(grid[l.get(i).x][l.get(i).y]!=0) {
				j = i+1;
				break;
			}
		}
		if(j!=0) {
		for(int n=j;n<l.size()-1;n++) {
			if(grid[l.get(n).x][l.get(n).y]==0) {
				for(int c=n+1;c<l.size()-1;c++) {
					if(grid[l.get(c).x][l.get(c).y]==0) {
						Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
						Collection<Pair> searchroute = m.wideBlueSearch(new ArrayList<Pair>(),new Pair(0,l.get(c).x,l.get(c).y),Pair.createPairMap(grid),l);
						if(searchroute==null) {
							Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
							int size = l.size();
							searchroute = st.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(size-1).x,l.get(size-1).y), Pair.createPairMap(grid),l);
							if(searchroute == null) {
								Pair lastsearch = new Pair(l.get(0).num,l.get(0).x,l.get(0).y);
								return lastsearch.wideBlueSearch(new ArrayList<Pair>(),new Pair(0,l.get(l.size()-1).x,l.get(l.size()-1).y),Pair.createPairMap(grid),l);
							}
							int count=0;
							while(count<size-j+2) {
								l.remove(j-2);
								count++;
							}
							l.addAll(j-2, searchroute);
							return SearchBlueWayWithObstacle(l);
							
						}
						int count=0;
						while(count<c-j+3) {
							l.remove(j-2);
							count++;
						}
						l.addAll(j-2,searchroute);
						return SearchBlueWayWithObstacle(l);
					}
				}
			}
		}
				
				
		Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
		int c = l.size();
		Collection<Pair> searchroute = s.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
		if(searchroute==null) {
			Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
			searchroute = st.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid),l);
			}
		int count=0;
		while(count<c-j+2) {
			l.remove(j-2);
			count++;
		}
		l.addAll(j-2, searchroute);
		return SearchBlueWayWithObstacle(l);
		}
			
			Pair.avoidRepeatedPath(l);
			Pair.Bluerenew(l,grid);
			return l;
	}
	
	
	
	
	public static ArrayList<Pair> SearchBlueWayWithObstacle(ArrayList<Pair> l){
		int j=0;
		for(int i=1;i<l.size()-1;i++) {
			if(grid[l.get(i).x][l.get(i).y]!=0) {
				j = i+1;
				break;
			}
		}
		if(j!=0) {
		for(int n=j;n<l.size()-1;n++) {
			if(grid[l.get(n).x][l.get(n).y]==0) {
				Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
				Collection<Pair> searchroute = m.wideBlueSearch(new ArrayList<Pair>(),new Pair(0,l.get(n).x,l.get(n).y),Pair.createPairMap(grid),l);
				if(searchroute==null) {
					Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
					searchroute = st.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(n).x,l.get(n).y), Pair.createPairMap(grid), l);
					if(searchroute == null)
						return SearchBlueWayWithObstacle2(l);
				int count=0;
				while(count<n-j+3) {
					l.remove(j-2);
					count++;
				}
				l.addAll(j-3,searchroute);
				return SearchBlueWayWithObstacle(l);
				}
				int count=0;
				while(count<n-j+3) {
					l.remove(j-2);
					count++;
				}
				l.addAll(j-2,searchroute);
				return SearchBlueWayWithObstacle(l);
			}
		}		
		Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
		int c = l.size();
		Collection<Pair> searchroute = s.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
		if(searchroute==null) {
			Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
			searchroute = st.wideBlueSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid), l);
			int count=0;
			while(count<c-j+3) {
				l.remove(j-3);
				count++;
			}
			l.addAll(j-3, searchroute);
			return SearchBlueWayWithObstacle(l);
			}
		int count=0;
		while(count<c-j+2) {
			l.remove(j-2);
			count++;
		}
		l.addAll(j-2, searchroute);
		return SearchBlueWayWithObstacle(l);
		}
			Pair.avoidRepeatedPath(l);
			Pair.Bluerenew(l,grid);
			return l;
	}
	/*
		避免新的道路和老的道路有很多交集。
	*/
	
	/*
	public static void Bluerenew(ArrayList<Pair> l,int[][] arr) {
		for(int i=0;i<l.size();i++) {
			arr[l.get(i).x][l.get(i).y]=7;
		}
	}

public ArrayList<Pair> wideBlueSearch(ArrayList<Pair> p,Pair a,Pair[][] t,ArrayList<Pair> list) {
	Queue<Pair> q = new LinkedList<Pair>();
	q.offer(t[x][y]);
	Pair temp;
	Pair traceback = null;
	ArrayList<Mark> trace = new ArrayList<Mark>();
	ArrayList<Pair> readroute = new ArrayList<Pair>();
	while(!q.isEmpty()) {
		temp = q.poll();
		t[temp.x][temp.y].num=11;
		boolean fir = temp.x-1>=0 && temp.x-1<t.length;
		boolean sec = temp.x+1>=0 && temp.x+1<t.length;
		boolean thir = temp.y+1>=0 && temp.y+1<t[0].length; 
		boolean four = temp.y-1>=0 && temp.y-1<t[0].length;
		if(((fir) && ((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a)))) || ((sec) && ((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a)))) || ((thir) && ((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a)))) || ((four) && ((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a)))) ) 
				trace.add(new Mark(t[temp.x][temp.y],new ArrayList<Pair>()));
						
		if(fir) {
			if((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a))) {
				q.offer(t[temp.x-1][temp.y]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x-1][temp.y]);
				trace.set(trace.size()-1, m);
			}
		}
		if(sec) {
			if((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a))) {
				q.offer(t[temp.x+1][temp.y]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x+1][temp.y]);
				trace.set(trace.size()-1, m);
			}
		}
			
		if(thir) {
			if((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a))) {
				q.offer(t[temp.x][temp.y+1]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x][temp.y+1]);
				trace.set(trace.size()-1, m);
			}
		}
		if(four) {
			if((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a))) {
				q.offer(t[temp.x][temp.y-1]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x][temp.y-1]);
				trace.set(trace.size()-1, m);
			}
		}
		
		
		if(t[a.x][a.y].isIn(q)) {
			List<Pair> l = (LinkedList<Pair>)q;
			for(int i=l.size()-1;i>=l.size()-4;i--) {
				if(l.get(i).x==a.x && l.get(i).y==a.y) {
					traceback=l.get(i);
					t[a.x][a.y].num=11;
					break;
				}
			}
			while(!traceback.Searchequals(this)) {
				
			for(int i=trace.size()-1;i>=0;i--) {
				if(trace.get(i).to.contains(traceback)) {
					readroute.add(traceback);
					traceback=trace.get(i).from;
					break;
				}
			}
		}
			this.num=11;
			readroute.add(this);
			ArrayList<Pair> answer = new ArrayList<Pair>();
			for(int i=readroute.size()-1;i>=0;i--)
				answer.add(readroute.get(i));
			return answer;
		}
}
	return null;
}













public ArrayList<Pair> searchGreenWay(Pair a){
	ArrayList<Pair> l = new ArrayList<>();
	if(a.x>x && a.y>y) {
		for(int i=x;i<=a.x;i++) 
			l.add(new Pair(8,i,y));
		for(int j=y+1;j<=a.y;j++) 
			l.add(new Pair(8,a.x,j));
		return l;
	}
	
	else if(a.x==x && a.y>y) {
		for(int i=y;i<=a.y;i++) {
			l.add(new Pair(8,x,i));
		}
		return l;
	}
	
	else if(a.x<x && a.y>y) {
		for(int i=y;i<=a.y;i++) {
			l.add(new Pair(8,x,i));
		}
		for(int j=x-1;j>=a.x;j--) {
			l.add(new Pair(8,j,a.y));
		}
		return l;	
	}
	
	else if(a.x<x && a.y==y) {
		for(int i=x;i>=a.x;i--) {
			l.add(new Pair(8,i,y));
		}
		return l;
	}
	
	else if(a.x<x && a.y<y) {
		for(int i=x;i>=a.x;i--) {
			l.add(new Pair(8,i,y));
		}
		for(int j=y-1;j>=a.y;j--) {
			l.add(new Pair(8,a.x,j));
		}
		return l;
	}
	
	else if(a.x==x && a.y<y) {
		for(int i=y;i>=a.y;i--) {
			l.add(new Pair(8,x,i));
		}
		return l;
	}
	
	else if(a.x>x && a.y<y) {
		for(int i=y;i>=a.y;i--) {
			l.add(new Pair(8,x,i));
		}
		for(int j=x+1;j<=a.x;j++) {
			l.add(new Pair(8,j,a.y));
		}
		return l;
	}

	else {
		for(int i=x;i<=a.x;i++) {
			l.add(new Pair(8,i,y));
		}
		return l;
	}

}

public static ArrayList<Pair> SearchGreenWayWithObstacle2(ArrayList<Pair> l){
	int j=0;
	for(int i=1;i<l.size()-1;i++) {
		if(grid[l.get(i).x][l.get(i).y]!=0) {
			j = i+1;
			break;
		}
	}
	if(j!=0) {
	for(int n=j;n<l.size()-1;n++) {
		if(grid[l.get(n).x][l.get(n).y]==0) {
			for(int c=n+1;c<l.size()-1;c++) {
				if(grid[l.get(c).x][l.get(c).y]==0) {
					Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
					Collection<Pair> searchroute = m.wideGreenSearch(new ArrayList<Pair>(),new Pair(0,l.get(c).x,l.get(c).y),Pair.createPairMap(grid),l);
					if(searchroute==null) {
						Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
						int size = l.size();
						searchroute = st.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(size-1).x,l.get(size-1).y), Pair.createPairMap(grid),l);
						if(searchroute == null) {
							Pair lastsearch = new Pair(l.get(0).num,l.get(0).x,l.get(0).y);
								return lastsearch.wideGreenSearch(new ArrayList<Pair>(),new Pair(0,l.get(l.size()-1).x,l.get(l.size()-1).y),Pair.createPairMap(grid),l);
						}
						int count=0;
						while(count<size-j+2) {
							l.remove(j-2);
							count++;
						}
						l.addAll(j-2, searchroute);
						return SearchGreenWayWithObstacle(l);
						
					}
					int count=0;
					while(count<c-j+3) {
						l.remove(j-2);
						count++;
					}
					l.addAll(j-2,searchroute);
					return SearchGreenWayWithObstacle(l);
				}
			}
		}
	}
			
			
	Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
	int c = l.size();
	Collection<Pair> searchroute = s.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
	if(searchroute==null) {
		Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
		searchroute = st.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid),l);
		}
	int count=0;
	while(count<c-j+2) {
		l.remove(j-2);
		count++;
	}
	l.addAll(j-2, searchroute);
	return SearchGreenWayWithObstacle(l);
	}
		
		Pair.avoidRepeatedPath(l);
		Pair.Greenrenew(l,grid);
		return l;
}




public static ArrayList<Pair> SearchGreenWayWithObstacle(ArrayList<Pair> l){
	int j=0;
	for(int i=1;i<l.size()-1;i++) {
		if(grid[l.get(i).x][l.get(i).y]!=0) {
			j = i+1;
			break;
		}
	}
	if(j!=0) {
	for(int n=j;n<l.size()-1;n++) {
		if(grid[l.get(n).x][l.get(n).y]==0) {
			Pair m = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
			Collection<Pair> searchroute = m.wideGreenSearch(new ArrayList<Pair>(),new Pair(0,l.get(n).x,l.get(n).y),Pair.createPairMap(grid),l);
			if(searchroute==null) {
				Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
				searchroute = st.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(n).x,l.get(n).y), Pair.createPairMap(grid), l);
				if(searchroute == null){
					return SearchGreenWayWithObstacle2(l);
				}
			int count=0;
			while(count<n-j+3) {
				l.remove(j-2);
				count++;
			}
			l.addAll(j-3,searchroute);
			return SearchGreenWayWithObstacle(l);
			}
			int count=0;
			while(count<n-j+3) {
				l.remove(j-2);
				count++;
			}
			l.addAll(j-2,searchroute);
			return SearchGreenWayWithObstacle(l);
		}
	}		
	Pair s = new Pair(l.get(j-2).num,l.get(j-2).x,l.get(j-2).y);
	int c = l.size();
	Collection<Pair> searchroute = s.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y),Pair.createPairMap(grid),l);
	if(searchroute==null) {
		Pair st = new Pair(l.get(j-3).num,l.get(j-3).x,l.get(j-3).y);
		searchroute = st.wideGreenSearch(new ArrayList<Pair>(), new Pair(0,l.get(c-1).x,l.get(c-1).y), Pair.createPairMap(grid), l);
		int count=0;
		while(count<c-j+3) {
			l.remove(j-3);
			count++;
		}
		l.addAll(j-3, searchroute);
		return SearchGreenWayWithObstacle(l);
		}
	int count=0;
	while(count<c-j+2) {
		l.remove(j-2);
		count++;
	}
	l.addAll(j-2, searchroute);
	return SearchGreenWayWithObstacle(l);
	}
		Pair.avoidRepeatedPath(l);
		Pair.Greenrenew(l,grid);
		return l;
}

public static void Greenrenew(ArrayList<Pair> l,int[][] arr) {
	for(int i=0;i<l.size();i++) {
		arr[l.get(i).x][l.get(i).y]=8;
	}
}

public ArrayList<Pair> wideGreenSearch(ArrayList<Pair> p,Pair a,Pair[][] t,ArrayList<Pair> list) {
	Queue<Pair> q = new LinkedList<Pair>();
	q.offer(t[x][y]);
	Pair temp;
	Pair traceback = null;
	ArrayList<Mark> trace = new ArrayList<Mark>();
	ArrayList<Pair> readroute = new ArrayList<Pair>();
	while(!q.isEmpty()) {
		temp = q.poll();
		t[temp.x][temp.y].num=11;
		boolean fir = temp.x-1>=0 && temp.x-1<t.length;
		boolean sec = temp.x+1>=0 && temp.x+1<t.length;
		boolean thir = temp.y+1>=0 && temp.y+1<t[0].length; 
		boolean four = temp.y-1>=0 && temp.y-1<t[0].length;
		if(((fir) && ((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a)))) || ((sec) && ((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a)))) || ((thir) && ((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a)))) || ((four) && ((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a)))) ) 
				trace.add(new Mark(t[temp.x][temp.y],new ArrayList<Pair>()));
						
		if(fir) {
			if((t[temp.x-1][temp.y].num==0) || (t[temp.x-1][temp.y].Searchequals(a))) {
				q.offer(t[temp.x-1][temp.y]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x-1][temp.y]);
				trace.set(trace.size()-1, m);
			}
		}
		if(sec) {
			if((t[temp.x+1][temp.y].num==0) || (t[temp.x+1][temp.y].Searchequals(a))) {
				q.offer(t[temp.x+1][temp.y]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x+1][temp.y]);
				trace.set(trace.size()-1, m);
			}
		}
			
		if(thir) {
			if((t[temp.x][temp.y+1].num==0) || (t[temp.x][temp.y+1].Searchequals(a))) {
				q.offer(t[temp.x][temp.y+1]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x][temp.y+1]);
				trace.set(trace.size()-1, m);
			}
		}
		if(four) {
			if((t[temp.x][temp.y-1].num==0) || (t[temp.x][temp.y-1].Searchequals(a))) {
				q.offer(t[temp.x][temp.y-1]);
				Mark m=trace.get(trace.size()-1);
				m.Markadd(t[temp.x][temp.y-1]);
				trace.set(trace.size()-1, m);
			}
		}
		
		
		if(t[a.x][a.y].isIn(q)) {
			List<Pair> l = (LinkedList<Pair>)q;
			for(int i=l.size()-1;i>=l.size()-4;i--) {
				if(l.get(i).x==a.x && l.get(i).y==a.y) {
					traceback=l.get(i);
					t[a.x][a.y].num=12;
					break;
				}
			}
			while(!traceback.Searchequals(this)) {
				
			for(int i=trace.size()-1;i>=0;i--) {
				if(trace.get(i).to.contains(traceback)) {
					readroute.add(traceback);
					traceback=trace.get(i).from;
					break;
				}
			}
		}
			this.num=12;
			readroute.add(this);
			ArrayList<Pair> answer = new ArrayList<Pair>();
			for(int i=readroute.size()-1;i>=0;i--)
				answer.add(readroute.get(i));
			return answer;
		}
}
	return null;
}

*/


}
	
	
	
	
	