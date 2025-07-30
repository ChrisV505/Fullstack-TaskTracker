import { useState } from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  IconButton,
  Button,
  Typography,
  Box,
  CircularProgress,
  Chip,
  TextField,
  MenuItem,
} from '@mui/material';
import { Edit, Delete, Add } from '@mui/icons-material';
import { useTasks, useDeleteTask, usePatchTask } from '../../hooks/useTasks';
import TaskForm from '../../components/tasks/TaskForm';
import { Priority } from '../../types';

const priorityColors = {
  HIGH: 'error',
  MEDIUM: 'warning', 
  LOW: 'success',
};

export default function TasksPage() {
  const [openForm, setOpenForm] = useState(false);
  const [selectedTask, setSelectedTask] = useState(null);
  const [filter, setFilter] = useState({ priority: '', completed: '' });
  
  const { data: tasks, isLoading, error } = useTasks();
  const deleteTask = useDeleteTask();
  const patchTask = usePatchTask();

  const handleEdit = (task) => {
    setSelectedTask(task);
    setOpenForm(true);
  };

  const handleDelete = async (id) => {
    if (window.confirm('Are you sure you want to delete this task?')) {
      try {
        await deleteTask.mutateAsync(id);
      } catch (error) {
        console.error('Error deleting task:', error);
      }
    }
  };

  const handleToggleComplete = async (task) => {
    try {
      await patchTask.mutateAsync({
        id: task.id,
        data: { completed: !task.completed }
      });
    } catch (error) {
      console.error('Error updating task:', error);
    }
  };

  const handleCloseForm = () => {
    setOpenForm(false);
    setSelectedTask(null);
  };

  // Filter tasks based on current filter state
  const filteredTasks = tasks?.filter(task => {
    if (filter.priority && task.priority !== filter.priority) return false;
    if (filter.completed !== '' && task.completed.toString() !== filter.completed) return false;
    return true;
  });

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" minHeight="400px">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return (
      <Box>
        <Typography color="error">Error loading tasks: {error.message}</Typography>
      </Box>
    );
  }

  return (
    <Box>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={3}>
        <Typography variant="h4" component="h1">
          Tasks
        </Typography>
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={() => setOpenForm(true)}
        >
          Add Task
        </Button>
      </Box>

      {/* Filters */}
      <Box display="flex" gap={2} mb={3}>
        <TextField
          select
          label="Priority Filter"
          value={filter.priority}
          onChange={(e) => setFilter(prev => ({ ...prev, priority: e.target.value }))}
          size="small"
          sx={{ minWidth: 120 }}
        >
          <MenuItem value="">All</MenuItem>
          {Object.values(Priority).map((priority) => (
            <MenuItem key={priority} value={priority}>
              {priority}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          select
          label="Status Filter"
          value={filter.completed}
          onChange={(e) => setFilter(prev => ({ ...prev, completed: e.target.value }))}
          size="small"
          sx={{ minWidth: 120 }}
        >
          <MenuItem value="">All</MenuItem>
          <MenuItem value="true">Completed</MenuItem>
          <MenuItem value="false">Pending</MenuItem>
        </TextField>
      </Box>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Title</TableCell>
              <TableCell>Description</TableCell>
              <TableCell>Due Date</TableCell>
              <TableCell>Priority</TableCell>
              <TableCell>Status</TableCell>
              <TableCell>Project</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredTasks?.map((task) => (
              <TableRow key={task.id}>
                <TableCell>{task.id}</TableCell>
                <TableCell>{task.title}</TableCell>
                <TableCell>
                  {task.description?.length > 50 
                    ? `${task.description.substring(0, 50)}...` 
                    : task.description}
                </TableCell>
                <TableCell>
                  {task.dueDate ? new Date(task.dueDate).toLocaleDateString() : '-'}
                </TableCell>
                <TableCell>
                  <Chip 
                    label={task.priority}
                    color={priorityColors[task.priority]}
                    size="small"
                  />
                </TableCell>
                <TableCell>
                  <Chip
                    label={task.completed ? 'Completed' : 'Pending'}
                    color={task.completed ? 'success' : 'default'}
                    size="small"
                    onClick={() => handleToggleComplete(task)}
                    sx={{ cursor: 'pointer' }}
                  />
                </TableCell>
                <TableCell>
                  {task.project ? (
                    <Chip 
                      label={task.project.name}
                      size="small"
                      variant="outlined"
                    />
                  ) : (
                    <Typography variant="body2" color="textSecondary">
                      No project
                    </Typography>
                  )}
                </TableCell>
                <TableCell align="right">
                  <IconButton
                    color="primary"
                    onClick={() => handleEdit(task)}
                  >
                    <Edit />
                  </IconButton>
                  <IconButton
                    color="error"
                    onClick={() => handleDelete(task.id)}
                  >
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <TaskForm
        open={openForm}
        onClose={handleCloseForm}
        task={selectedTask}
      />
    </Box>
  );
}