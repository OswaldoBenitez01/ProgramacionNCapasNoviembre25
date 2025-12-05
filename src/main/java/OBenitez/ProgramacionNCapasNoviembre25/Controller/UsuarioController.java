
package OBenitez.ProgramacionNCapasNoviembre25.Controller;

import OBenitez.ProgramacionNCapasNoviembre25.DAO.ColoniaDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.EstadoDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.MunicipioDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.PaisDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.RolDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.DAO.UsuarioDAOImplementation;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Colonia;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Direccion;
import OBenitez.ProgramacionNCapasNoviembre25.ML.ErrorCarga;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Result;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Rol;
import OBenitez.ProgramacionNCapasNoviembre25.ML.Usuario;
import OBenitez.ProgramacionNCapasNoviembre25.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("Usuario")
public class UsuarioController {
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    @Autowired
    private PaisDAOImplementation paisDAOImplementation;
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    @Autowired
    private ValidationService validationService;
    
    @GetMapping
    public String GetAll(Model model){ // resultDelete
    
        Result result = usuarioDAOImplementation.GetAll();
        model.addAttribute("Usuarios", result.Objects);
        return "Index";
    }
    
    @GetMapping("form")
    public String Form(Model model){
        Result resultRol = rolDAOImplementation.GetALl();
        model.addAttribute("Roles", resultRol.Objects);
        Result resultPais = paisDAOImplementation.GetAll();
        model.addAttribute("Paises", resultPais.Objects);
        model.addAttribute("Usuario", new Usuario());
        
        model.addAttribute("textoBoton", "Agregar Usuario");
        model.addAttribute("classBoton", "btn-success");
        model.addAttribute("btnVolver", "/Usuario");
        return "UsuarioForm";
    }
    
    @PostMapping("add")
    public String Add(@Valid @ModelAttribute("Usuario") Usuario usuario, BindingResult bindingResult, Model model){
        
        if (bindingResult.hasErrors()) {
            Result resultRol = rolDAOImplementation.GetALl();
            model.addAttribute("Roles", resultRol.Objects);
            Result resultPais = paisDAOImplementation.GetAll();
            model.addAttribute("Paises", resultPais.Objects);
            model.addAttribute("Usuario", usuario);
            return "UsuarioForm";
        } else {
            Result result = usuarioDAOImplementation.Add(usuario);
            if (result.Correct) {
                return "redirect:/Usuario";
            } else {
                return "redirect:/Usuario/form";
            }
        }
   
    }
    
    @GetMapping("detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model){
    
        Result result = usuarioDAOImplementation.GetById(IdUsuario);
        
        Usuario usuarioEncontrado = (Usuario) result.Objects.get(0);
        model.addAttribute("Usuario", usuarioEncontrado);
        
        return "UsuarioDetail";
    }
    
    @GetMapping("delete/{IdUsuario}")
    public String Delete(@PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes){
    
        Result result = usuarioDAOImplementation.DeleteById(IdUsuario);
        
        if(result.Correct){
            result.Object = "El usuario con ID " + IdUsuario + " fue eliminado";
        } else{
            result.Object = "No fue posible eliminar al usuario :c";
        }
        
        redirectAttributes.addFlashAttribute("resultDelete", result);
        return "redirect:/Usuario";
    }
    
