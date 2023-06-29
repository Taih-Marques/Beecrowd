import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Grafo {
    private int V; // número de vértices
    private int[][] adj; // matriz de adjacência
    private int[] lbl; // rótulos dos vértices
    private int cnt; // contador de profundidade

    public Grafo(int V) {
        this.V = V;
        adj = new int[V][V];
        lbl = new int[V];
        cnt = 0;
    }

    public void addEdge(int v, int w) {
        adj[v][w] = 1;
    }

    public void pathR(int v) {
        lbl[v] = cnt++;

        for (int w = 0; w < V; w++) {
            if (adj[v][w] == 1 && lbl[w] == -1) {
                pathR(w);
            }
        }
    }

    public void depthFirstSearch() {
        for (int v = 0; v < V; v++) {
            if (lbl[v] == -1) {
                pathR(v);
            }
        }
    }

    public void printResults() {
        for (int v = 0; v < V; v++) {
            System.out.println(getIndentation(lbl[v]) + v + "-" + lbl[v] + " pathR(G," + v + ")");
        }
    }

    private String getIndentation(int depth) {
        StringBuilder indentation = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indentation.append("  ");
        }
        return indentation.toString();
    }

    public static void main(String[] args) {
        try {
            File file = new File("entrada.txt");
            Scanner scanner = new Scanner(file);

            int numTestCases = scanner.nextInt();

            for (int t = 0; t < numTestCases; t++) {
                int V = scanner.nextInt();
                int E = scanner.nextInt();

                Grafo dfs = new Grafo(V);

                for (int i = 0; i < E; i++) {
                    int v = scanner.nextInt();
                    int w = scanner.nextInt();
                    dfs.addEdge(v, w);
                }

                dfs.depthFirstSearch();
                dfs.printResults();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
