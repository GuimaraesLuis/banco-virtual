import java.util.List;

public class Cliente{
    public String nome;
    public Cliente(String nom) {
       this.nome = nom;
    }
    public Cliente() {
    }
    //Metodos especiais
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
