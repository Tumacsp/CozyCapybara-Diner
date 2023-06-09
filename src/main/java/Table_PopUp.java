import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class Table_PopUp implements ActionListener, WindowListener, InternalFrameListener, Refreshable, MouseListener {

    private JFrame frame;
    private JDesktopPane desktopPane;
    private JPanel p1;
    private JLabel labelID, labelName, labelTime, labelPhoneNumber, labelCap;
    private JTextField tfName, tfTime, tfPhoneNumber, take_idTable, takeCap;
    private JButton btn_booked, btn_closed, btn_edit;
    private JInternalFrame internal_frame_1, internal_frame_2;
    private JComboBox selectStatus;
    private JTable tabledetails;
    private Database db;
    private TablePanel tablePanel;
    private MenuPanel menuPanel;
    private ArrayList<Table> tables;
    private MainGUI mainGUI;
    private JTextField[] textFieldArray;
    public Table_PopUp(TablePanel tablePanel) {
        db = new Database();
        frame = new JFrame();
        frame.setBackground(Color.yellow);
        desktopPane = new JDesktopPane();
        this.tablePanel = tablePanel;
        p1 = new JPanel();
        labelID = new JLabel("ID Table ( 1 - 8 ) : ");
        labelName = new JLabel("Customer Name : ");
        labelTime = new JLabel("Time : ");
        labelPhoneNumber = new JLabel("Phone Number : ");
        labelCap = new JLabel("Capacity : ");
        tfName = new JTextField("",20);
        tfTime = new JTextField("",20);
        tfPhoneNumber = new JTextField("",20);
        take_idTable = new JTextField("",20);
        takeCap = new JTextField("",20);
        btn_booked = new JButton("Booked");
        btn_closed = new JButton("Close");
        btn_edit = new JButton("Edit");
        tabledetails = new JTable();
        selectStatus = new JComboBox();

        textFieldArray = new JTextField[5];
        textFieldArray[0] = take_idTable;
        textFieldArray[1] = tfName;
        textFieldArray[2] = tfTime;
        textFieldArray[3] = tfPhoneNumber;
        textFieldArray[4] = takeCap;

        //selectStatus.addItem("booked");
        selectStatus.addItem("Free");
        selectStatus.addItem("Busy");
        selectStatus.addItem("Closed");
        internal_frame_1 = new JInternalFrame("TableNum", true, true, true, true);
        internal_frame_2 = new JInternalFrame("JTableNum", true, true, true, true);

        //SETBOUNDS
        labelID.setBounds(50,40,200,30);
        labelName.setBounds(50,80,200,30);
        labelTime.setBounds(50,120,200,30);
        labelPhoneNumber.setBounds(50,160,200,30);
        labelCap.setBounds(50,200,200,30);
        //SETBOUNDS TEXTFIELD
        take_idTable.setBounds(200,40,200,30);
        tfName.setBounds(200,80,200,30);
        tfTime.setBounds(200,120,200,30);
        tfPhoneNumber.setBounds(200,160,200,30);
        takeCap.setBounds(200,200,200,30);
        //SETBOUNDS COMBOBOX
        selectStatus.setBounds(200, 240, 200, 30);
        //SETBOUNDS BUTTONS
        btn_booked.setBounds(100,300,75,30);
        btn_edit.setBounds(200,300,75,30);
        btn_closed.setBounds(300,300,75,30);
        //ADD
        p1.add(labelID);
        p1.add(take_idTable);

        p1.add(labelName);
        p1.add(tfName);

        p1.add(labelTime);
        p1.add(tfTime);

        p1.add(labelPhoneNumber);
        p1.add(tfPhoneNumber);

        p1.add(labelCap);
        p1.add(takeCap);

        p1.add(selectStatus);
        p1.add(btn_closed);
        p1.add(btn_booked);
        p1.add(btn_edit);

        //LAYOUT
        p1.setLayout(null);

        //INTERNAL FRAME SETTING
        int x1 = internal_frame_1.getX() + internal_frame_1.getWidth() + 550;
        int y1 = internal_frame_1.getY() + 40;
        internal_frame_1.setLocation(x1, y1);
        internal_frame_1.pack();
        internal_frame_1.setVisible(true);
        internal_frame_1.setSize(new Dimension(450, 400));
        internal_frame_2.setLocation(x1 - 520, y1);
        internal_frame_2.setVisible(true);
        internal_frame_2.setSize(new Dimension(500, 500));

        //ADD TO Frame-Panel
        internal_frame_1.add(p1);
        desktopPane.add(internal_frame_1);
        desktopPane.add(internal_frame_2);
        frame.add(desktopPane);

        //ADD ACTIONLISTENER
        btn_booked.addActionListener(this);
        btn_closed.addActionListener(this);
        btn_edit.addActionListener(this);
        frame.addWindowListener(this);
        internal_frame_1.addInternalFrameListener(this);
        internal_frame_2.addInternalFrameListener(this);

       // JTABLE
         DefaultTableModel model = (DefaultTableModel)tabledetails.getModel();
         db.loadTable();
         tables = db.getTable();
         setJTable();
         JScrollPane scrollPane = new JScrollPane(tabledetails);
         internal_frame_2.add(scrollPane);
        tablePanel = new TablePanel(mainGUI, menuPanel);
        db.addContactView(this);

//        db.addContactView(tablePanel);


        frame.setSize(1080, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        internal_frame_1.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
        internal_frame_2.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    }

    public JTextField getTake_idTable() {
        return take_idTable;
    }

    public void setTake_idTable(JTextField take_idTable) {
        this.take_idTable = take_idTable;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public void setTfName(JTextField tfName) {
        this.tfName = tfName;
    }

    public JTextField getTfTime() {
        return tfTime;
    }

    public void setTfTime(JTextField tfTime) {
        this.tfTime = tfTime;
    }

    public JTextField getTfPhoneNumber() {
        return tfPhoneNumber;
    }

    public void setTfPhoneNumber(JTextField tfPhoneNumber) {
        this.tfPhoneNumber = tfPhoneNumber;
    }

    public JTextField getTakeCap() {
        return takeCap;
    }

    public void setTakeCap(JTextField takeCap) {
        this.takeCap = takeCap;
    }
    public JComboBox getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(JComboBox selectStatus) {
        this.selectStatus = selectStatus;
    }

     public void setJTable() {
         DefaultTableModel model = (DefaultTableModel) tabledetails.getModel();
         model.setRowCount(0);
         Object[] columnsName = new Object[6];
         columnsName[0] = "ID"; columnsName[1] = "NAME" ;columnsName[2] = "DATE";
         columnsName[3] = "PHONE";columnsName[4] = "CAP";columnsName[5] = "STATUS";
         model.setColumnIdentifiers(columnsName);
         Object[] rowData = new Object[6];
         JTableHeader Theader = tabledetails.getTableHeader();
         Color txtOrange = new Color(0, 0, 0);
         Color backgroundOrange = new Color(255, 128, 0);
         Theader.setBackground(backgroundOrange);
         Theader.setForeground(txtOrange);
         tabledetails.addMouseListener(this);
         for (int i = 0; i < tables.size(); i++) {
             rowData[0] = tables.get(i).getId();
             rowData[1] = tables.get(i).getTableNameCus();
             rowData[2] = tables.get(i).getTableTimeres();
             rowData[3] = tables.get(i).getTablePhoneCus();
             rowData[4] = tables.get(i).getTableCap();
             rowData[5] = tables.get(i).getTableStatus();
             model.addRow(rowData);
         }
     }


    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(btn_booked)) {
            if((take_idTable.getText().equals("")) || (tfName.getText().equals("")) || (tfPhoneNumber.getText().equals("")) || (tfTime.getText().equals(""))){
                JOptionPane.showMessageDialog(null, " Please fill the form."); //show message
            }if(Integer.parseInt(take_idTable.getText()) > 8) {
                JOptionPane.showMessageDialog(null, " Sorry, We have only 8 tables."); //show message
            }if((tfPhoneNumber.getText().length()) != 10) {
                if ((tfPhoneNumber.getText()).length() < 10) {
                    // Phone number is too short. less than 10 digits
                    JOptionPane.showMessageDialog(null, " Phonenumber requires 10 digits.");
                } else if ((tfPhoneNumber.getText()).length() > 10) {
                    // Phone number is too long. more than 10 digits
                    JOptionPane.showMessageDialog(null, " Phonenumber should not exceed 10 digits.");
                }
            }else{
                System.out.println("GET DATA FROM FORMS!");
                db.reserveTable(take_idTable.getText(), tfName.getText(), tfPhoneNumber.getText(), tfTime.getText()); //ADD TO TABLE
                db.addContactView(tablePanel);
                db.updateModel(tables);
                Table t = db.searchTableById(Integer.parseInt(String.valueOf(take_idTable.getText())));
                JOptionPane.showMessageDialog(null, " " + t.getInfocustomer() , "Booked Table By ",JOptionPane.PLAIN_MESSAGE);

                //frame.dispose();
            }
        } else if (ae.getSource().equals(btn_closed)) {
            int windowClose = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this application?", "Confirm Close", JOptionPane.YES_NO_OPTION);
            if (windowClose == JOptionPane.YES_OPTION) {
                frame.dispose();
            }
        } else if(ae.getSource().equals(btn_edit)){
            if (take_idTable.getText().equals("") | takeCap.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Pls insert idtable or cap");
            }else{
                db.editTable(this);
                db.addContactView(tablePanel);
                db.updateModel(tables);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this application?", "Confirm Close", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            frame.dispose();
        } else {
            return;
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //DO NOTHING
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        int internalClose = JOptionPane.showConfirmDialog(frame, "Are you sure you want to close this application?", "Confirm Close", JOptionPane.YES_NO_OPTION);
        if (internalClose == JOptionPane.YES_OPTION) {
            frame.dispose();
        } else {
            return;
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }

    @Override
    public void refreshtable(ArrayList<Table> tables) {
        this.tables = tables;
        setJTable();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ArrayList<Object> rowData = new ArrayList<>();
        if (e.getSource().equals(tabledetails)){
            int row = tabledetails.getSelectedRow(); // รับแถวที่ถูกเลือก

            // นำข้อมูลจากแถวที่ถูกเลือกมาแสดงผล
            for (int column = 0; column < tabledetails.getColumnCount(); column++) {
                Object data = tabledetails.getValueAt(row, column);
                rowData.add(data);
                System.out.println(data);
            }
            for (int i = 0; i < rowData.size()-1; i++) {
                textFieldArray[i].setText(rowData.get(i).toString());
                System.out.println(i);

                }
//            if (rowData.get(5).equals("booked") | rowData.get(5).equals("Booked")){
//                System.out.println(""+rowData.get(0));
//                Table t = db.searchTableById(Integer.parseInt(String.valueOf(rowData.get(0))));
//                System.out.println("table Id"+t.getId());
//                JOptionPane.showMessageDialog(null, "" + t.getInfocustomer() , "Booked Table By ",JOptionPane.PLAIN_MESSAGE);



        }
        }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}