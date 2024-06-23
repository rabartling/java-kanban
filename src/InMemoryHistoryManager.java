import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    //История просмотра задач
    private static final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
        if (task != null) {
            history.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return List.copyOf(history);
    }
}