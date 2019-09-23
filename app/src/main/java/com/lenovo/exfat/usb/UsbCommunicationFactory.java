package com.lenovo.exfat.usb;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.os.Build;
import android.util.Log;

public class UsbCommunicationFactory {
    private static final String TAG = UsbCommunicationFactory.class.getSimpleName();
    private static UnderlyingUsbCommunication underlyingUsbCommunication = UnderlyingUsbCommunication.DEVICE_CONNECTION_SYNC;

    public static UsbCommunication createUsbCommunication(UsbDeviceConnection deviceConnection,
                                                   UsbEndpoint outEndpoint,
                                                   UsbEndpoint inEndpoint){
        UsbCommunication communication;
        if (underlyingUsbCommunication == UnderlyingUsbCommunication.DEVICE_CONNECTION_SYNC) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                communication = new JellyBeanMr2Communication(deviceConnection, outEndpoint, inEndpoint);
            } else {
                Log.i(TAG, "using workaround usb communication");
                communication = new HoneyCombMr1Communication(deviceConnection, outEndpoint, inEndpoint);
            }
        } else {
            communication = new UsbRequestCommunication(deviceConnection, outEndpoint, inEndpoint);
        }

        return communication;
    }

}
