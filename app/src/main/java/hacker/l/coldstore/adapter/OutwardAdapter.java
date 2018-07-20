package hacker.l.coldstore.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.fragments.AccoutnFragment;
import hacker.l.coldstore.fragments.InwardDetailsFragment;
import hacker.l.coldstore.fragments.OutwardDetailsFragment;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.FontManager;
import hacker.l.coldstore.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class OutwardAdapter extends RecyclerView.Adapter<OutwardAdapter.MyViewHolder> implements Filterable {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    OutwardDetailsFragment fragment;
    ProgressDialog pd;

    public OutwardAdapter(Context mContext, List<Result> userList, OutwardDetailsFragment fragment) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.fragment = fragment;
        this.ProximaNovaRegular = FontManager.getFontTypeface(mContext, "fonts/ProximaNova-Regular.otf");
        this.materialdesignicons_font = FontManager.getFontTypefaceMaterialDesignIcons(mContext, "fonts/materialdesignicons-webfont.otf");
    }

    @Override
    public OutwardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_outward, parent, false);

        return new OutwardAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OutwardAdapter.MyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#44eebd82"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#44eebd82"));
        }
        holder.tv_saleno.setText("" + FilteruserList.get(position).getInwardId());
        holder.tv_name.setText(FilteruserList.get(position).getUserName());
        holder.tv_Fname.setText(FilteruserList.get(position).getFatherName());
        holder.tv_phone.setText(FilteruserList.get(position).getUserPhone());
        holder.tv_address.setText(FilteruserList.get(position).getAddress());
        holder.tv_variety.setText(FilteruserList.get(position).getVarietyName());
        holder.tv_rent.setText(FilteruserList.get(position).getRent() + "(rs)");
        holder.tv_rack.setText(FilteruserList.get(position).getRack());
        holder.tv_qty.setText(FilteruserList.get(position).getQuantity());
        holder.tv_Departure.setText(FilteruserList.get(position).getDeparture());
        holder.tv_advanced.setText(FilteruserList.get(position).getAdvanced() + "(rs)");
        holder.tv_casetype.setText(FilteruserList.get(position).getCaseType());
        holder.tv_grandotal.setText(FilteruserList.get(position).getGrandTotal() + "(rs)");
        holder.tv_time.setText(FilteruserList.get(position).getTime() + "/" + FilteruserList.get(position).getDay());
        holder.tv_byusr.setText(FilteruserList.get(position).getByUser());
        holder.tv_floor.setText(String.valueOf(FilteruserList.get(position).getFloor()));
        holder.tv_Rqty.setText(Double.parseDouble(FilteruserList.get(position).getQuantity()) - (Double.parseDouble(FilteruserList.get(position).getDeparture())) + "");

        holder.tv_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccoutnFragment fragment = AccoutnFragment.newInstance("payment", "", "", "", "", "", "", "", "", 0, FilteruserList.get(position).getInwardId(), "");
                moveragment(fragment);
            }
        });
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragment.updateVarietyData(true, FilteruserList.get(position).getVarietyName(), FilteruserList.get(position).getVarietyId());
//                varietyFragment.setVarietyAdapter();
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVariety(position);
            }
        });
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void deleteVariety(final int position) {
        if (Utility.isOnline(mContext)) {
            pd = new ProgressDialog(mContext);
            pd.setMessage("Deleting wait......");
            pd.show();
            pd.setCancelable(false);
            final DbHelper dbHelper = new DbHelper(mContext);
//            final Result result = dbHelper.getUserData();
//            if (result != null) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.deleteOutward,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
//                            fragment.setVarietyAdapter();
                            dbHelper.deleteOutwardData(FilteruserList.get(position).getOutwardId());
                            FilteruserList.remove(position);
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
                    params.put("inwardId", String.valueOf(FilteruserList.get(position).getInwardId()));
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().trim();
                // name match condition. this might differ depending on your requirement
                // here we are looking for name or phone number match
                if (charString.isEmpty()) {
                    FilteruserList = userList;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row : userList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getUserName().toLowerCase().trim().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }


                    FilteruserList = filteredList;
                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = FilteruserList;
                return filterResults;
            }


            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                FilteruserList = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_saleno, tv_name, tv_Fname, tv_Departure, tv_Rqty, tv_payment, tv_time, tv_phone, tv_grandotal, tv_byusr, tv_address, tv_casetype, tv_floor, tv_rack, tv_qty, tv_rent, tv_variety, tv_advanced, tv_edit, tv_delete;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_saleno = (TextView) itemView.findViewById(R.id.tv_saleno);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_Departure = (TextView) itemView.findViewById(R.id.tv_Departure);
            tv_Fname = (TextView) itemView.findViewById(R.id.tv_Fname);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_variety = (TextView) itemView.findViewById(R.id.tv_variety);
            tv_floor = (TextView) itemView.findViewById(R.id.tv_floor);
            tv_rack = (TextView) itemView.findViewById(R.id.tv_rack);
            tv_qty = (TextView) itemView.findViewById(R.id.tv_qty);
            tv_rent = (TextView) itemView.findViewById(R.id.tv_rent);
            tv_advanced = (TextView) itemView.findViewById(R.id.tv_advanced);
            tv_casetype = (TextView) itemView.findViewById(R.id.tv_casetype);
            tv_grandotal = (TextView) itemView.findViewById(R.id.tv_grandotal);
            tv_Rqty = (TextView) itemView.findViewById(R.id.tv_Rqty);
            tv_byusr = (TextView) itemView.findViewById(R.id.tv_byusr);
            tv_payment = (TextView) itemView.findViewById(R.id.tv_payment);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);


            tv_delete = (TextView) itemView.findViewById(R.id.tv_delete);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
//            tv_edit.setTypeface(materialdesignicons_font);
//            tv_delete.setTypeface(materialdesignicons_font);
//            tv_edit.setText(Html.fromHtml("&#xf64f;"));
//            tv_delete.setText(Html.fromHtml("&#xf5ad;"));
        }
    }
}