    @GetMapping("deleteAddress/{IdDireccion}/{IdUsuario}")
    public String DeleteAddress(@PathVariable("IdDireccion") int IdDireccion, @PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes){
    
        Result result = usuarioDAOImplementation.DeleteAddressById(IdDireccion);
        
        if(result.Correct){
            result.Object = "La direccion fue eliminada";
        } else{
            result.Object = "No fue posible eliminar la direccion :c";
        }
        
        redirectAttributes.addFlashAttribute("resultDeleteAddress", result);
        return "redirect:/Usuario/detail/"+IdUsuario;
    }
    
    
    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody // retorna un dato estructurado
    public Result EstadosByPais(@PathVariable("idPais") int idPais){
        Result result = estadoDAOImplementation.GetEstadosByPais(idPais);
        return result;
    }
    @GetMapping("getMunicipiosByEstado/{idEstado}")
    @ResponseBody // retorna un dato estructurado
    public Result MunicipiosByEstado(@PathVariable("idEstado") int idEstado){
        Result result = municipioDAOImplementation.GetMunicipiosByEstado(idEstado);
        return result;
    }
    @GetMapping("getColoniasByMunicipio/{idMunicipio}")
    @ResponseBody // retorna un dato estructurado
    public Result ColoniasByMunicipio(@PathVariable("idMunicipio") int idMunicipio){
        Result result = coloniaDAOImplementation.GetColoniasByMunicipio(idMunicipio);
        return result;
    }
    
    
    @GetMapping("/formEditable")
    public String Form(@RequestParam("IdUsuario") int IdUsuario, @RequestParam(required = false) Integer IdDireccion, Model model){
    
        if (IdDireccion == null) {
            // ==== EDITAR USUARIO
            Result result = usuarioDAOImplementation.GetByIdBasicInfo(IdUsuario);
            
            Usuario usuario = (Usuario) result.Object;
            usuario.Direcciones = new ArrayList<>();
            usuario.Direcciones.add(new Direccion());
            usuario.Direcciones.get(0).setIdDireccion(-1);
            model.addAttribute("Usuario", usuario);
            
            //Llenado de campos
            Result resultRol = rolDAOImplementation.GetALl();
            model.addAttribute("Roles", resultRol.Objects);
            Result resultPais = paisDAOImplementation.GetAll();
            model.addAttribute("Paises", resultPais.Objects);
            
            model.addAttribute("textoBoton", "Editar Usuario");
            model.addAttribute("classBoton", "btn-primary");
            model.addAttribute("btnVolver", "/Usuario/detail/"+IdUsuario);
            return "UsuarioForm";
        } else if (IdDireccion == 0) {
            //AGREGAR DIRECCION
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(IdUsuario);
            usuario.Direcciones = new ArrayList<>();
            usuario.Direcciones.add(new Direccion());
            model.addAttribute("Usuario", usuario);
            Result resultPais = paisDAOImplementation.GetAll();
            model.addAttribute("Paises", resultPais.Objects);
            
            model.addAttribute("textoBoton", "Agregar Direccion");
            model.addAttribute("classBoton", "btn-success");
            model.addAttribute("btnVolver", "/Usuario/detail/"+IdUsuario);
            return "UsuarioForm";
        } else {
            //EDITAR DIRECCION
            Result result = usuarioDAOImplementation.GetAddressById(IdUsuario,IdDireccion);
            model.addAttribute("Usuario", result.Object);
            Result resultPais = paisDAOImplementation.GetAll();
            model.addAttribute("Paises", resultPais.Objects);
            
            model.addAttribute("textoBoton", "Editar Direccion");
            model.addAttribute("classBoton", "btn-warning");
            model.addAttribute("btnVolver", "/Usuario/detail/"+IdUsuario);
            return "UsuarioForm";
        }
    
    }
    
