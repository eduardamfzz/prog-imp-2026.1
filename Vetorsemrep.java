import java.util.Scanner;

public class Vetorsemrep {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[] dezPosicoes = new int[10];
        int i = 0;

        while (i < 10) {

            System.out.print("Digite o valor para o vetor: ");
            int verificador = sc.nextInt();

            boolean repeticao = false;

           
            for (int j = 0; j < i; j++) {
                if (verificador == dezPosicoes[j]) {
                    repeticao = true;
                    break;
                }
            }

            if (repeticao) {
                System.out.println("Número já encontrado no vetor, digite outro.");
            } else {
                dezPosicoes[i] = verificador;
                i++;
            }

        }

        System.out.println("\nValores do vetor:");

        int k = 0;
        while (k < dezPosicoes.length) {
            System.out.print(dezPosicoes[k] + " ");
            k++;
        }

        sc.close();
    }
}