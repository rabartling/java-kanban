import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskManagerTest {

    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefaultTaskManager();
    }

    @Test
    void testTaskEquality() {
        Task task1 = new Task("Task 1", "Description 1", Status.NEW);
        assertEquals(task1, task1);
    }

    @Test
    void testTaskSubclassesEquality() {
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", Status.NEW, 1);
        assertEquals(subtask1, subtask1);
    }

    @Test
    void testEpicCannotBeAddedToItself() {
        Epic epic = new Epic("Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Subtask 1", "Description", Status.NEW, epic.getId());
        epic.addSubtask(subtask);
        assertFalse(epic.getSubtasks().contains(epic));
    }

    @Test
    void testAddingAndFindingTasksById() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task("Task 1", "Description 1", Status.NEW);
        Epic epic = new Epic("Epic 1", "Epic Description");
        Subtask subtask = new Subtask("Subtask 1", "Subtask Description", Status.IN_PROGRESS, epic.getId());

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()));

        Task anotherTask = new Task("Another Task", "Another Description", Status.DONE);
        taskManager.createTask(anotherTask);

        assertNotEquals(task, taskManager.getTaskById(anotherTask.getId()));
    }

    @Test
    void testAddingAndFindingTasks() {
        Task task = new Task("Task", "Description", Status.NEW);
        Epic epic = new Epic("Epic", "Epic Description");
        Subtask subtask = new Subtask("Subtask", "Subtask Description", Status.NEW, epic.getId());

        taskManager.createTask(task);
        taskManager.createEpic(epic);
        taskManager.createSubtask(subtask);

        assertEquals(task, taskManager.getTaskById(task.getId()));
        assertEquals(epic, taskManager.getEpicById(epic.getId()));
        assertEquals(subtask, taskManager.getSubtaskById(subtask.getId()));
    }

    @Test
    void testTaskImmutabilityWhenAdded() {
        Task task = new Task("Task", "Description", Status.NEW);
        taskManager.createTask(task);
        Task retrievedTask = taskManager.getTaskById(task.getId());
        assertEquals("Task", retrievedTask.getName());
        assertNotEquals("New Task Name", retrievedTask.getName());
        assertEquals("Task", retrievedTask.getName());
        assertEquals("Description", retrievedTask.getDescription());
        assertEquals(Status.NEW, retrievedTask.getStatus());
    }
}
