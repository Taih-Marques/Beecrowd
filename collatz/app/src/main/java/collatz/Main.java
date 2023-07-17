package collatz;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);       
        int maximo = leitor.nextInt();
        int tamanhoSenha = leitor.nextInt();
        int possibilidades = dividirProblema(maximo, tamanhoSenha, 1, maximo);
        System.out.println(possibilidades);
    }

    // passar por cada elemento de i..j e mandar pra collatz
    private static int dividirProblema(int maximo, int tamanhoSenha, int inicioParte, int fimParte) {
        if (inicioParte == fimParte) {
            // menor parte possivel, i vai ser o nk do collatz
            return geraSequenciaValida(maximo, tamanhoSenha, inicioParte);
        }
        int meio = (fimParte + inicioParte) / 2;
        int possibilidades1aParte = dividirProblema(maximo, tamanhoSenha, inicioParte, meio);
        int possibilidades2aParte = dividirProblema(maximo, tamanhoSenha, meio + 1, fimParte);
        return possibilidades1aParte + possibilidades2aParte;
    }

    // descobrir se começando por 'atual' da pra formar uma seq valida
    private static int geraSequenciaValida(int maximo, int tamanhoSenha, int atual) {
        for (int i = tamanhoSenha; i!=1; i--){
            if(atual % 2 == 0){
                atual = atual / 2; 
            } else{
                atual = (atual * 3) + 1;
                if(atual > maximo){
                    return 0;
                }
            }
        }
        return 1; 
}
}
