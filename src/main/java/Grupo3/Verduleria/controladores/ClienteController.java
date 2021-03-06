package Grupo3.Verduleria.controladores;
import Grupo3.Verduleria.Entidades.Clientes;
import Grupo3.Verduleria.Servicios.ServicioClientes;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
@Controller
@RequestMapping("/usuario")
public class ClienteController {

    @Autowired
    private ServicioClientes servicioClientes;

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @GetMapping("/editar-perfil")
    public String editarPerfil(HttpSession session, @RequestParam String id, ModelMap model) {

        //Evita que otro usuario logueado edite el perfil de otro comparando los id's y lo envia al inicio
        Clientes clienteLogueado = (Clientes) session.getAttribute("usuariosession");
        if (clienteLogueado == null || !clienteLogueado.getId().equals(id)) {
            return "redirect:/inicio";
        }

        try {
            Clientes cliente = servicioClientes.findById(id);
            model.addAttribute("perfil", cliente);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());

        }
        return "perfilanda.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN')")
    @PostMapping("/actualizar-perfil")
    public String actualizarPerfil(ModelMap model, HttpSession session, @RequestParam String id, @RequestParam String nombre, @RequestParam Long dni, @RequestParam String correo, @RequestParam(required = false) String clave, @RequestParam(required = false) String clave2) {
        Clientes cliente = null;

        try {
            Clientes clienteLogueado = (Clientes) session.getAttribute("usuariosession");
            if (clienteLogueado == null || !clienteLogueado.getId().equals(id)) {
                return "redirect:/inicio";
            }

            cliente = servicioClientes.findById(id);
            servicioClientes.edit(id, clave, clave2, nombre, dni, correo);
            session.setAttribute("usuariosession", cliente); //esto actualiza los cambios del perfil del cliente actual a toda la pagina/session  
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            model.put("perfil", cliente);

            return "perfilanda.html";
        }
        model.put("exitop", "Perfil actualizado correctamente");
        return "inicio.html";

    }
}
