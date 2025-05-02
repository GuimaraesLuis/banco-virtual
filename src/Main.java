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
            System.out.print("👤 " + accont.getDono().getNome());
            System.out.print(" | 💵 " + accont.getSaldo());
            System.out.print(" | 🔢 " + accont.getNumConata());
            System.out.print(" | 🏦 " + accont.getTipo());
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
                    String setTypeAccont;
                    System.out.print("Vamos Criar Sua Conta Em nosso Banco!\nPara Começar Qual seu nome: ");
                    String name = scan.nextLine();
                    Cliente pessoa = new Cliente(name);
                    while(true){
                        System.out.println("Qual Tipo de Conta Você deseja Criar:\n" +
                                "1️⃣ Conta Poupança 🏦\n" +
                                "2️⃣ Conta Corrente 🏛️");
                        String typeAccont = scan.nextLine();
                        if(typeAccont.equals("1")){
                            setTypeAccont = "Conta Poupança";
                            break;
                        } else if (typeAccont.equals("2")) {
                            setTypeAccont = "Conta Corrente";
                            break;
                        }else{
                            System.out.println("Opção invalida. Tente Novamente!");
                        }
                    }
                    accont = new ContaBancaria(pessoa, setTypeAccont);
                    accont.criarConta(pessoa);
                    System.out.println("Criando Conta...");
                    break;
                case "2":
                        System.out.print("🔢 Digite o número da sua conta: ");
                        String pesquisar = scan.nextLine();
                        ContaBancaria c = accont.login(pesquisar, arquivo, conversor, conta);
                        if(c != null){
                            if (c.getAtiva() == true) {
                                accont = c;
                            }else{
                                System.out.println("Conta inesistente ou Conta fechada!");
                            }
                        }else{
                            System.out.println("Conta inesistente ou Conta fechada!");
                        }
                    break;
                case "3":
                    System.out.print("🔢 Digite o numero da conta Recebedora: ");
                    String recebedor = scan.nextLine();
                    System.out.print("💸 Valor a Ser Enviado: ");
                    String val = scan.nextLine();
                    if (val.matches("\\d+")) {
                        int valor = Integer.parseInt(val);
                        ContaBancaria contaRecebedor = conta.pesquisarConta(recebedor, arquivo, conversor);
                        if (contaRecebedor != null) {
                            accont.trasnferir(contaRecebedor, valor);
                            conta.atualizarArrey(contaRecebedor, arquivo, conversor);
                        } else {
                            System.out.println("Conta Inesistente!");
                        }
                    }else{
                        System.out.println("Apenas Numeros!");
                    }
                    break;
                case "4":
                    System.out.print("💰 Valor para Depositar: ");
                    String deposito = scan.nextLine();
                    if(deposito.matches("\\d+")){
                        int valor = Integer.parseInt(deposito);
                        accont.depositar(valor);
                    }else{
                        System.out.println("Impossivel Fazer Deposito!");
                    }
                    break;
                case "5":
                    System.out.print("🔢 Numero da conta exclída: ");
                    String numero = scan.nextLine();
                    String newTipo;
                    while(true){
                        System.out.print("🏦 Tipo da conta:\n" +
                                "1️⃣ Conta Poupança 🏦\n" +
                                "2️⃣ Conta Corrente 🏛️\n" +
                                "➡\uFE0F ");
                        String tipo = scan.nextLine();
                        if(tipo.equals("1")){
                            newTipo = "Conta Corrente";
                            break;
                        } else if (tipo.equals("2")) {
                            newTipo = "Conta Poupança";
                            break;
                        }else{
                            System.out.println("Opção invalida. Tente Novamente!");
                        }
                    }
                    System.out.print("🗑 Nome da conta excluída: ");
                    String nome = scan.nextLine();
                    ContaBancaria contass = conta.pesquisarConta(numero, arquivo, conversor);
                    if(contass != null) {
                        if (contass.getAtiva() == false || contass.getDono().getNome().equals(nome)){
                            conta.RecuperarConta(contass, newTipo, numero, nome, arquivo, conversor);
                        }
                    }else{
                        System.out.println("Conta Inesistente!");
                    }
                    break;
                case "6" :
                    System.out.print("💵 Valor Para saque: ");
                    String saque = scan.nextLine();
                    if(saque.matches("\\d+")) {
                        int valor = Integer.parseInt(saque);
                        accont.sacar(valor);
                    }else{
                        System.out.println("Impossivel fazer Saque");
                    }
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
        if(conta.pesquisarConta(accont.getNumConata(), arquivo, conversor) == null && accont.getAtiva() != false){
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
