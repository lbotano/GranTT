/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.sql.*;

/**
 *
 * @author Proyecto5to
 */
public class Conexion {
    public Connection cnx = null;
    public Connection obtener() throws SQLException , ClassNotFoundException
    {
        if(cnx == null)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                cnx=DriverManager.getConnection("jdbc:mysql://localhost:3306/GRANTT" , "root" , "12345");                
            }
            catch(SQLException ex)
            {
                throw new SQLException(ex);
            }
            catch(ClassNotFoundException ex)
            {
                throw new ClassCastException(ex.getMessage()); 
            }
        }
        return cnx;
    }
    public  void cerrar() throws SQLException {
        if (cnx != null) {
            cnx.close();
        }
    }
}
