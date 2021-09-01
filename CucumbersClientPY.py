import socket, time

SERVER = "192.168.86.30"
LOGIN_PORT = 6969

def getPort(socket):
    port = socket.recv(1024)
    return int(port)

loginSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

loginSocket.connect((SERVER, LOGIN_PORT))

print("# Connected to login server")

port = getPort(loginSocket)
loginSocket.close()

print("# Got assigned a port: " + str(port))

time.sleep(2)

print("# Connecting to new socket")

while True:
    time.sleep(1)
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    sock.connect((SERVER, port))

    print("\n# Connected to new socket\n")
    print("# Listening for incoming commands...")
    command = sock.recv(1024)
    command = command.decode("utf-8").split("\n")[0]

    print("! Got Command: " + command)

    if command == "initialInformation":
        sock.sendall(b"Recieved this mate?? WaNaNa gEt Shanked up BRUV???!?!??")
        print("# Sent friendly and defenitely not threatening message to server :)")
    print("Restarting socket...")
    sock.close()
