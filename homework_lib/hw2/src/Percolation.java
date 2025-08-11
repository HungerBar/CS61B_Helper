import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.List;


public class Percolation {
    // TODO: Add any necessary instance variables.
     WeightedQuickUnionUF n,status;
    int tag,length;
    public Percolation(int N) {
        n = new WeightedQuickUnionUF(N*N);
        status = new WeightedQuickUnionUF(N*N);
        length = N;
        tag = -1;
    }

    private boolean openneighbors(int row, int col) {
        List<Integer> neighbors = new ArrayList<>();
        List<Integer> opneighbors = new ArrayList<>();
        if (row != 0){
            neighbors.add((row - 1) * length + col);
        }if (col != 0){
            neighbors.add( row * length + col - 1);
        }if (row != length - 1){
            neighbors.add((row + 1) * length + col);
        }if (col != length - 1){
            neighbors.add(row * length + col + 1);
        }
        for(int i : neighbors){
            int c = i % length;
            int r = (i - c) / length;
            if(isOpen(r, c)){
                opneighbors.add(i);
            }
        }
        if(opneighbors.size() == 0){
            return false;
        }else{
            int p = row * length + col;
            for(int i : opneighbors){
                if(!n.connected(p, i)){
                    n.union(p, i);
                }
            }
        }
        return true;

    }


    public void open(int row, int col) {
        if (tag == -1) {
            tag = row*length + col ;
        }else{
            status.union(tag , row*length + col);
        }

        openneighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        if (tag == -1) {
            return false;
        }else if (tag == row*length + col){
            return true;
        }else{
            return status.connected(tag , row*length + col);
        }
    }

    public boolean isFull(int row, int col) {
        if(!isOpen(row, col)){
            return false;
        }
        if (row == 0){
            return true;
        }
        for(int i = 0; i < length; i++){
            int t = i;
            if(n.connected(t, row*length + col)){
                return true;
            }
        }
        return false;
    }

    public int numberOfOpenSites() {
        if (tag == -1) {
            return 0;
        }
        else{
            return length*length-status.count() + 1;
        }
    }

    public boolean percolates() {
        for (int j = 0;j < length;j++){
            if(isFull(length-1, j) ){
                return true;
            }
        }

        return false;
    }


}
