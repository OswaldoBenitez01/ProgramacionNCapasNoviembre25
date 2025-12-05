
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.ML.Estado;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstado{

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetEstadosByPais(int IdPais) {
        Result result = new Result();
        
        try {
            result.Correct = jdbcTemplate.execute("{CALL GetEstadosByPais(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdPais);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.Objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.Objects.add(estado);
                }
            return true;
            });
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
