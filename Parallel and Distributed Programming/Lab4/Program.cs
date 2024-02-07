// See https://aka.ms/new-console-template for more information
using System;
using static System.Net.WebRequestMethods;


namespace Lab4
{
  
    class Program
    {
    
        static void Main()
        {
            var urls = new List<string>
            {
                "www.cs.ubbcluj.ro",
                "www.amazon.com",
                "www.cs.ubbcluj.ro/~rlupsa/edu/pdp",
                "images.ctfassets.net/hrltx12pl8hq/3Z1N8LpxtXNQhBD5EnIg8X/975e2497dc598bb64fde390592ae1133/spring-images-min.jpg"
            };

            new CallbacksApproach(urls).Run();
            // new TasksApproach(urls).Run();
            // new AsyncAwaitApproach(urls).Run();
        }
    }

}