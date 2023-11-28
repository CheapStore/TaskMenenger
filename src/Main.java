import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        while (true) {
            mainmeniu();
            int scannerr = Scannerr.printint("Bo`lim tanlang :");
            switch (scannerr) {
                case 1 -> creat();
                case 2 -> {
                    String status = Status.ACTIVE.toString();
                    List<Task> allTasksByStatus = getAllTasksByStatus(status);
                    allTasksByStatus.forEach(System.out::println);
                }
                case 3 -> {
                    String status = Status.DONE.toString();
                    List<Task> allTasksByStatus = getAllTasksByStatus(status);
                    allTasksByStatus.forEach(System.out::println);
                }
                case 4 -> {
                    boolean update = update();
                    if (update) {
                        System.out.println("update👌👌👌");
                    } else {
                        System.out.println("id not found 🫢🫢🫢.");
                    }
                }
                case 5 -> {
                    boolean delete = delete();
                    if (delete) {
                        System.out.println("Deleted 😊😊😊");
                    } else {
                        System.out.println("id not found 🫢🫢🫢.");
                    }
                }
                case 6 -> {
                    boolean done = done();
                    if (done) {
                        System.out.println("successful👌");
                    } else {
                        System.out.println("no successful👌");
                    }
                }
                case 7-> show();
                case 0 -> {
                    System.out.println("EXIT");
                    return;
                }
                default -> System.out.println("Qayta urining:");
            }
        }
    }

    private static List<Task> getAllTasksByStatus(String status) {
        List<Task> taskList = new LinkedList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456");  // <2>
            Statement statement = con.createStatement(); // <3>
            String sql = " select * from task where status like '%s'";
            sql = String.format(sql, status);
            ResultSet resultSet = statement.executeQuery(sql);// <4>
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTitle(resultSet.getString("title"));
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                task.setContent(resultSet.getString("content"));
                task.setCreat_date(resultSet.getTimestamp("creat_date").toLocalDateTime());
                if (status.equals(Status.DONE.toString())) {
                    Timestamp finishedDate = resultSet.getTimestamp("finishid_date");
                    if (finishedDate != null) {
                        LocalDateTime finishdate = finishedDate.toLocalDateTime();
                        task.setFinishid_date(finishdate);
                    }
                }
                taskList.add(task);
            }

            con.close();
            return taskList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mainmeniu() {
        System.out.println("""
                ***** Menu *****
                1]Create task
                2]Active Task List
                3]Finished Task List
                4]Update (id)
                5]Delete by id (id):
                6]Mark as Done:
                7]Show Task
                0]Exit
                """);
    }
    public static void creat() {
        try {
            String title = Scannerr.printString(" Enter title:");
            String content = Scannerr.printString("Enter content:");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456"); // <2>
            Statement statement = con.createStatement(); // <3>
            String sql = "insert into task (title,content) values ('%s','%s')";
            sql = String.format(sql, title, content);
            int effectedRows = statement.executeUpdate(sql); // <4>
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean update() {
        String id = Scannerr.printString("Enter Task Id:");
        String title = Scannerr.printString("Enter Title:");
        String content = Scannerr.printString("Enter Content:");
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456"); // <2>
            Statement statement = con.createStatement(); // <3>
            String sql = "UPDATE task SET title ='%s',content= '%s' WHERE id=" + id;
            sql = String.format(sql, title, content);
            int effectedRows = statement.executeUpdate(sql); // <4>
            con.close();
            return effectedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static boolean delete() {
        String id = Scannerr.printString("Enter Delete Task Id:");
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456");  // <2>
            Statement statement = con.createStatement(); // <3>
            String sql = "DELETE FROM task WHERE id =" + id;
            sql = String.format(sql, id);
            int effectedRows = statement.executeUpdate(sql); // <4>
            con.close();
            return effectedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean done() {
        String id = Scannerr.printString("Enter Task Id To Mark it as Done:");
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456"); // <2>
            Statement statement = con.createStatement(); // <3>
            String sql = "update task set finishid_date=now(),status='DONE' where id=" + id;
            sql = String.format(sql, id);
            int effectedRows = statement.executeUpdate(sql); // <4>
            con.close();
            return effectedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void show(){
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_db",
                    "jdbc_user", "123456"); // <2>
            Statement statement = con.createStatement(); // <3>
            ResultSet resultSet = statement.executeQuery("select * from task");
            while (resultSet.next()){
                System.out.println(resultSet.getInt("id")+" "+
                        resultSet.getString("title")+" "+
                        resultSet.getString("content")+" "+
                        resultSet.getString("status")+" "+
                        resultSet.getTimestamp("creat_date")+" "+
                        resultSet.getTimestamp("finishid_date"));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}