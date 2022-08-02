package com.example.yichengxuetang.activitys.learningactivitys;


import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.AddressListAdapter;
import com.example.yichengxuetang.bean.AddAddressResponse;
import com.example.yichengxuetang.bean.AddressListResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.contract.EditAddressContract;
import com.ljb.page.PageState;
import com.ljb.page.PageStateLayout;
import com.llw.mvplibrary.mvp.MvpActivity;

import java.util.ArrayList;

public class OtherAddressActivity extends MvpActivity<EditAddressContract.EditAddressPresenter> implements EditAddressContract.EditAddressView {


    private PageStateLayout page_layout;
    private RecyclerView rv_address_list;
    private TextView tv_sure;
    private AddressListAdapter addressListAdapter;
    private ArrayList<AddressListResponse.DataBean> dataBeans;

    @Override
    protected EditAddressContract.EditAddressPresenter createPresenter() {
        return new EditAddressContract.EditAddressPresenter();
    }

    @Override
    public void getEditAddress(AddAddressResponse wallPaperResponse) {

    }

    @Override
    public void getAddressList(AddressListResponse wallPaperResponse) {
        if (wallPaperResponse.getCode()==0){
            page_layout.setPage(PageState.STATE_SUCCESS);
            dataBeans.addAll(wallPaperResponse.getData());
            addressListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getDeleteAddress(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBeans.clear();
        mPresenter.getAddressList();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        page_layout = findViewById(R.id.page_layout);
        page_layout.setPage(PageState.STATE_LOADING);
        page_layout.setContentView(R.layout.activity_other_address);
        rv_address_list = findViewById(R.id.rv_address_list);
        tv_sure = findViewById(R.id.tv_sure);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        rl_left.setOnClickListener(v -> {
            finish();
        });


        dataBeans = new ArrayList<>();
        addressListAdapter = new AddressListAdapter(R.layout.item_address, dataBeans);
        rv_address_list.setLayoutManager(new LinearLayoutManager(this));
        rv_address_list.setAdapter(addressListAdapter);

       addressListAdapter.setOnItemClickListener((adapter, view, position) -> {
           Intent intent = getIntent();
           Bundle bundle =new Bundle();
           //传输的内容仍然是键值对的形式
           bundle.putString("address",dataBeans.get(position).getAddressDetail());//回发的消息,hello world from secondActivity!
           bundle.putString("name",dataBeans.get(position).getRealName());//回发的消息,hello world from secondActivity!
           bundle.putString("phone",dataBeans.get(position).getMobile());//回发的消息,hello world from secondActivity!
           bundle.putString("addressId",dataBeans.get(position).getId());//回发的消息,hello world from secondActivity!
           intent.putExtras(bundle);
           setResult(RESULT_OK,intent);
           finish();
       });

        //新增地址
        tv_sure.setOnClickListener(v -> startActivity(new Intent(OtherAddressActivity.this,EditActivity.class)));
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_layout;
    }
}