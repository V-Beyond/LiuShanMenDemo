package com.hpkj.gamesdk.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hpkj.gamesdk.R;
import com.hpkj.gamesdk.bean.AccountBean;
import com.hpkj.gamesdk.utils.KeWanDbUtils;
import com.hpkj.gamesdk.utils.ResourceUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;

/**
 * Created by 77429 on 2018/4/9.
 */

public class AccountListAdapter extends BaseAdapter {
    Context context;
    public    ArrayList<AccountBean> accountBeanArrayList;
    DbManager dbManager;
    Handler handler;
    public AccountListAdapter(Context context, ArrayList<AccountBean> accountBeanArrayList, Handler handler){
        this.context=context;
        this.accountBeanArrayList=accountBeanArrayList;
        this.handler=handler;
        dbManager= KeWanDbUtils.getDaoImpl(context);
    }
    @Override
    public int getCount() {
        return accountBeanArrayList==null?0:accountBeanArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return accountBeanArrayList==null?null:accountBeanArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void refresh(ArrayList<AccountBean> accountBeanArrayList){
        this.accountBeanArrayList=accountBeanArrayList;
        notifyDataSetChanged();
    }
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            int kewan_list_item= ResourceUtil.getLayoutId(context,"kewan_list_item");
            view = LayoutInflater.from(context).inflate(kewan_list_item, null);
            int id_text_view_spinner=ResourceUtil.getId(context,"text_view_spinner");
            int id_kewan_iv_clear=ResourceUtil.getId(context,"kewan_iv_clear");
            vh.text_view_spinner = (TextView)view.findViewById(id_text_view_spinner);
            vh.kewan_iv_clear = (ImageView)view.findViewById(id_kewan_iv_clear);
            view.setTag(vh);
        } else {
            vh = (ViewHolder)view.getTag();
        }
        vh.text_view_spinner.setText(accountBeanArrayList.get(i).getPhone());
        vh.kewan_iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    handler.sendMessage(handler.obtainMessage(2,accountBeanArrayList.get(i)));
            }
        });
        return view;
    }
    public  class ViewHolder
    {
        public TextView text_view_spinner;
        public ImageView kewan_iv_clear;
    }
}
