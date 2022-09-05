package org.hokas.guanggao;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setPageTransformer(true, getPageTransformer());
        AdvertisementPagerAdapter adapter = new AdvertisementPagerAdapter(this);
        viewPager.setAdapter(adapter);

    }

    private ViewPager.PageTransformer getPageTransformer() {
        int type = 0;
        ViewPager.PageTransformer pageTransformer;
        if (type == 0 || type == 2) {
            // type为0 或者2 为上下翻页动画
            pageTransformer = new ViewPager.PageTransformer() {
                private float margin = getResources().getDimension(R.dimen.dp_10);

                @Override
                public void transformPage(View page, float position) {
                    page.setTranslationY(-position * margin);
                    if (position >= -1.0f && position <= 0.0f) {
                        // 控制左侧滑入或者划出View相对X坐标为0
                        page.setTranslationX(-page.getWidth() * (position));
                        // 旋转
                        page.setPivotX(0);
                        page.setPivotY(0);
                        page.setRotation(-90f * position);
                    } else if (position > 0.0f) {
                        // 控制右侧滑入或者划出控制View相对X坐标为0
                        page.setTranslationX(-page.getWidth() * (position));
                        page.setPivotX(0);
                        page.setPivotY(0);
                        page.setRotation(0f);
                    } else {
                        // 控制左侧其它缓存View旋转状态固定
                        page.setPivotX(0);
                        page.setPivotY(0);
                        page.setRotation(90f);
                    }
                }
            };
        } else {
            // type 为1或者3为缩放翻页动画
            pageTransformer = new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    if (position >= -1.0f && position <= 0.0f) {
                        // 控制左侧滑入或者划出View缩放比例
                        page.setScaleX(1 + position * 0.1f);
                        page.setScaleY(1 + position * 0.2f);
                    } else if (position > 0.0f && position <= 1.0f) {
                        // 控制右侧滑入或者划出View缩放比例
                        page.setScaleX(1 - position * 0.1f);
                        page.setScaleY(1 - position * 0.2f);
                    } else {
                        // 控制其它View缩放比例
                        page.setScaleX(0.9f);
                        page.setScaleY(0.8f);
                    }
                }
            };
        }
        return pageTransformer;
    }
}
