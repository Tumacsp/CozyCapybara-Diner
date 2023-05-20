
import java.sql.*;
import java.util.ArrayList;

public class DBConnect{
    public Connection conn;
    public Statement st;
    String url = "jdbc:mysql://www.db4free.net:3306/projectreal"; // URL ของฐานข้อมูล
    String user = "rootoop"; // ชื่อผู้ใช้
    String password = "123456789"; // รหัสัสผ่าน
    private ArrayList<Table> tables;
    public ArrayList<Menu> menus;
    private ArrayList<Member> members;


    public DBConnect(){

        // เชื่อมต่อ MySQL
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn= DriverManager.getConnection(url, user, password);
            System.out.println("The connection to MySQL is complete.");

        }catch(Exception e){
            System.out.println("Unable to connect to MySQL : " + e.getMessage());
        }
    }
    // ปิดการทำงาน MySQL
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // เพิ่มข้อมูลใหม่ลงตาราง
    public void insert(String tableName, String col, String values)throws SQLException{
        String query = "INSERT INTO " + tableName +"("+ col +") VALUES (" + values + ")";
        Statement stmt = this.conn.createStatement();
        stmt.executeUpdate(query);
        System.out.println("inserted successfully!");
    }

    // แก้ไขข้อมูล
    public void update(String tableName, String setValues, String whereClause) throws SQLException {
        String query = "UPDATE " + tableName + " SET " + setValues + " WHERE " + whereClause;
        Statement stmt = this.conn.createStatement();
        stmt.executeUpdate(query);
        System.out.println("updated successfully!");
    }

    // ลบข้อมูลบนตาราง
    public void delete(String tableName, String whereClause) throws SQLException {
        String query = "DELETE FROM " + tableName + " WHERE " + whereClause;
        Statement stmt = this.conn.createStatement();
        stmt.executeUpdate(query);
        System.out.println("deleted successfully!");
    }

    // แสดงข้อมูลของตาราง มีเงื่อนไข
    public ResultSet select(String tableName, String columnNames, String whereClause) throws SQLException {
        String query = "SELECT " + columnNames + " FROM " + tableName + " WHERE " + whereClause;
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Select successfully!");
        return rs;
    }

    // ดึงข้อมูลจากฐานข้อมูล ArrayList
    public ResultSet selectAr(String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName;
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }



    // ดึงข้อมูลจาก ตาราง Table
    public ArrayList<Table> getTableList() throws SQLException{
        ResultSet rs = selectAr("tablenumber");
        // สร้าง ArrayList เพื่อเก็บข้อมูลที่ดึงมา
        tables = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("idTable");
            int cap= rs.getInt("Cap");
            String name = rs.getString("NameCus");
            String tel = rs.getString("TelCus");
            String time = rs.getString("DTime");
            String stus = rs.getString("Status");
            Table table = new Table(id, cap, name, tel, time, stus);
            tables.add(table);
        }
        return tables;

    }

    // ดึงข้อมูลจาก ตาราง MenuList
    public ArrayList<Menu> getMenuList() throws SQLException{
        ResultSet rs = selectAr("menulist");
        // สร้าง ArrayList เพื่อเก็บข้อมูลที่ดึงมา
        menus = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("FoodName");
            String pr = rs.getString("Price");
            String type = rs.getString("Type");
            Menu menu = new Menu(id,name, pr, type);
            menus.add(menu);
        }
        return menus;

    }

    // ดึงข้อมูลจาก ตาราง Customer
    public ArrayList<Member> getMemberList() throws SQLException{
        ResultSet rs = selectAr("customer");
        // สร้าง ArrayList เพื่อเก็บข้อมูลที่ดึงมา
        members = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("idCustomer");
            String name = rs.getString("NameCustomer");
            String tel = rs.getString("TelCustomer");
            String eml = rs.getString("Email");
            int pint= rs.getInt("Point");
            Member member1 = new Member(id, name, tel,eml, pint);
            members.add(member1);
        }
        return members;

    }

    public ArrayList<Member> getMember() {
        return members;
    }

    public void setMember(ArrayList<Member> member) {
        this.members = member;
    }
}