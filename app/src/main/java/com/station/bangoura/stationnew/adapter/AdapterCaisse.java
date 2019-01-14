package com.station.bangoura.stationnew.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.Caisse;
import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.CaisseService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCaisse extends RecyclerView.Adapter<AdapterCaisse.VHCaisse> {
    CaisseService caisseService = ApiClient.getRetrofit().create(CaisseService.class) ;
    private List<Caisse> listCaisse ;

    public AdapterCaisse(List<Caisse> listCaisse) {
        this.listCaisse = listCaisse;
    }

    @NonNull
    @Override
    public AdapterCaisse.VHCaisse onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_caisse,viewGroup,false) ;
        AdapterCaisse.VHCaisse VH = new AdapterCaisse.VHCaisse(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCaisse.VHCaisse holder, int postition) {
        holder.bind(listCaisse.get(postition));

    }

    @Override
    public int getItemCount() {
        return listCaisse == null ? 0 : listCaisse.size();
    }

    public  class VHCaisse extends RecyclerView.ViewHolder {
        private TextView tvMontant , tvState , tvDateAppro ;
        private Button btnOk , btnCancel , btnRec ;


        public VHCaisse(@NonNull final View itemView) {
            super(itemView);
            tvDateAppro = (TextView)itemView.findViewById(R.id.tvDateAppro) ;

            tvMontant =  (TextView)itemView.findViewById(R.id.tvMont) ;
            btnOk = (Button)itemView.findViewById(R.id.btnOk) ;
            btnCancel = (Button)itemView.findViewById(R.id.btnCancel) ;
            btnRec = (Button)itemView.findViewById(R.id.btnRec) ;
            btnRec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext()) ;
                    builder.setMessage("Voulez vous valider cet appro de caisse ?") ;
                    builder.setTitle("Confirmation") ;
                   final String station = "stationKey";
                   final String user = "userKey";
                   final SharedPreferences sharedPreference ;
                    sharedPreference = itemView.getContext().getSharedPreferences("mypref", itemView.getContext().MODE_PRIVATE);

                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Call<String> caisseCall = caisseService.validAppro(listCaisse.get(getLayoutPosition()).getId() ,sharedPreference.getInt(user, 1 ) ) ;
                            caisseCall.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    if(response.isSuccessful() && response.code()==200)
                                        Toast.makeText(itemView.getContext() , "Validé !", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(itemView.getContext() , "Ooops ! Une erreur est survenue ."  , Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(itemView.getContext() , "Ooops ! Une erreur est survenue . Vérifiez votre connexion .", Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    }) ;

                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }) ;
                    AlertDialog dialog = builder.create() ;
                    dialog.show();


                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });



        }

        public void bind(Caisse caisse){


            String y , d , m  ;
            y = caisse.getCreated_at().toString().substring(0,4) ;
            m = caisse.getCreated_at().toString().substring(5,7) ;
            d = caisse.getCreated_at().toString().substring(8,10) ;

            tvDateAppro.setText(d+"/"+m+"/"+y );



            if (caisse.getState().equals("pending")  )
                btnRec.setVisibility(View.VISIBLE);
            else if(caisse.getState().equals("receipt"))
                btnOk.setVisibility(View.VISIBLE);
            else
                btnCancel.setVisibility(View.VISIBLE);

            tvMontant.setText(caisse.getAmount()+" GNF");

        }
    }
}
