
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;

public interface IUsuarioJPA {
    //GET Y SEARCH
    public Result GetAll();
    public Result GetById(int IdUsuario);
    public Result BusquedaUserWithAddress(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    //ADDS
    public Result Add(Usuario usuario);
    public Result AddAddressById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    //UPDATES
    public Result UpdateBasicById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    public Result UpdateAddressById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    //DELETES
    public Result DeleteUserById(int IdUsuario);
    public Result DeleteAddressById(int IdDireccion);
    
}
