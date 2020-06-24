package ufpb.luis.vitor.advinha.utils;


import android.content.Context;

import android.content.res.Resources;
import android.util.JsonWriter;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;






public class ConfigurationLogger {


    private boolean teclado_personalizado;
    private boolean teclado_google;
    private Context context;


    public ConfigurationLogger(boolean personalizado_button,boolean google_button,Context context){
        teclado_google = google_button;
        teclado_personalizado = personalizado_button;
        this.context = context;
    }
    public ConfigurationLogger(Context context){
        this.context = context;
    }

    public void writeConfig () throws IOException {
        File file = new File(context.getFilesDir(),"config.json");
        Writer writer = new FileWriter(file);
        Gson g = new Gson();
        boolean [] json = {teclado_personalizado,teclado_google};
        g.toJson(json,writer);
        writer.close();
    }
    public boolean [] readConfig () throws IOException {
        File arq = new File(context.getFilesDir(),"config.json");
        FileReader reader = new FileReader(arq);
        BufferedReader buffReader = new BufferedReader(reader);
        Gson g = new Gson();
        return g.fromJson(buffReader, boolean[].class);
    }
}
