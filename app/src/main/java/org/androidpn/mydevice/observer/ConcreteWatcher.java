package org.androidpn.mydevice.observer;

/**
 * Created by Saber on 2015/10/14.
 */
public class ConcreteWatcher implements Watcher
{

    @Override
    public void update(String str)
    {
        System.out.println(str);
    }

}