MusicAcitivity MyService

MusicAcitivity 注册了一个broadcastreceiver1
MyService 注册了一个broadcastreceiver2

MyService 用来保证后台播放
MusicAcitivity需要更新view时发送broadcastreceiver2 (隐式调用，intentfilter匹配) ,broadcastreceiver2接收到更新播放状态，然后发送broadcastreceiver1
broadcastreceiver1 接收到后根据参数更新MusicAcitivity