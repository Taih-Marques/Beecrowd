import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

class Main {
    static class Estado {
        int[] robotLinha;
        int[] robotColuna;
        Estado anterior;
        char[][] labirinto;
        int passos;

        public Estado(int[] robotLinha, int[] robotColuna, char[][] labirinto, Estado anterior) {
            this.robotLinha = robotLinha;
            this.robotColuna = robotColuna;
            this.anterior = anterior;
            this.labirinto = labirinto;
            this.passos = anterior == null ? 0 : anterior.passos + 1;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(robotLinha) + Arrays.hashCode(robotColuna);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Estado other = (Estado) obj;
            return Arrays.equals(robotLinha, other.robotLinha) && Arrays.equals(robotColuna, other.robotColuna);
        }
    }

    static boolean isValido(int linha, int coluna, int N, char[][] labirinto) {
        return linha >= 0 && linha < N && coluna >= 0 && coluna < N && labirinto[linha][coluna] != '#';
    }

    static boolean atingiuAlvos(Estado atual, Estado inicial) {
        for (int i = 0; i < 3; i++) {
            int linhaRobo = atual.robotLinha[i];
            int colunaRobo = atual.robotColuna[i];
            if (inicial.labirinto[linhaRobo][colunaRobo] != 'X') {
                return false;
            }
        }
        return true;
    }

    static Estado resolverLabirinto(Estado inicial, int N) {
        int[][] direcoes = { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } }; // cima, direita, baixo, esquerda
        Set<Estado> visitados = new HashSet<>();
        Queue<Estado> fila = new LinkedList<>();

        visitados.add(inicial);
        fila.offer(inicial);

        while (!fila.isEmpty()) {
            int tamanho = fila.size();
            for (int i = 0; i < tamanho; i++) {
                Estado estadoAtual = fila.poll();
                int[] atualLinhaRobo = estadoAtual.robotLinha;
                int[] atualColunaRobo = estadoAtual.robotColuna;

                for (int[] direcao : direcoes) {
                    int[] proximoLinhaRobo = Arrays.copyOf(atualLinhaRobo, 3);
                    int[] proximoColunaRobo = Arrays.copyOf(atualColunaRobo, 3);
                    char[][] novoLabirinto = Arrays.stream(estadoAtual.labirinto).map(char[]::clone)
                            .toArray(char[][]::new);

                    Queue<Integer> robosAMover = new LinkedList<>();
                    robosAMover.addAll(Arrays.asList(0, 1, 2));
                    while (!robosAMover.isEmpty()) {
                        int roboAtual = robosAMover.poll();
                        int linha = atualLinhaRobo[roboAtual];
                        int coluna = atualColunaRobo[roboAtual];

                        int proximaLinha = linha + direcao[0];
                        int proximaColuna = coluna + direcao[1];

                        if (isValido(proximaLinha, proximaColuna, N, novoLabirinto)) {
                            if (novoLabirinto[proximaLinha][proximaColuna] == '.'
                                    || novoLabirinto[proximaLinha][proximaColuna] == 'X') {
                                proximoLinhaRobo[roboAtual] = proximaLinha;
                                proximoColunaRobo[roboAtual] = proximaColuna;
                                novoLabirinto[linha][coluna] = '.';
                                novoLabirinto[proximaLinha][proximaColuna] = robotNumberToLabel(roboAtual);
                            } else {
                                int roboConflito = robotLabelToNumber(novoLabirinto[proximaLinha][proximaColuna]);
                                if (robosAMover.contains(roboConflito)) {
                                    robosAMover.add(roboAtual);
                                }
                            }
                        }
                    }

                    Estado proximoEstado = new Estado(proximoLinhaRobo, proximoColunaRobo, novoLabirinto, estadoAtual);

                    if (atingiuAlvos(proximoEstado, inicial)) {
                        return proximoEstado;
                    }

                    if (!visitados.contains(proximoEstado)) {
                        visitados.add(proximoEstado);
                        fila.offer(proximoEstado);
                    }
                }
            }
        }

        return null; // Labirinto impossível de ser resolvido
    }

    static char robotNumberToLabel(int i) {
        switch (i) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            default:
                return '?';
        }
    }

    static int robotLabelToNumber(char l) {
        switch (l) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            default:
                return -1;
        }
    }

    static void imprimePassos(BufferedWriter writer, Estado resultado, int N) throws IOException {
        Estado aux = resultado;
        while (aux != null) {
            writer.append("----------Passo " + aux.passos + "------------\n");
            for (int i = 0; i < N; i++) {
                String linha = new String(aux.labirinto[i]);
                writer.append(linha + "\n");
            }
            aux = aux.anterior;
        }
    }

    public static void main(String[] args) throws IOException {
        File saida = new File("saida");
        Files.deleteIfExists(saida.toPath());
        BufferedWriter writer = new BufferedWriter(new FileWriter("saida"));

        File entrada = new File("entrada");
        Scanner scanner = new Scanner(entrada);
        // Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt(); // Número de casos de teste

        for (int testCase = 1; testCase <= T; testCase++) {
            int N = scanner.nextInt(); // Tamanho do labirinto
            scanner.nextLine(); // Limpar o buffer

            char[][] labirinto = new char[N][N];
            int[] robotLinha = new int[3];
            int[] robotColuna = new int[3];

            for (int i = 0; i < N; i++) {
                String linha = scanner.nextLine();
                for (int j = 0; j < N; j++) {
                    labirinto[i][j] = linha.charAt(j);
                    if (labirinto[i][j] == 'A') {
                        robotLinha[0] = i;
                        robotColuna[0] = j;
                    } else if (labirinto[i][j] == 'B') {
                        robotLinha[1] = i;
                        robotColuna[1] = j;
                    } else if (labirinto[i][j] == 'C') {
                        robotLinha[2] = i;
                        robotColuna[2] = j;
                    }
                }
            }

            // int resultado = resolverLabirinto(labirinto, robotLinha, robotColuna, N);
            // System.out.println("Case " + testCase + ": " + (resultado == -1 ? "trapped" :
            // resultado));
            Estado estadoInicial = new Estado(robotLinha, robotColuna, labirinto, null);
            Estado resultado = resolverLabirinto(estadoInicial, N);
            System.out.print("Case " + testCase + ": ");
            if (resultado == null) {
                System.out.println("trapped");
            } else {
                System.out.println(String.valueOf(resultado.passos));

                writer.append("Case " + testCase + "\n");
                imprimePassos(writer, resultado, N);
            }
        }

        writer.close();
        scanner.close();
    }
}
