package com.softgarden.garden.view.historyOrders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.softgarden.garden.global.BaseActivity;
import com.softgarden.garden.interfaces.ModifyCountInterface;
import com.softgarden.garden.jiadun_android.R;
import com.softgarden.garden.view.historyOrders.adapter.ModifyOrderAdapter;

import java.util.ArrayList;

public class OrderDetailActivity extends BaseActivity implements ModifyCountInterface{


    private ListView listview;
    private ArrayList<String> mData;
    private ModifyOrderAdapter adapter;
    private Button btn_modify;
    private RelativeLayout rl_confirm;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order_detail);

        btn_modify = getViewById(R.id.btn_modify);
        rl_confirm = getViewById(R.id.rl_confirm);

        virtualData();
        listview = getViewById(R.id.listview);
        adapter = new ModifyOrderAdapter(mData, this);
        adapter.setModifyCountInterface(this);
        listview.setAdapter(adapter);
        addFooter();
    }

    @Override
    protected void setListener() {
        btn_modify.setOnClickListener(this);
        getViewById(R.id.btn_cancel).setOnClickListener(this);
        getViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_modify:
                rl_confirm.setVisibility(View.VISIBLE);
                btn_modify.setVisibility(View.GONE);
                adapter.setEditable(true);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_cancel:
                rl_confirm.setVisibility(View.GONE);
                btn_modify.setVisibility(View.VISIBLE);
                adapter.setEditable(false);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_confirm:
                // 重新提交订单
                break;
        }
    }

    private void addFooter() {
        View footer_remark = LayoutInflater.from(this).inflate(R.layout.layout_footer_order_list,
                listview, false);
        listview.addFooterView(footer_remark);
    }

    private void virtualData() {
        mData = new ArrayList<>();
        for(int i=0;i<20;i++){
            mData.add("面包"+(i+1));
        }
    }


    @Override
    public void doIncrease(EditText et_total, int position, String currentCount) {
        if(TextUtils.isEmpty(currentCount)){
            et_total.setText("1");
        }else{
            int total = Integer.parseInt(currentCount);
            et_total.setText((++total)+"");
        }
    }

    @Override
    public void doDecrease(EditText editText, int position, String currentCount) {
        if(TextUtils.isEmpty(currentCount)){
            Toast.makeText(this,"宝贝不能再少了哦！",Toast.LENGTH_SHORT).show();
        }else{
            int total = Integer.parseInt(currentCount);
            if(total>1){
                total--;
                editText.setText(total+"");
            }
        }
    }
}
