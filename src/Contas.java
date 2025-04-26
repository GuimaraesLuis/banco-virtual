import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Contas {
    public ContaBancaria pesquisarConta(String numeroConta, File arquivo, ObjectMapper conversor) {

        try {
            if (!arquivo.exists() || arquivo.length() == 0) {
                System.out.println("Arquivo não encontrado ou vazio.");
                return null;
            }

            List<ContaBancaria> contas = conversor.readValue(arquivo, new TypeReference<List<ContaBancaria>>() {
            });

            for (ContaBancaria conta : contas) {
                if (conta.getNumConata().equals(numeroConta)) {
                    return conta;
                }
            }

            return null;

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return null;
        }
    }

    public void atualizarArrey(ContaBancaria c, File arquivo, ObjectMapper conversor) throws IOException {
        ArrayNode arrey = (ArrayNode) conversor.readTree(arquivo);
        for (JsonNode node : arrey) {
            if (node.get("numConata").asText().equals(c.getNumConata())) {
                ((ObjectNode) node).put("saldo", c.getSaldo());
            }
        }
        conversor.writerWithDefaultPrettyPrinter().writeValue(arquivo, arrey);
        System.out.println("Conta atualizado com sucesso!");
    }

    public void fecharConta(ContaBancaria c, File arquivo, ObjectMapper conversor) throws IOException {
        ArrayNode arrey = (ArrayNode) conversor.readTree(arquivo);
        for (JsonNode node : arrey) {
            if (node.get("numConata").asText().equals(c.getNumConata())) {
                ((ObjectNode) node).put("saldo", c.getSaldo());
                ((ObjectNode) node).put("numConata", c.getNumConata());
                ((ObjectNode) node).put("ativa", c.getAtiva());
                ((ObjectNode) node).put("tipo", c.getTipo());
            }
        }
        conversor.writerWithDefaultPrettyPrinter().writeValue(arquivo, arrey);
    }

    public void RecuperarConta(ContaBancaria conta, String tipoConta, String numeroConta, String name, File arquivo, ObjectMapper conversor) throws IOException {
        try {
            if (!arquivo.exists() || arquivo.length() == 0) {
                System.out.println("Arquivo não encontrado ou vazio.");
            }
            ArrayNode node = (ArrayNode) conversor.readTree(arquivo);
            if (conta.getNumConata().equals(numeroConta) && conta.getDono().getNome().equals(name)) {
                for (JsonNode nodes : node) {
                    ((ObjectNode) nodes).put("ativa", "true");
                    ((ObjectNode) nodes).put("tipo", tipoConta);
                    System.out.println("Conta Recuperada com Sucesso!!");
                }
            }else{
                System.out.println("Erro! Nome ou Numero da conta errado!");
            }
            conversor.writerWithDefaultPrettyPrinter().writeValue(arquivo, node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}