    @PostMapping("formEditable")
    public String Form(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes){
    
        if(usuario.getIdUsuario()== 0){
            // AGREGAR USUARIO FULL INFO
            Result result = usuarioDAOImplementation.Add(usuario);
            
            if(result.Correct){
                result.Object = "El usuario se agrego correctamente";
            } else{
                result.Object = "No fue posible agregar al usuario :c";
            }
            redirectAttributes.addFlashAttribute("resultAddUserFull", result);
            return "redirect:/Usuario";
            
        }else if(usuario.Direcciones.get(0).getIdDireccion() == -1){
            //ACTUALIZAR INFORMACION BASICA USUARIO
            Result result = usuarioDAOImplementation.UpdateBasicById(usuario);
            if(result.Correct){
                result.Object = "El usuario se actualizo correctamente";
            } else{
                result.Object = "No fue posible actualizar al usuario :c";
            }
            redirectAttributes.addFlashAttribute("resultEditUserBasic", result);
            return "redirect:/Usuario/detail/"+usuario.getIdUsuario();
            
        }else if(usuario.Direcciones.get(0).getIdDireccion() == 0){
            //AGREGA UNA DIRECCION NUEVA
            Result result = usuarioDAOImplementation.AddAddressById(usuario);
            if(result.Correct){
                result.Object = "La direccion se agrego correctamente";
            } else{
                result.Object = "No fue posible agregar la direccion :c";
            }
            redirectAttributes.addFlashAttribute("resultAddAddress", result);
            return "redirect:/Usuario/detail/"+usuario.getIdUsuario();
        }else{
            //ACTUALIZA UNA DIRECCION
            Result result = usuarioDAOImplementation.UpdateAddressById(usuario);            
            if(result.Correct){
                result.Object = "La direccion se actualizo correctamente";
            } else{
                result.Object = "No fue posible actualizar la direccion :c";
            }
            redirectAttributes.addFlashAttribute("resultEditAddress", result);
            return "redirect:/Usuario/detail/"+usuario.getIdUsuario();
        }
    }
    
    @GetMapping("CargaMasiva")
    public String CargaMsiva(){
        return "CargaMasiva";
    }
    
    @PostMapping("CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model, HttpSession sesion) throws IOException {
    
        String extension = archivo.getOriginalFilename().split("\\.")[1];
        
        String path = System.getProperty("user.dir");
        String pathArchivo = "src/main/resources/archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        String rutaAbsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();
        
        archivo.transferTo(new File(rutaAbsoluta));
        
        List<Usuario> usuarios = new ArrayList<>();
        
        if (extension.equals("txt")) {
            usuarios = LecturaArchivo(new File(rutaAbsoluta));
        } else {
            usuarios = LecturaArchivoExcel(new File(rutaAbsoluta));    
        }
        
        List<ErrorCarga> errores = ValidarDatos(usuarios);
        
        if (errores != null && !errores.isEmpty()) {
            model.addAttribute("errores", errores);
            model.addAttribute("tieneErrores", true);
        } else {
            model.addAttribute("mensajeExito", "Carga exitosa. Se cargaron " + usuarios.size() + " usuario(s) correctamente");
            model.addAttribute("tieneErrores", false);
            
            sesion.setAttribute("archivoCargaMasiva", rutaAbsoluta);
        }
        
        return "CargaMasiva";
    }

