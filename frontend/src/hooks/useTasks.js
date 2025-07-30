import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { taskService } from '../services/taskService';
import toast from 'react-hot-toast';

export const useTasks = () => {
  return useQuery({
    queryKey: ['tasks'],
    queryFn: () => taskService.getAll().then(res => res.data),
  });
};

export const useTask = (id) => {
  return useQuery({
    queryKey: ['tasks', id],
    queryFn: () => taskService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useTasksByProject = (projectId) => {
  return useQuery({
    queryKey: ['tasks', 'project', projectId],
    queryFn: () => taskService.getByProject(projectId).then(res => res.data),
    enabled: !!projectId,
  });
};

export const useFilteredTasks = (priority, completed) => {
  return useQuery({
    queryKey: ['tasks', 'filter', priority, completed],
    queryFn: () => taskService.filter(priority, completed).then(res => res.data),
  });
};

export const useSortedTasks = (direction) => {
  return useQuery({
    queryKey: ['tasks', 'sort', direction],
    queryFn: () => taskService.sort(direction).then(res => res.data),
    enabled: !!direction,
  });
};

export const useCreateTask = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: taskService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast.success('Task created successfully');
    },
    onError: (error) => {
      toast.error('Failed to create task: ' + error.message);
    },
  });
};

export const useUpdateTask = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ id, data }) => taskService.update(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast.success('Task updated successfully');
    },
    onError: (error) => {
      toast.error('Failed to update task: ' + error.message);
    },
  });
};

export const usePatchTask = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ id, data }) => taskService.patch(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast.success('Task updated successfully');
    },
    onError: (error) => {
      toast.error('Failed to update task: ' + error.message);
    },
  });
};

export const useDeleteTask = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: taskService.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['tasks'] });
      toast.success('Task deleted successfully');
    },
    onError: (error) => {
      toast.error('Failed to delete task: ' + error.message);
    },
  });
};