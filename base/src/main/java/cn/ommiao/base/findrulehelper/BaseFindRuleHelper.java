package cn.ommiao.base.findrulehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import cn.ommiao.base.entity.order.Order;

public abstract class BaseFindRuleHelper {

    public static final String DATA_VALID = "Data valid.";

    public View getUiInfoView(Context context){
        return LayoutInflater.from(context).inflate(getLayoutId(), null);
    }

    protected abstract @LayoutRes int getLayoutId();

    public abstract String isDataValid(View view);

    public abstract void saveToOrder(View view, Order order);

    public abstract void setData(View view, Order order);

    protected boolean isEditTextFilled(View view, @IdRes int etId){
        String text = getEditTextContent(view, etId);
        return text.length() > 0;
    }

    protected String getEditTextContent(View view, @IdRes int etId){
        EditText editText = view.findViewById(etId);
        return editText.getText().toString().trim();
    }

    protected EditText getEditText(View view, @IdRes int etId){
        EditText editText = view.findViewById(etId);
        return editText;
    }

}
