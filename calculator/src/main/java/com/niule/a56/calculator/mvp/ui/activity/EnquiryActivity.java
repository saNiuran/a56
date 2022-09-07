package com.niule.a56.calculator.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.captain_miao.grantap.CheckPermission;
import com.example.captain_miao.grantap.listeners.PermissionListener;
import com.example.captain_miao.grantap.utils.PermissionUtils;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.github.gzuliyujiang.wheelpicker.LinkagePicker;
import com.github.gzuliyujiang.wheelpicker.contract.LinkageProvider;
import com.google.gson.Gson;
import com.hokaslibs.utils.CustomTransform;
import com.hokaslibs.utils.FileUtils;
import com.hokaslibs.utils.HokasUtils;
import com.hokaslibs.utils.ZQImageViewRoundOval;
import com.hokaslibs.utils.recycler.XRecyclerViewHolder;
import com.niule.a56.calculator.BuildConfig;
import com.niule.a56.calculator.bean.*;
import com.hokaslibs.utils.recycler.XRecyclerAdapter;
import com.niule.a56.calculator.R;
import com.niule.a56.calculator.base.BaseActivity;
import com.niule.a56.calculator.mvp.contract.EnquiryContract;
import com.niule.a56.calculator.mvp.presenter.EnquiryPresenter;
import com.niule.a56.calculator.utils.UiUtils;
import com.niule.a56.calculator.utils.imageload.MyImageLoad;
import com.niule.a56.calculator.utils.imageload.MyImageTransAdapter;
import com.niule.a56.calculator.utils.imageload.MyProgressBarGet;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelActivity;
import com.yuyh.library.imgsel.ImgSelConfig;
import it.liuting.imagetrans.ITConfig;
import it.liuting.imagetrans.ImageTrans;
import it.liuting.imagetrans.ScaleType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class EnquiryActivity extends BaseActivity implements View.OnClickListener, EnquiryContract.View {

    private final List<ImagePath> imagePathList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MyImageAdapter adapter;

    private TextView tvCountryName;
    private TextView tvNext;
    private EditText etName;
    private EditText etMobile;
    private EditText etDescription;
    private EditText etContact;

    private final List<ContinentPack> continentPackList = new ArrayList<>();
    LinkagePicker countryPicker;

    private EnquiryPresenter enquiryPresenter;

    private File mTmpFile;
    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_IMAGE = 2;

    private final Enquiry enquiry = new Enquiry();

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_enquiry;
    }

    @Override
    protected void onInitView() {

        enquiryPresenter = new EnquiryPresenter(this, this);

        initView();
        initViews();
        setStatusBarPaddingWithPrimaryColor();

        initPickers();

        initData();

        initImages();
    }

    private void initViews() {
        tvTitle.setText("实单询价");

        recyclerView = findViewById(R.id.recyclerView);
        tvCountryName = findViewById(R.id.tvCountryName);
        tvNext = findViewById(R.id.tvNext);
        etMobile = findViewById(R.id.etMobile);
        etDescription = findViewById(R.id.etDescription);
        etContact = findViewById(R.id.etContact);
        etName = findViewById(R.id.etName);

        tvCountryName.setOnClickListener(this);
        tvNext.setOnClickListener(this);

        countryPicker = new LinkagePicker(this);

        etMobile.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                checkDataValid();
            }
        });

        disableBtn(tvNext);
    }

    private void initPickers(){
        countryPicker.setOnLinkagePickedListener((first, second, third) -> {
            Country selectedCountry = (Country) second;
            enquiry.setCountryId(selectedCountry.getId());
            tvCountryName.setText(selectedCountry.getName());

            checkDataValid();
        });
    }

    private void initData() {
        enquiryPresenter.getContinentPackList();
    }

    private void checkDataValid(){
        String mobile = etMobile.getText().toString();
        if(HokasUtils.isNoEmpty(mobile) && HokasUtils.isMobile(etMobile.getText().toString()) && enquiry.getCountryId()>0){
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

    //加载图片
    public void initImages() {
        removePlaceholder(imagePathList);
        addPlaceholder(imagePathList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new EnquiryActivity.MyImageAdapter(this, R.layout.item_published_grida, imagePathList);
        recyclerView.setAdapter(adapter);
        helper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new XRecyclerAdapter.OnItemClickListener<ImagePath>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, ImagePath bean, int position) {
                if ("000000".equals(bean.getLocalPath())) {
                    actionSheetDialogNoTitleHead();
                } else {
                    List<String> list = new ArrayList<>();
                    for (ImagePath ib : imagePathList) {
                        list.add(ib.getLocalPath());
                    }
                    list.remove("000000");
                    recyclerView.requestFocus();
                    ImageTrans.with(EnquiryActivity.this)
                            .setImageList(list)
                            .setSourceImageView(pos -> {
                                View view1 = recyclerView.getChildAt(pos);
                                if (view1 != null)
                                    return view1.findViewById(R.id.item_grida_image);
                                return null;
                            })
                            .setImageLoad(new MyImageLoad())
                            .setNowIndex(position)
                            .setProgressBar(new MyProgressBarGet())
                            .setAdapter(new MyImageTransAdapter())
                            .setConfig(new ITConfig().noThumbWhenCached())
                            .show();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, ImagePath bean, int position) {
                return false;
            }
        });
    }

    private void addPlaceholder(List<ImagePath> list) {
        ImagePath imagePath = new ImagePath();
        imagePath.setLocalPath("000000");
        imagePath.setWebPath("");
        list.add(imagePath);
    }

    private void removePlaceholder(List<ImagePath> list) {
        Iterator<ImagePath> iterator = list.iterator();
        while (iterator.hasNext()) {
            if ("000000".equals(iterator.next().getLocalPath())) {
                iterator.remove();
            }
        }
    }

    private void loadAdapter(List<ImagePath> list) {
        removePlaceholder(list);
        addPlaceholder(list);
        adapter.setListUrls(imagePathList);
    }

    @Override
    public void onClick(View v) {
        if (HokasUtils.isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.tvCountryName:
                countryPicker.show();
                break;
            case R.id.tvNext:  //点击了下一步
                nextPage();
                break;
        }

    }

    private void nextPage() {
        EnquiryRequest request = new EnquiryRequest();
        request.setCountryId(enquiry.getCountryId());
        request.setCargoInfo(etDescription.getText().toString());
        List<String> photoList = new ArrayList<>();
        for(ImagePath imagePath: imagePathList){
            if(!imagePath.getLocalPath().equals("000000")) {
                photoList.add(imagePath.getWebPath());
            }
        }
        request.setPhotos(new Gson().toJson(photoList));
        request.setMobile(etMobile.getText().toString());
        request.setName(etName.getText().toString());
        request.setContact(etContact.getText().toString());

        enquiryPresenter.createEnquiry(request);
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
    public void onContinentPackData(List<ContinentPack> list) {
        if (list != null && list.size() > 0) {
            continentPackList.addAll(list);

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
    public void onUpImgDone(List<ImagePath> list) {
        if(list!=null && list.size()>0) {
            ImagePath imagePathReturn = list.get(0);
            HokasUtils.logcat(imagePathList.toString());
            for (ImagePath iPath : imagePathList) {
                int start = imagePathReturn.getLocalPath().indexOf("android_");
                if (iPath.getLocalPath().contains(imagePathReturn.getLocalPath().substring(start+"android_".length()))) {
                    iPath.setWebPath(imagePathReturn.getWebPath());
                }
            }
            HokasUtils.logcat(imagePathReturn.toString());
            HokasUtils.logcat(imagePathList.toString());
        }
    }

    @Override
    public void onCreateEnquirySuccess(Enquiry enquiry) {
        new com.hokaslibs.utils.AlertDialog(this).builder()
                .setTitle(getString(R.string.xitongtishi))
                .setMsg("感谢您的实单询盘，我们将在2小时内和您取得联系！")
                .setPositiveButton("我知道了", v -> finish())
                .setCancelable(false)
                .show();
    }

    private class MyImageAdapter extends XRecyclerAdapter<ImagePath> {

        public MyImageAdapter(Context context, int layoutId, List<ImagePath> dataList) {
            super(context, layoutId, dataList);
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setListUrls(List<ImagePath> list) {
            mDatas = list;
            if (mDatas.size() == 6) {
                mDatas.remove(mDatas.size() - 1);
            }
            notifyDataSetChanged();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void convert(XRecyclerViewHolder holder, ImagePath imagePath, final int position) {
            ImageView imageView2 = holder.getView(R.id.item_grida_image2);
            ZQImageViewRoundOval imageView = holder.getView(R.id.item_grida_image);
            if (imagePath.getLocalPath().equals("000000")) {
                holder.setVisible(R.id.item_grida_bt, false);
                Glide.with(EnquiryActivity.this)
                        .load(R.mipmap.ic_add_img)
                        .into(imageView2);
                imageView.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
            } else {
                imageView.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.GONE);
                imageView.setRoundRadius(15);
//                imageView.setPadding(1, 1, 1, 1);
                //优先展示本地图片
                String imgPath = "";
                if (!imagePath.getLocalPath().isEmpty()) {
                    imgPath = imagePath.getLocalPath();
                } else if (!imagePath.getWebPath().isEmpty()) {
                    imgPath = imagePath.getWebPath();
                }
                Glide.with(EnquiryActivity.this)
                        .load(imgPath)
                        .placeholder(R.drawable.default_error)
                        .error(R.drawable.default_error)
                        .transform(new CustomTransform(mContext, ScaleType.CENTER_CROP))
                        .into(imageView);
                holder.setVisible(R.id.item_grida_bt, true);
                holder.setOnClickListener(R.id.item_grida_bt, view -> {
                    imagePathList.remove(position);
                    loadAdapter(imagePathList);
                    notifyDataSetChanged();
                });
            }
        }
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            HokasUtils.logcat("helper getMovementFlags");
            //首先回调的方法 返回int表示是否监听该方向
//            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//线性排列时监听到的为上下动作则为：拖拽排序
//            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//线性排列时监听到的为左右动作时则为：侧滑删除
//            return makeMovementFlags(dragFlags, swipeFlags);

            //首先回调的方法 返回int表示是否监听该方向
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            HokasUtils.logcat("helper onMove");
            //滑动事件
            //交换位置
            Collections.swap(imagePathList, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            //刷新adapter
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            HokasUtils.logcat("helper onSwiped");
            //侧滑事件
            imagePathList.remove(viewHolder.getAdapterPosition());
            //刷新adapter
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            //是否可拖拽
            return true;
        }
    });

    /**
     * 拍照或选择图片
     */
    private void actionSheetDialogNoTitleHead() {
        String[] stringItems;
        stringItems = new String[]{"从手机相册选择", "拍照"};

        final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL((parent, view, position, id) -> {
            if (0 == position) {
                selectorImage();
            }
            if (1 == position) {
                showRequestPermissionAlertWindowCamera();
            }
            dialog.dismiss();
        });
    }

    /**
     * 相机权限
     */
    public void showRequestPermissionAlertWindowCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isGranted = PermissionUtils.hasSelfPermissions(this,
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (isGranted) {
                showCameraAction();
                return;
            }
            CheckPermission.from(this).setPermissions
                    (Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).setPermissionListener
                    (new PermissionListener() {
                        @Override
                        public void permissionGranted() {
                            showCameraAction();
                        }

                        @Override
                        public void permissionDenied() {
                            openSettingActivity(EnquiryActivity.this
                            );
                        }
                    }).check();
        } else {
            showCameraAction();
        }
    }

    private static void openSettingActivity(final Activity activity) {
        showMessageOKCancel(activity, (dialog, which) -> {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
            intent.setData(uri);
            activity.startActivity(intent);
        });
    }

    private static void showMessageOKCancel(final Activity context, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage("该功能需要开启权限，点击“设置”-“权限管理”打开所需权限")
                .setPositiveButton("设置", okListener)
                .setNegativeButton("关闭", null)
                .create()
                .show();

    }

    /**
     * 选择相机
     */
    private void showCameraAction() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(this.getPackageManager()) != null) {
            // 设置系统相机拍照后的输出路径
            // 创建临时文件
            try {
                mTmpFile = FileUtils.createImageFile(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            FileProvider.getUriForFile(this,
                                    BuildConfig.APPLICATION_ID + ".provider", mTmpFile));
                } else {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTmpFile));
                }
                startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            UiUtils.makeText("没有系统相机");
        }
    }

    // 自定义图片加载器
    private final ImageLoader loader = (context, path, imageView) -> Glide.with(context).load(path).centerInside().into(imageView);

    /**
     * 选择图片
     */
    private void selectorImage() {
        com.yuyh.library.imgsel.common.Constant.imageList.clear();
        for (ImagePath imagePath : imagePathList) {
            if (null != imagePath.getLocalPath() && !imagePath.getLocalPath().contains("000000")) {
                com.yuyh.library.imgsel.common.Constant.imageList.add(imagePath.getLocalPath());
            }
        }
        int maxNum = 5;
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)

                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(true)
                // 最大选择图片数量，默认9
                .maxNum(maxNum)
                // “确定”按钮背景色
                .btnBgColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // “确定”按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // 返回图标ResId
                .backResId(R.drawable.btn_back_selector)
                // 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(ContextCompat.getColor(this, R.color.colorPrimary))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 250, 250)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(false)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == -1) {
                List<String> imagesAlbum = data.getStringArrayListExtra(ImgSelActivity2.INTENT_RESULT);
                assert imagesAlbum != null;
                for (String s : imagesAlbum) {
                    boolean isDuplicated = false;
                    for (ImagePath ib : imagePathList) {
                        if (ib.getLocalPath().equals(s)) {
                            isDuplicated = true;
                            break;
                        }
                    }
                    if (!isDuplicated) {
                        ImagePath imagePath = new ImagePath();
                        imagePath.setLocalPath(s);
                        imagePath.setWebPath("");
                        imagePathList.add(imagePath);
                        enquiryPresenter.uploadImg(s);
                    }
                }
                loadAdapter(imagePathList);
            }
        }
        // 相机拍照完成后，返回图片路径
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {
                if (mTmpFile != null) {
                    ImagePath imagePath = new ImagePath();
                    imagePath.setLocalPath(mTmpFile.getAbsolutePath());
                    imagePath.setWebPath("");
                    imagePathList.add(imagePath);
                    loadAdapter(imagePathList);
                    enquiryPresenter.uploadImg(imagePath.getLocalPath());
                }
            } else {
                while (mTmpFile != null && mTmpFile.exists()) {
                    boolean success = mTmpFile.delete();
                    if (success) {
                        mTmpFile = null;
                    }
                }
            }
        }
    }
}
