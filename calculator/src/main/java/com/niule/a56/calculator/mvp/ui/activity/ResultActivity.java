package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hokaslibs.utils.HokasUtils;
import com.hokaslibs.utils.recycler.XRecyclerViewHelper;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.ChargeUnit;
import com.niule.a56.calculator.bean.Options;
import com.niule.a56.calculator.bean.PriceExtra;
import com.niule.a56.calculator.bean.PriceItemPack;
import com.niule.a56.calculator.mvp.contract.PriceContract;
import com.niule.a56.calculator.mvp.presenter.PricePresenter;
import com.niule.a56.calculator.mvp.ui.adapter.CartonAdapter;
import com.niule.a56.calculator.mvp.ui.adapter.PriceExtraAdapter;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity implements PriceContract.View {

    private TextView tvCountryName;
    private TextView tvFreightLineName;
    private TextView tvChargeName;
    private TextView tvTotalWeight;
    private TextView tvChargeUnitName;
    private TextView tvCargoName;
    private TextView tvNoSum;
    private TextView tvTotalPrice;
    private TextView tvTimeMemo;

    private LinearLayout llPriceContainer;

    private TextView tvFreightPrice;

    private Options options;

    private PricePresenter pricePresenter;
    private List<ChargeUnit> chargeUnitList;

    private RecyclerView xRecyclerView;
    private PriceExtraAdapter priceExtraAdapter;
    private final List<PriceExtra> priceExtraList = new ArrayList<>();

    private int priceRowId;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_result;
    }

    @Override
    protected void onInitView() {

        initOptions();

        pricePresenter = new PricePresenter(this, this);

        initView();
        initViews();

        setStatusBarPaddingWithPrimaryColor();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        priceExtraAdapter = new PriceExtraAdapter(this,R.layout.item_price_extra, priceExtraList);

        initData();
    }

    private void initOptions(){
        //获取缓存数据
        options = PreferencesUtil.getOptions();
        HokasUtils.logcat("options="+options);
    }

    private void initViews() {
        tvTitle.setText("价格清单");

        llPriceContainer = findViewById(R.id.llPriceContainer);

        tvCountryName = findViewById(R.id.tvCountryName);
        tvFreightLineName = findViewById(R.id.tvFreightLineName);
        tvChargeName = findViewById(R.id.tvChargeName);
        tvTotalWeight = findViewById(R.id.tvTotalWeight);
        tvChargeUnitName = findViewById(R.id.tvChargeUnitName);
        tvCargoName = findViewById(R.id.tvCargoName);
        tvNoSum = findViewById(R.id.tvNoSum);
        tvTimeMemo = findViewById(R.id.tvTimeMemo);

        tvFreightPrice = findViewById(R.id.tvFreightPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        tvCountryName.setText(options.getCountryName());
        tvFreightLineName.setText(options.getFreightLineName());

        xRecyclerView = findViewById(R.id.xRecyclerView);
    }

    private void initData() {
        pricePresenter.getChargeUnitList();
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
    public void onChargeUnitListData(List<ChargeUnit> list) {
        if (list != null && list.size() > 0) {
            this.chargeUnitList = list;

            pricePresenter.getPriceItemPack(this.options);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onPriceItemPackData(PriceItemPack pack) {
        if (pack != null) {
            if (pack.getSmallPack() > 0) {     //是小包
                tvFreightPrice.setText("￥"+HokasUtils.toMoney(pack.getPrice()));
            } else {
                tvFreightPrice.setText("￥"+HokasUtils.toMoney((long) pack.getPrice() * pack.getWeight() / 100));
            }

            //计算可以包含的价格
            int extraPrice = 0;
            int notOkToSum = 0;
            List<PriceExtra> okList = new ArrayList<>();
            for(PriceExtra extra: pack.getPriceExtraList()){
                if(extra.getOkToSum()==0){
                    notOkToSum ++;
                    continue;
                }
                if(extra.getWay()==0){
                    if(extra.getMinChargePerBill()>0){
                        extraPrice += (options.getCurrent()==0 ? options.getTotalWeight(): options.getBulkVolume())*extra.getPrice();
                    }else {
                        if ((options.getCurrent() == 0 ? options.getTotalWeight() : options.getBulkVolume()) * extra.getPrice() > extra.getMinChargePerBill()) {
                            extraPrice += (options.getCurrent() == 0 ? options.getTotalWeight() : options.getBulkVolume()) * extra.getPrice();
                        } else {
                            extraPrice += extra.getMinChargePerBill();
                        }
                    }
                }else if(extra.getWay()==1){
                        extraPrice += extra.getPrice();
                }else if(extra.getWay()==2){
                    // do nothing
                }else if(extra.getWay()==3){
                    // do nothing
                }
            }

            if(notOkToSum>0){
                tvNoSum.setVisibility(View.VISIBLE);
            }else{
                tvNoSum.setVisibility(View.GONE);
            }

            if (pack.getSmallPack() > 0) {     //是小包
                tvTotalPrice.setText("￥"+HokasUtils.toMoney(pack.getPrice()+extraPrice));
            } else {
                tvTotalPrice.setText("￥"+HokasUtils.toMoney((long) pack.getPrice() * pack.getWeight() / 100 + extraPrice));
            }

            long chargeUnitId = pack.getPriceRow().getChargeUnitId();
            for(ChargeUnit chargeUnit: this.chargeUnitList){
                if(chargeUnit.getId()==chargeUnitId){
                    tvChargeName.setText(chargeUnit.getTitle());
                    tvChargeUnitName.setText(chargeUnit.getName());
                    priceExtraAdapter.setChargeUnitName(chargeUnit.getName());
                    break;
                }
            }
            xRecyclerView.setAdapter(priceExtraAdapter);

            tvTotalWeight.setText(options.getTotalWeight()+"");

            if(pack.getPriceRow().getCargoId()==1){
                tvCargoName.setText("普货");
            }else{
                tvCargoName.setText("");
            }

            if(pack.getPriceExtraList()!=null && pack.getPriceExtraList().size()>0){
                priceExtraList.addAll(pack.getPriceExtraList());
                priceExtraAdapter.notifyDataSetChanged();
            }

            tvTimeMemo.setText(pack.getPriceRow().getTimeMemo());
            priceRowId = (int) pack.getPriceRowId();

        }
    }
}
