thing device1 import "device1.thingml"
thing device2 import "device2.thingml"

channel:pubsub pubsub1 {
	topic topic1
}

network helloNetwork {
	domain org.hello
	instance mydevice1:device1 platform CPOSIX
	instance mydevice2:device2 platform CPOSIX
	instance myPubsub1:pubsub1 protocol MQTT
	bind mydevice1.myport => myPubsub1{topic1} => mydevice2.myport
}