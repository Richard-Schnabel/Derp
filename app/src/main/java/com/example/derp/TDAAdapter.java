package com.example.derp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class TDAAdapter extends RecyclerView.Adapter<TDAAdapter.MyViewHolder> {

    String errInputNull = "$nic$";
    String hoverDelete = "Odstranit";
    String alertDeleted = "Odstraněno";

    Context context;
    RealmResults<TDA> dataList;

    public TDAAdapter(Context context, RealmResults<TDA> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TDAAdapter.MyViewHolder holder, int position) {
        TDA TDA = dataList.get(position);

        //vpiš data z databáze do listu
        holder.popisOut.setText(TDA.getPopis().equals("") ? errInputNull : TDA.getPopis());
        holder.dateOut.setText(TDA.getDate().equals("") ? errInputNull : TDA.getDate());
        holder.jazykOut.setText(TDA.getJazyk().equals("") ? errInputNull : TDA.getJazyk());
        holder.rateOut.setText(TDA.getRate().equals("") ? errInputNull : (Integer.parseInt(TDA.getRate()) + 1) + " / 5");
        holder.timeOut.setText(TDA.getTime().equals("") ? errInputNull : TDA.getTime() + " min");

        //onhold
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //vyskoč menu
                PopupMenu menu = new PopupMenu(context,v);

                //přidej detele button
                menu.getMenu().add(hoverDelete);

                //po kliknutí na delete
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals(hoverDelete)){

                            //připoj se k databázi
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();

                            //smash záznam
                            TDA.deleteFromRealm();
                            realm.commitTransaction();

                            //křič "smazáno"
                            Toast.makeText(context,alertDeleted,Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView jazykOut;
        TextView popisOut;
        TextView timeOut;
        TextView rateOut;
        TextView dateOut;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //najdi všechny outputy
            jazykOut = itemView.findViewById(R.id.jazykout);
            popisOut = itemView.findViewById(R.id.popisout);
            dateOut = itemView.findViewById(R.id.dateout);
            rateOut = itemView.findViewById(R.id.rateout);
            timeOut = itemView.findViewById(R.id.timeout);
        }
    }
}
