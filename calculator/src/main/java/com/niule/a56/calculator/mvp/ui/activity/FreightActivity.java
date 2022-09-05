package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.github.gzuliyujiang.wheelpicker.LinkagePicker;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.github.gzuliyujiang.wheelpicker.contract.LinkageProvider;
import com.github.gzuliyujiang.wheelpicker.contract.OnLinkagePickedListener;
import com.github.gzuliyujiang.wheelpicker.contract.OnOptionPickedListener;
import com.hokaslibs.utils.*;
import com.j256.ormlite.stmt.query.In;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.*;
import com.niule.a56.calculator.mvp.contract.FreightContract;
import com.niule.a56.calculator.mvp.presenter.FreightPresenter;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;
import com.umeng.commonsdk.debug.D;

import java.util.ArrayList;
import java.util.List;

public class FreightActivity extends BaseActivity implements FreightContract.View, View.OnClickListener {

    private LinearLayout ll_country;
    private TextView tvCountryName;
    private LinearLayout ll_freightLine;
    private TextView tvFreightLineName;
    private LinearLayout ll_zipZone;
    private TextView tvDivisionZipName;
    private LinearLayout ll_cloudZone;
    private TextView tvDivisionCloudName;
    private LinearLayout ll_fbaZone;
    private TextView tvFbaName;
    private LinearLayout llPrev;
    private LinearLayout llNext;

    private TextView tvPrev;
    private TextView tvNext;

    private Options options;
    private List<ContinentPack> continentPackList;
    private List<FreightPack> freightPackList;
    private DivisionPack divisionPack = new DivisionPack();

    LinkagePicker countryPicker;
    LinkagePicker freightLinePicker;
    OptionPicker zipDivisionPicker;
    OptionPicker cloudDivisionPicker;
    LinkagePicker fbaPicker;

    private FreightPresenter freightPresenter;

