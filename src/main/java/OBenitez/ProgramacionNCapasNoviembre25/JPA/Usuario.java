
package OBenitez.ProgramacionNCapasNoviembre25.JPA;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int IdUsuario;
    
    @Column(name = "username")
    private String Username;
    
    @Column(name = "nombre")
    private String Nombre;
    
    @Column(name = "apellidopaterno")
    private String ApellidoPaterno;
    
    @Column(name = "apellidomaterno")
    private String ApellidoMaterno;
    
    @Column(name = "email")
    private String Email;
    
    @Column(name = "password")
    private String Password;
    
    @Column(name = "fechanacimiento")
    private String FechaNacimiento;
    
    @Column(name = "sexo")
    private String Sexo;
    
    @Column(name = "telefono")
    private String Telefono;
    
    @Column(name = "celular")
    private String Celular;
    
    @Column(name = "curp")
    private String Curp;
    
    @ManyToOne
    @JoinColumn(name = "idrol")
    public Rol Rol;
    
    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();

    
    //GETTERS Y SETTERS

}
