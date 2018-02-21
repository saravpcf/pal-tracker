package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    public TimeEntry create(TimeEntry entry ) ;

    public TimeEntry find(Long id);

    public List<TimeEntry> list();

    public TimeEntry delete(Long id_) ;

    public TimeEntry update(Long id, TimeEntry entry) ;
}
