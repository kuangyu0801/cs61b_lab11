package lab11.graphs;

import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        // Add more variables here!
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(int src) {
        // DONE: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
       PriorityQueue<Integer> myFrindge = new PriorityQueue<>();

        marked[src] = true;
        myFrindge.add(src); // adding the source to the queue
        while(myFrindge.size() != 0) {
            int vFrindge = myFrindge.remove();
            Iterable<Integer> vFrindgeAdj = maze.adj(vFrindge);
            Iterator<Integer> vFrindgeAdjIterator = vFrindgeAdj.iterator();
            for (int vNext : maze.adj(vFrindge)) {
                if (!marked[vNext]) {
                    if(vNext == t) {
                        targetFound = true;
                    }
                    myFrindge.add(vNext);
                    distTo[vNext] = distTo[vFrindge] + 1;
                    edgeTo[vNext] = vFrindge;
                    marked[vNext] = true;
                    announce();
                    if (targetFound) {
                        return;
                    }
                }
            }
/*            while (vFrindgeAdjIterator.hasNext()) {
                int vNext = vFrindgeAdjIterator.next();
                // if the node has not been visited then add it to fridge
                if (!marked[vNext]) {
                    if(vNext == t) {
                        targetFound = true;
                    }
                    myFrindge.add(vNext);
                    distTo[vNext] = distTo[vFrindge] + 1;
                    edgeTo[vNext] = vFrindge;
                    marked[vNext] = true;
                    announce();
                    if (targetFound) {
                        return;
                    }
                }
            }*/
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
}

