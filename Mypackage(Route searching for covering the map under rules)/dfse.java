package Mypackage;
import java.util.ArrayList;

public class dfse{
    public int[][] arr;
    public int[][] rec;
    public ArrayList<Pair> pairs;
    public ArrayList<Pair> castles;
    public Pair st;
    public boolean finished;
    public ArrayList<Pair> visitedcastles;
    /*rec里面，0是没有走过，1是走过了。arr记录所有城堡和路基(1,2,3,4,0)。decide判定是路基还是终点城堡：0不是终点城堡，1是终点城堡。Pairs记录走过路径，路径上标记了城堡颜色。
    Pairs不断更改。。
    */
    public dfse(int[][] a){
        arr = a;
        rec = new int[a.length][a[0].length];
        pairs = new ArrayList<Pair>();
        castles = new ArrayList<Pair>();
        for(int i=0;i<a.length;i++)
            for(int j=0;j<a[0].length;j++)
                if(arr[i][j]!=0)
                    castles.add(new Pair(arr[i][j],i,j));
        st = castles.get(0);
        pairs.add(new Pair(st.num,st.x,st.y));
        finished = false;
        visitedcastles = new ArrayList<Pair>();
        visitedcastles.add(st);
    }

    public void renew(){
        dfse newd =new dfse(arr);
        rec = newd.rec;
        pairs = newd.pairs;
        castles = newd.castles;
        st = newd.st;
        pairs.add(new Pair(st.num,st.x,st.y));
        finished = false;
        visitedcastles = newd.visitedcastles;
    }
    
    
    
    
    
    public void dfs(int decide){
        if(!finished && decide == 0){
            Pair m = pairs.get(pairs.size()-1);
            if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
                rec[m.x][m.y]=1;
                pairs.add(new Pair(st.num,m.x,m.y+1));
                dfs((arr[m.x][m.y+1]!=0)? 1:0 );
            }
            
            if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
                rec[m.x][m.y]=1;
                pairs.add(new Pair(st.num,m.x+1,m.y));
                dfs((arr[m.x+1][m.y]!=0)? 1:0 );
            }

            if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
                rec[m.x][m.y]=1;
                pairs.add(new Pair(st.num,m.x-1,m.y));
                dfs((arr[m.x-1][m.y]!=0)? 1:0 );
            }

            if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
                rec[m.x][m.y]=1;
                pairs.add(new Pair(st.num,m.x,m.y-1));
                dfs((arr[m.x][m.y-1]!=0)? 1:0 );
            }
            if(!finished && pairs.size()!=1){
                rec[m.x][m.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }
            else if(!finished){
                rec[m.x][m.y] = 1;
                this.renew();
                dfs1(0);
            }
            }


        else if(!finished && decide == 1){
            if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
                rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
                visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
                if(decideiffinished(0)){
                    for(int i=0;i<pairs.size();i++){
                        arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                    }
                }
                else if(!finished && visitedcastles.size()!=castles.size()){
                        Pair originalst = new Pair(st.num,st.x,st.y);
                        for(int i=0;i<castles.size();i++){
                            if(!contains(castles.get(i))){
                                visitedcastles.add(castles.get(i));
                                st = castles.get(i);
                                pairs.add(st);
                                break;
                            }
                        }
                        dfs(0);
                        if(!finished){
                            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                            st = originalst;
                            visitedcastles.remove(visitedcastles.size()-1);
                            visitedcastles.remove(visitedcastles.size()-1);
                            pairs.remove(pairs.size()-1);
                            return;
                        }
                    }

                else if(!finished){
                    Pair p = pairs.get(pairs.size()-1);
                    rec[p.x][p.y] = 0;
                    pairs.remove(pairs.size()-1);
                    return;
                }

        }

        else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
            Pair p = pairs.get(pairs.size()-1);
            rec[p.x][p.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }
    
    
    }
}

















