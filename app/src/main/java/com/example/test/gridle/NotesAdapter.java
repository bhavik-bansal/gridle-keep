package com.example.test.gridle;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bhavik on 1/29/16.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder>{
    private List<NotesData> dataSets;
    private static final String TAG ="Notes Adapter";
    public static final String CARD_SELECTED ="selected_card";

     public class NotesViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle;
         TextView nNote;
         CardView mCardView;
         public NotesViewHolder(View itemView) {
             super(itemView);
             nTitle = (TextView) itemView.findViewById(R.id.notes_asset_title);
             nNote = (TextView) itemView.findViewById(R.id.notes_asset_note);
             mCardView = (CardView) itemView.findViewById(R.id.notes_card_view);
         }
     }
    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NotesViewHolder holder, int position) {
        final NotesData data = dataSets.get(position);
        if (data.getTitle()!=null)
        holder.nTitle.setText(data.getTitle());
        if (data.getNote()!=null)
        holder.nNote.setText(data.getNote());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),AddNote.class);
                i.putExtra("id",data.getId());
                v.getContext().startActivity(i);
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.mCardView.setSelected(true);
                Intent intent = new Intent(CARD_SELECTED);
                intent.putExtra("id",data.getId());
                LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                return true;
            }
        });

    }

    public NotesAdapter(List<NotesData> dataSets) {
        this.dataSets = dataSets;
    }

    @Override
    public int getItemCount() {
        return dataSets.size();
    }


}
