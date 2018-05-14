/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Anexo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
    @Lob()
    @Basic(fetch = FetchType.EAGER)
    //@JsonIgnore
    private byte[] documentoAnexo;
    
    //@JsonIgnore
    private String tipoAnexo;
    
    private String nomeAnexo;

    
    
   
    public int getId() {
        return id;
    }
    public byte[] getDocumentoAnexo() {
        return documentoAnexo;
    }
    public String getTipoAnexo() {
        return tipoAnexo;
    }
    public String getNomeAnexo() {
        return nomeAnexo;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setDocumentoAnexo(byte[] documentoAnexo) {
        this.documentoAnexo = documentoAnexo;
    }
    public void setTipoAnexo(String tipoAnexo) {
        this.tipoAnexo = tipoAnexo;
    }
    public void setNomeAnexo(String nomeAnexo) {
        this.nomeAnexo = nomeAnexo;
    }
    
    
    
    
    
    
    
    
    
    
}
