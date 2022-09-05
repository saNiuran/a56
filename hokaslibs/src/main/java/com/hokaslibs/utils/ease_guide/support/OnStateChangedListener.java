package com.hokaslibs.utils.ease_guide.support;

import android.view.View;

/**
 * @author yuyh.
 * @date 2016/12/25.
 */
public interface OnStateChangedListener {

    void onShow();

    void onDismiss();

    void onHighlightViewClick(View view);
}
