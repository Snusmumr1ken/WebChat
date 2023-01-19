## Datagrok test task for Junior Software Engineer position

### Intro
So, in this doc, I'll write my experience of developing a web-based chat application.
User is able to choose his name, change it anytime, and post any text message to the chat.
Web sockets are used to connect to the server and get and send messages to other users.

It was done in 12 hours.

### Stack
It is developed in `Java 17` with `Spring Boot`, `webjars-locator-core`, `sockjs-client`, `stomp-websocket`, `jquery`, and basic `HTML/CSS/JS`.

### Begining
At first, I made a simple pplication in vanilla `Java 8` using `java.net.Socket` and `java.io.DataInput/OutputStream`.

There were to applications: `Server` and `Client`.

Server is listening to the specific port. When user connects, a new thread created for listening for his messages and redirecting them to all.

From the other hand, a client connects to the port, there is a loop which waits for his messages and a thread for getting messages from other people.

`Server.listen(int port)`:

      private void listen(int port) throws IOException {
          ss = new ServerSocket(port);
          System.out.println("Listening on " + ss);

          while (true) {
              Socket s = ss.accept();
              System.out.println("Connection from " + s);
              DataOutputStream dout = new DataOutputStream(s.getOutputStream());
              socketOutputMap.put(s, dout);

              new ServerThread(this, s, db);
          }
      }
      
 `Logic of each Server connection`:
 
    @Override
    public void run() {
        try {
            DataInputStream din = new DataInputStream(socket.getInputStream());

            while (true) {
                String message = din.readUTF();
                System.out.println("Sending " + message);
                server.sendToAll(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            server.removeSocket(socket);
        }
    }
    
`Client application logic`:

  public Client () {
        try {
            socket = new Socket("127.0.0.1", 5000);
            System.out.println( "connected to " + socket);
            din = new DataInputStream( socket.getInputStream() );
            dout = new DataOutputStream( socket.getOutputStream() );

            new Thread(this).start();

            // checks for user entered messages
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String inputMessage = scanner.nextLine();
                if (!inputMessage.isEmpty() && !inputMessage.isBlank()) {
                    System.out.println(inputMessage);
                    dout.writeUTF(inputMessage);
                }
            }
    }
    
That's it for a small test aplication. The whole code, I can share on your demand.

### Real work
To build a real web application with a client side in the browser,
I started by reading an official Spring framework guide "Using WebSocket to build an interactive web application".

Two big tasks there were:

1. Develop UI
2. Implement WebSockets logic using high level framework tools.

Honestly, I am absolutely bad at building UI.
Even this pretty small application was not adapted for mobile devices.
So, it took me some time to make a responsive UI based on DOM and MVC.

At first, I wanted to use bootstrap, but, imho, it's overkill for such small things.
