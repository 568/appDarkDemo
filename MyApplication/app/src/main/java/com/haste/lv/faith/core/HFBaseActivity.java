package com.haste.lv.faith.core;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.haste.lv.faith.utils.ToastUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lv on 18-12-5.
 */

public class HFBaseActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        methodRequiresTwoPermission();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //处理权限名字字符串
        StringBuffer sb = new StringBuffer();
        sb.append("豆腻应用需要:\n");
        for (String str : perms) {
            if (TextUtils.equals(str, Manifest.permission.CAMERA)) {
                str = "使用相机的权限";
            } else if (TextUtils.equals(str, Manifest.permission.READ_PHONE_STATE)) {
                str = "使用电话的权限";
            } else if (TextUtils.equals(str, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || TextUtils.equals(str, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                str = "使用SD卡的权限";
            } else if (TextUtils.equals(str, Manifest.permission.ACCESS_FINE_LOCATION)) {
                str = "使用定位的权限";
            }
            sb.append(str);
            sb.append("\n");
        }
        sb.append("否则无法正常使用，是否打开设置");
        sb.replace(sb.length() - 2, sb.length(), "");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setRationale(sb.toString())
                    .setPositiveButton("是")
                    .setNegativeButton("否")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {

    }

    @Override
    public void onRationaleDenied(int requestCode) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            ToastUtil.error("权限没有开启，应用无法正常使用，请及时开启权限！", 2000);
        }
    }

    @AfterPermissionGranted(600)
    private void methodRequiresTwoPermission() {
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, "豆腻应用需要使用到：SD卡，手机定位，相机权限，需要您及时开启！", 600, perms);
        }
    }

    final String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
}
