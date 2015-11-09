/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zs.devicemanager.device;

import com.zs.devicemanager.model.DeviceInfoIQ;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/** 
 * This class parses incoming IQ packets to NotificationIQ objects.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DeviceInfoIQProvider implements IQProvider {

    public DeviceInfoIQProvider() {
    }

    @Override
    public IQ parseIQ(XmlPullParser parser) throws Exception {

        DeviceInfoIQ deviceInfoIQ = new DeviceInfoIQ();
        for (boolean done = false; !done;) {
            int eventType = parser.next();
            if (eventType == 2) {
//                if ("longitude".equals(parser.getName())) {
//                    deviceInfoIQ.setLongitude(parser.nextText());
//                }
//                if ("latitude".equals(parser.getName())) {
//                    deviceInfoIQ.setLatitude(parser.nextText());
//                }
                if ("reqFlag".equals(parser.getName())) {
                    deviceInfoIQ.setReqFlag(parser.nextText());
                }
                if ("deviceCollection".equals(parser.getName())) {
                    deviceInfoIQ.setDeviceCollection(parser.nextText());
                }
                if ("deviceLimition".equals(parser.getName())) {
                    deviceInfoIQ.setDeviceLimition(parser.nextText());
                }
                if ("password".equals(parser.getName())) {
                    deviceInfoIQ.setPassword(parser.nextText());
                }
                if ("packageName".equals(parser.getName())) {
                    deviceInfoIQ.setPackageName(parser.nextText());
                }
                if ("hardwareSecurity".equals(parser.getName())) {
                    deviceInfoIQ.setHardwareSecurity(parser.nextText());
                }

            } else if (eventType == 3
                    && "deviceinfo".equals(parser.getName())) {
                done = true;
            }
        }

        return deviceInfoIQ;
    }

}
