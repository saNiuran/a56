package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hokaslibs.utils.AlertDialog;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.MainActivity;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.mvp.contract.PriceContract;
import com.niule.a56.calculator.mvp.presenter.PricePresenter;
import com.niule.a56.calculator.mvp.ui.adapter.MemoAdapter;
import com.niule.a56.calculator.mvp.ui.adapter.PriceExtraAdapter;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends BaseActivity implements PriceContract.View, View.OnClickListener {

    private TextView tvChargeName;
    private TextView tvTotalWeight;
    private TextView tvChargeUnitName;
    private TextView tvCargoName;
    private TextView tvNoSum;
    private TextView tvTotalPrice;
    private TextView tvTimeMemo;
    private TextView tvSalesLead;
    private TextView tvEnquiry;

    private ImageView ivBars;
    private ImageView ivArrow;

    private LinearLayout llPriceContainer;
    private LinearLayout llMemo;

    private TextView tvFreightPrice;

    private Options options;

    private String priceRowMemo;

    private PricePresenter pricePresenter;
    private List<ChargeUnit> chargeUnitList;

    private RecyclerView xRecyclerView;
    private PriceExtraAdapter priceExtraAdapter;
    private final List<PriceExtra> priceExtraList = new ArrayList<>();

    private RecyclerView memoRecyclerView;
    private MemoAdapter memoAdapter;
    private final List<GeneralMemo> memoList = new ArrayList<>();

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

        LinearLayoutManager manager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        memoRecyclerView.setLayoutManager(manager);
        memoAdapter = new MemoAdapter(this,R.layout.item_memo, memoList);
        memoRecyclerView.setAdapter(memoAdapter);

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

        TextView tvCountryName = findViewById(R.id.tvCountryName);
        TextView tvFreightLineName = findViewById(R.id.tvFreightLineName);
        tvChargeName = findViewById(R.id.tvChargeName);
        tvTotalWeight = findViewById(R.id.tvTotalWeight);
        tvChargeUnitName = findViewById(R.id.tvChargeUnitName);
        tvCargoName = findViewById(R.id.tvCargoName);
        tvNoSum = findViewById(R.id.tvNoSum);
        tvTimeMemo = findViewById(R.id.tvTimeMemo);
        tvSalesLead = findViewById(R.id.tvSalesLead);
        tvEnquiry = findViewById(R.id.tvEnquiry);

        tvSalesLead.setOnClickListener(this);
        tvEnquiry.setOnClickListener(this);

        ivBars = findViewById(R.id.ivBars);
        ivArrow = findViewById(R.id.ivArrow);

        ivBars.setOnClickListener(this);
        ivArrow.setOnClickListener(this);

        tvFreightPrice = findViewById(R.id.tvFreightPrice);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);

        llMemo = findViewById(R.id.llMemo);

        TextView tvPrev = findViewById(R.id.tvPrev);
        TextView tvNext = findViewById(R.id.tvNext);
        tvPrev.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        tvCountryName.setText(options.getCountryName());
        tvFreightLineName.setText(options.getFreightLineName());

        xRecyclerView = findViewById(R.id.xRecyclerView);
        memoRecyclerView = findViewById(R.id.memoRecyclerView);
    }

    private void initData() {
        pricePresenter.getChargeUnitList();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (HokasUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.ivBars:
                llMemo.setVisibility(View.VISIBLE);
                if(memoList.size()<2){
                    PriceRow priceRow = new PriceRow();
                    priceRow.setId(priceRowId);
                    pricePresenter.getGeneralMemoCombo(priceRow);
                }
                ivBars.setVisibility(View.GONE);
                ivArrow.setVisibility(View.VISIBLE);
                break;
            case R.id.ivArrow:
                ivArrow.setVisibility(View.GONE);
                ivBars.setVisibility(View.VISIBLE);
                llMemo.setVisibility(View.GONE);
                break;
            case R.id.tvSalesLead:
                intent2Activity(SalesLeadActivity.class);
                break;
            case R.id.tvEnquiry:
                intent2Activity(EnquiryActivity.class);
                break;
            case R.id.tvPrev:  //点击了上一步
                finish();
                break;
            case R.id.tvNext:  //点击了下一步
                nextPage();
                break;
        }
    }

    private void nextPage(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
            llPriceContainer.setVisibility(View.VISIBLE);

            if (pack.getSmallPack() > 0) {     //是小包
                tvFreightPrice.setText("￥"+HokasUtils.toMoney(pack.getPrice()));
            } else {
                tvFreightPrice.setText("￥"+HokasUtils.toMoney((long) pack.getPrice() * pack.getWeight() / 100));
            }

            //计算可以包含的价格
            int extraPrice = 0;
            int notOkToSum = 0;
            for(PriceExtra extra: pack.getPriceExtraList()){
                if(extra.getOkToSum()==0){
                    notOkToSum ++;
                    continue;
                }
                if(extra.getWay()==0){
                    if(extra.getMinChargePerBill()>0){
                        extraPrice += (options.getCurrent()==0 ? options.getTotalWeight(): options.getBulkVolume())*extra.getPrice();
                    }else {
                        extraPrice += Math.max((options.getCurrent() == 0 ? options.getTotalWeight() : options.getBulkVolume()) * extra.getPrice(), extra.getMinChargePerBill());
                    }
                }else if(extra.getWay()==1){
                        extraPrice += extra.getPrice();
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
                    tvChargeName.setText("计费"+chargeUnit.getTitle());
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
            priceRowId = (int) pack.getPriceRow().getId();

            priceRowMemo = pack.getPriceRow().getMemo();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGeneralMemoComboData(List<GeneralMemo> list) {
        if(list!=null && list.size()>0){
            memoList.clear();
            GeneralMemo memo = new GeneralMemo();
            memo.setMemo(priceRowMemo);
            memoList.add(memo);

            memoList.addAll(list);

            memoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLessThanMinChargeWeight(String message) {

        llPriceContainer.setVisibility(View.GONE);

        new AlertDialog(this).builder()
                .setTitle(getString(R.string.xitongtishi))
                .setMsg(message)
                .setPositiveButton("我知道了", null)
                .setCancelable(false)
                .show();
    }
}
