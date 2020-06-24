package ufpb.luis.vitor.advinha.utils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.*;

import ufpb.luis.vitor.advinha.model.Challenge;
import ufpb.luis.vitor.advinha.model.ChallengeDTO;

public class QueueChallenge extends AbstractQueue<Challenge> {

    private LinkedList<Challenge> fila_challenge;

    public QueueChallenge() {
        this.fila_challenge = new LinkedList<>();
    }

    @NonNull
    @Override
    public Iterator<Challenge> iterator() {
        return fila_challenge.iterator();
    }

    @Override
    public int size() {
        return fila_challenge.size();
    }

    public QueueChallenge(LinkedList<Challenge> fila_challenge) {
        this.fila_challenge = fila_challenge;
    }


    @Override
    public boolean offer(Challenge challenge) {
        if(challenge != null) {
            fila_challenge.add(challenge);
            return true;
        }else {
            return false;
        }
    }

    @Nullable
    @Override
    public Challenge poll() {
        Iterator<Challenge> iterator = fila_challenge.iterator();
        Challenge removido = iterator.next();
        if(fila_challenge.contains(removido)){
            iterator.remove();
            return removido;
        }else{return null;}
    }

    @Nullable
    @Override
    public Challenge peek() {
        return fila_challenge.getFirst();
    }

    public void ReiniciarChallenge(Challenge challenge){
        fila_challenge.poll();
        fila_challenge.offer(challenge);
    }



}

