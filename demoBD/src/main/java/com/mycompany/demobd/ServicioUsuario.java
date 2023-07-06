/*
                      * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.demobd;

import static com.mycompany.demobd.DemoBD.getConection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lulu
 */
public class ServicioUsuario extends Servicio implements ICrud<UsuarioTO> {

    public void insertar(UsuarioTO usuarioTO) throws SQLException, Exception {

        PreparedStatement ps = null;
        Connection conn = super.getConection();
        try {

            ps = super.getConection().prepareStatement("INSERT INTO usuario VALUES(?,?,?,?,?)");
            ps.setString(1, usuarioTO.getCorreo());
            ps.setString(2, usuarioTO.getClave());
            ps.setString(3, usuarioTO.getNombre());
            ps.setString(4, usuarioTO.getApellido());
            ps.setString(5, "activo");
            ps.executeUpdate();

        } catch (Exception e) {

        } finally {

            super.cerrar(ps);
            super.cerrar(conn);
        }
    }
    
     public void modificar(UsuarioTO usuarioTO) throws SQLException, Exception {

        PreparedStatement ps = null;
        Connection conn = super.getConection();
        try {

            ps = super.getConection().prepareStatement("UPDATE proyecto2.usuario SET nombre = ?,clave = ?, apellido = ? WHERE correo = ?");
            ps.setString(1, usuarioTO.getNombre());
            ps.setString(2, usuarioTO.getClave());
            ps.setString(3, usuarioTO.getApellido());
            ps.setString(4, usuarioTO.getCorreo());
            
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            super.cerrar(ps);
            super.cerrar(conn);
        }
    }

    /**
     *
     * @return
     * @throws Exception
     */
    
    public List<UsuarioTO> demeUsuarios() throws SQLException,Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = super.getConection();

        List<UsuarioTO> retorno = new ArrayList<UsuarioTO>();
        try {
            ps = getConection().prepareStatement("SELECT correo,nombre,apellido,estado FROM USUARIO");
            rs = ps.executeQuery();
            while (rs.next()) {
               
                String correo = rs.getString("correo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String estado = rs.getString("estado");
                UsuarioTO usuarioTO = new UsuarioTO(correo,nombre,apellido,estado);
                retorno.add(usuarioTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.cerrar(conn);
            super.cerrar(rs);
            super.cerrar(ps);
        }
        return retorno;

    }


    public UsuarioTO demeUsuario(int pk) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = super.getConection();

        UsuarioTO retorno = null;
        try {
            ps = getConection().prepareStatement("SELECT ID,NOMBRE FROM USUARIO WHERE id = ?");
            ps.setInt(1, pk);
            rs = ps.executeQuery();
            if (rs.next()) {

                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                retorno = new UsuarioTO(id, nombre);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.cerrar(conn);
            super.cerrar(rs);
            super.cerrar(ps);
        }
        return retorno;

    }
    
    public UsuarioTO demeUsuario(String correo, String clave) throws SQLException, Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = super.getConection();

        UsuarioTO retorno = null;
        try {
            ps = getConection().prepareStatement("SELECT correo,clave,rol FROM USUARIO WHERE correo = ? and clave = ?");
            ps.setString(1, correo);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.next()) {

                correo = rs.getString("correo");
                 clave = rs.getString("clave");
                  String rol = rs.getString("rol");
                retorno = new UsuarioTO(correo, clave,rol);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.cerrar(conn);
            super.cerrar(rs);
            super.cerrar(ps);
        }
        return retorno;

    }
   
}