import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SistemaPessoas {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Cadastrar pessoa");
            System.out.println("2 - Listar pessoas");
            System.out.println("3 - Atualizar idade de pessoa");
            System.out.println("4 - Remover pessoa");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Digite a idade: ");
                    int idade = Integer.parseInt(scanner.nextLine());

                    salvarPessoa(nome, idade);
                    break;

                case 2:
                    listarPessoas();
                    break;

                case 3:
                    atualizarIdade(scanner);
                    break;

                case 4:
                    removerPessoa(scanner);
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }

    public static void salvarPessoa(String nome, int idade) {
        try {
            FileWriter arquivo = new FileWriter("pessoas.txt", true); // true = append
            PrintWriter gravador = new PrintWriter(arquivo);

            gravador.println(nome + ";" + idade); // salva no formato nome;idade
            gravador.close();

            System.out.println("✅ Pessoa cadastrada com sucesso!");

        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }

    public static void listarPessoas() {
        try {
            File arquivo = new File("pessoas.txt");
            Scanner leitor = new Scanner(arquivo);

            System.out.println("\n=== Lista de Pessoas ===");

            // Lê cada linha do arquivo
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim(); // trim para remover espaços extras
                if (linha.isEmpty()) {
                    continue; // pula linhas em branco
                }

                String[] dados = linha.split(";");

                // Verifica se temos pelo menos 2 dados (nome e idade)
                if (dados.length >= 2) {
                    System.out.println("Nome: " + dados[0] + ", Idade: " + dados[1]);
                } else {
                    System.out.println("Dados inválidos na linha: " + linha);
                }
            }

            leitor.close();

        } catch (FileNotFoundException e) {
            System.out.println("❌ Arquivo não encontrado. Você ainda não cadastrou nenhuma pessoa.");
        }
    }
    public static void atualizarIdade(Scanner scanner) {
        System.out.print("Digite o nome da pessoa a ser atualizada: ");
        String nomeBusca = scanner.nextLine();

        File arquivo = new File("pessoas.txt");
        File tempFile = new File("pessoas_temp.txt");

        try {
            Scanner leitor = new Scanner(arquivo);
            FileWriter arquivoTemp = new FileWriter(tempFile, true);
            PrintWriter gravadorTemp = new PrintWriter(arquivoTemp);

            boolean encontrado = false;
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(";");

                if (dados.length >= 2 && dados[0].equalsIgnoreCase(nomeBusca)) {
                    System.out.print("Digite a nova idade para " + nomeBusca + ": ");
                    int novaIdade = Integer.parseInt(scanner.nextLine());
                    gravadorTemp.println(dados[0] + ";" + novaIdade);  // Atualiza a idade
                    System.out.println("✅ Idade de " + nomeBusca + " atualizada!");
                    encontrado = true;
                } else {
                    gravadorTemp.println(linha);  // Mantém a linha original
                }
            }

            leitor.close();
            gravadorTemp.close();

            if (!encontrado) {
                System.out.println("❌ Pessoa não encontrada.");
            } else {
                arquivo.delete();  // Deleta o arquivo original
                tempFile.renameTo(arquivo);  // Renomeia o arquivo temporário
            }

        } catch (IOException e) {
            System.out.println("❌ Erro ao atualizar: " + e.getMessage());
        }
    }
    public static void removerPessoa(Scanner scanner) {
        System.out.print("Digite o nome da pessoa a ser removida: ");
        String nomeBusca = scanner.nextLine();

        File arquivo = new File("pessoas.txt");
        File tempFile = new File("pessoas_temp.txt");

        try {
            Scanner leitor = new Scanner(arquivo);
            FileWriter arquivoTemp = new FileWriter(tempFile, true);
            PrintWriter gravadorTemp = new PrintWriter(arquivoTemp);

            boolean encontrado = false;
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] dados = linha.split(";");

                if (dados.length >= 2 && dados[0].equalsIgnoreCase(nomeBusca)) {
                    System.out.println("✅ " + nomeBusca + " removido!");
                    encontrado = true;
                    continue;  // Não grava essa linha, efetivamente removendo
                } else {
                    gravadorTemp.println(linha);  // Mantém a linha original
                }
            }

            leitor.close();
            gravadorTemp.close();

            if (!encontrado) {
                System.out.println("❌ Pessoa não encontrada.");
            } else {
                arquivo.delete();  // Deleta o arquivo original
                tempFile.renameTo(arquivo);  // Renomeia o arquivo temporário
            }

        } catch (IOException e) {
            System.out.println("❌ Erro ao remover: " + e.getMessage());
        }
    }
}

