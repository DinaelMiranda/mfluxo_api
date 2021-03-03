package com.dithec.mfluxo_api.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

@Getter//Cria os getters de todos os atibutos da class
public class RecursoCriadoEvent extends ApplicationEvent {

   private HttpServletResponse response;
   private Long id;

    public RecursoCriadoEvent(Object source, HttpServletResponse response, Long id) {
        super( source);
        this.response = response;
        this.id = id;
    }

}
