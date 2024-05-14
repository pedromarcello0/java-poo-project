import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;


public class RegistrarScreen extends JFrame {

    private JdbcConnection jdbcConnection;

    public RegistrarScreen() {

        setTitle("REGISTRAR");

        setSize(250, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(183, 176, 176));
        setVisible(true);

        jdbcConnection = new JdbcConnection();// Inicializa a conexão JDBC

        ///botao registrar ingrediente
        JButton addIng = new JButton("Adicionar Ingrediente");
        addIng.setBounds(20, 20, 200, 30);
        addIng.setFont(new Font("Arial", Font.BOLD, 14));
        addIng.setForeground(new Color(237, 241, 238));
        addIng.setBackground(new Color(224, 102, 102));
        add(addIng);
        addIng.addActionListener(this::addIngA);


        ///botao remover ingrediente
        JButton remIng = new JButton("Remover Ingrediente");
        remIng.setBounds(20, 60, 200, 30);
        remIng.setFont(new Font("Arial", Font.BOLD, 14));
        remIng.setForeground(new Color(237, 241, 238));
        remIng.setBackground(new Color(224, 102, 102));
        add(remIng);
        remIng.addActionListener(this::remIngA);

    }

    private void addIngA(ActionEvent actionEvent) {
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

    private void remIngA(ActionEvent actionEvent) {
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
    }
