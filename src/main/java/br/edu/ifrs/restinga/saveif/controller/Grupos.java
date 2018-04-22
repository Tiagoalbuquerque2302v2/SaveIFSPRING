/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gustavo e eduarda
 */
@RestController
@RequestMapping(path = "/api")
public class Grupos {
    
    @Autowired
    GrupoDAO GrupoDAO;

    @RequestMapping(path = "/grupos", method = RequestMethod.GET)
    public Iterable<Grupo> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return GrupoDAO.findAll(pageRequest);
    }
    
    @RequestMapping(path = "/grupos/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Grupo grupo) {
        if (GrupoDAO.existsById(id)) {
            grupo.setId(id);
            GrupoDAO.save(grupo);
        }
    }
    
    @RequestMapping(path="/grupos/integrantes", method = RequestMethod.GET)
    public Grupo pesquisaPorIntegrantes(@RequestParam int id) throws Exception {
        Usuario igual = new Usuario();
        igual.setId(id);
        return (GrupoDAO.findByIntegrantesGrupo(igual));
    }

}
