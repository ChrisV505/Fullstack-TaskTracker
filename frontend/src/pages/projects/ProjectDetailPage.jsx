import { useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  Typography,
  Box,
  CircularProgress,
  Card,
  CardContent,
  Button,
  List,
  ListItem,
  ListItemText,
  Chip,
  IconButton,
} from '@mui/material';
import { Add, Edit, Delete } from '@mui/icons-material';
import { useProject } from '../../hooks/useProjects';
import { useTasksByProject, useDeleteTask } from '../../hooks/useTasks';
import TaskForm from '../../components/tasks/TaskForm';

const priorityColors = {
  HIGH: 'error',
  MEDIUM: 'warning', 
  LOW: 'success',
};

export default function ProjectDetailPage() {
  const { id } = useParams();
  const [openTaskForm, setOpenTaskForm] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);

  const { data: project, isLoading: projectLoading } = useProject(id);
  const { data: tasks, isLoading: tasksLoading } = useTasksByProject(id);
  const deleteTask = useDeleteTask();

  const handleEditTask = (task) => {
    setSelectedTask(task);
    setOpenTaskForm(true);
  };

  const handleDeleteTask = async (taskId) => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      try {
        await deleteTask.mutateAsync(taskId);
      } catch (error) {
        console.error('Error deleting task:', error);
      }
    }
  };

  const handleCloseTaskForm = () => {
    setOpenTaskForm(false);
    setSelectedTask(null);
  };

  if (projectLoading || tasksLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Project: {project?.name}
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={() => setOpenTaskForm(true)}
        >
          Add Task
        </Button>
      </Box>

      {project?.user && (
        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Project Details
            </Typography>
            <Typography variant="body1">
              <strong>Assigned to:</strong> {project.user.name} ({project.user.email})
            </Typography>
          </CardContent>
        </Card>
      )}

      <Card>
        <CardContent>
          <Typography variant="h6" gutterBottom>
            Tasks ({tasks?.length || 0})
          </Typography>
          {tasks?.length > 0 ? (
            <List>
              {tasks.map((task) => (
                <ListItem
                  key={task.id}
                  secondaryAction={
                    <Box>
                      <IconButton
                        color="primary"
                        onClick={() => handleEditTask(task)}
                      >
                        <Edit />
                      </IconButton>
                      <IconButton
                        color="error"
                        onClick={() => handleDeleteTask(task.id)}
                      >
                        <Delete />
                      </IconButton>
                    </Box>
                  }
                >
                  <ListItemText
                    primary={
                      <Box display="flex" alignItems="center" gap={1}>
                        {task.title}
                        <Chip 
                          label={task.priority}
                          color={priorityColors[task.priority]}
                          size="small"
                        />
                        {task.completed && (
                          <Chip label="Completed" color="success" size="small" />
                        )}
                      </Box>
                    }
                    secondary={
                      <Box>
                        {task.description && (
                          <Typography variant="body2" color="textSecondary">
                            {task.description}
                          </Typography>
                        )}
                        {task.dueDate && (
                          <Typography variant="body2" color="textSecondary">
                            Due: {new Date(task.dueDate).toLocaleDateString()}
                          </Typography>
                        )}
                      </Box>
                    }
                  />
                </ListItem>
              ))}
            </List>
          ) : (
            <Typography variant="body1" color="textSecondary">
              No tasks in this project yet.
            </Typography>
          )}
        </CardContent>
      </Card>

      <TaskForm
        open={openTaskForm}
        onClose={handleCloseTaskForm}
        task={selectedTask}
      />
    </Box>
  );
}