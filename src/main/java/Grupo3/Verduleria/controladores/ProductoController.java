package Grupo3.Verduleria.controladores;

import Grupo3.Verduleria.Entidades.ProductoKilo;
import Grupo3.Verduleria.Servicios.ServicioKilo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/productoslista")
public class ProductoController {

    @Autowired
    private ServicioKilo servicioKilo;

    @GetMapping("/productoslista")
    public String listaProductos(ModelMap model) throws Exception {
        List<ProductoKilo> listaKilo = servicioKilo.findAll();
        model.addAttribute("lista", listaKilo);
        return "Lista_Productos.html";
    }

    
    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")  
    @GetMapping("/productos-cliente")
    public String listaProductosCliente(ModelMap model) throws Exception {
        List<ProductoKilo> listaKilo = servicioKilo.findAll();
        model.addAttribute("lista", listaKilo);
        return "productos_cliente";
    }

    @PostMapping("/addproducto")
    public String agregar(ModelMap model, @RequestParam String nombre, @RequestParam Integer precio, @RequestParam Integer kilo) {
        try {
            servicioKilo.save(nombre, precio, kilo);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "Lista_Productos.html";
        }

        return "Lista_Productos.html";
    }

    @PostMapping("/delproducto")
    public String borrar(ModelMap model, @RequestParam String id) {
        try {
            servicioKilo.delete(id);
        } catch (Exception ex) {
            model.addAttribute("error", "Hubo un problema: " + ex.getMessage());
        }
        return "Lista_Productos.html";
    }

}
