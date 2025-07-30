import api from './api';

export const taskService = {
  getAll: () => api.get('/tasks'),
  getById: (id) => api.get(`/tasks/${id}`),
  getByProject: (projectId) => api.get(`/tasks/project/${projectId}`),
  filter: (priority = 'HIGH', completed = false) => 
    api.get(`/tasks/filter?priority=${priority}&completed=${completed}`),
  sort: (direction) => api.get(`/tasks/sort?direction=${direction}`),
  create: (taskData) => api.post('/tasks', taskData),
  update: (id, taskData) => api.put(`/tasks/${id}`, taskData),
  patch: (id, taskData) => api.patch(`/tasks/${id}`, taskData),
  delete: (id) => api.delete(`/tasks/${id}`),
};