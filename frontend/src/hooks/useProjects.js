import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { projectService } from '../services/projectService';
import toast from 'react-hot-toast';

export const useProjects = () => {
  return useQuery({
    queryKey: ['projects'],
    queryFn: () => projectService.getAll().then(res => res.data),
  });
};

export const useProject = (id) => {
  return useQuery({
    queryKey: ['projects', id],
    queryFn: () => projectService.getById(id).then(res => res.data),
    enabled: !!id,
  });
};

export const useProjectsByUser = (userId) => {
  return useQuery({
    queryKey: ['projects', 'user', userId],
    queryFn: () => projectService.getByUser(userId).then(res => res.data),
    enabled: !!userId,
  });
};

export const useCreateProject = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: projectService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] });
      toast.success('Project created successfully');
    },
    onError: (error) => {
      toast.error('Failed to create project: ' + error.message);
    },
  });
};

export const useUpdateProject = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: ({ id, data }) => projectService.update(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] });
      toast.success('Project updated successfully');
    },
    onError: (error) => {
      toast.error('Failed to update project: ' + error.message);
    },
  });
};

export const useDeleteProject = () => {
  const queryClient = useQueryClient();
  
  return useMutation({
    mutationFn: projectService.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['projects'] });
      toast.success('Project deleted successfully');
    },
    onError: (error) => {
      toast.error('Failed to delete project: ' + error.message);
    },
  });
};