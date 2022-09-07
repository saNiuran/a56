package com.niule.a56.calculator.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.github.gzuliyujiang.wheelpicker.OptionPicker;
import com.hokaslibs.utils.AlertDialog;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.bean.QuestionTemplate;
import com.niule.a56.calculator.bean.SalesLead;
import com.niule.a56.calculator.bean.SalesLeadRequest;
import com.niule.a56.calculator.mvp.contract.SalesLeadContract;
import com.niule.a56.calculator.mvp.presenter.SalesLeadPresenter;
import com.niule.a56.calculator.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class SalesLeadActivity extends BaseActivity implements View.OnClickListener, SalesLeadContract.View {

    private TextView tvQuestionTitle;
    private EditText etContent;
    private EditText etName;
    private EditText etMobile;
    private TextView tvNext;

    private OptionPicker titlePicker;

    private SalesLeadPresenter salesLeadPresenter;
    private SalesLead salesLead;
    private final List<QuestionTemplate> templateList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_saleslead;
    }

    @Override
    protected void onInitView() {

        salesLeadPresenter = new SalesLeadPresenter(this,this);
        titlePicker = new OptionPicker(this);
        salesLead = new SalesLead();

        initView();
        initViews();

        setStatusBarPaddingWithPrimaryColor();

        initData();
    }

    private void initViews(){

        tvQuestionTitle = findViewById(R.id.tvQuestionTitle);
        etContent = findViewById(R.id.etContent);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        tvNext = findViewById(R.id.tvNext);

        tvQuestionTitle.setOnClickListener(this);

        titlePicker.setOnOptionPickedListener((position, item) -> {
            salesLead.setTemplateId(((QuestionTemplate) item).getId());
            tvQuestionTitle.setText(((QuestionTemplate) item).getTitle());
            checkDataValid();
        });

        etMobile.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    checkDataValid();
                }
            }
        });

        disableBtn(tvNext);
    }

    private void initData(){
        salesLeadPresenter.getQuestionTemplateAll();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (HokasUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tvQuestionTitle:
                titlePicker.show();
                break;
            case R.id.tvNext:  //点击了下一步
                nextPage();
                break;
        }
    }

    private void checkDataValid(){
        String mobile = etMobile.getText().toString();
        if(HokasUtils.isNoEmpty(mobile) && HokasUtils.isMobile(etMobile.getText().toString()) && salesLead.getTemplateId()>0){
            enableBtn(tvNext);
        }else{
            disableBtn(tvNext);
        }
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
    public void onQuestionTemplateData(List<QuestionTemplate> list) {
        if(list!=null && list.size()>0){
            templateList.addAll(list);
            titlePicker.setData(templateList);
        }
    }

    @Override
    public void onCreateSalesLeadSuccess() {
        new AlertDialog(this).builder()
                .setTitle(getString(R.string.xitongtishi))
                .setMsg("感谢您的留言，我们将在24小时内和您取得联系，合作愉快！")
                .setPositiveButton("我知道了", v -> finish())
                .setCancelable(false)
                .show();
    }

    private void nextPage(){
        SalesLeadRequest request = new SalesLeadRequest();

        request.setTemplateId(salesLead.getTemplateId());
        request.setMemo(etContent.getText().toString());
        request.setMobile(etMobile.getText().toString());
        request.setName(etName.getText().toString());

        salesLeadPresenter.createSalesLead(request);
    }
}
