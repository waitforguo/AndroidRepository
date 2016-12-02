package com.fausgoal.repository.utils;

import android.graphics.Paint;
import android.text.TextPaint;
import android.widget.EditText;
import android.widget.TextView;

import com.fausgoal.repository.common.GLConst;


/**
 * Description：TextView工具类
 * <br/><br/>Created by Fausgoal on 15/9/8.
 * <br/><br/>
 */
public class GLTextViewUtil {
    private GLTextViewUtil() {
    }

    /**
     * 将光标移动到指定的EditText中
     *
     * @param editText   editText
     * @param isSetEmpty true 并将editText设置为空
     */
    public static void setEditTextFocus(final EditText editText, final boolean isSetEmpty) {
        if (null == editText) {
            return;
        }
        if (isSetEmpty)
            editText.setText("");
        // 设置光标到指定文本框内
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.requestFocusFromTouch();

        if (editText.getText().length() != GLConst.NONE) {
            editText.setSelection(editText.getText().length()); // 将光标移动到输入内容的最后
        }
    }

    /**
     * 设置TextView显示删除线
     */
    public static void setTextPaintStrikeThru(TextView textView) {
        if (null == textView) {
            return;
        }
        TextPaint textPaint = textView.getPaint();
        textPaint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG);  //添加删除线
        textPaint.setAntiAlias(true); //抗锯齿;
    }

    public static int getTextWidth(TextView tv, String content) {
        TextPaint paint = tv.getPaint();
        return (int) paint.measureText(content);
    }
}
