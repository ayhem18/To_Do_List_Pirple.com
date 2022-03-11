package pirpleProject2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ToDoList implements ActionListener {
    private JFrame frame;
    private ArrayList<JCheckBox> items;
    private JPanel itemPanel;
    private JPanel optionPanel;
    private Dashboard parent;

    private JButton saveButton;
    private JButton renameListButton;
    private JButton renameItemButton;
    private JButton addItemButton;

    private String title;

    public String getTitle(){
     return this.title;
    }

    public JFrame getFrame() {
        return frame;
    }

    private void setItemPanel(){
        itemPanel= new JPanel();
        itemPanel.setLayout(new GridLayout(15,1));
        itemPanel.setBounds(0,0,600,520);
        itemPanel.setBackground(Color.lightGray);
    }

    private void setOptionPanel(){
        optionPanel= new JPanel();
        optionPanel.setBounds(0,440,300,120);
        optionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
        optionPanel.setBackground(Color.CYAN);

        saveButton= new JButton("save");
        saveButton.setPreferredSize(new Dimension(110,40));
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);

        renameListButton = new JButton("rename list");
        renameListButton.setPreferredSize(new Dimension(110,40));
        renameListButton.setFocusable(false);
        renameListButton.addActionListener(this);

        renameItemButton = new JButton("rename item");
        renameItemButton.setPreferredSize(new Dimension(110,40));
        renameItemButton.setFocusable(false);
        renameItemButton.addActionListener(this);

        addItemButton = new JButton("add item");
        addItemButton.setPreferredSize(new Dimension(110,40));
        addItemButton.setFocusable(false);
        addItemButton.addActionListener(this);

        optionPanel.add(renameListButton);
        optionPanel.add(renameItemButton);
        optionPanel.add(addItemButton);
        optionPanel.add(saveButton);
    }

    private String orderingString(int index){
        if(index==1)
            return "1st ";
        else if(index==2)
            return "2nd ";
        else if(index==3){
            return "3rd ";
        }
        else
            return index+"th ";
    }

    ToDoList(Dashboard parent){
        items= new ArrayList<>();
        this.parent=parent;

        frame= new JFrame(orderingString(this.parent.getListCount()+1)+"list");
        this.title="new List";

        setItemPanel();
        setOptionPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,600));
        frame.setLayout(null);

        frame.add(optionPanel);
        frame.add(itemPanel);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addItemButton){
            addItemAction();
        }

        else if(e.getSource()==renameListButton){
            renameListButtonAction();
        }
        else if(e.getSource()==saveButton){
            saveButtonAction();
        }
        else if(e.getSource()==renameItemButton){
            renameItemAction();
        }
    }

    // this method is created to show that either two items or two lists have a common name
    private void announceNamingConflict(){
        String errorMessage="This name has already been chosen. Please pick another name";
        String namingConflict="Naming conflict";
        JOptionPane.showMessageDialog(this.frame,errorMessage,
                namingConflict,JOptionPane.WARNING_MESSAGE);
    }

    // addItemButton methods
    private void customizeCheckBox(JCheckBox box){
        box.setFocusable(false);
        box.setForeground(Color.blue);
        box.setHorizontalTextPosition(JCheckBox.LEFT);
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.setFont(new Font("damn!!",Font.ITALIC,30));

    }

    private boolean checkBoxNames(String title){
        boolean unique= true;
        for(JCheckBox item: items ){
            unique= !item.getText().equals(title);
            if(!unique){
                break;
            }
        }
        return unique;
    }
    
    private void addItemAction(){
        String itemName;
        itemName=JOptionPane.showInputDialog("Please enter the name of the new-added item");
        if(itemName!=null && itemName.length()>0){

        if(checkBoxNames(itemName)){
        JCheckBox newBox = new JCheckBox(itemName);

        customizeCheckBox(newBox);

        items.add(items.size(),newBox);
        itemPanel.add(items.get(items.size()-1));
        frame.pack();
        }

        else{
            announceNamingConflict();
        }
        }
    }

    // RenameListButton methods

    private boolean checkNames(String title){
        boolean unique=true;
        ArrayList<ToDoList> lists= this.parent.getLists();
        for(ToDoList list: lists){
            unique= !(list.getTitle().equals(title));
            if(!unique)
                break;
        }
        return unique;
    }
    private void renameListButtonAction(){

        String newName;
        newName= JOptionPane.showInputDialog("Please enter the new name for the list");
        // if the user hits the button cancel or closes the window directly the returned string will be null
        // no action should be taken in this case

        if(newName!=null){

        if(checkNames(newName)){
        frame.setTitle(newName);
        }
        else{
            announceNamingConflict();
        }

        }

    }

    // savaButton methods
    private void saveButtonAction(){
        String confirmMessage="Are you sure you want to confirm these modifications ?";
        int answer= JOptionPane.showConfirmDialog(this.frame,confirmMessage,
                        "confirmation window",JOptionPane.YES_NO_OPTION);
        if(answer==0){

            if(checkNames(frame.getTitle())){
                this.title=this.frame.getTitle();
                this.parent.addList(this);
                this.frame.setVisible(false);
            }
            else
                announceNamingConflict();
        }
    }

    //renameItemButton methods
    private void renameItemAction(){
        if(items.isEmpty()){
            String errorMessage="we cannot rename items if the list is empty";
            JOptionPane.showMessageDialog(this.frame,errorMessage,"empty list",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String oldName;
        oldName = JOptionPane.showInputDialog("Please enter the name of the item to change");
        if(oldName!=null && oldName.length()>0){
            if(!checkBoxNames(oldName)){
                String newName;
                newName=JOptionPane.showInputDialog("Please enter the item's new name");
                for(JCheckBox item : items){
                    if(item.getText().equals(oldName)){
                        item.setText(newName);
                        break;
                    }
                }
            }
            else{
                String errorMessage="No item has been found with name "+oldName+".";
                JOptionPane.showMessageDialog(this.frame,errorMessage,"No item found",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }



}
