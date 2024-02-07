using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    abstract class Approach
    {
        protected List<string> Urls { get; }
        protected string ParserType { get; set; }

        protected Approach(List<String> Urls) { 
            this.Urls = Urls;

        }

        public abstract void Run();

        protected void ConfirmSocketConnected(SocketOperations socket)
        {
            Console.WriteLine($"Parser type: {ParserType} -> Socket {socket.Id} connected to {socket.BaseUrl}{socket.RelativeUrl}");
        }

        protected void ConfirmSent(SocketOperations socket, int bytesSent)
        {
            Console.WriteLine($"Socket {socket.Id}: Sent {bytesSent} bytes to server.");

        }

        protected void ConfirmReceived(SocketOperations socket)
        {
            Console.WriteLine("\n" + socket.GetResponse);
        }
    }
}
