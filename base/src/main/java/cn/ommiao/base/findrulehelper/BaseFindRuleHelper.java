package cn.ommiao.base.findrulehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.test.uiautomator.BySelector;
import androidx.test.uiautomator.UiSelector;

import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.UiInfo;

public abstract class BaseFindRuleHelper {

    public static final String DATA_VALID = "Data valid.";

    public @NonNull View getFindRuleView(Context context){
        return LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    protected abstract @LayoutRes int getLayoutId();

    public abstract String isDataValid(View view);

    public void save(View view, UiInfo uiInfo){
        FindRule findRule = getFindRule();
        String value = getValueFromView(view);
        uiInfo.findRules.put(findRule, value);
    }

    public abstract void setData(View view, String value);

    protected abstract String getValueFromView(View view);

    protected abstract FindRule getFindRule();

    protected boolean isEditTextFilled(View view, @IdRes int etId){
        String text = getEditTextContent(view, etId);
        return text.length() > 0;
    }

    protected String getEditTextContent(View view, @IdRes int etId){
        EditText editText = getEditText(view, etId);
        return editText.getText().toString().trim();
    }

    protected void setEditTextContent(View view, @IdRes int etId, String value){
        EditText editText = getEditText(view, etId);
        editText.setText(value);
    }

    protected EditText getEditText(View view, @IdRes int etId){
        EditText editText = view.findViewById(etId);
        return editText;
    }

    public abstract UiSelector bindUiSelector(@NonNull UiSelector uiSelector, String value);
    public abstract BySelector bindBySelector(@NonNull BySelector bySelector, String value);

}
