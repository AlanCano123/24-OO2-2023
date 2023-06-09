package unla.oo2.grupo24.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import unla.oo2.grupo24.entity.*;
import unla.oo2.grupo24.service.imp.EstacionamientoSevicesImp;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class EstacionamientoController {

    @Autowired
    private EstacionamientoSevicesImp service;



    @GetMapping("/estacionamiento")
    public String listarDispositivos(Model model) {


        List<SensorEstacionamiento> listado = service.getAll();
        model.addAttribute("lista",listado);

        return "views/dispositivos/listaEstacionamientos";
    }


    @GetMapping("/estacionamiento/create")
    public String formRegistroEstacionamiento(Model model) {

        SensorEstacionamiento sensor=new SensorEstacionamiento();

        model.addAttribute("estacionamiento",sensor);
        return "views/dispositivos/crearEstacionamiento";
    }

    @PostMapping("/estacionamiento")
    public String registerUser( @ModelAttribute("estacionamiento") SensorEstacionamiento estacionamiento,Model model) {


        estacionamiento.setFecha(LocalDate.now());
        estacionamiento.setActivo(true);
        estacionamiento.setEstadoActual(false);

        service.add(estacionamiento);



        return "redirect:/estacionamiento";
    }

    @GetMapping("/estacionamiento/{id}")
    public String eliminarContenedor(@PathVariable("id") long id, Model model) {

        service.delete(id);
        return "redirect:/estacionamiento";
    }

    @GetMapping("/estacionamiento/{id}/edit")
    public String formEditarEstacionamiento(@PathVariable("id") long id, Model model) {
       SensorEstacionamiento estacionamiento = service.getById(id);
        model.addAttribute("estacionamiento", estacionamiento);
        return "views/dispositivos/editarEstacionamiento";
    }

    @PostMapping("/estacionamiento/{id}/update")
    public String formEditarEstacionamiento(@PathVariable("id") long id, @ModelAttribute("contenedor") Contenedor contenedor, Model model) {
        SensorEstacionamiento sensor = service.getById(id);
        sensor.setNombre(contenedor.getNombre());
        sensor.setDescripcion(contenedor.getDescripcion());
        service.modify(sensor);
        model.addAttribute("lista", service.getAll());
        return "views/dispositivos/listaEstacionamientos";
    }

}
