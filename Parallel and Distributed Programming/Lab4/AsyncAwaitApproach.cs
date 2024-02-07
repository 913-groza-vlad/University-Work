using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab4
{
    class AsyncAwaitApproach : Approach
    {
        public AsyncAwaitApproach(List<string> Urls) : base(Urls)
        {
            ParserType = "Async/Await Tasks";
        }

        public override void Run()
        {
            var count = 0;
            var tasks = Urls.Select(url => Task.Run(async () => {
                var index = ++count;
                await Start(SocketOperations.CreateSocket(url, index));
            })).ToList();
            Task.WhenAll(tasks).Wait();
        }

        private async Task Start(SocketOperations socket)
        {
            await socket.BeginConnectAsync();
            ConfirmSocketConnected(socket);

            var numberOfSentBytes = await socket.BeginSendAsync();
            ConfirmSent(socket, numberOfSentBytes);

            await socket.BeginReceiveAsync();
            ConfirmReceived(socket);

            socket.ShutdownAndClose();
        }
    }
}
