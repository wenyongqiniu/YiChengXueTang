package com.example.yichengxuetang.activitys.learningactivitys;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.load.resource.transcode.TranscoderRegistry;
import com.example.yichengxuetang.R;
import com.example.yichengxuetang.adapter.CourseTimeAdapter;
import com.example.yichengxuetang.bean.AddAddressResponse;
import com.example.yichengxuetang.bean.AddressListResponse;
import com.example.yichengxuetang.bean.CommitSuccessResponse;
import com.example.yichengxuetang.contract.EditAddressContract;
import com.example.yichengxuetang.utils.CoustomerDialog;
import com.example.yichengxuetang.utils.HidePhone;
import com.example.yichengxuetang.utils.ToastUtils;
import com.ljb.page.PageState;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.llw.mvplibrary.mvp.MvpActivity;


public class EditActivity extends MvpActivity<EditAddressContract.EditAddressPresenter> implements EditAddressContract.EditAddressView {

    private CityPicker cityPicker;
    private String realName = "";
    private String prove = "";
    private String city = "";
    private String area = "";
    private String addressDetail = "";
    private String mobile = "";
    private String id = "";
    private int isDefault;
    private int directUse = 1;
    private String packageId ="" ;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch st_default;

    private void initView() {
        Intent intent = getIntent();
        AddressListResponse.DataBean addressData = (AddressListResponse.DataBean) intent.getSerializableExtra("addressData");
        //packageId = intent.getStringExtra("packageId");
        TextView area_des = findViewById(R.id.area_des);
        TextView tv_sure = findViewById(R.id.tv_sure);
        TextView tv_delete_address = findViewById(R.id.tv_delete_address);
        EditText ed_receiver_name = findViewById(R.id.ed_receiver_name);
        EditText ed_receiver_phone = findViewById(R.id.ed_receiver_phone);
        EditText ed_receiver_address = findViewById(R.id.ed_receiver_address);
        RelativeLayout rl_left = findViewById(R.id.rl_left);
        st_default = findViewById(R.id.st_default);


        rl_left.setOnClickListener(v -> finish());
        if (addressData != null) {
            tv_delete_address.setVisibility(View.VISIBLE);
            id = addressData.getId();
            realName = addressData.getRealName();
            mobile = addressData.getMobile();
            prove = addressData.getProve();
            city = addressData.getCity();
            area = addressData.getArea();
            addressDetail = addressData.getAddressDetail();
            ed_receiver_name.setText(realName);
            String phone = HidePhone.phoneNumber(mobile);
            ed_receiver_phone.setText(phone);
            ed_receiver_address.setText(addressDetail);
            area_des.setText(prove+"  " + city+"  " + area);
            if (addressData.getIsDefault() == 1) {
                isDefault = 1;
                st_default.setChecked(true);
            } else {
                isDefault = 0;
                st_default.setChecked(false);
            }

        }

        tv_delete_address.setOnClickListener(v -> {
            CoustomerDialog.Builder builder = new CoustomerDialog.Builder(context);
            builder.setTitle("是否删除该地址");
            builder.setMessage("");
            builder.setPositiveButton("确定", (dialog, which) -> {
                dialog.dismiss();
                mPresenter.getDeleteAddress(id);
            });

            builder.setNegativeButton("取消",
                    (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });

        cityPicker = new CityPicker.Builder(EditActivity.this)
                .textSize(16)//滚轮文字的大小
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#FFFFFF")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("北京市")
                .city("北京市")
                .district("昌平区")
                .textColor(Color.parseColor("#FE8000"))//滚轮文字的颜色
                .provinceCyclic(false)//省份滚轮是否循环显示
                .cityCyclic(false)//城市滚轮是否循环显示
                .districtCyclic(false)//地区（县）滚轮是否循环显示
                .visibleItemsCount(7)//滚轮显示的item个数
                .itemPadding(10)//滚轮item间距
                .onlyShowProvinceAndCity(false)
                .build();
        area_des.setOnClickListener(v -> cityPicker.show());
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                prove = citySelected[0];
                //城市
                city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                area = citySelected[2];
                //邮编
                //String code = citySelected[3];
                area_des.setText(prove + city + area);
            }

            @Override
            public void onCancel() {

            }
        });
        //姓名
        ed_receiver_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                realName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //手机
        ed_receiver_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //详细地址
        ed_receiver_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addressDetail = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //switch监听
        st_default.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isDefault == 0) {
                isDefault = 1;
            } else {
                isDefault = 0;
            }
        });
        //保存并使用
        tv_sure.setOnClickListener(v -> {
            if (addressData != null) {//编辑
                if (realName.length() > 0 && prove.length() > 0 && city.length() > 0 && area.length() > 0 && addressDetail.length() > 0 && mobile.length() > 0) {
                    directUse=0;
                    mPresenter.getChangeAddress(id, realName, prove, city, area, addressDetail, mobile, isDefault,directUse,packageId);
                } else {
                    ToastUtils.showShort(EditActivity.this, getString(R.string.fill_complete));
                }
            } else {//新增
                if (realName.length() > 0 && prove.length() > 0 && city.length() > 0 && area.length() > 0 && addressDetail.length() > 0 && mobile.length() > 0) {
                    directUse=0;
                    mPresenter.getChangeAddress(id, realName, prove, city, area, addressDetail, mobile, isDefault,directUse,packageId);
                } else {
                    ToastUtils.showShort(EditActivity.this, getString(R.string.fill_complete));
                }
            }
        });
    }

    @Override
    public void getEditAddress(AddAddressResponse wallPaperResponse) {
        if (wallPaperResponse.getCode() == 0) {
            ToastUtils.showShort(EditActivity.this, "添加成功");
            Intent intent = getIntent();
            Bundle bundle =new Bundle();
            //传输的内容仍然是键值对的形式
            bundle.putString("address",wallPaperResponse.getData().getAddressDetail());//回发的消息,hello world from secondActivity!
            bundle.putString("name",wallPaperResponse.getData().getRealName());//回发的消息,hello world from secondActivity!
            bundle.putString("phone",wallPaperResponse.getData().getMobile());//回发的消息,hello world from secondActivity!
            bundle.putString("addressId",wallPaperResponse.getData().getId());//回发的消息,hello world from secondActivity!
            intent.putExtras(bundle);
            setResult(RESULT_OK,intent);
            finish();
        } else {
            ToastUtils.showShort(EditActivity.this, wallPaperResponse.getMessage() + "");
        }
    }

    @Override
    public void getAddressList(AddressListResponse wallPaperResponse) {

    }

    @Override
    public void getDeleteAddress(CommitSuccessResponse wallPaperResponse) {

    }

    @Override
    public void getFailed(Throwable e) {
        ToastUtils.showShort(EditActivity.this, e.getMessage() + "");
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit;
    }

    @Override
    protected EditAddressContract.EditAddressPresenter createPresenter() {
        return new EditAddressContract.EditAddressPresenter();
    }
}