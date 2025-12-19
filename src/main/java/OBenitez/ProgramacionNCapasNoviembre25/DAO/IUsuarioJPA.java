
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.JPA.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import java.util.List;

public interface IUsuarioJPA {
    //GET Y SEARCH
    public Result GetAll();
    public Result GetById(int IdUsuario);
    public Result BusquedaUserWithAddress(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    //ADDS
    public Result Add(Usuario usuario);
    public Result AddAll(List<Usuario> usuarios);
    public Result AddAddressById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    //UPDATES
    public Result UpdateBasicById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    public Result UpdateAddressById(OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario usuarioML);
    public Result UpdateStatusById(Integer IdUsuario, Integer status);
    public Result UpdatePhoto(Integer IdUsuario, String Foto);
    //DELETES
    public Result DeleteUserById(int IdUsuario);
    public Result DeleteAddressById(int IdDireccion);
}
