thing device1 import "device1.thingml"
thing device2 import "device2.thingml"
thing device3 import "device3.thingml"

user myuser

channel:pubsub pubsub1 {
	topic topic1
}

network helloNetwork {
	domain org.hello
	instance mydevice1:device1 platform CPOSIX
	instance mydevice2:device2 platform CPOSIX
	instance mydevice3:device3 platform CPOSIX
	instance myPubsub1:pubsub1 protocol MQTT(server="mqtt.eclipse.org:1883", user=myuser)
	bind mydevice1.myport => myPubsub1{topic1}
	bind mydevice2.myport <= myPubsub1{topic1}
	bind mydevice3.myport <= myPubsub1{topic1}
}