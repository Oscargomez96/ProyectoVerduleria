package Grupo3.Verduleria.Entidades;

import Grupo3.Verduleria.enums.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Clientes {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String clave;
    private Long dni;
    private String correo;
    @Enumerated (EnumType.STRING)
    private Role role;
    
    public Clientes() {
    }

    public Clientes(String nombre, String clave, Long dni, String correo) {
        this.nombre = nombre;
        this.clave = clave;
        this.dni = dni;
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
    @Override
    public String toString() {
        return "Clientes{" + "id=" + id + ", nombre=" + nombre + ", clave=" + clave + ", dni=" + dni + ", correo=" + correo + '}';
    }
}
