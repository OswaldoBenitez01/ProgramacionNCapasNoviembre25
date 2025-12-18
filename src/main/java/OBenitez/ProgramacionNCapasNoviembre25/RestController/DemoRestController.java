
package OBenitez.ProgramacionNCapasNoviembre25.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class DemoRestController {
    
    @GetMapping("suma/{numero1}/{numero2}")
    public int Suma(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 + numero2;
        return resultado;
    }
    
    @GetMapping("resta/{numero1}/{numero2}")
    public int Resta(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 - numero2;
        return resultado;
    }
    
    @GetMapping("multiplicacion/{numero1}/{numero2}")
    public int Multiplicacion(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 * numero2;
        return resultado;
    }
    
    @GetMapping("division/{numero1}/{numero2}")
    public int Division(@PathVariable int numero1, @PathVariable int numero2){
        int resultado = numero1 / numero2;
        return resultado;
    }
    
    @GetMapping("suma")
    public int SumaUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 + numero2;
        return resultado;
    }
    
    @GetMapping("resta")
    public int RestaUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 - numero2;
        return resultado;
    }
    
    @GetMapping("multiplicacion")
    public int MultiplicacionUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 * numero2;
        return resultado;
    }
    
    @GetMapping("division")
    public int DivisionUrl(@RequestParam int numero1, @RequestParam int numero2){
        int resultado = numero1 / numero2;
        return resultado;
    }
}