public void dfs1(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs1((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs1((arr[m.x][m.y+1]!=0)? 1:0 );
        }

        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs1((arr[m.x-1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs1((arr[m.x][m.y-1]!=0)? 1:0 );
        }
           
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }
        else if(!finished){
            rec[m.x][m.y] = 1;
            this.renew();
            dfs2(0);
        }
        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs1(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}

















public void dfs2(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
       
        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs2((arr[m.x-1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs2((arr[m.x][m.y-1]!=0)? 1:0 );
        }
       

        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs2((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs2((arr[m.x][m.y+1]!=0)? 1:0 );
        }
        
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

        else if(!finished){
            rec[m.x][m.y] = 1;
            this.renew();
            dfs3(0);
        }

        

        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs2(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}















public void dfs3(int decide){
if(!finished && decide == 0){
    Pair m = pairs.get(pairs.size()-1);

    if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
        rec[m.x][m.y]=1;
        pairs.add(new Pair(st.num,m.x,m.y-1));
        dfs3((arr[m.x][m.y-1]!=0)? 1:0 );
    }

    if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
        rec[m.x][m.y]=1;
        pairs.add(new Pair(st.num,m.x-1,m.y));
        dfs3((arr[m.x-1][m.y]!=0)? 1:0 );
    }

    if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
        rec[m.x][m.y]=1;
        pairs.add(new Pair(st.num,m.x+1,m.y));
        dfs3((arr[m.x+1][m.y]!=0)? 1:0 );
    }

    if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
        rec[m.x][m.y]=1;
        pairs.add(new Pair(st.num,m.x,m.y+1));
        dfs3((arr[m.x][m.y+1]!=0)? 1:0 );
    }

    if(!finished && pairs.size()!=1){
        rec[m.x][m.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }

    else if(!finished){
        rec[m.x][m.y] = 1;
        this.renew();
        dfs4(0);
    }

   

    }


else if(!finished && decide == 1){
    if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
        visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
        if(decideiffinished(0)){
            for(int i=0;i<pairs.size();i++){
                arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
            }
        }
        else if(!finished && visitedcastles.size()!=castles.size()){
                Pair originalst = new Pair(st.num,st.x,st.y);
                for(int i=0;i<castles.size();i++){
                    if(!contains(castles.get(i))){
                        visitedcastles.add(castles.get(i));
                        st = castles.get(i);
                        pairs.add(st);
                        break;
                    }
                }
                dfs3(0);
                if(!finished){
                    rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                    st = originalst;
                    visitedcastles.remove(visitedcastles.size()-1);
                    visitedcastles.remove(visitedcastles.size()-1);
                    pairs.remove(pairs.size()-1);
                    return;
                }
            }

        else if(!finished){
            Pair p = pairs.get(pairs.size()-1);
            rec[p.x][p.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

}

else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
    Pair p = pairs.get(pairs.size()-1);
    rec[p.x][p.y] = 0;
    pairs.remove(pairs.size()-1);
    return;
}


}
}














public void dfs4(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
        
        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs4((arr[m.x][m.y+1]!=0)? 1:0 );
        }

        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs4((arr[m.x-1][m.y]!=0)? 1:0 );
        }       

        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs4((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs4((arr[m.x][m.y-1]!=0)? 1:0 );
        } 
        
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

        else if(!finished){
            rec[m.x][m.y] = 1;
            this.renew();
            dfs5(0);
        }

        

        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs4(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}












public void dfs5(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
        
        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs5((arr[m.x-1][m.y]!=0)? 1:0 );
        }   

        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs5((arr[m.x][m.y+1]!=0)? 1:0 );
        }    

        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs5((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs5((arr[m.x][m.y-1]!=0)? 1:0 );
        } 
        
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

        else if(!finished){
            rec[m.x][m.y] = 1;
            this.renew();
            dfs6(0);
        }

        

        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs5(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}

















public void dfs6(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
        

        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs6((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs6((arr[m.x][m.y-1]!=0)? 1:0 );
        } 

        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs6((arr[m.x-1][m.y]!=0)? 1:0 );
        }   

        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs6((arr[m.x][m.y+1]!=0)? 1:0 );
        }    
        
        
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

        else if(!finished){
            rec[m.x][m.y] = 1;
            this.renew();
            dfs7(0);
        }  

        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs6(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}









public void dfs7(int decide){
    if(!finished && decide == 0){
        Pair m = pairs.get(pairs.size()-1);
        
        if(!finished && m.y-1>=0 && rec[m.x][m.y-1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y-1));
            dfs7((arr[m.x][m.y-1]!=0)? 1:0 );
        } 


        if(!finished && m.x+1<rec.length && rec[m.x+1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x+1,m.y));
            dfs7((arr[m.x+1][m.y]!=0)? 1:0 );
        }

        if(!finished && m.x-1>=0 && rec[m.x-1][m.y]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x-1,m.y));
            dfs7((arr[m.x-1][m.y]!=0)? 1:0 );
        }   

        if(!finished && m.y+1<rec[0].length && rec[m.x][m.y+1]==0){
            rec[m.x][m.y]=1;
            pairs.add(new Pair(st.num,m.x,m.y+1));
            dfs7((arr[m.x][m.y+1]!=0)? 1:0 );
        }    
        
        
        if(!finished && pairs.size()!=1){
            rec[m.x][m.y] = 0;
            pairs.remove(pairs.size()-1);
            return;
        }

        else if(!finished){
            rec[m.x][m.y] = 0;
            this.renew();
            return;
        }  

        }


    else if(!finished && decide == 1){
        if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]==st.num){
            rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=1;
            visitedcastles.add(new Pair(arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y],pairs.get(pairs.size()-1).x,pairs.get(pairs.size()-1).y));
            if(decideiffinished(0)){
                for(int i=0;i<pairs.size();i++){
                    arr[pairs.get(i).x][pairs.get(i).y]=pairs.get(i).num;
                }
            }
            else if(!finished && visitedcastles.size()!=castles.size()){
                    Pair originalst = new Pair(st.num,st.x,st.y);
                    for(int i=0;i<castles.size();i++){
                        if(!contains(castles.get(i))){
                            visitedcastles.add(castles.get(i));
                            st = castles.get(i);
                            pairs.add(st);
                            break;
                        }
                    }
                    dfs7(0);
                    if(!finished){
                        rec[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]=0;
                        st = originalst;
                        visitedcastles.remove(visitedcastles.size()-1);
                        visitedcastles.remove(visitedcastles.size()-1);
                        pairs.remove(pairs.size()-1);
                        return;
                    }
                }

            else if(!finished){
                Pair p = pairs.get(pairs.size()-1);
                rec[p.x][p.y] = 0;
                pairs.remove(pairs.size()-1);
                return;
            }

    }

    else if(!finished && arr[pairs.get(pairs.size()-1).x][pairs.get(pairs.size()-1).y]!=st.num){
        Pair p = pairs.get(pairs.size()-1);
        rec[p.x][p.y] = 0;
        pairs.remove(pairs.size()-1);
        return;
    }


}
}




    public boolean contains(Pair p){
        for(int i=0;i<visitedcastles.size();i++){
            if(visitedcastles.get(i).equals(p))
                return true;
        }
        return false;
    }

    public boolean decideiffinished(int count){
        for(int i=0;i<rec.length;i++)
                for(int j=0;j<rec[0].length;j++)
                        if(rec[i][j]==0){
                            finished=false;
                            count++;
                        }

        if(count == 0)
            finished = true;
        return finished;
                    }
    }
