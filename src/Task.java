import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Task {

    int id;
    String title;


    String content;
    Status status;
    LocalDateTime creat_date;
    LocalDateTime finishid_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreat_date() {
        return creat_date;
    }

    public void setCreat_date(LocalDateTime creat_date) {
        this.creat_date = creat_date;
    }

    public LocalDateTime getFinishid_date() {
        return finishid_date;
    }

    public void setFinishid_date(LocalDateTime finishid_date) {
        this.finishid_date = finishid_date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", creat_date=" + creat_date +
                ", finishid_date=" + finishid_date +
                '}';
    }
}
