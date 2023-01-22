package com.example.derp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.prefs.Preferences;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TDAAdapter extends RecyclerView.Adapter<TDAAdapter.MyViewHolder> {

    //definuj texty
    String errInputNull = "$nic$";
    String hoverDelete = "Odstranit";
    String hoverEdit = "Upravit";
    String alertDeleted = "Odstraněno";

    Context context;
    RealmResults<TDA> zaznamy;

    public TDAAdapter(Context context, RealmResults<TDA> zaznamy) {
        this.context = context;

        //získej uložené nastavení a pokud není zvoleno, tak createdTime sestupně
        Preferences prefs = Preferences.userNodeForPackage(this.getClass());
        String sortOrder = prefs.get("sortOrder", Sort.DESCENDING.toString());
        String sortBy = prefs.get("sortBy", "createdTime");

        //tady budou podobně udělány filtry a reddit tagy

        //seřaď záznamy
        this.zaznamy = zaznamy.sort(sortBy, Sort.valueOf(sortOrder));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TDAAdapter.MyViewHolder holder, int position) {

        //najdi zaznam v databázi
        TDA zaznam = zaznamy.get(position);

        //vpiš data záznamu z databáze do listu
        holder.popisOut.setText(zaznam.getPopis().equals("") ? errInputNull : zaznam.getPopis());
        holder.dateOut.setText(zaznam.getDate().equals("") ? errInputNull : zaznam.getDate());
        holder.jazykOut.setText(zaznam.getJazyk().equals("") ? errInputNull : zaznam.getJazyk());
        holder.rateOut.setText(zaznam.getRate().equals("") ? errInputNull : (Integer.parseInt(zaznam.getRate()) + 1) + " / 5");
        holder.timeOut.setText(zaznam.getTime().equals("") ? errInputNull : zaznam.getTime() + " min");

        //po podržení záznamu
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //vyskoč menu s buttony
                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add(hoverDelete);
                menu.getMenu().add(hoverEdit);

                //po kliknutí na něco
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        //pokud to něco je Delete
                        if(item.getTitle().equals(hoverDelete)){

                            //připoj se k databázi
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();

                            //smash záznam
                            zaznam.deleteFromRealm();
                            realm.commitTransaction();

                            //křič "smazáno"
                            Toast.makeText(context,alertDeleted,Toast.LENGTH_SHORT).show();

                            //reloadni Main bez animace
                            Intent intent = new Intent(v.getContext(), Main.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //nevim co to dela, ale bez toho to nefunguje
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            v.getContext().startActivity(intent);

                        }

                        //pokud to něco je Edit
                        if(item.getTitle().equals(hoverEdit)){
                            String id = String.valueOf(zaznam.getCreatedTime());

                            //otevři Edit s animací
                            Intent intent = new Intent(v.getContext(), Edit.class);
                            intent.putExtra("id", id);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //nevim co to dela, ale bez toho to nefunguje
                            v.getContext().startActivity(intent);
                        }
                        return true;
                    }
                });

                //ukaž menu
                menu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return zaznamy.size();
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
