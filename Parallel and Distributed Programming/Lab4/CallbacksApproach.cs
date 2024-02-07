using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    class CallbacksApproach : Approach
    {

        public CallbacksApproach(List<string> Urls) : base(Urls)
        {
            ParserType = "Direct Callbacks";
        }


        public override void Run()
        {
            var count = 0;
            Urls.ForEach(url =>
            {
                var index = ++count;
                string fileName;

                Start(SocketOperations.CreateSocket(url, index));
            });
           
        }

        private void OnConnected(SocketOperations socket)
        {
            ConfirmSocketConnected(socket);
            socket.BeginSend(OnSent);
        }

        private void OnSent(SocketOperations socket, int bytesSent)
        {
            ConfirmSent(socket, bytesSent);
            socket.BeginReceive(OnReceived);
        }

        private void OnReceived(SocketOperations socket)
        {
            ConfirmReceived(socket);
            socket.ShutdownAndClose();
        }

        private void Start(SocketOperations socket)
        {
            socket.BeginConnect(OnConnected);
            do
            {
                Thread.Sleep(100);
            }
            while (socket.Connected);
        }
    }
}
