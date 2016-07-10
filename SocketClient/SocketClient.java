/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient;

/**
 *
 * @author pradeep
 */
import java.awt.Color; 
import java.awt.BorderLayout; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.net.*; 

class SocketClient extends JFrame implements ActionListener 
{
    JLabel text , clicked ;
    JButton button;
    JPanel panel;
    JTextField textField;
    
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null ;
    
    SocketClient()
    {
        text = new JLabel("Text to send over socket: ");
        textField = new JTextField(30);
        
        button = new JButton("Click Me");
        button.addActionListener(this);
        
        panel = new JPanel();
        panel.setLayout(new BorderLayout() );
        
        panel.setBackground(Color.WHITE);
        
        getContentPane().add(panel);
        
        panel.add("North", text );
        panel.add("Center", textField );
        panel.add("South", button );
        
    }
    
    
    public void actionPerformed(ActionEvent event)
    {
        Object source = event.getSource() ;
        
        if( source == button )
        {
            //send data over socket
            String text = textField.getText();
            out.println(text);
            textField.setText(new String(""));
            
            //receive text from server
            try
            {
                String line = in.readLine() ;
                System.out.println("Text received : "+ line );
            }
            catch(IOException e)
            {
                System.out.println("Read failed");
                System.exit(1);
            }
        }
    }
    
    
    
    public void listenSocket()
    {
        //create socket connection
        try
        {
            socket = new Socket("localhost", 4567 );
            out = new PrintWriter(socket.getOutputStream() , true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(UnknownHostException e)
        {
            System.out.println("Unknown host : localhost.eng");;
            System.exit(1);
        }
        catch(IOException e)
        {
            System.out.println("No I/O");
            System.exit(1);
        }
    }
    
    
    
    public static void main(String[] args)
    {
        SocketClient frame = new SocketClient();
        
        frame.setTitle("Client Program");
        
        WindowListener wl = new WindowAdapter()
        {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        } ;
        
        frame.addWindowListener(wl);
        frame.pack();
        frame.setVisible(true);
        frame.listenSocket();
        
    }
}