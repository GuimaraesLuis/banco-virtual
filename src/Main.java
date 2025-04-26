import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        ObjectMapper conversor = new ObjectMapper();
        File arquivo = new File("contas.json");
        //Criação da conta
        ContaBancaria accont = new ContaBancaria();
        Contas conta = new Contas();


        String type = "";
        while (!type.equals("8")) {
        if(accont.getDono() == null){
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║                BANCO VIRTUAL                 ║");
            System.out.print("╚══════════════════════════════════════════════╝\n");
            System.out.print(
                    "1️⃣  Criar Conta \uD83D\uDEE0\uFE0F\n" +
                            "2️⃣  Login 🔐\n" +
                            "5️⃣  Recuperar Conta ♻️\n" +
                            "8️⃣  Sair 🚪\n" +
                            "════════════════════════════════════════════════\n" +
                            "➡\uFE0F "
            );
            }else {
            System.out.println("╔══════════════════════════════════════════════╗");
            System.out.println("║                BANCO VIRTUAL                 ║");
            System.out.print("╚══════════════════════════════════════════════╝\n");
            System.out.print("\uD83D\uDC64 " + accont.getDono().getNome());
            System.out.print(" | 💵 " + accont.getSaldo());
            System.out.print(" | \uD83D\uDD22 " + accont.getNumConata());
            System.out.print(" | \uD83C\uDFE6 " + accont.getTipo());
            System.out.print(
                            "\n3️⃣  Transferência 💸\n" +
                            "4️⃣  Depósito 💰\n" +
                            "6️⃣  Saque 🏧\n" +
                            "7️⃣  Fechar Conta ❌🏦\n" +
                            "8️⃣  Sair 🚪\n" +
                            "════════════════════════════════════════════════\n" +
                            "➡\uFE0F "
            );}
            type = scan.nextLine();
            switch (type) {
                case "1":
                    System.out.print("Vamos Criar Sua Conta Em nosso Banco!\nPara Começar Qual seu nome: ");
                    String name = scan.nextLine();
                    Cliente pessoa = new Cliente(name);
                    System.out.println("Qual Tipo de Conta Você deseja Criar:\n" +
                            "1)Conta Poupança\n2)Conta Corrente");
                    String typeAccont = scan.nextLine();
                    switch (typeAccont){
                        case "1":
                            typeAccont = "Conta Poupança";
                            break;
                        case "2":
                            typeAccont = "Conta Corrente";
                            break;
                        default:
                            System.out.println("Opção Invalida. Tente Novamente!");
                    }

                    accont = new ContaBancaria(pessoa, typeAccont);
                    accont.criarConta(pessoa);
                    System.out.println("Criando Conta...");

                    break;
                case "2":
                        System.out.print("Digite o numero da sua conta: ");
                        String pesquisar = scan.nextLine();
                        ContaBancaria c = accont.login(pesquisar, arquivo, conversor, conta);
                    if(c.getAtiva() == true) {
                        accont = c;
                    }else{
                        System.out.println("Conta inesistente ou Conta fechada!");
                    }
                    break;
                case "3":
                    System.out.print("Digite o numero da conta Recebedora: ");
                    String recebedor = scan.nextLine();
                    System.out.print("Valor a Ser Enviado: ");
                    int val = scan.nextInt();
                    scan.nextLine();
                    ContaBancaria contaRecebedor = conta.pesquisarConta(recebedor,arquivo, conversor);
                    accont.trasnferir(contaRecebedor, val);
                    conta.atualizarArrey(contaRecebedor , arquivo, conversor);
                    break;
                case "4":
                    System.out.print("Valor Para depositar: ");
                    int deposito = scan.nextInt();
                    scan.nextLine();
                    accont.depositar(deposito);
                    break;
                case "5":
                    System.out.print("Numero da conta exclída: ");
                    String numero = scan.nextLine();
                    System.out.print("Tipo da conta: ");
                    String tipo = scan.nextLine();
                    System.out.print("Nome da conta excluída: ");
                    String nome = scan.nextLine();
                    ContaBancaria contass = conta.pesquisarConta(numero, arquivo, conversor);
                    if(contass.getAtiva() == false) {
                        conta.RecuperarConta(contass, tipo, numero, nome, arquivo, conversor);
                    }
                    break;
                case "6" :
                    System.out.print("Valor Para saque: ");
                    double saque = scan.nextDouble();
                    scan.nextLine();
                    accont.sacar(saque);
                    break;
                case "7":
                    accont.fecharConta();
                    conta.fecharConta(accont, arquivo, conversor);
                    System.out.println("Fechando conta...");
                    type = "8";
                    break;
                case "8":
                    System.out.println("Saindo....");
                    break;
                default:
                    System.out.println("Opção invalida. Tente Novamente");
            }
        }
        if(conta.pesquisarConta(accont.getNumConata(), arquivo,conversor) != null && accont.getAtiva() == true){
            conta.atualizarArrey(accont, arquivo, conversor);

        }
        //implementação de todas contas criadas dentro de um só arquivo
        if(conta.pesquisarConta(accont.getNumConata(), arquivo,conversor) == null){
        List<ContaBancaria> acont = new ArrayList<>();
        if(arquivo.exists() && arquivo.length() > 0) {
            try {
                 acont = conversor.readValue(arquivo, new TypeReference<List<ContaBancaria>>() {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        acont.add(accont);
        try {
            conversor.writerWithDefaultPrettyPrinter().writeValue(arquivo, acont);
            System.out.println("Conta adicionada com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar JSON: " + e.getMessage());
        }

        }
    }
}
