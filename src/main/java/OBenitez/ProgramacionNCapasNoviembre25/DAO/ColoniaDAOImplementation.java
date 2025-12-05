
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.ML.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColonia{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetColoniasByMunicipio(int idMunicipio) {
        Result result = new Result();
        
        try {
            result.Correct = jdbcTemplate.execute("{CALL GetColoniasByMunicipio(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, idMunicipio);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.Objects = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    result.Objects.add(colonia);
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
