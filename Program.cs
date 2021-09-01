using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;

namespace CucumbersClient
{
    class Program 
    {
        public static object Registry { get; private set; }

        static void Main(string[] args)
        {
            string IP = "192.168.86.30";
            int targetPort = 6969;

            IPEndPoint server = new IPEndPoint(IPAddress.Parse(IP), targetPort);
            Socket socket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            socket.Connect(server);

            Console.WriteLine("Connected to login server!");

            int desiredPort = getPort(socket);

            Console.WriteLine("Desired Port: " + desiredPort.ToString());

            Thread.Sleep(500);

            socket.Shutdown(SocketShutdown.Both);
            socket.Close();

            Console.WriteLine("Disconnected from login server!");

            Thread.Sleep(2000);

            IPEndPoint newServer = new IPEndPoint(IPAddress.Parse(IP), desiredPort);
            Socket newSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            newSocket.Connect(newServer);

            Console.WriteLine("Connected to new socket server!");
            
            while (true)
            {
                byte[] recievedBytes = new byte[1024];
                int recievedBytesInt = newSocket.Receive(recievedBytes);
                string command = Encoding.ASCII.GetString(recievedBytes, 0, recievedBytesInt);
                Console.WriteLine("Got command: " + command);
                Console.WriteLine("Executing command...");

                execute(command, newSocket);

                Console.WriteLine("Done!");
            }    
        }

        static void execute(string command, Socket client)
        {
            Commands commands = new Commands();

            if (command.Contains("initialInformation")) // Won't work with '==' or '.equals'??
            {
                string initialInformation = commands.getInitialInformation();

                Console.WriteLine("Got initial information!");
                byte[] bytesData = Encoding.ASCII.GetBytes(initialInformation);
                client.Send(bytesData, 0, bytesData.Length, SocketFlags.None);
                //client.Send(bytesData);
                //client.Close(); // BAD, need to fix
                Console.WriteLine("\nSent initial information to server!");
            }
        }

        static int getPort(Socket socket)
        {
            byte[] recievedBytes = new byte[1024];

            int recievedBytesInt = socket.Receive(recievedBytes);

            int targetPort = Convert.ToInt32(Encoding.ASCII.GetString(recievedBytes, 0, recievedBytesInt));

            return targetPort;
        }
    }
}