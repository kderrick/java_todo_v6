import java.util.List;
import org.sql2o.*;

public class Task {
  private int id;
  private String description;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }


  public Task(String description) {
    this.description = description;
  }

public static List<Task> all() {
  String sql = "SELECT id, description FROM Tasks";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Task.class);
  }
 }

 @Override
 public boolean equals(Object otherTask) {
   if (!(otherTask instanceof Task)) {
     return false;
   } else {
     Task newTask = (Task) otherTask;
     return this.getDescription().equals(newTask.getDescription()) &&
      this.getId() == newTask.getId();
   }
 }

 public void save() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "INSERT INTO Tasks (description) VALUES (:description)";
     this.id = (int) con.createQuery(sql, true)
      .addParameter("description", this.description)
      .executeUpdate()
      .getKey();
   }
 }

 public static Task find(int id) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM Tasks where id=:id";
     Task task = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Task.class);
    return task;
   }
 }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM tasks WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
     }
   }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

}
