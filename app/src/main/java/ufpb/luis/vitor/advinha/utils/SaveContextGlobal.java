package ufpb.luis.vitor.advinha.utils;

import android.os.Parcelable;

import java.util.LinkedList;

import ufpb.luis.vitor.advinha.model.ContextDTO;
import ufpb.luis.vitor.advinha.model.Creator;

public final class SaveContextGlobal {

    private static LinkedList<ContextDTO> listaContexto = new LinkedList<>();
    private static LinkedList<Creator> listaUsers = new LinkedList<>();

    private final static  SaveContextGlobal INSTANCE = new SaveContextGlobal();

    private SaveContextGlobal() {
    }

    public static SaveContextGlobal getInstance() {
        return INSTANCE;
    }

    public void clearListaContexto (){listaContexto = new LinkedList<>();}

    public LinkedList<ContextDTO> getLista(){return listaContexto;}

    public LinkedList<Creator> getUsers(){return listaUsers;}

    public void setLista(LinkedList<ContextDTO> lista){listaContexto = lista;}

    public void add (ContextDTO contexto){
        boolean contextoIgual = false;
        for(ContextDTO c : SaveContextGlobal.getInstance().getLista()){
            if(c.getId().equals(contexto.getId())){
                contextoIgual = true;
                break;
            }
        }
        if(!contextoIgual) {
            listaContexto.add(contexto);
        }
    }


    public void addAll (LinkedList<ContextDTO> param){
        for (ContextDTO c: param) {
            SaveContextGlobal.getInstance().add(c);
        }
    }

    public void addCreator (Creator creator){listaUsers.add(creator);}


}
