package Mypackage;
public class bpTest3{
    public static void main(String[] args){
        double[][] time = {{0},{0.2},{0.45},{0.6},{0.7},{0.8},{0.85}};/* Divided by ten to obtain the range 0-1 */
        double[][] OD1 = {{0.046},{0.0696},{0.386},{0.671},{0.7949},{0.8970},{0.9100}};
        double[][] OD2 = {{0.0459},{0.0945},{0.406},{0.675},{0.8192},{0.8977},{0.9129}};
        double[][] OD3 = {{0.0472},{0.0781},{0.466},{0.697},{0.8133},{0.8807},{0.888}};/* There are some problems with the last datum in this set*/
        double[][] OD4 = {{0.0464},{0.0817},{0.476},{0.725},{0.8127},{0.8858},{0.9032}};
    bp a = new bp();
    bp b = new bp();
    bp c = new bp();
    bp d = new bp();
    a.train(time,OD1);
    b.train(time,OD2);
    c.train(time,OD3);
    d.train(time,OD4);
    a.bpNetOutput1(time,OD1);
    b.bpNetOutput1(time,OD2);
    c.bpNetOutput1(time,OD3);
    d.bpNetOutput1(time,OD4);
    }
}