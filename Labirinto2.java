import java.util.*;

class Main {
    static class Estado {
        int[] robotLinha;
        int[] robotColuna;

        public Estado(int[] robotLinha, int[] robotColuna) {
            this.robotLinha = robotLinha;
            this.robotColuna = robotColuna;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(robotLinha) + Arrays.hashCode(robotColuna);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Estado other = (Estado) obj;
            return Arrays.equals(robotLinha, other.robotLinha) && Arrays.equals(robotColuna, other.robotColuna);
        }
    }

    static boolean isValido(int linha, int coluna, int N) {
        return linha >= 0 && linha < N && coluna >= 0 && coluna < N;
    }

    static boolean estaPreso(int[] contadorAlvo) {
        for (int count : contadorAlvo) {
            if (count < 3) {
                return true;
            }
        }
        return false;
    }

    static int resolverLabirinto(char[][] labirinto, int[] robotLinha, int[] robotColuna, int N) {
        int[][] direcoes = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int[] contadorAlvo = new int[3];
        Set<Estado> visitados = new HashSet<>();
        Queue<Estado> fila = new LinkedList<>();

        Estado estadoInicial = new Estado(robotLinha, robotColuna);
        visitados.add(estadoInicial);
        fila.offer(estadoInicial);

        int tempo = 0;

        while (!fila.isEmpty()) {
            int tamanho = fila.size();
            for (int i = 0; i < tamanho; i++) {
                Estado estadoAtual = fila.poll();
                int[] atualLinhaRobo = estadoAtual.robotLinha;
                int[] atualColunaRobo = estadoAtual.robotColuna;

                // Verificar se o estado atual alcançou a condição de parada
                if (!estaPreso(contadorAlvo)) {
                    return tempo;
                }

                for (int j = 0; j < 3; j++) {
                    int linha = atualLinhaRobo[j];
                    int coluna = atualColunaRobo[j];

                    if (labirinto[linha][coluna] == 'X') {
                        contadorAlvo[j]++;
                    }

                    for (int[] direcao : direcoes) {
                        int proximaLinha = linha + direcao[0];
                        int proximaColuna = coluna + direcao[1];

                        if (isValido(proximaLinha, proximaColuna, N) && (labirinto[proximaLinha][proximaColuna] == '.' || labirinto[proximaLinha][proximaColuna] == 'X')) {
                            int[] proximoLinhaRobo = Arrays.copyOf(atualLinhaRobo, 3);
                            int[] proximoColunaRobo = Arrays.copyOf(atualColunaRobo, 3);
                            proximoLinhaRobo[j] = proximaLinha;
                            proximoColunaRobo[j] = proximaColuna;

                            Estado proximoEstado = new Estado(proximoLinhaRobo, proximoColunaRobo);

                            if (!visitados.contains(proximoEstado)) {
                                visitados.add(proximoEstado);
                                fila.offer(proximoEstado);
                            }
                        }
                    }
                }
            }

            tempo++;
        }

        return -1; // Labirinto impossível de ser resolvido
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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

            int resultado = resolverLabirinto(labirinto, robotLinha, robotColuna, N);

            System.out.println("Case " + testCase + ": " + (resultado == -1 ? "trapped" : resultado));
        }

        scanner.close();
    }
}
