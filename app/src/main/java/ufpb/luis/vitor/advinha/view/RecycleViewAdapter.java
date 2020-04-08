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
import java.util.*;
import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.model.ChallengeDTO;
import ufpb.luis.vitor.advinha.model.ContextDTO;


public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.viewHolder> {

    private Context context ;
    private LinkedList<ContextDTO> listaTemas;
    public static LinkedList<ChallengeDTO> tmp;

    public RecycleViewAdapter(Context context, LinkedList<ContextDTO> listaTemas) {
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
        holder.context_name.setText(listaTemas.get(position).getName());

        loadImage(listaTemas.get(position).getImageUrl(),holder.context_image);

        holder.itemView.setOnClickListener(v -> {
            ArrayList<ChallengeDTO> tmp = TransformaLinkedEmArray(listaTemas.get(position).getListaChallenge());

            Intent tela = new Intent();

            tela.setClass(this.context,MainActivity.class);

            tela.putParcelableArrayListExtra("listinha",tmp);

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
                .error(R.drawable.erroimage)
                .centerInside()
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

    public static ArrayList<ChallengeDTO> TransformaLinkedEmArray(LinkedList<ChallengeDTO> lista){
        ArrayList<ChallengeDTO> tmp = new ArrayList<>();
        for(ChallengeDTO c :lista) {
            tmp.add(c);
        }
        return tmp;
    }


}
