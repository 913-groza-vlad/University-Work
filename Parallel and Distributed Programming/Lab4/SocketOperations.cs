using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;

namespace Lab4
{
    class SocketOperations : Socket
    {
        public int Id { get; }
        public string BaseUrl { get; }
        public string RelativeUrl { get; }
        
        private IPEndPoint EndPoint { get; }
        private StringBuilder Response { get; } = new StringBuilder();
        private StringBuilder ResponseContent { get; } = new StringBuilder();
        private StringBuilder ResponseHeaders { get; } = new StringBuilder();
        private const int DefaultPort = 80;
        private const int BufferSize = 512;

        private byte[] ContentResponseForImage { get; set; } = new byte[100000];
        private int NrOfBytesRead = 0;

        private SocketOperations(string baseUrl, string relativeUrl, IPAddress ipAddress, int id) :
           base(ipAddress.AddressFamily, SocketType.Stream, ProtocolType.Tcp)
        {
            Id = id;
            BaseUrl = baseUrl;
            RelativeUrl = relativeUrl;
            EndPoint = new IPEndPoint(ipAddress, DefaultPort);
        }

        public static SocketOperations CreateSocket(string url, int id)
        {
            var index = url.IndexOf('/');
            var baseUrl = index < 0 ? url : url.Substring(0, index);
            var relativeUrl = index < 0 ? "/" : url.Substring(index);
            var ipAddress = Dns.GetHostEntry(baseUrl).AddressList[0];

            return new SocketOperations(baseUrl, relativeUrl, ipAddress, id);
        }

        private void SaveContentToFile(string headers, byte[] content)
        {
            var contentType = GetContentType(headers);
            Console.WriteLine(contentType);
            var filePath = $"response{Id}.{GetFileExtension(contentType)}";

            try
            {
                using StreamWriter writer = new StreamWriter(filePath);
                if (contentType.Contains("image/jpeg"))
                    writer.Write(ContentResponseForImage);
                else
                    writer.Write(GetResponseContent);
                Console.WriteLine($"Response for the request to {BaseUrl}{RelativeUrl} saved to file: {filePath}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Error saving response to file: {ex.Message}");
            }

            File.WriteAllBytes(filePath, content);
        }

        private string GetFileExtension(string contentType)
        {
            if (contentType.Contains("text/html"))
            {
                return "html";
            }
            if (contentType.Contains("image/jpeg"))
            {
                return "jpg";
            }

            return "txt";
        }

        public void BeginConnect(Action<SocketOperations> onConnected)
        {
            BeginConnect(EndPoint, asyncResult => {
                EndConnect(asyncResult);
                onConnected(this);
            }, null);
        }

        private void ReceiveResult(IAsyncResult asyncResult, byte[] buffer, Action<SocketOperations> onReceived)
        {
            var numberOfReadBytes = EndReceive(asyncResult);
            Response.Append(Encoding.ASCII.GetString(buffer, 0, numberOfReadBytes));
            buffer.CopyTo(ContentResponseForImage, NrOfBytesRead);
            NrOfBytesRead += numberOfReadBytes;

            // Check if the response contains the end of headers marker ("\r\n\r\n")
            var responseString = Response.ToString();
            var headerEndIndex = responseString.IndexOf("\r\n\r\n", StringComparison.Ordinal);

            if (headerEndIndex >= 0)
            {
                var headers = responseString.Substring(0, headerEndIndex);
                ResponseHeaders.Append(headers);

                // Check if we have received the full content based on Content-Length
                if (responseString.Length >= headerEndIndex + 4)
                {
                    var contentStartIndex = headerEndIndex + 4;
                    var contentLength = GetContentLength(headers);

                    if (responseString.Length >= contentStartIndex + contentLength)
                    {
                        // Complete response received
                        var content = Encoding.Default.GetBytes(responseString.Substring(contentStartIndex, contentLength));
                        ResponseContent.Clear().Append(content);

                        SaveContentToFile(headers, content);

                        onReceived(this);
                        return;
                    }
                }
            }

            // Continue receiving if the full response is not yet received
            BeginReceive(buffer, 0, BufferSize, SocketFlags.None, asyncResult2 =>
                ReceiveResult(asyncResult2, buffer, onReceived), null);
        }

        private int GetContentLength(string headers)
        {
            var contentLengthHeader = headers.Split('\n').FirstOrDefault(line => line.StartsWith("Content-Length:", StringComparison.OrdinalIgnoreCase));
            if (contentLengthHeader != null)
            {
                var lengthValue = contentLengthHeader
                    .Substring("Content-Length:".Length)
                    .Trim();

                if (int.TryParse(lengthValue, out var contentLength))
                {
                    return contentLength;
                }
            }

            return 0;
        }

        public string GetContentType(string headers)
        {
            var contentTypeHeader = headers.Split('\n').FirstOrDefault(line => line.StartsWith("Content-Type:", StringComparison.OrdinalIgnoreCase));
            if (contentTypeHeader != null)
            {
                var typeValue = contentTypeHeader
                    .Substring("Content-Type:".Length)
                    .Trim();

                return typeValue;
            }

            return "";
        }

        public void BeginReceive(Action<SocketOperations> onReceived)
        {
            var buffer = new byte[BufferSize];
            Response.Clear();

            BeginReceive(buffer, 0, BufferSize, SocketFlags.None, asyncResult =>
                ReceiveResult(asyncResult, buffer, onReceived), null);
        }

        public void BeginSend(Action<SocketOperations, int> onSent)
        {
            var stringToSend = $"GET {RelativeUrl} HTTP/1.1\r\n" + $"Host: {BaseUrl}\r\n" + "Content-Length: 0\r\n\r\n";

            var encodedString = Encoding.ASCII.GetBytes(stringToSend);

            BeginSend(encodedString, 0, encodedString.Length, SocketFlags.None, asyncResult => {
                var numberOfSentBytes = EndSend(asyncResult);
                onSent(this, numberOfSentBytes);
            }, null);
        }


        public Task BeginConnectAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource();
            BeginConnect(_ => { taskCompletion.TrySetResult(); });
            return taskCompletion.Task;
        });

        public Task<int> BeginSendAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource<int>();
            BeginSend((_, numberOfSentBytes) => taskCompletion.TrySetResult(numberOfSentBytes));
            return taskCompletion.Task;
        });

        public Task BeginReceiveAsync() => Task.Run(() =>
        {
            var taskCompletion = new TaskCompletionSource();
            BeginReceive(_ => taskCompletion.TrySetResult());
            return taskCompletion.Task;
        });

        public void ShutdownAndClose()
        {
            Shutdown(SocketShutdown.Both);
            Close();
        }

        public string GetResponse => Response.ToString();
        public string GetResponseContent => ResponseContent.ToString();
        public string GetResponseHeaders => ResponseHeaders.ToString();
    }
}
