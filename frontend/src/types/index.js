// User types
export const Priority = {
  HIGH: 'HIGH',
  MEDIUM: 'MEDIUM', 
  LOW: 'LOW'
};

// API response types are handled dynamically in JavaScript
// Backend DTOs correspond to these shapes:

// UserDTO: { id, name, email }
// ProjectDTO: { id, name, user }
// SimpleProjectDTO: { id, name }
// TaskDTO: { id, title, description, dueDate, completed, priority, project }
// SimpleTaskDTO: { id, title, description, dueDate, completed, priority }