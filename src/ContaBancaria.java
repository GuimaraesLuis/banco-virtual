import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Random;
import java.util.Scanner;
public class ContaBancaria  {
    private double saldo;
    private String numConata;
    private boolean ativa;
    private Cliente dono;
    private String tipo;
    Scanner sc = new Scanner(System.in);

    public ContaBancaria(Cliente dono, String tipo) {
        this.saldo = 0;
        this.numConata = numConata;
        this.dono = dono;
        this.tipo = tipo;
    }

    public ContaBancaria() {

    }
    public ContaBancaria login(String numeroConta, File arquivo,ObjectMapper mapper, Contas contas ){
        ContaBancaria banco = contas.pesquisarConta(numeroConta,arquivo,mapper);
        if(banco != null){
            if (banco.getNumConata().equals(numeroConta)) {
                return banco;
            }
        }
        return null;
    }


    Random random = new Random();
    public void criarConta(Cliente c){
                ativa = true;
                int s = 0;
                int numero = 0;
                for (int i = 0; i < 10; i++) {
                    int v = random.nextInt(1000);
                    int num = random.nextInt(10);
                    numero = num;
                    s += v;

                String string = String.format("%d-%d", s, numero);
                this.setNumConata(string);

            }

    }

    public void fecharConta(){
        if(ativa = true){
            this.setSaldo(0);
            this.setDono(null);
            this.setTipo("Fechada");
            this.ativa = false;
        }
    }
    public void trasnferir(ContaBancaria t, int valor){
            if(this.saldo >= valor){
                this.setSaldo(getSaldo() - valor);
                t.setSaldo(t.getSaldo() + valor);
                System.out.println(valor +"$ Foi enviado para a conta de " + t.getDono().getNome());
            }else{
                System.out.println("Valor inssuficiente!!");
        }
    }

    public void depositar(double valor){
        if(ativa == true) {
            this.setSaldo(getSaldo() + valor);
            System.out.println(dono.getNome() + " depositou " + valor);
            }else{
            System.out.println("Voce nn tem uma conta");
        }
        }

    public void sacar(double valor){
        if(ativa == true) {
            if (saldo >= valor) {
                this.setSaldo(getSaldo() - valor);
                System.out.println("Você sacou " + valor);
            } else {
                System.out.println("Você não tem saldo suficiente!!");
            }
        }else{
            System.out.println("Voce nn tem uma conta!!");
        }
    }

    //metodos especiaias
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNumConata() {
        return numConata;
    }

    public void setNumConata(String numConata) {
        this.numConata = numConata;

    }

    public boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Cliente getDono() {
        return dono;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "ContaBancaria{" +
                "saldo=" + saldo +
                ", numConata=" + numConata +
                ", dono=" + dono +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
