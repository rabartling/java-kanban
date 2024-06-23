import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    void testUtilityClassReturnsInitializedInstance() {
        assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void testHistoryManagerRetainsPreviousVersions() {
        TaskManager taskManager = Managers.getDefaultTaskManager();
        Task task = new Task("Task", "Description", Status.NEW);
        taskManager.createTask(task);

        Task initialTask = new Task(task.getName(), task.getDescription(), task.getStatus());
        historyManager.add(initialTask);

        task.setName("Updated Task");
        taskManager.updateTask(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals("Task", history.get(0).getName());
        assertEquals("Description", history.get(0).getDescription());
        assertEquals(Status.NEW, history.get(0).getStatus());
    }
}