    private List<Usuario> LecturaArchivo(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))){
            
            bufferedReader.readLine();
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {                
                
                String[] datos = line.split("\\|");
                
                Usuario usuario = new Usuario();
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setEmail(datos[4]);
                usuario.setPassword(datos[5]);
                usuario.setFechaNacimiento(datos[6]);
                usuario.setSexo(datos[7]);
                usuario.setTelefono(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCurp(datos[10]);
                
                //Direccion
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));
                
                //DIRECCION
                usuario.Direcciones = new ArrayList<>();
                Direccion Direccion = new Direccion();
                Direccion.setCalle(datos[12]);
                Direccion.setNumeroExterior(datos[13]);
                Direccion.setNumeroInterior(datos[14]);
                usuario.Direcciones.add(Direccion);
                
                Direccion.Colonia = new Colonia();
                Direccion.Colonia.setIdColonia(Integer.parseInt(datos[15]));
                
                usuarios.add(usuario);
            }
        }
        catch(Exception ex){
            usuarios = null;
        }
        
        return usuarios;
    }

    private List<Usuario> LecturaArchivoExcel(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        
         try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
             
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                
                Usuario usuario = new Usuario();
                Cell cell0 = row.getCell(0);
                if (cell0 != null) {
                    usuario.setUsername(row.getCell(0).toString());
                } else {
                    continue;
                }
                
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setEmail(row.getCell(4).toString());
                usuario.setPassword(row.getCell(5).toString());
                usuario.setFechaNacimiento(row.getCell(6).getLocalDateTimeCellValue().toString().split("T")[0]);
                usuario.setSexo(row.getCell(7).toString());
                usuario.setCelular(row.getCell(8).toString());
                usuario.setTelefono(row.getCell(9).toString());
                usuario.setCurp(row.getCell(10).toString());
                
                usuario.Rol = new Rol();
                usuario.Rol.setIdRol((int) row.getCell(11).getNumericCellValue());
                //DIRECCION
                usuario.Direcciones = new ArrayList<>();
                Direccion Direccion = new Direccion();
                Direccion.setCalle(row.getCell(12).toString());
                Direccion.setNumeroExterior(row.getCell(13).toString());
                Direccion.setNumeroInterior(row.getCell(14).toString());
                usuario.Direcciones.add(Direccion);
                
                Direccion.Colonia = new Colonia();
                Direccion.Colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());
                
                usuarios.add(usuario);
            }
            
        } catch (Exception ex) {
            usuarios = null;
        }
        
        return usuarios;
    }
    
    private List<ErrorCarga> ValidarDatos(List<Usuario> usuarios) {
        
        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;
        
        for (Usuario usuario : usuarios) {
            LineaError++;
            BindingResult bindingResult = validationService.validateObjects(usuario);
            List<ObjectError> errors = bindingResult.getAllErrors();
            
            if (usuario.Rol == null) {
                usuario.Rol = new Rol();
            }
            if (usuario.Direcciones == null) {
                usuario.Direcciones = new ArrayList<>();
                Direccion Direccion = new Direccion();
                usuario.Direcciones.add(Direccion);
            }
            
            BindingResult bindingResultRol = validationService.validateObjects(usuario.Rol);
            List<ObjectError> errorsRol = bindingResultRol.getAllErrors();
            
            BindingResult bindingResultDireccion = validationService.validateObjects(usuario.Direcciones.get(0));
            List<ObjectError> errorsDireccion = bindingResultDireccion.getAllErrors();
            
            List<ObjectError> listaCombinada = new ArrayList<>(errors);
            
            if (!errorsRol.isEmpty()) { 
                listaCombinada.addAll(errorsRol);
            }
            if (!errorsDireccion.isEmpty()) { 
                listaCombinada.addAll(errorsDireccion);
            }
            
            for (ObjectError error : listaCombinada) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.Linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                
                erroresCarga.add(errorCarga);
            }
        }
        
        return erroresCarga;
    }
    
    @GetMapping("CargaMasiva/procesar")
    public String ProcesarArchivo(HttpSession sesion, Model model, RedirectAttributes redirectAttributes){
    
        String rutaArchivo = sesion.getAttribute("archivoCargaMasiva").toString();
        
        File archivo = new File(rutaArchivo);
        String nombreArchivo = archivo.getName();
        String extension = nombreArchivo.split("\\.")[1];
        List<Usuario> usuarios = new ArrayList<>();
        
        if (extension.equals("txt")) {
            usuarios = LecturaArchivo(archivo);
        } else {
            usuarios = LecturaArchivoExcel(archivo);  
        }
        
        if (usuarios != null && !usuarios.isEmpty()) {
            
            //Intentamos la insercion
            Result result = usuarioDAOImplementation.AddAll(usuarios);
            sesion.removeAttribute("archivoCargaMasiva");
            
            if(result.Correct){
                result.Object = "Se agregó " + usuarios.size() + " usuario(s) nuevo(s)";
            } else{
                result.Object = "No fue posible agregar a los usuarios :c";
            }
            redirectAttributes.addFlashAttribute("resultCargaMasiva", result);
            
            return "redirect:/Usuario";
        } else {
            
            return "redirect:/Usuario/CargaMasiva";
        }
    }
    
    @PostMapping("busqueda")
    public String Busqueda(){
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setApellidoPaterno("");
        usuario.setApellidoMaterno("");
        usuario.Rol = new Rol();
        usuario.Rol.setIdRol(21);
        Result result = usuarioDAOImplementation.BusquedaUserWithAddress(usuario);
        return "Index";
    }
}
