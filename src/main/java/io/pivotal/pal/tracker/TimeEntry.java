package io.pivotal.pal.tracker;
import java.time.LocalDate;
import java.util.Objects;

public class TimeEntry {
    private Long id;
    private Long projectId;
    private Long userId;
    private LocalDate date;
    private Integer hours;


    public TimeEntry() { }

    public TimeEntry(Long id,Long projectId, Long userId, LocalDate date, Integer hours) {
        this.id = id;
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;

    }


    public TimeEntry(Long projectId, Long userId, LocalDate date, Integer hours) {
        this.projectId = projectId;
        this.userId = userId;
        this.date = date;
        this.hours = hours;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getHours() {
        return hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeEntry timeEntry = (TimeEntry) o;
        return Objects.equals(getProjectId(), timeEntry.getProjectId()) &&
                Objects.equals(getUserId(), timeEntry.getUserId()) &&
                Objects.equals(getDate(), timeEntry.getDate()) &&
                Objects.equals(getHours(), timeEntry.getHours());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getProjectId(), getUserId(), getDate(), getHours());
    }
}
