package pirpleProject2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Dashboard implements ActionListener {

    private JFrame frame;
    private JPanel listPanel;
    private JPanel addPanel;
    private JButton newListButton;
    private ArrayList<JButton> buttons;
    private ArrayList<ToDoList> lists;
    private int listCount;

    public int getListCount() {
        return listCount;
    }

    public ArrayList<ToDoList> getLists() {
        return lists;
    }

    private void setAddPanel(){
        // create and specify the panel
        addPanel=new JPanel();
        addPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        addPanel.setBackground(Color.CYAN);
        addPanel.setBounds(0,480,600,80);

        // create, specify and add the button to the panel

        newListButton= new JButton("Create new list");
        newListButton.setPreferredSize(new Dimension(150,60));
        newListButton.setForeground(Color.BLACK);
        newListButton.setFont(new Font("anyway",Font.ITALIC,15));
        newListButton.setFocusable(false);
        newListButton.addActionListener(this);

        addPanel.add(newListButton);
    }

    private void setListPanel(){
        listPanel= new JPanel();
        listPanel.setLayout(new GridLayout(25,1));
        listPanel.setBounds(0,0,600,520);
        listPanel.setBorder(BorderFactory.createBevelBorder(2));
        listPanel.setBackground(Color.lightGray);

    }

    public Dashboard(){
        listCount=0;
        buttons= new ArrayList<>();
        lists=new ArrayList<>();

        frame= new JFrame();

        setAddPanel();
        setListPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600,600));

        frame.add(addPanel);
        frame.add(listPanel);

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==newListButton){
            newListAction();
        }
        else if(buttons.contains(e.getSource())){
            listButtonAction((JButton) e.getSource());
        }
    }

    public void newListAction(){
        ToDoList newList= new ToDoList(this);
        frame.setVisible(false);

    }

    public void listButtonAction(JButton button){
        int index=buttons.indexOf(button);
        lists.get(index).getFrame().setVisible(true);
        this.frame.setVisible(false);
    }

    public void addList(ToDoList list){
        this.lists.add(lists.size(),list);
        JButton newButton= new JButton(list.getTitle());
        buttons.add(buttons.size(),newButton);
        newButton.addActionListener(this);

        listPanel.add(newButton);
        frame.pack();
        frame.setVisible(true);
        listCount++;
    }

    public static void main(String[] args){
        new Dashboard();
    }
}
