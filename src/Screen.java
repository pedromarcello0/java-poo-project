import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class Screen extends JFrame {
    private JdbcConnection jdbcConnection;

    public Screen() {

        setTitle("teste");

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        jdbcConnection = new JdbcConnection();// Inicializa a conexão JDBC

        ///botao registrar ingrediente
        JButton regIng = new JButton("Registrar Ingrediente");
        regIng.setBounds(20, 20, 200, 30);
        regIng.setFont(new Font("Arial", Font.BOLD, 14));
        regIng.setForeground(new Color(237, 241, 238));
        regIng.setBackground(new Color(9, 10, 9));
        add(regIng);
        regIng.addActionListener(this::regIngA);


        ///botao remover ingrediente
        JButton remIng = new JButton("Remover Ingrediente");
        remIng.setBounds(20, 60, 200, 30);
        remIng.setFont(new Font("Arial", Font.BOLD, 14));
        remIng.setForeground(new Color(237, 241, 238));
        remIng.setBackground(new Color(9, 10, 9));
        add(remIng);
        remIng.addActionListener(this::remIngA);

        ///botao ver quantidade ingrediente
        JButton verQua = new JButton("Verificar Quantidade");
        verQua.setBounds(20, 100, 200, 30);
        verQua.setFont(new Font("Arial", Font.BOLD, 14));
        verQua.setForeground(new Color(237, 241, 238));
        verQua.setBackground(new Color(9, 10, 9));
        add(verQua);
        verQua.addActionListener(this::verQuaA);

        ///botao atualizar quantidade ingrediente
        JButton atuQua = new JButton("Atualizar Quantidade");
        atuQua.setBounds(20, 140, 200, 30);
        atuQua.setFont(new Font("Arial", Font.BOLD, 14));
        atuQua.setForeground(new Color(237, 241, 238));
        atuQua.setBackground(new Color(9, 10, 9));
        add(atuQua);
        atuQua.addActionListener(this::atuQuaA);

        ///botao listar ingredientes
        JButton lisIng = new JButton("Listar Ingredientes");
        lisIng.setBounds(20, 180, 200, 30);
        lisIng.setFont(new Font("Arial", Font.BOLD, 14));
        lisIng.setForeground(new Color(237, 241, 238));
        lisIng.setBackground(new Color(9, 10, 9));
        add(lisIng);
        lisIng.addActionListener(this::lisIngA);

        setVisible(true);
    }


    private void regIngA(ActionEvent actionEvent) {
        JTextField nomeField = new JTextField(10);
        JTextField quantidadeField = new JTextField(10);

        JPanel myPanel = new JPanel(new GridLayout(0, 1));
        myPanel.add(new JLabel("Nome:"));
        myPanel.add(nomeField);
        myPanel.add(Box.createVerticalStrut(10)); // a spacer
        myPanel.add(new JLabel("Quantidade:"));
        myPanel.add(quantidadeField);
        myPanel.add(Box.createVerticalStrut(10));

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Registrar Ingredientes", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String quantidadeStr = quantidadeField.getText();

            // Verifica se o usuário cancelou a entrada
            if (nome.isEmpty() || quantidadeStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Converte a quantidade para inteiro
            int quantidade;
            try {
                quantidade = Integer.parseInt(quantidadeStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantidade inválida. Por favor, insira um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Main.registrar(nome, quantidade); // Chama o método registrar() passando nome e a quantidade do ingrediente
                JOptionPane.showMessageDialog(null, "Ingrediente registrado com sucesso!", "Registrar Ingrediente", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar ingrediente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void remIngA(ActionEvent actionEvent){
        String idStr = JOptionPane.showInputDialog(null, "ID:", "Remover Ingrediente", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, insira o ID do ingrediente a ser removido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Main.remover(id);
            JOptionPane.showMessageDialog(null, "Ingrediente removido com sucesso!", "Registrar Ingrediente", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao remover ingrediente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
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






    ///verificar quantidade

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




    } ///atualizar quantidade

    private void lisIngA(ActionEvent actionEvent) {
        try {
            String listaIngredientes = Main.mostrarLista();
            JTextArea textArea = new JTextArea(listaIngredientes);
            textArea.setFont(new Font("Arial", Font.PLAIN, 14));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scrollPane, "Lista de Ingredientes", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar ingredientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}



