import api from './api';

export const projectService = {
  getAll: () => api.get('/projects'),
  getById: (id) => api.get(`/projects/${id}`),
  getByUser: (userId) => api.get(`/projects/user/${userId}`),
  create: (projectData) => api.post('/projects', projectData),
  update: (id, projectData) => api.put(`/projects/${id}`, projectData),
  patch: (id, projectData) => api.patch(`/projects/${id}`, projectData),
  delete: (id) => api.delete(`/projects/${id}`),
};