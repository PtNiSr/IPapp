package com.ni___ckel.ipapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<IPaddress> ipADDresses = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setIpADDresses(List<IPaddress> ipADDresses) {
        this.ipADDresses = ipADDresses;
        notifyDataSetChanged();
    }

    public List<IPaddress> getIpADDresses() {
        return new ArrayList<>(ipADDresses);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.activity_ip_item,
                        parent,
                        false
                );
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder viewHolder, int position) {
        IPaddress ipaddress = ipADDresses.get(position);
        viewHolder.TextViewNote.setText(ipaddress.getIP());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNoteClickListener.onNoteClick(ipaddress);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ipADDresses.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private TextView TextViewNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewNote = itemView.findViewById(R.id.TextViewNote);
        }
    }

    interface OnNoteClickListener{
        void onNoteClick(IPaddress ipaddress);
    }


}
