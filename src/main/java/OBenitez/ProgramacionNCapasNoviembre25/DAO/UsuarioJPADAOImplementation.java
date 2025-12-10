
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll() {
        
        Result result = new Result();
    
        try {
            
            TypedQuery<Usuario> query = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuariosJPA = query.getResultList();

            result.Objects = new ArrayList<>();

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
    
}
