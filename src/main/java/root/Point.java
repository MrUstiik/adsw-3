package root;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import root.aspect.cache.Cache;

import java.sql.*;

public class Point {
    private int id;
    private int x, y, z;

    public Point() {
        this(0, 0, 0);
    }

    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            stat.executeUpdate("INSERT INTO Point VALUES(null," + x + ", " + y + ", " + z + ");");
            ResultSet rs = stat.executeQuery("SELECT MAX(id) FROM Point;");
            rs.next();
            this.id = Integer.parseInt(rs.getString("MAX(id)"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Point(int id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT * FROM Point WHERE Point.id = " + id + ";");
            rs.next();
            this.id = Integer.parseInt(rs.getString("id"));
            this.x = Integer.parseInt(rs.getString("x"));
            this.y = Integer.parseInt(rs.getString("y"));
            this.z = Integer.parseInt(rs.getString("z"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Cache
    public int getX() {
        System.out.println("in getX() method");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT x FROM Point WHERE Point.id = " + id + ";");
            rs.next();
            x = Integer.parseInt(rs.getString("x"));
            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return x;
    }

    @Cache
    public void setX(int x) {
        this.x = x;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            stat.executeUpdate("UPDATE Point SET x = " + x + " WHERE Point.id = " + id + ";");
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Cache
    public int getY() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT y FROM Point WHERE Point.id = " + id + ";");
            rs.next();
            y = Integer.parseInt(rs.getString("y"));
            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return y;
    }

    @Cache
    public void setY(int y) {
        this.y = y;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            stat.executeUpdate("UPDATE Point SET y = " + y + "WHERE Point.id = " + id + ";");
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Cache
    public int getZ() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("SELECT z FROM Point WHERE Point.id = " + id + ";");
            rs.next();
            z = Integer.parseInt(rs.getString("z"));
            rs.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return z;
    }

    @Cache
    public void setZ(int z) {
        this.z = z;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/points?"
                    + "user=root&password=rootPassword");
            Statement stat = conn.createStatement();
            stat.executeUpdate("UPDATE Point SET z = " + z + "WHERE Point.id = " + id + ";");
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "root.Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("AppContext.xml");
        Point p = ctx.getBean(Point.class);
        //Point p = new Point(1);
        System.out.println("x=" + p.getX());
        System.out.println("x=" + p.getX());
        p.setX(12);
        System.out.println("x=" + p.getX());
        System.out.println("x=" + p.getX());
        p.setX(1);
    }
}
