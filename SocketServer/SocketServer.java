/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

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


class SocketServer extends JFrame implements ActionListener {
    
    JButton button ;
    JLabel label = new JLabel("Text received over socket: ");
    JPanel panel ;
    JTextArea textArea = new JTextArea();
    ServerSocket server = null;
    Socket client = null;
    
    BufferedReader in = null;
    PrintWriter out = null;
    
    String line;
    
    
    SocketServer()
    {
        button = new JButton("Click me");
        button.addActionListener(this);
        
        panel = new JPanel() ;
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.white);
        
        getContentPane().add(panel);
        
        panel.add("North",label);
        panel.add("Center", textArea);
        panel.add("South", button);
    }
    
    
    public void actionPerformed(ActionEvent event)
    {
        if( event.getSource() == button )
        {
            textArea.setText(line);
        }
    }
    
    
    
    public void listenSocket()
    {
        try
        {
            server = new ServerSocket(4567);
        }
        catch(IOException e)
        {
            System.out.println("Could not listen on port 4567");
            System.exit(-1);
        }
        
        try
        {
            client = server.accept();
        }
        catch(IOException e)
        {
            System.out.println("Accept failed: 4567");
            System.exit(-1);
        }
        
        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream() , true);
        }
        catch(IOException e)
        {
            System.out.println("Accept failed: 4567");
            System.exit(-1);
        }
        
        
        while(true)
        {
            try
            {
                line = in.readLine() ;
                
                //send data to client
                out.println(line);
            }
            catch(IOException e)
            {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
        
    }
        
    
        protected void finalize()
        {
            //clean up
            try
            {
                in.close();
                out.close();
                server.close();
            }
            catch(IOException e)
            {
                System.out.println("Could not close");
                System.exit(-1);
            }
        }


        
        public static void main(String[] args)
        {
            SocketServer frame = new SocketServer();
            
            frame.setTitle("Server Program");
            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            frame.pack();
            frame.setVisible(true);
            
            frame.listenSocket();
        }
    
}
