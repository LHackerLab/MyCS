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
import android.widget.CheckBox;
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
import hacker.l.coldstore.fragments.SMSFragment;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.FontManager;
import hacker.l.coldstore.utility.Utility;

/**
 * Created by lalitsingh on 23/03/18.
 */

public class SMSAdapter extends RecyclerView.Adapter<SMSAdapter.MyViewHolder> {
    private Typeface materialdesignicons_font, ProximaNovaRegular;
    private Context mContext;
    private List<Result> userList, FilteruserList;
    ProgressDialog pd;
    SMSFragment.Type type;

    public SMSAdapter(Context mContext, List<Result> userList, SMSFragment.Type type) {
        this.mContext = mContext;
        this.userList = userList;
        this.FilteruserList = userList;
        this.type = type;
    }

    @Override
    public SMSAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sms, parent, false);

        return new SMSAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(SMSAdapter.MyViewHolder holder, final int position) {
        if (position % 2 == 1) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#33A5DC86"));
        }
        if (type.equals(SMSFragment.Type.Emp)) {
            holder.tv_name.setText("");
            holder.tv_phone.setText("");
            holder.tv_name.setText(FilteruserList.get(position).getEmpName());
            holder.tv_phone.setText(FilteruserList.get(position).getEmpPhone());
        } else {
            holder.tv_name.setText("");
            holder.tv_phone.setText("");
            holder.tv_name.setText(FilteruserList.get(position).getUserName());
            holder.tv_phone.setText(FilteruserList.get(position).getUserPhone());
        }
    }


    @Override
    public int getItemCount() {
        return FilteruserList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_phone;
        LinearLayout linearLayout;
        CheckBox checkbox;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}
