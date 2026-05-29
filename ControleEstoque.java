import java.util.Scanner;

class Produto {
    String nome;
    int qtdEstoque;
    double precoUnitario;
    String categoria;
    int qtdMinima;
}

public class ControleEstoque {
    public static final int TAM = 100;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        Produto[] produtos = new Produto[TAM];
        int qtd = 0;
        int opcao;

        do {
            System.out.println("\n===== CONTROLE DE ESTOQUE =====");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar");
            System.out.println("3 - Filtrar por categoria");
            System.out.println("4 - Ordenar");
            System.out.println("5 - Remover elemento");
            System.out.println("6 - Atualizar preco");
            System.out.println("7 - Listar com subtotal por categoria");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = input.nextInt();
            input.nextLine();

            if (opcao == 1) {
                qtd = cadastrarProduto(produtos, qtd);
            } else if (opcao == 2) {
                listar(produtos, qtd);
            } else if (opcao == 3) {
                filtrarPorCategoria(produtos, qtd);
            } else if (opcao == 4) {
                ordenar(produtos, qtd);
            } else if (opcao == 5) {
                qtd = removerElemento(produtos, qtd);
            } else if (opcao == 6) {
                atualizarPreco(produtos, qtd);
            } else if (opcao == 7) {
                listarComSubtotalPorCategoria(produtos, qtd);
            }
        } while (opcao != 0);
    }

    public static int cadastrarProduto(Produto[] v, int qtd) {
        if (qtd >= v.length) {
            System.out.println("Estoque cheio!");
            return qtd;
        }
        v[qtd] = new Produto();
        System.out.println("Digite o nome: ");
        v[qtd].nome = input.nextLine();
        System.out.println("Digite a categoria: ");
        v[qtd].categoria = input.nextLine();
        System.out.println("Digite a quantidade em estoque: ");
        v[qtd].qtdEstoque = input.nextInt();
        System.out.println("Digite o preco unitario: ");
        v[qtd].precoUnitario = input.nextDouble();
        System.out.println("Digite a quantidade minima: ");
        v[qtd].qtdMinima = input.nextInt();
        input.nextLine();
        return qtd + 1;
    }

    public static void listar(Produto[] v, int qtd) {
        System.out.println("\n===== LISTAGEM =====");
        for (int i = 0; i < qtd; i += 1) {
            imprimirProduto(v[i], i);
        }
    }

    public static void imprimirProduto(Produto p, int i) {
        System.out.printf("[%d] %s | Cat: %s | Qtd: %d | Preco: R$%.2f | Min: %d\n",
            i + 1, p.nome, p.categoria, p.qtdEstoque, p.precoUnitario, p.qtdMinima);
    }

    public static void filtrarPorCategoria(Produto[] v, int qtd) {
        System.out.println("Digite a categoria: ");
        String categoria = input.nextLine();
        System.out.println("\n===== FILTRO: " + categoria + " =====");
        int encontrados = 0;
        for (int i = 0; i < qtd; i += 1) {
            if (v[i].categoria.compareToIgnoreCase(categoria) == 0) {
                imprimirProduto(v[i], i);
                encontrados += 1;
            }
        }
        if (encontrados == 0) {
            System.out.println("Nenhum produto encontrado nessa categoria.");
        }
    }

    public static void ordenar(Produto[] v, int qtd) {
        selectionSortPorCategoriaNome(v, qtd);
        System.out.println("Produtos ordenados por categoria e nome!");
        listar(v, qtd);
    }

    public static void selectionSortPorCategoriaNome(Produto[] v, int n) {
        for (int i = 0; i < n - 1; i += 1) {
            int menor = i;
            for (int j = i + 1; j < n; j += 1) {
                int cmp = v[j].categoria.compareToIgnoreCase(v[menor].categoria);
                if (cmp < 0 || (cmp == 0 && v[j].nome.compareToIgnoreCase(v[menor].nome) < 0)) {
                    menor = j;
                }
            }
            Produto aux = v[i];
            v[i] = v[menor];
            v[menor] = aux;
        }
    }

    public static int removerElemento(Produto[] v, int qtd) {
        System.out.println("Digite o nome do produto a remover: ");
        String nome = input.nextLine();
        int pos = buscaSequencialPorNome(v, qtd, nome);
        if (pos == -1) {
            System.out.println("Produto nao encontrado!");
            return qtd;
        }
        for (int i = pos; i < qtd - 1; i += 1) {
            v[i] = v[i + 1];
        }
        v[qtd - 1] = null;
        System.out.println("Produto removido com sucesso!");
        return qtd - 1;
    }

    public static int buscaSequencialPorNome(Produto[] v, int qtd, String nome) {
        for (int i = 0; i < qtd; i += 1) {
            if (v[i].nome.compareToIgnoreCase(nome) == 0) {
                return i;
            }
        }
        return -1;
    }

    public static void atualizarPreco(Produto[] v, int qtd) {
        System.out.println("Digite o nome do produto: ");
        String nome = input.nextLine();
        int pos = buscaSequencialPorNome(v, qtd, nome);
        if (pos == -1) {
            System.out.println("Produto nao encontrado!");
            return;
        }
        System.out.println("Digite o novo preco: ");
        v[pos].precoUnitario = input.nextDouble();
        input.nextLine();
        System.out.println("Preco atualizado com sucesso!");
    }

    public static void listarComSubtotalPorCategoria(Produto[] v, int qtd) {
        selectionSortPorCategoriaNome(v, qtd);
        System.out.println("\n===== LISTAGEM COM SUBTOTAL POR CATEGORIA =====");
        double totalGeral = 0;
        int i = 0;
        while (i < qtd) {
            String categoriaAtual = v[i].categoria;
            System.out.println("\nCategoria: " + categoriaAtual);
            double subtotal = 0;
            while (i < qtd && v[i].categoria.compareToIgnoreCase(categoriaAtual) == 0) {
                imprimirProduto(v[i], i);
                subtotal += v[i].qtdEstoque * v[i].precoUnitario;
                i += 1;
            }
            System.out.printf("Subtotal: R$%.2f\n", subtotal);
            totalGeral += subtotal;
        }
        System.out.printf("\nTotal Geral: R$%.2f\n", totalGeral);
    }
}
