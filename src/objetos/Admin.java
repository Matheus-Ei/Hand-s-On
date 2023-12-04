package objetos;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

/*
GRAVAR DADOS EM ARQUIVOS

import java.io.FileWriter;
import java.io.IOException;  

OU 

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

1. **Definir os dados a serem gravados.**
2. **Especificar o caminho do arquivo.**
3. **Utilizar as classes `FileWriter` ou `BufferedWriter` para escrever os dados no arquivo.**
4. **Fechar o escritor para liberar os recursos.**
 */

public class Admin extends Usuario {
    public String nome;

    String diretorioAreaDeTrabalho = System.getProperty("user.home") + "/Desktop/";

    // Nome do arquivo
    String dadosDoUsuario = "DadosDoUsuario.txt";
    String todosUsuarios = "TodosUsuarios.txt";

    // Caminho completo do arquivo
    String caminhoParaDadosDoUsuario = diretorioAreaDeTrabalho + dadosDoUsuario;
    String caminhoParaTodosUsuarios = diretorioAreaDeTrabalho + todosUsuarios;

    File arquivoDados = new File(caminhoParaDadosDoUsuario);
    File arquivoUsuarios = new File(caminhoParaTodosUsuarios);

    public void cadastrarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o seu email");
        String email = scanner.nextLine();

        if (arquivoUsuarios.exists()) {
            try (FileReader leitorArquivo = new FileReader(arquivoUsuarios);
                    BufferedReader bufferedReader = new BufferedReader(leitorArquivo)) {

                String linha;

                // Lê cada linha do arquivo
                while ((linha = bufferedReader.readLine()) != null) {
                    // Processa cada linha conforme necessário
                    if (linha.equals("email: " + email)) {
                        System.out.println("Ja tem um usuario cadastrado com esse email");
                        System.out.println("Insira o email novamente");
                        email = scanner.nextLine();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Digite a sSua senha");
        String senha = scanner.nextLine();
        this.fazerLogin(email, senha);
        // Obtendo o diretório da área de trabalho do usuário

        if (!arquivoDados.exists()) {
            try {
                arquivoDados.createNewFile();
                arquivoUsuarios.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String dados = "email: " + email + "    senha: " + senha + "\n";

        try (FileWriter escritor = new FileWriter(caminhoParaDadosDoUsuario, true)) {
            escritor.write(dados);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter escritor = new FileWriter(caminhoParaTodosUsuarios, true)) {
            escritor.write("email: " + email + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listarUsuarios() {
        if (arquivoUsuarios.exists()) {
            try (FileReader leitorArquivo = new FileReader(arquivoUsuarios);
                    BufferedReader bufferedReader = new BufferedReader(leitorArquivo)) {

                String linha;

                // Lê cada linha do arquivo
                while ((linha = bufferedReader.readLine()) != null) {
                    // Processa cada linha conforme necessário
                    System.out.println(linha);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("O arquivo esta vazio!!");
        }

    }

    public void deletarUsuario() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o email a ser deletado:");
        String email = scanner.nextLine();
        String deletarLinha = "email: " + email;

        // Configuração do arquivo temporário
        String temporarioFile = "Temporario.txt";
        String caminhoTemporario = diretorioAreaDeTrabalho + temporarioFile;
        File arquivoTemporario = new File(caminhoTemporario);

        // Verifica se o arquivo de usuários existe
        if (arquivoUsuarios.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivoUsuarios));
                    BufferedWriter escritorTemporario = new BufferedWriter(new FileWriter(arquivoTemporario, true))) {

                String linha;

                // Lê cada linha do arquivo original de usuários
                while ((linha = bufferedReader.readLine()) != null) {
                    // Processa cada linha
                    if (!linha.contains(deletarLinha)) {
                        // Se a linha não contiver a string a ser deletada, escreve no arquivo
                        // temporário
                        escritorTemporario.write(linha);
                        escritorTemporario.newLine();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Substitui o arquivo original pelo temporário
            substituirArquivo(arquivoUsuarios, arquivoTemporario);
        } else {
            System.out.println("O arquivo de usuários está vazio!!");
        }

        // Verifica se o arquivo de dados existe
        if (arquivoDados.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(arquivoDados));
                    BufferedWriter escritorTemporario = new BufferedWriter(new FileWriter(arquivoTemporario, true))) {

                String linha;

                // Lê cada linha do arquivo original de dados
                while ((linha = bufferedReader.readLine()) != null) {
                    // Processa cada linha
                    if (!linha.contains(deletarLinha)) {
                        // Se a linha não contiver a string a ser deletada, escreve no mesmo arquivo
                        // temporário
                        escritorTemporario.write(linha);
                        escritorTemporario.newLine();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Substitui o arquivo original de dados pelo temporário
            substituirArquivo(arquivoDados, arquivoTemporario);
        } else {
            System.out.println("O arquivo de dados está vazio!!");
        }
    }

    // Método para substituir o arquivo original pelo temporário
    /*
     * private: O método só é acessível dentro da própria classe Admin. Outras
     * classes fora de Admin não podem chamar diretamente esse método.
     * 
     * static: O método pertence à classe Admin e não a instâncias específicas dessa
     * classe. Pode ser chamado diretamente usando o nome da classe
     * (Admin.substituirArquivo(...)) sem a necessidade de criar uma instância de
     * Admin.
     */
    private static void substituirArquivo(File arquivoOriginal, File arquivoTemporario) {
        // Deleta o arquivo original
        if (!arquivoOriginal.delete()) {
            System.out.println("Não foi possível deletar o arquivo original.");
            return;
        }

        // Renomeia o arquivo temporário para o nome original
        if (!arquivoTemporario.renameTo(arquivoOriginal)) {
            System.out.println("Não foi possível renomear o arquivo temporário.");
        }
    }

    public void acessarRelatorioVendas() {

    }

    public void acessarListaVendas() {

    }
}
