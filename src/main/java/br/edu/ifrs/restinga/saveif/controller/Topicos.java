/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class Topicos {
    @Autowired
    TopicoDAO topicoDAO;
    
    @Autowired
    GrupoDAO grupoDAO;
    
    @RequestMapping(path = "/topicos", method = RequestMethod.GET)
    public Iterable<Topico> listar() {
        return topicoDAO.findAll();
    } 
    
    @RequestMapping(path="/topicos/{idGrupo}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Topico inserir(@RequestBody Topico topico, @AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo) throws Exception
    {
        if (topico.getNome().equalsIgnoreCase("Geral"))
            throw new Exception("Nome de tópico inválido");  
        topico.setId(0);
        topico.setCriadorTopico(usuarioAut.getUsuario());
             
        Grupo grupoAtual = grupoDAO.findById(idGrupo);
        
        Topico topicoSalvo = topicoDAO.save(topico);
        
        List<Topico> topicos = new ArrayList<>();
        topicos = grupoAtual.getTopicos();
        topicos.add (topico);
         
        grupoAtual.setTopicos(topicos);
        
        grupoDAO.save(grupoAtual);
        
        return topicoSalvo;
    }
    
    
    @RequestMapping(path = "/grupos/{id}/topicos", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Topico>pesquisaPorTopicos(@RequestParam(required = false, defaultValue = "0") int pagina,@PathVariable int id) throws Exception{
         PageRequest pageRequest = new PageRequest(pagina, 10);
         
         return topicoDAO.findTopico(id);                  // TESTE SQL nativo       
//         return topicoDAO.findTopico(id, pageRequest);  // TESTE Spring Query
                
      }
}
