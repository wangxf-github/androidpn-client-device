package mylocation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.mydevice.DeviceHandler;
import org.androidpn.mydevice.DeviceManager;
import org.json.JSONException;
import org.json.JSONObject;

public class GetLocation implements AMapLocationListener{
    private LocationManagerProxy mLocationManagerProxy;
    private JSONObject object;
    private DeviceInfoIQ device;
    private DeviceHandler handler;
    private String wlanMac;

    public  GetLocation(Context context,String wlanMac){
        object = new JSONObject();
        this.wlanMac = wlanMac;
        init(context);
    }
    private void init(Context context) {
        mLocationManagerProxy = LocationManagerProxy.getInstance(context);
        handler = new DeviceHandler();
        Log.e("jlkj"," in gouzao----");
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        try {
            mLocationManagerProxy.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 60 * 1000, 15, this);
        }catch (Exception e){
            Log.e("xxxx","xxxxxx",e);
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("amap===================",aMapLocation.toString()+"_");
        device = new DeviceInfoIQ();
        if(aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            Double geoLat = aMapLocation.getLatitude();
            Double geoLng = aMapLocation.getLongitude();
            Log.e("123",geoLat+"+++"+geoLng);
            try {
                object.put("geoLat",geoLat);
                object.put("geoLng",geoLng);
                device.setWifiMac(wlanMac);
                device.setLongitude(String.valueOf(geoLng));
                device.setLatitude(String.valueOf(geoLat));
                device.setAddress(aMapLocation.getAddress());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            stopLocation();
        }else {
            device.setLongitude("");
            device.setLatitude("");
            device.setAddress("");
        }
        Message message = new Message();
        message.obj=device;
        message.what= DeviceManager.LOCATION_INFO;
        handler.sendMessage(message);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public DeviceInfoIQ getDevice() {
        return device;
    }

    private void stopLocation() {
        if (mLocationManagerProxy != null) {

            mLocationManagerProxy.removeUpdates(this);
            mLocationManagerProxy.destory();
        }
        mLocationManagerProxy = null;
    }


}
