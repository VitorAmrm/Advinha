package ufpb.luis.vitor.advinha.control;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.*;

import ufpb.luis.vitor.advinha.model.ChallengeDTO;

public class QueueChallenge extends AbstractQueue<ChallengeDTO> {

    private LinkedList<ChallengeDTO> fila_challenge;

    public QueueChallenge() {
        this.fila_challenge = new LinkedList<>();
    }

    @NonNull
    @Override
    public Iterator<ChallengeDTO> iterator() {
        return fila_challenge.iterator();
    }

    @Override
    public int size() {
        return fila_challenge.size();
    }

    public QueueChallenge(LinkedList<ChallengeDTO> fila_challenge) {
        this.fila_challenge = fila_challenge;
    }


    @Override
    public boolean offer(ChallengeDTO challenge) {
        if(challenge != null) {
            fila_challenge.add(challenge);
            return true;
        }else {
            return false;
        }
    }

    @Nullable
    @Override
    public ChallengeDTO poll() {
        Iterator<ChallengeDTO> iterator = fila_challenge.iterator();
        ChallengeDTO removido = iterator.next();
        if(fila_challenge.contains(removido)){
            iterator.remove();
            return removido;
        }else{return null;}
    }

    @Nullable
    @Override
    public ChallengeDTO peek() {
        return fila_challenge.getFirst();
    }

    public void ReiniciarChallenge(ChallengeDTO challenge){
        fila_challenge.poll();
        fila_challenge.offer(challenge);
    }



}