    private static final String defaultVal = "请选择";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_freight;
    }

    @Override
    protected void onInitView() {
        freightPresenter = new FreightPresenter(this, this);
        continentPackList = new ArrayList<>();
        freightPackList = new ArrayList<>();

        //初始化选择器
        countryPicker = new LinkagePicker(this);
        freightLinePicker = new LinkagePicker(this);
        zipDivisionPicker = new OptionPicker(this);
        cloudDivisionPicker = new OptionPicker(this);
        fbaPicker = new LinkagePicker(this);

        initView();
        initViews();

        initPickers();
        setStatusBarPaddingWithPrimaryColor();

        initData();
    }

    private void initData() {
        initOptions();
        //获取国家picker的预设值
        freightPresenter.getContinentPackList();
    }

    private void initOptions(){
        //获取缓存数据
        options = PreferencesUtil.getOptions();
        if (options == null) {
            options = new Options();
        }
        if (options.getCountryName() == null) {
            options.setCountryName(defaultVal);
        }
        if (options.getFreightLineName() == null) {
            options.setFreightLineName(defaultVal);
        }
        if (options.getDivisionName() == null) {
            options.setDivisionName(defaultVal);
        }
        if (options.getFbaName() == null) {
            options.setFbaName(defaultVal);
        }

        tvCountryName.setText(options.getCountryName());
        tvFreightLineName.setText(options.getFreightLineName());
        tvDivisionZipName.setText(options.getDivisionName());
        tvDivisionCloudName.setText(options.getDivisionName());
        tvFbaName.setText(options.getFbaName());

        if (options.isShowFreightLine()) {
            ll_freightLine.setVisibility(View.VISIBLE);
        } else {
            ll_freightLine.setVisibility(View.GONE);
        }
        if (options.isShowZipZone()) {
            ll_zipZone.setVisibility(View.VISIBLE);
        } else {
            ll_zipZone.setVisibility(View.GONE);
        }
        if (options.isShowCloudZone()) {
            ll_cloudZone.setVisibility(View.VISIBLE);
        } else {
            ll_cloudZone.setVisibility(View.GONE);
        }
        if (options.isShowFbaZone()) {
            ll_fbaZone.setVisibility(View.VISIBLE);
        } else {
            ll_fbaZone.setVisibility(View.GONE);
        }
    }

    private void initPickers() {
        countryPicker.setOnLinkagePickedListener(new OnLinkagePickedListener() {
            @Override
            public void onLinkagePicked(Object first, Object second, Object third) {
                boolean countryChanged = false;
                Country selectedCountry = (Country) second;

                if (options.getCountryId() == 0 || options.getCountryId() != selectedCountry.getId()) {
                    countryChanged = true;
                }

                options.setCountryId(selectedCountry.getId());
                tvCountryName.setText(selectedCountry.getName());
                options.setCountryName(selectedCountry.getName());

                if (countryChanged) {
                    ll_freightLine.setVisibility(View.GONE);
                    options.setFreightLineId(0);
                    options.setFreightLineName(defaultVal);
                    tvFreightLineName.setText(defaultVal);

                    ll_cloudZone.setVisibility(View.GONE);
                    ll_zipZone.setVisibility(View.GONE);
                    options.setDivisionId(0);
                    options.setDivisionName(defaultVal);
                    tvDivisionCloudName.setText(defaultVal);
                    tvDivisionZipName.setText(defaultVal);

                    ll_fbaZone.setVisibility(View.GONE);
                    options.setFbaId(0);
                    options.setFbaName(defaultVal);
                    tvFbaName.setText(defaultVal);

                    options.setShowFreightLine(false);
                    options.setShowFbaZone(false);
                    options.setShowCloudZone(false);
                    options.setShowZipZone(false);
                }
                //获取航线
                if (countryChanged) {
                    freightPresenter.getFreightLineListOfCountry(selectedCountry.getId());
                    disableBtn(tvNext);
                }
            }
        });

        freightLinePicker.setOnLinkagePickedListener(new OnLinkagePickedListener() {
            @Override
            public void onLinkagePicked(Object first, Object second, Object third) {
                boolean freightLineChanged = false;
                FreightLine selectedFreightLine = (FreightLine) second;

                if (options.getFreightLineId() == 0 || options.getFreightLineId() != selectedFreightLine.getId()) {
                    freightLineChanged = true;
                }

                options.setFreightLineId(selectedFreightLine.getId());
                options.setFreightLineName(selectedFreightLine.getName());
                tvFreightLineName.setText(selectedFreightLine.getName());

                if (freightLineChanged) {
                    ll_cloudZone.setVisibility(View.GONE);
                    ll_zipZone.setVisibility(View.GONE);
                    ll_fbaZone.setVisibility(View.GONE);

                    options.setDivisionId(0);
                    options.setDivisionName(defaultVal);
                    tvDivisionCloudName.setText(defaultVal);
                    tvDivisionZipName.setText(defaultVal);

                    options.setFbaId(0);
                    options.setFbaName(defaultVal);
                    tvFbaName.setText(defaultVal);

                    options.setShowFbaZone(false);
                    options.setShowCloudZone(false);
                    options.setShowZipZone(false);
                }

                //获取分区信息
                if (freightLineChanged) {
                    freightPresenter.getDivisionPackOfFreightLine(selectedFreightLine.getId());
                    disableBtn(tvNext);
                }
            }
        });

        zipDivisionPicker.setOnOptionPickedListener(new OnOptionPickedListener() {
            @Override
            public void onOptionPicked(int position, Object item) {
                options.setDivisionId(((Division) item).getId());
                options.setDivisionName(((Division) item).getName());
                tvDivisionZipName.setText(((Division) item).getName());

                enableBtn(tvNext);
            }
        });

        cloudDivisionPicker.setOnOptionPickedListener(new OnOptionPickedListener() {
            @Override
            public void onOptionPicked(int position, Object item) {
                options.setDivisionId(((Division) item).getId());
                options.setDivisionName(((Division) item).getName());
                tvDivisionCloudName.setText(((Division) item).getName());

                enableBtn(tvNext);
            }
        });

        fbaPicker.setOnLinkagePickedListener(new OnLinkagePickedListener() {
            @Override
            public void onLinkagePicked(Object first, Object second, Object third) {
                Division division = (Division) first;
                options.setDivisionId(division.getId());
                options.setDivisionName(division.getName());

                BaseZone  baseZone = (BaseZone) second;
                options.setFbaId(baseZone.getId());
                options.setFbaName(baseZone.getName());
                tvFbaName.setText(baseZone.getName());

                enableBtn(tvNext);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initOptions();
    }

    private void initViews() {
        tvTitle.setText("选择目的地");

        ll_country = findViewById(R.id.ll_country);
        tvCountryName = findViewById(R.id.tvCountryName);
        ll_freightLine = findViewById(R.id.ll_freightLine);
        tvFreightLineName = findViewById(R.id.tvFreightLineName);
        ll_zipZone = findViewById(R.id.ll_zipZone);
        tvDivisionZipName = findViewById(R.id.tvDivisionZipName);
        ll_cloudZone = findViewById(R.id.ll_cloudZone);
        tvDivisionCloudName = findViewById(R.id.tvDivisionCloudName);
        ll_fbaZone = findViewById(R.id.ll_fbaZone);
        tvFbaName = findViewById(R.id.tvFbaName);

        tvPrev = findViewById(R.id.tvPrev);
        tvNext = findViewById(R.id.tvNext);

        llPrev = findViewById(R.id.llPrev);
        llNext = findViewById(R.id.llNext);

        ll_country.setOnClickListener(this);
        ll_freightLine.setOnClickListener(this);
        ll_zipZone.setOnClickListener(this);
        ll_cloudZone.setOnClickListener(this);
        ll_fbaZone.setOnClickListener(this);

        llPrev.setVisibility(View.GONE);

        disableBtn(tvNext);
//        enableBtn(tvNext);
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
            case R.id.ll_country:  //点击了国家选择器
                countryPicker.show();
                break;
            case R.id.ll_freightLine:  //点击了航线选择器
                freightLinePicker.show();
                break;
            case R.id.ll_zipZone:  //点击了邮编选择器
                zipDivisionPicker.show();
                break;
            case R.id.ll_cloudZone:  //点击了云仓选择器
                cloudDivisionPicker.show();
                break;
            case R.id.ll_fbaZone:  //点击了FBA仓选择器
                fbaPicker.show();
                break;
            case R.id.tvPrev:  //点击了上一步
                break;
            case R.id.tvNext:  //点击了下一步
                nextPage();
                break;
        }
    }

    @Override
    public void onContinentPackData(List<ContinentPack> list) {
        if (list != null && list.size() > 0) {
            continentPackList = list;

            countryPicker.setData(new LinkageProvider() {
                @Override
                public boolean firstLevelVisible() {
                    return true;
                }

                @Override
                public boolean thirdLevelVisible() {
                    return false;
                }

                @NonNull
                @Override
                public List<Continent> provideFirstData() {
                    return getContinentList();
                }

                @NonNull
                @Override
                public List<Country> linkageSecondData(int firstIndex) {
                    if (firstIndex > continentPackList.size() - 1) {
                        return new ArrayList<>();
                    }
                    return getCountryList(firstIndex);
                }

                @NonNull
                @Override
                public List<?> linkageThirdData(int firstIndex, int secondIndex) {
                    return null;
                }

                @Override
                public int findFirstIndex(Object firstValue) {
                    List<Continent> continentList = getContinentList();
                    return continentList.indexOf((Continent) firstValue);
                }

                @Override
                public int findSecondIndex(int firstIndex, Object secondValue) {
                    List<Country> countryList = getCountryList(firstIndex);
                    return countryList.indexOf((Country) secondValue);
                }

                @Override
                public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
                    return 0;
                }
            });

        }
    }

    @NonNull
    private List<Continent> getContinentList() {
        List<Continent> continentList = new ArrayList<>();
        for (ContinentPack pack : continentPackList) {
            Continent continent = new Continent();
            continent.setId(pack.getId());
            continent.setName(pack.getName());
            continentList.add(continent);
        }
        return continentList;
    }

    @NonNull
    private List<Country> getCountryList(int firstIndex) {
        List<Country> countryList = new ArrayList<>();
        for (CountryResponse country : continentPackList.get(firstIndex).getCountryList()) {
            Country country1 = new Country();
            country1.setId(country.getId());
            country1.setName(country.getName());
            countryList.add(country1);
        }
        return countryList;
    }

    @Override
    public void onFreightPackData(List<FreightPack> list) {
        if (list != null && list.size() > 0) {
            freightPackList = list;

            freightLinePicker.setData(new LinkageProvider() {
                @Override
                public boolean firstLevelVisible() {
                    return true;
                }

                @Override
                public boolean thirdLevelVisible() {
                    return false;
                }

                @NonNull
                @Override
                public List<BaseFreight> provideFirstData() {
                    return getBaseFreightList();
                }

                @NonNull
                @Override
                public List<FreightLine> linkageSecondData(int firstIndex) {
                    if (firstIndex > freightPackList.size() - 1) {
                        return new ArrayList<>();
                    }
                    return getFreightLineList(firstIndex);
                }

                @NonNull
                @Override
                public List<?> linkageThirdData(int firstIndex, int secondIndex) {
                    return null;
                }

                @Override
                public int findFirstIndex(Object firstValue) {
                    List<BaseFreight> baseFreightList = getBaseFreightList();
                    return baseFreightList.indexOf((BaseFreight) firstValue);
                }

                @Override
                public int findSecondIndex(int firstIndex, Object secondValue) {
                    List<FreightLine> freightLineList = getFreightLineList(firstIndex);
                    return freightLineList.indexOf((FreightLine) secondValue);
                }

                @Override
                public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
                    return 0;
                }
            });

            ll_freightLine.setVisibility(View.VISIBLE);
            options.setShowFreightLine(true);
        }
    }

    @NonNull
    private List<FreightLine> getFreightLineList(int firstIndex) {
        List<FreightLine> freightLineList = new ArrayList<>();
        for (FreightLineResponse freight : freightPackList.get(firstIndex).getFreightLineList()) {
            FreightLine freightLine = new FreightLine();
            freightLine.setId(freight.getId());
            freightLine.setName(freight.getName());
            freightLineList.add(freightLine);
        }
        return freightLineList;
    }

    @NonNull
    private List<BaseFreight> getBaseFreightList() {
        List<BaseFreight> baseFreightList = new ArrayList<>();
        for (FreightPack pack : freightPackList) {
            BaseFreight baseFreight = new BaseFreight();
            baseFreight.setId(pack.getId());
            baseFreight.setName(pack.getName());
            baseFreightList.add(baseFreight);
        }
        return baseFreightList;
    }

    @Override
    public void onFreightPackError() {
        //没有相关的航线
        new AlertDialog(this).builder()
                .setTitle(getString(R.string.xitongtishi))
                .setMsg("很抱歉，您选择的国家【" + options.getCountryName() + "】，暂未开通运费计算。请选择其他国家，或加微信yunjg8与客服取得联系！")
                .setPositiveButton("我知道了", null)
                .setCancelable(false)
                .show();
    }

    @Override
    public void onDivisionPackData(DivisionPack divisionPack) {
        this.divisionPack = divisionPack;
        this.options.setCargoId((int) divisionPack.getFreightLine().getCargoId());
        if (divisionPack.getHasDivision() > 0) {
            int divisionType = divisionPack.getDivisionList().get(0).getType();
            List<Division> divisionList = new ArrayList<>();
            if(divisionType==0){  //fba分区
                List<DivisionFbaPack> fbaPackList = divisionPack.getFbaPackList();
                fbaPicker.setData(new LinkageProvider() {
                    @Override
                    public boolean firstLevelVisible() {
                        return true;
                    }

                    @Override
                    public boolean thirdLevelVisible() {
                        return false;
                    }

                    @NonNull
                    @Override
                    public List<Division> provideFirstData() {
                        return getDivisionList(fbaPackList);
                    }

                    @NonNull
                    @Override
                    public List<BaseZone> linkageSecondData(int firstIndex) {
                        if (firstIndex > fbaPackList.size() - 1) {
                            return new ArrayList<>();
                        }
                        return fbaPackList.get(firstIndex).getBaseZoneList();
                    }

                    @NonNull
                    @Override
                    public List<?> linkageThirdData(int firstIndex, int secondIndex) {
                        return null;
                    }

                    @Override
                    public int findFirstIndex(Object firstValue) {
                        List<Division> divisionList1 = getDivisionList(fbaPackList);
                        return divisionList1.indexOf((Division) firstValue);
                    }

                    @Override
                    public int findSecondIndex(int firstIndex, Object secondValue) {
                        List<BaseZone> baseZoneList = fbaPackList.get(firstIndex).getBaseZoneList();
                        return baseZoneList.indexOf((BaseZone) secondValue);
                    }

                    @Override
                    public int findThirdIndex(int firstIndex, int secondIndex, Object thirdValue) {
                        return 0;
                    }
                });
                ll_fbaZone.setVisibility(View.VISIBLE);
                ll_cloudZone.setVisibility(View.GONE);
                ll_zipZone.setVisibility(View.GONE);
                options.setShowFbaZone(true);
                options.setShowCloudZone(false);
                options.setShowZipZone(false);
            }else if(divisionType==1 || divisionType==2){
                if(divisionPack.getDivisionList()!=null && divisionPack.getDivisionList().size()>0){
                    List<Division> List = divisionPack.getDivisionList();
                    for(Division d: List) {
                        Division division = new Division();
                        division.setId(d.getId());
                        division.setName(d.getName());
                        divisionList.add(division);
                    }
                }
                ll_fbaZone.setVisibility(View.GONE);
                options.setShowFbaZone(false);
                if(divisionType==1){
                    ll_cloudZone.setVisibility(View.GONE);
                    ll_zipZone.setVisibility(View.VISIBLE);
                    zipDivisionPicker.setData(divisionList);
                    options.setShowCloudZone(false);
                    options.setShowZipZone(true);
                }else{
                    ll_cloudZone.setVisibility(View.VISIBLE);
                    ll_zipZone.setVisibility(View.GONE);
                    cloudDivisionPicker.setData(divisionList);
                    options.setShowCloudZone(true);
                    options.setShowZipZone(false);
                }
            }
        } else {
            ll_cloudZone.setVisibility(View.GONE);
            ll_fbaZone.setVisibility(View.GONE);
            ll_zipZone.setVisibility(View.GONE);
            options.setShowFbaZone(false);
            options.setShowCloudZone(false);
            options.setShowZipZone(false);
            enableBtn(tvNext);
        }
    }

    @Override
    public void onPriceRowData(PriceRow priceRow) {

    }

    private List<Division> getDivisionList(List<DivisionFbaPack> fbaPackList) {
        List<Division> divisionList = new ArrayList<>();
        for(DivisionFbaPack pack: fbaPackList){
            Division division = new Division();
            division.setId(pack.getId());
            division.setName(pack.getName());
            divisionList.add(division);
        }
        return divisionList;
    }

    private void nextPage() {
        PreferencesUtil.setOptions(options);
        intent2Activity(CargoActivity.class);
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
}
