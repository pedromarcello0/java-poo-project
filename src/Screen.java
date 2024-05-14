import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class Screen extends JFrame {
    private JdbcConnection jdbcConnection;
    private JTextArea lisIngt; // Declare lisIngt como um campo da classe

    public Screen() {

        setTitle("ESTOQUE");

        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(84, 84, 84));

        jdbcConnection = new JdbcConnection();// inicializa a conexão JDBC

        ///botao registrar ingrediente
        JButton regIng = new JButton("Registrar Ingrediente");
        regIng.setBounds(20, 20, 200, 30);
        regIng.setFont(new Font("Arial", Font.BOLD, 14));
        regIng.setForeground(new Color(255, 255, 255));
        regIng.setBackground(new Color(224, 102, 102));
        add(regIng);
        regIng.addActionListener(this::regIngA);

        ///botao ver quantidade ingrediente
        JButton verQua = new JButton("Verificar Quantidade");
        verQua.setBounds(20, 60, 200, 30);
        verQua.setFont(new Font("Arial", Font.BOLD, 14));
        verQua.setForeground(new Color(255, 255, 255));
        verQua.setBackground(new Color(224, 102, 102));
        add(verQua);
        verQua.addActionListener(this::verQuaA);

        ///botao atualizar quantidade ingrediente
        JButton atuQua = new JButton("Atualizar Quantidade");
        atuQua.setBounds(20, 100, 200, 30);
        atuQua.setFont(new Font("Arial", Font.BOLD, 14));
        atuQua.setForeground(new Color(255, 255, 255));
        atuQua.setBackground(new Color(224, 102, 102));
        add(atuQua);
        atuQua.addActionListener(this::atuQuaA);

        ///botao listar ingredientes
        JButton lisIng = new JButton("Listar");
        lisIng.setBounds(20, 140, 200, 30);
        lisIng.setFont(new Font("Arial", Font.BOLD, 14));
        lisIng.setForeground(new Color(255, 255, 255));
        lisIng.setBackground(new Color(224, 102, 102));
        add(lisIng);
        lisIng.addActionListener(this::lisIngA);

        // Inicializa lisIngt
        lisIngt = new JTextArea("Clique em Listar, para atualizar a lista");
        lisIngt.setBounds(230, 20, 290, 360);
        add(lisIngt);

        setVisible(true);
    }

    ///registrar ingrediente
    private void regIngA(ActionEvent actionEvent){
        new RegistrarScreen();
    }
    ///verificar quantidade
    private void verQuaA(ActionEvent actionEvent) {
        String idStr = JOptionPane.showInputDialog(null, "ID:", "Ver quantidade do Ingrediente", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, insira o ID do ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número inteiro." + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Map<String, Object> ingrediente = Main.verificarQuantidade(id);
            if (ingrediente != null && !ingrediente.isEmpty()) {
                int ingredienteId = (int) ingrediente.get("id");
                String nome = (String) ingrediente.get("nome");
                int quantidade = (int) ingrediente.get("quantidade");
                JOptionPane.showMessageDialog(null, "ID: " + ingredienteId + "\nNome: " + nome + "\nQuantidade: " + quantidade, "Detalhes do Ingrediente", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar quantidade do ingrediente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    ///atualizar quantidade
    private void atuQuaA(ActionEvent actionEvent) {
        String idStr = JOptionPane.showInputDialog(null, "ID:", "Atualizar quantidade do Ingrediente", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, insira o ID do ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String quaStr = JOptionPane.showInputDialog(null, "Quantidade:", "Atualizar quantidade do Ingrediente", JOptionPane.QUESTION_MESSAGE);
        if (quaStr == null || quaStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, insira a nova quantidade do ingrediente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número inteiro." + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(quaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida. Por favor, insira um número inteiro." + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Main.atualizarQuantidade(id, quantidade);
            JOptionPane.showMessageDialog(null, "Quantidade do ingrediente atualizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar quantidade do ingrediente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

    }
    ///listar quantidade
    private void lisIngA(ActionEvent actionEvent) {
        try {
            String listaIngredientes = Main.mostrarLista();
            lisIngt.setText(listaIngredientes); // Atualiza o conteúdo do JTextArea com a lista de ingredientes
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar ingredientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
