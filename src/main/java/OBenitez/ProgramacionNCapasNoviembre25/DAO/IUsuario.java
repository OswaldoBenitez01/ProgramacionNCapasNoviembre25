
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario;
import java.util.List;

public interface IUsuario {
    public Result GetAll();
    public Result Add(Usuario usuario);
    public Result GetById(int IdUsuario);
    public Result GetByIdBasicInfo(int IdUsuario);
    public Result GetAddressById(int IdUsuario, int IdDireccion);
    public Result UpdateBasicById(Usuario usuario);
    public Result UpdateStatusById(Integer IdUsuario, Integer status);
    public Result AddAddressById(Usuario usuario);
    public Result UpdateAddressById(Usuario usuario);
    public Result DeleteById(int IdUsuario);
    public Result DeleteAddressById(int IdDireccion);
    public Result AddAll(List<Usuario> usuarios);
    public Result UpdatePhoto(int IdUsuario, String Foto);
    //Busqueda
    public Result BusquedaUserWithAddress(Usuario usuario);
}
