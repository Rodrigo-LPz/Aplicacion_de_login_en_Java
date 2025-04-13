package dbloginapp;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;


public class DBloginApp{
    public static void main(String[] args){
        // Creamos las 3 propiedades constantes de la clase (url o direcci?n de la base de datos, usuario y contrase?a).
        String db_url = "jdbc:mysql://localhost:3306";
        String db_user = "root";
        String db_password = "root";
        
        // Declaramos la/s variable/s.
        Scanner user = new Scanner(System.in);
        String username = "";
        String password = "";
        
        // Declaramos los objetos "Connection" para poder conectarse a la base de datos y "Statement" para declarar posteriormente las sentencias con las que interactuar con dicha base.
        Connection connection = null;
        Statement stmt = null;
        
        // Declaramos el objeto "ResultSet", definido dentro del "try" para poder obtener el resultado dado por la consulta a la base de datos y "Statement" para declarar posteriormente las sentencias con las que interactuar con dicha base.
        ResultSet resultS = null;
        
        // Solicitamos los datos de entrada al usuario.
        System.out.print("Please, enter your username: ");
        username = user.nextLine();
        
        System.out.print("Now, create a password: ");
        password = user.nextLine();
        
        try{
            // Cargar el Driver de la base de datos 'MySQL'.
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado correctamente");
            
            // Conectarse a la base de datos 'MySQL'.
            connection = DriverManager.getConnection(db_url, db_user, db_password); /* Creamos el objeto "connection". */
            System.out.println("Conexión establecida con la base de datos.");
            
            //Realizamos las distintas consultas SQL.
            String consulta = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = connection.prepareStatement(consulta);
            // Tanto el "1" como el "2" funcionarán como array, sustituyéndose su valor por los (?) del String "Consulta". Eto funcionará como una especie de array
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            // Muestreo de la consulta.
            System.out.println("Consulta SQL ejecutada: SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "';");
            
            // Ejecutamos la consulta.
            rs = pstmt.executeQuery();
            
            // Verificamos si la consulta encontró el usuario, es decir, validamos el resultado.
            if (rs.next()) {
                System.out.println("Inicio de sesión correcto.");
            } else {
                System.out.println("Usuario o contraseña incorrectos.");
            }
            
        } catch (ClassNotFoundException ex){ /* Capturamos el posible error que puede surgir al cargar el driver, es decir, en "Class.forName". */
            System.err.println("Error al cargar el driver del SGBD: " + ex.getMessage());
        } catch (SQLException ex){/* Capturamos el posible error que puede surgir al cargar o intentar conectarse a la base de datos. */
            System.err.println("Error al conectarse a la base de datos o al realizar la consulta/sentencia: " + ex.getMessage());
        } finally{ /* Creamos o capturamos la ?ltima sentencia, es decir, el cierre de la base de datos tras terminar de utilizarla. */
            try{
                if (stmt != null){
                    stmt.close();
                }
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException ex){
                System.out.println("Error al cerrar o finalizar la conexi?n." + ex.getMessage());
            }
        }
    }
}
