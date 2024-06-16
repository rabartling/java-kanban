public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefaultTaskManager();
        HistoryManager historyManager = Managers.getDefaultHistoryManager();

        // Создание задач
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);
        manager.createTask(task1);
        manager.createTask(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        manager.createEpic(epic1);
        Subtask subtask1_1 = new Subtask("Подзадача 1.1", "Описание подзадачи 1.1", Status.NEW, epic1.getId());
        Subtask subtask1_2 = new Subtask("Подзадача 1.2", "Описание подзадачи 1.2", Status.NEW, epic1.getId());
        manager.createSubtask(subtask1_1);
        manager.createSubtask(subtask1_2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.createEpic(epic2);
        Subtask subtask2_1 = new Subtask("Подзадача 2.1", "Описание подзадачи 2.1", Status.NEW, epic2.getId());
        manager.createSubtask(subtask2_1);

        // Печать списков задач, эпиков и подзадач
        System.out.println("Все задачи: " + manager.getAllTasks());
        System.out.println("Все эпики: " + manager.getAllEpics());
        System.out.println("Все подзадачи: " + manager.getAllSubtasks());

        // Изменение задачи
        task1.setDescription("Описание задачи 1 измененное");
        task1.setName("Задача 1 измененная");
        manager.updateTask(task1);


        // Изменение статусов
        task1.setStatus(Status.IN_PROGRESS);
        manager.updateTask(task1);
        subtask1_1.setStatus(Status.DONE);
        manager.updateTask(subtask1_1);

        // Печать изменённых статусов
        System.out.println("Все задачи после изменения статусов: " + manager.getAllTasks());
        System.out.println("Все эпики после изменения статусов: " + manager.getAllEpics());
        System.out.println("Все подзадачи после изменения статусов: " + manager.getAllSubtasks());

        System.out.println("Получение истории просмотра задач: ");
        manager.getTaskById(task1.getId());
        manager.getSubtaskById(subtask1_1.getId());
        manager.getEpicById(epic1.getId());
        System.out.println(historyManager.getHistory());

        System.out.println("Очистка истории просмотра задач");
        historyManager.clearHistory();
        System.out.println(historyManager.getHistory());

        // Удаление задачи и эпика
        manager.deleteTaskById(task2.getId());
        manager.deleteEpicById(epic1.getId());

        // Печать после удаления
        System.out.println("Все задачи после удаления: " + manager.getAllTasks());
        System.out.println("Все эпики после удаления: " + manager.getAllEpics());
        System.out.println("Все подзадачи после удаления: " + manager.getAllSubtasks());
    }
}