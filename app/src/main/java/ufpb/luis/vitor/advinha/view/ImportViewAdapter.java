package ufpb.luis.vitor.advinha.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;


import ufpb.luis.vitor.advinha.R;
import ufpb.luis.vitor.advinha.model.ContextDTO;
import ufpb.luis.vitor.advinha.utils.SaveContextGlobal;

public class ImportViewAdapter extends RecyclerView.Adapter<ImportViewAdapter.viewHolder> {

    private android.content.Context context ;
    private LinkedList<ContextDTO> listaTemas;

    public ImportViewAdapter(Context context, LinkedList<ContextDTO> listaTemas){
        this.context = context;
        this.listaTemas = listaTemas;
    }

    @NonNull
    @Override
    public ImportViewAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.card_view_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImportViewAdapter.viewHolder holder, int position) {
        holder.context_name.setText(listaTemas.get(position).getName());
        loadImage(listaTemas.get(position).getImageUrl(),holder.context_image);
        holder.itemView.setOnClickListener(v ->{SaveContextGlobal.getInstance().add(listaTemas.get(position));

            ContextChoose.fillRecycleView(this.context,SaveContextGlobal.getInstance().getLista(),ContextChoose.rv);});


    }

    @Override
    public int getItemCount() {
        return listaTemas.size();
    }

    static class viewHolder extends RecyclerView.ViewHolder{
        private ImageView context_image;
        private TextView context_name;

        viewHolder(@NonNull View itemView) {
            super(itemView);

            context_image = itemView.findViewById(R.id.context_image);
            context_name = itemView.findViewById(R.id.context_name);
        }
    }
    private void loadImage(String url, ImageView view) {
        Picasso.get()
                .load(url)
                .centerCrop()
                .resize(400,400)
                .into(view);
    }
}
