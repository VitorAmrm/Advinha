package ufpb.luis.vitor.advinha.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.*;

import retrofit2.Call;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.control.service.RetrofitInitializer;
import ufpb.luis.vitor.advinha.model.Challenge;
import ufpb.luis.vitor.advinha.model.ContextDTO;

import static java.lang.Character.toUpperCase;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.viewHolder> {

    private Context context ;
    private LinkedList<ContextDTO> listaTemas;
    private ArrayList<Challenge> listaChallenge = new ArrayList<>();


    RecycleViewAdapter(Context context, LinkedList<ContextDTO> listaTemas) {
        this.context = context;
        this.listaTemas = listaTemas;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflado = LayoutInflater.from(this.context);

        view = inflado.inflate(R.layout.card_view_layout,parent,false);




        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        final ufpb.luis.vitor.advinha.model.Context[] tmp = {new ufpb.luis.vitor.advinha.model.Context()};


        holder.context_name.setText(capitalize(listaTemas.get(position).getName()));

        loadImage(listaTemas.get(position).getImageUrl(),holder.context_image);

        holder.itemView.setOnClickListener(v -> {
            Runnable r = new Runnable() {
               private ufpb.luis.vitor.advinha.model.Context contexto;

                @Override
                public void run() {
                    try {
                        tmp[0] = pegarTodosChallengesDeUmContexto(context,listaTemas.get(position).getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public ufpb.luis.vitor.advinha.model.Context getContexto(){
                    return this.contexto;
                }
            };
            Thread t = new Thread(r);
            t.start();
            System.out.println(position);
            Intent tela = new Intent();
            tela.setClass(this.context,MainActivity.class);
            try {
                t.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tela.putParcelableArrayListExtra("listinha",tmp[0].getChallenges());
            context.startActivity(tela);

        });
    }

    @Override
    public int getItemCount() {
        return listaTemas.size();
    }


    private void loadImage(String url, ImageView view) {
        Picasso.get()
                .load(url)
                .centerCrop()
                .resize(400,400)
                .into(view);
    }



    public static class viewHolder extends RecyclerView.ViewHolder{
        private ImageView context_image;
        private TextView context_name;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            context_image = itemView.findViewById(R.id.context_image);
            context_name = itemView.findViewById(R.id.context_name);
        }
    }

    public static ArrayList<Challenge> TransformaLinkedEmArray(LinkedList<Challenge> lista){
        ArrayList<Challenge> tmp = new ArrayList<>();
        for(Challenge c :lista) {
            tmp.add(c);
        }
        return tmp;
    }

    public static String  capitalize(String txt) {
     String f = txt.substring(0,1).toUpperCase();
     return txt.replaceFirst(txt.substring(0,1),f);
    }

    public ufpb.luis.vitor.advinha.model.Context pegarTodosChallengesDeUmContexto (Context context, Long id) throws IOException {
        Call<ufpb.luis.vitor.advinha.model.Context> call = new RetrofitInitializer().contextService().pegarChallengesDoContexto(id);
        System.out.println("-------------------------TEMQUERESPEITRA----------------------------");
        /*
        call.enqueue(new Callback<ufpb.luis.vitor.advinha.model.Context>() {
            @Override
            public void onResponse(Call<ufpb.luis.vitor.advinha.model.Context> call, Response<ufpb.luis.vitor.advinha.model.Context> response) {
                System.out.println("-------------------------Tonresponse----------------------------");
                System.out.println(response.body());

                //listaChallenge.addAll(response.body().getChallenges());
            }

            @Override
            public void onFailure(Call<ufpb.luis.vitor.advinha.model.Context> call, Throwable t) {
                System.out.println("-------------------------Tonfailure----------------------------");
                System.out.println(t.getMessage());
            }
        });
        */
        ufpb.luis.vitor.advinha.model.Context r =  call.execute().body();
        System.out.println("-------------------------RETURN-----------------------------");
        return r;
    }

}
