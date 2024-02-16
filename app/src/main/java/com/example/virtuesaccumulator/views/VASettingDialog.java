package com.example.virtuesaccumulator.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.virtuesaccumulator.R;
import com.example.virtuesaccumulator.activity.VAStoreActivity;


public class VASettingDialog extends Dialog {

    private VASettingButton shopButton;

    private VASettingButton settingButton;

    private VASettingButton otherButton;

    private View quitButton;
    private Context context;

    public VASettingDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.va_setting_dialog, null);
        setContentView(contentView);
        setCanceledOnTouchOutside(true);
        shopButton = findViewById(R.id.va_shop_button);
        settingButton = findViewById(R.id.va_setting_button1);
        otherButton = findViewById(R.id.va_other_button);
        quitButton = findViewById(R.id.va_dialog_cancel);

        shopButton.setRes(R.drawable.va_coin_icon1, R.drawable.va_dialog_store_text);
        settingButton.setRes(R.drawable.va_dialog_settings_icon, R.drawable.va_dialog_settings_text);
        otherButton.setRes(R.drawable.va_dialog_quit_icon, R.drawable.va_dialog_quit_text);

        shopButton.setCallback(new VASettingButton.SettingButtonCallback() {
            @Override
            public void onButtonClicked() {
                Intent intent = new Intent(getContext(), VAStoreActivity.class);
                getContext().startActivity(intent);
                dismiss();
            }
        });
        settingButton.setCallback(new VASettingButton.SettingButtonCallback() {
            @Override
            public void onButtonClicked() {

            }
        });
        otherButton.setCallback(new VASettingButton.SettingButtonCallback() {
            @Override
            public void onButtonClicked() {

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}