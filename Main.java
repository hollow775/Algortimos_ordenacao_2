import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
public class Main {

    public static void main(String[] args) throws IOException {
        FileReader file = new FileReader("/Users/macbook/Documents/IF_BBC/EAD/Estrutura_de_dados_II/Arquivos_ordenacao/dados_3.txt");
        BufferedReader readFile = new BufferedReader(file);
        FileWriter novoArquivo = new FileWriter("/Users/macbook/Documents/IF_BBC/EAD/Estrutura_de_dados_II/Arquivos_ordenacao/Algoritmos_ord_2/dadosOrdenados_3.txt");
        PrintWriter gravarArquivo = new PrintWriter(novoArquivo);

        gravarArquivo.printf("Desenvolvedor: Clayton Rodrigues Dos Prazeres.\n ___________ \n");
        // preparando o documento

        double contador = 0.0;
        String line = "";
        long tempoInicial;
        long tempoFinal;

        String linha = readFile.readLine();
        while (linha != null) {
            linha = readFile.readLine();
            if (linha != null) {
                line = linha;
            }
        }
        file.close();
        line = line.replace("[", "");
        line = line.replace("]", "");
        line = line.replace(" ", "");
        String[] str = line.split(",");
        int[] dados = new int[str.length];
        iniciarVetor(dados, str);

        //QuickSort - aqui
        tempoInicial = System.currentTimeMillis();
        quickSort(dados,0, dados.length-1);
        tempoFinal = System.currentTimeMillis() - tempoInicial;
        System.out.println("QuickSort Executado em = " + (tempoFinal) + "ms\n");
        gravarArquivo.printf("\nQuickSort: \n--------------------\n");
        gravarArquivo(gravarArquivo, tempoFinal, dados);

        //MergeSort - aqui
        contador = 0.0;
        iniciarVetor(dados, str);
        tempoInicial = System.currentTimeMillis();
        mergeSort(0, dados.length, dados);
        tempoFinal = System.currentTimeMillis() - tempoInicial;
        System.out.println("MergeSort Executado em = " + (tempoFinal) + "ms\n");
        gravarArquivo.printf("\nMergeSort: \n--------------------\n");
        gravarArquivo(gravarArquivo, tempoFinal, dados);

        //Shell Sort - aqui
        contador = 0.0;
        iniciarVetor(dados, str);
        tempoInicial = System.currentTimeMillis();
        shellSort(dados);
        tempoFinal = System.currentTimeMillis() - tempoInicial;
        System.out.println("Shell Sort Executado em = " + (tempoFinal) + "ms\n");
        gravarArquivo.printf("\nShell Sort: \n--------------------\n");
        gravarArquivo(gravarArquivo, tempoFinal, dados);


        gravarArquivo.close();
    }

    public static void gravarArquivo(PrintWriter gravarArquivo,long tempoFinal, int dados[]) {
        gravarArquivo.printf("O tempo gasto foi: " + tempoFinal + " ms.\n");
        gravarArquivo.printf("O vetor ordenado é: \n");
        for (int i = 0; i < dados.length; i++) {
            if (i == 0) {
                gravarArquivo.printf("[" + dados[i] + ", ");
            } else if (i == dados.length - 1) {
                gravarArquivo.printf(dados[i] + "]");
            } else {
                gravarArquivo.printf(dados[i] + ", ");
            }
        }
        gravarArquivo.printf("_______________________________\n\n\n");
    }

    public static void iniciarVetor(int dados[], String str[]) {
        for (int i = 0; i < str.length; i++) {
            dados[i] = Integer.parseInt(str[i]);
        }
    }
    private static void quickSort(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int posicaoPivo = separar(vetor, inicio, fim);
          //Finalizado o processo de separação então é chamado um próprio método
          //quickSort() de forma recursiva, onde ele fará o processo de separação interna
          //dentro da sublista e assim será feito até que todos os elementos estejam ordenados.
            quickSort(vetor, inicio, posicaoPivo - 1);
            quickSort(vetor, posicaoPivo + 1, fim);
        }
    }
    // esse metodo separar retorna o pivo do algoritmo
    // esse pivo será o agente necessario para a ordenaçao da base de dados.
    // o pivo é disposto no meio da lista
    private static int separar(int[] vetor, int inicio, int fim) {
        int pivo = vetor[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (vetor[i] <= pivo)
                i++;
            else if (pivo < vetor[f])
                f--;
            else {
                int troca = vetor[i];
                vetor[i] = vetor[f];
                vetor[f] = troca;
                i++;
                f--;
            }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
    }
    private static void mergeSort(int inicio, int fim, int[] vetor) {
        // Se o inicio for menor que o fim menos 1, significa que tem elementos dentro do vetor.
        if(inicio < fim - 1) {
            // Guarda a posição do meio do vetor.
            int meio = (inicio + fim) / 2;

            //Chama este método recursivamente, indicando novas posições de inicio e fim do vetor.
            mergeSort(inicio, meio, vetor);

            // Chama este método recursivamente, indicando novas posições de inicio e fim do vetor.
            mergeSort(meio, fim, vetor);

            // Chama o método que intercala os elementos do vetor.
            intercala(vetor, inicio, meio, fim);
        }
    }
    private static void intercala(int[] vetor, int inicio, int meio, int fim) {
        int novoVetor[] = new int[fim - inicio];
        int i = inicio;
        int m = meio;
        int pos = 0;

        // Enquanto o inicio não chegar até o meio do vetor, ou o meio do vetor
        // não chegar até seu fim, compara os valores entre o inicio e o meio,
        // verificando em qual ordem vai guarda-los ordenado.
        while(i < meio && m < fim) {
      //Se o vetor[i] for menor que o vetor[m], então guarda o valor do vetor[i] pois este é menor.
            if(vetor[i] <= vetor[m]) {
                novoVetor[pos] = vetor[i];
                pos = pos + 1;
                i = i + 1;
                // Senão guarda o valor do vetor[m] pois este é o menor.
            } else {
                novoVetor[pos] = vetor[m];
                pos = pos + 1;
                m = m + 1;
            }
        }

        // Adicionar no vetor os elementos que estão entre o inicio e meio,
        // que ainda não foram adicionados no vetor.
        while(i < meio) {
            novoVetor[pos] = vetor[i];
            pos = pos + 1;
            i = i + 1;
        }

        // Adicionar no vetor os elementos que estão entre o meio e o fim,
        // que ainda não foram adicionados no vetor.
        while(m < fim) {
            novoVetor[pos] = vetor[m];
            pos = pos + 1;
            m = m + 1;
        }

        // Coloca no vetor os valores já ordenados.
        for(pos = 0, i = inicio; i < fim; i++, pos++) {
            vetor[i] = novoVetor[pos];
        }
    }
    private static void shellSort(int vetor[])
    {
        int n = vetor.length;

        // Começa dividindo em um grupo grande e depois vai aumentando a quantidade de  grupos e
        // dimunuindo o tamanho do grupo.
        for (int gap = n/2; gap > 0; gap /= 2)
        {
            //depois faz um insertion sort em cada gap
            for (int i = gap; i < n; i += 1)
            {
                int temp = vetor[i];

                int j;
                for (j = i; j >= gap && vetor[j - gap] > temp; j -= gap)
                    vetor[j] = vetor[j - gap];

                vetor[j] = temp;
            }
        }
    }
}


