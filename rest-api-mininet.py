from mininet.net import Containernet
from mininet.node import RemoteController
from mininet.log import setLogLevel, info

from bottle import route, run
net = "net_object"


def topology():
    "Create a network with some docker containers acting as hosts."
    global net
    net = Containernet()

    info('*** Adding controller\n')
    net.addController(name='c0',
                           controller=RemoteController,
                           ip='172.17.0.2',
                           protocol='tcp',
                           port=6653)

    info('*** Starting network\n')
    net.start()
    # server ip
    run(host='localhost', port=8090)
    # info('*** Running CLI\n')
    # CLI(net)

    info('*** Stopping network')
    net.stop()


@route('/cli/host/add/<hostName>/<deviceId>')
def addHostOnDevice(hostName, deviceId):
    info('*** Dynamically add a container at runtime\n')
    dockerHost = net.addDocker(hostName, dimage="ubuntu:trusty")
    for switch in net.switches:
        if switch.dpid == deviceId[3:]:
            ip = net.getNextIp()
            net.addLink(dockerHost, switch, params1={"ip": ip})
    return "Docker host container created!" + ip


@route('/cli/switch/add/<name>')
def addSwitch(name):
    switch = net.addSwitch(name)
    switch.start([net.get('c0')])
    return "Switch added! " + switch.dpid


@route('/cli/link/add/<switchId1>/<switchId2>')
def switchAddLink(switchId1, switchId2):
    switch1 = ''
    switch2 = ''
    for switch in net.switches:
        if switch.dpid == switchId1[3:]:
            switch1 = switch
        elif switch.dpid == switchId2[3:]:
            switch2 = switch
    if not switch1 or not switch2:
        info("Not devices where found")
    net.addLink(switch1, switch2)
    return "Link added!"


@route('/cli/link/rm/<deviceId1>/<deviceId2>')
def removeLink(deviceId1, deviceId2):
    device1 = ''
    device2 = ''
    for switch in net.switches:
        if switch.dpid == deviceId1[3:]:
            device1 = switch
        elif switch.dpid == deviceId2[3:]:
            device2 = switch
    if not device1 and device2:
        for host in net.hosts:
            if host.MAC() == deviceId1[:17].lower():
                device1 = host
    else:
        for host in net.hosts:
            if host.MAC() == deviceId2[:17].lower():
                device2 = host
    net.removeLink(node1=device1, node2=device2)
    return "Link removed!"


if __name__ == '__main__':
    setLogLevel('info')
    topology()
