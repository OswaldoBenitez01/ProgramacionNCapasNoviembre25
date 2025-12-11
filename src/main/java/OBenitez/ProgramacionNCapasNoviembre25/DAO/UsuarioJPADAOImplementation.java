
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{

    @Autowired
    private EntityManager entityManager;
    
//    @Autowired
//    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll() {
        
        Result result = new Result();
        ModelMapper modelMapper = new ModelMapper();
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario ASC", Usuario.class);
            List<Usuario> usuariosJPA = query.getResultList();
            result.Objects = new ArrayList<>();
            if (usuariosJPA.isEmpty()) {
                result.Correct = false;
                result.ErrorMessage = "No se encontraron usuarios";
                return result;
            }
            
            for (Usuario usuario : usuariosJPA) {
                OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML = modelMapper.map(usuario, OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario.class);
                result.Objects.add(usuarioML);
            }
            
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Add(Usuario usuario) {
        Result result = new Result();
        
        usuario.setIdUsuario(null); //Para qeu no choque con el id 
        System.out.println("ID " + usuario.getIdUsuario());

        entityManager.persist(usuario);
        entityManager.flush();
        
        
        OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion direccion = new OBenitez.ProgramacionNCapasNoviembre25.JPA.Direccion();
        direccion.setUsuario(usuario);
        direccion.setCalle(usuario.getDirecciones().get(0).getCalle());
        direccion.setNumeroInterior(usuario.getDirecciones().get(0).getNumeroInterior());
        direccion.setNumeroExterior(usuario.getDirecciones().get(0).getNumeroExterior());
        direccion.setColonia(usuario.getDirecciones().get(0).getColonia());
        System.out.println("ID despues de persist" + direccion.getUsuario().getIdUsuario());
        
        entityManager.persist(direccion);



        result.Correct = true;
        
        return result;
    }
    
}
