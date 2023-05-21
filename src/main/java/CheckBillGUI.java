
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JPanel;


public class CheckBillGUI implements ActionListener, WindowListener{
    private JFrame f;
    private JPanel pshowTop,pshownamecus, pallmember, panelcheckbill, panelcenter, panelcal, panelfinal;
    private JLabel tablelaJlable, namecus, totalJlable, phonemem, total, calex, changeLabel;
    private JTextField tableid, namecusshow, getphonemem, moneycus;
    private JButton findmem, usePoint, checkBill, calButton;
    private JTextArea showdetail;
    private MenuPanel menuPanel;
    private Database db;
    private ArrayList<Member> member;
    private Double totalnum, change, pointuse;
    private Exchange exchange;


    private Member mem;
    public CheckBillGUI(MenuPanel menuPanel){
        f = new JFrame("Check Bill");
        pshowTop = new JPanel();
        pshownamecus = new JPanel();
        panelfinal = new JPanel();
        panelcal = new JPanel();
        this.menuPanel = menuPanel;
        tablelaJlable = new JLabel("Table ID");
        pallmember = new JPanel();
        tableid = new JTextField(7);
        db = new Database();
        panelcenter = new JPanel();
        panelcheckbill = new JPanel();
        namecusshow = new JTextField("customer",8);
        namecus = new JLabel("namecustomer");
        phonemem = new JLabel("Phone");
        total = new JLabel("total");
        getphonemem = new JTextField(7);
        findmem = new JButton("find");
        usePoint = new JButton("usePoint");
        checkBill = new JButton("checkBill");
        calex = new JLabel("Money");
        calButton = new JButton("cal");
        moneycus = new JTextField(8);
        showdetail = new JTextArea();
        changeLabel = new JLabel("Change");


        showdetail.setSize(250, 100);
//      namecusshow.setEditable(false);




        pshowTop.setLayout(new BorderLayout());
        pshowTop.add(tablelaJlable, BorderLayout.NORTH);
        tablelaJlable.setHorizontalAlignment(JLabel.CENTER);

        pshowTop.add(showdetail, BorderLayout.SOUTH);

        panelfinal.setLayout(new BorderLayout());
        pshownamecus.add(namecus);
        pshownamecus.add(namecusshow);
        pshowTop.add(pshownamecus, BorderLayout.CENTER);

        panelcenter.setLayout(new BorderLayout());
        pallmember.setLayout(new FlowLayout());
        pallmember.add(phonemem);
        pallmember.add(getphonemem);
        pallmember.add(findmem);
        panelcenter.add(total, BorderLayout.NORTH);
        total.setHorizontalAlignment(JLabel.CENTER);

        panelcal.add(calex);
        panelcal.add(moneycus);
        panelcal.add(calButton);
        panelfinal.add(panelcal, BorderLayout.NORTH);
        panelfinal.add(changeLabel, BorderLayout.CENTER);
        changeLabel.setHorizontalAlignment(JLabel.CENTER);
        panelcenter.add(panelfinal, BorderLayout.CENTER);
        panelcenter.add(pallmember, BorderLayout.SOUTH);
        usePoint.setEnabled(false);



        totalnum = menuPanel.getSum();
        Exchange exchange = new Exchange() {
            @Override
            public double calculate(double total) {
                if (Double.parseDouble(moneycus.getText())- totalnum < 0){
                    JOptionPane.showMessageDialog(null, " minas");
                    return 0;
                }
                return Double.parseDouble(moneycus.getText())- totalnum;
            }
        };

        // กำหนดค่าให้กับตัวแปร calculator ใน CheckBillGUI
        this.setCalculator(exchange);

        panelcheckbill.add(usePoint);
        panelcheckbill.add(checkBill);
        f.addWindowListener(this);
        checkBill.addActionListener(this);
        findmem.addActionListener(this);
        usePoint.addActionListener(this);
        f.setLayout(new BorderLayout());
        f.add(pshowTop, BorderLayout.NORTH);
        f.add(panelcenter, BorderLayout.CENTER);
        f.add(panelcheckbill, BorderLayout.SOUTH);
        calButton.addActionListener(this);
        f.setSize(250, 450);
        f.setVisible(true);
    }
    public static void main(String[] args) {
        MenuPanel menuPanel = new MenuPanel();
        new CheckBillGUI(menuPanel);
    }
    public void setCalculator(Exchange exchange) {
        this.exchange = exchange;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(mem);
        if(e.getSource().equals(findmem)){
            totalnum = menuPanel.getSum();
            mem = db.searchmemberByphone(getphonemem.getText());
            if (mem == null){
                System.out.println("not found");
            }else{
                usePoint.setEnabled(true);

                total.setText("Total "+ mem.culculatetotal(totalnum));
                totalnum = mem.culculatetotal(totalnum);
                JOptionPane.showMessageDialog(null, ""+mem.getInfocustomer(), "alert", JOptionPane.PLAIN_MESSAGE);


                namecusshow.setText(mem.getName());
            System.out.println(mem.getInfocustomer());}
        }else if(e.getSource().equals(calButton)){
            moneycus.getText();
            System.out.println(totalnum);
            //เช็คตรงนี้
            Double Ex = exchange.calculate(totalnum);;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String formattedEx = String.format("%.2f", Ex);
            changeLabel.setText("Change "+formattedEx);
        }else if(e.getSource().equals(checkBill)){
            if (mem != null& !(showdetail.getText().isEmpty())){
                mem.culculatePoint(totalnum);

                db.memSetPoint(mem);
                System.out.println("After "+mem.getPoint());
                JOptionPane.showMessageDialog(null, ""+mem.getInfocustomer(), "Check Bill", JOptionPane.PLAIN_MESSAGE);
                mem = null;
                f.dispose();
            }else if(mem == null & !(showdetail.getText().isEmpty())){
                Guest g = new Guest(namecusshow.getText());
                JOptionPane.showMessageDialog(null, ""+g.getInfocustomer()+" Check bill", "Check Bill", JOptionPane.PLAIN_MESSAGE);

                f.dispose();
            }
        }else if(e.getSource().equals(usePoint)){
            System.out.println(menuPanel.getSum());
            if (mem.getPoint() > menuPanel.getSum()){
                pointuse = menuPanel.getSum();
                int x = JOptionPane.showConfirmDialog(null, mem.getInfocustomer()+ "You use point "+pointuse, "choose one", JOptionPane.OK_CANCEL_OPTION);
                System.out.println("User clicked button " + x);
                if(x == 0){

                    mem.usePoint(pointuse);
                    JOptionPane.showMessageDialog(null, "" + mem.getInfocustomer() + " Have Point", "Point", JOptionPane.PLAIN_MESSAGE);
                    db.memSetPoint(mem);
                    menuPanel.clearOrder();
                    menuPanel.setTotalcliked();
                    f.dispose();
                }

            }else {
                JOptionPane.showMessageDialog(null, "" + mem.getInfocustomer() + " Have Point", "member don't have Point", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    public JButton getCheckBill() {
        return checkBill;
    }

    public void setCheckBill(JButton checkBill) {
        this.checkBill = checkBill;
    }

    public JTextArea getShowdetail() {
        return showdetail;
    }

    public void setShowdetail(JTextArea showdetail) {
        this.showdetail = showdetail;
    }

    public JLabel getTotal() {
        return total;
    }
    public JFrame getF() {
        return f;
    }

    public void setF(JFrame f) {
        this.f = f;
    }

    public void setTotal(JLabel total) {
        this.total = total;
    }

    public JTextField getTableid() {
        return tableid;
    }

    public void setTableid(JTextField tableid) {
        this.tableid = tableid;
    }

    public JLabel getTablelaJlable() {
        return tablelaJlable;
    }

    public void setTablelaJlable(JLabel tablelaJlable) {
        this.tablelaJlable = tablelaJlable;
    }
    

    @Override
    public void windowOpened(WindowEvent e){
        System.out.println("555");
        db.loadMember();
        member = db.getMember();
        System.out.println(member);
        
        
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        mem = null;

    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    
}
