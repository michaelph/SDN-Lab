/*
 * Copyright 2015-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 Sample Demo module. This contains the "business logic" for the topology
 overlay that we are implementing.
 */

(function () {
    'use strict';

    // injected refs
    var $log, fs, flash, wss;

    var addDevice = 'addDevice';
    var addHost = 'addHost';
    var addLink = 'addLink';
    var removeLink = 'removeLink';
    var removeHost = 'removeHost';


    var tempWhat = null;

    //UTILS
    function sendDeviceClick(clickType, what) {
        if (clickType == 'single') {
            //flash.flash('Device clicked! ' + what);
            /*wss.sendEvent(deviceClicked, {
             id: what ? what.id : ''
             });*/
        } else if (clickType == 'multi') {
            flash.flash('Devices clicked! ' + what[0] + " : " + what[1]);
            tempWhat = what;
        }

    }

    //SDN LAB API


    function sendAddDevice() {

        flash.flash('Adding Device');
        wss.sendEvent(addDevice, {});
    }

    function sendAddHost(containerName, deviceId) {

        flash.flash('Adding host');
        wss.sendEvent(addHost, {containerName: containerName, deviceId: deviceId});

    }

    function sendAddLink() {
        wss.sendEvent(addLink, {
            deviceId1: tempWhat[0], deviceId2: tempWhat[1]
        });
        tempWhat = null;
    }

    function sendRemoveLink() {
        wss.sendEvent(removeLink, {
            deviceId1: tempWhat[0], deviceId2: tempWhat[1]
        });
        tempWhat = null;
    }

    function sendRemoveHost(hostAddress) {
        flash.flash('Removing host with IP address ' + hostAddress);
        wss.sendEvent(removeHost, {hostIpAddress: hostAddress});
    }

    // === ---------------------------
    // === Module Factory Definition

    angular.module('ovSampleTopov', [])
        .factory('SampleTopovDemoService',
            ['$log', 'FnService', 'FlashService', 'WebSocketService',

                function (_$log_, _fs_, _flash_, _wss_) {
                    $log = _$log_;
                    fs = _fs_;
                    flash = _flash_;
                    wss = _wss_;

                    return {
                        sendDeviceClick: sendDeviceClick,
                        sendAddDevice: sendAddDevice,
                        sendAddHost: sendAddHost,
                        sendAddLink: sendAddLink,
                        sendRemoveLink: sendRemoveLink,
                        sendRemoveHost: sendRemoveHost
                    };
                }]);
}());
