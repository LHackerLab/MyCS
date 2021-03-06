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
import hacker.l.coldstore.fragments.FloorFragment;
import hacker.l.coldstore.fragments.RackFragment;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.FontManager;
import hacker.l.coldstore.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class RackAdapter extends RecyclerView.Adapter<RackAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    RackFragment fragment;
    ProgressDialog pd;
    double fillQuantity = 0.0, remainQty = 0.0;

    public RackAdapter(Context mContext, List<Result> userList, RackFragment fragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.fragment = fragment;
        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public RackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rack, parent, false);

        return new RackAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RackAdapter.MyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#33A5DC86"));
        }
        String rack = FilteruserList.get(position).getRack();
        String capacity = FilteruserList.get(position).getCapacity();
        int floor = FilteruserList.get(position).getFloor();
        holder.tv_rack.setText(rack);
        holder.tv_floor.setText(String.valueOf(floor));
        holder.tv_capacity.setText(capacity);
        DbHelper dbHelper = new DbHelper(mContext);
        List<Result> result = dbHelper.getAllInwardDataByRackFloor(rack, floor);
        if (result != null && result.size() != 0) {
            for (int i = 0; i < result.size(); i++) {
                //get inward and send in outward data  and get remaing qty(fill capacity)
                String qty = result.get(i).getQuantity();
                if (qty != null && !qty.equalsIgnoreCase("")) {
                    double fqty = Double.parseDouble(qty);
                    fillQuantity = fillQuantity + fqty;
                }
            }
            remainQty = Double.parseDouble(capacity) - fillQuantity;
            holder.tv_Fillcapacity.setText(fillQuantity + "");
            holder.tv_Freecapacity.setText(remainQty + "");
        } else {
            holder.tv_Fillcapacity.setText("0");
            holder.tv_Freecapacity.setText(capacity);
        }
        if (Double.parseDouble(capacity) == fillQuantity) {
            holder.linearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.ic_check_black_24dp));
        }
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateRackData(true, FilteruserList.get(position).getRack(), FilteruserList.get(position).getCapacity(), FilteruserList.get(position).getRackId());
//                fragment.setRackAdapter();
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
            final DbHelper dbHelper = new DbHelper(mContext);
//            final Result result = dbHelper.getUserData();
//            if (result != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deleteRack,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            dbHelper.deleteRackData(FilteruserList.get(position).getRackId());
                            FilteruserList.remove(position);
//                            fragment.setRackAdapter();
                            notifyDataSetChanged();
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
                    params.put("rackId", String.valueOf(FilteruserList.get(position).getRackId()));
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
        TextView tv_rack, tv_capacity, tv_edit, tv_delete, tv_floor, tv_Fillcapacity, tv_Freecapacity;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_rack = (TextView) itemView.findViewById(R.id.tv_rack);
            tv_capacity = (TextView) itemView.findViewById(R.id.tv_capacity);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_floor = (TextView) itemView.findViewById(R.id.tv_floor);
            tv_Fillcapacity = (TextView) itemView.findViewById(R.id.tv_Fillcapacity);
            tv_Freecapacity = (TextView) itemView.findViewById(R.id.tv_Freecapacity);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
//            tv_rack.setTypeface(ProximaNovaRegular);
//            tv_capacity.setTypeface(ProximaNovaRegular);
            tv_edit.setTypeface(materialdesignicons_font);
            tv_delete.setTypeface(materialdesignicons_font);
            tv_edit.setText(Html.fromHtml("&#xf64f;"));
            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
