package ufpb.luis.vitor.advinha.utils;

import java.util.LinkedList;

import ufpb.luis.vitor.advinha.model.ContextDTO;

public final class SaveContextGlobal {

    private static LinkedList<ContextDTO> listaContexto = new LinkedList<>();

    private final static  SaveContextGlobal INSTANCE = new SaveContextGlobal();

    public SaveContextGlobal() {
    }

    public static SaveContextGlobal getInstance() {
        return INSTANCE;
    }

    public void clearListaContexto (){listaContexto = new LinkedList<>();}

    public LinkedList<ContextDTO> getLista(){return listaContexto;}

    public void setLista(LinkedList<ContextDTO> lista){listaContexto = lista;}

    public void add (ContextDTO contexto){listaContexto.add(contexto);}


}
