
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import com.mycompany.demobd.DemoBD;
import com.mycompany.demobd.ICrud;
import com.mycompany.demobd.Servicio;
import com.mycompany.demobd.ServicioUsuario;
import com.mycompany.demobd.TesterServicio;
import com.mycompany.demobd.UsuarioTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lulu
 */
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private String correo;
    private String rol;
    private String clave;
    private String nombre;
    private String apellidos;
    private String estado;
    private List<UsuarioTO> usuarios;
    private ServicioUsuario su;
    private boolean esNuevo = false;
    private UsuarioTO selectedUsuario = new UsuarioTO();

    

    public void insertar() {

        System.out.println("ESTOY EN EL INGRESAR");
        System.out.println("EL VALOR DE USUARIO ES:" + this.getCorreo());
        System.out.println("EL VALOR DE CLAVE ES:" + this.getClave());
        boolean continuar = true;
        if (this.getCorreo() == null || this.getCorreo().equals("")) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "El usuario no es correcto"));
            continuar = false;
        }
        if (this.getClave() == null || this.getClave().equals("")) {

            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "La clave no es correcto"));
            continuar = false;
        }
        if (continuar) {
            validacionUsuario();

        }

    }

    @PostConstruct
    public void mostrarUsuarios() {
        try {
            ServicioUsuario su = new ServicioUsuario();
            this.usuarios = new ServicioUsuario().demeUsuarios();
        } catch (Exception e) {
        }

    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public UsuarioTO validacionUsuario() {

        UsuarioTO retorne = new UsuarioTO(correo, clave);

        try {

            ServicioUsuario su = new ServicioUsuario();

            retorne = su.demeUsuario(correo, clave);
            if (retorne != null) {
                String rol= retorne.getRol();
                if (rol.equals("administrador")){
                    this.redireccionar("/faces/bienvenida.xhtml");
                
            }
                else if (rol.equals("usuario")){
                 this.redireccionar("/faces/bienvenidausuario.xhtml");
            }
            

            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Existe en la base de datos", "No Existe en la base de datos"));

            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "ERROR"));

            e.printStackTrace();
        }
        return retorne;

    }

    public void saveUser() {
        System.out.println("Estoy salvando al usuario");
        try {
            ServicioUsuario su = new ServicioUsuario();
            su.insertar(selectedUsuario);
            this.usuarios.add(selectedUsuario);//para simular       
            
            this.esNuevo = false;
            this.selectedUsuario = new UsuarioTO();
            PrimeFaces.current().executeScript("PF('manageUserDialogAgregar').hide()");
            mostrarUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //---this.servicioUsuario.listarUsuarios();

    }
    
    public void updateUser(){
    System.out.println("Estoy modificando al usuario");
        try {
            ServicioUsuario su = new ServicioUsuario();
            su.modificar(selectedUsuario);
            this.esNuevo = false;
            this.selectedUsuario = new UsuarioTO();
            PrimeFaces.current().executeScript("PF('manageUserDialogEdit').hide()");
            mostrarUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
 public void openNew() {
        this.esNuevo = true;
        this.selectedUsuario = new UsuarioTO();
    }
    public LoginController() {
    }

    public LoginController(String usuario, String clave) {
        this.correo = usuario;
        this.clave = clave;
    }

    public LoginController(String correo, String nombre, String apellidos, String estado, ArrayList<UsuarioTO> usuarios) {
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.estado = estado;
        this.usuarios = usuarios;
    }

   

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<UsuarioTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioTO> usuarios) {
        this.usuarios = usuarios;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public UsuarioTO getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(UsuarioTO selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

}
