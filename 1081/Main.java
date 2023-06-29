import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    private int V; // número de vértices
    private int[][] adj; // matriz de adjacência
    private int[] nvl; // nivel dos vértices
    private int cnt; // contador de profundidade

    public Main(int V) {
        this.V = V;
        adj = new int[V][V];
        nvl = new int[V];
        for (int i = 0; i < V; i++) {
            nvl[i] = -1;
        }
        cnt = 0;
    }

    public void addAresta(int v, int w) {
        adj[v][w] = 1;
    }

    public void pathR(int v) {
        nvl[v] = cnt++;
        boolean isolado = true;
        for (int w = 0; w < V; w++) {
            if (adj[v][w] == 1) {
                isolado = false;
                System.out.print(gerarIdentacao(nvl[v]) + v + "-" + w);
                if (nvl[w] == -1) {
                    System.out.println(" pathR(G," + w + ")");
                    pathR(w);
                } else {
                    System.out.println();
                }
            }
        }
        cnt--;
        if (nvl[v] == 0 && !isolado) {
            System.out.println();
        }
    }

    public void buscaProfundidade() {
        for (int v = 0; v < V; v++) {
            if (nvl[v] == -1) {
                pathR(v);

            }
        }
    }

    private String gerarIdentacao(int nivel) {
        StringBuilder identacao = new StringBuilder();
        for (int i = 0; i <= nivel; i++) {
            identacao.append("  ");
        }
        return identacao.toString();
    }

    public static void main(String[] args) throws FileNotFoundException {
     //File file = new File("entrada.txt");
        //Scanner leitor = new Scanner(file);
        Scanner leitor = new Scanner(System.in);
        int numCasos = leitor.nextInt();

        for (int t = 1; t <= numCasos; t++) {
            int V = leitor.nextInt();
            int E = leitor.nextInt();

            Main grafo = new Main(V);

            for (int i = 1; i <= E; i++) {
                int v = leitor.nextInt();
                int w = leitor.nextInt();
                grafo.addAresta(v, w);
            }

            System.out.println("Caso " + t + ":");
            grafo.buscaProfundidade();
        }

        leitor.close();
    }
}
