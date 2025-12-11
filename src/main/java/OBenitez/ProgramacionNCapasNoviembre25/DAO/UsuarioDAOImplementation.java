
package OBenitez.ProgramacionNCapasNoviembre25.DAO;

import OBenitez.ProgramacionNCapasNoviembre25.ML.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Estado;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Municipio;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Pais;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Rol;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOImplementation implements IUsuario{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(){
    
        Result result = new Result();
        
        try{
            result.Correct = jdbcTemplate.execute("{CALL GetAllUser(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.Objects = new ArrayList<>();
                
                while (resultSet.next()) {   
                    int IdUsuario = resultSet.getInt("IdUsuario");
                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result. Objects.size() - 1)).getIdUsuario()== IdUsuario) {
                        Direccion Direccion = new Direccion();
                        //DIRECCION
                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Usuario usuario = ((Usuario) result.Objects.get(result. Objects.size() - 1));
                        usuario.Direcciones.add(Direccion);
                        //COLONIA
                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        //MUNIcipIO
                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                        //ESTADO
                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        //PAIS
                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                    } else {
                        Usuario usuario = new Usuario();
                        //USUARIO
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("Nombre"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        Date fecha = resultSet.getDate("FechaNacimiento");
                        usuario.setFechaNacimiento(fecha);
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        if (resultSet.getString("Celular") != null) {usuario.setCelular(resultSet.getString("Celular"));}
                        if (resultSet.getString("Curp") != null) {usuario.setCurp(resultSet.getString("Curp"));}
                        //ROL
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombre(resultSet.getString("Puesto"));
                        //DIRECCION
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            usuario.Direcciones = new ArrayList<>();
                            Direccion Direccion = new Direccion();
                            Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            Direccion.setCalle(resultSet.getString("Calle"));
                            Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            usuario.Direcciones.add(Direccion);
                            //COLONIA
                            Direccion.Colonia = new Colonia();
                            Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                            Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            //MUNICIPIO
                            Direccion.Colonia.Municipio = new Municipio();
                            Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                            //ESTADO
                            Direccion.Colonia.Municipio.Estado = new Estado();
                            Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                            //PAIS
                            Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                            
                        }
                        result.Objects.add(usuario);
                    }
                }
                
                return true;
            });
        }
        catch(Exception ex){
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result Add(Usuario usuario){
        Result result = new Result();
        
        try{
            result.Correct = jdbcTemplate.execute("{CALL AddUserWithAddress(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                //Usuario
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                //callableStatement.setDate(4, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setString(6, usuario.getPassword());
                
                callableStatement.setInt(7, usuario.Rol.getIdRol());
                callableStatement.setString(8, usuario.getUsername());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.getCurp());
                //Direccion
                callableStatement.setString(13, usuario.Direcciones.get(0).getCalle());
                callableStatement.setString(14, usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(15, usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt(16, usuario.Direcciones.get(0).Colonia.getIdColonia());
                
                callableStatement.executeUpdate();
                return true;
            });
        }
        catch(Exception ex){
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL GetUserById(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.Objects = new ArrayList<>();
                
                while (resultSet.next()) {                    
                    int IdUsuarioIngresar = resultSet.getInt("IdUsuario");
                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result. Objects.size() - 1)).getIdUsuario()== IdUsuarioIngresar) {
                        Direccion Direccion = new Direccion();
                        //DIRECCION
                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Usuario usuario = ((Usuario) result.Objects.get(result. Objects.size() - 1));
                        usuario.Direcciones.add(Direccion);
                        //COLONIA
                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        //MUNIcipIO
                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                        //ESTADO
                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        //PAIS
                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                    } else {
                        Usuario usuario = new Usuario();
                        //USUARIO
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setNombre(resultSet.getString("Nombre"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        Date fecha = resultSet.getDate("FechaNacimiento");
                        usuario.setFechaNacimiento(fecha);
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("Password"));
                        usuario.setUsername(resultSet.getString("Username"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        if (resultSet.getString("Celular") != null) {usuario.setCelular(resultSet.getString("Celular"));}
                        if (resultSet.getString("Curp") != null) {usuario.setCurp(resultSet.getString("Curp"));}
                        //ROL
                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombre(resultSet.getString("Puesto"));
                        //DIRECCION
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            usuario.Direcciones = new ArrayList<>();
                            Direccion Direccion = new Direccion();
                            Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            Direccion.setCalle(resultSet.getString("Calle"));
                            Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            usuario.Direcciones.add(Direccion);
                            //COLONIA
                            Direccion.Colonia = new Colonia();
                            Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                            Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            //MUNICIPIO
                            Direccion.Colonia.Municipio = new Municipio();
                            Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                            //ESTADO
                            Direccion.Colonia.Municipio.Estado = new Estado();
                            Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                            //PAIS
                            Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                        }
                        result.Objects.add(usuario);
                    }
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

    @Override
    public Result GetByIdBasicInfo(int IdUsuario) {
        Result result = new Result();
        
        try {
            result.Correct = jdbcTemplate.execute("{CALL GetUserByIdBasicInfo(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                while (resultSet.next()) {                
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    Date fecha = resultSet.getDate("FechaNacimiento");
                    usuario.setFechaNacimiento(fecha);
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setUsername(resultSet.getString("Username"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCelular(resultSet.getString("Celular"));
                    usuario.setCurp(resultSet.getString("Curp"));
                    //ROL
                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuario.Rol.setNombre(resultSet.getString("Puesto"));

                    result.Object = usuario;
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

    @Override
    public Result GetAddressById(int IdUsuario, int IdDireccion) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL GetAddressByUserId(?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, IdUsuario);
                callableStatement.setInt(3, IdDireccion);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                while (resultSet.next()) {                    
                    Usuario usuario = new Usuario();
                    //USUARIO
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setNombre(resultSet.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    //DIRECCION
                    usuario.Direcciones = new ArrayList<>();
                    Direccion Direccion = new Direccion();
                    Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    Direccion.setCalle(resultSet.getString("Calle"));
                    Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    usuario.Direcciones.add(Direccion);
                    //COLONIA
                    Direccion.Colonia = new Colonia();
                    Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                    Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    //MUNICIPIO
                    Direccion.Colonia.Municipio = new Municipio();
                    Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                    //ESTADO
                    Direccion.Colonia.Municipio.Estado = new Estado();
                    Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                    //PAIS
                    Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                    result.Object = usuario;
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

    @Override
    public Result UpdateBasicById(Usuario usuario) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL UpdateUser(?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2,usuario.getNombre());
                callableStatement.setString(3,usuario.getApellidoPaterno());
                callableStatement.setString(4,usuario.getApellidoMaterno());
                callableStatement.setDate(5, (java.sql.Date) usuario.getFechaNacimiento());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getPassword());
                callableStatement.setInt(8, usuario.Rol.getIdRol());
                callableStatement.setString(9, usuario.getUsername());
                callableStatement.setString(10, usuario.getSexo());
                callableStatement.setString(11, usuario.getTelefono());
                callableStatement.setString(12, usuario.getCelular());
                callableStatement.setString(13, usuario.getCurp());
                
                callableStatement.executeUpdate();
                
                return true;
            });
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result AddAddressById(Usuario usuario) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL AddAddresByUserId(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
               callableStatement.setInt(1, usuario.getIdUsuario());
               callableStatement.setString(2, usuario.Direcciones.get(0).getCalle());
               callableStatement.setString(3, usuario.Direcciones.get(0).getNumeroInterior());
               callableStatement.setString(4, usuario.Direcciones.get(0).getNumeroExterior());
               callableStatement.setInt(5, usuario.Direcciones.get(0).Colonia.getIdColonia());
               
               callableStatement.executeUpdate();
               
               return true; 
            });
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result UpdateAddressById(Usuario usuario) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL UpdateAddressById(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.setInt(1, usuario.Direcciones.get(0).getIdDireccion());
                callableStatement.setString(2, usuario.Direcciones.get(0).getCalle());
                callableStatement.setString(3, usuario.Direcciones.get(0).getNumeroInterior());
                callableStatement.setString(4, usuario.Direcciones.get(0).getNumeroExterior());
                callableStatement.setInt(5, usuario.Direcciones.get(0).Colonia.getIdColonia());
                
                callableStatement.executeUpdate();
                
                return true;
            });
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result DeleteById(int IdUsuario) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL DeleteUserById(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                
                callableStatement.setInt(1, IdUsuario);
                callableStatement.executeUpdate();
                
               return true; 
            });
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result DeleteAddressById(int IdDireccion) {
        Result result = new Result();
        
        try {
            
            result.Correct = jdbcTemplate.execute("{CALL DeleteAddressById(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
            
                callableStatement.setInt(1, IdDireccion);
                callableStatement.executeUpdate();
                
                return true;
            });
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        
        try {
            
            jdbcTemplate.batchUpdate("CALL AddUserWithAddress(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", usuarios, usuarios.size(), (CallableStatement, usuario) -> {
            
                //Usuario
                CallableStatement.setString(1, usuario.getNombre());
                CallableStatement.setString(2, usuario.getApellidoPaterno());
                CallableStatement.setString(3, usuario.getApellidoMaterno());
                //CallableStatement.setDate(4, java.sql.Date.valueOf(usuario.getFechaNacimiento()));
                CallableStatement.setDate(5, (java.sql.Date) usuario.getFechaNacimiento());
                CallableStatement.setString(5, usuario.getEmail());
                CallableStatement.setString(6, usuario.getPassword());
                
                CallableStatement.setInt(7, usuario.Rol.getIdRol());
                CallableStatement.setString(8, usuario.getUsername());
                CallableStatement.setString(9, usuario.getSexo());
                CallableStatement.setString(10, usuario.getTelefono());
                CallableStatement.setString(11, usuario.getCelular());
                CallableStatement.setString(12, usuario.getCurp());
                //Direccion
                CallableStatement.setString(13, usuario.Direcciones.get(0).getCalle());
                CallableStatement.setString(14, usuario.Direcciones.get(0).getNumeroInterior());
                CallableStatement.setString(15, usuario.Direcciones.get(0).getNumeroExterior());
                CallableStatement.setInt(16, usuario.Direcciones.get(0).Colonia.getIdColonia());
                
                //CallableStatement.execute();
            });
            result.Correct = true;
            
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }

    @Override
    public Result BusquedaUserWithAddress(Usuario usuario) {
        Result result = new Result();
        
        try {
            result.Correct = jdbcTemplate.execute("{CALL BusquedaUserWithAddress(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                Integer idRol = (usuario.Rol != null && usuario.Rol.getIdRol() != null) ? usuario.Rol.getIdRol() : -1;
                callableStatement.setInt(5, idRol);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.Objects = new ArrayList<>();
                
                while (resultSet.next()) {   
                    int IdUsuario = resultSet.getInt("IdUsuario");
                    if (!result.Objects.isEmpty() && ((Usuario) result.Objects.get(result. Objects.size() - 1)).getIdUsuario()== IdUsuario) {
                        Direccion Direccion = new Direccion();
                        //DIRECCION
                        Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        Direccion.setCalle(resultSet.getString("Calle"));
                        Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        Usuario usuarioResult = ((Usuario) result.Objects.get(result. Objects.size() - 1));
                        usuarioResult.Direcciones.add(Direccion);
                        //COLONIA
                        Direccion.Colonia = new Colonia();
                        Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                        Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        //MUNIcipIO
                        Direccion.Colonia.Municipio = new Municipio();
                        Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                        //ESTADO
                        Direccion.Colonia.Municipio.Estado = new Estado();
                        Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                        //PAIS
                        Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                    } else {
                        Usuario usuarioResult = new Usuario();
                        //USUARIO
                        usuarioResult.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioResult.setNombre(resultSet.getString("Nombre"));
                        usuarioResult.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioResult.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        //usuarioResult.setFechaNacimiento(resultSet.getString("FechaNacimiento"));
                        Date fecha = resultSet.getDate("FechaNacimiento");
                        usuarioResult.setFechaNacimiento(fecha);
                        usuarioResult.setEmail(resultSet.getString("Email"));
                        usuarioResult.setPassword(resultSet.getString("Password"));
                        usuarioResult.setUsername(resultSet.getString("Username"));
                        usuarioResult.setSexo(resultSet.getString("Sexo"));
                        usuarioResult.setTelefono(resultSet.getString("Telefono"));
                        if (resultSet.getString("Celular") != null) {usuarioResult.setCelular(resultSet.getString("Celular"));}
                        if (resultSet.getString("Curp") != null) {usuarioResult.setCurp(resultSet.getString("Curp"));}
                        //ROL
                        usuarioResult.Rol = new Rol();
                        usuarioResult.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioResult.Rol.setNombre(resultSet.getString("Puesto"));
                        //DIRECCION
                        int IdDireccion = resultSet.getInt("IdDireccion");
                        if (IdDireccion != 0) {
                            usuarioResult.Direcciones = new ArrayList<>();
                            Direccion Direccion = new Direccion();
                            Direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            Direccion.setCalle(resultSet.getString("Calle"));
                            Direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            Direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            usuarioResult.Direcciones.add(Direccion);
                            //COLONIA
                            Direccion.Colonia = new Colonia();
                            Direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            Direccion.Colonia.setNombre(resultSet.getString("Colonia"));
                            Direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            //MUNICIPIO
                            Direccion.Colonia.Municipio = new Municipio();
                            Direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            Direccion.Colonia.Municipio.setNombre(resultSet.getString("Municpio"));
                            //ESTADO
                            Direccion.Colonia.Municipio.Estado = new Estado();
                            Direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            Direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("Estado"));
                            //PAIS
                            Direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            Direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            Direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("Pais"));

                            
                        }
                        result.Objects.add(usuarioResult);
                    }
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
