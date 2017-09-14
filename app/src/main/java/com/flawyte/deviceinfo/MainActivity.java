package com.flawyte.deviceinfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    public String customDeviceId() {
        String m_szDevIDShort = "35"
            + (Build.BOARD.length() % 10)
            + (Build.BRAND.length() % 10)
            // + (Build.CPU_ABI.length() % 10)
            + (Build.DEVICE.length() % 10)
            + (Build.MANUFACTURER.length() % 10)
            + (Build.MODEL.length() % 10)
            + (Build.PRODUCT.length() % 10);

        return "android-" + new UUID(m_szDevIDShort.hashCode(), Build.SERIAL.hashCode()).toString();
    }

    public String getDeviceInfo() {
        String str = "";

        str += "* Build.BOARD = " + Build.BOARD;
        str += "\n";
        str += "* Build.BOOTLOADER = " + Build.BOOTLOADER;
        str += "\n";
        str += "* Build.BRAND = " + Build.BRAND;
        str += "\n";
        str += "* Build.DEVICE = " + Build.DEVICE;
        str += "\n";
        str += "* Build.DISPLAY = " + Build.DISPLAY;
        str += "\n";
        str += "* Build.FINGERPRINT = " + Build.FINGERPRINT;
        str += "\n";
        str += "* Build.HARDWARE = " + Build.HARDWARE;
        str += "\n";
        str += "* Build.HOST = " + Build.HOST;
        str += "\n";
        str += "* Build.ID = " + Build.ID;
        str += "\n";
        str += "* Build.MANUFACTURER = " + Build.MANUFACTURER;
        str += "\n";
        str += "* Build.MODEL = " + Build.MODEL;
        str += "\n";
        str += "* Build.PRODUCT = " + Build.PRODUCT;
        str += "\n";
        str += "* Build.getRadioVersion() = " + Build.getRadioVersion();
        str += "\n";
        str += "* Build.SERIAL = " + Build.SERIAL;
        str += "\n";
        str += "* Build.TAGS = " + Build.TAGS;
        str += "\n";
        str += "* Build.TIME = " + Build.TIME;
        str += "\n";
        str += "* Build.TYPE = " + Build.TYPE;
        str += "\n";
        str += "* Build.USER = " + Build.USER;
        str += "\n";
        str += "* Build.VERSION.CODENAME = " + Build.VERSION.CODENAME;
        str += "\n";
        str += "* Build.VERSION.INCREMENTAL = " + Build.VERSION.INCREMENTAL;
        str += "\n";
        str += "* Build.VERSION.RELEASE = " + Build.VERSION.RELEASE;
        str += "\n";
        str += "* Build.VERSION.SDK_INT = " + Build.VERSION.SDK_INT;
        str += "\n";

        str += "\n";
        str += "* Settings.Secure.ANDROID_ID = " + Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        str += "\n";

        str += "\n";
        str += "* customDeviceId() = " + customDeviceId();
        str += "\n";

        str += "\n";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);

            str += "* ril.serialnumber = " + get.invoke(c, "ril.serialnumber", null);
            str += "\n";
            str += "* ro.boot.serialno = " + get.invoke(c, "ro.boot.serialno", null);
            str += "\n";
            str += "* ro.serialno = " + get.invoke(c, "ro.serialno", null);
            str += "\n";
            str += "* sys.serialnumber = " + get.invoke(c, "sys.serialnumber", null);
        } catch (Exception e) {
            e.printStackTrace();
            str += e.getMessage();
        }

        return str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ((Button) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("device-info", getDeviceInfo());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this, R.string.copied, Toast.LENGTH_SHORT).show();
            }
        });
        ((TextView) findViewById(R.id.text)).setText(getDeviceInfo());
    }
}
