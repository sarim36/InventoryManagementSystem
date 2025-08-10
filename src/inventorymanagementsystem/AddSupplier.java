package inventorymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;


public class AddSupplier extends JFrame implements ActionListener {
    
    JButton add, cancel;
    JTextField tfname, tfphone;
    
    AddSupplier() {
        
        setLayout(null);
        
        JLabel heading = new JLabel("ADD SUPPLIER");
        heading.setFont(new Font("Tahoma", Font.BOLD, 25));
        heading.setBounds(120, 20, 260, 40);
        add(heading);
        
        JLabel lblname = new JLabel("Supplier Name");
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblname.setBounds(60, 140, 120, 30);
        add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(200, 140, 150, 30);
        add(tfname);        
  
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblphone.setBounds(60, 220, 120, 30);
        add(lblphone);
        
        tfphone = new JTextField("XXXX-XXXXXXX");
        tfphone.setBounds(200, 220, 150, 30);
        add(tfphone);        
        
        
        add = new JButton("ADD SUPPLIER");
        add.setForeground(Color.WHITE);
        add.setBackground(Color.DARK_GRAY);
        add.setBounds(60, 350, 130, 30);
        add.addActionListener(this);
        add(add);
        
        cancel = new JButton("CANCEL");
        cancel.setForeground(Color.WHITE);
        cancel.setBackground(Color.DARK_GRAY);
        cancel.setBounds(220, 350, 130, 30);
        cancel.addActionListener(this);
        add(cancel);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/fifth.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(400, 30, 500, 400);
        add(image);
        
        getContentPane().setBackground(Color.WHITE);
        setBounds(260, 120, 940, 500);
        setVisible(true);
         
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String name=tfname.getText();
            String phone=tfphone.getText();
            try{
                Conn conn= new Conn();
                String str = "CALL insert_supplier('"+name+"','"+phone+"')";
                conn.s.executeUpdate(str);
                
                JOptionPane.showMessageDialog(null,"Supplier Added Successfully");
                setVisible(false);
            
            }catch (SQLException sqlException) {
                sqlException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding Supplier: " + sqlException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }catch(Exception e){
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }
    
    public static void main(String[] args) {
        new AddSupplier();
    }
}
