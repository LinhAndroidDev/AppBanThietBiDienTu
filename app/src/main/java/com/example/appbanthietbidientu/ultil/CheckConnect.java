package com.example.appbanthietbidientu.ultil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CheckConnect {
    public static boolean haveNetworkConnected(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos= connectivityManager.getAllNetworkInfo();
        for(NetworkInfo ni : networkInfos){
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveConnectedWifi=true;
                if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if(ni.isConnected())
                        haveConnectedMobile=true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    public static void ShowToast_Short(Context context,String thongbao){
        Toast.makeText(context,thongbao,Toast.LENGTH_LONG).show();
    }
}
