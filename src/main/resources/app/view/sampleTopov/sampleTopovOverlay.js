(function () {
    'use strict';

    // injected refs
    var $log, tov, stds;

    // our overlay definition
    var overlay = {
        // NOTE: this must match the ID defined in AppUiTopovOverlay
        overlayId: 'sdn-lab-overlay',
        glyphId: 'cloud',
        tooltip: 'SDN Lab',

        glyphs: {
            star4: {
                vb: '0 0 8 8',
                d: 'M1,4l2,-1l1,-2l1,2l2,1l-2,1l-1,2l-1,-2z'
            },
            banner: {
                vb: '0 0 6 6',
                d: 'M1,1v4l2,-2l2,2v-4z'
            }
        },

        activate: function () {
            $log.debug("SDN LAb topology overlay ACTIVATED");
        },
        deactivate: function () {
            stds.stopDisplay();
            $log.debug("SDN LAb topology overlay DEACTIVATED");
        },

        // detail panel button definitions
        buttons: {
            addHost: {
                gid: 'endstation',
                tt: 'Add Host',
                cb: function (data) {
                    $log.info('Add host invoked with data:', data);

                    //ubuntu docker image example
                    stds.sendAddHost("ubuntu", data.id)

                }
            },
            addLink: {
                gid: 'chain',
                tt: 'Add link',
                cb: function (data) {
                    stds.sendAddLink();
                    $log.info('Add link invoked with data:', data);

                }
            },
            removeLink: {
                gid: 'map',
                tt: 'Remove link',
                cb: function (data) {
                    stds.sendRemoveLink();
                    $log.info('Remove link invoked with data:', data);

                }
            },
            removeHost: {
                gid: 'stop',
                tt: 'Remove host',
                cb: function (data) {
                    stds.sendRemoveHost(data.props.IP);
                    $log.info('Remove service invoked with data:', data);

                }
            },

        },

        // Key bindings for traffic overlay buttons
        // NOTE: fully qual. button ID is derived from overlay-id and key-name
        keyBindings: {
            A: {
                cb: buttonCallback,
                tt: 'Add device',
                gid: 'switch'
            },

            _keyOrder: [
                'A'
            ]
        },

        hooks: {

            // hooks for when the selection changes...
            empty: function () {
                selectionCallback('empty');
            },
            link: function () {
                selectionCallback('link');
                tov.removeDetailButton('addLink');
                tov.removeDetailButton('removeLink');
            },
            single: function (data) {
                selectionCallback('single', data);
            },
            multi: function (selectOrder) {
                selectionCallback('multi', selectOrder);
                tov.addDetailButton('addLink');
                tov.addDetailButton('removeLink');
            }

        }
    };


    function buttonCallback(x) {
        $log.debug('Add device callback', x);
        stds.sendAddDevice();
    }

    function selectionCallback(x, d) {
        $log.info('Selection callback', x, d);

        stds.sendDeviceClick(x, d);
    }

    // invoke code to register with the overlay service
    angular.module('ovSampleTopov')
        .run(['$log', 'TopoOverlayService', 'SampleTopovDemoService',

            function (_$log_, _tov_, _stds_) {
                $log = _$log_;
                tov = _tov_;
                stds = _stds_;
                tov.register(overlay);
            }]);

}());
