import java.sql.*;
import javax.swing.SwingUtilities;
import java.util.Map;
import java.util.HashMap;


public class Main {


    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            }
        });


        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();

    }

    public static void remover(int id) throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();
        String sql = "DELETE FROM ingredientes WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        ps.close();
        con.close();
    }


    public static void registrar(String nome, int quantidade) throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();
        String sql = "INSERT INTO ingredientes (nome, quantidade) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nome);
        ps.setInt(2, quantidade);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

    public static String mostrarLista() throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();
        String sql = "select * from ingredientes";
        Statement st = con.createStatement();
        ResultSet resultSet = st.executeQuery(sql);

        StringBuilder listaIngredientes = new StringBuilder();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            int quantidade = resultSet.getInt("quantidade");
            listaIngredientes.append("ID: ").append(id).append(", Nome: ").append(nome).append(", Quantidade: ").append(quantidade).append("\n");
        }

        return listaIngredientes.toString();
    }

    // Método verificarQuantidade modificado para retornar um Map com os valores do ID, nome e quantidade
    public static Map<String, Object> verificarQuantidade(int id) throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();
        String sql = "SELECT id, nome, quantidade FROM ingredientes WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        Map<String, Object> ingrediente = new HashMap<>();

        if (resultSet.next()) {
            int ingredienteId = resultSet.getInt("id");
            String nome = resultSet.getString("nome");
            int quantidade = resultSet.getInt("quantidade");

            ingrediente.put("id", ingredienteId);
            ingrediente.put("nome", nome);
            ingrediente.put("quantidade", quantidade);
        } else {
            throw new SQLException("Nenhum ingrediente encontrado com o ID: " + id);

        }

        // Fechar recursos
        resultSet.close();
        ps.close();
        con.close();

        return ingrediente;
    }

    public static void atualizarQuantidade(int id, int quantidade) throws SQLException, ClassNotFoundException {
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection con = jdbcConnection.getConnection();
        String sql = "UPDATE ingredientes SET quantidade = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, quantidade);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

}






