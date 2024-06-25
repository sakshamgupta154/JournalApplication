package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.* ;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {

    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry entry)
    {
          journalEntries.put(entry.getId(), entry);
            return true ;


    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalEntry(@PathVariable ObjectId myId)
    {
        return journalEntries.get(myId);
    }

    @DeleteMapping("/id/{myId}")
    public JournalEntry deleteJournalEntry(@PathVariable ObjectId myId)
    {
        return journalEntries.remove(myId);
    }

    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntry(@PathVariable ObjectId myId,@RequestBody JournalEntry entry)
    {
        return journalEntries.put(myId,entry);
    }

}
