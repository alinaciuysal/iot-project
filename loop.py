import paho.mqtt.client as mqtt
import json
import MySQLdb



def update_sold_item(item_id):
    print 'sold item: ' + item_id
    
    db = MySQLdb.Connect("server ip with mysql", "piroot", "", "nodered")
    with db:
        cur = db.cursor()
        db.ping(True)
        cur.execute("SELECT productAmount from nodered.product where id=" + str(item_id))
        row = cur.fetchone()
        new_amount = row[0] - 1
        cur.execute("UPDATE nodered.product SET productAmount ="+str(new_amount)+" where machineId=1 and id=" + str(item_id))
        db.commit()
    db.close()


def alert_temperature(payload):
    print 'machine id: ' + str(payload["machine_id"]) + ' temperature: ' + str(payload["payload"])
    
    db = MySQLdb.Connect("server ip with mysql", "piroot", "", "nodered")
    with db:
        cur = db.cursor()
        db.ping(True)
        cur.execute("INSERT INTO nodered.machinestatus (temperature, date, isOperating, machineId) VALUES('" + str(payload["payload"]) + "', current_timestamp(), '1', '" +str(payload["machine_id"]) + "')")
        db.commit()
    db.close()
        
# The callback for when the client receives a CONNACK response from the server.
def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

    # Subscribing in on_connect() means that if we lose the connection and
    # reconnect then subscriptions will be renewed.
    client.subscribe([("sale", 2), ("temp", 2)])


# The callback for when a PUBLISH message is received from the server.
def on_message(client, userdata, msg):
    if msg.topic == 'sale':
        update_sold_item(msg.payload)
    elif msg.topic == 'temp':
        alert_temperature(json.loads(msg.payload))
    else:
        print 'unconventional message arrived'
        print 'topic: ' + msg.topic + 'content:' + str(msg.payload) 



client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.connect("176.9.41.239", 1883, 60)

# Blocking call that processes network traffic, dispatches callbacks and
# handles reconnecting.
# Other loop*() functions are available that give a threaded interface and a
# manual interface.
client.loop_forever()
