import socket

bruh = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

bruh.connect(("192.168.86.30", 6970))

print("Done!")
