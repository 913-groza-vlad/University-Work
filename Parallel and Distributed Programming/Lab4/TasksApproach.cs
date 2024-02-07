using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    class TasksApproach : Approach
    {
        public TasksApproach(List<string> Urls) : base(Urls)
        {
            ParserType = "Tasks";
        }

        public override void Run()
        {
            var count = 0;
            var tasks = Urls.Select(url => Task.Run(() => {
                var index = ++count;
                Start(SocketOperations.CreateSocket(url, index));
            })).ToList();
            Task.WhenAll(tasks).Wait();
        }

        private Task Start(SocketOperations socket)
        {
            socket.BeginConnectAsync().Wait();
            ConfirmSocketConnected(socket);

            var sendTask = socket.BeginSendAsync();
            sendTask.Wait();
            var sentBytes = sendTask.Result;
            ConfirmSent(socket, sentBytes);

            socket.BeginReceiveAsync().Wait();
            ConfirmReceived(socket);

            socket.ShutdownAndClose();

            return Task.CompletedTask;
        }
    }
}
