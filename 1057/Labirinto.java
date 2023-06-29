
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Labirinto {

    static class Posicao {

        int x;
        int y;
    }

    static Posicao aneed = new Posicao();
    static Posicao ben = new Posicao();
    static Posicao cindy = new Posicao();
    static Posicao[] alvos = new Posicao[3];
    static final char A = 'A';
    static final char B = 'B';
    static final char C = 'C';
    static final char X = 'X';

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("entrada");
        Scanner leitor = new Scanner(file);
        int t = leitor.nextInt();
        for (int i = 1; i <= t; i++) {

            int n = leitor.nextInt();
            leitor.nextLine(); // le \n sobrando
            char[][] matriz = lerMatriz(n, leitor);

            System.out.print("Case " + i + ": ");
            int tempo = resolverMatriz(n, matriz);
            System.out.println(tempo > 0 ? tempo : "trapped");
        }
    }

    public static int resolverMatriz(int n, char[][] matriz) {
        imprimirMatriz(n, matriz);
        
        return 0;
    }

    public static char[][] lerMatriz(int n, Scanner leitor) {
        char[][] matriz = new char[n][n];
        int alvosEncontrados = 0;
        for (int y = 0; y < n; y++) {
            String linha = leitor.nextLine();
            matriz[y] = linha.toCharArray();
            for (int x = 0; x < n; x++) {
                switch (matriz[y][x]) {
                    case A:
                        aneed.y = y;
                        aneed.x = x;
                        break;
                    case B:
                        ben.y = y;
                        ben.x = x;
                        break;
                    case C:
                        cindy.y = y;
                        cindy.x = x;
                        break;
                    case X:
                        alvos[alvosEncontrados] = new Posicao();
                        alvos[alvosEncontrados].y = y;
                        alvos[alvosEncontrados].x = x;
                        alvosEncontrados++;
                        break;
                    default:
                        break;
                }
            }
        }
        return matriz;
    }

    public static void imprimirMatriz(int n, char[][] matriz) {
        System.out.println("-----------------------");
        for (int i = 0; i < n; i++) {
            String linha = new String(matriz[i]);
            System.out.println(linha);
        }
        System.out.println("-----------------------");
    }
}
