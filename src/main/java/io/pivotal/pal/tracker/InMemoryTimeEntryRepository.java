package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private Map<Long,TimeEntry> mapData = new HashMap<Long,TimeEntry>();
    private Long sequence=0L;


    public TimeEntry create(TimeEntry entry ){
       TimeEntry data = new TimeEntry(++sequence, entry.getProjectId(), entry.getUserId(), entry.getDate(), entry.getHours());
       entry.setId(data.getId());
        mapData.put(data.getId(), data);
        return data;
    }

    public TimeEntry find(Long id){
        return mapData.get(id);
    }

    public List<TimeEntry> list(){
        return new ArrayList<TimeEntry>(mapData.values());

    }

    public TimeEntry delete(Long id ) {
        return mapData.remove(id);
    }

    public TimeEntry update(Long id, TimeEntry entry) {
        TimeEntry existing = mapData.get(id) ;
        if( existing != null ) {
            mapData.put(id, entry);
        }

        entry.setId(id);
        return entry;
    }
}
