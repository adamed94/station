package com.station.bangoura.stationnew.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.station.bangoura.stationnew.R;
import com.station.bangoura.stationnew.models.CmdLivrs;
import com.station.bangoura.stationnew.models.Pompist;
import com.station.bangoura.stationnew.networkapi.ApiClient;
import com.station.bangoura.stationnew.networkapi.ProvisionningService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCmdPending extends RecyclerView.Adapter<AdapterCmdPending.VHCmdLivrs>  {

    ProvisionningService provisionningService = ApiClient.getRetrofit().create(ProvisionningService.class);
    private List<CmdLivrs> cmdlivrsList ;
    private OnCmdLivrsClickLister onCmdLivrsClickLister;

    public AdapterCmdPending(List<CmdLivrs> cmdlivrsList) {
        this.cmdlivrsList = cmdlivrsList;
    }

    public void setOnCmdLivrsClickLister(OnCmdLivrsClickLister onCmdLivrsClickLister) {
        this.onCmdLivrsClickLister = onCmdLivrsClickLister;
    }

    public List<CmdLivrs> getCmdLivrsList() {
        return cmdlivrsList;
    }

    public void setCmdLivrsList(List<CmdLivrs> cmdlivrsList) {
        this.cmdlivrsList = cmdlivrsList;
    }

    @NonNull
    @Override
    public AdapterCmdPending.VHCmdLivrs onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_command_pending,viewGroup,false) ;
        AdapterCmdPending.VHCmdLivrs VH = new AdapterCmdPending.VHCmdLivrs(itemView) ;
        return  VH ;
    }

    @Override
    public void onBindViewHolder(@NonNull VHCmdLivrs holder, int i) {
        holder.bind(cmdlivrsList.get(i));
    }


    @Override
    public int getItemCount() {
        return cmdlivrsList.size();
    }

    public class VHCmdLivrs extends RecyclerView.ViewHolder {
        private TextView dateCmd ,  station , volume ;
        private Button state , rec ;
        public VHCmdLivrs(@NonNull final View itemView) {


            super(itemView);
            dateCmd = (TextView)itemView.findViewById(R.id.tvDateComand);
            state = (Button) itemView.findViewById(R.id.tvStatut);
            rec = (Button)itemView.findViewById(R.id.btnReceipt);
            station = (TextView)itemView.findViewById(R.id.tvStation) ;
            volume =(TextView)itemView.findViewById(R.id.tvVolume);
            rec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(itemView.getContext() , cmdlivrsList.get(getLayoutPosition()).toString(), Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext()) ;
                    builder.setMessage("Voulez vous receptionner cette commande ?") ;
                    builder.setTitle("Confirmation") ;
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Call<CmdLivrs> cmdLivrsCall = provisionningService.receiptProvisionning(cmdlivrsList.get(getLayoutPosition()).getId());

                            cmdLivrsCall.enqueue(new Callback<CmdLivrs>() {
                                @Override
                                public void onResponse(Call<CmdLivrs> call, Response<CmdLivrs> response) {
                                    if(response.isSuccessful())
                                        Toast.makeText(itemView.getContext() , "RECEIPT", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(itemView.getContext() , "NOT RECEIPT", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<CmdLivrs> call, Throwable t) {
                                    Toast.makeText(itemView.getContext() , t.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });

                        }
                    }) ;
                    builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            System.out.println("Cancel Clicked");
                        }
                    }) ;
                    AlertDialog dialog = builder.create() ;
                    dialog.show();

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(cmdlivrsList.get(getLayoutPosition()));
                    //System.out.println(cmdlivrsList.get(getLayoutPosition()).getlg_cmdlivrs().get(getLayoutPosition()).getCarburanType().getName());

                    Toast.makeText(itemView.getContext() , cmdlivrsList.get(getLayoutPosition()).toString(), Toast.LENGTH_LONG).show();
                    Dialog dialog = new Dialog(itemView.getContext());
                    dialog.setContentView(R.layout.details_cmd_lvr);
                    dialog.setTitle("DETAILS COMMANDE");


                    TextView tvDateCmd = (TextView)dialog.findViewById(R.id.tvDateCmd) ;
                    TextView tvDateRcp = (TextView)dialog.findViewById(R.id.tvDateRcp) ;

                    TextView tvQtyEss = (TextView)dialog.findViewById(R.id.tvQtyEss) ;
                    TextView tvQtyGaz = (TextView)dialog.findViewById(R.id.tvQtyGaz) ;
                    TextView tvState = (TextView)dialog.findViewById(R.id.tvState) ;
                    tvDateCmd.setText(cmdlivrsList.get(getLayoutPosition()).getCreated_at());

                    tvState.setText(cmdlivrsList.get(getLayoutPosition()).getState());
                    int qtyEss = cmdlivrsList.get(getLayoutPosition()).getlg_cmdlivrs().get(0).getQuantity() ;
                    int qtyGaz = cmdlivrsList.get(getLayoutPosition()).getlg_cmdlivrs().get(1).getQuantity() ;
                    tvQtyEss.setText(qtyEss+" L");
                    tvQtyGaz.setText(qtyGaz+" L");


                    //TextView tv = (TextView)dialog.findViewById(R.id.dialogTv);
                   // tv.setText("This a custom dialog box ...");
                    dialog.show();
//                    if(onCmdLivrsClickLister!=null)
//                    {
//                        onCmdLivrsClickLister.onCmdLivrsClick(cmdlivrsList.get(getLayoutPosition()));
//                        Toast.makeText(itemView.getContext() , cmdlivrsList.get(getLayoutPosition()).toString(), Toast.LENGTH_LONG).show();
//                    }


                }
            });
        }


        public void bind(CmdLivrs cmdlivrs)
        {

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss",Locale.FRENCH);
            SimpleDateFormat formatt = new SimpleDateFormat("yyyy/MM/dd",Locale.JAPAN);
            Date date = null;
            String s = "" ;

            try {
                date = format.parse(cmdlivrs.getCreated_at());
                String p = String.valueOf(date);

                 s = p.substring(0,20);
                String DateToStr = format.format(date);
                System.out.println("Date  = "+DateToStr);
                Log.d("msg" ,s+"") ;
                //dateCmd.setText(s+"");

            } catch (ParseException e) {
                e.printStackTrace();
            }


            dateCmd.setText(cmdlivrs.getCreated_at().toString().substring(0,10) +"");
            if (cmdlivrs.getState().equals("pending")  )
                rec.setVisibility(View.VISIBLE);
            else if(cmdlivrs.getState().equals("receipt"))
                state.setVisibility(View.VISIBLE);
              //state.setText(cmdlivrs.getState());

            volume.setText(cmdlivrs.getQuantity()+" L");


        }
    }

    public interface OnCmdLivrsClickLister
    {
        void onCmdLivrsClick(CmdLivrs cmdLivrs);
    }
}
