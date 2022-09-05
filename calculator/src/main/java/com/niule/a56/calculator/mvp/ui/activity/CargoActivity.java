package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import com.niule.a56.calculator.mvp.presenter.FreightPresenter;
import com.niule.a56.calculator.mvp.ui.CartonInfoChangeListener;
import com.niule.a56.calculator.mvp.ui.adapter.CartonAdapter;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CargoActivity extends BaseActivity implements View.OnClickListener, CartonInfoChangeListener, FreightContract.View {

    private TextView tvTotalWeight;
    private TextView tvTotalCarton;

    private TextView etTotalGrossWeight;
    private TextView etTotalVolumeWeight;

    private RadioButton rbCartonInfo;
    private RadioButton rbGrossInfo;

    private LinearLayout llCartonsInfo;     //箱子列表
    private LinearLayout llGrossInfo;       //总数输入

    private LinearLayout llTotalCartons;

    private TextView tvAddCarton;
    private TextView tvPrev;
    private TextView tvNext;

    private Options options;
    private final List<CartonInfo> cartonInfoList = new ArrayList<>();

    private RecyclerView xRecyclerView;
    private CartonAdapter cartonAdapter;

    private FreightPresenter freightPresenter;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_cargo;
    }

    @Override
    protected void onInitView() {

        freightPresenter = new FreightPresenter(this,this);
        initView();

        initOptions();

        initViews();

        setStatusBarPaddingWithPrimaryColor();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        cartonAdapter = new CartonAdapter(this,R.layout.item_carton, cartonInfoList);
        cartonAdapter.setCartonListener(this);
        xRecyclerView.setAdapter(cartonAdapter);

        initData();
    }

    private void initOptions(){
        //获取缓存数据
        options = PreferencesUtil.getOptions();
        if (options == null) {
            options = new Options();
        }
        if(options.getCartonInfoList()!=null && options.getCartonInfoList().size()>0){
            cartonInfoList.addAll(options.getCartonInfoList());
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initData() {
        if(cartonInfoList.size()==0) {
            CartonInfo info = new CartonInfo();
            info.setQuantity(1);
            cartonInfoList.add(info);
        }
        cartonAdapter.notifyDataSetChanged();

        freightPresenter.getPriceRow(options);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
////        initData();
//    }

    @SuppressLint("NotifyDataSetChanged")
    private void initViews() {
        tvTitle.setText("货物参数");

        tvTotalWeight = findViewById(R.id.tvTotalWeight);
        tvTotalCarton = findViewById(R.id.tvTotalCarton);
        etTotalGrossWeight = findViewById(R.id.etTotalGrossWeight);
        etTotalVolumeWeight = findViewById(R.id.etTotalVolumeWeight);

        rbCartonInfo = findViewById(R.id.rbCartonInfo);
        rbGrossInfo = findViewById(R.id.rbGrossInfo);
        xRecyclerView = findViewById(R.id.xRecyclerView);

        llCartonsInfo = findViewById(R.id.llCartonsInfo);
        llGrossInfo = findViewById(R.id.llGrossInfo);

        llTotalCartons = findViewById(R.id.llTotalCartons);

        tvAddCarton = findViewById(R.id.tvAddCarton);

        tvPrev = findViewById(R.id.tvPrev);
        tvNext = findViewById(R.id.tvNext);

        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        tvAddCarton.setOnClickListener(v -> {
            CartonInfo cartonInfo = new CartonInfo();
            cartonInfo.setQuantity(1);
            cartonInfoList.add(cartonInfo);

            cartonAdapter.notifyDataSetChanged();
            calcuCartonsWeight();
        });

        if(options.getCurrent()==0){
            rbCartonInfo.setChecked(true);
            rbGrossInfo.setChecked(false);
            activeCartonLayout();
        }else{
            rbCartonInfo.setChecked(false);
            rbGrossInfo.setChecked(true);
            activeGrossLayout();
        }

        if(options.getBulkWeight()>0){
            etTotalGrossWeight.setText(options.getBulkWeight()+"");
        }else{
            etTotalGrossWeight.setText("");
        }

        if(options.getBulkVolume()>0){
            etTotalVolumeWeight.setText(options.getBulkVolume()+"");
        }else{
            etTotalVolumeWeight.setText("");
        }

        rbCartonInfo.setOnClickListener(v -> {
            activeCartonLayout();
            calcuCartonsWeight();
        });

        rbGrossInfo.setOnClickListener(v -> {
            activeGrossLayout();

            String sWeight = etTotalGrossWeight.getText().toString().trim();
            float totalWeight = 0;
            if (HokasUtils.isNoEmpty(sWeight)) {
                totalWeight = BigDecimal.valueOf(Float.parseFloat(sWeight)).setScale(2,RoundingMode.HALF_UP).floatValue();
            }
            tvTotalWeight.setText(totalWeight+"");
            if(totalWeight>0){
                enableBtn(tvNext);
            }else{
                disableBtn(tvNext);
            }
        });

        disableBtn(tvNext);

        etTotalGrossWeight.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                String sWeight = etTotalGrossWeight.getText().toString().trim();
                float totalWeight = 0;
                float totalVolume = 0;
                if (HokasUtils.isNoEmpty(sWeight)) {
                    totalWeight = BigDecimal.valueOf(Float.parseFloat(sWeight)).setScale(2,RoundingMode.HALF_UP).floatValue();
                    totalVolume = BigDecimal.valueOf(totalWeight*options.getVolumeRate()/1000000).setScale(1,RoundingMode.HALF_UP).floatValue();
                }
                etTotalGrossWeight.setText(totalWeight+"");
                etTotalVolumeWeight.setText(totalVolume+"");

                tvTotalWeight.setText(totalWeight+"");

                if(totalWeight>0){
                    enableBtn(tvNext);
                }else{
                    disableBtn(tvNext);
                }
            }
        });

        etTotalVolumeWeight.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                String sVolume = etTotalVolumeWeight.getText().toString().trim();
                float totalWeight = 0;
                float totalVolume = 0;
                if(HokasUtils.isNoEmpty(sVolume)){
                    totalWeight = BigDecimal.valueOf(Float.parseFloat(sVolume)*1000000/options.getVolumeRate()).setScale(2,RoundingMode.HALF_UP).floatValue();
                    totalVolume = BigDecimal.valueOf(Float.parseFloat(sVolume)).setScale(2,RoundingMode.HALF_UP).floatValue();
                }
                etTotalGrossWeight.setText(totalWeight+"");
                etTotalVolumeWeight.setText(totalVolume+"");

                tvTotalWeight.setText(totalWeight+"");

                if(totalWeight>0){
                    enableBtn(tvNext);
                }else{
                    disableBtn(tvNext);
                }
            }
        });
    }

    private void activeCartonLayout(){
        llCartonsInfo.setVisibility(View.VISIBLE);
        llGrossInfo.setVisibility(View.GONE);
        ColorStateList colorPrimary = getBaseContext().getResources().getColorStateList(R.color.colorPrimary);
        rbGrossInfo.setTextColor(colorPrimary);
        ColorStateList white = getBaseContext().getResources().getColorStateList(R.color.white);
        rbCartonInfo.setTextColor(white);

        llTotalCartons.setVisibility(View.VISIBLE);
    }

    private void activeGrossLayout(){
        llCartonsInfo.setVisibility(View.GONE);
        llGrossInfo.setVisibility(View.VISIBLE);
        ColorStateList colorPrimary = getBaseContext().getResources().getColorStateList(R.color.colorPrimary);
        rbCartonInfo.setTextColor(colorPrimary);
        ColorStateList white = getBaseContext().getResources().getColorStateList(R.color.white);
        rbGrossInfo.setTextColor(white);

        llTotalCartons.setVisibility(View.GONE);
    }

    private void disableBtn(TextView view){
        Drawable backColor = getBaseContext().getResources().getDrawable(R.color.colorPrimaryDark);
        ColorStateList fontColor = getBaseContext().getResources().getColorStateList(R.color.color_text_999999);
        view.setBackgroundDrawable(backColor);
        view.setTextColor(fontColor);
        view.setOnClickListener(null);
    }

    private void enableBtn(TextView view){
        Drawable backColor = getBaseContext().getResources().getDrawable(R.color.colorPrimary);
        ColorStateList fontColor = getBaseContext().getResources().getColorStateList(R.color.white);
        view.setBackgroundDrawable(backColor);
        view.setTextColor(fontColor);
        view.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (HokasUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tvPrev:  //点击了上一步
                options.setCartonInfoList(cartonInfoList);
                saveBulkInfoToOptions();
                PreferencesUtil.setOptions(options);
                finish();
                break;
            case R.id.tvNext:  //点击了下一步
                nextPage();
                break;
        }
    }

    private void saveBulkInfoToOptions(){
        if(rbCartonInfo.isChecked()){
            options.setCurrent(0);
        }else{
            options.setCurrent(1);
            if(HokasUtils.isNoEmpty(etTotalGrossWeight.getText().toString())) {
                options.setBulkWeight(BigDecimal.valueOf(Float.parseFloat(etTotalGrossWeight.getText().toString())).setScale(0, RoundingMode.UP).intValue());
            }
            if(HokasUtils.isNoEmpty(etTotalVolumeWeight.getText().toString())) {
                options.setBulkVolume(BigDecimal.valueOf(Float.parseFloat(etTotalVolumeWeight.getText().toString())).setScale(2, RoundingMode.UP).intValue());
            }
        }
    }

    private void nextPage() {
        saveBulkInfoToOptions();
        options.setTotalWeight(BigDecimal.valueOf(Float.parseFloat(tvTotalWeight.getText().toString())).setScale(0,RoundingMode.UP).intValue());
        PreferencesUtil.setOptions(options);
        intent2Activity(ResultActivity.class);
    }

    @Override
    public void onCartonInfoChanged(CartonInfo cartonInfo, int position) {
        CartonInfo info = cartonInfoList.get(position);
        if(info!=null){
            info.setWeight(cartonInfo.getWeight());
            info.setLength(cartonInfo.getLength());
            info.setWidth(cartonInfo.getWidth());
            info.setHeight(cartonInfo.getHeight());
            info.setQuantity(cartonInfo.getQuantity());
        }
        calcuCartonsWeight();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRemoveCarton(int position) {
        if(position<cartonInfoList.size()){
            cartonInfoList.remove(position);
            cartonAdapter.notifyDataSetChanged();
            calcuCartonsWeight();
        }
    }

    private void calcuCartonsWeight(){
        int totalCartons = 0;
        float volumeWeight = 0;
        float totalWeight;
        float weight = 0;

        for(CartonInfo info: cartonInfoList){
            totalCartons += info.getQuantity();

            volumeWeight += Math.ceil(info.getLength() * info.getWidth() * info.getHeight() / options.getVolumeRate()) * info.getQuantity();
            if (info.getWeight()>0) {
                weight += info.getWeight() * info.getQuantity();
            }
        }
        totalWeight = BigDecimal.valueOf(Math.max(volumeWeight, weight)).setScale(2, RoundingMode.HALF_UP).floatValue();

        tvTotalCarton.setText(totalCartons+"");
        tvTotalWeight.setText(totalWeight+"");
        if(totalWeight>0){
            enableBtn(tvNext);
        }else{
            disableBtn(tvNext);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showLoading() {
        showLoadingView();
    }

    @Override
    public void hideLoading() {
        hideLoadingView();
    }

    @Override
    public void showMessage(String message) {
        UiUtils.makeText(message);
    }

    @Override
    public void launchActivity(Intent intent) {

    }

    @Override
    public void killMyself() {

    }

    @Override
    public void onError(String errMsg) {

    }

    @Override
    public void onContinentPackData(List<ContinentPack> continentList) {

    }

    @Override
    public void onFreightPackData(List<FreightPack> freightPackList) {

    }

    @Override
    public void onFreightPackError() {

    }

    @Override
    public void onDivisionPackData(DivisionPack divisionPack) {

    }

    @Override
    public void onPriceRowData(PriceRow priceRow) {
        if(priceRow!=null){
            options.setChargeUnitId((int)priceRow.getChargeUnitId());
            options.setVolumeRate(priceRow.getVolumeRate());

            calcuCartonsWeight();
        }
    }
}
