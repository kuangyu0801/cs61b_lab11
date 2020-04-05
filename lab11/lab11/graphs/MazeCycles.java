package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze maze;
    private boolean cycleFound;
    private int s;
    private int[] bufferEdgeTo;
    private boolean[] bufferMarked;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        cycleFound = false;
        s = maze.xyTo1D(1, 1);
        bufferEdgeTo = new int[maze.V()];
        bufferMarked = new boolean[maze.V()];
        for (int i = 0; i < maze.V(); i += 1) {
            bufferEdgeTo[i] = edgeTo[i];
            bufferMarked[i] = marked[i];
        }
    }

    @Override
    public void solve() {
        // DONE: Your code here!
        //dfs(s);
        //System.out.println("Done");
        cycleDetect(s);
    }

    private void dfs(int v) {
        for (int w : maze.adj(v)) {
            if (!bufferMarked[w]) {
                bufferMarked[w] = true;
                bufferEdgeTo[w] = v;
                dfs(w);
            }
        }
        return;
    }

    // Helper methods go here
    private void setCyclePath(int head, int tail) {
        if (head == tail) {
            cycleFound = true;
            announce();
            return;
        }
        edgeTo[tail] = bufferEdgeTo[tail];
        if(edgeTo[tail] != Integer.MAX_VALUE) {
            setCyclePath(head, edgeTo[tail]);
        }
    }


    private void cycleDetect(int v) {
        marked[v] = true;
        edgeTo[v] = v;
        dfsCycleDetect(v);
        return;
    }

    /**
     * For every visited vertex v,
     * if there is an adjacent u such that u is already visited and u is not parent of v,
     * then there is a cycle in graph.*/
    private void dfsCycleDetect(int v) {

        marked[v] = true;

        if(cycleFound) {
            announce();
            return;
        }

        for (int u : maze.adj(v)) {
            if (!marked[u]) {
                marked[u] = true;
                bufferEdgeTo[u] = v;
                dfsCycleDetect(u);
            } else if (marked[u] && bufferEdgeTo[v] != u) {
                edgeTo[u] = v;
                setCyclePath(u, v);
            }
        }
        return;
    }
}

