package com.dithec.mfluxo_api.exceptions.customExceptionMessages;


public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Object obj, Object id) {
        super("Não existe no BD dados da " + obj.toString() + " com o Id: " + id);
    }
}
