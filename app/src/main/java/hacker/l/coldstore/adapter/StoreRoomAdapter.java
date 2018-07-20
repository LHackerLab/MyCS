package hacker.l.coldstore.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.fragments.StoreRoomFragment;
import hacker.l.coldstore.fragments.VardanaFragment;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.FontManager;
import hacker.l.coldstore.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class StoreRoomAdapter extends RecyclerView.Adapter<StoreRoomAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    StoreRoomFragment fragment;
    ProgressDialog pd;

    public StoreRoomAdapter(Context mContext, List<Result> userList, StoreRoomFragment fragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.fragment = fragment;
        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public StoreRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_store_room, parent, false);

        return new StoreRoomAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(StoreRoomAdapter.MyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#33A5DC86"));
        }
        holder.tv_name.setText(FilteruserList.get(position).getName());
        holder.tv_phone.setText(FilteruserList.get(position).getPhone());
        holder.tv_qty.setText(FilteruserList.get(position).getQty());
        holder.tv_type.setText(FilteruserList.get(position).getType());
        holder.tv_date.setText(FilteruserList.get(position).getDate());
        holder.tv_amount.setText(FilteruserList.get(position).getAmount());
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateStoreRoomData(true, FilteruserList.get(position).getName(), FilteruserList.get(position).getPhone(), FilteruserList.get(position).getQty(), FilteruserList.get(position).getType(), FilteruserList.get(position).getsRoomId(), FilteruserList.get(position).getAmount());
//                fragment.setFloorAdapter();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData(position);
            }
        });
    }

    private void deleteData(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Deleting wait......");
            pd.show();
            pd.setCancelable(false);
            DbHelper dbHelper = new DbHelper(mContext);
//            final Result result = dbHelper.getUserData();
//            if (result != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deleteStoreRoom,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            FilteruserList.remove(position);
                            fragment.setAdapter();
                            Toast.makeText(mContext, "Delete Successully", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("sRoomId", String.valueOf(FilteruserList.get(position).getsRoomId()));
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(stringRequest);
//            }
        } else {
            new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                    .show();
        }
    }

    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_phone, tv_amount, tv_date, tv_qty, tv_type, tv_edit, tv_delete;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
            tv_edit.setTypeface(materialdesignicons_font);
            tv_delete.setTypeface(materialdesignicons_font);
            tv_edit.setText(Html.fromHtml("&#xf64f;"));
            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
